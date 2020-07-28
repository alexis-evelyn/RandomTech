package me.alexisevelyn.randomtech.blockentities;

import me.alexisevelyn.randomtech.api.blockentities.BasePowerAcceptorBlockEntity;
import me.alexisevelyn.randomtech.guis.TeleporterGui;
import me.alexisevelyn.randomtech.utility.BlockEntities;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import me.alexisevelyn.randomtech.blocks.TeleporterBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import reborncore.api.IToolDrop;
import reborncore.api.blockentity.InventoryProvider;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.client.screen.builder.ScreenHandlerBuilder;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.common.powerSystem.PowerSystem;
import reborncore.common.util.RebornInventory;

import java.util.Optional;

public class TeleporterBlockEntity extends BasePowerAcceptorBlockEntity implements IToolDrop, InventoryProvider, BuiltScreenHandlerProvider {
    // TechReborn's Frequency Transmitter
    final Identifier frequencyTransmitterIdentifier = new Identifier("techreborn", "frequency_transmitter");
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType") final Optional<Item> frequencyTransmitter = Registry.ITEM.getOrEmpty(frequencyTransmitterIdentifier);

    // Inventory Slot Markers
    final int inputSlot = 0;

    final int energyAddend = 1000;

    public TeleporterBlockEntity() {
        super(BlockEntities.TELEPORTER);
        this.inventory = new RebornInventory<>(1, "TeleporterBlockEntity", 1, this);
    }

    // Used for TR's Wrench
    @Override
    public ItemStack getToolDrop(PlayerEntity playerEntity) {
        return new ItemStack(RegistryHelper.TELEPORTER);
    }

    @Override
    public BuiltScreenHandler createScreenHandler(int syncID, PlayerEntity playerEntity) {
        return new ScreenHandlerBuilder("teleporter_gui")
                .player(playerEntity.inventory)
                .inventory()
                .hotbar()
                .addInventory()
                .blockEntity(this)
                .slot(inputSlot, TeleporterGui.linkerSlotX, TeleporterGui.linkerSlotY)
                .syncEnergyValue()
                .addInventory()
                .create(this, syncID);
    }

    @Override
    public void tick() {
        super.tick();

        if (world == null){
            return;
        }

        if (world.getTime() % 20 == 0)
            updateEnergyModelState();

        PlayerEntity playerEntity = world.getClosestPlayer(getPos().getX(), getPos().getY(), getPos().getZ(), 2, false);

        if (isPlayerReadyToTeleport(playerEntity))
            teleportPlayer(playerEntity);
    }

    private void teleportPlayer(PlayerEntity playerEntity) {
        if (hasValidTeleporterItem()) {
            // Send Alert if Not Enough Energy
            if (!hasEnoughEnergy(energyAddend)) {
                alertNotEnoughEnergy(playerEntity);
                return;
            }

            ItemStack inputItem = inventory.getStack(inputSlot);

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

           try {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) playerEntity;

                serverPlayerEntity.teleport(newWorld, pos[0], pos[1], pos[2], serverPlayerEntity.getHeadYaw(), serverPlayerEntity.getPitch(20));
                addEnergy(-1 * energyAddend); // Take out the energy from use of the teleporter
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

    // This checks for my Teleporter Linker (used if TechReborn is not installed)
    public boolean isTeleporterItem(ItemStack item) {
        return item.getItem().equals(RegistryHelper.TELEPORTER_LINKER);
    }

    public boolean isPlayerReadyToTeleport(PlayerEntity playerEntity) {
        return playerEntity != null && playerEntity.isInSneakingPose() && playerEntity.getBlockPos().equals(this.pos.add(0, 1, 0));
    }

    private void alertNotEnoughEnergy(PlayerEntity playerEntity) {
        if (playerEntity == null)
            return;

        Text message = new TranslatableText("message.randomtech.teleporter_energy_fail",
                new LiteralText(PowerSystem.getLocaliszedPower(energyAddend))
                        .formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                new LiteralText(PowerSystem.getLocaliszedPower(getEnergy()))
                        .formatted(Formatting.DARK_RED, Formatting.BOLD))
                .formatted(Formatting.GOLD, Formatting.BOLD);

        playerEntity.sendMessage(message, true);
    }

    public boolean hasValidTeleporterItem() {
        ItemStack inputItem = inventory.getStack(inputSlot);

        return isTRFrequencyTransmitter(inputItem) || isTeleporterItem(inputItem);
    }

    // Used to Update Visible Texture of Block
    public void updateEnergyModelState() {
        if (world == null)
            return;

        if (hasEnoughEnergy(energyAddend) && hasValidTeleporterItem())
            world.setBlockState(pos, world.getBlockState(pos).with(BlockMachineBase.ACTIVE, true));
        else
            world.setBlockState(pos, world.getBlockState(pos).with(BlockMachineBase.ACTIVE, false));

        // This exists solely to have dynamic light levels based on Teleporter Energy Stored
        world.setBlockState(pos, world.getBlockState(pos).with(TeleporterBlock.ENERGY, getEnergyState()));
    }

    // Convert Current Energy Level to Level Within (Not Exclusive) Range 0 - 15
    public int getEnergyState() {
        int minLevel = 0;
        int maxLevel = 15;

        // Formula Pulled From: https://stackoverflow.com/a/929107/6828099
        double energyRange = (getMaxPower() - getMinPower());
        double stateRange = maxLevel - minLevel;
        int energyState;

        if (energyRange == 0)
            energyState = 0;
        else
        {
            energyState = (int) ((((getEnergy() - getMinPower()) * stateRange) / energyRange) + minLevel);
        }

        return energyState;
    }
}
