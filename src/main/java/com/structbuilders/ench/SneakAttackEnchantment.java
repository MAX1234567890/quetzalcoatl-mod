package com.structbuilders.ench;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class SneakAttackEnchantment extends Enchantment {
    protected SneakAttackEnchantment(Weight weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public int getMinimumLevel() {
        return 1;
    }

    @Override
    public int getMaximumLevel() {
        return 3;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        super.onTargetDamaged(user, target, level);
        System.out.println(user.yaw - target.yaw);
        if (Math.abs(user.yaw - target.yaw) < 45)
            target.damage(DamageSource.MAGIC, 10 * level);
    }

    @Override
    public boolean isTreasure() {
        return true;
    }
}
