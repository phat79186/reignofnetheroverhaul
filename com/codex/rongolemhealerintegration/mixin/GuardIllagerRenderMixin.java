/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.min01.guardillagers.client.render.GuardIllagerRender
 *  com.solegendary.reignofnether.unit.modelling.layers.VillagerUnitArmorLayer
 *  com.solegendary.reignofnether.unit.modelling.models.IllagerArmorModel
 *  com.solegendary.reignofnether.unit.modelling.renderers.AbstractVillagerUnitRenderer
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.RenderLayerParent
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.codex.rongolemhealerintegration.mixin;

import com.codex.rongolemhealerintegration.mixin.LivingEntityRendererInvoker;
import com.min01.guardillagers.client.render.GuardIllagerRender;
import com.solegendary.reignofnether.unit.modelling.layers.VillagerUnitArmorLayer;
import com.solegendary.reignofnether.unit.modelling.models.IllagerArmorModel;
import com.solegendary.reignofnether.unit.modelling.renderers.AbstractVillagerUnitRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuardIllagerRender.class}, remap=false)
public abstract class GuardIllagerRenderMixin {
    @Inject(method={"<init>"}, at={@At(value="TAIL")}, remap=false)
    private void onInit(EntityRendererProvider.Context context, CallbackInfo callbackInfo) {
        System.out.println("[RoN Armor Mixin] GuardIllagerRenderMixin successfully injected onInit!");
        RenderLayerParent renderLayerParent = (RenderLayerParent)this;
        ((LivingEntityRendererInvoker)((Object)this)).callAddLayer((RenderLayer<?, ?>)new VillagerUnitArmorLayer(renderLayerParent, (HumanoidModel)new IllagerArmorModel(context.m_174023_(AbstractVillagerUnitRenderer.VILLAGER_ARMOR_INNER_LAYER)), (HumanoidModel)new IllagerArmorModel(context.m_174023_(AbstractVillagerUnitRenderer.VILLAGER_ARMOR_OUTER_LAYER))));
    }
}

