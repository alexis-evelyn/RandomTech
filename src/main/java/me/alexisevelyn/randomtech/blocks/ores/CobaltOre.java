package me.alexisevelyn.randomtech.blocks.ores;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.api.utilities.MiningLevel;
import me.alexisevelyn.randomtech.utility.Materials;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * The type Cobalt ore.
 */
public class CobaltOre extends Block {
    /**
     * Instantiates a new Cobalt ore.
     */
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

    /**
     * Add ore feature.
     */
    public static void addOreFeature(Biome biome) {
        String cobalt = "ore_cobalt";
        ConfiguredFeature<?, ?> configuredFeature = Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, RegistryHelper.COBALT_ORE.getDefaultState(), 8))
                .method_30377(16).spreadHorizontally().repeat(8);

        if (!BuiltinRegistries.CONFIGURED_FEATURE.getKey(configuredFeature).isPresent() && !BuiltinRegistries.CONFIGURED_FEATURE.getOrEmpty(new Identifier(cobalt)).isPresent())
            BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_FEATURE, cobalt, configuredFeature);

        RegistryHelper.insertIntoBiome(configuredFeature, GenerationStep.Feature.UNDERGROUND_ORES, biome);
    }
}

