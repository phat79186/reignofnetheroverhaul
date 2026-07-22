/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.min01.guardillagers.client.render.layer.GuardCrossedArmsItemLayer
 *  com.min01.guardillagers.entity.GuardIllager
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.solegendary.reignofnether.unit.modelling.models.VillagerUnitModel
 *  com.solegendary.reignofnether.unit.modelling.renderers.AbstractVillagerUnitRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.RenderLayerParent
 *  net.minecraft.client.renderer.entity.layers.ItemInHandLayer
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.LivingEntity
 */
package com.codex.rongolemhealerintegration.client.render;

import com.min01.guardillagers.client.render.layer.GuardCrossedArmsItemLayer;
import com.min01.guardillagers.entity.GuardIllager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.solegendary.reignofnether.unit.modelling.models.VillagerUnitModel;
import com.solegendary.reignofnether.unit.modelling.renderers.AbstractVillagerUnitRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class GuardIllagerUnitRenderer
extends AbstractVillagerUnitRenderer<GuardIllager> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("guardillagers", "textures/entity/illager/guardillager.png");

    public GuardIllagerUnitRenderer(EntityRendererProvider.Context context) {
        super(context, new VillagerUnitModel(context.m_174023_(VillagerUnitModel.LAYER_LOCATION)), 0.5f);
        this.m_115326_((RenderLayer)new ItemInHandLayer<GuardIllager, VillagerUnitModel<GuardIllager>>((RenderLayerParent)this, context.m_234598_()){

            public void m_6494_(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, GuardIllager guardIllager, float f, float f2, float f3, float f4, float f5, float f6) {
                if (guardIllager.m_5912_()) {
                    super.m_6494_(poseStack, multiBufferSource, n, (LivingEntity)guardIllager, f, f2, f3, f4, f5, f6);
                }
            }
        });
        this.m_115326_((RenderLayer)new GuardCrossedArmsItemLayer((RenderLayerParent)this, context.m_234598_()));
    }

    public ResourceLocation m_5478_(GuardIllager guardIllager) {
        return TEXTURE;
    }
}

