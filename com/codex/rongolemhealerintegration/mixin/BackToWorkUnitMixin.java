package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.ability.abilities.BackToWorkUnit;
import com.solegendary.reignofnether.hud.AbilityButton;
import com.solegendary.reignofnether.keybinds.Keybinding;
import com.solegendary.reignofnether.unit.interfaces.Unit;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BackToWorkUnit.class, remap = false)
public class BackToWorkUnitMixin {
    @Inject(method = "getButton", at = @At("HEAD"), cancellable = true)
    private void onGetButton(Keybinding hotkey, Unit unit, CallbackInfoReturnable<AbilityButton> cir) {
        if (unit instanceof LivingEntity entity && entity.m_19880_().contains("no_villager_convert")) {
            cir.setReturnValue(null);
        }
    }
}
