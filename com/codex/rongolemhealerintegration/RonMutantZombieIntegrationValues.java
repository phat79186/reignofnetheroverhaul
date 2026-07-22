package com.codex.rongolemhealerintegration;

import com.solegendary.reignofnether.resources.ResourceCost;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public final class RonMutantZombieIntegrationValues {
    public static final String DISPLAY_NAME = "Mutant Zombie";
    public static final ResourceLocation SOURCE_ENTITY_ID = new ResourceLocation("mutantmonsters", "mutant_zombie");
    public static final ResourceLocation ICON_TEXTURE = new ResourceLocation("ron_golem_healer_integration", "textures/icons/mutant_zombie.png");
    public static final float MAX_HEALTH = 150.0f;
    public static final float MOVEMENT_SPEED = 0.26f;
    public static final int POPULATION_COST = 4;
    public static final ResourceCost RESOURCE_COST = ResourceCost.Unit(0, 150, 300, 50, 4);

    private RonMutantZombieIntegrationValues() {
    }

    public static EntityType<?> resolveSourceEntityType() {
        return (EntityType<?>)ForgeRegistries.ENTITY_TYPES.getValue(SOURCE_ENTITY_ID);
    }
}
