package com.structbuilders.ent;

import com.structbuilders.block.ModBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.LinkedHashSet;

public class NecromancerEntity extends IllusionerEntity {
    public NecromancerEntity(EntityType<? extends IllusionerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.getBlockState(this.getBlockPos()).isAir()) {
            this.world.setBlockState(this.getBlockPos(), ModBlocks.ACID.getDefaultState());
        }
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        Field goals_ = null;
        try {
            goals_ = GoalSelector.class.getDeclaredField("goals");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        goals_.setAccessible(true);
        LinkedHashSet<WeightedGoal> goals = null;
        try {
            goals = (LinkedHashSet<WeightedGoal>) goals_.get(this.goalSelector);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (WeightedGoal goal : goals) {
            if (
                    goal.getGoal() instanceof IllusionerEntity.CastSpellGoal
                            || goal.getGoal() instanceof BowAttackGoal
            ) {
                this.goalSelector.remove(goal);
            }
        }
        this.goalSelector.add(3, new SummonZombieGoal());
    }

    private class SummonZombieGoal extends CastSpellGoal {
        @Override
        protected void castSpell() {
            ZombieEntity zombo = new ZombieEntity(NecromancerEntity.this.world);
            zombo.changeDimension(NecromancerEntity.this.dimension);
            zombo.setPos(NecromancerEntity.this.getX(), NecromancerEntity.this.getY(), NecromancerEntity.this.getZ());
            NecromancerEntity.this.world.spawnEntity(zombo);
        }

        @Override
        protected int getSpellTicks() {
            return 20;
        }

        @Override
        protected int startTimeDelay() {
            return 340;
        }

        @Nullable
        @Override
        protected SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK;
        }

        @Override
        protected Spell getSpell() {
            return Spell.FANGS;
        }
    }
}
