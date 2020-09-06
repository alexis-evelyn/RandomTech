package me.alexisevelyn.randomtech.blocks.metals;

import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.api.utilities.MiningLevel;
import me.alexisevelyn.randomtech.utility.MaterialsHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

/**
 * The type Cobalt block.
 */
public class PureRedstoneBlock extends Block {
    /**
     * Instantiates a new Cobalt block.
     */
    public PureRedstoneBlock() {
        super(FabricBlockSettings
                .of(MaterialsHelper.METAL_ORE_MATERIAL)
                .sounds(BlockSoundGroup.METAL)
                .breakByTool(FabricToolTags.PICKAXES, MiningLevel.IRON.getValue())
                .requiresTool()
                .allowsSpawning(GenericBlockHelper::always)
                .solidBlock(GenericBlockHelper::always)
                .suffocates(GenericBlockHelper::always)
                .blockVision(GenericBlockHelper::always)
                .strength(3.0F, 3.0F));
    }
}