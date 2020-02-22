package com.structbuilders.dim;

import com.google.common.collect.Sets;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.FloatingIslandsChunkGeneratorConfig;

import javax.annotation.Nullable;

public class ElementalPlanesDimension extends Dimension {
    public ElementalPlanesDimension(World world, DimensionType type) {
        super(world, type, 0.1f);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        BiomeSource source = new BiomeSource(Sets.newHashSet(Biomes.MUSHROOM_FIELDS, Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.BADLANDS)) {
            @Override
            public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
                if (biomeX >= 0 && biomeY >= 0) {
                    // EARTH
                    return Biomes.MUSHROOM_FIELDS;
                } else if (biomeX < 0 && biomeY >= 0) {
                    // WATER
                    return Biomes.FOREST;
                } else if (biomeX < 0 && biomeY < 0) {
                    // AIR
                    return Biomes.BIRCH_FOREST;
                } else if (biomeX >= 0 && biomeY < 0) {
                    // FIRE
                    return Biomes.BADLANDS;
                } else {
                    return null;
                }
            }
        };
        FloatingIslandsChunkGeneratorConfig config = new FloatingIslandsChunkGeneratorConfig();
        return ChunkGeneratorType.FLOATING_ISLANDS.create(world, source, config);
    }

    @Nullable
    @Override
    public BlockPos getSpawningBlockInChunk(ChunkPos chunkPos, boolean checkMobSpawnValidity) {
        return null;
    }

    @Nullable
    @Override
    public BlockPos getTopSpawningBlockPosition(int x, int z, boolean checkMobSpawnValidity) {
        return null;
    }

    @Override
    public float getSkyAngle(long timeOfDay, float tickDelta) {
        final int dayLength = 24000;
        double daysPassed = ((double) timeOfDay + tickDelta) / dayLength;
        return (float) MathHelper.fractionalPart(daysPassed - 0.25);
    }

    @Override
    public boolean hasVisibleSky() {
        return true;
    }

    @Override
    public Vec3d getFogColor(float skyAngle, float tickDelta) {
        return new Vec3d(0.1, 0.1, 0.6);
    }

    @Override
    public boolean canPlayersSleep() {
        return true;
    }

    @Override
    public boolean isFogThick(int x, int z) {
        return true;
    }

    @Override
    public DimensionType getType() {
        return Dimensions.ELEMENTAL_PLANES;
    }
}
