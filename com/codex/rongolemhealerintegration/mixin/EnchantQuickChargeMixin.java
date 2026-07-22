package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.ability.abilities.EnchantQuickCharge;
import com.solegendary.reignofnether.ability.PillagerGunEnchantBridge;
import com.solegendary.reignofnether.keybinds.Keybinding;
import com.solegendary.reignofnether.building.BuildingPlacement;
import com.solegendary.reignofnether.hud.AbilityButton;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EnchantQuickCharge.class, remap = false)
public class EnchantQuickChargeMixin {
    @Inject(method = "isCorrectUnitAndEquipment", at = @At("HEAD"), cancellable = true)
    private void onIsCorrectUnitAndEquipment(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (PillagerGunEnchantBridge.isSpringfieldPillager(livingEntity)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getButton", at = @At("RETURN"), cancellable = true)
    private void onGetButton(Keybinding hotkey, BuildingPlacement placement, CallbackInfoReturnable<AbilityButton> cir) {
        AbilityButton button = cir.getReturnValue();
        if (button != null) {
            button.iconResource = new ResourceLocation("reignofnether", "textures/icons/items/ammo_mod_i.png");
        }
    }
}
