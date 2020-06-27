package me.alexisevelyn.randomtech.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class FirstBlock extends Block {

    // Break By Hand Seems to Do Nothing?
    public FirstBlock() {
        super(FabricBlockSettings
                .of(Material.STONE)
                .breakByHand(false)
                .breakByTool(FabricToolTags.PICKAXES)
                .sounds(BlockSoundGroup.NETHERITE)
                .strength(2.0F, 0.2F));
    }

    // Left here as reference code
    // Suggested Constructor
//    public FirstBlock(Settings settings) {
//        super(settings);
//    }
}
