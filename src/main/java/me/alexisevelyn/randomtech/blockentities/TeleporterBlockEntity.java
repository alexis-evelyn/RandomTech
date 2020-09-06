package me.alexisevelyn.randomtech.blockentities;

import me.alexisevelyn.randomtech.api.blockentities.BasePowerAcceptorBlockEntity;
import me.alexisevelyn.randomtech.api.utilities.CalculationHelper;
import me.alexisevelyn.randomtech.blocks.TeleporterBlock;
import me.alexisevelyn.randomtech.guis.TeleporterGui;
import me.alexisevelyn.randomtech.utility.BlockEntitiesHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
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

import java.util.Arrays;
import java.util.Optional;

/**
 * The type Teleporter block entity.
 */
public class TeleporterBlockEntity extends BasePowerAcceptorBlockEntity implements IToolDrop, InventoryProvider, BuiltScreenHandlerProvider {
    // TechReborn's Frequency Transmitter
    private final Identifier frequencyTransmitterIdentifier = new Identifier("techreborn", "frequency_transmitter");
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType") private final Optional<Item> frequencyTransmitter = Registry.ITEM.getOrEmpty(frequencyTransmitterIdentifier);

    // Inventory Slot Markers
    private final int inputSlot = 0;

    // Energy Usage
    private final int energyAddend = 1000;

    // Scheduler Setup
    private long tickTime;
    private long lastTickTime = Util.getMeasuringTimeMs();

    private ServerPlayerEntity serverPlayerEntity;
    private ServerWorld newWorld;
    private int[] newPos;

    private final long delayTeleport = 2 * 1000;
    private boolean isPlayingTeleportSound = false;

    /**
     * Instantiates a new Teleporter block entity.
     */
    public TeleporterBlockEntity() {
        super(BlockEntitiesHelper.TELEPORTER);
        this.inventory = new RebornInventory<>(1, "TeleporterBlockEntity", 1, this);

        registerScheduler();
    }

    /**
     * Register scheduler.
     */
    public final void registerScheduler() {
        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            this.tickTime = Util.getMeasuringTimeMs();
            if (this.lastTickTime < this.tickTime - this.delayTeleport) {
                this.lastTickTime = this.tickTime;

                if (serverPlayerEntity == null || newWorld == null || pos == null)
                    return;

                serverPlayerEntity.teleport(newWorld, newPos[0], newPos[1], newPos[2], serverPlayerEntity.getHeadYaw(), serverPlayerEntity.getPitch(20));
                addEnergy(-1 * getEnergyAddend()); // Take out the energy from use of the teleporter

                serverPlayerEntity = null;
                newWorld = null;
                newPos = null;
                isPlayingTeleportSound = false;
            }
        });
    }

    /**
     * Gets energy addend.
     *
     * @return the energy addend
     */
    public int getEnergyAddend() {
        return this.energyAddend;
    }

    /**
     * Gets tool drop.
     *
     * @param playerEntity the player entity
     * @return the tool drop
     */
    // Used for TR's Wrench
    @Override
    public ItemStack getToolDrop(PlayerEntity playerEntity) {
        return new ItemStack(RegistryHelper.TELEPORTER);
    }

    /**
     * Create screen handler built screen handler.
     *
     * @param syncID       the sync id
     * @param playerEntity the player entity
     * @return the built screen handler
     */
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

    /**
     * Tick.
     */
    @Override
    public void tick() {
        super.tick();

        if (world == null) {
            return;
        }

        if (world.getTime() % 20 == 0)
            updateEnergyModelState();

        PlayerEntity playerEntity = world.getClosestPlayer(getPos().getX(), getPos().getY(), getPos().getZ(), 2, false);

        if (isPlayerReadyToTeleport(playerEntity))
            teleportPlayer(playerEntity);
    }

    /**
     * Teleport player.
     *
     * @param playerEntity the player entity
     */
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

                initializeAndTeleport(serverPlayerEntity, newWorld, pos);
            } catch (Exception exception) {
                System.out.println("Teleport Exception: ");
                exception.printStackTrace();
            }
        }
    }

    /**
     * Initialize and teleport.
     *
     * @param serverPlayerEntity the server player entity
     * @param newWorld           the new world
     * @param pos                the pos
     */
    public void initializeAndTeleport(ServerPlayerEntity serverPlayerEntity, ServerWorld newWorld, int[] pos) {
        if (!isPlayingTeleportSound) {
            newWorld.playSound(
                    null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                    this.getPos(), // The position of where the sound will come from
                    RegistryHelper.TELEPORTER_TELEPORTS_SOUND, // The sound that will play
                    SoundCategory.BLOCKS, // This determines which of the volume sliders affect this sound
                    1.0F, // Volume multiplier, 1 is normal, 0.5 is half volume, etc
                    1.0F // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
            );

            isPlayingTeleportSound = true;
        }

        // Take a look at https://discordapp.com/channels/507304429255393322/507982478276034570/740471565866631279
        this.serverPlayerEntity = serverPlayerEntity;
        this.newWorld = newWorld;
        this.newPos = Arrays.copyOf(pos, pos.length);
    }

    /**
     * Is tr frequency transmitter boolean.
     *
     * @param item the item
     * @return the boolean
     */
    // This checks for TechReborn's Frequency Transmitter and Outputs the Destination of the Transmitter
    // techreborn:frequency_transmitter
    public boolean isTRFrequencyTransmitter(ItemStack item) {
        return frequencyTransmitter.isPresent() && item.getItem().equals(frequencyTransmitter.get());
    }

    /**
     * Is teleporter item boolean.
     *
     * @param item the item
     * @return the boolean
     */
    // This checks for my Teleporter Linker (used if TechReborn is not installed)
    public boolean isTeleporterItem(ItemStack item) {
        return item.getItem().equals(RegistryHelper.TELEPORTER_LINKER);
    }

    /**
     * Is player ready to teleport boolean.
     *
     * @param playerEntity the player entity
     * @return the boolean
     */
    public boolean isPlayerReadyToTeleport(PlayerEntity playerEntity) {
        return playerEntity != null && playerEntity.isInSneakingPose() && playerEntity.getBlockPos().equals(this.pos.add(0, 5.0/16, 0));
    }

    /**
     * Alert not enough energy.
     *
     * @param playerEntity the player entity
     */
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

    /**
     * Has valid teleporter item boolean.
     *
     * @return the boolean
     */
    public boolean hasValidTeleporterItem() {
        ItemStack inputItem = inventory.getStack(inputSlot);

        return isTRFrequencyTransmitter(inputItem) || isTeleporterItem(inputItem);
    }

    /**
     * Update energy model state.
     */
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

    /**
     * Gets energy state.
     *
     * @return the energy state
     */
    // Convert Current Energy Level to Level Within (Not Exclusive) Range 0 - 15
    public int getEnergyState() {
        int minLevel = 0;
        int maxLevel = 15;

        double energyRange = (getMaxPower() - getMinPower());
        int energyState;

        if (energyRange == 0)
            energyState = 0;
        else
            energyState = (int) Math.floor(CalculationHelper.proportionCalculator(getEnergy(), getMinPower(), energyRange, minLevel, maxLevel)); // ((((getEnergy() - getMinPower()) * stateRange) / energyRange) + minLevel);

        return energyState;
    }
}
