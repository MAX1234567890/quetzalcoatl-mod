package com.structbuilders.ent;

import com.structbuilders.QZEntryPoint;
import com.structbuilders.fluid.NecromancerAcidFluid;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {
    public static final EntityType<NecromancerEntity> NECROMANCER =
            Registry.register(
                    Registry.ENTITY_TYPE,
                    new Identifier(QZEntryPoint.MOD_ID, "necromancer"),
                    FabricEntityTypeBuilder.create(EntityCategory.MONSTER, NecromancerEntity::new).size(EntityDimensions.fixed(1, 2)).build()
            );

    public static void register() {
        EntityRendererRegistry.INSTANCE.register(ModEntities.NECROMANCER, (entityRenderDispatcher, context) -> new NecromancerRenderer(entityRenderDispatcher));
        Registry.register(Registry.ITEM, new Identifier(QZEntryPoint.MOD_ID, "necromancer_spawn_egg"), new SpawnEggItem(ModEntities.NECROMANCER, 0x36393d, 0xb1c959, new Item.Settings().group(ItemGroup.MISC)));
    }
}
