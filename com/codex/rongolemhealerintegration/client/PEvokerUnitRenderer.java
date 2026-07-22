/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.solegendary.reignofnether.unit.modelling.models.VillagerUnitModel
 *  com.solegendary.reignofnether.unit.units.villagers.EvokerUnit
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.MobRenderer
 *  net.minecraft.client.renderer.entity.layers.CustomHeadLayer
 *  net.minecraft.client.renderer.entity.layers.ItemInHandLayer
 *  net.minecraft.world.entity.EntityType
 *  yesman.epicfight.api.asset.AssetAccessor
 *  yesman.epicfight.client.mesh.HumanoidMesh
 *  yesman.epicfight.client.renderer.patched.layer.PatchedItemInHandLayer
 *  yesman.epicfight.world.capabilities.entitypatch.mob.EvokerPatch
 */
package com.codex.rongolemhealerintegration.client;

import com.solegendary.reignofnether.unit.modelling.models.VillagerUnitModel;
import com.solegendary.reignofnether.unit.units.villagers.EvokerUnit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.EntityType;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.client.renderer.patched.layer.PatchedHeadLayer;
import yesman.epicfight.client.renderer.patched.layer.PatchedItemInHandLayer;
import yesman.epicfight.world.capabilities.entitypatch.mob.EvokerPatch;

public class PEvokerUnitRenderer
extends PatchedLivingEntityRenderer<EvokerUnit, EvokerPatch<EvokerUnit>, VillagerUnitModel<EvokerUnit>, MobRenderer<EvokerUnit, VillagerUnitModel<EvokerUnit>>, HumanoidMesh> {
    public PEvokerUnitRenderer(EntityRendererProvider.Context context, EntityType<?> entityType) {
        super(context, entityType);
        this.addPatchedLayer(ItemInHandLayer.class, new PatchedItemInHandLayer());
        this.addPatchedLayer(CustomHeadLayer.class, new PatchedHeadLayer());
    }

    @Override
    public AssetAccessor<HumanoidMesh> getDefaultMesh() {
        return Meshes.ILLAGER;
    }
}

