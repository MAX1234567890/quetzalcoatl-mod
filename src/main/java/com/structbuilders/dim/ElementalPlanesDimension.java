package com.structbuilders.dim;

import com.google.common.collect.Sets;
import com.structbuilders.biome.ModBiomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.CheckerboardBiomeSource;
import net.minecraft.world.biome.source.CheckerboardBiomeSourceConfig;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.FloatingIslandsChunkGeneratorConfig;
import net.minecraft.world.level.LevelProperties;

import javax.annotation.Nullable;
import javax.security.auth.login.Configuration;

public class ElementalPlanesDimension extends Dimension {
    public ElementalPlanesDimension(World world, DimensionType type) {
        super(world, type, 0.1f);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        CheckerboardBiomeSourceConfig conf = new CheckerboardBiomeSourceConfig(world.getLevelProperties());
        conf.setSize(12);
        conf.setBiomes(new Biome[]{
           ModBiomes.FIRE_PLANE,
           ModBiomes.EARTH_PLANE,
//           ModBiomes.WATER_PLANE,
//           ModBiomes.AIR_PLANE,
        });
        BiomeSource source = new CheckerboardBiomeSource(conf);
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
