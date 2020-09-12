package me.alexisevelyn.randomtech.blockentities.cables;

import me.alexisevelyn.randomtech.api.blocks.cables.GenericCable;
import me.alexisevelyn.randomtech.utility.cablehelpers.ItemTransferHelper;
import me.alexisevelyn.randomtech.api.utilities.pathfinding.dijkstra.Vertex;
import me.alexisevelyn.randomtech.api.utilities.pathfinding.dijkstra.VertexPath;
import me.alexisevelyn.randomtech.blocks.cables.ItemCable;
import me.alexisevelyn.randomtech.guis.ItemCableGuiHandler;
import me.alexisevelyn.randomtech.inventories.ItemCableInventory;
import me.alexisevelyn.randomtech.utility.BlockEntitiesHelper;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The type Item cable block entity.
 */
public class ItemCableBlockEntity extends BlockEntity implements InventoryProvider, Tickable, ExtendedScreenHandlerFactory {
    private final ItemCableInventory inventory;
    private final ItemCable ourCable = new ItemCable();

    /**
     * Instantiates a new Item cable block entity.
     */
    public ItemCableBlockEntity() {
        super(BlockEntitiesHelper.ITEM_CABLE);
        inventory = new ItemCableInventory();
    }

    /**
     * To tag compound tag.
     *
     * @param tag the tag
     * @return the compound tag
     */
    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        // Save Inventory
        Inventories.toTag(tag, this.inventory.getInventory());

        // Inventory is Saved Now, Mark Clean
        this.inventory.markClean();

