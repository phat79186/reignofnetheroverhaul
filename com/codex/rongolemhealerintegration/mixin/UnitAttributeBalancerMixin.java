package com.codex.rongolemhealerintegration.mixin;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Mob.class, remap = false)
public abstract class UnitAttributeBalancerMixin {

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void ronGolemHealerIntegration$applyFactionAttributeBalance(EntityType<?> entityType, Level level, CallbackInfo ci) {
        Mob mob = (Mob) (Object) this;
        String className = mob.getClass().getName();

        double newHp = -1.0;
        double newDmg = -1.0;

        // --- MONSTERS FACTION ---
        if (className.contains("ZombieUnit") || className.contains("HuskUnit") || className.contains("DrownedUnit")) {
            newHp = 48.0;
            newDmg = 4.0;
        } else if (className.contains("SkeletonUnit") || className.contains("StrayUnit")) {
            newHp = 32.0;
            newDmg = 4.5;
        } else if (className.contains("SpiderUnit") || className.contains("PoisonSpiderUnit")) {
            newHp = 36.0;
            newDmg = 5.5;
        } else if (className.contains("WraithUnit") && !className.contains("Wretched")) {
            newHp = 55.0;
            newDmg = 5.5;
        } else if (className.contains("MutantZombie")) {
            newHp = 175.0;
            newDmg = 14.0;
        } else if (className.contains("MutantSkeleton")) {
            newHp = 160.0;
            newDmg = 9.5;
        } else if (className.contains("MutantCreeper")) {
            newHp = 135.0;
            newDmg = 32.0;
        } else if (className.contains("NecromancerUnit")) {
            newHp = 115.0;
            newDmg = 5.0;
        } else if (className.contains("WretchedWraithUnit")) {
            newHp = 155.0;
            newDmg = 6.0;
        } else if (className.contains("WardenUnit")) {
            newHp = 175.0;
            newDmg = 9.5;
        }

        // --- PIGLINS / NETHER FACTION ---
        else if (className.contains("GruntUnit")) {
            newHp = 28.0;
            newDmg = 1.5;
        } else if (className.contains("BruteUnit")) {
            newHp = 65.0;
            newDmg = 6.0;
        } else if (className.contains("HoglinUnit") && !className.contains("Armoured")) {
            newHp = 85.0;
            newDmg = 6.0;
        } else if (className.contains("HeadhunterUnit")) {
            newHp = 42.0;
            newDmg = 6.0;
        } else if (className.contains("WitherSkeletonUnit")) {
            newHp = 110.0;
            newDmg = 4.5;
        } else if (className.contains("GiddyBlaze")) {
            newHp = 90.0;
            newDmg = 7.0;
        } else if (className.contains("ReinforcedBlaze")) {
            newHp = 105.0;
            newDmg = 7.5;
        } else if (className.contains("Propulk")) {
            newHp = 135.0;
            newDmg = 9.0;
        } else if (className.contains("AwfulGhast")) {
            newHp = 220.0;
            newDmg = 13.0;
        } else if (className.contains("IncompleteWither")) {
            newHp = 275.0;
            newDmg = 15.0;
        } else if (className.contains("NetherKeeper")) {
            newHp = 330.0;
            newDmg = 17.0;
        } else if (className.contains("WildfireUnit")) {
            newHp = 130.0;
            newDmg = 3.5;
        } else if (className.contains("PiglinMerchantUnit")) {
            newHp = 165.0;
            newDmg = 8.5;
        } else if (className.contains("ZombiePiglinUnit")) {
            newHp = 42.0;
            newDmg = 4.5;
        } else if (className.contains("ZoglinUnit")) {
            newHp = 70.0;
            newDmg = 6.0;
        }

        if (newHp > 0) {
            AttributeInstance hpAttr = mob.m_21051_(Attributes.f_22276_);
            if (hpAttr != null) {
                hpAttr.m_22100_(newHp);
                mob.m_21153_((float) newHp);
            }
        }
        if (newDmg > 0) {
            AttributeInstance dmgAttr = mob.m_21051_(Attributes.f_22281_);
            if (dmgAttr != null) {
                dmgAttr.m_22100_(newDmg);
            }
        }
    }
}
