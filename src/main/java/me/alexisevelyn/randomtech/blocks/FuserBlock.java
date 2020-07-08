package me.alexisevelyn.randomtech.blocks;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.Materials;
import me.alexisevelyn.randomtech.RegistryHelper;
import me.alexisevelyn.randomtech.blockentities.FuserBlockEntity;
import me.alexisevelyn.randomtech.blockentities.FuserBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.common.fluid.FluidValue;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

public class FuserBlock extends BlockMachineBase {
    public FuserBlock() {
        super(FabricBlockSettings
                .of(Materials.FirstMaterial)
                .breakByHand(false).requiresTool()
                .breakByTool(FabricToolTags.PICKAXES, ToolMaterials.IRON.getMiningLevel())
                .sounds(BlockSoundGroup.NETHERITE)
                .strength(2.0F, 0.2F));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView worldIn) {
        return new FuserBlockEntity();
    }

    @Override
    public IMachineGuiHandler getGui() {
        return RegistryHelper.fuserGuiHandler;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return PowerAcceptorBlockEntity.calculateComparatorOutputFromEnergy(world.getBlockEntity(pos));
    }

    @Nullable
    public FluidValue getFluidLevel(BlockState state, World world, BlockPos pos) {
        FuserBlockEntity fuserBlockEntity = (FuserBlockEntity) world.getBlockEntity(pos);

        if (fuserBlockEntity == null) {
            return null;
        }

        return fuserBlockEntity.getFluidLevel();
    }

    @Nullable
    public FluidValue getMaxFluidLevel(BlockState state, World world, BlockPos pos) {
        FuserBlockEntity fuserBlockEntity = (FuserBlockEntity) world.getBlockEntity(pos);

        if (fuserBlockEntity == null) {
            return null;
        }

        return fuserBlockEntity.getMaxFluidLevel();
    }

    public double getPower(BlockState state, World world, BlockPos pos) {
        FuserBlockEntity fuserBlockEntity = (FuserBlockEntity) world.getBlockEntity(pos);

        if (fuserBlockEntity == null) {
            return -1.0;
        }

        return fuserBlockEntity.getEnergy();
    }

    public double getMaxPower(BlockState state, World world, BlockPos pos) {
        FuserBlockEntity fuserBlockEntity = (FuserBlockEntity) world.getBlockEntity(pos);

        if (fuserBlockEntity == null) {
            return -1.0;
        }

        return fuserBlockEntity.getBaseMaxPower();
    }
}
