/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.solegendary.reignofnether.unit.modelling.layers.VillagerUnitArmorLayer
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.Model
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.Sheets
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.client.renderer.texture.TextureAtlas
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.ModelManager
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlot$Type
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorMaterial
 *  net.minecraft.world.item.DyeableLeatherItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.armortrim.ArmorTrim
 *  net.minecraftforge.client.ForgeHooksClient
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.Event
 *  net.minecraftforge.registries.ForgeRegistries
 *  yesman.epicfight.api.asset.AssetAccessor
 *  yesman.epicfight.api.asset.JsonAssetLoader
 *  yesman.epicfight.api.client.forgeevent.AnimatedArmorTextureEvent
 *  yesman.epicfight.api.client.model.Mesh$DrawingFunction
 *  yesman.epicfight.api.client.model.SkinnedMesh
 *  yesman.epicfight.api.exception.AssetLoadingException
 *  yesman.epicfight.api.model.Armature
 *  yesman.epicfight.api.utils.math.OpenMatrix4f
 *  yesman.epicfight.client.ClientEngine
 *  yesman.epicfight.client.mesh.HumanoidMesh
 *  yesman.epicfight.client.renderer.EpicFightRenderTypes
 *  yesman.epicfight.client.renderer.patched.layer.ModelRenderLayer
 */
