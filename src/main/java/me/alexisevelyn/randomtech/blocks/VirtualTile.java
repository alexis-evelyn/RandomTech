package me.alexisevelyn.randomtech.blocks;

import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.ToIntFunction;

// This block was inspired by an accidental texture produced by my clear glass not rendering properly. :P

public class VirtualTile extends Block {
    public VirtualTile() {
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
