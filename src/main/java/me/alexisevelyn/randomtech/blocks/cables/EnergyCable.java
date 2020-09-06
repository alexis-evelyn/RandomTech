package me.alexisevelyn.randomtech.blocks.cables;

import me.alexisevelyn.randomtech.api.blocks.cables.GenericCable;
import me.alexisevelyn.randomtech.api.blocks.machines.PowerAcceptorBlock;
import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

import java.awt.*;

/**
 * The type Energy cable.
 */
public class EnergyCable extends GenericCable {
    public static final IntProperty POWER = IntProperty.of("power", 0, 15);

    /**
     * Instantiates a new Energy cable.
     *
     */
    public EnergyCable() {
        this((float) (3.0/16), FabricBlockSettings
                .of(Materials.CABLE_MATERIAL)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque() // Fixes xray issue. Also allows light pass through block
                .allowsSpawning(GenericBlockHelper::never) // Allows or denies spawning
                .solidBlock(GenericBlockHelper::never) // ??? - Seems to have no apparent effect
                .suffocates(GenericBlockHelper::never) // Suffocates player
                .blockVision(GenericBlockHelper::never) // Blocks Vision inside of block
                .strength(0.3F, 0.3F)
                .ticksRandomly());
    }

    /**
     *
     * @param radius
     * @param settings
     */
    public EnergyCable(float radius, @NotNull Settings settings) {
        super(radius, settings);

        this.setDefaultState(this.getStateManager().getDefaultState()
                .with(POWER, 0)
                .with(GenericCable.WATERLOGGED, false) // For some reason, adding a new property reverses waterlogged to true, so we set it false here too
        );
    }

    /**
     * For Block Form
     *
     * @param state
     * @param world
     * @param pos
     * @param tintIndex
     * @return
     */
    public static int getEdgeColor(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
        return Color.GREEN.getRGB();
    }

    /**
     * For Item Form
     *
     * @param itemStack
     * @param tintIndex
     * @return
     */
    public static int getEdgeColor(ItemStack itemStack, int tintIndex) {
        return Color.GREEN.getRGB();
    }

    /**
     * Is instance of cable boolean.
     *
     * @param block    the block
     * @param world    the world
     * @param blockPos the block pos
     * @return the boolean
     */
    @Override
    public boolean isInstanceOfCable(Block block, WorldAccess world, BlockPos blockPos) {
        return block instanceof EnergyCable;
    }

    /**
     * Is instance of interfaceable block boolean.
     *
     * @param block    the block
     * @param world    the world
     * @param blockPos the block pos
     * @return the boolean
     */
    @Override
    public boolean isInstanceOfInterfaceableBlock(Block block, WorldAccess world, BlockPos blockPos) {
        if (block instanceof PowerAcceptorBlock)
            return true;

        // Checking the instance of also inherently checks if the block entity is null
        return world.getBlockEntity(blockPos) instanceof PowerAcceptorBlockEntity;
    }

    /**
     * Is valid side boolean.
     *
     * @param block    the block
     * @param world    the world
     * @param blockPos the block pos
     * @param side     the side
     * @return the boolean
     */
    @Override
    public boolean isValidSide(Block block, WorldAccess world, BlockPos blockPos, Direction side) {
        return true;
    }

    /**
     * Allows for Opening Gui
     *
     * @param state
     * @param world
     * @param pos
     * @param player
     * @param hand
     * @param hit
     * @return
     */
    @Override
    public ActionResult openGui(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return ActionResult.PASS;
    }

    /**
     *
     * @param state
     * @param world
     * @param pos
     * @param block
     * @param fromPos
     * @param notify
     */
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);

        // world.setBlockState(pos, state.with(POWER, world.getReceivedRedstonePower(pos)));
    }

    /**
     *
     * @param builder the builder
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        builder.add(POWER);
    }
}
