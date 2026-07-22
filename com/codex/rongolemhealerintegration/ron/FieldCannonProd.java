package com.codex.rongolemhealerintegration.ron;

import com.solegendary.reignofnether.building.buildings.placements.ProductionPlacement;
import com.solegendary.reignofnether.building.production.ProductionItem;
import com.solegendary.reignofnether.building.production.StartProductionButton;
import com.solegendary.reignofnether.building.production.StopProductionButton;
import com.solegendary.reignofnether.hud.buttons.UnitSpawnButton;
import com.solegendary.reignofnether.keybinds.Keybinding;
import com.solegendary.reignofnether.resources.ResourceCost;
import com.solegendary.reignofnether.resources.ResourceCosts;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;

public final class FieldCannonProd extends ProductionItem {
    public static final String ITEM_NAME = "Field Cannon";
    public static final ResourceLocation ICON_TEXTURE = new ResourceLocation("ron_golem_healer_integration", "textures/icons/field_cannon.png");
    public static final ResourceCost COST = ResourceCost.Unit(0, 200, 300, 30, 4);

    public FieldCannonProd() {
        super(COST);
        this.onComplete = FieldCannonProd::spawnFieldCannon;
    }

    public String getItemName() {
        return ITEM_NAME;
    }

    public UnitSpawnButton getPlaceButton() {
        return new UnitSpawnButton(ITEM_NAME, ICON_TEXTURE, List.of(
            FieldCannonProd.line("Field Cannon", true),
            FieldCannonProd.line("", false),
            FieldCannonProd.line("A powerful mobile artillery unit.", false),
            FieldCannonProd.line("Can be driven by any allied unit.", false)
        ));
    }

    public StartProductionButton getStartButton(ProductionPlacement placement, Keybinding hotkey) {
        return new StartProductionButton(ITEM_NAME, ICON_TEXTURE, hotkey, () -> false, () -> true, List.of(
            FieldCannonProd.line("Field Cannon", true),
            ResourceCosts.getFormattedCost(COST),
            ResourceCosts.getFormattedPopAndTime(COST),
            FieldCannonProd.literalLine("HP: Default", false),
            FieldCannonProd.literalLine("Explosive Cannonball: 100 DMG", false),
            FieldCannonProd.literalLine("Self-Reloading", false),
            FieldCannonProd.line("", false),
            FieldCannonProd.line("A powerful mobile artillery unit.", false),
            FieldCannonProd.line("Can be driven by any allied unit.", false)
        ), this);
    }

    public StopProductionButton getCancelButton(ProductionPlacement placement, boolean first) {
        return new StopProductionButton(ITEM_NAME, ICON_TEXTURE, placement, this, first);
    }

    private static FormattedCharSequence line(String key, boolean bold) {
        return FieldCannonProd.componentLine(key.isEmpty() ? Component.m_237119_() : Component.m_237115_(key), bold);
    }

    private static FormattedCharSequence literalLine(String text, boolean bold) {
        return FieldCannonProd.componentLine(Component.m_237113_(text), bold);
    }

    private static FormattedCharSequence componentLine(Component component, boolean bold) {
        return component.m_6881_().m_130948_(bold ? Style.f_131099_.m_131136_(true) : Style.f_131099_).m_7532_();
    }

    private static void spawnFieldCannon(Level level, ProductionPlacement placement) {
        if (level.m_5776_()) {
            return;
        }
        ServerLevel serverLevel = (ServerLevel) level;
        BlockPos spawnPoint = placement.getDefaultOutdoorSpawnPoint();

        Entity cannon = ((EntityType<?>) com.camdenscottc.colonialcannons.init.ModEntities.FIELD_CANNON.get()).m_262455_(
            serverLevel,
            null,
            null,
            spawnPoint,
            net.minecraft.world.entity.MobSpawnType.SPAWNER,
            true,
            false
        );

        if (cannon instanceof FieldCannonEntityAccess) {
            ((FieldCannonEntityAccess) cannon).ron$setOwnerName(placement.ownerName);
        }

        Entity driver = placement.produceUnit(
            serverLevel,
            (EntityType) com.solegendary.reignofnether.registrars.EntityRegistrar.MILITIA_UNIT.get(),
            placement.ownerName,
            true
        );

        if (driver != null) {
            driver.m_20049_("no_villager_convert");
            if (cannon != null) {
                driver.m_20329_(cannon);
            }
        }
    }
}
