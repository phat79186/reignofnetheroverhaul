/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.min01.guardillagers.client.model.GuardIllagerModel
 *  com.min01.guardillagers.client.render.GuardIllagerRender
 *  com.min01.guardillagers.entity.GuardIllager
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.entity.layers.CustomHeadLayer
 *  net.minecraft.client.renderer.entity.layers.ItemInHandLayer
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.monster.AbstractIllager$IllagerArmPose
 *  yesman.epicfight.api.asset.AssetAccessor
 *  yesman.epicfight.api.utils.math.MathUtils
 *  yesman.epicfight.api.utils.math.OpenMatrix4f
 *  yesman.epicfight.api.utils.math.Vec3f
 *  yesman.epicfight.client.mesh.HumanoidMesh
 *  yesman.epicfight.client.renderer.patched.layer.PatchedItemInHandLayer
 *  yesman.epicfight.client.renderer.patched.layer.PatchedLayer
 */
package com.codex.rongolemhealerintegration.client;

import com.min01.guardillagers.client.model.GuardIllagerModel;
import com.min01.guardillagers.client.render.GuardIllagerRender;
import com.min01.guardillagers.entity.GuardIllager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.client.renderer.patched.layer.PatchedHeadLayer;
import yesman.epicfight.client.renderer.patched.layer.PatchedItemInHandLayer;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class PGuardIllagerRenderer
extends PatchedLivingEntityRenderer<GuardIllager, MobPatch<GuardIllager>, GuardIllagerModel<GuardIllager>, GuardIllagerRender<GuardIllager>, HumanoidMesh> {
    private final GuardIllagerModel<GuardIllager> headModel;
    private static final ResourceLocation GUARD_ILLAGER_TEXTURE = new ResourceLocation("guardillagers", "textures/entity/illager/guardillager.png");
    private boolean scaleUV = false;

    public PGuardIllagerRenderer(EntityRendererProvider.Context context, EntityType<?> entityType) {
        super(context, entityType);
        this.headModel = new GuardIllagerModel(context.m_174023_(GuardIllagerModel.LAYER_LOCATION));
        this.addPatchedLayerAlways(ItemInHandLayer.class, new PatchedItemInHandLayer<GuardIllager, MobPatch<GuardIllager>, GuardIllagerModel<GuardIllager>>(){

            public void renderLayer(MobPatch<GuardIllager> mobPatch, GuardIllager guardIllager, RenderLayer<GuardIllager, GuardIllagerModel<GuardIllager>> renderLayer, PoseStack poseStack, MultiBufferSource multiBufferSource, int n, OpenMatrix4f[] openMatrix4fArray, float f, float f2, float f3, float f4) {
                if (guardIllager.m_5912_()) {
                    super.renderLayer(mobPatch, (LivingEntity)guardIllager, renderLayer, poseStack, multiBufferSource, n, openMatrix4fArray, f, f2, f3, f4);
                }
            }
        });
        this.addPatchedLayer(CustomHeadLayer.class, new PatchedHeadLayer());
        this.addCustomLayer(new PatchedLayer<GuardIllager, MobPatch<GuardIllager>, GuardIllagerModel<GuardIllager>, RenderLayer<GuardIllager, GuardIllagerModel<GuardIllager>>>(){

            protected void renderLayer(MobPatch<GuardIllager> mobPatch, GuardIllager guardIllager, RenderLayer<GuardIllager, GuardIllagerModel<GuardIllager>> renderLayer, PoseStack poseStack, MultiBufferSource multiBufferSource, int n, OpenMatrix4f[] openMatrix4fArray, float f, float f2, float f3, float f4) {
                boolean bl;
                PGuardIllagerRenderer.this.renderPart(poseStack, multiBufferSource, n, openMatrix4fArray[9], 1.0f, 1.0f, 1.0f, 0.0f, 0.02f, 0.0f, PGuardIllagerRenderer.this.headModel.Head);
                boolean bl2 = bl = guardIllager.m_6768_() == AbstractIllager.IllagerArmPose.CROSSED;
                if (bl) {
                    PGuardIllagerRenderer.this.renderPart(poseStack, multiBufferSource, n, openMatrix4fArray[8], 1.0f, 1.0f, 1.0f, 0.0f, -0.375f, 0.0f, PGuardIllagerRenderer.this.headModel.ChestPlate, PGuardIllagerRenderer.this.headModel.Cape, PGuardIllagerRenderer.this.headModel.MiddleClosedArm);
                } else {
                    PGuardIllagerRenderer.this.renderPart(poseStack, multiBufferSource, n, openMatrix4fArray[8], 1.0f, 1.0f, 1.0f, 0.0f, -0.375f, 0.0f, PGuardIllagerRenderer.this.headModel.ChestPlate, PGuardIllagerRenderer.this.headModel.Cape);
                }
            }
        });
    }

    private void resetAndHideAll() {
        this.headModel.Head.f_104207_ = false;
        this.headModel.Body.f_104207_ = false;
        this.headModel.LeftLeg.f_104207_ = false;
        this.headModel.RightLeg.f_104207_ = false;
        this.headModel.LeftOpenArm.f_104207_ = false;
        this.headModel.RightOpenArm.f_104207_ = false;
        this.headModel.MiddleClosedArm.f_104207_ = false;
        this.headModel.ChestPlate.f_104207_ = false;
        this.headModel.Cape.f_104207_ = false;
        this.resetPart(this.headModel.Head);
        this.resetPart(this.headModel.Body);
        this.resetPart(this.headModel.LeftLeg);
        this.resetPart(this.headModel.RightLeg);
        this.resetPart(this.headModel.LeftOpenArm);
        this.resetPart(this.headModel.RightOpenArm);
        this.resetPart(this.headModel.ChestPlate);
        this.headModel.MiddleClosedArm.f_104200_ = 0.0f;
        this.headModel.MiddleClosedArm.f_104201_ = 3.0f;
        this.headModel.MiddleClosedArm.f_104202_ = -1.0f;
        this.headModel.MiddleClosedArm.f_104203_ = -0.75f;
        this.headModel.MiddleClosedArm.f_104204_ = 0.0f;
        this.headModel.MiddleClosedArm.f_104205_ = 0.0f;
        this.resetPart(this.headModel.LeftClosedArm);
        this.resetPart(this.headModel.RightClosedArm);
        this.headModel.Cape.f_104200_ = 0.0f;
        this.headModel.Cape.f_104201_ = 0.0f;
        this.headModel.Cape.f_104202_ = 3.6f;
        this.headModel.Cape.f_104204_ = 0.0f;
        this.headModel.Cape.f_104205_ = 0.0f;
    }

    private void resetPart(ModelPart modelPart) {
        modelPart.f_104200_ = 0.0f;
        modelPart.f_104201_ = 0.0f;
        modelPart.f_104202_ = 0.0f;
        modelPart.f_104203_ = 0.0f;
        modelPart.f_104204_ = 0.0f;
        modelPart.f_104205_ = 0.0f;
    }

    private void renderPart(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, OpenMatrix4f openMatrix4f, float f, float f2, float f3, float f4, float f5, float f6, ModelPart ... vertexConsumer) {
        poseStack.m_85836_();
        OpenMatrix4f openMatrix4f2 = new OpenMatrix4f();
        openMatrix4f2.scale(new Vec3f(-1.0f, -1.0f, 1.0f)).mulFront(openMatrix4f);
        if (f4 != 0.0f || f5 != 0.0f || f6 != 0.0f) {
            openMatrix4f2.translate(f4, f5, f6);
        }
        MathUtils.mulStack((PoseStack)poseStack, (OpenMatrix4f)openMatrix4f2);
        if (f != 1.0f || f2 != 1.0f || f3 != 1.0f) {
            poseStack.m_85841_(f, f2, f3);
        }
        this.resetAndHideAll();
        for (ModelPart modelPart : vertexConsumer) {
            modelPart.f_104207_ = true;
        }
        VertexConsumer vertexConsumer2 = multiBufferSource.m_6299_(RenderType.m_110473_((ResourceLocation)GUARD_ILLAGER_TEXTURE));
        this.headModel.m_7695_(poseStack, vertexConsumer2, n, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        poseStack.m_85849_();
    }

    @Override
    public void render(GuardIllager guardIllager, MobPatch<GuardIllager> mobPatch, GuardIllagerRender<GuardIllager> guardIllagerRender, MultiBufferSource multiBufferSource, PoseStack poseStack, int n, float f) {
        this.scaleUV = true;
        MultiBufferSource multiBufferSource2 = renderType -> {
            VertexConsumer vertexConsumer = multiBufferSource.m_6299_(renderType);
            if (this.scaleUV) {
                return new UVScaleVertexConsumer(vertexConsumer, 1.0f, 0.5f);
            }
            return vertexConsumer;
        };
        super.render(guardIllager, mobPatch, guardIllagerRender, multiBufferSource2, poseStack, n, f);
        this.scaleUV = false;
    }

    @Override
    protected void renderLayer(LivingEntityRenderer<GuardIllager, GuardIllagerModel<GuardIllager>> livingEntityRenderer, MobPatch<GuardIllager> mobPatch, GuardIllager guardIllager, OpenMatrix4f[] openMatrix4fArray, MultiBufferSource multiBufferSource, PoseStack poseStack, int n, float f) {
        this.scaleUV = false;
        super.renderLayer(livingEntityRenderer, mobPatch, guardIllager, openMatrix4fArray, multiBufferSource, poseStack, n, f);
    }

    @Override
    protected void prepareModel(HumanoidMesh humanoidMesh, GuardIllager guardIllager, MobPatch<GuardIllager> mobPatch, GuardIllagerRender<GuardIllager> guardIllagerRender) {
        boolean bl;
        super.prepareModel(humanoidMesh, guardIllager, mobPatch, guardIllagerRender);
        boolean bl2 = bl = guardIllager.m_6768_() == AbstractIllager.IllagerArmPose.CROSSED;
        if (humanoidMesh.hasPart("head")) {
            humanoidMesh.getPart("head").setHidden(true);
        }
        if (humanoidMesh.hasPart("hat")) {
            humanoidMesh.getPart("hat").setHidden(true);
        }
        if (humanoidMesh.hasPart("leftArm")) {
            humanoidMesh.getPart("leftArm").setHidden(bl);
        }
        if (humanoidMesh.hasPart("rightArm")) {
            humanoidMesh.getPart("rightArm").setHidden(bl);
        }
        if (humanoidMesh.hasPart("torso")) {
            humanoidMesh.getPart("torso").setHidden(false);
        }
        if (humanoidMesh.hasPart("leftLeg")) {
            humanoidMesh.getPart("leftLeg").setHidden(false);
        }
        if (humanoidMesh.hasPart("rightLeg")) {
            humanoidMesh.getPart("rightLeg").setHidden(false);
        }
    }

    @Override
    public AssetAccessor<HumanoidMesh> getDefaultMesh() {
        return Meshes.ILLAGER;
    }

    private static class UVScaleVertexConsumer
    implements VertexConsumer {
        private final VertexConsumer delegate;
        private final float scaleU;
        private final float scaleV;
        private double currentY;

        public UVScaleVertexConsumer(VertexConsumer vertexConsumer, float f, float f2) {
            this.delegate = vertexConsumer;
            this.scaleU = f;
            this.scaleV = f2;
        }

        public VertexConsumer m_5483_(double d, double d2, double d3) {
            this.currentY = d2;
            this.delegate.m_5483_(d, d2, d3);
            return this;
        }

        public VertexConsumer m_6122_(int n, int n2, int n3, int n4) {
            this.delegate.m_6122_(n, n2, n3, n4);
            return this;
        }

        public VertexConsumer m_7421_(float f, float f2) {
            float f3 = f;
            float f4 = f2;
            if (f >= 0.62f) {
                f3 = f + 0.0625f;
                f4 = f2 - 0.4375f;
            } else {
                f4 = f < 0.26f && this.currentY < 0.78 ? f2 - 0.0625f : f2 - 0.03125f;
            }
            return this.delegate.m_7421_(f3 * this.scaleU, f4 * this.scaleV);
        }

        public VertexConsumer m_7122_(int n, int n2) {
            this.delegate.m_7122_(n, n2);
            return this;
        }

        public VertexConsumer m_7120_(int n, int n2) {
            this.delegate.m_7120_(n, n2);
            return this;
        }

        public VertexConsumer m_5601_(float f, float f2, float f3) {
            this.delegate.m_5601_(f, f2, f3);
            return this;
        }

        public void m_5752_() {
            this.delegate.m_5752_();
        }

        public void m_7404_(int n, int n2, int n3, int n4) {
            this.delegate.m_7404_(n, n2, n3, n4);
        }

        public void m_141991_() {
            this.delegate.m_141991_();
        }
    }
}

