package me.alexisevelyn.randomtech.waila.machines;

import mcp.mobius.waila.api.*;
import me.alexisevelyn.randomtech.blockentities.FuserBlockEntity;
import me.alexisevelyn.randomtech.blocks.FuserBlock;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import me.alexisevelyn.randomtech.blocks.TeleporterBlock;
import me.alexisevelyn.randomtech.waila.WailaRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import reborncore.common.fluid.FluidUtil;
import reborncore.common.fluid.FluidValue;
import reborncore.common.fluid.container.FluidInstance;
import reborncore.common.powerSystem.PowerSystem;
import reborncore.common.util.Tank;

import java.util.List;

/**
 * The type Machines.
 */
public class Machines implements IComponentProvider, IServerDataProvider<BlockEntity> {
    public static final Machines INSTANCE = new Machines();

    /**
     * Append server data.
     *
     * @param compoundTag        the compound tag
     * @param serverPlayerEntity the server player entity
     * @param world              the world
     * @param blockEntity        the block entity
     */
    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayerEntity serverPlayerEntity, World world, BlockEntity blockEntity) {
        // Only Sync Energy Data When Player is Looking at Block With Hywla Active
        // It is slow compared to running it in RebornCore's tick function, but it'll save network data.
        if (blockEntity instanceof MachineBaseBlockEntity) {
            ((MachineBaseBlockEntity) blockEntity).syncWithAll();
        }
    }

    /**
     * Gets stack.
     *
     * @param accessor the accessor
     * @param config   the config
     * @return the stack
     */
    @Override
    public ItemStack getStack(IDataAccessor accessor, IPluginConfig config) {
        return null;
    }

    /**
     * Append head.
     *
     * @param tooltip  the tooltip
     * @param accessor the accessor
     * @param config   the config
     */
    @Override
    public void appendHead(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {

    }

    /**
     * Append body.
     *
     * @param tooltip  the tooltip
     * @param accessor the accessor
     * @param config   the config
     */
    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        boolean configDisplayPower = config.get(WailaRegistry.CONFIG_DISPLAY_POWER, true);
        boolean configDisplayTank = config.get(WailaRegistry.CONFIG_DISPLAY_TANK, true);

        if (configDisplayPower) {
            if (accessor.getBlock() == RegistryHelper.TELEPORTER) {
                TeleporterBlock teleporterBlock = (TeleporterBlock) accessor.getBlock();

                double energy = teleporterBlock.getPower(accessor.getWorld(), accessor.getPosition());
                double maxEnergy = teleporterBlock.getMaxPower(accessor.getWorld(), accessor.getPosition());

                TranslatableText energyLine = new TranslatableText("tooltip.waila.energy", PowerSystem.getLocaliszedPowerNoSuffix(energy), PowerSystem.getLocaliszedPower(maxEnergy));

                tooltip.add(energyLine);
            }

            if (accessor.getBlock() == RegistryHelper.FUSER) {
                FuserBlock fuserBlock = (FuserBlock) accessor.getBlock();

                double energy = fuserBlock.getPower(accessor.getWorld(), accessor.getPosition());
                double maxEnergy = fuserBlock.getMaxPower(accessor.getWorld(), accessor.getPosition());

                TranslatableText energyLine = new TranslatableText("tooltip.waila.energy", PowerSystem.getLocaliszedPowerNoSuffix(energy), PowerSystem.getLocaliszedPower(maxEnergy));

                tooltip.add(energyLine);
            }
        }

        if (configDisplayTank) {
            if (accessor.getBlock() == RegistryHelper.FUSER) {
                FuserBlockEntity fuserBlockEntity = (FuserBlockEntity) accessor.getBlockEntity();

                FluidInstance fluidInstance = fuserBlockEntity.getFluid();
                FluidValue fluidValue = fluidInstance.getAmount();
                Tank tank = fuserBlockEntity.getTank();

                int tankCapacity = -1;
                if (tank != null)
                    tankCapacity = tank.getCapacity().getRawValue();

                TranslatableText fluidLine = new TranslatableText("tooltip.waila.fluid_level", fluidValue.getRawValue(), tankCapacity, FluidUtil.getFluidName(fluidInstance.getFluid()));

                tooltip.add(fluidLine);
            }
        }
    }

    /**
     * Append tail.
     *
     * @param tooltip  the tooltip
     * @param accessor the accessor
     * @param config   the config
     */
    @Override
    public void appendTail(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {

    }
}
