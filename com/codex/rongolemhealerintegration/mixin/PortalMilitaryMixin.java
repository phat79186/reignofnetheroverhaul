package com.codex.rongolemhealerintegration.mixin;

import com.codex.rongolemhealerintegration.ron.RonStalwartDungeonsProductionItems;
import com.codex.rongolemhealerintegration.ron.RonGolemHealerProductionItems;
import com.solegendary.reignofnether.building.buildings.piglins.PortalMilitary;
import com.solegendary.reignofnether.building.production.ProductionItems;
import com.solegendary.reignofnether.keybinds.Keybindings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {PortalMilitary.class}, remap = false)
public abstract class PortalMilitaryMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void ronGolemHealerIntegration$addStalwartDungeonsUnits(CallbackInfo ci) {
        PortalMilitary self = (PortalMilitary) (Object) this;
        self.productions.add(RonStalwartDungeonsProductionItems.AWFUL_GHAST, Keybindings.abilitySlot9);
        self.productions.add(RonStalwartDungeonsProductionItems.NETHER_KEEPER, Keybindings.abilitySlot10);
        self.productions.add(RonStalwartDungeonsProductionItems.GIDDY_BLAZE, Keybindings.hotkey1);
        self.productions.add(RonStalwartDungeonsProductionItems.INCOMPLETE_WITHER, Keybindings.hotkey2);
        self.productions.add(RonStalwartDungeonsProductionItems.REINFORCED_BLAZE, Keybindings.minimapToggle);
        self.productions.add(ProductionItems.ZOMBIE_PIGLIN, Keybindings.hotkey7);
        self.productions.add(RonGolemHealerProductionItems.WITHER_BOSS, Keybindings.hotkey6);
    }
}
