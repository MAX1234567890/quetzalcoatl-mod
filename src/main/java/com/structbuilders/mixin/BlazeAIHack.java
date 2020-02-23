package com.structbuilders.mixin;

import com.structbuilders.block.ModBlocks;
import com.structbuilders.dim.Dimensions;
import com.sun.org.apache.xpath.internal.operations.Mod;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Mixin(BlazeEntity.class)
public class BlazeAIHack {
    @Inject(at = @At("RETURN"), method = "initGoals", cancellable = true)
    private void initGoals(CallbackInfo info) throws NoSuchFieldException, IllegalAccessException {
        MobEntityWithAi self = (MobEntityWithAi) (Object) this;
        Field targetSelector = MobEntity.class.getDeclaredField("targetSelector");
        targetSelector.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.set(targetSelector, targetSelector.getModifiers() & ~Modifier.FINAL);

        if (self.world.getDimension().getType() == Dimensions.ELEMENTAL_PLANES) {
            GoalSelector newSel =  new GoalSelector(self.world.getProfiler());
            newSel.add(0, (new RevengeGoal(self)).setGroupRevenge());
            targetSelector.set(self, newSel);
        }
    }
}
