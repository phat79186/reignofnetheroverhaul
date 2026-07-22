package com.codex.rongolemhealerintegration.client;

import com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod;
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
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.client.renderer.patched.entity.PIronGolemRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.client.renderer.patched.entity.PVindicatorRenderer;
import yesman.epicfight.client.renderer.patched.entity.PWitchRenderer;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;
import yesman.epicfight.client.renderer.patched.entity.PDrownedRenderer;
import yesman.epicfight.client.renderer.patched.entity.PStrayRenderer;
import yesman.epicfight.client.renderer.patched.entity.PCreeperRenderer;

@Mod.EventBusSubscriber(modid="ron_golem_healer_integration", bus=Mod.EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
public final class GolemHealerClientHooks {
    private GolemHealerClientHooks() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RonGolemHealerIntegrationMod.LOGGER.info("Reusing the original Villager Golem Healer renderer, model, texture, sounds and GeckoLib animation setup");
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        RonGolemHealerIntegrationMod.LOGGER.info("No renderer override registered for Golem Healer because the integration keeps the original entity type and renderer");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @SubscribeEvent
    public static void onRegisterPatchedRenderers(PatchedRenderersEvent.Add add) {
        RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug] onRegisterPatchedRenderers called via GolemHealerClientHooks!");
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

            EntityType<?> entityType3 = RonSlashIllagerIntegrationValues.resolveSourceEntityType();
            RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug] resolved Blade Master EntityType: " + entityType3);
            if (entityType3 != null) {
                add.addPatchedEntityRenderer(entityType3, entityType -> {
                    RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug] Creating PBladeMasterRenderer for: " + entityType);
                    return new PBladeMasterRenderer(add.getContext(), (EntityType<?>)entityType).initLayerLast(add.getContext(), (EntityType<?>)entityType);
                });
                RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug] Blade Master Patched Entity Renderer added successfully!");
            } else {
                RonGolemHealerIntegrationMod.LOGGER.warn("[RoN Debug] WARNING: Blade Master EntityType was null! Cannot patch renderer!");
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


