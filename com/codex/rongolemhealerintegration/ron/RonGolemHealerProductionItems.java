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
import com.codex.rongolemhealerintegration.ron.GolemHealerProd;
import com.codex.rongolemhealerintegration.ron.ResearchChemicalX;
import com.solegendary.reignofnether.api.ReignOfNetherRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public final class RonGolemHealerProductionItems {
    public static final ResourceLocation GOLEM_HEALER_ID = new ResourceLocation("ron_golem_healer_integration", "golem_healer");
    public static final GolemHealerProd GOLEM_HEALER = new GolemHealerProd();

    public static final ResourceLocation CHEMICAL_X_RESEARCH_ID = new ResourceLocation("ron_golem_healer_integration", "research_chemical_x");
    public static final ResearchChemicalX RESEARCH_CHEMICAL_X = new ResearchChemicalX();

    public static final ResourceLocation WITHER_BOSS_ID = new ResourceLocation("ron_golem_healer_integration", "wither_boss");
    public static final WitherBossProd WITHER_BOSS = new WitherBossProd();

    public static final ResourceLocation FIELD_CANNON_ID = new ResourceLocation("ron_golem_healer_integration", "field_cannon");
    public static final FieldCannonProd FIELD_CANNON = new FieldCannonProd();

    private RonGolemHealerProductionItems() {
    }

    public static void register() {
        if (!ReignOfNetherRegistries.PRODUCTION_ITEM.m_7804_(GOLEM_HEALER_ID)) {
            Registry.m_122965_((Registry)ReignOfNetherRegistries.PRODUCTION_ITEM, (ResourceLocation)GOLEM_HEALER_ID, (Object)((Object)GOLEM_HEALER));
            RonGolemHealerIntegrationMod.LOGGER.info("Registered RoN production item {}", (Object)GOLEM_HEALER_ID);
        }
        if (!ReignOfNetherRegistries.PRODUCTION_ITEM.m_7804_(CHEMICAL_X_RESEARCH_ID)) {
            Registry.m_122965_((Registry)ReignOfNetherRegistries.PRODUCTION_ITEM, (ResourceLocation)CHEMICAL_X_RESEARCH_ID, (Object)RESEARCH_CHEMICAL_X);
            RonGolemHealerIntegrationMod.LOGGER.info("Registered RoN production item {}", (Object)CHEMICAL_X_RESEARCH_ID);
        }
        if (!ReignOfNetherRegistries.PRODUCTION_ITEM.m_7804_(WITHER_BOSS_ID)) {
            Registry.m_122965_((Registry)ReignOfNetherRegistries.PRODUCTION_ITEM, (ResourceLocation)WITHER_BOSS_ID, (Object)WITHER_BOSS);
            RonGolemHealerIntegrationMod.LOGGER.info("Registered RoN production item {}", (Object)WITHER_BOSS_ID);
        }
        if (!ReignOfNetherRegistries.PRODUCTION_ITEM.m_7804_(FIELD_CANNON_ID)) {
            Registry.m_122965_((Registry)ReignOfNetherRegistries.PRODUCTION_ITEM, (ResourceLocation)FIELD_CANNON_ID, (Object)FIELD_CANNON);
            RonGolemHealerIntegrationMod.LOGGER.info("Registered RoN production item {}", (Object)FIELD_CANNON_ID);
        }
    }
}

