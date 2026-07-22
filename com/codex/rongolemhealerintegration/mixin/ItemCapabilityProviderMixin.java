package com.codex.rongolemhealerintegration.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.provider.ItemCapabilityProvider;

@Mixin(value = ItemCapabilityProvider.class, remap = false)
public abstract class ItemCapabilityProviderMixin {
    @Shadow
    private CapabilityItem capability;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void ronGolemHealerIntegration$onInit(ItemStack itemStack, CallbackInfo ci) {
        if (itemStack.m_41720_() instanceof TridentItem) {
            this.capability = CapabilityItem.EMPTY;
        }
    }
}
