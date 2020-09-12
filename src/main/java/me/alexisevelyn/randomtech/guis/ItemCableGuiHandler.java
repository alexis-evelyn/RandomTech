package me.alexisevelyn.randomtech.guis;

import me.alexisevelyn.randomtech.inventories.ItemCableInventory;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.LiteralText;

public class ItemCableGuiHandler extends ScreenHandler {
    private final ItemCableInventory inventory;
    private final PlayerInventory playerInventory;

    /**
     * Called Client Side - Blank Inventory is Synced With Server
     *
     * @param syncID
     * @param playerInventory
     * @param packetByteBuf
     */
    public ItemCableGuiHandler(int syncID, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        super(RegistryHelper.itemCableScreenHandlerType, syncID);

        this.inventory = new ItemCableInventory();
        this.playerInventory = playerInventory;

        // Incase I run custom logic in the future
        this.inventory.onOpen(this.playerInventory.player);

        checkSize(inventory, 12);
        checkSize(playerInventory, 36);
        drawSlots();
    }

    /**
     * Called Server Side
     *
     * @param syncId
     * @param playerInventory
     * @param inventory
     */
    public ItemCableGuiHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(RegistryHelper.itemCableScreenHandlerType, syncId);

        this.inventory = (ItemCableInventory) inventory;
        this.playerInventory = playerInventory;

        // Incase I run custom logic in the future
        this.inventory.onOpen(this.playerInventory.player);

        checkSize(inventory, 12);
        checkSize(playerInventory, 36);
        drawSlots();
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void drawSlots() {
        // Our Filter Slots
        for (int row = 0; row < 3; ++row)
            for (int column = 0; column < 3; ++column)
                this.addSlot(new Slot(inventory, column + row * 3, 62 + column * 18, 17 + row * 18));

        // Our Real Slots
        for (int row = 0; row < 3; ++row)
            this.addSlot(new Slot(inventory, 9 + row, 30, 17 + row * 18));

        // The player inventory
        for (int row = 0; row < 3; ++row)
            for (int column = 0; column < 9; ++column)
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));

        // The player Hotbar
        for (int column = 0; column < 9; ++column)
            this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
    }

    /**
     * When the player shift clicks
     *
     * @param player
     * @param invSlot
     * @return
     */
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        Slot slot = this.slots.get(invSlot);

        // Don't Allow Shift Clicking into Filter Slots (May change this for UX)
        if (isRealSlot(player, invSlot) && hasStack(slot))
            return this.transferToPlayer(slot);
        if (isPlayerSlot(player, invSlot) && hasStack(slot))
            return this.transferToCable(player, slot);

        return ItemStack.EMPTY;
    }

    /**
     *
     * @param playerSlot
     * @return
     */
    private ItemStack transferToCable(PlayerEntity playerEntity, Slot playerSlot) {
//        ItemStack originalStack = playerSlot.getStack();
//        ItemStack newStack = originalStack.copy();

        // Write Code to Transfer From Player To Cable on Shift-Click
        return ItemStack.EMPTY;
    }

    /**
     *
     * @param ourSlot
     * @return
     */
    private ItemStack transferToPlayer(Slot ourSlot) {
        ItemStack originalStack = ourSlot.getStack();
        ItemStack newStack = originalStack.copy();

        // Rewrite This to Be Good Code
        if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true))
            return ItemStack.EMPTY;
        else if (!this.insertItem(originalStack, 0, this.inventory.size(), false))
            return ItemStack.EMPTY;

        if (originalStack.isEmpty())
            ourSlot.setStack(ItemStack.EMPTY);
        else
            ourSlot.markDirty();

        return newStack;
    }

    private boolean hasStack(Slot slot) {
        return slot != null && slot.hasStack();
    }

    private boolean isEmpty(Slot slot) {
        return slot != null && !slot.hasStack();
    }

    private boolean isPlayerSlot(PlayerEntity playerEntity, int slot) {
        // Better Detection For Player Inventory Slot
        return !(isRealSlot(playerEntity, slot) || isFilterSlot(playerEntity, slot));
    }

    private boolean isRealSlot(PlayerEntity playerEntity, int slot) {
        int[] slots = this.inventory.getRealSlots(null); // You can check the player direction facing the block. I don't need to as all real slots are accessible everywhere

        for (int currentSlot : slots)
            if (currentSlot == slot)
                return true;

        return false;
    }

    private boolean isFilterSlot(PlayerEntity playerEntity, int slot) {
        int[] slots = this.inventory.getFilterSlots(null); // You can check the player direction facing the block. I don't need to as all real slots are accessible everywhere

        for (int currentSlot : slots)
            if (currentSlot == slot)
                return true;

        return false;
    }
}
