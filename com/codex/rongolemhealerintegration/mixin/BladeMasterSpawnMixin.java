package com.codex.rongolemhealerintegration.mixin;

import baguchan.slash_illager.entity.BladeMaster;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;

@Mixin(value = BladeMaster.class, remap = false)
public class BladeMasterSpawnMixin {

    @Inject(method = "m_213945_", at = @At("RETURN"))
    private void onPopulateDefaultEquipmentSlots(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        ItemStack stack = self.m_6844_(EquipmentSlot.MAINHAND);
        if (!stack.m_41619_()) {
            EnchantmentHelper.m_44865_(Collections.emptyMap(), stack);
        }
    }

    @Inject(method = "m_6518_", at = @At("RETURN"))
    private void onFinalizeSpawn(CallbackInfoReturnable cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        ItemStack stack = self.m_6844_(EquipmentSlot.MAINHAND);
        if (!stack.m_41619_()) {
            EnchantmentHelper.m_44865_(Collections.emptyMap(), stack);
        }
    }

    @Inject(method = "m_7895_", at = @At("RETURN"))
    private void onApplyRaidBuffs(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        ItemStack stack = self.m_6844_(EquipmentSlot.MAINHAND);
        if (!stack.m_41619_()) {
            EnchantmentHelper.m_44865_(Collections.emptyMap(), stack);
        }
    }
}