        return tag;
    }

    /**
     * From tag.
     *
     * @param state the state
     * @param tag   the tag
     */
    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);

        // To Compare Previous Inventory With Current Inventory
        DefaultedList<ItemStack> itemStacks = this.inventory.getInventory();

        // Setup Inventory
        this.inventory.setInventory(DefaultedList.ofSize(this.inventory.size(), ItemStack.EMPTY));
        Inventories.fromTag(tag, this.inventory.getInventory());

        if (world == null)
            return;

        // This allows command blocks to set the inventory of the cable and have items moved.
        // Command blocks do not update the block themselves (using /data merge), but they do modify the inventory and we can tick the cable here
        if (!itemStacks.equals(this.inventory.getInventory()))
            moveItemInNetwork(world);
    }

    /**
     * Move item in network.
     *
     * @param world the world
     */
    // Example Command For Testing: /data merge block ~ ~ ~-4 {Items: [{Slot: 9b, id: "minecraft:bedrock", Count: 1b}]}
    public void moveItemInNetwork(@NotNull World world) {
        // This should only run on the server side.
        if (world.isClient)
            return;

        // Use a boolean to determine if should extract or insert
        // Attempt to Transfer into Inventory
        attemptInsertIntoInterfaceableBlocks(world);

        // Attempt to Transfer Out of Inventory
        attemptExtractFromInterfaceableBlocks(world);

        // Attempt to Transfer To Another Cable
        attemptCableTransfer(world);
    }

    /**
     * Attempt insert into interfaceable blocks.
     *
     * @param world the world
     */
    private void attemptInsertIntoInterfaceableBlocks(@NotNull World world) {
        ItemStack currentItemStack = ItemTransferHelper.findNonEmptyItemStack(inventory);

        // If no itemstacks found, just return
        if (currentItemStack.isEmpty())
            return;

        ItemTransferHelper.tryTransferToContainer(ourCable, world, pos, currentItemStack);
    }

    /**
     * Attempt extract from interfaceable blocks.
     *
     * @param world the world
     */
    private void attemptExtractFromInterfaceableBlocks(@NotNull World world) {
        // Implement Me
    }

    /**
     * Attempt cable transfer.
     *
     * @param world the world
     */
    private void attemptCableTransfer(@NotNull World world) {
        List<BlockPos> currentKnownCables = ourCable.getAllCables(world, pos); // Will be used for the search algorithm later
        List<BlockPos> currentInterfaceableBlocks = ourCable.getAllInterfacingCables(world, currentKnownCables); // Our endpoints to choose from in the search algorithm

        // If nowhere to go, just return (we still need to validate filters and stuff)
        if (currentInterfaceableBlocks.size() == 0)
            return;

        // Look for first non-empty slot
        ItemStack currentItemStack = ItemTransferHelper.findNonEmptyItemStack(inventory);

        // If no itemstacks found, just return
        if (currentItemStack.isEmpty())
            return;

        // We choose a slot ahead of time so we can figure out what slot needs to be transfered
        VertexPath path = findPath(world, currentItemStack, currentKnownCables, currentInterfaceableBlocks);
        Vertex nextVertex = path.getNext();

        // We are at the end of the path (if any path) if this if statement equals true
        if (nextVertex == null || !(nextVertex.getPosition() instanceof BlockPos))
            return;

        // Get Destination's Block Entity
        ItemCableBlockEntity neighborCableEntity = ItemTransferHelper.getCableBlockEntity(world, (BlockPos) nextVertex.getPosition());

        if (neighborCableEntity == null)
            return;

        int[] neighborSlots = ItemTransferHelper.getSlots(neighborCableEntity.inventory);

        // Neighbor doesn't have any slots
        if (neighborSlots == null)
            return;

        // Attempt to Add Items to Neighbor Slots
        for (int slot : neighborSlots) {
            // Don't bother looping through neighbor's slots if nothing to transfer to those slots
            if (currentItemStack.isEmpty())
                break;

            currentItemStack = ItemTransferHelper.transferItemStacks(neighborCableEntity.inventory, slot, currentItemStack);
            world.updateComparators(neighborCableEntity.getPos(), world.getBlockState(neighborCableEntity.getPos()).getBlock());
        }

        world.updateComparators(pos, world.getBlockState(pos).getBlock());
    }

    /**
     * Find path vertex path.
     *
     * @param chosenItemStack            the chosen item stack
     * @param currentKnownCables         the current known cables
     * @param currentInterfaceableBlocks the current interfaceable blocks
     * @return the vertex path
     */
    @NotNull
    private VertexPath findPath(@NotNull World world, @NotNull ItemStack chosenItemStack, @NotNull List<BlockPos> currentKnownCables, @NotNull List<BlockPos> currentInterfaceableBlocks) {
        if (currentInterfaceableBlocks.isEmpty())
            return new VertexPath();

        BlockPos nextBlockPos = null;
        for (BlockPos currentCable : currentInterfaceableBlocks) {
            if (ItemTransferHelper.canReceiveStack(ourCable, world, currentCable, chosenItemStack)) {
                nextBlockPos = currentCable;
                break;
            }
        }

        if (nextBlockPos == null)
            return new VertexPath();

        return GenericCable.dijkstraAlgorithm(currentKnownCables, getPos(), nextBlockPos);
    }

    /**
     * Gets inventory.
     *
     * @param state the state
     * @param world the world
     * @param pos   the pos
     * @return the inventory
     */
    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return inventory;
    }

    /**
     * Tick.
     */
    @Override
    public void tick() {
        // Only Run when World is Not Null
        // Helps Lower World Load Lag
        if (world == null)
            return;

        // Mark self as dirty to ensure the nbt data gets saved on next save
        if (inventory.isDirty())
            markDirty();

        // We only want the cable to move when the inventory is updated
        // Command blocks do not allow marking the inventory as dirty as they update the nbt data directly
        if (inventory.needsProcessing()) {
            inventory.setNeedsProcessing(false);
            moveItemInNetwork(world);
        }
    }

    /**
     *
     * @param serverPlayerEntity
     * @param packetByteBuf
     */
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        // Do Nothing For Now!!!
    }

    /**
     * Returns the title of this screen handler; will be a part of the open
     * screen packet sent to the client.
     */
    @Override
    public Text getDisplayName() {
        return new TranslatableText("block.randomtech.item_cable");
    }

    /**
     *
     * @param syncId
     * @param inv
     * @param player
     * @return
     */
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ItemCableGuiHandler(syncId, player.inventory, this.inventory);
    }
}
