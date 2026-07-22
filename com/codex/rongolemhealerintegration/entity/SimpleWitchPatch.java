/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.monster.Witch
 *  yesman.epicfight.api.animation.Animator
 *  yesman.epicfight.api.animation.LivingMotion
 *  yesman.epicfight.api.animation.LivingMotions
 *  yesman.epicfight.api.asset.AssetAccessor
 *  yesman.epicfight.gameasset.Animations
 *  yesman.epicfight.world.capabilities.entitypatch.Faction
 *  yesman.epicfight.world.capabilities.entitypatch.Factions
 */
package com.codex.rongolemhealerintegration.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Witch;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.Factions;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;

public class SimpleWitchPatch<T extends Witch>
extends HumanoidMobPatch<T> {
    public SimpleWitchPatch() {
        super((Faction)Factions.NEUTRAL);
    }

    @Override
    protected void initAI() {
    }

    @Override
    public void onMount(boolean bl, Entity entity) {
    }

    @Override
    public void modifyLivingMotionByCurrentItem(boolean bl) {
    }

    @Override
    public void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation((LivingMotion)LivingMotions.DEATH, (AssetAccessor)Animations.BIPED_DEATH);
        animator.addLivingAnimation((LivingMotion)LivingMotions.IDLE, (AssetAccessor)Animations.ILLAGER_IDLE);
        animator.addLivingAnimation((LivingMotion)LivingMotions.WALK, (AssetAccessor)Animations.ILLAGER_WALK);
        animator.addLivingAnimation((LivingMotion)LivingMotions.DRINK, (AssetAccessor)Animations.WITCH_DRINKING);
    }

    @Override
    public void updateMotion(boolean bl) {
        super.commonMobUpdateMotion(bl);
        if (this.original != null && ((Witch)this.original).m_34161_()) {
            this.currentCompositeMotion = LivingMotions.DRINK;
        }
    }
}

