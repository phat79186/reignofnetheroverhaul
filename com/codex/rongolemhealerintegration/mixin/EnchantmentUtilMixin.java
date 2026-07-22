package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.util.EnchantmentUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = EnchantmentUtil.class, remap = false)
public class EnchantmentUtilMixin {

    @Inject(method = "updateEnchantLevels", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onUpdateEnchantLevels(LivingEntity entity, boolean regularLevels, CallbackInfo ci) {
        if (entity == null) return;
        ci.cancel();
        EquipmentSlot[] armorSlots = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (EquipmentSlot slot : armorSlots) {
            ItemStack armorItem = entity.m_6844_(slot);
            if (!armorItem.m_41619_()) {
                Map armorEnchants = EnchantmentHelper.m_44831_(armorItem);
                if (!armorEnchants.isEmpty()) {
                    armorEnchants.replaceAll((enchant, level) -> EnchantmentUtil.getRegularEnchantLevel((Enchantment) enchant, armorItem) * (regularLevels ? 1 : 2));
                    EnchantmentHelper.m_44865_((Map) armorEnchants, (ItemStack) armorItem);
                }
            }
        }
        ItemStack mainhandItem = entity.m_6844_(EquipmentSlot.MAINHAND);
        if (!mainhandItem.m_41619_()) {
            Map mainhandEnchants = EnchantmentHelper.m_44831_(mainhandItem);
            if (!mainhandEnchants.isEmpty()) {
                mainhandEnchants.replaceAll((enchant, level) -> EnchantmentUtil.getRegularEnchantLevel((Enchantment) enchant, mainhandItem) * (regularLevels ? 1 : 2));
                EnchantmentHelper.m_44865_((Map) mainhandEnchants, (ItemStack) mainhandItem);
            }
        }
    }
}
