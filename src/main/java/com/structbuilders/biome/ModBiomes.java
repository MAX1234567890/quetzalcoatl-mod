package com.structbuilders.biome;

import com.structbuilders.QZEntryPoint;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class ModBiomes {
    public static Biome FIRE_PLANE = new FirePlaneBiome();
    public static Biome EARTH_PLANE = new EarthPlaneBiome();

    public static void register() {
        Registry.register(Registry.BIOME, new Identifier(QZEntryPoint.MOD_ID, "fire_planes"), FIRE_PLANE);
        Registry.register(Registry.BIOME, new Identifier(QZEntryPoint.MOD_ID, "earth_planes"), EARTH_PLANE);
    }
}
