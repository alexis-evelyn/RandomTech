package me.alexisevelyn.randomtech.api.blocks.machines;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

/**
 * The type Power acceptor block.
 */
public abstract class PowerAcceptorBlock extends BlockMachineBase {
    /**
     * Instantiates a new Power acceptor block.
     *
     * @param settings the settings
     */
    public PowerAcceptorBlock(AbstractBlock.Settings settings) {
        super(settings, false);
    }

    /**
     * Instantiates a new Power acceptor block.
     *
     * @param settings     the settings
     * @param customStates the custom states
     */
    public PowerAcceptorBlock(AbstractBlock.Settings settings, boolean customStates) {
        super(settings, customStates);
    }

    /**
     * Gets gui.
     *
     * @return the gui
     */
    @Nullable
    @Override
    public IMachineGuiHandler getGui() {
        return null;
    }

    /**
     * Has comparator output boolean.
     *
     * @param state the state
     * @return the boolean
     */
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    /**
     * Gets comparator output.
     *
     * @param state the state
     * @param world the world
     * @param pos   the pos
     * @return the comparator output
     */
    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return PowerAcceptorBlockEntity.calculateComparatorOutputFromEnergy(world.getBlockEntity(pos));
    }

    /**
     * Gets power.
     *
     * @param world the world
     * @param pos   the pos
     * @return the power
     */
    public double getPower(World world, BlockPos pos) {
        PowerAcceptorBlockEntity powerAcceptorBlockEntity = (PowerAcceptorBlockEntity) world.getBlockEntity(pos);

        if (powerAcceptorBlockEntity == null) {
            return -1.0;
        }

        return powerAcceptorBlockEntity.getEnergy();
    }

    /**
     * Gets max power.
     *
     * @param world the world
     * @param pos   the pos
     * @return the max power
     */
    public double getMaxPower(World world, BlockPos pos) {
        PowerAcceptorBlockEntity powerAcceptorBlockEntity = (PowerAcceptorBlockEntity) world.getBlockEntity(pos);

        if (powerAcceptorBlockEntity == null) {
            return -1.0;
        }

        return powerAcceptorBlockEntity.getBaseMaxPower();
    }
}
