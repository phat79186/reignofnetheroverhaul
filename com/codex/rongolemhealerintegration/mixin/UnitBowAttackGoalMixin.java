package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.unit.goals.UnitBowAttackGoal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = {UnitBowAttackGoal.class}, remap = false)
public class UnitBowAttackGoalMixin {
    @Shadow
    private Mob mob;

    /**
     * Injects before performUnitRangedAttack in UnitBowAttackGoal.tick() (m_8037_)
     * to trigger the Epic Fight shooting/throwing animation. This gives ranged units like
     * HeadhunterUnit proper visual ranged attack animations instead of being frozen or meleeing.
     */
    @Inject(
        method = "m_8037_",
        at = @At(
            value = "INVOKE",
            target = "Lcom/solegendary/reignofnether/unit/interfaces/RangedAttackerUnit;performUnitRangedAttack(Lnet/minecraft/world/entity/LivingEntity;F)V"
        ),
        remap = false
    )
    private void ronGolemHealerIntegration$playEpicFightRangedAnimation(CallbackInfo ci) {
        if (this.mob != null && !(this.mob instanceof com.solegendary.reignofnether.unit.units.piglins.HeadhunterUnit)) {
            EpicFightCapabilities.getUnparameterizedEntityPatch((Entity) this.mob, LivingEntityPatch.class)
                .ifPresent(entitypatch -> entitypatch.playShootingAnimation());
        }
    }
}
