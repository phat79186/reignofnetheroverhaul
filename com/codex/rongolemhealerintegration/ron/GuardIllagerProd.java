/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.solegendary.reignofnether.building.buildings.placements.ProductionPlacement
 *  com.solegendary.reignofnether.building.production.ProductionItem
 *  com.solegendary.reignofnether.building.production.StartProductionButton
 *  com.solegendary.reignofnether.building.production.StopProductionButton
 *  com.solegendary.reignofnether.hud.buttons.UnitSpawnButton
 *  com.solegendary.reignofnether.keybinds.Keybinding
 *  com.solegendary.reignofnether.resources.ResourceCost
 *  com.solegendary.reignofnether.resources.ResourceCosts
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Style
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.Level
 */
package com.codex.rongolemhealerintegration.ron;

import com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod;
import com.codex.rongolemhealerintegration.RonGuardIllagerIntegrationValues;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public final class GuardIllagerProd
extends ProductionItem {
    public static final String ITEM_NAME = "Guard Illager";
    public static final ResourceCost COST = RonGuardIllagerIntegrationValues.RESOURCE_COST;

    public GuardIllagerProd() {
        super(COST);
        this.onComplete = GuardIllagerProd::spawnUnit;
    }

    public String getItemName() {
        return ITEM_NAME;
    }

    public UnitSpawnButton getPlaceButton() {
        return new UnitSpawnButton(ITEM_NAME, RonGuardIllagerIntegrationValues.ICON_TEXTURE, List.of(GuardIllagerProd.line("units.villagers.reignofnether.guard_illager", true), GuardIllagerProd.line("", false), GuardIllagerProd.line("units.villagers.reignofnether.guard_illager.tooltip1", false), GuardIllagerProd.line("units.villagers.reignofnether.guard_illager.tooltip2", false)));
    }

    public StartProductionButton getStartButton(ProductionPlacement placement, Keybinding hotkey) {
        return new StartProductionButton(ITEM_NAME, RonGuardIllagerIntegrationValues.ICON_TEXTURE, hotkey, () -> false, () -> true, List.of(GuardIllagerProd.line("units.villagers.reignofnether.guard_illager", true), ResourceCosts.getFormattedCost((ResourceCost)COST), ResourceCosts.getFormattedPopAndTime((ResourceCost)COST), GuardIllagerProd.literalLine("HP 24", false), GuardIllagerProd.literalLine("Damage 3", false), GuardIllagerProd.literalLine("Armor 6", false), GuardIllagerProd.line("", false), GuardIllagerProd.line("units.villagers.reignofnether.guard_illager.tooltip1", false), GuardIllagerProd.line("units.villagers.reignofnether.guard_illager.tooltip2", false)), (ProductionItem)this);
    }

    public StopProductionButton getCancelButton(ProductionPlacement placement, boolean first) {
        return new StopProductionButton(ITEM_NAME, RonGuardIllagerIntegrationValues.ICON_TEXTURE, placement, (ProductionItem)this, first);
    }

    private static FormattedCharSequence line(String key, boolean bold) {
        return GuardIllagerProd.componentLine((Component)(key.isEmpty() ? Component.m_237119_() : Component.m_237115_((String)key)), bold);
    }

    private static FormattedCharSequence literalLine(String text, boolean bold) {
        return GuardIllagerProd.componentLine((Component)Component.m_237113_((String)text), bold);
    }

    private static FormattedCharSequence componentLine(Component component, boolean bold) {
        return component.m_6881_().m_130948_(bold ? Style.f_131099_.m_131136_(Boolean.valueOf(true)) : Style.f_131099_).m_7532_();
    }

    private static void spawnUnit(Level level, ProductionPlacement placement) {
        if (level.m_5776_()) {
            return;
        }
        EntityType<?> entityType = RonGuardIllagerIntegrationValues.resolveSourceEntityType();
        if (entityType == null) {
            RonGolemHealerIntegrationMod.LOGGER.warn("Could not resolve source Guard Illager entity type {}", (Object)RonGuardIllagerIntegrationValues.SOURCE_ENTITY_ID);
            return;
        }
        placement.produceUnit((ServerLevel)level, (EntityType)entityType, placement.ownerName, true);
    }
}

