package me.alexisevelyn.randomtech.waila.machines;

import mcp.mobius.waila.api.*;
import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.RegistryHelper;
import me.alexisevelyn.randomtech.blocks.TeleporterBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import reborncore.common.powerSystem.PowerSystem;

import java.util.List;

public class Machines implements IComponentProvider, IServerDataProvider<BlockEntity> {
    static final Machines INSTANCE = new Machines();

    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayerEntity serverPlayerEntity, World world, BlockEntity blockEntity) {
        // Only Sync Energy Data When Player is Looking at Block With Hywla Active
        // It is slow compared to running it in RebornCore's tick function, but it'll save network data.
        if (blockEntity instanceof MachineBaseBlockEntity) {
            ((MachineBaseBlockEntity) blockEntity).syncWithAll();
        }
    }

    @Override
    public ItemStack getStack(IDataAccessor accessor, IPluginConfig config) {
        return null;
    }

    @Override
    public void appendHead(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {

    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        boolean configDisplayPower = config.get(WailaRegistry.CONFIG_DISPLAY_POWER, true);

        if (configDisplayPower) {
            if (accessor.getBlock() == RegistryHelper.TELEPORTER) {
                TeleporterBlock teleporterBlock = (TeleporterBlock) accessor.getBlock();

                double energy = teleporterBlock.getPower(accessor.getBlockState(), accessor.getWorld(), accessor.getPosition());
                double maxEnergy = teleporterBlock.getMaxPower(accessor.getBlockState(), accessor.getWorld(), accessor.getPosition());

                TranslatableText energyLine = new TranslatableText("tooltip.waila.energy", PowerSystem.getLocaliszedPowerNoSuffix(energy), PowerSystem.getLocaliszedPower(maxEnergy));

                tooltip.add(energyLine);
            }
        }
    }

    @Override
    public void appendTail(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {

    }
}
