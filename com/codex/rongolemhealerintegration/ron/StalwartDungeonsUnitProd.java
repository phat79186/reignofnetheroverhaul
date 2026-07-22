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

@SuppressWarnings({"rawtypes", "unchecked"})
public class StalwartDungeonsUnitProd extends ProductionItem {
    private final String itemName;
    private final ResourceLocation entityId;
    private final ResourceLocation icon;
    private final String translationKey;
    private final String tooltipKey;
    private final List<String> extraInfo;

    public StalwartDungeonsUnitProd(String itemName, ResourceLocation entityId, ResourceLocation icon, String translationKey, String tooltipKey, ResourceCost cost, List<String> extraInfo) {
        super(cost);
        this.itemName = itemName;
        this.entityId = entityId;
        this.icon = icon;
        this.translationKey = translationKey;
        this.tooltipKey = tooltipKey;
        this.extraInfo = extraInfo;
        this.onComplete = this::spawnUnit;
    }

    public String getItemName() {
        return this.itemName;
    }

    public UnitSpawnButton getPlaceButton() {
        return new UnitSpawnButton(this.itemName, this.icon, List.of(
            line(this.translationKey, true),
            line("", false),
            line(this.tooltipKey, false)
        ));
    }

    @Override
    public StartProductionButton getStartButton(ProductionPlacement placement, Keybinding hotkey) {
        java.util.ArrayList<FormattedCharSequence> lines = new java.util.ArrayList<>();
        lines.add(line(this.translationKey, true));
        lines.add(ResourceCosts.getFormattedCost(this.defaultCost));
        lines.add(ResourceCosts.getFormattedPopAndTime(this.defaultCost));
        for (String info : this.extraInfo) {
            lines.add(literalLine(info, false));
        }
        lines.add(line("", false));
        lines.add(line(this.tooltipKey, false));
        return new StartProductionButton(this.itemName, this.icon, hotkey, () -> false, () -> true, lines, this);
    }

    @Override
    public StopProductionButton getCancelButton(ProductionPlacement placement, boolean first) {
        return new StopProductionButton(this.itemName, this.icon, placement, this, first);
    }

    private static FormattedCharSequence line(String key, boolean bold) {
        return componentLine(key.isEmpty() ? Component.m_237119_() : Component.m_237115_(key), bold);
    }

    private static FormattedCharSequence literalLine(String text, boolean bold) {
        return componentLine(Component.m_237113_(text), bold);
    }

    private static FormattedCharSequence componentLine(Component component, boolean bold) {
        return component.m_6881_().m_130948_(bold ? Style.f_131099_.m_131136_(true) : Style.f_131099_).m_7532_();
    }

    private void spawnUnit(Level level, ProductionPlacement placement) {
        if (level.m_5776_()) {
            return;
        }
        EntityType entityType = ForgeRegistries.ENTITY_TYPES.getValue(this.entityId);
        if (entityType == null) {
            return;
        }
        placement.produceUnit((ServerLevel) level, entityType, placement.ownerName, true);
    }
}
