package com.structbuilders.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.CobwebBlock;
import net.minecraft.block.Material;

public class ModBlocks {
    public static final Block TOXIC_COBWEB = new ToxicCobwebBlock(FabricBlockSettings.of(Material.COBWEB).noCollision().nonOpaque().strength(4.0f, 0).build());
}
