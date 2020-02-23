package com.structbuilders.mixin;

import com.structbuilders.dim.Dimensions;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.mob.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Mixin(AbstractSkeletonEntity.class)
public class SkeletonAIHack {
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
