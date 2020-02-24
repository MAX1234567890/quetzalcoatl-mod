package com.structbuilders.mixin;

import com.structbuilders.block.ModBlocks;
import com.structbuilders.dim.Dimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.MobEntityWithAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Mixin(LivingEntity.class)
public class MakeAcidGreatAgainMixin {
    @Inject(at = @At("HEAD"), method = "baseTick")
    private void tick(CallbackInfo info) {
        LivingEntity self = (LivingEntity) (Object) this;
        // TODO: add acid pathfinding penalty

        if (
                self.world.getBlockState(self.getBlockPos()).getFluidState().getFluid() == ModBlocks.STILL_ACID
                        || self.world.getBlockState(self.getBlockPos()).getFluidState().getFluid() == ModBlocks.FLOWING_ACID
        ) {
            self.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, self.world.getDifficulty().getId()));
            if (!self.isUndead())
                self.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, self.world.getDifficulty().getId()));
            else
                self.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40, self.world.getDifficulty().getId()));
        }
    }
}
