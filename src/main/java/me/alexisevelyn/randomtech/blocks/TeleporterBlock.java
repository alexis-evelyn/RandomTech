package me.alexisevelyn.randomtech.blocks;

import me.alexisevelyn.randomtech.MainClient;
import me.alexisevelyn.randomtech.Materials;
import me.alexisevelyn.randomtech.blockentities.TeleporterBlockEntity;
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

public class TeleporterBlock extends BlockMachineBase {
    public TeleporterBlock() {
        super(FabricBlockSettings
                .of(Materials.FirstMaterial)
                .breakByHand(false).requiresTool()
                .breakByTool(FabricToolTags.PICKAXES, ToolMaterials.IRON.getMiningLevel())
                .sounds(BlockSoundGroup.NETHERITE)
                .strength(2.0F, 0.2F));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView worldIn) {
        return new TeleporterBlockEntity();
    }

    @Override
    public IMachineGuiHandler getGui() {
        return MainClient.teleporterGuiHandler;
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
        TeleporterBlockEntity teleporterBlockEntity = (TeleporterBlockEntity) world.getBlockEntity(pos);

        if (teleporterBlockEntity == null) {
            return -1.0;
        }

        return teleporterBlockEntity.getEnergy();
    }

    public double getMaxPower(BlockState state, World world, BlockPos pos) {
        TeleporterBlockEntity teleporterBlockEntity = (TeleporterBlockEntity) world.getBlockEntity(pos);

        if (teleporterBlockEntity == null) {
            return -1.0;
        }

        return teleporterBlockEntity.getBaseMaxPower();
    }
}
