package com.codex.rongolemhealerintegration;

import com.solegendary.reignofnether.resources.ResourceCost;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public final class RonMutantSkeletonIntegrationValues {
    public static final String DISPLAY_NAME = "Mutant Skeleton";
    public static final ResourceLocation SOURCE_ENTITY_ID = new ResourceLocation("mutantmonsters", "mutant_skeleton");
    public static final ResourceLocation ICON_TEXTURE = new ResourceLocation("ron_golem_healer_integration", "textures/icons/mutant_skeleton.png");
    public static final float MAX_HEALTH = 150.0f;
    public static final float MOVEMENT_SPEED = 0.27f;
    public static final int POPULATION_COST = 4;
    public static final ResourceCost RESOURCE_COST = ResourceCost.Unit(0, 150, 300, 50, 4);

    private RonMutantSkeletonIntegrationValues() {
    }

    public static EntityType<?> resolveSourceEntityType() {
        return (EntityType<?>)ForgeRegistries.ENTITY_TYPES.getValue(SOURCE_ENTITY_ID);
    }
}
