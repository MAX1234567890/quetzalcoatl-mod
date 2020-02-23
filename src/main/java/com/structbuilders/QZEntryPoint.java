package com.structbuilders;

import com.structbuilders.biome.ModBiomes;
import com.structbuilders.block.ModBlocks;
import com.structbuilders.dim.Dimensions;
import com.structbuilders.ench.ModEnchantments;
import com.structbuilders.fluid.NecromancerAcidFluid;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.*;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class QZEntryPoint implements ClientModInitializer, ModInitializer {
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
        System.out.println("Setting up fluids");

        ModBlocks.STILL_ACID = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "acid"), new NecromancerAcidFluid.Still());
        System.out.println("Set up still!");

        ModBlocks.FLOWING_ACID = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "flowing_acid"), new NecromancerAcidFluid.Flowing());
        System.out.println("Set up flowing!");

        ModBlocks.ACID_BUCKET = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "acid_bucket"), new BucketItem(ModBlocks.STILL_ACID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        System.out.println("Set up bucket!");

        ModBlocks.ACID = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "acid"), new FluidBlock(ModBlocks.STILL_ACID, FabricBlockSettings.copy(Blocks.WATER).build()) {
        });
        System.out.println("Set up block");
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

    @Override
    public void onInitializeClient() {
        System.out.println("Init client! (CTRL-F for 'alfalfa')");
        setupFluidRendering(ModBlocks.STILL_ACID, ModBlocks.FLOWING_ACID, new Identifier("minecraft", "water"), 0x49e372);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModBlocks.STILL_ACID, ModBlocks.FLOWING_ACID);
    }

    public static void setupFluidRendering(final Fluid still, final Fluid flowing, final Identifier textureFluidId, final int color) {
        final Identifier stillSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_still");
        final Identifier flowingSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_flow");

        // If they're not already present, add the sprites to the block atlas
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX).register((atlasTexture, registry) ->
        {
            registry.register(stillSpriteId);
            registry.register(flowingSpriteId);
        });

        final Identifier fluidId = Registry.FLUID.getId(still);
        final Identifier listenerId = new Identifier(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");

        final Sprite[] fluidSprites = {null, null};

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return listenerId;
            }

            /**
             * Get the sprites from the block atlas when resources are reloaded
             */
            @Override
            public void apply(ResourceManager resourceManager) {
                final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
                fluidSprites[0] = atlas.apply(stillSpriteId);
                fluidSprites[1] = atlas.apply(flowingSpriteId);
            }
        });

        // The FluidRenderer gets the sprites and color from a FluidRenderHandler during rendering
        final FluidRenderHandler renderHandler = new FluidRenderHandler() {
            @Override
            public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) {
                return fluidSprites;
            }

            @Override
            public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
                return color;
            }
        };

        FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler);
        FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler);
    }
}
