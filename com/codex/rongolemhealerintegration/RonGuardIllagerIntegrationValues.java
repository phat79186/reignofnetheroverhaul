/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.solegendary.reignofnether.resources.ResourceCost
 *  com.solegendary.reignofnether.resources.ResourceCosts
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 *  net.minecraftforge.registries.ForgeRegistries
 */
package com.codex.rongolemhealerintegration;

import com.solegendary.reignofnether.resources.ResourceCost;
import com.solegendary.reignofnether.resources.ResourceCosts;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public final class RonGuardIllagerIntegrationValues {
    public static final String DISPLAY_NAME = "Guard Illager";
    public static final ResourceLocation SOURCE_ENTITY_ID = new ResourceLocation("guardillagers", "guard_illager");
    public static final ResourceLocation ICON_TEXTURE = new ResourceLocation("ron_golem_healer_integration", "textures/icons/guard_illager.png");
    public static final float MAX_HEALTH = 24.0f;
    public static final float MOVEMENT_SPEED = 0.348f;
    public static final float ATTACK_DAMAGE = 3.0f;
    public static final float ARMOR = 6.0f;
    public static final int POPULATION_COST = 1;
    public static final ResourceCost RESOURCE_COST = ResourceCosts.VINDICATOR;

    private RonGuardIllagerIntegrationValues() {
    }

    public static EntityType<?> resolveSourceEntityType() {
        return (EntityType)ForgeRegistries.ENTITY_TYPES.getValue(SOURCE_ENTITY_ID);
    }
}

