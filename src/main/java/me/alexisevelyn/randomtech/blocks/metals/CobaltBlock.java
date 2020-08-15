package me.alexisevelyn.randomtech.blocks.metals;

import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.api.utilities.MiningLevel;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

/**
 * The type Cobalt block.
 */
public class CobaltBlock extends Block {
    /**
     * Instantiates a new Cobalt block.
     */
    public CobaltBlock() {
        super(FabricBlockSettings
                .of(Materials.METAL_ORE_MATERIAL)
                .sounds(BlockSoundGroup.METAL)
                .breakByTool(FabricToolTags.PICKAXES, MiningLevel.POWERED.getValue())
                .requiresTool()
                .allowsSpawning(GenericBlockHelper::always)
                .solidBlock(GenericBlockHelper::always)
                .suffocates(GenericBlockHelper::always)
                .blockVision(GenericBlockHelper::always)
                .strength(3.0F, 3.0F));
    }
}