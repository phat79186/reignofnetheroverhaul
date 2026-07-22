/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.tacz.guns.api.TimelessAPI
 *  com.tacz.guns.api.client.other.ThirdPersonManager
 *  com.tacz.guns.api.entity.IGunOperator
 *  com.tacz.guns.client.resource.GunDisplayInstance
 *  net.minecraft.client.model.IllagerModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.monster.AbstractIllager
 *  net.minecraft.world.entity.monster.Pillager
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.registries.ForgeRegistries
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.codex.rongolemhealerintegration.mixin;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.other.ThirdPersonManager;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.client.resource.GunDisplayInstance;
import java.util.Optional;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={IllagerModel.class}, remap=false)
public abstract class IllagerGunModelMixin<T extends AbstractIllager> {
    @Shadow
    @Final
    private ModelPart f_102901_;
    @Shadow
    @Final
    private ModelPart f_102908_;
    @Shadow
    @Final
    private ModelPart f_102907_;
    @Shadow
    @Final
    private ModelPart f_102904_;
    private ModelPart body;

    @Inject(method={"<init>"}, at={@At(value="TAIL")})
    private void getBody(ModelPart modelPart, CallbackInfo callbackInfo) {
        this.body = modelPart.m_171324_("body");
    }

    @Inject(method={"setupAnim", "m_6973_"}, at={@At(value="TAIL")})
    private void onSetupAnim(T t, float f, float f2, float f3, float f4, float f5, CallbackInfo callbackInfo) {
        if (t.m_21205_() != null && "tacz".equals(ForgeRegistries.ITEMS.getKey((net.minecraft.world.item.Item)t.m_21205_().m_41720_()).m_135827_())) {
            this.f_102904_.f_104207_ = false;
            this.f_102907_.f_104207_ = true;
            this.f_102908_.f_104207_ = true;
            
            com.codex.rongolemhealerintegration.client.PillagerGunAnimationHelper.applyGunAnimations(
                t, this.f_102907_, this.f_102908_, this.body, this.f_102901_, f, f2, f3
            );
        }
    }
}

