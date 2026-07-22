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
import com.codex.rongolemhealerintegration.ron.GuardIllagerProd;
import com.solegendary.reignofnether.api.ReignOfNetherRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public final class RonGuardIllagerProductionItems {
    public static final ResourceLocation GUARD_ILLAGER_ID = new ResourceLocation("ron_golem_healer_integration", "guard_illager");
    public static final GuardIllagerProd GUARD_ILLAGER = new GuardIllagerProd();

    private RonGuardIllagerProductionItems() {
    }

    public static void register() {
        if (ReignOfNetherRegistries.PRODUCTION_ITEM.m_7804_(GUARD_ILLAGER_ID)) {
            return;
        }
        Registry.m_122965_((Registry)ReignOfNetherRegistries.PRODUCTION_ITEM, (ResourceLocation)GUARD_ILLAGER_ID, (Object)((Object)GUARD_ILLAGER));
        RonGolemHealerIntegrationMod.LOGGER.info("Registered RoN production item {}", (Object)GUARD_ILLAGER_ID);
    }
}

