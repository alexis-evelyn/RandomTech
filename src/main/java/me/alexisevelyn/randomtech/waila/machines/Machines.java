package me.alexisevelyn.randomtech.waila.machines;

import mcp.mobius.waila.addons.minecraft.PluginMinecraft;
import mcp.mobius.waila.api.*;
import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.blockentities.FirstBlockEntity;
import me.alexisevelyn.randomtech.blocks.FirstBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

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
            if (accessor.getBlock() == Main.FIRST_BLOCK) {
                FirstBlock firstBlock = (FirstBlock) accessor.getBlock();
                FirstBlockEntity firstBlockEntity = (FirstBlockEntity) accessor.getBlockEntity();

                // Doesn't get the entity associated with the block, only the registered block entity
                TranslatableText energy = new TranslatableText("tooltip.waila.energy", firstBlockEntity.getEnergy());

                tooltip.add(energy);
            }
        }
    }

    @Override
    public void appendTail(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {

    }
}
