package me.alexisevelyn.randomtech.blocks.glass;

import me.alexisevelyn.randomtech.utility.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.sound.BlockSoundGroup;

public class IntangibleGlass extends AbstractGlassBlock {
    public IntangibleGlass() {
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
    }
}
