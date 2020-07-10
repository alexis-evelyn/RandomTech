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
// TODO: Stop light getting through
// TODO: Fix rendering issue on edges of glass - This shows up on every glass block from my mod, but not vanilla (artifacts?)
public class DarkIntangibleGlass extends AbstractGlassBlock {
//    public static DirectionProperty FACING = Properties.HORIZONTAL_FACING;
//    public static final IntProperty CONNECTED_TEXTURE = IntProperty.of("connected", 0, 46);

    public DarkIntangibleGlass() {
        super(FabricBlockSettings
                .of(Materials.GLASS_MATERIAL)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque()
                .noCollision()
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
