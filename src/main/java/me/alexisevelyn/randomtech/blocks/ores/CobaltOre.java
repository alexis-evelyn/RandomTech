package me.alexisevelyn.randomtech.blocks.ores;

import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.api.utilities.MiningLevel;
import me.alexisevelyn.randomtech.utility.Materials;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

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

    public static void addOreFeature(Biome biome) {
        // Don't Add Ore to Nether or End
        if (biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
            biome.addFeature(
                    GenerationStep.Feature.UNDERGROUND_ORES,
                    Feature.ORE.configure(
                            new OreFeatureConfig(
                                    OreFeatureConfig.Target.NATURAL_STONE,
                                    RegistryHelper.COBALT_ORE.getDefaultState(),
                                    8 // Ore vein size
                            )).createDecoratedFeature(
                            Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(
                                    8, // Number of veins per chunk
                                    0, // Bottom Offset
                                    13, // Min y level
                                    32 // Max y level
                            ))));
        }
    }
}

