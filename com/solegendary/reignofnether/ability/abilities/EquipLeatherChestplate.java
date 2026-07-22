/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.registries.ForgeRegistries
 */
package com.solegendary.reignofnether.ability.abilities;

import com.min01.guardillagers.entity.GuardIllager;
import com.solegendary.reignofnether.ability.Ability;
import com.solegendary.reignofnether.ability.BuildingAbilityServerboundPacket;
import com.solegendary.reignofnether.ability.EquipAbility;
import com.solegendary.reignofnether.building.BuildingPlacement;
import com.solegendary.reignofnether.building.buildings.villagers.Blacksmith;
import com.solegendary.reignofnether.cursor.CursorClientEvents;
import com.solegendary.reignofnether.hud.AbilityButton;
import com.solegendary.reignofnether.keybinds.Keybinding;
import com.solegendary.reignofnether.keybinds.Keybindings;
import com.solegendary.reignofnether.registrars.EntityRegistrar;
import com.solegendary.reignofnether.resources.ResourceCosts;
import com.solegendary.reignofnether.unit.UnitAction;
import com.solegendary.reignofnether.util.MiscUtil;
import java.util.List;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public class EquipLeatherChestplate
extends EquipAbility {
    private static final UnitAction EQUIP_ACTION = UnitAction.EQUIP_LEATHER_ARMOR;

    public EquipLeatherChestplate() {
        super(EQUIP_ACTION, ResourceCosts.EQUIP_LEATHER_ARMOR, Items.f_42408_, EquipmentSlot.CHEST);
        this.defaultHotkey = Keybindings.abilitySlot5;
    }

    @Override
    public AbilityButton getButton(Keybinding hotkey, BuildingPlacement placement) {
        return new AbilityButton("Leather Chestplate", new ResourceLocation("minecraft", "textures/item/leather_chestplate.png"), hotkey, () -> CursorClientEvents.getLeftClickAction() == EQUIP_ACTION || placement.getDataStorage().getData(Blacksmith.AUTO_CAST_EQUIP) == this, () -> false, () -> true, () -> CursorClientEvents.setLeftClickAction(EQUIP_ACTION), () -> {
            BuildingAbilityServerboundPacket.doAbility(EQUIP_ACTION, placement.originPos, this.oneClickOneUse);
            if (placement.getDataStorage().getData(Blacksmith.AUTO_CAST_EQUIP) == this) {
                placement.getDataStorage().setData(Blacksmith.AUTO_CAST_EQUIP, null);
            } else {
                placement.getDataStorage().setData(Blacksmith.AUTO_CAST_EQUIP, this);
            }
        }, List.of(MiscUtil.fcs(I18n.m_118938_((String)"ability.reignofnether.equip.leather_chestplate", (Object[])new Object[0]), Style.f_131099_.m_131136_(Boolean.valueOf(true))), ResourceCosts.getFormattedCost(this.cost), MiscUtil.fcs("", Style.f_131099_), MiscUtil.fcs(I18n.m_118938_((String)"ability.reignofnether.equip.leather_chestplate.tooltip1", (Object[])new Object[0]), Style.f_131099_), MiscUtil.fcs("", Style.f_131099_), MiscUtil.fcs(I18n.m_118938_((String)"abilities.reignofnether.autocast", (Object[])new Object[0]), Style.f_131099_)), (Ability)this, placement);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean isCorrectUnit(LivingEntity livingEntity) {
        if (livingEntity instanceof GuardIllager) {
            return true;
        }
        return List.of((EntityType)EntityRegistrar.MILITIA_UNIT.get(), (EntityType)EntityRegistrar.VINDICATOR_UNIT.get(), (EntityType)EntityRegistrar.PILLAGER_UNIT.get(), (EntityType)EntityRegistrar.EVOKER_UNIT.get()).contains(livingEntity.m_6095_());
    }

    @Override
    protected void doEquip(LivingEntity livingEntity) {
        livingEntity.m_8061_(EquipmentSlot.HEAD, new ItemStack((ItemLike)ForgeRegistries.ITEMS.getValue(new ResourceLocation("magistuarmory", "kettlehat"))));
        livingEntity.m_8061_(EquipmentSlot.CHEST, new ItemStack((ItemLike)ForgeRegistries.ITEMS.getValue(new ResourceLocation("magistuarmory", "platemail_chestplate"))));
        livingEntity.m_8061_(EquipmentSlot.LEGS, new ItemStack((ItemLike)ForgeRegistries.ITEMS.getValue(new ResourceLocation("magistuarmory", "platemail_leggings"))));
        livingEntity.m_8061_(EquipmentSlot.FEET, new ItemStack((ItemLike)ForgeRegistries.ITEMS.getValue(new ResourceLocation("magistuarmory", "platemail_boots"))));
    }
}

