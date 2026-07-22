/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  baguchan.slash_illager.entity.BladeMaster
 *  com.min01.guardillagers.entity.GuardIllager
 *  com.solegendary.reignofnether.hud.HudClientEvents
 *  net.mcreator.villagergolemhealer.entity.VillagergolemhealerEntity
 *  net.minecraft.world.entity.LivingEntity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.codex.rongolemhealerintegration.mixin;

import baguchan.slash_illager.entity.BladeMaster;
import com.min01.guardillagers.entity.GuardIllager;
import com.solegendary.reignofnether.hud.HudClientEvents;
import fuzs.mutantmonsters.world.entity.mutant.MutantZombie;
import fuzs.mutantmonsters.world.entity.mutant.MutantSkeleton;
import fuzs.mutantmonsters.world.entity.mutant.MutantCreeper;
import net.mcreator.villagergolemhealer.entity.VillagergolemhealerEntity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={HudClientEvents.class}, remap=false)
public abstract class HudClientEventsMixin {
    @Inject(method={"getModifiedEntityName"}, at={@At(value="HEAD")}, cancellable=true, remap=false)
    private static void ronGolemHealerIntegration$fixDisplayedName(LivingEntity entity, CallbackInfoReturnable<String> cir) {
        if (entity instanceof VillagergolemhealerEntity) {
            cir.setReturnValue("golem healer");
        } else if (entity instanceof GuardIllager) {
            cir.setReturnValue("guard illager");
        } else if (entity instanceof BladeMaster) {
            cir.setReturnValue("blade master");
        } else if (entity instanceof MutantZombie) {
            cir.setReturnValue("mutant zombie");
        } else if (entity instanceof MutantSkeleton) {
            cir.setReturnValue("mutant skeleton");
        } else if (entity instanceof MutantCreeper) {
            cir.setReturnValue("mutant creeper");
        }
    }



    @org.spongepowered.asm.mixin.injection.Redirect(
        method = "onDrawScreen",
        at = @org.spongepowered.asm.mixin.injection.At(
            value = "FIELD",
            target = "Lcom/solegendary/reignofnether/hud/Button;DEFAULT_ICON_FRAME_SIZE:I",
            ordinal = 5
        ),
        remap = false
    )
    private static int ronGolemHealerIntegration$redirectDefaultIconFrameSize() {
        if (com.solegendary.reignofnether.hud.HudClientEvents.hudSelectedPlacement instanceof com.solegendary.reignofnether.building.buildings.placements.ProductionPlacement) {
            com.solegendary.reignofnether.building.buildings.placements.ProductionPlacement placement = 
                (com.solegendary.reignofnether.building.buildings.placements.ProductionPlacement) com.solegendary.reignofnether.hud.HudClientEvents.hudSelectedPlacement;
            if (placement.productionButtons != null) {
                long size = placement.productionButtons.stream().filter(b -> b != null && (Boolean)b.isHidden.get() == false).count();
                int rowsUp = (int) Math.max(0, Math.floor((size - 1) / 6.0));
                return rowsUp * com.solegendary.reignofnether.hud.Button.DEFAULT_ICON_FRAME_SIZE;
            }
        }
        return com.solegendary.reignofnether.hud.Button.DEFAULT_ICON_FRAME_SIZE;
    }
}

