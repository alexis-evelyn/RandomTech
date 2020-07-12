package me.alexisevelyn.randomtech.blocks.glass;

import me.alexisevelyn.randomtech.utility.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

// TODO: Add inverted intangible glass which only blocks the player
// TODO: Fix rendering issue on edges of glass - This shows up on every glass block from my mod, but not vanilla (artifacts?)
// TODO: Allow connecting different types of glass together
public class DarkGlass extends AbstractGlassBlock {
    public DarkGlass() {
        super(FabricBlockSettings
                .of(Materials.DARK_GLASS_MATERIAL)
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
