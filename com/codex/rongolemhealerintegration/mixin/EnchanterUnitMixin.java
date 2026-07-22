package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.ability.PillagerGunEnchantBridge;
import com.solegendary.reignofnether.registrars.EnchantmentRegistrar;
import com.solegendary.reignofnether.unit.units.villagers.EnchanterUnit;
import java.lang.reflect.Method;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {EnchanterUnit.class}, remap = false)
public abstract class EnchanterUnitMixin {

    @Shadow(remap = false)
    private void playEnchantSound() {
        throw new AbstractMethodError();
    }

    @Inject(method = {"enchantMilitary(Lnet/minecraft/world/entity/LivingEntity;)V"}, at = {@At(value = "TAIL")})
    private void onEnchantMilitary(LivingEntity livingEntity, CallbackInfo callbackInfo) {
        if (livingEntity != null && livingEntity.getClass().getName().endsWith(".PillagerUnit")) {
            try {
                Method method = PillagerGunEnchantBridge.class.getDeclaredMethod("isSpringfieldPillager", Object.class);
                method.setAccessible(true);
                boolean bl = (Boolean) method.invoke(null, livingEntity);
                if (bl) {
                    Method method2 = PillagerGunEnchantBridge.class.getDeclaredMethod("installExtendedMag", Object.class, String.class);
                    method2.setAccessible(true);
                    method2.invoke(null, livingEntity, "tacz:ammo_mod_hp");
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    @Inject(method = "enchantArmour(Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true, remap = false)
    private void onEnchantArmour(LivingEntity entity, CallbackInfo ci) {
        ci.cancel();
        if (entity != null && !entity.m_9236_().f_46443_ && EnchantmentRegistrar.FORTYIFYING != null && EnchantmentRegistrar.FORTYIFYING.isPresent()) {
            Enchantment fortifying = (Enchantment) EnchantmentRegistrar.FORTYIFYING.get();
            boolean enchantedAny = false;
            EquipmentSlot[] armorSlots = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
            for (EquipmentSlot slot : armorSlots) {
                ItemStack stack = entity.m_6844_(slot);
                if (!stack.m_41619_()) {
                    stack.m_41663_(fortifying, 1);
                    enchantedAny = true;
                }
            }
            if (enchantedAny) {
                this.playEnchantSound();
                EnchanterUnit self = (EnchanterUnit) (Object) this;
                if (self.getHeroLevel() < 5) {
                    self.addExperience(40);
                }
            }
        }
    }
}
