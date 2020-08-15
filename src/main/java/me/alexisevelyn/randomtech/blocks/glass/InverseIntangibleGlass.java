package me.alexisevelyn.randomtech.blocks.glass;

import me.alexisevelyn.randomtech.blockentities.InverseIntangibleGlassBlockEntity;
import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

/**
 * The type Inverse intangible glass.
 */
public class InverseIntangibleGlass extends AbstractGlassBlock implements BlockEntityProvider {
    /**
     * Instantiates a new Inverse intangible glass.
     */
    public InverseIntangibleGlass() {
        super(FabricBlockSettings
                .of(Materials.GLASS_MATERIAL)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque()
                .allowsSpawning(GenericBlockHelper::never)
                .solidBlock(GenericBlockHelper::never)
                .suffocates(GenericBlockHelper::never)
                .blockVision(GenericBlockHelper::never)
                .strength(0.3F, 0.3F));
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
        InverseIntangibleGlassBlockEntity inverseIntangibleGlassBlockEntity = ((InverseIntangibleGlassBlockEntity) world.getBlockEntity(pos));

        if (inverseIntangibleGlassBlockEntity == null)
            return state.getOutlineShape(world, pos);

        return inverseIntangibleGlassBlockEntity.getCollisionShape(state, world, pos);
    }

    /**
     * Create block entity block entity.
     *
     * @param worldIn the world in
     * @return the block entity
     */
    @Override
    public BlockEntity createBlockEntity(BlockView worldIn) {
        return new InverseIntangibleGlassBlockEntity();
    }
}
