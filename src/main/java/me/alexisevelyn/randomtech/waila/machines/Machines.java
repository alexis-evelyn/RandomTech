package me.alexisevelyn.randomtech.waila.machines;

import mcp.mobius.waila.api.*;
import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.blocks.TeleporterBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import reborncore.common.powerSystem.PowerSystem;

import java.util.List;

public class Machines implements IComponentProvider, IServerDataProvider<BlockEntity> {
    static final Machines INSTANCE = new Machines();

    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayerEntity serverPlayerEntity, World world, BlockEntity blockEntity) {

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
        // TODO: Figure out how to save config on init
        if (!config.get(WailaRegistry.CONFIG_DISPLAY_POWER)) {
            if (accessor.getBlock() == Main.TELEPORTER) {
                TeleporterBlock teleporterBlock = (TeleporterBlock) accessor.getBlock();

                double energy = teleporterBlock.getPower(accessor.getBlockState(), accessor.getWorld(), accessor.getPosition());

                // Doesn't get the entity associated with the block, only the registered block entity
                TranslatableText energyLine = new TranslatableText("tooltip.waila.energy", PowerSystem.getLocaliszedPower(energy));

                tooltip.add(energyLine);
            }
        }
    }

    @Override
    public void appendTail(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {

    }
}
