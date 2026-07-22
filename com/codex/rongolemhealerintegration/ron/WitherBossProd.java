package com.codex.rongolemhealerintegration.ron;

import com.solegendary.reignofnether.building.buildings.placements.ProductionPlacement;
import com.solegendary.reignofnether.building.production.ProductionItem;
import com.solegendary.reignofnether.building.production.StartProductionButton;
import com.solegendary.reignofnether.building.production.StopProductionButton;
import com.solegendary.reignofnether.hud.buttons.UnitSpawnButton;
import com.solegendary.reignofnether.keybinds.Keybinding;
import com.solegendary.reignofnether.resources.ResourceCost;
import com.solegendary.reignofnether.resources.ResourceCosts;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public final class WitherBossProd extends ProductionItem {
    public static final String ITEM_NAME = "Wither Boss";
    public static final ResourceCost COST = ResourceCost.Unit(800, 0, 800, 60, 5);
    public static final ResourceLocation ICON_TEXTURE = new ResourceLocation("minecraft", "textures/item/nether_star.png");

    public WitherBossProd() {
        super(COST);
        this.onComplete = WitherBossProd::spawnUnit;
    }

    @Override
    public String getItemName() {
        return ITEM_NAME;
    }

    public UnitSpawnButton getPlaceButton() {
        return new UnitSpawnButton(ITEM_NAME, ICON_TEXTURE, List.of(WitherBossProd.line("Wither Boss", true)));
    }

    @Override
    public StartProductionButton getStartButton(ProductionPlacement placement, Keybinding hotkey) {
        return new StartProductionButton(ITEM_NAME, ICON_TEXTURE, hotkey, () -> false, () -> true, List.of(WitherBossProd.line("Wither Boss", true), ResourceCosts.getFormattedCost(COST), ResourceCosts.getFormattedPopAndTime(COST)), this);
    }

    @Override
    public StopProductionButton getCancelButton(ProductionPlacement placement, boolean first) {
        return new StopProductionButton(ITEM_NAME, ICON_TEXTURE, placement, this, first);
    }

    private static FormattedCharSequence line(String text, boolean bold) {
        return Component.m_237113_(text).m_6881_().m_130948_(bold ? Style.f_131099_.m_131136_(Boolean.valueOf(true)) : Style.f_131099_).m_7532_();
    }

    @SuppressWarnings("unchecked")
    private static void spawnUnit(Level level, ProductionPlacement placement) {
        if (level.m_5776_()) {
            return;
        }
        EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("minecraft", "wither"));
        if (entityType != null) {
            placement.produceUnit((ServerLevel) level, (EntityType<? extends com.solegendary.reignofnether.unit.interfaces.Unit>) entityType, placement.ownerName, true);
        }
    }
}
