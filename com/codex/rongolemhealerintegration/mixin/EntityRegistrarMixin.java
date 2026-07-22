/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.solegendary.reignofnether.registrars.EntityRegistrar
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.Mob
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.codex.rongolemhealerintegration.mixin;

import com.codex.rongolemhealerintegration.RonGolemHealerIntegrationValues;
import com.codex.rongolemhealerintegration.RonGuardIllagerIntegrationValues;
import com.codex.rongolemhealerintegration.RonSlashIllagerIntegrationValues;
import com.codex.rongolemhealerintegration.RonMutantZombieIntegrationValues;
import com.codex.rongolemhealerintegration.RonMutantSkeletonIntegrationValues;
import com.codex.rongolemhealerintegration.RonMutantCreeperIntegrationValues;
import com.solegendary.reignofnether.registrars.EntityRegistrar;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EntityRegistrar.class}, remap=false)
public abstract class EntityRegistrarMixin {
    @Inject(method={"getEntityType"}, at={@At(value="HEAD")}, cancellable=true, remap=false)
    private static void ronGolemHealerIntegration$mapGolemHealer(String unitName, CallbackInfoReturnable<EntityType<? extends Mob>> cir) {
        EntityType<?> entityType = null;
        if ("Golem Healer".equals(unitName)) {
            entityType = RonGolemHealerIntegrationValues.resolveSourceEntityType();
        } else if ("Guard Illager".equals(unitName)) {
            entityType = RonGuardIllagerIntegrationValues.resolveSourceEntityType();
        } else if ("Blade Master".equals(unitName)) {
            entityType = RonSlashIllagerIntegrationValues.resolveSourceEntityType();
        } else if ("Mutant Zombie".equals(unitName)) {
            entityType = RonMutantZombieIntegrationValues.resolveSourceEntityType();
        } else if ("Mutant Skeleton".equals(unitName)) {
            entityType = RonMutantSkeletonIntegrationValues.resolveSourceEntityType();
        } else if ("Mutant Creeper".equals(unitName)) {
            entityType = RonMutantCreeperIntegrationValues.resolveSourceEntityType();
        } else if ("Awful Ghast".equals(unitName)) {
            entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("stalwart_dungeons", "awful_ghast"));
        } else if ("Nether Keeper".equals(unitName)) {
            entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("stalwart_dungeons", "nether_keeper"));
        } else if ("Giddy Blaze".equals(unitName)) {
            entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("stalwart_dungeons", "giddy_blaze"));
        } else if ("Incomplete Wither".equals(unitName)) {
            entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("stalwart_dungeons", "incomplete_wither"));
        } else if ("Reinforced Blaze".equals(unitName)) {
            entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("stalwart_dungeons", "reinforced_blaze"));
        } else if ("Propulk".equals(unitName)) {
            entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("stalwart_dungeons", "propulk"));
        } else if ("Shelterer".equals(unitName)) {
            entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("stalwart_dungeons", "shelterer"));
        } else if ("Shelterer Without Armor".equals(unitName)) {
            entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("stalwart_dungeons", "shelterer_without_armor"));
        } else if ("Shulker Cannon".equals(unitName)) {
            entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("stalwart_dungeons", "shulker_cannon"));
        } else if ("Wither Boss".equals(unitName)) {
            entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("minecraft", "wither"));
        }
        if (entityType == null) {
            return;
        }
        cir.setReturnValue((EntityType<? extends Mob>) entityType);
    }
}

