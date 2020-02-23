package com.structbuilders.biome;

import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class EarthPlaneBiome extends Biome {
    public EarthPlaneBiome() {
        super((new Settings()).configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.DIRT_CONFIG).precipitation(Precipitation.RAIN).category(Category.FOREST).depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).waterColor(4159204).waterFogColor(329011).parent((String)null));
        this.addStructureFeature(Feature.NETHER_BRIDGE.configure(FeatureConfig.DEFAULT));
        this.addCarver(GenerationStep.Carver.AIR, configureCarver(Carver.CANYON, new ProbabilityConfig(0.2F)));
        DefaultBiomeFeatures.addJungleTrees(this);
        DefaultBiomeFeatures.addSavannaTrees(this);
        DefaultBiomeFeatures.addBirchTrees(this);
        DefaultBiomeFeatures.addTaigaTrees(this);
        DefaultBiomeFeatures.addForestTrees(this);
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SKELETON, 30, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ENDERMAN, 1, 4, 4));
    }
}
