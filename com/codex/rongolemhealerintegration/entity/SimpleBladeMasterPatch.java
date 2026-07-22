/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  baguchan.slash_illager.entity.BladeMaster
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.PathfinderMob
 *  yesman.epicfight.api.animation.Animator
 *  yesman.epicfight.api.animation.LivingMotion
 *  yesman.epicfight.api.animation.LivingMotions
 *  yesman.epicfight.api.asset.AssetAccessor
 *  yesman.epicfight.gameasset.Animations
 *  yesman.epicfight.world.capabilities.entitypatch.Faction
 *  yesman.epicfight.world.capabilities.entitypatch.Factions
 */
package com.codex.rongolemhealerintegration.entity;

import baguchan.slash_illager.entity.BladeMaster;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.Factions;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;

public class SimpleBladeMasterPatch<T extends PathfinderMob>
extends HumanoidMobPatch<T> {
    static {
        System.out.println("[RoN Debug] SimpleBladeMasterPatch class loaded!");
    }

    private int lastSwingTick = -1000;

    public SimpleBladeMasterPatch() {
        super((Faction)Factions.ILLAGER);
        com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug Patch] SimpleBladeMasterPatch constructor called");
    }

    @Override
    public boolean overrideRender() {
        if (this.original instanceof BladeMaster) {
            BladeMaster bladeMaster = (BladeMaster) this.original;
            if (bladeMaster.m_21223_() > 0.0f) {
                baguchan.slash_illager.animation.VanillaConvertedVmdAnimation anim = bladeMaster.getCurrentAnimation();
                if (anim != null) {
                    com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug Patch] overrideRender: VMD anim present, active=" + anim.isActive() + ", tick=" + anim.getCurrentTick());
                    if (anim.isActive()) {
                        com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug Patch] overrideRender returning FALSE (vanilla renderer active)");
                        return false;
                    }
                }
            }
        }
        boolean res = super.overrideRender();
        // Log occasionally or just let it print to see if it runs
        return res;
    }



    private boolean isEnemy(net.minecraft.world.entity.LivingEntity e, baguchan.slash_illager.entity.BladeMaster bladeMaster) {
        if (e == bladeMaster) {
            return false;
        }
        if (e.m_21223_() <= 0.0f) {
            return false;
        }
        
        // Ignore passive animals and ambient creatures
        if (e instanceof net.minecraft.world.entity.animal.Animal || e instanceof net.minecraft.world.entity.ambient.AmbientCreature) {
            return false;
        }
        
        // Ignore friendly villagers and wandering traders
        if (e instanceof net.minecraft.world.entity.npc.Villager || e instanceof net.minecraft.world.entity.npc.WanderingTrader) {
            return false;
        }
        
        if (e instanceof net.minecraft.world.entity.player.Player) {
            net.minecraft.world.entity.player.Player player = (net.minecraft.world.entity.player.Player) e;
            if (player.m_150110_().f_35937_ || player.m_5833_()) {
                return false;
            }
            
            com.solegendary.reignofnether.unit.interfaces.Unit bmUnit = (com.solegendary.reignofnether.unit.interfaces.Unit) bladeMaster;
            String bladeMasterOwner = bmUnit.getOwnerName();
            String playerName = player.m_7755_().getString();
            
            if (bladeMasterOwner == null || bladeMasterOwner.isEmpty()) {
                return true;
            }
            
            if (playerName.equals(bladeMasterOwner)) {
                return false;
            }
            
            try {
                if (com.solegendary.reignofnether.alliance.AlliancesClient.isAllied(bladeMasterOwner, playerName)) {
                    return false;
                }
            } catch (Throwable t) {
            }
            return true;
        }
        
        if (e instanceof com.solegendary.reignofnether.unit.interfaces.Unit) {
            com.solegendary.reignofnether.unit.interfaces.Unit otherUnit = (com.solegendary.reignofnether.unit.interfaces.Unit) e;
            com.solegendary.reignofnether.unit.interfaces.Unit bmUnit = (com.solegendary.reignofnether.unit.interfaces.Unit) bladeMaster;
            String bladeMasterOwner = bmUnit.getOwnerName();
            String otherUnitOwner = otherUnit.getOwnerName();
            
            if (bladeMasterOwner == null || bladeMasterOwner.isEmpty()) {
                if (otherUnitOwner == null || otherUnitOwner.isEmpty()) {
                    return false;
                }
                return true;
            }
            
            if (bladeMasterOwner.equals(otherUnitOwner)) {
                return false;
            }
            
            try {
                if (com.solegendary.reignofnether.alliance.AlliancesClient.isAllied(bladeMasterOwner, otherUnitOwner)) {
                    return false;
                }
            } catch (Throwable t) {
            }
            return true;
        }
        
        // Treat monsters, custom mod entities, and opposing faction golems as enemies
        return true;
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
        com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug Patch] initAnimator called");
        animator.addLivingAnimation((LivingMotion)LivingMotions.DEATH, (AssetAccessor)Animations.BIPED_DEATH);
        animator.addLivingAnimation((LivingMotion)LivingMotions.IDLE, (AssetAccessor)Animations.BIPED_IDLE);
        animator.addLivingAnimation((LivingMotion)LivingMotions.WALK, (AssetAccessor)Animations.BIPED_WALK);
        animator.addLivingAnimation((LivingMotion)LivingMotions.ANGRY, (AssetAccessor)Animations.BIPED_IDLE);
        animator.addLivingAnimation((LivingMotion)LivingMotions.CHASE, (AssetAccessor)Animations.BIPED_WALK);
    }

    @Override
    public void updateMotion(boolean bl) {
        if (((PathfinderMob)this.original).m_21223_() <= 0.0f) {
            this.currentLivingMotion = LivingMotions.DEATH;
        } else if (this.state.inaction() && bl) {
            this.currentLivingMotion = LivingMotions.INACTION;
        } else {
            boolean bl2 = ((PathfinderMob)this.original).m_5912_();
            this.currentLivingMotion = ((PathfinderMob)this.original).f_267362_.m_267731_() > 0.01f ? (bl2 ? LivingMotions.CHASE : LivingMotions.WALK) : (bl2 ? LivingMotions.ANGRY : LivingMotions.IDLE);
        }
        this.currentCompositeMotion = this.currentLivingMotion;
        // Occasional logging of motion
        if (this.original.f_19797_ % 100 == 0) {
            com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug Patch] updateMotion: currentLivingMotion=" + this.currentLivingMotion + ", speed=" + ((PathfinderMob)this.original).f_267362_.m_267731_());
        }
    }
}

