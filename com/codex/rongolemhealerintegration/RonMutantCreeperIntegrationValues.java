package com.codex.rongolemhealerintegration;

import com.solegendary.reignofnether.resources.ResourceCost;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public final class RonMutantCreeperIntegrationValues {
    public static final String DISPLAY_NAME = "Mutant Creeper";
    public static final ResourceLocation SOURCE_ENTITY_ID = new ResourceLocation("mutantmonsters", "mutant_creeper");
    public static final ResourceLocation ICON_TEXTURE = new ResourceLocation("ron_golem_healer_integration", "textures/icons/mutant_creeper.png");
    public static final float MAX_HEALTH = 120.0f;
    public static final float MOVEMENT_SPEED = 0.26f;
    public static final int POPULATION_COST = 3;
    public static final ResourceCost RESOURCE_COST = ResourceCost.Unit(0, 100, 200, 35, 3);

    private RonMutantCreeperIntegrationValues() {
    }

    public static EntityType<?> resolveSourceEntityType() {
        return (EntityType<?>)ForgeRegistries.ENTITY_TYPES.getValue(SOURCE_ENTITY_ID);
    }
}
