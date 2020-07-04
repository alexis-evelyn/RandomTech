package me.alexisevelyn.randomtech.blockentities;

import me.alexisevelyn.randomtech.BlockEntities;
import me.alexisevelyn.randomtech.Main;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import reborncore.api.IToolDrop;
import reborncore.api.blockentity.IUpgrade;
import reborncore.api.blockentity.InventoryProvider;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.client.screen.builder.ScreenHandlerBuilder;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;
import reborncore.common.util.RebornInventory;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class TeleporterBlockEntity extends PowerAcceptorBlockEntity implements IToolDrop, InventoryProvider, BuiltScreenHandlerProvider {
    int state = 0;
    double energyAddend = -100.0;
    double maxPower = 10000;
    double maxInput = 10000;
    RebornInventory<TeleporterBlockEntity> inventory;

    World dimension;
    Vector3f coordinates = new Vector3f();

    int inputSlot = 0;

    public TeleporterBlockEntity() {
        super(BlockEntities.TELEPORTER);
        this.inventory = new RebornInventory<>(1, "TeleporterBlockEntity", 1, this);
    }

    @Override
    public double getBaseMaxPower() {
        return maxPower;
    }

    @Override
    public double getBaseMaxOutput() {
        return 0;
    }

    @Override
    public double getBaseMaxInput() {
        return maxInput;
    }

    // Used for TR's Wrench
    @Override
    public ItemStack getToolDrop(PlayerEntity playerEntity) {
        return new ItemStack(Main.TELEPORTER);
    }

    @Override
    public int getMaxCountPerStack() {
        return inventory.getMaxCountPerStack();
    }

    @Override
    public void onOpen(PlayerEntity player) {

    }

    @Override
    public void onClose(PlayerEntity player) {

    }

    @Override
    public int count(Item item) {
        return inventory.count(item);
    }

    @Override
    public boolean containsAny(Set<Item> items) {
        return inventory.containsAny(items);
    }

    @Override
    public boolean canBeUpgraded() {
        return false;
    }

    @Override
    public boolean isUpgradeValid(IUpgrade upgrade, ItemStack stack) {
        return false;
    }

    // Setters and getters for the GUI to sync
    private void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    @Override
    public BuiltScreenHandler createScreenHandler(int syncID, PlayerEntity playerEntity) {
        return new ScreenHandlerBuilder("teleporter_gui")
                .player(playerEntity.inventory)
                .inventory()
                .hotbar()
                .addInventory()
                .blockEntity(this)
                .slot(inputSlot, 8, 72)
                .syncEnergyValue()
                .sync(this::getState, this::setState)
                .addInventory()
                .create(this, syncID);
    }

    @Override
    public void tick() {
        super.tick();

        if (world == null){
            return;
        }

        //if (world.getTime() % 20 == 0) {
            //world.setBlockState(pos, world.getBlockState(pos).with(BlockMachineBase.ACTIVE, false));
            //addEnergy(energyAddend);

            updateTeleportLocation();
        //}
    }

    private void updateTeleportLocation() {
        // WARNING!!! This Below Code Has VERY LITTLE ERROR CHECKING
        // -------------------------------------------------
        // This checks for TechReborn's Frequency Transmitter and Outputs the Destination of the Transmitter
        // techreborn:frequency_transmitter
        Identifier frequencyTransmitterIdentifier = new Identifier("techreborn", "frequency_transmitter");
        Optional<Item> frequencyTransmitter = Registry.ITEM.getOrEmpty(frequencyTransmitterIdentifier);
        ItemStack inputItem = inventory.getStack(inputSlot);

        // TODO: Clean Up Code and Functionify It. Also Add Error Checking For Tags!!!
        if (frequencyTransmitter.isPresent() && inputItem.getItem().equals(frequencyTransmitter.get())) {
            // {"tag": {"pos": {"pos": ["I", 9, 55, 12], "dimension": "minecraft:overworld"}}}
            CompoundTag itemTag = inputItem.getTag();

            CompoundTag root = itemTag.getCompound("pos");

            int[] pos = root.getIntArray("pos");
            String dimension = root.getString("dimension");
            // dimension

            PlayerEntity playerEntity = Objects.requireNonNull(getWorld()).getClosestPlayer(getPos().getX(), getPos().getY(), getPos().getZ(), 2, false);
            coordinates.set(pos[0], pos[1], pos[2]);

            if (playerEntity != null && playerEntity.isInSneakingPose() && playerEntity.getBlockPos().equals(this.pos.add(0, 1, 0))) {
                //System.out.println("Detected: " + playerEntity.getDisplayName().toString());

                playerEntity.teleport(coordinates.getX(), coordinates.getY(), coordinates.getZ());
            }

//            System.out.println("Teleporter Destination");
//            System.out.println("Dimension: " + dimension);
//
//            System.out.println("Position X: " + pos[0]);
//            System.out.println("Position Y: " + pos[1]);
//            System.out.println("Position Z: " + pos[2]);
        }
        // -------------------------------------------------
    }

    @Override
    public boolean canAcceptEnergy(final Direction direction) {
        return true;
    }

    @Override
    public boolean canProvideEnergy(final Direction direction) {
        return false;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void fromTag(BlockState blockState, CompoundTag compoundTag) {
        super.fromTag(blockState, compoundTag);
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        super.toTag(compoundTag);

        return compoundTag;
    }
}
