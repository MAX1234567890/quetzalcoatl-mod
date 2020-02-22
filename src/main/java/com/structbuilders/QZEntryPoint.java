package com.structbuilders;

import com.structbuilders.dim.Dimensions;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class QZEntryPoint implements ModInitializer {
    public static final String MOD_ID = "quetzalcoatl";
    // IDEA FOR MOD:
    // New dimension: elemental planes:
    // four quadrants, earth fire air water
    // each quadrant can have altars
    // players can make offerings at an altar to gain benefits from that track (more potent the higher the level of sacrifice [sacrifice depends on track], can only have one track)
    // air => invisibility
    // water => dolphin's grace
    // fire => fire resistance
    // earth => haste


    @Override
    public void onInitialize() {
        try {
            patchBeaconLevels();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Dimensions.register();
    }

    private void patchBeaconLevels() throws NoSuchFieldException, IllegalAccessException {
        Field effects_by_level = BeaconBlockEntity.class.getField("EFFECTS_BY_LEVEL");
        Field effects = BeaconBlockEntity.class.getDeclaredField("EFFECTS");
		effects.setAccessible(true);
        Field flags = Field.class.getDeclaredField("modifiers");
        flags.setAccessible(true);
        flags.set(effects_by_level, effects_by_level.getModifiers() & ~Modifier.FINAL);
        flags.set(effects, effects_by_level.getModifiers() & ~Modifier.FINAL);

		effects_by_level.set(null, new StatusEffect[][]{{StatusEffects.SPEED, StatusEffects.NIGHT_VISION}, {StatusEffects.RESISTANCE, StatusEffects.JUMP_BOOST}, {StatusEffects.STRENGTH}, {StatusEffects.REGENERATION}});
		effects.set(null, (Set) Arrays.stream(BeaconBlockEntity.EFFECTS_BY_LEVEL).flatMap(Arrays::stream).collect(Collectors.toSet()));
        System.out.println("Successfully patched beacon effects!");
    }
}
