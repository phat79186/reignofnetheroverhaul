package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.building.production.ProductionItems;
import com.solegendary.reignofnether.research.ResearchServerEvents;
import com.solegendary.reignofnether.unit.units.villagers.VindicatorUnit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = VindicatorUnit.class, remap = false)
public abstract class VindicatorUnitMixin {

    @Inject(method = "setupEquipmentAndUpgradesServer", at = @At("HEAD"), cancellable = true)
    private void ronGolemHealerIntegration$equipHalberd(CallbackInfo ci) {
        VindicatorUnit unit = (VindicatorUnit) (Object) this;
        if (unit.hasAnyEnchant()) {
            return;
        }

        boolean hasDiamondResearch = ResearchServerEvents.playerHasResearch(unit.getOwnerName(), ProductionItems.RESEARCH_VINDICATOR_AXES);
        String itemId = hasDiamondResearch ? "diamond_concavehalberd" : "iron_concavehalberd";
        Item halberd = ForgeRegistries.ITEMS.getValue(new ResourceLocation("magistuarmory", itemId));

        if (halberd == null || halberd == Items.f_41852_ /* AIR */) {
            halberd = ForgeRegistries.ITEMS.getValue(new ResourceLocation("magistuarmory", "iron_concavehalberd"));
        }

        if (halberd != null && halberd != Items.f_41852_ /* AIR */) {
            ItemStack stack = new ItemStack(halberd);
            unit.m_8061_(EquipmentSlot.MAINHAND, stack);
            ci.cancel();
        }
    }

    @Inject(method = "m_8119_", at = @At("HEAD"))
    private void ronGolemHealerIntegration$onTickCheckWeapon(CallbackInfo ci) {
        VindicatorUnit unit = (VindicatorUnit) (Object) this;
        if (!unit.m_9236_().m_5776_()) { // Server side only
            ItemStack mainhand = unit.m_6844_(EquipmentSlot.MAINHAND);
            if (mainhand.m_41619_() /* isEmpty */ || mainhand.m_41720_() == Items.f_42386_ /* IRON_AXE */ || mainhand.m_41720_() == Items.f_42574_ /* DIAMOND_AXE */) {
                unit.setupEquipmentAndUpgradesServer();
            }
        }
    }
}
