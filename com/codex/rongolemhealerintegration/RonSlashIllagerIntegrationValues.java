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

public final class RonSlashIllagerIntegrationValues {
    public static final String DISPLAY_NAME = "Blade Master";
    public static final ResourceLocation SOURCE_ENTITY_ID = new ResourceLocation("slash_illager", "blade_master");
    public static final ResourceLocation ICON_TEXTURE = new ResourceLocation("ron_golem_healer_integration", "textures/icons/blade_master.png");
    public static final float MAX_HEALTH = 50.0f;
    public static final float MOVEMENT_SPEED = 0.3f;
    public static final float ATTACK_DAMAGE = 3.0f;
    public static final float ARMOR = 8.0f;
    public static final int POPULATION_COST = 2;
    public static final ResourceCost RESOURCE_COST = ResourceCost.Unit((int)0, (int)70, (int)160, (int)24, (int)2);

    private RonSlashIllagerIntegrationValues() {
    }

    public static EntityType<?> resolveSourceEntityType() {
        return (EntityType)ForgeRegistries.ENTITY_TYPES.getValue(SOURCE_ENTITY_ID);
    }
}

