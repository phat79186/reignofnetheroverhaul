package com.codex.rongolemhealerintegration.ron;

import com.solegendary.reignofnether.building.buildings.placements.ProductionPlacement;
import com.solegendary.reignofnether.building.production.ProdDupeRule;
import com.solegendary.reignofnether.building.production.ProductionItem;
import com.solegendary.reignofnether.building.production.StartProductionButton;
import com.solegendary.reignofnether.building.production.StopProductionButton;
import com.solegendary.reignofnether.keybinds.Keybinding;
import com.solegendary.reignofnether.research.ResearchClient;
import com.solegendary.reignofnether.research.ResearchServerEvents;
import com.solegendary.reignofnether.resources.ResourceCost;
import com.solegendary.reignofnether.resources.ResourceCosts;
import java.util.List;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class ResearchChemicalX extends ProductionItem {
    public static final String itemName = "Research Chemical X";
    public static final ResourceCost cost = ResourceCost.Research(150, 100, 300, 60);

    public ResearchChemicalX() {
        super(cost, ProdDupeRule.DISALLOW);
        this.onComplete = (level, placement) -> {
            if (level.m_5776_()) {
                ResearchClient.addResearch(placement.ownerName, RonGolemHealerProductionItems.RESEARCH_CHEMICAL_X);
            } else {
                ResearchServerEvents.addResearch(placement.ownerName, RonGolemHealerProductionItems.RESEARCH_CHEMICAL_X);
            }
        };
    }

    @Override
    public String getItemName() {
        return itemName;
    }

    @Override
    public StartProductionButton getStartButton(ProductionPlacement prodBuilding, Keybinding hotkey) {
        return new StartProductionButton(
            itemName, 
            new ResourceLocation("mutantmonsters", "textures/item/lingering_chemical_x.png"), 
            new ResourceLocation("reignofnether", "textures/hud/icon_frame_bronze.png"), 
            hotkey, 
            () -> RonGolemHealerProductionItems.RESEARCH_CHEMICAL_X.itemIsBeingProduced(prodBuilding.ownerName) || ResearchClient.hasResearch(RonGolemHealerProductionItems.RESEARCH_CHEMICAL_X), 
            () -> true, 
            List.of(
                FormattedCharSequence.m_13714_("Research Chemical X", Style.f_131099_.m_131136_(Boolean.valueOf(true))), 
                ResourceCosts.getFormattedCost(cost), 
                ResourceCosts.getFormattedTime(cost), 
                FormattedCharSequence.m_13714_("", Style.f_131099_), 
                FormattedCharSequence.m_13714_("Unlocks the Chemical X Potion throwing ability", Style.f_131099_),
                FormattedCharSequence.m_13714_("at the Laboratory.", Style.f_131099_)
            ), 
            this
        );
    }

    @Override
    public StopProductionButton getCancelButton(ProductionPlacement prodBuilding, boolean first) {
        return new StopProductionButton(
            itemName, 
            new ResourceLocation("mutantmonsters", "textures/item/lingering_chemical_x.png"), 
            new ResourceLocation("reignofnether", "textures/hud/icon_frame_bronze.png"), 
            prodBuilding, 
            this, 
            first
        );
    }
}
