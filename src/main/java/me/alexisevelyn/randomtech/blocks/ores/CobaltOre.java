package me.alexisevelyn.randomtech.blocks.ores;

import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.api.utilities.MiningLevel;
import me.alexisevelyn.randomtech.utility.MaterialsHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

/**
 * The type Cobalt ore.
 */
public class CobaltOre extends Block {
    private static final String cobalt = "ore_cobalt";
    private static ConfiguredFeature<?, ?> configuredFeature;

    /**
     * Instantiates a new Cobalt ore.
     */
    public CobaltOre() {
        super(FabricBlockSettings
                .of(MaterialsHelper.METAL_ORE_MATERIAL)
                .sounds(BlockSoundGroup.STONE)
                .breakByTool(FabricToolTags.PICKAXES, MiningLevel.POWERED.getValue())
                .requiresTool()
                .allowsSpawning(GenericBlockHelper::always)
                .solidBlock(GenericBlockHelper::always)
                .suffocates(GenericBlockHelper::always)
                .blockVision(GenericBlockHelper::always)
                .strength(10.0F, 11.0F));
    }

    /**
     * Add ore feature.
     */
    public static void addOreFeature(Biome biome) {
        // Don't Modify End or Nether Generation
        if (biome.getCategory().equals(Biome.Category.NETHER) || biome.getCategory().equals(Biome.Category.THEEND))
            return;

        // Only set it once, otherwise bad things happen (e.g. all overworld ores being wiped from generation)
        if (configuredFeature == null)
            configuredFeature = Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, RegistryHelper.COBALT_ORE.getDefaultState(), 8))
                .method_30377(16).spreadHorizontally().repeat(8);

        if (!BuiltinRegistries.CONFIGURED_FEATURE.getKey(configuredFeature).isPresent() && !BuiltinRegistries.CONFIGURED_FEATURE.getOrEmpty(new Identifier(cobalt)).isPresent())
            BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_FEATURE, cobalt, configuredFeature);

        RegistryHelper.insertIntoBiome(configuredFeature, GenerationStep.Feature.UNDERGROUND_ORES, biome);
    }
}

