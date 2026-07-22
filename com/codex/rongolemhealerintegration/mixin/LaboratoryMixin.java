package com.codex.rongolemhealerintegration.mixin;

import com.codex.rongolemhealerintegration.ron.ThrowChemicalXAbility;
import com.codex.rongolemhealerintegration.ron.RonGolemHealerProductionItems;
import com.solegendary.reignofnether.ability.Ability;
import com.solegendary.reignofnether.building.buildings.monsters.Laboratory;
import com.solegendary.reignofnether.building.BuildingPlacement;
import com.solegendary.reignofnether.keybinds.Keybindings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {Laboratory.class}, remap = false)
public abstract class LaboratoryMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void ronGolemHealerIntegration$addChemicalXAbility(CallbackInfo ci) {
        Laboratory self = (Laboratory) (Object) this;
        self.getAbilities().add(new ThrowChemicalXAbility(), Keybindings.abilitySlot7);
        self.productions.add(RonGolemHealerProductionItems.RESEARCH_CHEMICAL_X, Keybindings.hotkey7);
    }

    @Overwrite
    public int getRange(BuildingPlacement placement) {
        Laboratory self = (Laboratory) (Object) this;
        boolean hasChemicalX;
        if (placement.level.m_5776_()) {
            hasChemicalX = com.solegendary.reignofnether.research.ResearchClient.hasResearch(RonGolemHealerProductionItems.RESEARCH_CHEMICAL_X);
        } else {
            hasChemicalX = com.solegendary.reignofnether.research.ResearchServerEvents.playerHasResearch(placement.ownerName, RonGolemHealerProductionItems.RESEARCH_CHEMICAL_X);
        }
        return (self.getUpgradeLevel(placement) > 0 || hasChemicalX) && placement.isBuilt ? 25 : 0;
    }
}
