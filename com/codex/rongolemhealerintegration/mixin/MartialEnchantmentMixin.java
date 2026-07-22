package com.codex.rongolemhealerintegration.mixin;

import com.min01.guardillagers.entity.GuardIllager;
import baguchan.slash_illager.entity.BladeMaster;
import com.solegendary.reignofnether.ability.heroAbilities.enchanter.MartialEnchantment;
import com.solegendary.reignofnether.hud.HudClientEvents;
import com.solegendary.reignofnether.unit.interfaces.Unit;
import com.solegendary.reignofnether.unit.units.villagers.EnchanterUnit;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(value = MartialEnchantment.class, remap = false)
public class MartialEnchantmentMixin {

    @Inject(method = "getEnchantmentForUnit", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onGetEnchantmentForUnit(LivingEntity unit, CallbackInfoReturnable<Enchantment> cir) {
        if (unit instanceof GuardIllager) {
            // Guard Illager gets Sharpness I (Sắc bén I)
            cir.setReturnValue(Enchantments.f_44977_);
        } else if (unit instanceof BladeMaster) {
            // Blade Master gets Fire Aspect I (Trảm lửa I)
            cir.setReturnValue(Enchantments.f_44983_);
        }
    }

    @Inject(method = "canEnchant", at = @At("HEAD"), cancellable = true, remap = false)
    private void onCanEnchant(LivingEntity le, CallbackInfoReturnable<Boolean> cir) {
        if (le instanceof GuardIllager || le instanceof BladeMaster) {
            if (le instanceof Unit) {
                ItemStack weapon = le.m_6844_(EquipmentSlot.MAINHAND);
                if (!weapon.m_41619_()) {
                    Enchantment enchantment = MartialEnchantment.getEnchantmentForUnit(le);
                    if (enchantment != null) {
                        Map<Enchantment, Integer> map = EnchantmentHelper.m_44831_(weapon);
                        cir.setReturnValue(!map.containsKey(enchantment));
                        return;
                    }
                }
            }
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true, remap = false)
    private void onUse(Level level, Unit unitUsing, LivingEntity targetEntity, CallbackInfo ci) {
        if (targetEntity instanceof GuardIllager || targetEntity instanceof BladeMaster) {
            if (targetEntity.m_6844_(EquipmentSlot.MAINHAND).m_41619_()) {
                if (level.m_5776_()) {
                    HudClientEvents.showTemporaryMessage(I18n.m_118938_("ability.reignofnether.enchant.error7"));
                }
                ci.cancel();
                return;
            }
            Enchantment enchantment = MartialEnchantment.getEnchantmentForUnit(targetEntity);
            if (enchantment != null && EnchantmentHelper.m_44831_(targetEntity.m_6844_(EquipmentSlot.MAINHAND)).containsKey(enchantment)) {
                if (level.m_5776_()) {
                    HudClientEvents.showTemporaryMessage(I18n.m_118938_("ability.reignofnether.enchant.error4"));
                }
                ci.cancel();
                return;
            }
            if (unitUsing instanceof EnchanterUnit) {
                EnchanterUnit enchanter = (EnchanterUnit) unitUsing;
                enchanter.getCastEnchantMartialGoal().setAbility((MartialEnchantment) (Object) this);
                enchanter.getCastEnchantMartialGoal().setTarget(targetEntity);
            }
            ci.cancel();
        }
    }
}
