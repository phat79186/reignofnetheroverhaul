package com.codex.rongolemhealerintegration.mixin;

import com.camdenscottc.colonialcannons.projectile.CannonballEntity;
import com.solegendary.reignofnether.building.BuildingPlacement;
import com.solegendary.reignofnether.building.BuildingUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ArmorStand.class, remap = false)
public abstract class ArmorStandMixin {

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void ronGolemHealerIntegration$onCannonHurtMojang(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ronGolemHealerIntegration$handleHurt(source, amount, cir);
    }

    @Inject(method = "m_6469_", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void ronGolemHealerIntegration$onCannonHurtSrg(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ronGolemHealerIntegration$handleHurt(source, amount, cir);
    }

    private void ronGolemHealerIntegration$handleHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ArmorStand self = (ArmorStand) (Object) this;
        if (!self.m_9236_().m_5776_()) { // server side
            BuildingPlacement bp = BuildingUtils.findBuilding(false, self.m_20183_());
            if (bp != null) {
                boolean isCannonDamage = (source.m_7639_() instanceof CannonballEntity) 
                                      || (source.m_7640_() instanceof CannonballEntity);
                if (isCannonDamage) {
                    // Deal corresponding damage to the building
                    double blocksToDestroy = (double) (amount / 2.0f);
                    bp.destroyRandomBlocks(blocksToDestroy);
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
