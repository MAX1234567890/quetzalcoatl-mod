    package com.structbuilders.dim;

import com.structbuilders.QZEntryPoint;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensionType;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;

public class Dimensions {
    public static final FabricDimensionType ELEMENTAL_PLANES = FabricDimensionType.builder()
            .defaultPlacer((oldEntity, destinationWorld, portalDir, horizontalOffset, verticalOffset) -> new BlockPattern.TeleportTarget(new Vec3d(destinationWorld.getTopPosition(Heightmap.Type.WORLD_SURFACE, BlockPos.ORIGIN)), oldEntity.getVelocity(), (int) oldEntity.yaw))
            .factory(ElementalPlanesDimension::new)
            .skyLight(false)
            .buildAndRegister(new Identifier(QZEntryPoint.MOD_ID, "elemental_planes"));

    public static void register() {
        // load the class
    }
}
