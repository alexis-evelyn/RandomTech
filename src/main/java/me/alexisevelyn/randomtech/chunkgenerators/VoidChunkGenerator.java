package me.alexisevelyn.randomtech.chunkgenerators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.alexisevelyn.randomtech.blockentities.VirtualTileBlockEntity;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;

import java.awt.*;
import java.util.Random;

public class VoidChunkGenerator extends ChunkGenerator {
    public final boolean debug;

    public static final Codec<VoidChunkGenerator> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    BiomeSource.field_24713.fieldOf("biome_source")
                            .forGetter((generator) -> generator.biomeSource),
                    Codec.BOOL.fieldOf("debug")
                            .forGetter((generator) -> generator.debug)
            ).apply(instance, instance.stable(VoidChunkGenerator::new))
    );

    public VoidChunkGenerator(BiomeSource biomeSource, StructuresConfig structuresConfig) {
        super(biomeSource, structuresConfig);
        this.debug = false;
    }

    public VoidChunkGenerator(BiomeSource biomeSource, BiomeSource biomeSource2, StructuresConfig structuresConfig, long l) {
        super(biomeSource, biomeSource2, structuresConfig, l);
        this.debug = false;
    }

    public VoidChunkGenerator(BiomeSource biomeSource, Boolean debug) {
        super(biomeSource, new StructuresConfig(false));
        this.debug = debug;
    }

    @Override
    protected Codec<? extends ChunkGenerator> method_28506() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return this;
    }

    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
        // Nothing For Now!!!

        // Setup Block Entity
        VirtualTileBlockEntity virtualTileBlockEntity = new VirtualTileBlockEntity();
        virtualTileBlockEntity.setColor(randomColor(region.getSeed(), chunk.getPos().getCenterBlockPos()));

        // Set Block and BlockState
        chunk.setBlockState(chunk.getPos().getCenterBlockPos(), RegistryHelper.VIRTUAL_TILE_BLOCK.getDefaultState(), false);

        // Set Block Entity
        chunk.setBlockEntity(chunk.getPos().getCenterBlockPos(), virtualTileBlockEntity);
    }

    public Color randomColor(long seed, BlockPos blockPos) {
        Random rand = new Random(seed + blockPos.asLong());

        return new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    }

    @Override
    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
        super.carve(seed, access, chunk, carver);
    }

    /**
     * Generates the base shape of the chunk out of the basic block states as decided by this chunk generator's config.
     *
     * @param world
     * @param accessor
     * @param chunk
     */
    @Override
    public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
        // Nothing For Now!!!
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        return 1;
    }

    @Override
    public BlockView getColumnSample(int x, int z) {
        return new VerticalBlockSample(new BlockState[0]);
    }
}
