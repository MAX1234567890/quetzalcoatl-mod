package com.structbuilders.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.CobwebBlock;
import net.minecraft.block.Material;
import net.minecraft.fluid.BaseFluid;
import net.minecraft.item.Item;

public class ModBlocks {
    public static BaseFluid STILL_ACID;
    public static BaseFluid FLOWING_ACID;
    public static Block ACID;

    public static Item ACID_BUCKET;
    public static final Block TOXIC_COBWEB = new ToxicCobwebBlock(FabricBlockSettings.of(Material.COBWEB).noCollision().nonOpaque().strength(4.0f, 0).build());
}