package com.codex.rongolemhealerintegration.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.solegendary.reignofnether.unit.modelling.layers.VillagerUnitArmorLayer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.asset.JsonAssetLoader;
import yesman.epicfight.api.client.forgeevent.AnimatedArmorTextureEvent;
import yesman.epicfight.api.client.model.Mesh;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.api.client.model.transformer.HumanoidModelBaker;
import yesman.epicfight.api.exception.AssetLoadingException;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;
import yesman.epicfight.client.renderer.patched.layer.ModelRenderLayer;
import yesman.epicfight.client.renderer.patched.layer.WearableItemLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class PatchedVillagerUnitArmorLayer
extends ModelRenderLayer<LivingEntity, LivingEntityPatch<LivingEntity>, EntityModel<LivingEntity>, RenderLayer<LivingEntity, EntityModel<LivingEntity>>, HumanoidMesh> {
    private final boolean firstPersonModel;
    private final TextureAtlas armorTrimAtlas;
    private static final Map<String, ResourceLocation> EPICFIGHT_OVERRIDING_TEXTURES = new HashMap<String, ResourceLocation>();
    private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = new HashMap<String, ResourceLocation>();

    public PatchedVillagerUnitArmorLayer(AssetAccessor<HumanoidMesh> assetAccessor, boolean bl, ModelManager modelManager) {
        super(assetAccessor);
        this.firstPersonModel = bl;
        this.armorTrimAtlas = modelManager.m_119428_(Sheets.f_265912_);
    }

    private static boolean innerModel(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.LEGS;
    }

    private static boolean shouldRiderSit(Entity entity) {
        try {
            Method method = Entity.class.getMethod("shouldRiderSit", new Class[0]);
            return (Boolean)method.invoke(entity, new Object[0]);
        }
        catch (Exception exception) {
            return true;
        }
    }

    protected void renderLayer(LivingEntityPatch<LivingEntity> livingEntityPatch, LivingEntity livingEntity, RenderLayer<LivingEntity, EntityModel<LivingEntity>> renderLayer, PoseStack poseStack, MultiBufferSource multiBufferSource, int n, OpenMatrix4f[] openMatrix4fArray, float f, float f2, float f3, float f4) {
        if (!(renderLayer instanceof VillagerUnitArmorLayer)) {
            return;
        }
        VillagerUnitArmorLayer villagerUnitArmorLayer = (VillagerUnitArmorLayer)renderLayer;
        HumanoidModel humanoidModel = null;
        HumanoidModel humanoidModel2 = null;
        try {
            EquipmentSlot[] equipmentSlotArray = VillagerUnitArmorLayer.class.getDeclaredField("innerModel");
            equipmentSlotArray.setAccessible(true);
            humanoidModel = (HumanoidModel)equipmentSlotArray.get(villagerUnitArmorLayer);
            Field field = VillagerUnitArmorLayer.class.getDeclaredField("outerModel");
            field.setAccessible(true);
            humanoidModel2 = (HumanoidModel)field.get(villagerUnitArmorLayer);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
        if (humanoidModel == null || humanoidModel2 == null) {
            return;
        }
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            float f5;
            float f6;
            int n2;
            HumanoidModel humanoidModel3;
            Model model;
            HumanoidModel humanoidModel4;
            SkinnedMesh skinnedMesh;
            ArmorItem armorItem;
            Item item;
            ItemStack itemStack;
            if (equipmentSlot.m_20743_() != EquipmentSlot.Type.ARMOR || (itemStack = livingEntity.m_6844_(equipmentSlot)).m_41619_() || !((item = itemStack.m_41720_()) instanceof ArmorItem) || equipmentSlot != (armorItem = (ArmorItem)item).m_40402_()) continue;
            boolean bl = false;
            if (livingEntityPatch.isFirstPerson() && this.firstPersonModel) {
                if (equipmentSlot != EquipmentSlot.CHEST) continue;
                bl = true;
            }
            if (equipmentSlot == EquipmentSlot.HEAD && this.firstPersonModel) continue;
            poseStack.m_85836_();
            float f7 = 0.0f;
            if (equipmentSlot == EquipmentSlot.HEAD) {
                poseStack.m_85837_(0.0, (double)f7 * 0.055, 0.0);
            }
            if ((skinnedMesh = this.getArmorModel(villagerUnitArmorLayer, humanoidModel4 = PatchedVillagerUnitArmorLayer.innerModel(equipmentSlot) ? humanoidModel : humanoidModel2, model = ForgeHooksClient.getArmorModel((LivingEntity)livingEntity, (ItemStack)itemStack, (EquipmentSlot)equipmentSlot, (HumanoidModel)humanoidModel4), livingEntity, armorItem, itemStack, equipmentSlot)) == null) {
                poseStack.m_85849_();
                return;
            }
            if (model instanceof HumanoidModel) {
                humanoidModel3 = (HumanoidModel)model;
                n2 = livingEntity.m_20159_() && livingEntity.m_20202_() != null && PatchedVillagerUnitArmorLayer.shouldRiderSit(livingEntity.m_20202_()) ? 1 : 0;
                f6 = 0.0f;
                f5 = 0.0f;
                if (n2 == 0 && livingEntity.m_6084_()) {
                    f6 = livingEntity.f_267362_.m_267711_(f4);
                    f5 = livingEntity.f_267362_.m_267590_(f4);
                    if (livingEntity.m_6162_()) {
                        f5 *= 3.0f;
                    }
                    if (f6 > 1.0f) {
                        f6 = 1.0f;
                    }
                }
                try {
                    humanoidModel3.m_6973_(livingEntity, f6, f5, f, f2, f3);
                }
                catch (ClassCastException classCastException) {
                    // empty catch block
                }
                humanoidModel3.f_102808_.m_171322_(humanoidModel3.f_102808_.m_233566_());
                humanoidModel3.f_102809_.m_171322_(humanoidModel3.f_102809_.m_233566_());
                humanoidModel3.f_102810_.m_171322_(humanoidModel3.f_102810_.m_233566_());
                humanoidModel3.f_102812_.m_171322_(humanoidModel3.f_102812_.m_233566_());
                humanoidModel3.f_102811_.m_171322_(humanoidModel3.f_102811_.m_233566_());
                humanoidModel3.f_102814_.m_171322_(humanoidModel3.f_102814_.m_233566_());
                humanoidModel3.f_102813_.m_171322_(humanoidModel3.f_102813_.m_233566_());
            }
            skinnedMesh.initialize();
            if (bl) {
                skinnedMesh.getAllParts().forEach(skinnedMeshPart -> skinnedMeshPart.setHidden(true));
                if (skinnedMesh.hasPart("leftArm")) {
                    skinnedMesh.getPart("leftArm").setHidden(false);
                }
                if (skinnedMesh.hasPart("rightArm")) {
                    skinnedMesh.getPart("rightArm").setHidden(false);
                }
            }
            if (armorItem instanceof DyeableLeatherItem) {
                humanoidModel3 = (DyeableLeatherItem)armorItem;
                n2 = humanoidModel3.m_41121_(itemStack);
                f6 = (float)(n2 >> 16 & 0xFF) / 255.0f;
                f5 = (float)(n2 >> 8 & 0xFF) / 255.0f;
                float f8 = (float)(n2 & 0xFF) / 255.0f;
                this.renderArmor(poseStack, multiBufferSource, n, skinnedMesh, livingEntityPatch.getArmature(), f6, f5, f8, this.getArmorTexture(itemStack, livingEntity, skinnedMesh, equipmentSlot, null, humanoidModel4), openMatrix4fArray);
                this.renderArmor(poseStack, multiBufferSource, n, skinnedMesh, livingEntityPatch.getArmature(), 1.0f, 1.0f, 1.0f, this.getArmorTexture(itemStack, livingEntity, skinnedMesh, equipmentSlot, "overlay", humanoidModel4), openMatrix4fArray);
            } else {
                this.renderArmor(poseStack, multiBufferSource, n, skinnedMesh, livingEntityPatch.getArmature(), 1.0f, 1.0f, 1.0f, this.getArmorTexture(itemStack, livingEntity, skinnedMesh, equipmentSlot, null, humanoidModel4), openMatrix4fArray);
            }
            ArmorTrim.m_266285_((RegistryAccess)livingEntity.m_9236_().m_9598_(), (ItemStack)itemStack).ifPresent(armorTrim -> this.renderTrim(poseStack, multiBufferSource, n, skinnedMesh, livingEntityPatch.getArmature(), armorItem.m_40401_(), (ArmorTrim)armorTrim, equipmentSlot, openMatrix4fArray));
            if (itemStack.m_41790_()) {
                this.renderGlint(poseStack, multiBufferSource, n, skinnedMesh, livingEntityPatch.getArmature(), openMatrix4fArray);
            }
            poseStack.m_85849_();
        }
    }

    private void renderArmor(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, SkinnedMesh skinnedMesh, Armature armature, float f, float f2, float f3, ResourceLocation resourceLocation, OpenMatrix4f[] openMatrix4fArray) {
        skinnedMesh.draw(poseStack, multiBufferSource, RenderType.m_110431_((ResourceLocation)resourceLocation), n, f, f2, f3, 1.0f, OverlayTexture.f_118083_, armature, openMatrix4fArray);
    }

    private void renderGlint(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, SkinnedMesh skinnedMesh, Armature armature, OpenMatrix4f[] openMatrix4fArray) {
        skinnedMesh.draw(poseStack, multiBufferSource, RenderType.m_110484_(), n, 1.0f, 1.0f, 1.0f, 1.0f, OverlayTexture.f_118083_, armature, openMatrix4fArray);
    }

    private void renderTrim(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, SkinnedMesh skinnedMesh, Armature armature, ArmorMaterial armorMaterial, ArmorTrim armorTrim, EquipmentSlot equipmentSlot, OpenMatrix4f[] openMatrix4fArray) {
        TextureAtlasSprite textureAtlasSprite = this.armorTrimAtlas.m_118316_(PatchedVillagerUnitArmorLayer.innerModel(equipmentSlot) ? armorTrim.m_267774_(armorMaterial) : armorTrim.m_267606_(armorMaterial));
        VertexConsumer vertexConsumer = textureAtlasSprite.m_118381_(multiBufferSource.m_6299_(EpicFightRenderTypes.getTriangulated((RenderType)Sheets.m_266442_())));
        skinnedMesh.drawPosed(poseStack, vertexConsumer, Mesh.DrawingFunction.NEW_ENTITY, n, 1.0f, 1.0f, 1.0f, 1.0f, OverlayTexture.f_118083_, armature, openMatrix4fArray);
    }

    private SkinnedMesh getArmorModel(VillagerUnitArmorLayer villagerUnitArmorLayer, HumanoidModel<?> humanoidModel, Model model, LivingEntity livingEntity, ArmorItem armorItem, ItemStack itemStack, EquipmentSlot equipmentSlot) {
        ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey((Object)armorItem);
        SkinnedMesh skinnedMesh = WearableItemLayer.getCachedModel((Item)armorItem);
        if (skinnedMesh != null && !ClientEngine.getInstance().renderEngine.shouldRenderVanillaModel()) {
            return skinnedMesh;
        }
        ResourceManager resourceManager = Minecraft.m_91087_().m_91098_();
        ResourceLocation resourceLocation2 = new ResourceLocation(ForgeRegistries.ITEMS.getKey((Object)armorItem).m_135827_(), "animmodels/armor/" + ForgeRegistries.ITEMS.getKey((Object)armorItem).m_135815_() + ".json");
        SkinnedMesh skinnedMesh2 = null;
        if (resourceManager.m_213713_(resourceLocation2).isPresent()) {
            try {
                JsonAssetLoader jsonAssetLoader = new JsonAssetLoader(resourceManager, resourceLocation2);
                skinnedMesh2 = jsonAssetLoader.loadSkinnedMesh(SkinnedMesh::new);
            }
            catch (AssetLoadingException assetLoadingException) {
                assetLoadingException.printStackTrace();
                skinnedMesh2 = null;
            }
        } else {
            Object object;
            List list;
            Iterable iterable = livingEntity.m_6168_();
            ItemStack itemStack2 = livingEntity.m_6844_(EquipmentSlot.HEAD);
            ItemStack itemStack3 = livingEntity.m_6844_(EquipmentSlot.CHEST);
            ItemStack itemStack4 = livingEntity.m_6844_(EquipmentSlot.LEGS);
            ItemStack itemStack5 = livingEntity.m_6844_(EquipmentSlot.FEET);
            if (iterable instanceof List) {
                list = (List)iterable;
                list.set(0, ItemStack.f_41583_);
                list.set(1, ItemStack.f_41583_);
                list.set(2, ItemStack.f_41583_);
                list.set(3, ItemStack.f_41583_);
                list.set(equipmentSlot.m_20749_(), itemStack);
            }
            list = new PoseStack();
            list.m_252880_(0.0f, 0.0f, 10000.0f);
            if (model instanceof HumanoidModel) {
                object = (HumanoidModel)model;
                switch (equipmentSlot) {
                    case FEET: {
                        ((HumanoidModel)object).f_102813_.f_104207_ = true;
                        ((HumanoidModel)object).f_102814_.f_104207_ = true;
                        break;
                    }
                    case LEGS: {
                        ((HumanoidModel)object).f_102810_.f_104207_ = true;
                        ((HumanoidModel)object).f_102813_.f_104207_ = true;
                        ((HumanoidModel)object).f_102814_.f_104207_ = true;
                        break;
                    }
                    case CHEST: {
                        ((HumanoidModel)object).f_102810_.f_104207_ = true;
                        ((HumanoidModel)object).f_102811_.f_104207_ = true;
                        ((HumanoidModel)object).f_102812_.f_104207_ = true;
                        break;
                    }
                    case HEAD: {
                        ((HumanoidModel)object).f_102808_.f_104207_ = true;
                        ((HumanoidModel)object).f_102809_.f_104207_ = true;
                    }
                }
            }
            villagerUnitArmorLayer.m_6494_((PoseStack)list, (MultiBufferSource)Minecraft.m_91087_().m_91269_().m_110104_(), 0, (Entity)livingEntity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
            if (iterable instanceof List) {
                object = (List)iterable;
                object.set(0, itemStack5);
                object.set(1, itemStack4);
                object.set(2, itemStack3);
                object.set(3, itemStack2);
            }
            skinnedMesh2 = HumanoidModelBaker.bakeArmor(livingEntity, itemStack, armorItem, equipmentSlot, humanoidModel, model, villagerUnitArmorLayer.m_117386_() instanceof HumanoidModel ? (HumanoidModel)villagerUnitArmorLayer.m_117386_() : null, (HumanoidMesh)this.mesh.get());
        }
        WearableItemLayer.putModel(resourceLocation, skinnedMesh2);
        return skinnedMesh2;
    }

    private ResourceLocation getArmorTexture(ItemStack itemStack, LivingEntity livingEntity, SkinnedMesh skinnedMesh, EquipmentSlot equipmentSlot, String string, HumanoidModel<?> humanoidModel) {
        ResourceManager resourceManager;
        String string2 = WearableItemLayer.getArmorResource((Entity)livingEntity, itemStack, equipmentSlot, string).toString();
        Object[] objectArray = new Object[2];
        int n = string2.lastIndexOf(47);
        objectArray[0] = string2.substring(0, n);
        objectArray[1] = string2.substring(n + 1);
        String string3 = String.format("%s/epicfight/%s", objectArray);
        ResourceLocation resourceLocation = EPICFIGHT_OVERRIDING_TEXTURES.get(string3);
        if (resourceLocation != null) {
            return resourceLocation;
        }
        if (!EPICFIGHT_OVERRIDING_TEXTURES.containsKey(string3)) {
            resourceLocation = new ResourceLocation(string3);
            resourceManager = Minecraft.m_91087_().m_91098_();
            if (resourceManager.m_213713_(resourceLocation).isPresent()) {
                EPICFIGHT_OVERRIDING_TEXTURES.put(string3, resourceLocation);
                return resourceLocation;
            }
            EPICFIGHT_OVERRIDING_TEXTURES.put(string3, null);
        }
        resourceManager = new AnimatedArmorTextureEvent(livingEntity, itemStack, equipmentSlot, humanoidModel);
        MinecraftForge.EVENT_BUS.post((Event)resourceManager);
        ResourceLocation resourceLocation2 = resourceManager.getResultLocation();
        if (skinnedMesh.getRenderProperties() != null && skinnedMesh.getRenderProperties().customTexturePath() != null) {
            string2 = skinnedMesh.getRenderProperties().customTexturePath().toString();
            resourceLocation2 = null;
        }
        if (resourceLocation2 != null) {
            return resourceLocation2;
        }
        ResourceLocation resourceLocation3 = ARMOR_LOCATION_CACHE.get(string2);
        if (resourceLocation3 == null) {
            resourceLocation3 = new ResourceLocation(string2);
            ARMOR_LOCATION_CACHE.put(string2, resourceLocation3);
        }
        return resourceLocation3;
    }
}

