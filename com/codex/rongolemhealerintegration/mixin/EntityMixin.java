package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.unit.units.monsters.ZombiePiglinUnit;
import com.solegendary.reignofnether.unit.interfaces.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = net.minecraft.world.entity.Entity.class, remap = false)
public abstract class EntityMixin {

    @Inject(method = "isAlliedTo", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void ronGolemHealerIntegration$onIsAlliedToMojang(net.minecraft.world.entity.Entity other, CallbackInfoReturnable<Boolean> cir) {
        ronGolemHealerIntegration$checkAlliance(other, cir);
    }

    @Inject(method = "m_7307_", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void ronGolemHealerIntegration$onIsAlliedToSrg(net.minecraft.world.entity.Entity other, CallbackInfoReturnable<Boolean> cir) {
        ronGolemHealerIntegration$checkAlliance(other, cir);
    }

    private void ronGolemHealerIntegration$checkAlliance(net.minecraft.world.entity.Entity other, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof ZombiePiglinUnit && other instanceof Unit) {
            Unit otherUnit = (Unit) other;
            ZombiePiglinUnit self = (ZombiePiglinUnit) (Object) this;
            String myOwner = self.getOwnerName();
            String otherOwner = otherUnit.getOwnerName();
            System.out.println("[RON_INTEGRATION_DEBUG] ZombiePiglinUnit isAlliedTo check: selfOwner='" + myOwner + "', otherOwner='" + otherOwner + "'");
            if (!myOwner.isBlank() && !otherOwner.isBlank() && !myOwner.equals(otherOwner)) {
                System.out.println("[RON_INTEGRATION_DEBUG] Different owners! Returning false.");
                cir.setReturnValue(false);
            }
        }
    }
}
