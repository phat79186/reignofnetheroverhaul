/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.min01.guardillagers.entity.GuardIllager
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeInstance
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.alchemy.Potion
 *  net.minecraft.world.item.alchemy.PotionUtils
 *  net.minecraft.world.item.alchemy.Potions
 *  net.minecraft.world.level.ItemLike
 */
package com.codex.rongolemhealerintegration.entity.ai;

import com.min01.guardillagers.entity.GuardIllager;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.UUID;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;

public class GuardIllagerCustomAttackGoal
extends Goal {
    private final GuardIllager mob;
    private final PathNavigation navigation;
    private LivingEntity target;
    private int attackInterval = 20;
    private int ticksUntilNextAttack = 0;
    private static final UUID SPEED_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
    private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(SPEED_UUID, "Drinking speed penalty", -0.24, AttributeModifier.Operation.ADDITION);
    private static final UUID KNOCKBACK_UUID = UUID.fromString("8742C557-FCD8-4EDC-8B97-D295E2F415FF");
    private static final AttributeModifier KNOCKBACK_MODIFIER = new AttributeModifier(KNOCKBACK_UUID, "Shield knockback resistance", 0.75, AttributeModifier.Operation.ADDITION);

    public GuardIllagerCustomAttackGoal(GuardIllager guardIllager) {
        this.mob = guardIllager;
        this.navigation = guardIllager.m_21573_();
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean m_8036_() {
        LivingEntity livingEntity = this.mob.m_5448_();
        if (livingEntity == null || !livingEntity.m_6084_()) {
            return false;
        }
        this.target = livingEntity;
        return true;
    }

    public boolean m_8045_() {
        LivingEntity livingEntity = this.mob.m_5448_();
        return livingEntity != null && livingEntity.m_6084_();
    }

    public void m_8056_() {
        this.ticksUntilNextAttack = 0;
        this.mob.m_21561_(true);
    }

    public void m_8041_() {
        this.target = null;
        this.navigation.m_26573_();
        this.mob.m_21561_(false);
        if (this.mob.isGuardSelf()) {
            this.mob.setGuardSelf(false);
            this.mob.m_5810_();
            this.removeModifiers();
        }
        if (this.mob.isDrinkingPotion()) {
            this.mob.setDrinkingPotion(false);
            this.mob.m_5810_();
            this.removeModifiers();
            this.restoreOffhandShield();
        }
    }

    public void m_8037_() {
        if (this.target == null) {
            return;
        }
        this.mob.m_21563_().m_24960_((Entity)this.target, 30.0f, 30.0f);
        double d = this.mob.m_21223_() / this.mob.m_21233_();
        if (d < 0.5 && !this.mob.isDrinkingPotion()) {
            if (this.mob.isGuardSelf()) {
                this.mob.setGuardSelf(false);
                this.mob.m_5810_();
                this.removeModifiers();
            }
            this.mob.m_8061_(EquipmentSlot.OFFHAND, PotionUtils.m_43549_((ItemStack)new ItemStack((ItemLike)Items.f_42589_), (Potion)Potions.f_43623_));
            this.setItemUseTimer(this.mob.m_21206_().m_41779_());
            this.mob.setDrinkingPotion(true);
            this.mob.m_9236_().m_6263_(null, this.mob.m_20185_(), this.mob.m_20186_(), this.mob.m_20189_(), SoundEvents.f_12551_, this.mob.m_5720_(), 1.0f, 0.8f + this.mob.m_217043_().m_188501_() * 0.4f);
            AttributeInstance attributeInstance = this.mob.m_21051_(Attributes.f_22279_);
            if (attributeInstance != null) {
                attributeInstance.m_22130_(SPEED_MODIFIER);
                attributeInstance.m_22125_(SPEED_MODIFIER);
            }
        }
        if (this.mob.isDrinkingPotion()) {
            this.navigation.m_5624_((Entity)this.target, 1.0);
            return;
        }
        double d2 = this.mob.m_20270_((Entity)this.target);
        if (d2 > 10.0) {
            if (this.mob.isGuardSelf()) {
                this.mob.setGuardSelf(false);
                this.mob.m_5810_();
                this.removeModifiers();
            }
            if (!this.mob.isDrinkingPotion() && !this.mob.m_21023_(MobEffects.f_19596_)) {
                this.mob.m_5810_();
                this.mob.m_8061_(EquipmentSlot.OFFHAND, PotionUtils.m_43549_((ItemStack)new ItemStack((ItemLike)Items.f_42589_), (Potion)Potions.f_43612_));
                this.setItemUseTimer(this.mob.m_21206_().m_41779_());
                this.mob.setDrinkingPotion(true);
                this.mob.m_9236_().m_6263_(null, this.mob.m_20185_(), this.mob.m_20186_(), this.mob.m_20189_(), SoundEvents.f_12551_, this.mob.m_5720_(), 1.0f, 0.8f + this.mob.m_217043_().m_188501_() * 0.4f);
                AttributeInstance attributeInstance = this.mob.m_21051_(Attributes.f_22279_);
                if (attributeInstance != null) {
                    attributeInstance.m_22130_(SPEED_MODIFIER);
                    attributeInstance.m_22125_(SPEED_MODIFIER);
                }
            }
            this.navigation.m_5624_((Entity)this.target, 1.25);
        } else if (d2 >= 5.0) {
            if (this.mob.isDrinkingPotion()) {
                this.mob.setDrinkingPotion(false);
                this.mob.m_5810_();
                this.removeModifiers();
                this.restoreOffhandShield();
            }
            if (!this.mob.isGuardSelf()) {
                this.setItemUseTimer(100);
                this.mob.setGuardSelf(true);
                this.mob.m_6672_(InteractionHand.OFF_HAND);
                AttributeInstance attributeInstance = this.mob.m_21051_(Attributes.f_22279_);
                AttributeInstance attributeInstance2 = this.mob.m_21051_(Attributes.f_22278_);
                if (attributeInstance2 != null) {
                    attributeInstance2.m_22130_(KNOCKBACK_MODIFIER);
                    attributeInstance2.m_22125_(KNOCKBACK_MODIFIER);
                }
                if (attributeInstance != null) {
                    attributeInstance.m_22130_(SPEED_MODIFIER);
                    attributeInstance.m_22125_(SPEED_MODIFIER);
                }
            } else {
                this.setItemUseTimer(100);
            }
            this.navigation.m_5624_((Entity)this.target, 1.0);
        } else {
            if (this.mob.isGuardSelf()) {
                this.mob.setGuardSelf(false);
                this.mob.m_5810_();
                this.removeModifiers();
            }
            if (this.mob.isDrinkingPotion()) {
                this.mob.setDrinkingPotion(false);
                this.mob.m_5810_();
                this.removeModifiers();
                this.restoreOffhandShield();
            }
            this.navigation.m_5624_((Entity)this.target, 1.0);
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            if (this.ticksUntilNextAttack <= 0 && d2 <= 3.0) {
                this.mob.m_6674_(InteractionHand.MAIN_HAND);
                this.mob.m_7327_((Entity)this.target);
                this.ticksUntilNextAttack = this.attackInterval;
            }
        }
    }

    private void removeModifiers() {
        AttributeInstance attributeInstance = this.mob.m_21051_(Attributes.f_22279_);
        AttributeInstance attributeInstance2 = this.mob.m_21051_(Attributes.f_22278_);
        if (attributeInstance != null) {
            attributeInstance.m_22130_(SPEED_MODIFIER);
        }
        if (attributeInstance2 != null) {
            attributeInstance2.m_22130_(KNOCKBACK_MODIFIER);
        }
    }

    private void restoreOffhandShield() {
        if (this.mob.getGuardLevel() >= 1) {
            this.mob.m_8061_(EquipmentSlot.OFFHAND, GuardIllager.getIllagerShield());
        } else {
            this.mob.m_8061_(EquipmentSlot.OFFHAND, ItemStack.f_41583_);
        }
    }

    private void setItemUseTimer(int n) {
        try {
            Field field = GuardIllager.class.getDeclaredField("itemUseTimer");
            field.setAccessible(true);
            field.setInt(this.mob, n);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

