package me.alexisevelyn.randomtech.blocks;

import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

import java.awt.*;
import java.util.function.ToIntFunction;

// This block was inspired by an accidental texture produced by my clear glass not rendering properly. :P

public class VirtualTile extends BlockItem {
    public VirtualTile(Block block, Settings settings) {
        super(block, settings);
    }

    // For Block Form
    public static int getEdgeColor(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
        return new Color(57, 148, 25).getRGB(); // #399419 For Cool Green
    }

    // For Item Form
    public static int getEdgeColor(ItemStack itemStack, int tintIndex) {
        return new Color(57, 148, 25).getRGB(); // #399419 For Cool Green
    }

    public static class VirtualTileBlock extends Block {
        public VirtualTileBlock() {
            super(FabricBlockSettings
                    .of(Materials.TILE_MATERIAL)
                    .sounds(BlockSoundGroup.STONE)
                    .breakByTool(FabricToolTags.PICKAXES, ToolMaterials.WOOD.getMiningLevel())
                    .requiresTool()
                    .allowsSpawning(GenericBlockHelper::never)
                    .solidBlock(GenericBlockHelper::always)
                    .suffocates(GenericBlockHelper::always)
                    .blockVision(GenericBlockHelper::always)
                    .strength(1.8F, 1.8F)
                    .lightLevel(getLightLevel()));
        }

        public static ToIntFunction<BlockState> getLightLevel() {
            return (state) -> 10; // 7?
        }
    }
}
