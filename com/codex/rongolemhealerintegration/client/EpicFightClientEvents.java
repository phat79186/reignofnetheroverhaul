/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.solegendary.reignofnether.registrars.EntityRegistrar
 *  com.solegendary.reignofnether.unit.modelling.layers.VillagerUnitArmorLayer
 *  net.minecraft.world.entity.EntityType
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  yesman.epicfight.api.asset.AssetAccessor
 *  yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent$Add
 *  yesman.epicfight.client.mesh.HumanoidMesh
 *  yesman.epicfight.client.renderer.patched.entity.PIronGolemRenderer
 */
package com.codex.rongolemhealerintegration.client;

import com.codex.rongolemhealerintegration.RonGuardIllagerIntegrationValues;
import com.codex.rongolemhealerintegration.RonSlashIllagerIntegrationValues;
import com.codex.rongolemhealerintegration.client.PBladeMasterRenderer;
import com.codex.rongolemhealerintegration.client.PEvokerUnitRenderer;
import com.codex.rongolemhealerintegration.client.PGuardIllagerRenderer;
import com.codex.rongolemhealerintegration.client.PatchedVillagerUnitArmorLayer;
import com.solegendary.reignofnether.registrars.EntityRegistrar;
import com.solegendary.reignofnether.unit.modelling.layers.VillagerUnitArmorLayer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.client.renderer.patched.entity.PIronGolemRenderer;
import yesman.epicfight.client.renderer.patched.entity.PIllagerRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.client.renderer.patched.entity.PVindicatorRenderer;
import yesman.epicfight.client.renderer.patched.entity.PWitchRenderer;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;
import yesman.epicfight.client.renderer.patched.entity.PDrownedRenderer;
import yesman.epicfight.client.renderer.patched.entity.PStrayRenderer;
import yesman.epicfight.client.renderer.patched.entity.PCreeperRenderer;
import yesman.epicfight.client.renderer.patched.entity.PRavagerRenderer;
import net.minecraftforge.api.distmarker.Dist;

