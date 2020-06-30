package me.alexisevelyn.randomtech.blocks;

import me.alexisevelyn.randomtech.Materials;
import me.alexisevelyn.randomtech.blockentities.FirstBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.BlockSoundGroup;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

import java.util.Random;

public class FirstBlock extends BlockMachineBase {
    public FirstBlock() {
        super(FabricBlockSettings
                .of(Materials.FirstMaterial)
                .breakByHand(false).requiresTool()
                .breakByTool(FabricToolTags.PICKAXES, ToolMaterials.IRON.getMiningLevel())
                .sounds(BlockSoundGroup.NETHERITE)
                .strength(2.0F, 0.2F));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView worldIn) {
        return new FirstBlockEntity();
    }

    @Override
    public IMachineGuiHandler getGui() {
        //return GuiType.FIRST_GUI;

        return null;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return PowerAcceptorBlockEntity.calculateComparatorOutputFromEnergy(world.getBlockEntity(pos));
    }

    public double getPower(BlockState state, World world, BlockPos pos) {
        FirstBlockEntity firstBlockEntity = (FirstBlockEntity) world.getBlockEntity(pos);

        if (firstBlockEntity == null) {
            return -1.0;
        }

        // TODO: Fix Me!!! This does not reflect power loss from when machines take power from this block!!!
        return firstBlockEntity.getEnergy();
    }
}
