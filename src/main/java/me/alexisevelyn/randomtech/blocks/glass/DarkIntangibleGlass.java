package me.alexisevelyn.randomtech.blocks.glass;

import me.alexisevelyn.randomtech.blockentities.IntangibleDarkGlassBlockEntity;
import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * The type Dark intangible glass.
 */
public class DarkIntangibleGlass extends AbstractGlassBlock implements BlockEntityProvider {
    /**
     * Instantiates a new Dark intangible glass.
     */
    public DarkIntangibleGlass() {
        super(FabricBlockSettings
                .of(Materials.DARK_GLASS_MATERIAL)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque() // Fixes xray issue. Also allows light pass through block
//                .noCollision() // Allows walking through block. Also allows light pass through block
//                .collidable(false) // Allows Disabling Collision Without Allowing Light to Pass Through (Cannot set after block initialized)
                .allowsSpawning(GenericBlockHelper::never) // Allows or denies spawning
                .solidBlock(GenericBlockHelper::never) // ??? - Seems to have no apparent effect
                .suffocates(GenericBlockHelper::never) // Suffocates player
                .blockVision(GenericBlockHelper::never) // Blocks Vision inside of block
                .strength(0.3F, 0.3F)
        );
    }

    /**
     * Gets opacity.
     *
     * @param state the state
     * @param world the world
     * @param pos   the pos
     * @return the opacity
     */
    // Used for light calculations
    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 15;
    }

    /**
     * On entity collision.
     *
     * @param state  the state
     * @param world  the world
     * @param pos    the pos
     * @param entity the entity
     */
    // Only gets called if block is not collidable by Block Settings
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);

//        if (entity instanceof PlayerEntity)
//            ((PlayerEntity) entity).sendMessage(new LiteralText("Entity Collision Detected!!!"), true);
    }

    /**
     * Gets collision shape.
     *
     * @param state   the state
     * @param world   the world
     * @param pos     the pos
     * @param context the context
     * @return the collision shape
     */
    // Allows to specify the collision shape of the block. Can be used to block certain entities from going through.
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        IntangibleDarkGlassBlockEntity intangibleDarkGlassBlockEntity = ((IntangibleDarkGlassBlockEntity) world.getBlockEntity(pos));

        if (intangibleDarkGlassBlockEntity == null)
            return state.getOutlineShape(world, pos);

        return intangibleDarkGlassBlockEntity.getCollisionShape(state, world, pos);
    }

    /**
     * Create block entity block entity.
     *
     * @param worldIn the world in
     * @return the block entity
     */
    @Override
    public BlockEntity createBlockEntity(BlockView worldIn) {
        return new IntangibleDarkGlassBlockEntity();
    }
}
