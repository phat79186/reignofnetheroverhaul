package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.fogofwar.FogOfWarClientEvents;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = {PatchedLivingEntityRenderer.class}, remap = false)
public class PatchedLivingEntityRendererMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true, remap = false)
    private void ronGolemHealerIntegration$onEpicFightRender(
        LivingEntity entity, 
        LivingEntityPatch<?> patch, 
        net.minecraft.client.renderer.entity.LivingEntityRenderer<?, ?> renderer, 
        MultiBufferSource buffer, 
        com.mojang.blaze3d.vertex.PoseStack poseStack, 
        int packedLight, 
        float partialTicks, 
        CallbackInfo ci
    ) {
        if (!FogOfWarClientEvents.isInBrightChunk(entity)) {
            ci.cancel();
        }
    }
}
