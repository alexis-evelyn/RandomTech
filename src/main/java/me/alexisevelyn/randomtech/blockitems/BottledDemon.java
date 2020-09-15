package me.alexisevelyn.randomtech.blockitems;

import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.MaterialsHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.function.ToIntFunction;

/**
 * The type Bottled demon.
 */
public class BottledDemon extends BlockItem {
    /**
     * Instantiates a new Bottled demon.
     *
     * @param block    the block
     * @param settings the settings
     */
    public BottledDemon(Block block, Settings settings) {
        super(block, settings);
    }

    /**
     * Append tooltip.
     *
     * @param stack   the stack
     * @param worldIn the world in
     * @param tooltip the tooltip
     * @param flagIn  the flag in
     */
    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        // Intentionally Left Empty For Now
    }

    /**
     * Color Item Form
     *
     * @param itemStack
     * @param tintIndex
     * @return
     */
    public static int getColor(ItemStack itemStack, int tintIndex) {
        return Color.WHITE.getRGB();
    }

    /**
     * The type Bottled demon block.
     */
    public static class BottledDemonBlock extends Block {
        // Base Part of Bottle
        private static final VoxelShape BASE_SHAPE = Block.createCuboidShape(3, 0, 3, 13, 11, 13);

        // Neck Part of Bottle
        private static final VoxelShape NECK_SHAPE = Block.createCuboidShape(6, 11, 6, 10, 16, 10);

        // Cork Part of Bottle
        private static final VoxelShape CORK_SHAPE = Block.createCuboidShape(6.5, 14, 6.5, 9.5, 17, 9.5);

        // Full Shape
        private static final VoxelShape BOTTLE_SHAPE = VoxelShapes.union(BASE_SHAPE, NECK_SHAPE, CORK_SHAPE);

        /**
         * Instantiates a new Bottled demon block.
         */
        public BottledDemonBlock() {
            super(FabricBlockSettings
                    .of(MaterialsHelper.GLASS_MATERIAL)
                    .sounds(BlockSoundGroup.GLASS)
//                    .breakByTool(FabricToolTags.PICKAXES, ToolMaterials.WOOD.getMiningLevel())
//                    .requiresTool()
                    .allowsSpawning(GenericBlockHelper::never)
                    .solidBlock(GenericBlockHelper::never)
                    .suffocates(GenericBlockHelper::never)
                    .blockVision(GenericBlockHelper::never)
                    .strength(0.3F, 0.3F)
                    .nonOpaque()
                    .lightLevel(getLightLevel()));
        }

        /**
         * Gets light level.
         *
         * @return the light level
         */
        public static ToIntFunction<BlockState> getLightLevel() {
            return (state) -> 0; // 7?
        }

        /**
         * To remove the shadow
         *
         * @param state
         * @param world
         * @param pos
         * @return
         */
        @Environment(EnvType.CLIENT)
        @Override
        public float getAmbientOcclusionLightLevel(final BlockState state, final BlockView world, final BlockPos pos) {
            return 1.0f;
        }

        /**
         * Color Block Form
         *
         * @param state
         * @param world
         * @param pos
         * @param tintIndex
         * @return
         */
        public static int getColor(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
            return Color.WHITE.getRGB();
        }

        @Override
        public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
            return BOTTLE_SHAPE;
        }

        @Override
        public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
            return this.getOutlineShape(state, world, pos, ShapeContext.absent());
        }

        @Override
        public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
            return this.getOutlineShape(state, world, pos, context);
        }
    }
}