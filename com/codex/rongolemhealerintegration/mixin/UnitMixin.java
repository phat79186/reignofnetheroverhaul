package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.ability.PillagerGunEnchantBridge;
import com.solegendary.reignofnether.hud.passives.EnchantmentIcon;
import com.solegendary.reignofnether.hud.passives.PassiveIcons;
import com.solegendary.reignofnether.registrars.EnchantmentRegistrar;
import com.solegendary.reignofnether.unit.interfaces.Unit;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {Unit.class}, remap = false)
public interface UnitMixin {
    class Holder {
        static final EnchantmentIcon CUSTOM_MULTISHOT = new EnchantmentIcon(PassiveIcons.MULTISHOT.enchantment, PassiveIcons.MULTISHOT.slot, new net.minecraft.resources.ResourceLocation("reignofnether", "textures/icons/items/ammo_mod_he.png"), (List<net.minecraft.util.FormattedCharSequence>) PassiveIcons.MULTISHOT.tooltipLines);
        static final EnchantmentIcon CUSTOM_QUICK_CHARGE = new EnchantmentIcon(PassiveIcons.QUICK_CHARGE.enchantment, PassiveIcons.QUICK_CHARGE.slot, new net.minecraft.resources.ResourceLocation("reignofnether", "textures/icons/items/ammo_mod_i.png"), (List<net.minecraft.util.FormattedCharSequence>) PassiveIcons.QUICK_CHARGE.tooltipLines);
        static final EnchantmentIcon CUSTOM_PIERCING = new EnchantmentIcon(PassiveIcons.PIERCING.enchantment, PassiveIcons.PIERCING.slot, new net.minecraft.resources.ResourceLocation("reignofnether", "textures/icons/items/ammo_mod_hp.png"), (List<net.minecraft.util.FormattedCharSequence>) PassiveIcons.PIERCING.tooltipLines);
    }

    @Inject(method = {"getPassiveIcons"}, at = {@At(value = "RETURN")}, cancellable = true, remap = false)
    default public void onGetPassiveIcons(CallbackInfoReturnable<List<EnchantmentIcon>> callbackInfoReturnable) {
        List<EnchantmentIcon> list = callbackInfoReturnable.getReturnValue();
        ArrayList<EnchantmentIcon> arrayList = null;

        if (PillagerGunEnchantBridge.isSpringfieldPillager((Object) this)) {
            arrayList = new ArrayList<EnchantmentIcon>(list);
            try {
                Method method = PillagerGunEnchantBridge.class.getDeclaredMethod("getInstalledExtendedMagId", Object.class);
                method.setAccessible(true);
                String string = (String) method.invoke(null, this);
                if ("tacz:ammo_mod_he".equals(string)) {
                    arrayList.remove(PassiveIcons.MULTISHOT);
                    arrayList.add(Holder.CUSTOM_MULTISHOT);
                } else if ("tacz:ammo_mod_i".equals(string)) {
                    arrayList.remove(PassiveIcons.QUICK_CHARGE);
                    arrayList.add(Holder.CUSTOM_QUICK_CHARGE);
                } else if ("tacz:ammo_mod_hp".equals(string)) {
                    arrayList.remove(PassiveIcons.PIERCING);
                    arrayList.add(Holder.CUSTOM_PIERCING);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        if (this instanceof LivingEntity livingEntity && EnchantmentRegistrar.FORTYIFYING != null && EnchantmentRegistrar.FORTYIFYING.isPresent()) {
            Enchantment fortifying = (Enchantment) EnchantmentRegistrar.FORTYIFYING.get();
            boolean hasFortifyingOnAnySlot = false;
            EquipmentSlot[] armorSlots = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
            for (EquipmentSlot slot : armorSlots) {
                if (EnchantmentHelper.m_44843_(fortifying, livingEntity.m_6844_(slot)) > 0) {
                    hasFortifyingOnAnySlot = true;
                    break;
                }
            }
            if (hasFortifyingOnAnySlot) {
                if (arrayList == null) {
                    arrayList = new ArrayList<EnchantmentIcon>(list);
                }
                if (!arrayList.contains(PassiveIcons.FORTIFYING)) {
                    arrayList.add(PassiveIcons.FORTIFYING);
                }
            }
        }

        if (arrayList != null) {
            callbackInfoReturnable.setReturnValue(arrayList);
        }
    }

    @Inject(method = "hasAnyEnchants", at = @At("HEAD"), cancellable = true, remap = false)
    default void onHasAnyEnchants(CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof LivingEntity le) {
            if (!EnchantmentHelper.m_44831_(le.m_21205_()).isEmpty()) {
                cir.setReturnValue(true);
                return;
            }
            EquipmentSlot[] armorSlots = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
            for (EquipmentSlot slot : armorSlots) {
                if (!EnchantmentHelper.m_44831_(le.m_6844_(slot)).isEmpty()) {
                    cir.setReturnValue(true);
                    return;
                }
            }
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), remap = false)
    public static void onTick(Unit unit, CallbackInfo ci) {
        if (unit instanceof Mob unitMob && EnchantmentRegistrar.FORTYIFYING != null && EnchantmentRegistrar.FORTYIFYING.isPresent()) {
            if (unitMob.f_19797_ % 80 == 0) {
                Enchantment fortifying = (Enchantment) EnchantmentRegistrar.FORTYIFYING.get();
                if (EnchantmentHelper.m_44843_(fortifying, unitMob.m_6844_(EquipmentSlot.CHEST)) == 0) {
                    int fortifyingLevel = 0;
                    EquipmentSlot[] nonChestSlots = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.LEGS, EquipmentSlot.FEET};
                    for (EquipmentSlot slot : nonChestSlots) {
                        int lvl = EnchantmentHelper.m_44843_(fortifying, unitMob.m_6844_(slot));
                        if (lvl > fortifyingLevel) {
                            fortifyingLevel = lvl;
                        }
                    }
                    float absorbHp = unitMob.m_6103_();
                    if (fortifyingLevel > 0 && absorbHp < (float) fortifyingLevel * 20.0f) {
                        unitMob.m_7911_(absorbHp + 1.0f);
                    }
                }
            }
        }
    }

    @Inject(method = "setMoveTarget", at = @At("HEAD"))
    default void onSetMoveTarget(net.minecraft.core.BlockPos bp, CallbackInfo ci) {
        if (this instanceof net.minecraft.world.entity.Entity) {
            net.minecraft.world.entity.Entity self = (net.minecraft.world.entity.Entity) this;
            if (self.m_20159_() && self.m_20202_() instanceof com.codex.rongolemhealerintegration.ron.FieldCannonEntityAccess) {
                ((com.codex.rongolemhealerintegration.ron.FieldCannonEntityAccess) self.m_20202_()).ron$setTargetBlockPos(null);
            }
        }
    }

    @Inject(method = "resetBehaviours", at = @At("HEAD"))
    default void onResetBehaviours(CallbackInfo ci) {
        if (this instanceof net.minecraft.world.entity.Entity) {
            net.minecraft.world.entity.Entity self = (net.minecraft.world.entity.Entity) this;
            if (self.m_20159_() && self.m_20202_() instanceof com.codex.rongolemhealerintegration.ron.FieldCannonEntityAccess) {
                ((com.codex.rongolemhealerintegration.ron.FieldCannonEntityAccess) self.m_20202_()).ron$setTargetBlockPos(null);
            }
        }
    }
}
