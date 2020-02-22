package com.structbuilders.mixin;

import com.structbuilders.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CobwebBlock;
import net.minecraft.block.WitherRoseBlock;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.container.BeaconContainer;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpiderEntity.class)
public class SpiderSlownessHack {
    @Inject(at = @At("HEAD"), method = "slowMovement", cancellable = true)
    private void slowMovement(BlockState state, Vec3d multiplier, CallbackInfo info) {
        if (state.getBlock() == ModBlocks.TOXIC_COBWEB) info.cancel();
    }
}
