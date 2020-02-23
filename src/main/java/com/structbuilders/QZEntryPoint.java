package com.structbuilders;

import com.structbuilders.biome.ModBiomes;
import com.structbuilders.block.ModBlocks;
import com.structbuilders.dim.Dimensions;
import com.structbuilders.ench.ModEnchantments;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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

    // each plane also has ambient passive mobs and (at night) hostile mobs:
    // air: passive cave spider, scorpion (crawl) hostile (drops enchanted spider eye: eat to get immunity to poison and regen)
    // Scorpion:
    // melee attack, spawns toxic webs wherever it walks
    // can move through webs like a spider
    // toxic webs inflict poison
    // fire: passive blaze, demon lord (crawl) hostile
    // water: passive guardian, mind squid (crawl) hostile
    // earth: passive skeleton, necromancer (crawl) hostile


    @Override
    public void onInitialize() {
        try {
            patchBeaconLevels();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Dimensions.register();
        ModBiomes.register();
        ModEnchantments.register();
        registerBlocks();
    }

    // Registers blocks and block items
    private void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "toxic_cobweb"), ModBlocks.TOXIC_COBWEB);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "toxic_cobweb"), new BlockItem(ModBlocks.TOXIC_COBWEB, new Item.Settings().group(ItemGroup.DECORATIONS)));
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TOXIC_COBWEB, RenderLayer.getCutout());
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
