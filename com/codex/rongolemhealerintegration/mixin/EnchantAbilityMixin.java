package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.ability.EnchantAbility;
import com.solegendary.reignofnether.ability.abilities.EnchantMultishot;
import com.solegendary.reignofnether.ability.abilities.EnchantQuickCharge;
import com.solegendary.reignofnether.ability.PillagerGunEnchantBridge;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EnchantAbility.class, remap = false)
public class EnchantAbilityMixin {
    @Inject(method = "isCorrectUnitAndEquipment", at = @At("HEAD"), cancellable = true)
    private void onIsCorrectUnitAndEquipment(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        Object self = (Object) this;
        if (self instanceof EnchantMultishot || self instanceof EnchantQuickCharge) {
            if (PillagerGunEnchantBridge.isSpringfieldPillager(livingEntity)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "doEnchant", at = @At("HEAD"), cancellable = true)
    private void onDoEnchant(LivingEntity livingEntity, CallbackInfo ci) {
        Object self = (Object) this;
        if (self instanceof EnchantMultishot || self instanceof EnchantQuickCharge) {
            if (PillagerGunEnchantBridge.isSpringfieldPillager(livingEntity)) {
                PillagerGunEnchantBridge.apply(self, livingEntity);
                ci.cancel();
            }
        }
    }

    @Inject(method = "hasSameEnchant", at = @At("HEAD"), cancellable = true)
    private void onHasSameEnchant(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        Object self = (Object) this;
        if (self instanceof EnchantMultishot || self instanceof EnchantQuickCharge) {
            if (PillagerGunEnchantBridge.isSpringfieldPillager(livingEntity)) {
                cir.setReturnValue(PillagerGunEnchantBridge.hasSame(self, livingEntity));
            }
        }
    }
}
