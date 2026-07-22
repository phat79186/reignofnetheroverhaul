/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.solegendary.reignofnether.registrars.MobEffectRegistrar
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  org.spongepowered.asm.mixin.Mixin
 */
package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.ability.PillagerGunEnchantBridge;
import com.solegendary.reignofnether.hud.passives.EnchantmentIcon;
import com.solegendary.reignofnether.hud.passives.PassiveIcons;
import com.solegendary.reignofnether.registrars.MobEffectRegistrar;
import com.solegendary.reignofnether.unit.interfaces.Unit;
import com.solegendary.reignofnether.unit.units.villagers.PillagerUnit;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={PillagerUnit.class}, remap=false)
public abstract class PillagerUnitMixin
implements Unit {
    private static EnchantmentIcon CUSTOM_MULTISHOT = null;
    private static EnchantmentIcon CUSTOM_QUICK_CHARGE = null;
    private static EnchantmentIcon CUSTOM_PIERCING = null;

    @org.spongepowered.asm.mixin.injection.Inject(method = "setupEquipmentAndUpgradesServer", at = @org.spongepowered.asm.mixin.injection.At("HEAD"), cancellable = true, remap = false)
    private void onSetupEquipmentAndUpgradesServer(org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        PillagerUnit self = (PillagerUnit) (Object) this;
        try {
            Class<?> forgeRegistriesClass = Class.forName("net.minecraftforge.registries.ForgeRegistries");
            Object itemsRegistry = forgeRegistriesClass.getField("ITEMS").get(null);
            Class<?> registryClass = Class.forName("net.minecraftforge.registries.IForgeRegistry");
            Method getValueMethod = registryClass.getMethod("getValue", net.minecraft.resources.ResourceLocation.class);
            
            Object taczItem = getValueMethod.invoke(itemsRegistry, new net.minecraft.resources.ResourceLocation("tacz:modern_kinetic_gun"));
            if (taczItem != null) {
                net.minecraft.world.item.ItemStack gunStack = new net.minecraft.world.item.ItemStack((net.minecraft.world.level.ItemLike) taczItem);
                
                Class<?> igunClass = Class.forName("com.tacz.guns.api.item.IGun");
                Object iGun = igunClass.getMethod("getIGunOrNull", net.minecraft.world.item.ItemStack.class).invoke(null, gunStack);
                if (iGun != null) {
                    igunClass.getMethod("setGunId", net.minecraft.world.item.ItemStack.class, net.minecraft.resources.ResourceLocation.class)
                        .invoke(iGun, gunStack, new net.minecraft.resources.ResourceLocation("tacz:springfield1873"));
                    
                    Class<?> timelessApiClass = Class.forName("com.tacz.guns.api.TimelessAPI");
                    java.util.Optional<?> commonGunIndexOpt = (java.util.Optional<?>) timelessApiClass.getMethod("getCommonGunIndex", net.minecraft.resources.ResourceLocation.class)
                        .invoke(null, new net.minecraft.resources.ResourceLocation("tacz:springfield1873"));
                    
                    if (commonGunIndexOpt.isPresent()) {
                        Object commonGunIndex = commonGunIndexOpt.get();
                        Object gunData = commonGunIndex.getClass().getMethod("getGunData").invoke(commonGunIndex);
                        
                        Class<?> attachmentDataUtilsClass = Class.forName("com.tacz.guns.util.AttachmentDataUtils");
                        int maxAmmoCount = ((Number) attachmentDataUtilsClass.getMethod("getAmmoCountWithAttachment", net.minecraft.world.item.ItemStack.class, gunData.getClass())
                            .invoke(null, gunStack, gunData)).intValue();
                        
                        igunClass.getMethod("setCurrentAmmoCount", net.minecraft.world.item.ItemStack.class, Integer.TYPE)
                            .invoke(iGun, gunStack, maxAmmoCount);
                        
                        java.util.List<?> fireModeSet = (java.util.List<?>) gunData.getClass().getMethod("getFireModeSet").invoke(gunData);
                        if (!fireModeSet.isEmpty()) {
                            igunClass.getMethod("setFireMode", net.minecraft.world.item.ItemStack.class, Class.forName("com.tacz.guns.api.item.gun.FireMode"))
                                .invoke(iGun, gunStack, fireModeSet.get(0));
                        }
                        
                        igunClass.getMethod("setBulletInBarrel", net.minecraft.world.item.ItemStack.class, Boolean.TYPE)
                            .invoke(iGun, gunStack, true);
                    }
                }
                
                self.m_8061_(net.minecraft.world.entity.EquipmentSlot.MAINHAND, gunStack);
                
                // Equip ammo box in offhand
                Object ammoBoxItem = getValueMethod.invoke(itemsRegistry, new net.minecraft.resources.ResourceLocation("tacz:ammo_box"));
                if (ammoBoxItem != null) {
                    net.minecraft.world.item.ItemStack ammoBoxStack = new net.minecraft.world.item.ItemStack((net.minecraft.world.level.ItemLike) ammoBoxItem);
                    ammoBoxStack.m_41784_().m_128379_("AllTypeCreative", true);
                    self.m_8061_(net.minecraft.world.entity.EquipmentSlot.OFFHAND, ammoBoxStack);
                }
                
                ci.cancel();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public List<EnchantmentIcon> getPassiveIcons() {
        ArrayList<EnchantmentIcon> arrayList = new ArrayList<EnchantmentIcon>();
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        for (EnchantmentIcon object : PassiveIcons.ENCHANTMENT_ICONS) {
            Map<Enchantment, Integer> map;
            ItemStack itemStack = livingEntity.m_6844_(object.slot);
            if (itemStack == null || (map = EnchantmentHelper.m_44831_((ItemStack)itemStack)) == null) continue;
            for (Enchantment enchantment : map.keySet()) {
                if (enchantment != object.enchantment) continue;
                arrayList.add(object);
            }
        }
        if (livingEntity.m_21023_((MobEffect)MobEffectRegistrar.TEMPORARY_EFFICIENCY.get())) {
            arrayList.add(PassiveIcons.EFFICIENCY);
        }
        if (this.hasAnyEnchants() && livingEntity.m_21023_((MobEffect)MobEffectRegistrar.ENCHANTMENT_AMPLIFIER.get())) {
            arrayList.add(PassiveIcons.ENCHANTMENT_AMPLIFIER);
        }
        try {
            Method method = PillagerGunEnchantBridge.class.getDeclaredMethod("getInstalledExtendedMagId", Object.class);
            method.setAccessible(true);
            String string = (String)method.invoke(null, this);
            if ("tacz:ammo_mod_he".equals(string)) {
                arrayList.remove(PassiveIcons.MULTISHOT);
                if (CUSTOM_MULTISHOT == null) {
                    CUSTOM_MULTISHOT = new EnchantmentIcon(PassiveIcons.MULTISHOT.enchantment, PassiveIcons.MULTISHOT.slot, new ResourceLocation("reignofnether", "textures/icons/items/ammo_mod_he.png"), (List<FormattedCharSequence>)PassiveIcons.MULTISHOT.tooltipLines);
                }
                arrayList.add(CUSTOM_MULTISHOT);
            } else if ("tacz:ammo_mod_i".equals(string)) {
                arrayList.remove(PassiveIcons.QUICK_CHARGE);
                if (CUSTOM_QUICK_CHARGE == null) {
                    CUSTOM_QUICK_CHARGE = new EnchantmentIcon(PassiveIcons.QUICK_CHARGE.enchantment, PassiveIcons.QUICK_CHARGE.slot, new ResourceLocation("reignofnether", "textures/icons/items/ammo_mod_i.png"), (List<FormattedCharSequence>)PassiveIcons.QUICK_CHARGE.tooltipLines);
                }
                arrayList.add(CUSTOM_QUICK_CHARGE);
            } else if ("tacz:ammo_mod_hp".equals(string)) {
                arrayList.remove(PassiveIcons.PIERCING);
                if (CUSTOM_PIERCING == null) {
                    CUSTOM_PIERCING = new EnchantmentIcon(PassiveIcons.PIERCING.enchantment, PassiveIcons.PIERCING.slot, new ResourceLocation("reignofnether", "textures/icons/items/ammo_mod_hp.png"), (List<FormattedCharSequence>)PassiveIcons.PIERCING.tooltipLines);
                }
                arrayList.add(CUSTOM_PIERCING);
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return arrayList;
    }
}
