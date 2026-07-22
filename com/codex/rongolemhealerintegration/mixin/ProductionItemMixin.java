package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.building.buildings.placements.ProductionPlacement;
import com.solegendary.reignofnether.building.production.ProductionItem;
import com.solegendary.reignofnether.building.production.StartProductionButton;
import com.solegendary.reignofnether.building.production.StopProductionButton;
import com.solegendary.reignofnether.unit.units.monsters.ZombiePiglinProd;
import com.solegendary.reignofnether.keybinds.Keybinding;
import com.solegendary.reignofnether.resources.ResourceCosts;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.List;

@Mixin(value = ProductionItem.class, remap = false)
public abstract class ProductionItemMixin {

    @Unique
    private static final ResourceLocation ronGolemHealerIntegration$ZOMBIE_PIGLIN_ICON = new ResourceLocation("reignofnether", "textures/mobheads/zombie_piglin.png");

    @Inject(method = "getStartButton", at = @At("HEAD"), cancellable = true)
    private void ronGolemHealerIntegration$onGetStartButton(ProductionPlacement placement, Keybinding hotkey, CallbackInfoReturnable<StartProductionButton> cir) {
        if ((Object)this instanceof ZombiePiglinProd) {
            cir.setReturnValue(new StartProductionButton(
                ZombiePiglinProd.itemName, 
                ronGolemHealerIntegration$ZOMBIE_PIGLIN_ICON, 
                hotkey, 
                () -> false, 
                () -> true, 
                List.of(
                    ronGolemHealerIntegration$line("Zombie Piglin", true), 
                    ResourceCosts.getFormattedCost(ZombiePiglinProd.cost), 
                    ResourceCosts.getFormattedPopAndTime(ZombiePiglinProd.cost)
                ), 
                (ProductionItem)(Object)this
            ));
        }
    }

    @Inject(method = "getCancelButton", at = @At("HEAD"), cancellable = true)
    private void ronGolemHealerIntegration$onGetCancelButton(ProductionPlacement placement, boolean first, CallbackInfoReturnable<StopProductionButton> cir) {
        if ((Object)this instanceof ZombiePiglinProd) {
            cir.setReturnValue(new StopProductionButton(
                ZombiePiglinProd.itemName, 
                ronGolemHealerIntegration$ZOMBIE_PIGLIN_ICON, 
                placement, 
                (ProductionItem)(Object)this, 
                first
            ));
        }
    }

    @Unique
    private static FormattedCharSequence ronGolemHealerIntegration$line(String text, boolean bold) {
        return Component.m_237113_(text).m_6881_().m_130948_(bold ? Style.f_131099_.m_131136_(Boolean.valueOf(true)) : Style.f_131099_).m_7532_();
    }
}
