package me.alexisevelyn.randomtech.blocks.glass;

import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.MaterialsHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

/**
 * The type Dark glass.
 */
public class DarkGlass extends AbstractGlassBlock {
    /**
     * Instantiates a new Dark glass.
     */
    public DarkGlass() {
        super(FabricBlockSettings
                .of(MaterialsHelper.DARK_GLASS_MATERIAL)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque()
                //.lightLevel(0)
                .allowsSpawning(GenericBlockHelper::never)
                .solidBlock(GenericBlockHelper::never)
                .suffocates(GenericBlockHelper::never)
                .blockVision(GenericBlockHelper::never)
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
    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 15;
    }

//    @Override
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        if (!world.isClient) {
//            player.sendMessage(new LiteralText("AO: " + state.getAmbientOcclusionLightLevel(world, pos)), false);
//        }
//
//        return ActionResult.SUCCESS;
//    }
}
