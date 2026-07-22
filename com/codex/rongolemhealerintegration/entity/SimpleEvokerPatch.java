/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.monster.SpellcasterIllager
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
import net.minecraft.world.entity.monster.SpellcasterIllager;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.Factions;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;

public class SimpleEvokerPatch<T extends SpellcasterIllager>
extends HumanoidMobPatch<T> {
    public SimpleEvokerPatch() {
        super((Faction)Factions.ILLAGER);
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
        animator.addLivingAnimation((LivingMotion)LivingMotions.SPELLCAST, (AssetAccessor)Animations.EVOKER_CAST_SPELL);
    }

    @Override
    public void updateMotion(boolean bl) {
        if (this.state.inaction() && bl) {
            this.currentLivingMotion = LivingMotions.INACTION;
        } else if (this.original != null && ((SpellcasterIllager)this.original).m_33736_()) {
            this.currentLivingMotion = LivingMotions.SPELLCAST;
        } else {
            super.commonMobUpdateMotion(bl);
        }
    }
}

