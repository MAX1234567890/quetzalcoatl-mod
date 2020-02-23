package com.structbuilders.ench;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class VampirismEnchantment extends Enchantment {
    protected VampirismEnchantment(Weight weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public int getMinimumLevel() {
        return 1;
    }

    @Override
    public int getMaximumLevel() {
        return 7;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        super.onTargetDamaged(user, target, level);
        user.heal(level);
    }

    @Override
    public boolean isTreasure() {
        return true;
    }
}
