package com.codex.rongolemhealerintegration.ron;

import baguchan.slash_illager.entity.BladeMaster;
import com.solegendary.reignofnether.ability.Ability;
import com.solegendary.reignofnether.ability.BuildingAbilityServerboundPacket;
import com.solegendary.reignofnether.ability.EquipAbility;
import com.solegendary.reignofnether.building.BuildingPlacement;
import com.solegendary.reignofnether.building.buildings.villagers.Blacksmith;
import com.solegendary.reignofnether.cursor.CursorClientEvents;
import com.solegendary.reignofnether.hud.AbilityButton;
import com.solegendary.reignofnether.keybinds.Keybinding;
import com.solegendary.reignofnether.keybinds.Keybindings;
import com.solegendary.reignofnether.resources.ResourceCost;
import com.solegendary.reignofnether.unit.UnitAction;
import com.solegendary.reignofnether.util.MiscUtil;
import mods.flammpfeil.slashblade.data.builtin.SlashBladeBuiltInRegistry;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.registry.slashblade.SlashBladeDefinition;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.List;

public class EquipYamato extends EquipAbility {
    private static final UnitAction EQUIP_ACTION = UnitAction.DEBUG2;
    public static final EquipYamato INSTANCE = new EquipYamato();

    public EquipYamato() {
        // Sử dụng Items.IRON_SWORD (f_42636_) làm dummy item để tránh gọi SBItems.slashblade.get() quá sớm trong giai đoạn tải mod
        super(EQUIP_ACTION, ResourceCost.Enchantment(0, 0, 200), net.minecraft.world.item.Items.f_42636_, EquipmentSlot.MAINHAND);
        this.defaultHotkey = Keybindings.abilitySlot8;
    }

    @Override
    public AbilityButton getButton(Keybinding hotkey, BuildingPlacement placement) {
        return new AbilityButton("Yamato", new ResourceLocation("ron_golem_healer_integration", "textures/gui/yamato_icon.png"), hotkey,
            () -> CursorClientEvents.getLeftClickAction() == EQUIP_ACTION || placement.getDataStorage().getData(Blacksmith.AUTO_CAST_EQUIP) == this,
            () -> false, () -> true,
            () -> CursorClientEvents.setLeftClickAction(EQUIP_ACTION),
            () -> {
                BuildingAbilityServerboundPacket.doAbility(EQUIP_ACTION, placement.originPos, this.oneClickOneUse);
                if (placement.getDataStorage().getData(Blacksmith.AUTO_CAST_EQUIP) == this) {
                    placement.getDataStorage().setData(Blacksmith.AUTO_CAST_EQUIP, null);
                } else {
                    placement.getDataStorage().setData(Blacksmith.AUTO_CAST_EQUIP, this);
                }
            },
            List.of(
                MiscUtil.fcs("Yamato", Style.f_131099_.m_131136_(Boolean.valueOf(true))),
                com.solegendary.reignofnether.resources.ResourceCosts.getFormattedCost(this.cost),
                MiscUtil.fcs("", Style.f_131099_),
                MiscUtil.fcs("Nâng cấp kiếm của Blade Master thành Yamato", Style.f_131099_),
                MiscUtil.fcs("", Style.f_131099_),
                MiscUtil.fcs(I18n.m_118938_("abilities.reignofnether.autocast", new Object[0]), Style.f_131099_)
            ),
            (Ability) this, placement
        );
    }

    @Override
    public boolean isCorrectUnit(LivingEntity livingEntity) {
        return livingEntity instanceof BladeMaster;
    }

    @Override
    public boolean hasSameItem(LivingEntity entity) {
        ItemStack stack = entity.m_6844_(EquipmentSlot.MAINHAND);
        if (stack.m_41720_() instanceof ItemSlashBlade) {
            ItemSlashBlade bladeItem = (ItemSlashBlade) stack.m_41720_();
            ResourceLocation bladeId = bladeItem.getBladeId(stack);
            return bladeId != null && bladeId.toString().equals("slashblade:yamato");
        }
        return false;
    }

    @Override
    public boolean hasItemInSlot(LivingEntity entity) {
        return this.hasSameItem(entity);
    }

    @Override
    protected void doEquip(LivingEntity entity) {
        try {
            Level level = entity.m_9236_();
            Registry<SlashBladeDefinition> registry = level.m_9598_().m_175515_(SlashBladeDefinition.REGISTRY_KEY);
            SlashBladeDefinition def = registry.m_6246_(SlashBladeBuiltInRegistry.YAMATO);
            ItemStack yamatoStack = ItemStack.f_41583_;
            if (def != null) {
                yamatoStack = def.getBlade();
            } else {
                yamatoStack = new ItemStack((ItemLike) ForgeRegistries.ITEMS.getValue(new ResourceLocation("slashblade", "slashblade")));
            }
            entity.m_8061_(EquipmentSlot.MAINHAND, yamatoStack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
