/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.solegendary.reignofnether.resources.ResourceCost
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 *  net.minecraftforge.registries.ForgeRegistries
 */
package com.codex.rongolemhealerintegration;

import com.solegendary.reignofnether.resources.ResourceCost;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public final class RonGolemHealerIntegrationValues {
    public static final String DISPLAY_NAME = "Golem Healer";
    public static final ResourceLocation SOURCE_ENTITY_ID = new ResourceLocation("villagergolemhealer", "villagergolemhealer");
    public static final ResourceLocation ICON_TEXTURE = new ResourceLocation("ron_golem_healer_integration", "textures/icons/golem_healer.png");
    public static final float MAX_HEALTH = 50.0f;
    public static final float MOVEMENT_SPEED = 0.3f;
    public static final float HEAL_RANGE = 16.0f;
    public static final int HEAL_COOLDOWN_TICKS = 40;
    public static final int POPULATION_COST = 2;
    public static final ResourceCost RESOURCE_COST = ResourceCost.Unit((int)0, (int)50, (int)120, (int)18, (int)2);

    private RonGolemHealerIntegrationValues() {
    }

    public static EntityType<?> resolveSourceEntityType() {
        return (EntityType)ForgeRegistries.ENTITY_TYPES.getValue(SOURCE_ENTITY_ID);
    }
}

