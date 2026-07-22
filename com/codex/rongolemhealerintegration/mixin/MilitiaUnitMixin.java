package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.unit.units.villagers.MilitiaUnit;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MilitiaUnit.class, remap = false)
public class MilitiaUnitMixin {
    @Inject(method = "convertToVillager", at = @At("HEAD"), cancellable = true)
    private void onConvertToVillager(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.m_19880_().contains("no_villager_convert")) {
            ci.cancel();
        }
    }
}
