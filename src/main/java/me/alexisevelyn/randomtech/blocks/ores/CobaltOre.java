package me.alexisevelyn.randomtech.blocks.ores;

import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.api.utilities.MiningLevel;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

// TODO: Fix this so that mining level is respected
public class CobaltOre extends Block {
    public CobaltOre() {
        super(FabricBlockSettings
                .of(Materials.METAL_ORE_MATERIAL)
                .sounds(BlockSoundGroup.STONE)
                .breakByTool(FabricToolTags.PICKAXES, MiningLevel.POWERED.getValue())
                .requiresTool()
                .allowsSpawning(GenericBlockHelper::always)
                .solidBlock(GenericBlockHelper::always)
                .suffocates(GenericBlockHelper::always)
                .blockVision(GenericBlockHelper::always)
                .strength(10.0F, 11.0F));
    }
}

