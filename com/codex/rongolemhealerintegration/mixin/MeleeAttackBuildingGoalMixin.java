package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.unit.goals.MeleeAttackBuildingGoal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;

import com.solegendary.reignofnether.unit.goals.MoveToTargetBlockGoal;

@Mixin(value = {MeleeAttackBuildingGoal.class}, remap = false)
public abstract class MeleeAttackBuildingGoalMixin extends MoveToTargetBlockGoal {

    public MeleeAttackBuildingGoalMixin(Mob mob) {
        super(mob, true, 0);
    }

    @Inject(method = "doBuildingAttack", at = @At("HEAD"))
    private void ronGolemHealerIntegration$playEpicFightAnimation(CallbackInfo ci) {
        if (this.mob != null && !this.mob.m_9236_().m_5776_()) {
            playEpicFightAttackAnimation(this.mob);
        }
    }

    private static void playEpicFightAttackAnimation(LivingEntity entity) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch == null) {
            return;
        }

        AssetAccessor<? extends StaticAnimation> anim = null;
        ItemStack stack = entity.m_21205_(); // main hand
        CapabilityItem capItem = EpicFightCapabilities.getItemStackCapability(stack);

        if (capItem != null && !capItem.isEmpty()) {
            yesman.epicfight.world.capabilities.item.WeaponCategory category = capItem.getWeaponCategory();
            if (category == CapabilityItem.WeaponCategories.GREATSWORD) {
                anim = Animations.BIPED_MOB_GREATSWORD;
            } else if (category == CapabilityItem.WeaponCategories.TACHI) {
                anim = Animations.BIPED_MOB_TACHI;
            } else if (category == CapabilityItem.WeaponCategories.SPEAR) {
                anim = Animations.BIPED_MOB_SPEAR_TWOHAND1;
            } else if (category == CapabilityItem.WeaponCategories.SWORD) {
                anim = Animations.BIPED_MOB_ONEHAND1;
            } else if (category == CapabilityItem.WeaponCategories.AXE) {
                anim = Animations.VINDICATOR_SWING_AXE1;
            } else if (category == CapabilityItem.WeaponCategories.DAGGER) {
                anim = Animations.BIPED_MOB_DAGGER_ONEHAND1;
            }
        }

        if (anim == null) {
            EntityType<?> type = entity.m_6095_();
            String name = type.m_20677_().m_135815_(); // type name path
            if (name.contains("zombie") || name.contains("husk") || name.contains("drowned")) {
                int r = entity.m_217043_().m_188503_(3);
                if (r == 0) anim = Animations.ZOMBIE_ATTACK1;
                else if (r == 1) anim = Animations.ZOMBIE_ATTACK2;
                else anim = Animations.ZOMBIE_ATTACK3;
            } else if (name.contains("skeleton") || name.contains("stray") || name.contains("bogged")) {
                anim = Animations.BIPED_MOB_ONEHAND1;
            } else if (name.contains("creeper")) {
                anim = Animations.CREEPER_HIT_SHORT;
            } else if (name.contains("spider")) {
                anim = Animations.SPIDER_ATTACK;
            } else if (name.contains("iron_golem") || entity instanceof net.minecraft.world.entity.animal.IronGolem) {
                anim = Animations.GOLEM_ATTACK1;
            } else {
                anim = Animations.BIPED_MOB_ONEHAND1;
            }
        }

        if (anim != null) {
            patch.playAnimationSynchronized(anim, 0.0f);
        }
    }
}
