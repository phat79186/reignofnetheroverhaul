package com.codex.rongolemhealerintegration.mixin;

import fuzs.mutantmonsters.world.entity.mutant.MutantZombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.List;

@Mixin(targets = "fuzs.mutantmonsters.world.entity.mutant.MutantZombie$RoarGoal", remap = false)
public abstract class MutantZombieRoarGoalMixin {
    @Redirect(
        method = "m_8037_",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;add(Ljava/lang/Object;)Z"
        ),
        remap = false
    )
    private boolean ronGolemHealerIntegration$redirectAdd(List list, Object obj) {
        if (list.isEmpty()) {
            return list.add(obj);
        }
        return false;
    }
}
