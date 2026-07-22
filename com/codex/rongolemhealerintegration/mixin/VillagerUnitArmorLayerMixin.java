package com.codex.rongolemhealerintegration.mixin;

import com.min01.guardillagers.entity.GuardIllager;
import com.min01.guardillagers.client.model.GuardIllagerModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.solegendary.reignofnether.unit.modelling.layers.VillagerUnitArmorLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;

@Mixin(value = VillagerUnitArmorLayer.class, remap = false)
public abstract class VillagerUnitArmorLayerMixin<T extends LivingEntity, M extends EntityModel<T>, A extends HumanoidModel<T>> extends net.minecraft.client.renderer.entity.layers.RenderLayer<T, M> {

    public VillagerUnitArmorLayerMixin(net.minecraft.client.renderer.entity.RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Shadow
    protected abstract void setPartVisibility(A model, EquipmentSlot slot);

    @Shadow
    protected abstract net.minecraft.client.model.Model getArmorModelHook(T entity, ItemStack stack, EquipmentSlot slot, A model);

    @Shadow
    protected abstract boolean usesInnerModel(EquipmentSlot slot);

    @Shadow
    protected abstract ResourceLocation getArmorResource(net.minecraft.world.entity.Entity entity, ItemStack stack, EquipmentSlot slot, String type);

    @Shadow
    protected abstract void renderModel(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ArmorItem armorItem, net.minecraft.client.model.Model model, boolean glint, float r, float g, float b, ResourceLocation resourceLocation);

    @Shadow
    protected abstract void renderGlint(PoseStack poseStack, MultiBufferSource buffer, int packedLight, net.minecraft.client.model.Model model);

    @Inject(method = "renderArmorPiece", at = @At("HEAD"), cancellable = true, remap = false)
    private void onRenderArmorPiece(PoseStack poseStack, MultiBufferSource buffer, T entity, EquipmentSlot slot, int packedLight, A armorModel, CallbackInfo ci) {
        if (entity instanceof GuardIllager) {
            ci.cancel();
            
            ItemStack itemStack = entity.m_6844_(slot);
            if (itemStack.m_41720_() instanceof ArmorItem) {
                ArmorItem armorItem = (ArmorItem) itemStack.m_41720_();
                if (armorItem.m_40402_() == slot) {
                    M parentModel = this.m_117386_();
                    if (parentModel instanceof GuardIllagerModel) {
                        GuardIllagerModel<?> guardModel = (GuardIllagerModel<?>) parentModel;
                        
                        // Copy properties manually from GuardIllagerModel to armorModel (which is a HumanoidModel)
                        armorModel.f_102610_ = guardModel.f_102610_;
                        armorModel.f_102609_ = guardModel.f_102609_;
                        
                        // Head
                        armorModel.f_102808_.f_104203_ = guardModel.Head.f_104203_;
                        armorModel.f_102808_.f_104204_ = guardModel.Head.f_104204_;
                        armorModel.f_102808_.f_104205_ = guardModel.Head.f_104205_;
                        armorModel.f_102808_.f_104200_ = guardModel.Head.f_104200_;
                        armorModel.f_102808_.f_104201_ = guardModel.Head.f_104201_;
                        armorModel.f_102808_.f_104202_ = guardModel.Head.f_104202_;
                        
                        // Body
                        armorModel.f_102810_.f_104203_ = guardModel.Body.f_104203_;
                        armorModel.f_102810_.f_104204_ = guardModel.Body.f_104204_;
                        armorModel.f_102810_.f_104205_ = guardModel.Body.f_104205_;
                        armorModel.f_102810_.f_104200_ = guardModel.Body.f_104200_;
                        armorModel.f_102810_.f_104201_ = guardModel.Body.f_104201_;
                        armorModel.f_102810_.f_104202_ = guardModel.Body.f_104202_;
                        
                        // Legs
                        armorModel.f_102813_.f_104203_ = guardModel.RightLeg.f_104203_;
                        armorModel.f_102813_.f_104204_ = guardModel.RightLeg.f_104204_;
                        armorModel.f_102813_.f_104205_ = guardModel.RightLeg.f_104205_;
                        armorModel.f_102813_.f_104200_ = guardModel.RightLeg.f_104200_;
                        armorModel.f_102813_.f_104201_ = guardModel.RightLeg.f_104201_;
                        armorModel.f_102813_.f_104202_ = guardModel.RightLeg.f_104202_;
                        
                        armorModel.f_102814_.f_104203_ = guardModel.LeftLeg.f_104203_;
                        armorModel.f_102814_.f_104204_ = guardModel.LeftLeg.f_104204_;
                        armorModel.f_102814_.f_104205_ = guardModel.LeftLeg.f_104205_;
                        armorModel.f_102814_.f_104200_ = guardModel.LeftLeg.f_104200_;
                        armorModel.f_102814_.f_104201_ = guardModel.LeftLeg.f_104201_;
                        armorModel.f_102814_.f_104202_ = guardModel.LeftLeg.f_104202_;
                        
                        // Arms
                        if (guardModel.RightOpenArm.f_104207_) {
                            armorModel.f_102811_.f_104203_ = guardModel.RightOpenArm.f_104203_;
                            armorModel.f_102811_.f_104204_ = guardModel.RightOpenArm.f_104204_;
                            armorModel.f_102811_.f_104205_ = guardModel.RightOpenArm.f_104205_;
                        } else {
                            armorModel.f_102811_.f_104203_ = guardModel.MiddleClosedArm.f_104203_;
                            armorModel.f_102811_.f_104204_ = guardModel.MiddleClosedArm.f_104204_;
                            armorModel.f_102811_.f_104205_ = guardModel.MiddleClosedArm.f_104205_;
                        }
                        
                        if (guardModel.LeftOpenArm.f_104207_) {
                            armorModel.f_102812_.f_104203_ = guardModel.LeftOpenArm.f_104203_;
                            armorModel.f_102812_.f_104204_ = guardModel.LeftOpenArm.f_104204_;
                            armorModel.f_102812_.f_104205_ = guardModel.LeftOpenArm.f_104205_;
                        } else {
                            armorModel.f_102812_.f_104203_ = guardModel.MiddleClosedArm.f_104203_;
                            armorModel.f_102812_.f_104204_ = guardModel.MiddleClosedArm.f_104204_;
                            armorModel.f_102812_.f_104205_ = guardModel.MiddleClosedArm.f_104205_;
                        }
                        
                        this.setPartVisibility(armorModel, slot);
                        
                        net.minecraft.client.model.Model customModel = this.getArmorModelHook(entity, itemStack, slot, armorModel);
                        boolean glint = itemStack.m_41790_();
                        boolean inner = this.usesInnerModel(slot);
                        
                        if (armorItem instanceof net.minecraft.world.item.DyeableLeatherItem) {
                            int color = ((net.minecraft.world.item.DyeableLeatherItem) armorItem).m_41121_(itemStack);
                            float r = (float)(color >> 16 & 255) / 255.0F;
                            float g = (float)(color >> 8 & 255) / 255.0F;
                            float b = (float)(color & 255) / 255.0F;
                            
                            this.renderModel(poseStack, buffer, packedLight, armorItem, customModel, inner, r, g, b, this.getArmorResource(entity, itemStack, slot, null));
                            this.renderModel(poseStack, buffer, packedLight, armorItem, customModel, inner, 1.0F, 1.0F, 1.0F, this.getArmorResource(entity, itemStack, slot, "overlay"));
                        } else {
                            this.renderModel(poseStack, buffer, packedLight, armorItem, customModel, inner, 1.0F, 1.0F, 1.0F, this.getArmorResource(entity, itemStack, slot, null));
                        }
                        
                        if (glint) {
                            this.renderGlint(poseStack, buffer, packedLight, customModel);
                        }
                    }
                }
            }
        }
    }
}
