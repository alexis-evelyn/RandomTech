package me.alexisevelyn.randomtech.blockentities;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.alexisevelyn.randomtech.BlockEntities;
import me.alexisevelyn.randomtech.Main;
import net.fabricmc.fabric.api.dimension.v1.EntityPlacer;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TeleportCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import reborncore.api.IToolDrop;
import reborncore.api.blockentity.IUpgrade;
import reborncore.api.blockentity.InventoryProvider;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.client.screen.builder.ScreenHandlerBuilder;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;
import reborncore.common.powerSystem.PowerSystem;
import reborncore.common.util.RebornInventory;

import java.util.Optional;
import java.util.Set;

public class TeleporterBlockEntity extends PowerAcceptorBlockEntity implements IToolDrop, InventoryProvider, BuiltScreenHandlerProvider {
    // Does Nothing
    int state = 0;

    // Energy Values
    double energyAddend = -1000.0;
    double maxPower = 10000;
    double maxInput = 10000;

    // TechReborn's Frequency Transmitter
    Identifier frequencyTransmitterIdentifier = new Identifier("techreborn", "frequency_transmitter");
    Optional<Item> frequencyTransmitter = Registry.ITEM.getOrEmpty(frequencyTransmitterIdentifier);

    // Inventory Slot Markers
    RebornInventory<TeleporterBlockEntity> inventory;
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
        //}

        PlayerEntity playerEntity = world.getClosestPlayer(getPos().getX(), getPos().getY(), getPos().getZ(), 2, false);

        if (isPlayerReadyToTeleport(playerEntity) && hasEnoughEnergy())
            teleportPlayer(playerEntity);
        else if (isPlayerReadyToTeleport(playerEntity) && !hasEnoughEnergy())
            alertNotEnoughEnergy(playerEntity);
    }

    private void teleportPlayer(PlayerEntity playerEntity) {
        ItemStack inputItem = inventory.getStack(inputSlot);

        if (isTRFrequencyTransmitter(inputItem) || isTeleporterItem(inputItem)) {
            // {"tag": {"pos": {"pos": ["I", 9, 55, 12], "dimension": "minecraft:overworld"}}}
            CompoundTag itemTag = inputItem.getTag();

            if (itemTag == null)
                return;

            CompoundTag root = itemTag.getCompound("pos");

            int[] pos = root.getIntArray("pos");

            if (pos.length != 3)
                return;

            if (playerEntity.getServer() == null) {
                // TODO: Log fatal error

                return;
            }

            Identifier dimension = new Identifier(root.getString("dimension"));
            ServerWorld newWorld = playerEntity.getServer().getWorld(RegistryKey.of(Registry.DIMENSION, dimension));

            if (newWorld == null) {
                // TODO: Alert player world is not loaded

                return;
            }

            // The teleport methods by default place the vanilla portal associated with that dimension. Nether - Nether Portal. Overworld - Nether Portal. End - End Portal?
            // This solves that problem by overriding the vanilla entity placement logic.
            EntityPlacer entityPlacer = new EntityPlacer() {
                @Override
                public BlockPattern.TeleportTarget placeEntity(Entity teleported, ServerWorld destination, Direction portalDir, double horizontalOffset, double verticalOffset) {
                    return new BlockPattern.TeleportTarget(new Vec3d(pos[0], pos[1], pos[2]), teleported.getVelocity(), (int) teleported.getHeadYaw());
                }
            };

           try {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) playerEntity;

                serverPlayerEntity.teleport(newWorld, (double) pos[0], (double) pos[1], (double) pos[2], serverPlayerEntity.getHeadYaw(), serverPlayerEntity.getPitch(20));
                addEnergy(energyAddend); // Take out the energy from use of the teleporter
            } catch (Exception exception) {
                System.out.println("Teleport Exception: ");
                exception.printStackTrace();
            }
        }
    }

    // This checks for TechReborn's Frequency Transmitter and Outputs the Destination of the Transmitter
    // techreborn:frequency_transmitter
    public boolean isTRFrequencyTransmitter(ItemStack item) {
        return frequencyTransmitter.isPresent() && item.getItem().equals(frequencyTransmitter.get());
    }

    // This checks for my Teleporter Control Item (used if TechReborn is not installed)
    public boolean isTeleporterItem(ItemStack item) {
        return item.getItem().equals(Main.TELEPORTER_CONTROL);
    }

    public boolean isPlayerReadyToTeleport(PlayerEntity playerEntity) {
        return playerEntity != null && playerEntity.isInSneakingPose() && playerEntity.getBlockPos().equals(this.pos.add(0, 1, 0));
    }

    private void alertNotEnoughEnergy(PlayerEntity playerEntity) {
        if (playerEntity == null)
            return;

        Text message = new TranslatableText("message.randomtech.teleporter_energy_fail",
                PowerSystem.getLocaliszedPower(-1 * energyAddend),
                PowerSystem.getLocaliszedPower(getEnergy()));

        playerEntity.sendMessage(message, true);
    }

    public boolean hasEnoughEnergy() {
        return getEnergy() >= (-1 * energyAddend);
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
