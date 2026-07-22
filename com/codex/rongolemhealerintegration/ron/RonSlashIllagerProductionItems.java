/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.solegendary.reignofnether.api.ReignOfNetherRegistries
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceLocation
 */
package com.codex.rongolemhealerintegration.ron;

import com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod;
import com.codex.rongolemhealerintegration.ron.BladeMasterProd;
import com.solegendary.reignofnether.api.ReignOfNetherRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public final class RonSlashIllagerProductionItems {
    public static final ResourceLocation BLADE_MASTER_ID = new ResourceLocation("ron_golem_healer_integration", "blade_master");
    public static final BladeMasterProd BLADE_MASTER = new BladeMasterProd();

    private RonSlashIllagerProductionItems() {
    }

    public static void register() {
        if (ReignOfNetherRegistries.PRODUCTION_ITEM.m_7804_(BLADE_MASTER_ID)) {
            return;
        }
        Registry.m_122965_((Registry)ReignOfNetherRegistries.PRODUCTION_ITEM, (ResourceLocation)BLADE_MASTER_ID, (Object)((Object)BLADE_MASTER));
        RonGolemHealerIntegrationMod.LOGGER.info("Registered RoN production item {}", (Object)BLADE_MASTER_ID);
    }
}

