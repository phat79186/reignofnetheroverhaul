/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  baguchan.slash_illager.client.model.BladeMasterModel
 *  baguchan.slash_illager.client.render.BladeMasterRenderer
 *  baguchan.slash_illager.entity.BladeMaster
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  mods.flammpfeil.slashblade.client.renderer.layers.LayerMainBlade
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
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  yesman.epicfight.api.asset.AssetAccessor
 *  yesman.epicfight.api.utils.math.MathUtils
 *  yesman.epicfight.api.utils.math.OpenMatrix4f
 *  yesman.epicfight.api.utils.math.Vec3f
 *  yesman.epicfight.client.mesh.HumanoidMesh
 *  yesman.epicfight.client.renderer.patched.layer.PatchedItemInHandLayer
 *  yesman.epicfight.client.renderer.patched.layer.PatchedLayer
 */
package com.codex.rongolemhealerintegration.client;

import baguchan.slash_illager.client.model.BladeMasterModel;
import baguchan.slash_illager.client.render.BladeMasterRenderer;
import baguchan.slash_illager.entity.BladeMaster;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mods.flammpfeil.slashblade.client.renderer.layers.LayerMainBlade;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

import mods.flammpfeil.slashblade.capability.slashblade.CapabilitySlashBlade;
import mods.flammpfeil.slashblade.capability.slashblade.ISlashBladeState;
import mods.flammpfeil.slashblade.client.renderer.CarryType;
import mods.flammpfeil.slashblade.client.renderer.model.BladeModelManager;
import mods.flammpfeil.slashblade.client.renderer.model.obj.WavefrontObject;
import mods.flammpfeil.slashblade.client.renderer.util.BladeRenderState;
import mods.flammpfeil.slashblade.init.DefaultResources;
import org.joml.Quaternionf;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import com.mojang.math.Axis;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.common.util.LazyOptional;

