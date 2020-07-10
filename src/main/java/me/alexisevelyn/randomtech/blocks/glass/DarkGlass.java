package me.alexisevelyn.randomtech.blocks.glass;

import me.alexisevelyn.randomtech.utility.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class DarkGlass extends AbstractGlassBlock {
//    public static DirectionProperty FACING = Properties.HORIZONTAL_FACING;
//    public static final IntProperty CONNECTED_TEXTURE = IntProperty.of("connected", 0, 46);

    public DarkGlass() {
        super(FabricBlockSettings
                .of(Materials.GLASS_MATERIAL)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque()
                .allowsSpawning(GenericBlockHelper::never)
                .solidBlock(GenericBlockHelper::never)
                .suffocates(GenericBlockHelper::never)
                .blockVision(GenericBlockHelper::never)
                .strength(0.3F, 0.3F));

//        setDefaultState(getStateManager()
//                .getDefaultState()
//                .with(FACING, Direction.NORTH)
//                .with(CONNECTED_TEXTURE, 0));
    }

//    @Override
//    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//        builder.add(FACING, CONNECTED_TEXTURE);
//    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
}
