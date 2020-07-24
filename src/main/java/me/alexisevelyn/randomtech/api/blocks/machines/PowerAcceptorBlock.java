package me.alexisevelyn.randomtech.api.blocks.machines;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

public abstract class PowerAcceptorBlock extends BlockMachineBase {
    public PowerAcceptorBlock(AbstractBlock.Settings settings) {
        super(settings, false);
    }

    public PowerAcceptorBlock(AbstractBlock.Settings settings, boolean customStates) {
        super(settings, customStates);
    }

    @Nullable
    @Override
    public IMachineGuiHandler getGui() {
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
        PowerAcceptorBlockEntity powerAcceptorBlockEntity = (PowerAcceptorBlockEntity) world.getBlockEntity(pos);

        if (powerAcceptorBlockEntity == null) {
            return -1.0;
        }

        return powerAcceptorBlockEntity.getEnergy();
    }

    public double getMaxPower(BlockState state, World world, BlockPos pos) {
        PowerAcceptorBlockEntity powerAcceptorBlockEntity = (PowerAcceptorBlockEntity) world.getBlockEntity(pos);

        if (powerAcceptorBlockEntity == null) {
            return -1.0;
        }

        return powerAcceptorBlockEntity.getBaseMaxPower();
    }
}
