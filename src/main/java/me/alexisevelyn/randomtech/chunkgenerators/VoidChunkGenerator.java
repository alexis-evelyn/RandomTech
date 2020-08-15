package me.alexisevelyn.randomtech.chunkgenerators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.alexisevelyn.randomtech.blockentities.VirtualTileBlockEntity;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.StructureBlock;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.StructureFeature;

import java.awt.*;
import java.util.Random;

/**
 * The type Void chunk generator.
 */
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

    /**
     * Instantiates a new Void chunk generator.
     *
     * @param biomeSource      the biome source
     * @param structuresConfig the structures config
     */
    public VoidChunkGenerator(BiomeSource biomeSource, StructuresConfig structuresConfig) {
        super(biomeSource, structuresConfig);
        this.debug = false;
    }

    /**
     * Instantiates a new Void chunk generator.
     *
     * @param biomeSource      the biome source
     * @param biomeSource2     the biome source 2
     * @param structuresConfig the structures config
     * @param l                the l
     */
    public VoidChunkGenerator(BiomeSource biomeSource, BiomeSource biomeSource2, StructuresConfig structuresConfig, long l) {
        super(biomeSource, biomeSource2, structuresConfig, l);
        this.debug = false;
    }

    /**
     * Instantiates a new Void chunk generator.
     *
     * @param biomeSource the biome source
     * @param debug       the debug
     */
    public VoidChunkGenerator(BiomeSource biomeSource, Boolean debug) {
        super(biomeSource, new StructuresConfig(false));
        this.debug = debug;
    }

    /**
     * Method 28506 codec.
     *
     * @return the codec
     */
    @Override
    protected Codec<? extends ChunkGenerator> method_28506() {
        return CODEC;
    }

    /**
     * With seed chunk generator.
     *
     * @param seed the seed
     * @return the chunk generator
     */
    @Override
    public ChunkGenerator withSeed(long seed) {
        return this;
    }

    /**
     * Build surface.
     *
     * @param region the region
     * @param chunk  the chunk
     */
    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
        // Setup Color For Chunk
        Color color = randomColor(region.getSeed(), chunk.getPos().getCenterBlockPos());

        VirtualTileBlockEntity virtualTileBlockEntity;
        BlockPos pos;
        for (int x = chunk.getPos().getStartX(); x <= chunk.getPos().getEndX(); x++) {
            for (int z = chunk.getPos().getStartZ(); z <= chunk.getPos().getEndZ(); z++) {
                // Setup Block Entity
                virtualTileBlockEntity = new VirtualTileBlockEntity();
                virtualTileBlockEntity.setColor(color);

                // Setup Position
                pos = new BlockPos(x, 1, z);

                // Set Block and BlockState
                chunk.setBlockState(pos, RegistryHelper.VIRTUAL_TILE_BLOCK.getDefaultState(), false);

                // Set Block Entity
                chunk.setBlockEntity(pos, virtualTileBlockEntity);
            }
        }
   }

    /**
     * Random color color.
     *
     * @param seed     the seed
     * @param blockPos the block pos
     * @return the color
     */
    public Color randomColor(long seed, BlockPos blockPos) {
        Random rand = new Random(seed + blockPos.asLong());

        return new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    }

    /**
     * Generate features.
     *
     * @param region   the region
     * @param accessor the accessor
     */
    @Override
    public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
        // Do Nothing For Now!!!
    }

    /**
     * Populate entities.
     *
     * @param region the region
     */
    @Override
    public void populateEntities(ChunkRegion region) {
        if (region.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            // Center Chunk of Region
            int chunkX = region.getCenterChunkX();
            int chunkZ = region.getCenterChunkZ();

            // Biome
            Biome biome = region.getBiome((new ChunkPos(chunkX, chunkZ)).getCenterBlockPos());

            // Random Chunk
            ChunkRandom chunkRandom = new ChunkRandom();
            chunkRandom.setPopulationSeed(region.getSeed(), chunkX << 4, chunkZ << 4);

            // Populate Chunk
            SpawnHelper.populateEntities(region, biome, chunkX, chunkZ, chunkRandom);

            // TODO: Mess around with spawning!!!
        }
    }

    /**
     * Carve.
     *
     * @param seed   the seed
     * @param access the access
     * @param chunk  the chunk
     * @param carver the carver
     */
    @Override
    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
        super.carve(seed, access, chunk, carver);
    }

    /**
     * Generates the base shape of the chunk out of the basic block states as decided by this chunk generator's config.
     *
     * @param world Server World
     * @param accessor
     * @param chunk
     */
    @Override
    public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
        // Nothing For Now!!!
    }

    /**
     * Gets height.
     *
     * @param x             the x
     * @param z             the z
     * @param heightmapType the heightmap type
     * @return the height
     */
    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        return 1;
    }

    /**
     * Gets column sample.
     *
     * @param x the x
     * @param z the z
     * @return the column sample
     */
    @Override
    public BlockView getColumnSample(int x, int z) {
        return new VerticalBlockSample(new BlockState[0]);
    }
}