public class EpicFightClientEvents {
    static {
        com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug] EpicFightClientEvents class loaded!");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @SubscribeEvent
    public static void onRegisterPatchedRenderers(PatchedRenderersEvent.Add add) {
        com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug] onRegisterPatchedRenderers called!");
        try {
            EntityType<?> entityType2;
            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.IRON_GOLEM_UNIT.get(), entityType -> new PIronGolemRenderer(add.getContext(), entityType).initLayerLast(add.getContext(), entityType));
            
            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.VINDICATOR_UNIT.get(), entityType -> {
                PVindicatorRenderer pVindicatorRenderer = new PVindicatorRenderer(add.getContext(), (EntityType<?>)entityType);
                ((PatchedLivingEntityRenderer)pVindicatorRenderer).addPatchedLayerAlways(VillagerUnitArmorLayer.class, new PatchedVillagerUnitArmorLayer((AssetAccessor<HumanoidMesh>)Meshes.BIPED, false, add.getContext().m_266367_()));
                return pVindicatorRenderer.initLayerLast(add.getContext(), (EntityType<?>)entityType);
            });

            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.WITCH_UNIT.get(), entity -> new PWitchRenderer(add.getContext(), (EntityType<?>)entity).initLayerLast(add.getContext(), (EntityType<?>)entity));
            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.EVOKER_UNIT.get(), entityType -> new PEvokerUnitRenderer(add.getContext(), (EntityType<?>)entityType).initLayerLast(add.getContext(), (EntityType<?>)entityType));

            // Zombie-like unit renderers
            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.ZOMBIE_UNIT.get(), entityType -> new PHumanoidRenderer((AssetAccessor)Meshes.BIPED_OLD_TEX, add.getContext(), (EntityType<?>)entityType).initLayerLast(add.getContext(), (EntityType<?>)entityType));
            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.HUSK_UNIT.get(), entityType -> new PHumanoidRenderer((AssetAccessor)Meshes.BIPED_OLD_TEX, add.getContext(), (EntityType<?>)entityType).initLayerLast(add.getContext(), (EntityType<?>)entityType));
            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.DROWNED_UNIT.get(), entityType -> new PDrownedRenderer(add.getContext(), (EntityType<?>)entityType).initLayerLast(add.getContext(), (EntityType<?>)entityType));

            // Skeleton-like unit renderers
            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.SKELETON_UNIT.get(), entityType -> new PHumanoidRenderer((AssetAccessor)Meshes.SKELETON, add.getContext(), (EntityType<?>)entityType).initLayerLast(add.getContext(), (EntityType<?>)entityType));
            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.STRAY_UNIT.get(), entityType -> new PStrayRenderer(add.getContext(), (EntityType<?>)entityType).initLayerLast(add.getContext(), (EntityType<?>)entityType));
            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.BOGGED_UNIT.get(), entityType -> new PHumanoidRenderer((AssetAccessor)Meshes.SKELETON, add.getContext(), (EntityType<?>)entityType).initLayerLast(add.getContext(), (EntityType<?>)entityType));
            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.WITHER_SKELETON_UNIT.get(), entityType -> new PHumanoidRenderer((AssetAccessor)Meshes.SKELETON, add.getContext(), (EntityType<?>)entityType).initLayerLast(add.getContext(), (EntityType<?>)entityType));

            // Creeper-like unit renderer
            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.CREEPER_UNIT.get(), entityType -> new PCreeperRenderer(add.getContext(), (EntityType<?>)entityType).initLayerLast(add.getContext(), (EntityType<?>)entityType));

            // Piglin faction humanoid unit renderers (excluding HeadhunterUnit, heroes, and mod units)
            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.GRUNT_UNIT.get(), entityType -> new PHumanoidRenderer((AssetAccessor)Meshes.PIGLIN, add.getContext(), (EntityType<?>)entityType).initLayerLast(add.getContext(), (EntityType<?>)entityType));
            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.BRUTE_UNIT.get(), entityType -> new PHumanoidRenderer((AssetAccessor)Meshes.PIGLIN, add.getContext(), (EntityType<?>)entityType).initLayerLast(add.getContext(), (EntityType<?>)entityType));
            add.addPatchedEntityRenderer((EntityType)EntityRegistrar.MARAUDER_UNIT.get(), entityType -> new PHumanoidRenderer((AssetAccessor)Meshes.PIGLIN, add.getContext(), (EntityType<?>)entityType).initLayerLast(add.getContext(), (EntityType<?>)entityType));
            // ZombiePiglinUnit disabled


            EntityType<?> entityType3 = RonSlashIllagerIntegrationValues.resolveSourceEntityType();
            com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug] resolved Blade Master EntityType: " + entityType3);
            if (entityType3 != null) {
                add.addPatchedEntityRenderer(entityType3, entityType -> {
                    com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug] Creating PBladeMasterRenderer for: " + entityType);
                    return new PBladeMasterRenderer(add.getContext(), (EntityType<?>)entityType).initLayerLast(add.getContext(), (EntityType<?>)entityType);
                });
                com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug] Blade Master Patched Entity Renderer added successfully!");
            } else {
                com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod.LOGGER.warn("[RoN Debug] WARNING: Blade Master EntityType was null! Cannot patch renderer!");
            }

            if ((entityType2 = RonGuardIllagerIntegrationValues.resolveSourceEntityType()) != null) {
                add.addPatchedEntityRenderer(entityType2, entityType -> {
                    PGuardIllagerRenderer pGuardIllagerRenderer = new PGuardIllagerRenderer(add.getContext(), (EntityType<?>)entityType);
                    ((PatchedLivingEntityRenderer)pGuardIllagerRenderer).addPatchedLayerAlways(VillagerUnitArmorLayer.class, new PatchedVillagerUnitArmorLayer((AssetAccessor<HumanoidMesh>)Meshes.BIPED, false, add.getContext().m_266367_()));
                    return pGuardIllagerRenderer.initLayerLast(add.getContext(), (EntityType<?>)entityType);
                });
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

