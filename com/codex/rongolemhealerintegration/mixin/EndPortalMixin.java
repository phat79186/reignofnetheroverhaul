package com.codex.rongolemhealerintegration.mixin;

import com.codex.rongolemhealerintegration.ron.RonStalwartDungeonsProductionItems;
import com.solegendary.reignofnether.building.buildings.neutral.EndPortal;
import com.solegendary.reignofnether.keybinds.Keybindings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {EndPortal.class}, remap = false)
public abstract class EndPortalMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void ronGolemHealerIntegration$addStalwartDungeonsUnits(CallbackInfo ci) {
        EndPortal self = (EndPortal) (Object) this;
        self.productions.add(RonStalwartDungeonsProductionItems.PROPULK, Keybindings.abilitySlot2);
        self.productions.add(RonStalwartDungeonsProductionItems.SHULKER_CANNON, Keybindings.abilitySlot3);
    }
}
