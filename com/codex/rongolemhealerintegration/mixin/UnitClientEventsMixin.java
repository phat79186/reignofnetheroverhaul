package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.unit.UnitClientEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityMountEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(value = {UnitClientEvents.class}, remap = false)
public abstract class UnitClientEventsMixin {

    @Shadow private static ArrayList<LivingEntity> preselectedUnits;
    @Shadow private static ArrayList<LivingEntity> selectedUnits;

    @Shadow public static void markSelectedUnitsChanged() {}

    @Redirect(method = "addPreselectedUnit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;m_20159_()Z"))
    private static boolean ron$redirectIsPassengerPre(LivingEntity entity) {
        if (entity.m_20159_() && entity.m_20202_() instanceof com.camdenscottc.colonialcannons.entity.FieldCannonEntity) {
            return false;
        }
        return entity.m_20159_();
    }

    @Redirect(method = "addSelectedUnit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;m_20159_()Z"))
    private static boolean ron$redirectIsPassengerSel(LivingEntity entity) {
        if (entity.m_20159_() && entity.m_20202_() instanceof com.camdenscottc.colonialcannons.entity.FieldCannonEntity) {
            return false;
        }
        return entity.m_20159_();
    }

    @Redirect(method = "onRenderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;m_20159_()Z"))
    private static boolean ron$redirectIsPassengerRender(LivingEntity entity) {
        if (entity.m_20159_() && entity.m_20202_() instanceof com.camdenscottc.colonialcannons.entity.FieldCannonEntity) {
            return false;
        }
        return entity.m_20159_();
    }

    @Inject(method = "onEntityMount", at = @At("HEAD"), cancellable = true)
    private static void ron$onEntityMount(EntityMountEvent evt, CallbackInfo ci) {
        if (evt.getLevel().m_5776_() && evt.isMounting() && evt.getEntityBeingMounted() instanceof com.camdenscottc.colonialcannons.entity.FieldCannonEntity) {
            markSelectedUnitsChanged();
            ci.cancel();
        }
    }
}
