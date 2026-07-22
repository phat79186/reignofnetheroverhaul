package com.codex.rongolemhealerintegration.mixin;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = {"net.mcreator.stalwartdungeons.client.model.Modelgiddyblaze"}, remap = false)
public abstract class ModelGiddyBlazeMixin<T extends Entity> extends EntityModel<T> {

    @Shadow
    public ModelPart rightarm;
    @Shadow
    public ModelPart leftarm;

    @Inject(method = "m_6973_", at = @At("TAIL"), remap = false)
    private void onSetupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (this.f_102608_ > 0.0F) {
            float swing = (float) Math.sin(this.f_102608_ * Math.PI);
            this.rightarm.f_104203_ -= swing * 1.0F;
            this.leftarm.f_104203_ -= swing * 1.0F;
        }
    }
}
