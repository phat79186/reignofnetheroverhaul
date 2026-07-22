package com.codex.rongolemhealerintegration.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.projectile.ProjectilePatch;

@Mixin(value = ProjectilePatch.class, remap = false)
public abstract class ProjectilePatchMixin<T extends Projectile> {

    @Inject(method = "onAddedToWorld", at = @At("HEAD"), cancellable = true)
    private void ronGolemHealerIntegration$onAddedToWorld(CallbackInfo ci) {
        EntityPatch<?> entityPatch = (EntityPatch<?>)(Object)this;
        Entity projectile = entityPatch.getOriginal();
        if (projectile instanceof Projectile) {
            Entity shooter = ((Projectile) projectile).m_19749_(); // getOwner()
            if (shooter instanceof com.solegendary.reignofnether.unit.units.piglins.HeadhunterUnit) {
                ci.cancel();
            }
        }
    }
}
