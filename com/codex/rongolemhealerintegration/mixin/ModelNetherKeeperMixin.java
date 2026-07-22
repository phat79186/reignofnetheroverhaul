package com.codex.rongolemhealerintegration.mixin;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = {"net.mcreator.stalwartdungeons.client.model.Modelnetherkeeper"}, remap = false)
public abstract class ModelNetherKeeperMixin<T extends Entity> extends EntityModel<T> {

    @Shadow
    public ModelPart rightarm;
    @Shadow
    public ModelPart leftarm;

    @Inject(method = "m_6973_", at = @At("TAIL"), remap = false)
    private void onSetupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (this.f_102608_ > 0.0F) {
            float swingProgress = this.f_102608_; // attackTime
            float f = 1.0F - swingProgress;
            f = f * f * f;
            float f1 = (float) Math.sin(f * Math.PI);
            float f2 = (float) Math.sin(swingProgress * Math.PI) * 0.75F;

            // Heavy arm swing strike for Nether Keeper
            this.rightarm.f_104203_ -= f1 * 1.5F + f2;
            this.rightarm.f_104204_ += (float) Math.sin(swingProgress * Math.PI) * 0.4F;
            this.rightarm.f_104205_ += (float) Math.sin(swingProgress * Math.PI) * -0.4F;

            this.leftarm.f_104203_ += f1 * 0.5F;
        }
    }
}
