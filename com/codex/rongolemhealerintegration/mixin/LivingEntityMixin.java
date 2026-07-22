package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.unit.units.monsters.ZombiePiglinUnit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class, remap = false)
public abstract class LivingEntityMixin {

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void ronGolemHealerIntegration$onHurtMojang(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ronGolemHealerIntegration$printHurtInfo(source, amount);
    }

    @Inject(method = "m_6469_", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void ronGolemHealerIntegration$onHurtSrg(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ronGolemHealerIntegration$printHurtInfo(source, amount);
    }

    private void ronGolemHealerIntegration$printHurtInfo(DamageSource source, float amount) {
        if ((Object) this instanceof ZombiePiglinUnit) {
            ZombiePiglinUnit self = (ZombiePiglinUnit) (Object) this;
            net.minecraft.world.entity.Entity attacker = source.m_7639_();
            String attackerInfo = "null";
            if (attacker instanceof com.solegendary.reignofnether.unit.interfaces.Unit) {
                attackerInfo = attacker.getClass().getSimpleName() + " (Owner: " + ((com.solegendary.reignofnether.unit.interfaces.Unit) attacker).getOwnerName() + ")";
            } else if (attacker != null) {
                attackerInfo = attacker.getClass().getSimpleName();
            }
            System.out.println("[RON_INTEGRATION_DEBUG] ZombiePiglinUnit hurt called! TargetOwner: '" + self.getOwnerName() + 
                               "', Attacker: " + attackerInfo + 
                               ", Amount: " + amount + 
                               ", DamageType: " + source.m_19385_());
        }
    }
}
