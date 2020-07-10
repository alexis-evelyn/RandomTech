package me.alexisevelyn.randomtech.blocks;

import me.alexisevelyn.randomtech.utility.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

// AbstractGlassBlock and ConnectingBlock
public class ClearGlass extends AbstractGlassBlock {
    public static DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty CONNECTED_TEXTURE = IntProperty.of("connected", 0, 46);

    public ClearGlass() {
        super(FabricBlockSettings
                .of(Materials.GLASS_MATERIAL)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque() // .noCollision() - Allows for walking through blocks
                .allowsSpawning(GenericBlockHelper::never)
                .solidBlock(GenericBlockHelper::never)
                .suffocates(GenericBlockHelper::never)
                .blockVision(GenericBlockHelper::never)
                .strength(0.3F, 0.3F));

        setDefaultState(getStateManager()
                .getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(CONNECTED_TEXTURE, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, CONNECTED_TEXTURE);
    }
}
