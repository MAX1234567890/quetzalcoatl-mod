package com.structbuilders.biome;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class FirePlaneBiome extends Biome {
    public FirePlaneBiome() {
        super((new Settings()).configureSurfaceBuilder(SurfaceBuilder.BADLANDS, SurfaceBuilder.NETHER_CONFIG).precipitation(Precipitation.NONE).category(Category.NETHER).depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).waterColor(4159204).waterFogColor(329011).parent((String)null));
        this.addStructureFeature(Feature.NETHER_BRIDGE.configure(FeatureConfig.DEFAULT));
        this.addCarver(GenerationStep.Carver.AIR, configureCarver(net.minecraft.world.gen.carver.Carver.HELL_CAVE, new ProbabilityConfig(0.2F)));
        this.addFeature(net.minecraft.world.gen.GenerationStep.Feature.VEGETAL_DECORATION, Feature.SPRING_FEATURE.configure(DefaultBiomeFeatures.LAVA_SPRING_CONFIG).createDecoratedFeature(Decorator.COUNT_VERY_BIASED_RANGE.configure(new RangeDecoratorConfig(20, 8, 16, 256))));
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        this.addFeature(net.minecraft.world.gen.GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.NETHER_BRIDGE.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.NOPE.configure(DecoratorConfig.DEFAULT)));
        this.addFeature(net.minecraft.world.gen.GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.configure(DefaultBiomeFeatures.NETHER_SPRING_CONFIG).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(8, 4, 8, 128))));
        this.addFeature(net.minecraft.world.gen.GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.NETHER_FIRE_CONFIG).createDecoratedFeature(Decorator.HELL_FIRE.configure(new CountDecoratorConfig(10))));
        this.addFeature(net.minecraft.world.gen.GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.configure(DefaultBiomeFeatures.ENCLOSED_NETHER_SPRING_CONFIG).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(16, 10, 20, 128))));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.BLAZE, 30, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ENDERMAN, 1, 4, 4));
    }
}
