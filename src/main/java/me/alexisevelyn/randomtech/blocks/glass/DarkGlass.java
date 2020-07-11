package me.alexisevelyn.randomtech.blocks.glass;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import me.alexisevelyn.randomtech.utility.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.State;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

// TODO: Look at net.minecraft.client.render.block.BlockRenderManager and net.minecraft.client.render.block.BlockModelRenderer
// TODO: Look for how to render the block translucently without causing the server to allow light through
public class DarkGlass extends AbstractGlassBlock {
    // net.minecraft.world.chunk.light.ChunkLightProvider#getStateForLighting(long, MutableInt) - boolean bl = blockState.isOpaque() && blockState.hasSidedTransparency();
    // The above line is what causes light to pass through when the block is not opaque
    // There's also blockState.getOpacity() for future reference

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