public class PBladeMasterRenderer
extends PatchedLivingEntityRenderer<BladeMaster, MobPatch<BladeMaster>, BladeMasterModel<BladeMaster>, BladeMasterRenderer<BladeMaster>, HumanoidMesh> {
    private boolean scaleUV = false;
    private LayerMainBlade<BladeMaster, BladeMasterModel<BladeMaster>> mainBladeLayer = null;
    private BladeMasterRenderer<BladeMaster> vanillaRenderer = null;
    private MultiBufferSource originalBuffer = null;

    public PBladeMasterRenderer(EntityRendererProvider.Context context, EntityType<?> entityType) {
        super(context, entityType);
        this.addPatchedLayerAlways(ItemInHandLayer.class, new PatchedItemInHandLayer<BladeMaster, MobPatch<BladeMaster>, BladeMasterModel<BladeMaster>>(){
            @Override
            protected void renderLayer(MobPatch<BladeMaster> mobPatch, BladeMaster bladeMaster, RenderLayer<BladeMaster, BladeMasterModel<BladeMaster>> vanillaLayer, PoseStack postStack, MultiBufferSource buffer, int packedLight, OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks) {
                net.minecraft.world.item.ItemStack mainHandStack = bladeMaster.m_21205_();
                if (mainHandStack.m_41720_() instanceof mods.flammpfeil.slashblade.item.ItemSlashBlade) {
                    return;
                }
                super.renderLayer(mobPatch, bladeMaster, vanillaLayer, postStack, buffer, packedLight, poses, bob, yRot, xRot, partialTicks);
            }
        });
        this.addPatchedLayerAlways(LayerMainBlade.class, new PatchedLayer<BladeMaster, MobPatch<BladeMaster>, BladeMasterModel<BladeMaster>, RenderLayer<BladeMaster, BladeMasterModel<BladeMaster>>>(){
            @Override
            protected void renderLayer(MobPatch<BladeMaster> mobPatch, BladeMaster bladeMaster, RenderLayer<BladeMaster, BladeMasterModel<BladeMaster>> vanillaLayer, PoseStack postStack, MultiBufferSource buffer, int packedLight, OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks) {
                // Block automatic rendering of LayerMainBlade through RenderOriginalModelLayer
            }
        });
        this.addPatchedLayer(CustomHeadLayer.class, new PatchedHeadLayer());
        this.addCustomLayer(new PatchedLayer<BladeMaster, MobPatch<BladeMaster>, BladeMasterModel<BladeMaster>, RenderLayer<BladeMaster, BladeMasterModel<BladeMaster>>>(){

            protected void renderLayer(MobPatch<BladeMaster> mobPatch, BladeMaster bladeMaster, RenderLayer<BladeMaster, BladeMasterModel<BladeMaster>> renderLayer, PoseStack poseStack, MultiBufferSource multiBufferSource, int n, OpenMatrix4f[] openMatrix4fArray, float f, float f2, float f3, float f4) {
                net.minecraft.world.item.ItemStack mainHandStack = bladeMaster.m_21205_();
                boolean isSlashBlade = mainHandStack.m_41720_() instanceof mods.flammpfeil.slashblade.item.ItemSlashBlade;
                
                com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug] renderLayer: entity=" + bladeMaster + ", item=" + mainHandStack + ", isSlashBlade=" + isSlashBlade + ", isCombat=" + bladeMaster.m_5912_());
                
                if (isSlashBlade) {
                    boolean isCombat = mobPatch.getEntityState().attacking() || (mobPatch.getEntityState().inaction() && !mobPatch.getEntityState().hurt() && !mobPatch.getEntityState().knockDown());
                    
                    // Render sheath (always) and blade (only if not in combat) on the back (attached to Chest joint)
                    OpenMatrix4f modelMatrix = openMatrix4fArray[mobPatch.getArmature().searchJointByName("Chest").getId()];
                    poseStack.m_85836_();
                    MathUtils.mulStack((PoseStack)poseStack, (OpenMatrix4f)modelMatrix);
                    
                    // Mirror to align with Epic Fight's chest joint orientation
                    poseStack.m_85841_(-1.0f, -1.0f, 1.0f);
                    
                    // Position the sword/sheath on the back (shifted 4px to the right)
                    poseStack.m_252880_(0.25f, 0.15f, -0.18f);
                    
                    // Rotate the sword vertically
                    poseStack.m_252781_(new Quaternionf().rotateZYX((float)Math.toRadians(90.0), (float)Math.toRadians(90.0), (float)Math.toRadians(180.0)));
                    
                    // Scale the SlashBlade model
                    float backScale = 0.0078125f;
                    poseStack.m_85841_(backScale, backScale, backScale);
                    
                    MultiBufferSource targetBuffer = PBladeMasterRenderer.this.originalBuffer != null ? PBladeMasterRenderer.this.originalBuffer : multiBufferSource;
                    
                    ((net.minecraftforge.common.capabilities.ICapabilityProvider) (Object) mainHandStack).getCapability(CapabilitySlashBlade.BLADESTATE, (net.minecraft.core.Direction) null).ifPresent(s -> {
                        ResourceLocation textureLocation = s.getTexture().orElse(DefaultResources.resourceDefaultTexture);
                        WavefrontObject obj = BladeModelManager.getInstance().getModel(s.getModel().orElse(DefaultResources.resourceDefaultModel));
                        
                        if (!isCombat) {
                            String part = s.isBroken() ? "blade_damaged" : "blade";
                            BladeRenderState.renderOverrided(mainHandStack, obj, part, textureLocation, poseStack, targetBuffer, n);
                            BladeRenderState.renderOverridedLuminous(mainHandStack, obj, part + "_luminous", textureLocation, poseStack, targetBuffer, n);
                        }
                        
                        BladeRenderState.renderOverrided(mainHandStack, obj, "sheath", textureLocation, poseStack, targetBuffer, n);
                        BladeRenderState.renderOverridedLuminous(mainHandStack, obj, "sheath_luminous", textureLocation, poseStack, targetBuffer, n);
                    });
                    
                    poseStack.m_85849_();
                    
                    // Render blade in hand (only if in combat)
                    if (isCombat) {
                        OpenMatrix4f toolMatrix = openMatrix4fArray[mobPatch.getArmature().searchJointByName("Tool_R").getId()];
                        poseStack.m_85836_();
                        MathUtils.mulStack((PoseStack)poseStack, (OpenMatrix4f)toolMatrix);
                        poseStack.m_252880_(0.0f, 0.0f, -0.13f);
                        poseStack.m_252781_(Axis.f_252529_.m_252977_(-90.0f)); // rotate -90 around X
                        poseStack.m_85841_(-1.0f, -1.0f, 1.0f);
                        
                        // Scale the SlashBlade model
                        float scale = 0.0078125f;
                        poseStack.m_85841_(scale, scale, scale);
                        
                        // Render only the blade
                        ((net.minecraftforge.common.capabilities.ICapabilityProvider) (Object) mainHandStack).getCapability(CapabilitySlashBlade.BLADESTATE, (net.minecraft.core.Direction) null).ifPresent(s -> {
                            ResourceLocation textureLocation = s.getTexture().orElse(DefaultResources.resourceDefaultTexture);
                            WavefrontObject obj = BladeModelManager.getInstance().getModel(s.getModel().orElse(DefaultResources.resourceDefaultModel));
                            String part = s.isBroken() ? "blade_damaged" : "blade";
                            BladeRenderState.renderOverrided(mainHandStack, obj, part, textureLocation, poseStack, targetBuffer, n);
                            BladeRenderState.renderOverridedLuminous(mainHandStack, obj, part + "_luminous", textureLocation, poseStack, targetBuffer, n);
                        });
                        
                        poseStack.m_85849_();
                    }
                }
                
                if (PBladeMasterRenderer.this.vanillaRenderer != null) {
                    ModelPart modelPart;
                    BladeMasterModel bladeMasterModel = (BladeMasterModel)PBladeMasterRenderer.this.vanillaRenderer.m_7200_();
                    ResourceLocation resourceLocation = PBladeMasterRenderer.this.vanillaRenderer.m_5478_(bladeMaster);
                    poseStack.m_85836_();
                    OpenMatrix4f openMatrix4f = new OpenMatrix4f();
                    openMatrix4f.scale(new Vec3f(-1.0f, -1.0f, 1.0f)).mulFront(openMatrix4fArray[9]);
                    openMatrix4f.translate(0.0f, 0.02f, 0.0f);
                    MathUtils.mulStack((PoseStack)poseStack, (OpenMatrix4f)openMatrix4f);
                    ModelPart modelPart2 = bladeMasterModel.m_142109_();
                    modelPart2.f_104207_ = true;
                    try {
                        modelPart2.m_171324_((String)"body").f_104207_ = false;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    try {
                        modelPart2.m_171324_((String)"left_arm").f_104207_ = false;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    try {
                        modelPart2.m_171324_((String)"right_arm").f_104207_ = false;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    try {
                        modelPart2.m_171324_((String)"left_leg").f_104207_ = false;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    try {
                        modelPart2.m_171324_((String)"right_leg").f_104207_ = false;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    ModelPart modelPart3 = ((net.minecraft.client.model.HeadedModel) bladeMasterModel).m_5585_();
                    modelPart3.f_104207_ = true;
                    modelPart3.f_104200_ = 0.0f;
                    modelPart3.f_104201_ = 0.0f;
                    modelPart3.f_104202_ = 0.0f;
                    modelPart3.f_104203_ = 0.0f;
                    modelPart3.f_104204_ = 0.0f;
                    modelPart3.f_104205_ = 0.0f;
                    try {
                        modelPart = modelPart3.m_171324_("hat");
                        if (modelPart != null) {
                            modelPart.f_104207_ = true;
                            ModelPart modelPart4 = modelPart.m_171324_("bone");
                            if (modelPart4 != null) {
                                modelPart4.f_104207_ = true;
                            }
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    try {
                        modelPart = modelPart3.m_171324_("nose");
                        if (modelPart != null) {
                            modelPart.f_104207_ = true;
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    VertexConsumer vertexConsumer = multiBufferSource.m_6299_(RenderType.m_110473_(resourceLocation));
                    bladeMasterModel.m_7695_(poseStack, vertexConsumer, n, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
                    poseStack.m_85849_();
                    try { modelPart2.m_171324_("body").f_104207_ = true; } catch (Exception exception) {}
                    try { modelPart2.m_171324_("left_arm").f_104207_ = true; } catch (Exception exception) {}
                    try { modelPart2.m_171324_("right_arm").f_104207_ = true; } catch (Exception exception) {}
                    try { modelPart2.m_171324_("left_leg").f_104207_ = true; } catch (Exception exception) {}
                    try { modelPart2.m_171324_("right_leg").f_104207_ = true; } catch (Exception exception) {}
                }
            }
        });
    }

    @Override
    public void render(BladeMaster bladeMaster, MobPatch<BladeMaster> mobPatch, BladeMasterRenderer<BladeMaster> bladeMasterRenderer, MultiBufferSource multiBufferSource, PoseStack poseStack, int n, float f) {
        this.originalBuffer = multiBufferSource;
        this.vanillaRenderer = bladeMasterRenderer;
        this.scaleUV = true;
        MultiBufferSource multiBufferSource2 = renderType -> {
            VertexConsumer vertexConsumer = multiBufferSource.m_6299_(renderType);
            if (this.scaleUV) {
                return new UVScaleVertexConsumer(vertexConsumer, 0.5f, 0.5f);
            }
            return vertexConsumer;
        };
        super.render(bladeMaster, mobPatch, bladeMasterRenderer, multiBufferSource2, poseStack, n, f);
        this.scaleUV = false;
    }

    @Override
    protected void renderLayer(LivingEntityRenderer<BladeMaster, BladeMasterModel<BladeMaster>> livingEntityRenderer, MobPatch<BladeMaster> mobPatch, BladeMaster bladeMaster, OpenMatrix4f[] openMatrix4fArray, MultiBufferSource multiBufferSource, PoseStack poseStack, int n, float f) {
        this.scaleUV = false;
        super.renderLayer(livingEntityRenderer, mobPatch, bladeMaster, openMatrix4fArray, multiBufferSource, poseStack, n, f);
    }

    @Override
    protected void prepareModel(HumanoidMesh humanoidMesh, BladeMaster bladeMaster, MobPatch<BladeMaster> mobPatch, BladeMasterRenderer<BladeMaster> bladeMasterRenderer) {
        super.prepareModel(humanoidMesh, bladeMaster, mobPatch, bladeMasterRenderer);
        if (humanoidMesh.hasPart("head")) {
            humanoidMesh.getPart("head").setHidden(true);
        }
        if (humanoidMesh.hasPart("hat")) {
            humanoidMesh.getPart("hat").setHidden(true);
        }
    }

    private void renderCustomBlade(PoseStack matrixStack, MultiBufferSource bufferIn, int lightIn, net.minecraft.world.item.ItemStack blade, LivingEntity entity, boolean drawBlade, boolean drawSheath) {
        com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug] renderCustomBlade: item=" + blade + ", drawBlade=" + drawBlade + ", drawSheath=" + drawSheath);
        ((net.minecraftforge.common.capabilities.ICapabilityProvider) (Object) blade).getCapability(CapabilitySlashBlade.BLADESTATE, (net.minecraft.core.Direction) null).ifPresent(s -> {
            com.codex.rongolemhealerintegration.RonGolemHealerIntegrationMod.LOGGER.info("[RoN Debug] capability present! model=" + s.getModel().orElse(null) + ", texture=" + s.getTexture().orElse(null) + ", carryType=" + s.getCarryType());
            double modelScaleBase = 0.0078125;
            double motionScale = 0.125;
            ResourceLocation textureLocation = s.getTexture().orElse(DefaultResources.resourceDefaultTexture);
            WavefrontObject obj = BladeModelManager.getInstance().getModel(s.getModel().orElse(DefaultResources.resourceDefaultModel));
            
            matrixStack.m_85836_(); // push
            matrixStack.m_252880_(0.0f, 1.5f, 0.0f);
            CarryType carrytype = s.getCarryType();
            
            if (carrytype == CarryType.DEFAULT) {
                matrixStack.m_252880_(1.0f, -1.125f, 0.2f);
                matrixStack.m_252781_(new Quaternionf().rotateZYX(-0.122173f, 0.0f, 0.0f));
            } else if (carrytype == CarryType.PSO2) {
                matrixStack.m_252880_(0.25f, -0.875f, -0.55f);
                matrixStack.m_252781_(new Quaternionf().rotateZYX((float)Math.PI, 1.570796f, 0.261799f));
            } else if (carrytype == CarryType.NINJA) {
                matrixStack.m_252880_(0.25f, -0.875f, -0.55f);
                matrixStack.m_252781_(new Quaternionf().rotateZYX(0.0f, 1.570796f, 0.261799f));
            } else if (carrytype == CarryType.KATANA) {
                matrixStack.m_252880_(-0.5f, -2.0f, 0.2f);
                matrixStack.m_252781_(new Quaternionf().rotateZYX(-2.094395f, 0.0f, (float)Math.PI));
            } else if (carrytype == CarryType.RNINJA) {
                matrixStack.m_252880_(0.5f, -2.0f, 0.2f);
                matrixStack.m_252781_(new Quaternionf().rotateZYX(-1.047198f, 0.0f, 0.0f));
            } else {
                matrixStack.m_85849_(); // pop
                return;
            }
            
            float modelScale = (float)(modelScaleBase * (1.0 / motionScale));
            matrixStack.m_85841_((float)motionScale, (float)motionScale, (float)motionScale);
            matrixStack.m_85841_(modelScale, modelScale, modelScale);
            
            matrixStack.m_85836_(); // push
            if (drawBlade) {
                String part = s.isBroken() ? "blade_damaged" : "blade";
                BladeRenderState.renderOverrided(blade, obj, part, textureLocation, matrixStack, bufferIn, lightIn);
                BladeRenderState.renderOverridedLuminous(blade, obj, part + "_luminous", textureLocation, matrixStack, bufferIn, lightIn);
            }
            if (drawSheath) {
                BladeRenderState.renderOverrided(blade, obj, "sheath", textureLocation, matrixStack, bufferIn, lightIn);
                BladeRenderState.renderOverridedLuminous(blade, obj, "sheath_luminous", textureLocation, matrixStack, bufferIn, lightIn);
            }
            matrixStack.m_85849_(); // pop
            matrixStack.m_85849_(); // pop
        });
    }

    @Override
    public AssetAccessor<HumanoidMesh> getDefaultMesh() {
        return Meshes.BIPED;
    }

    private static class UVScaleVertexConsumer
    implements VertexConsumer {
        private final VertexConsumer delegate;
        private final float scaleU;
        private final float scaleV;

        public UVScaleVertexConsumer(VertexConsumer vertexConsumer, float f, float f2) {
            this.delegate = vertexConsumer;
            this.scaleU = f;
            this.scaleV = f2;
        }

        public VertexConsumer m_5483_(double d, double d2, double d3) {
            this.delegate.m_5483_(d, d2, d3);
            return this;
        }

        public VertexConsumer m_6122_(int n, int n2, int n3, int n4) {
            this.delegate.m_6122_(n, n2, n3, n4);
            return this;
        }

        public VertexConsumer m_7421_(float f, float f2) {
            float f3 = f * 64.0f;
            float f4 = f2 * 64.0f;
            float f5 = f3;
            float f6 = f4;
            if (f4 >= 0.0f && f4 < 16.0f) {
                if (f3 >= 0.0f && f3 < 32.0f) {
                    f5 = f3;
                    f6 = f4;
                } else {
                    f5 = f3 - 32.0f + 38.0f;
                    f6 = f4;
                }
            } else if (f4 >= 16.0f && f4 < 32.0f) {
                if (f3 >= 0.0f && f3 < 16.0f) {
                    f5 = f3;
                    f6 = f4 + 6.0f;
                } else if (f3 >= 16.0f && f3 < 40.0f) {
                    if (f4 >= 16.0f && f4 < 20.0f) {
                        if (f3 >= 20.0f && f3 < 36.0f) {
                            f5 = f3 + 2.0f;
                            f6 = (f4 - 16.0f) * 1.5f + 18.0f;
                        } else {
                            f5 = f3 + 2.0f;
                            f6 = f4 + 4.0f;
                        }
                    } else if (f3 >= 16.0f && f3 < 20.0f) {
                        f5 = (f3 - 16.0f) * 1.5f + 16.0f;
                        f6 = f4 + 4.0f;
                    } else if (f3 >= 20.0f && f3 < 28.0f) {
                        f5 = f3 + 2.0f;
                        f6 = f4 + 4.0f;
                    } else if (f3 >= 28.0f && f3 < 32.0f) {
                        f5 = (f3 - 28.0f) * 1.5f + 30.0f;
                        f6 = f4 + 4.0f;
                    } else {
                        f5 = f3 + 4.0f;
                        f6 = f4 + 4.0f;
                    }
                } else {
                    f5 = f3 + 4.0f;
                    f6 = f4 + 4.0f;
                }
            } else if (f4 >= 32.0f && f4 < 48.0f) {
                if (f3 >= 0.0f && f3 < 16.0f) {
                    f5 = f3;
                    f6 = f4 - 10.0f;
                } else if (f3 >= 16.0f && f3 < 40.0f) {
                    if (f4 >= 32.0f && f4 < 36.0f) {
                        if (f3 >= 20.0f && f3 < 36.0f) {
                            f5 = f3 - 14.0f;
                            f6 = (f4 - 32.0f) * 1.5f + 36.0f;
                        } else {
                            f5 = f3 - 14.0f;
                            f6 = (f4 - 36.0f) * 1.5f + 42.0f;
                        }
                    } else if (f3 >= 16.0f && f3 < 20.0f) {
                        f5 = (f3 - 16.0f) * 1.5f;
                        f6 = (f4 - 36.0f) * 1.5f + 42.0f;
                    } else if (f3 >= 20.0f && f3 < 28.0f) {
                        f5 = f3 - 14.0f;
                        f6 = (f4 - 36.0f) * 1.5f + 42.0f;
                    } else if (f3 >= 28.0f && f3 < 32.0f) {
                        f5 = (f3 - 28.0f) * 1.5f + 14.0f;
                        f6 = (f4 - 36.0f) * 1.5f + 42.0f;
                    } else {
                        f5 = f3 - 12.0f;
                        f6 = (f4 - 36.0f) * 1.5f + 42.0f;
                    }
                } else {
                    f5 = f3 + 4.0f;
                    f6 = f4 - 12.0f;
                }
            } else if (f3 >= 0.0f && f3 < 16.0f) {
                f5 = f3;
                f6 = f4 - 26.0f;
            } else if (f3 >= 16.0f && f3 < 32.0f) {
                f5 = f3 - 16.0f;
                f6 = f4 - 26.0f;
            } else if (f3 >= 32.0f && f3 < 48.0f) {
                f5 = f3 + 12.0f;
                f6 = f4 - 26.0f;
            } else {
                f5 = f3 - 4.0f;
                f6 = f4 - 26.0f;
            }
            return this.delegate.m_7421_(f5 / 128.0f, f6 / 128.0f);
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

