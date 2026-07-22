package com.codex.rongolemhealerintegration.mixin;

import baguchan.slash_illager.client.model.BladeMasterModel;
import baguchan.slash_illager.entity.BladeMaster;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BladeMasterModel.class, remap = false)
public abstract class BladeMasterModelMixin<T extends BladeMaster> {
    @Shadow private ModelPart head;
    @Shadow private ModelPart body;
    @Shadow private ModelPart left_arm;
    @Shadow private ModelPart right_arm;
    @Shadow private ModelPart left_leg;
    @Shadow private ModelPart right_leg;

    @Inject(method = "setupAnim", at = @At("HEAD"))
    private void onSetupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (this.head != null) this.head.f_104207_ = true;
        if (this.body != null) this.body.f_104207_ = true;
        if (this.left_arm != null) this.left_arm.f_104207_ = true;
        if (this.right_arm != null) this.right_arm.f_104207_ = true;
        if (this.left_leg != null) this.left_leg.f_104207_ = true;
        if (this.right_leg != null) this.right_leg.f_104207_ = true;
    }
}
