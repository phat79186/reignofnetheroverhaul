package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.unit.units.monsters.ZombiePiglinUnit;
import com.solegendary.reignofnether.unit.interfaces.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ZombiePiglinUnit.class, remap = false)
public abstract class ZombiePiglinUnitMixin {

    @Inject(method = "m_21527_", at = @At("HEAD"), cancellable = true, remap = false)
    private void ronGolemHealerIntegration$onIsSensitiveToSun(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "getSunlightEffect", at = @At("HEAD"), cancellable = true, remap = false)
    private void ronGolemHealerIntegration$onGetSunlightEffect(CallbackInfoReturnable<Unit.SunlightEffect> cir) {
        cir.setReturnValue(Unit.SunlightEffect.NONE);
    }
}
