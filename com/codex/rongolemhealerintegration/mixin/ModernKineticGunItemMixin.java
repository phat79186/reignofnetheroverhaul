package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.ability.PillagerGunEnchantBridge;
import com.tacz.guns.api.entity.ReloadState;
import com.tacz.guns.item.ModernKineticGunItem;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ModernKineticGunItem.class, remap = false)
public abstract class ModernKineticGunItemMixin {

    @Shadow
    private void defaultReloadFinishing(ModernKineticGunScriptAPI api, boolean isTactical) {}

    @Inject(method = "defaultTickReload", at = @At("HEAD"), cancellable = true, remap = false)
    private void onDefaultTickReload(ModernKineticGunScriptAPI api, CallbackInfoReturnable<ReloadState> cir) {
        LivingEntity shooter = api.getShooter();
        if (shooter != null && PillagerGunEnchantBridge.isSpringfieldPillager(shooter)) {
            long feedTime = 4000L; // 4 seconds ammo feed phase
            long finishingTime = 5000L; // 5 seconds total reload duration
            long progressTime = api.getReloadTime();

            ReloadState.StateType oldStateType = ReloadState.StateType.values()[api.getReloadStateType()];
            ReloadState.StateType stateType;
            long countDown;

            if (oldStateType.isReloadingEmpty()) {
                if (progressTime < feedTime) {
                    stateType = ReloadState.StateType.EMPTY_RELOAD_FEEDING;
                    countDown = feedTime - progressTime;
                } else if (progressTime < finishingTime) {
                    stateType = ReloadState.StateType.EMPTY_RELOAD_FINISHING;
                    countDown = finishingTime - progressTime;
                } else {
                    stateType = ReloadState.StateType.NOT_RELOADING;
                    countDown = -1L;
                }
            } else if (oldStateType.isReloadingTactical()) {
                if (progressTime < feedTime) {
                    stateType = ReloadState.StateType.TACTICAL_RELOAD_FEEDING;
                    countDown = feedTime - progressTime;
                } else if (progressTime < finishingTime) {
                    stateType = ReloadState.StateType.TACTICAL_RELOAD_FINISHING;
                    countDown = finishingTime - progressTime;
                } else {
                    stateType = ReloadState.StateType.NOT_RELOADING;
                    countDown = -1L;
                }
            } else {
                stateType = ReloadState.StateType.NOT_RELOADING;
                countDown = -1L;
            }

            if (oldStateType == ReloadState.StateType.EMPTY_RELOAD_FEEDING && oldStateType != stateType) {
                this.defaultReloadFinishing(api, false);
            }
            if (oldStateType == ReloadState.StateType.TACTICAL_RELOAD_FEEDING && oldStateType != stateType) {
                this.defaultReloadFinishing(api, true);
            }

            ReloadState reloadState = new ReloadState();
            reloadState.setStateType(stateType);
            reloadState.setCountDown(countDown);
            cir.setReturnValue(reloadState);
        }
    }
}
