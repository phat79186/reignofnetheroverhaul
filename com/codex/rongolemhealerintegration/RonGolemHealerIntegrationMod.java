/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  net.minecraftforge.fml.common.Mod
 *  org.slf4j.Logger
 */
package com.codex.rongolemhealerintegration;

import com.codex.rongolemhealerintegration.ron.RonGolemHealerProductionItems;
import com.codex.rongolemhealerintegration.ron.RonGuardIllagerProductionItems;
import com.codex.rongolemhealerintegration.ron.RonSlashIllagerProductionItems;
import org.slf4j.LoggerFactory;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(value="ron_golem_healer_integration")
public final class RonGolemHealerIntegrationMod {
    public static final String MODID = "ron_golem_healer_integration";
    public static final Logger LOGGER = LoggerFactory.getLogger(RonGolemHealerIntegrationMod.class);

    public RonGolemHealerIntegrationMod() {
        RonSlashIllagerProductionItems.register();
        RonGuardIllagerProductionItems.register();
        RonGolemHealerProductionItems.register();
        com.codex.rongolemhealerintegration.ron.RonStalwartDungeonsProductionItems.register();
        
        // Set Zombie Piglin cost fields to 25 food
        com.solegendary.reignofnether.resources.ResourceCosts.ZOMBIE_PIGLIN.food = 25;
        com.solegendary.reignofnether.resources.ResourceCosts.ZOMBIE_PIGLIN.wood = 0;
        com.solegendary.reignofnether.resources.ResourceCosts.ZOMBIE_PIGLIN.ore = 0;
        com.solegendary.reignofnether.resources.ResourceCosts.ZOMBIE_PIGLIN.ticks = 10 * 20; // 10 seconds
        com.solegendary.reignofnether.resources.ResourceCosts.ZOMBIE_PIGLIN.population = 1;
        
        // Add productions programmatically to PortalMilitary
        com.solegendary.reignofnether.building.Buildings.PORTAL_MILITARY.productions.add(
            com.codex.rongolemhealerintegration.ron.RonStalwartDungeonsProductionItems.AWFUL_GHAST, 
            com.solegendary.reignofnether.keybinds.Keybindings.abilitySlot9
        );
        com.solegendary.reignofnether.building.Buildings.PORTAL_MILITARY.productions.add(
            com.codex.rongolemhealerintegration.ron.RonStalwartDungeonsProductionItems.NETHER_KEEPER, 
            com.solegendary.reignofnether.keybinds.Keybindings.abilitySlot10
        );
        com.solegendary.reignofnether.building.Buildings.PORTAL_MILITARY.productions.add(
            com.codex.rongolemhealerintegration.ron.RonStalwartDungeonsProductionItems.GIDDY_BLAZE, 
            com.solegendary.reignofnether.keybinds.Keybindings.hotkey1
        );
        com.solegendary.reignofnether.building.Buildings.PORTAL_MILITARY.productions.add(
            com.codex.rongolemhealerintegration.ron.RonStalwartDungeonsProductionItems.INCOMPLETE_WITHER, 
            com.solegendary.reignofnether.keybinds.Keybindings.hotkey2
        );
        com.solegendary.reignofnether.building.Buildings.PORTAL_MILITARY.productions.add(
            com.codex.rongolemhealerintegration.ron.RonStalwartDungeonsProductionItems.REINFORCED_BLAZE, 
            com.solegendary.reignofnether.keybinds.Keybindings.minimapToggle
        );
        com.solegendary.reignofnether.building.Buildings.PORTAL_MILITARY.productions.add(
            com.solegendary.reignofnether.building.production.ProductionItems.ZOMBIE_PIGLIN, 
            com.solegendary.reignofnether.keybinds.Keybindings.hotkey7
        );
        com.solegendary.reignofnether.building.Buildings.PORTAL_MILITARY.productions.add(
            com.codex.rongolemhealerintegration.ron.RonGolemHealerProductionItems.WITHER_BOSS, 
            com.solegendary.reignofnether.keybinds.Keybindings.hotkey6
        );

        // Add productions programmatically to EndPortal
        com.solegendary.reignofnether.building.Buildings.END_PORTAL.productions.add(
            com.codex.rongolemhealerintegration.ron.RonStalwartDungeonsProductionItems.PROPULK, 
            com.solegendary.reignofnether.keybinds.Keybindings.abilitySlot2
        );
        com.solegendary.reignofnether.building.Buildings.END_PORTAL.productions.add(
            com.codex.rongolemhealerintegration.ron.RonStalwartDungeonsProductionItems.SHULKER_CANNON, 
            com.solegendary.reignofnether.keybinds.Keybindings.abilitySlot3
        );

        LOGGER.info("RoN Golem Healer Integration loaded");
    }
}


