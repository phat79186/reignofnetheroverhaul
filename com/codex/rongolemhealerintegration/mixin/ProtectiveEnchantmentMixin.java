package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.ability.heroAbilities.enchanter.ProtectiveEnchantment;
import com.solegendary.reignofnether.hud.HudClientEvents;
import com.solegendary.reignofnether.registrars.EnchantmentRegistrar;
import com.solegendary.reignofnether.unit.interfaces.Unit;
import com.solegendary.reignofnether.unit.units.villagers.EnchanterUnit;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ProtectiveEnchantment.class, remap = false)
public class ProtectiveEnchantmentMixin {

    @Inject(method = "canEnchant", at = @At("HEAD"), cancellable = true, remap = false)
    private void onCanEnchant(LivingEntity le, CallbackInfoReturnable<Boolean> cir) {
        if (le == null || !(le instanceof Unit) || EnchantmentRegistrar.FORTYIFYING == null || !EnchantmentRegistrar.FORTYIFYING.isPresent()) {
            cir.setReturnValue(false);
            return;
        }
        Enchantment fortifying = (Enchantment) EnchantmentRegistrar.FORTYIFYING.get();
        boolean hasArmor = false;
        boolean hasUnenchantedArmor = false;
        EquipmentSlot[] armorSlots = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (EquipmentSlot slot : armorSlots) {
            ItemStack stack = le.m_6844_(slot);
            if (!stack.m_41619_()) {
                hasArmor = true;
                if (EnchantmentHelper.m_44843_(fortifying, stack) == 0) {
                    hasUnenchantedArmor = true;
                }
            }
        }
        cir.setReturnValue(hasArmor && hasUnenchantedArmor);
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true, remap = false)
    private void onUse(Level level, Unit unitUsing, LivingEntity targetEntity, CallbackInfo ci) {
        if (targetEntity == null || EnchantmentRegistrar.FORTYIFYING == null || !EnchantmentRegistrar.FORTYIFYING.isPresent()) {
            return;
        }
        ci.cancel();
        Enchantment fortifying = (Enchantment) EnchantmentRegistrar.FORTYIFYING.get();
        boolean hasArmor = false;
        boolean hasUnenchantedArmor = false;
        EquipmentSlot[] armorSlots = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (EquipmentSlot slot : armorSlots) {
            ItemStack stack = targetEntity.m_6844_(slot);
            if (!stack.m_41619_()) {
                hasArmor = true;
                if (EnchantmentHelper.m_44843_(fortifying, stack) == 0) {
                    hasUnenchantedArmor = true;
                }
            }
        }
        if (!hasArmor) {
            if (level.m_5776_()) {
                HudClientEvents.showTemporaryMessage(I18n.m_118938_("ability.reignofnether.enchant.error6", new Object[0]));
            }
            return;
        }
        if (!hasUnenchantedArmor) {
            if (level.m_5776_()) {
                HudClientEvents.showTemporaryMessage(I18n.m_118938_("ability.reignofnether.enchant.error4", new Object[0]));
            }
            return;
        }
        ProtectiveEnchantment self = (ProtectiveEnchantment) (Object) this;
        ((EnchanterUnit) unitUsing).getCastEnchantProtectiveGoal().setAbility(self);
        ((EnchanterUnit) unitUsing).getCastEnchantProtectiveGoal().setTarget(targetEntity);
    }
}
