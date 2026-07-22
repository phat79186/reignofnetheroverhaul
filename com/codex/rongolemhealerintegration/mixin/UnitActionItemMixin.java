package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.unit.UnitActionItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {UnitActionItem.class}, remap = false)
public abstract class UnitActionItemMixin {

    @Shadow @Final private com.solegendary.reignofnether.unit.UnitAction action;
    @Shadow @Final private int unitId;
    @Shadow @Final private int[] unitIds;
    @Shadow @Final private net.minecraft.core.BlockPos preselectedBlockPos;

    @Inject(method = "action", at = @At("HEAD"), cancellable = true)
    public void onAction(net.minecraft.world.level.Level level, CallbackInfo ci) {
        if (this.unitId == -1) {
            if ((this.action == com.solegendary.reignofnether.unit.UnitAction.ATTACK || this.action == com.solegendary.reignofnether.unit.UnitAction.ATTACK_MOVE || this.action == com.solegendary.reignofnether.unit.UnitAction.ATTACK_BUILDING) && this.preselectedBlockPos != null) {
                boolean handledAny = false;
                for (int id : this.unitIds) {
                    net.minecraft.world.entity.Entity entity = level.m_6815_(id);
                    if (entity instanceof net.minecraft.world.entity.LivingEntity) {
                        net.minecraft.world.entity.LivingEntity le = (net.minecraft.world.entity.LivingEntity) entity;
                        if (le.m_20159_() && le.m_20202_() instanceof com.codex.rongolemhealerintegration.ron.FieldCannonEntityAccess) {
                            ((com.codex.rongolemhealerintegration.ron.FieldCannonEntityAccess) le.m_20202_()).ron$setTargetBlockPos(this.preselectedBlockPos);
                            handledAny = true;
                        }
                    }
                }
                if (handledAny) {
                    ci.cancel();
                }
            } else if (this.action == com.solegendary.reignofnether.unit.UnitAction.MOVE || this.action == com.solegendary.reignofnether.unit.UnitAction.STOP || this.action == com.solegendary.reignofnether.unit.UnitAction.HOLD) {
                for (int id : this.unitIds) {
                    net.minecraft.world.entity.Entity entity = level.m_6815_(id);
                    if (entity instanceof net.minecraft.world.entity.LivingEntity) {
                        net.minecraft.world.entity.LivingEntity le = (net.minecraft.world.entity.LivingEntity) entity;
                        if (le.m_20159_() && le.m_20202_() instanceof com.codex.rongolemhealerintegration.ron.FieldCannonEntityAccess) {
                            ((com.codex.rongolemhealerintegration.ron.FieldCannonEntityAccess) le.m_20202_()).ron$setTargetBlockPos(null);
                        }
                    }
                }
            }
        }
    }
}
