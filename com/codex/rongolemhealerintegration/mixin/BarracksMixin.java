/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.solegendary.reignofnether.building.buildings.villagers.Barracks
 *  com.solegendary.reignofnether.building.production.ProductionItem
 *  com.solegendary.reignofnether.keybinds.Keybindings
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.codex.rongolemhealerintegration.mixin;

import com.codex.rongolemhealerintegration.ron.RonGuardIllagerProductionItems;
import com.codex.rongolemhealerintegration.ron.RonSlashIllagerProductionItems;
import com.solegendary.reignofnether.building.buildings.villagers.Barracks;
import com.solegendary.reignofnether.building.production.ProductionItem;
import com.solegendary.reignofnether.keybinds.Keybindings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Barracks.class}, remap=false)
public abstract class BarracksMixin {
    @Inject(method={"<init>"}, at={@At(value="RETURN")}, remap=false)
    private void ronGolemHealerIntegration$addProduction(CallbackInfo ci) {
        Barracks self = (Barracks)(Object)this;
        self.productions.add((ProductionItem)RonGuardIllagerProductionItems.GUARD_ILLAGER, Keybindings.abilitySlot4);
        self.productions.add((ProductionItem)RonSlashIllagerProductionItems.BLADE_MASTER, Keybindings.abilitySlot5);
    }
}

