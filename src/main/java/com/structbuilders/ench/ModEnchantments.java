package com.structbuilders.ench;

import com.structbuilders.QZEntryPoint;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEnchantments {
    public static Enchantment VAMPIRISM;
    public static Enchantment SNEAK_ATTACK;

    public static void register() {
        VAMPIRISM = Registry.register(
                Registry.ENCHANTMENT,
                new Identifier(QZEntryPoint.MOD_ID, "vampirism"),
                new VampirismEnchantment(
                        Enchantment.Weight.VERY_RARE,
                        EnchantmentTarget.WEAPON,
                        new EquipmentSlot[] {
                                EquipmentSlot.MAINHAND
                        }
                )
        );

        SNEAK_ATTACK = Registry.register(
                Registry.ENCHANTMENT,
                new Identifier(QZEntryPoint.MOD_ID, "sneak_attack"),
                new SneakAttackEnchantment(
                        Enchantment.Weight.VERY_RARE,
                        EnchantmentTarget.WEAPON,
                        new EquipmentSlot[] {
                                EquipmentSlot.MAINHAND
                        }
                )
        );
    }
}
