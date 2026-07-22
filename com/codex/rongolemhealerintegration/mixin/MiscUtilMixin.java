/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  baguchan.slash_illager.entity.BladeMaster
 *  com.min01.guardillagers.entity.GuardIllager
 *  com.solegendary.reignofnether.util.MiscUtil
 *  net.mcreator.villagergolemhealer.entity.VillagergolemhealerEntity
 *  net.minecraft.world.entity.Entity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.codex.rongolemhealerintegration.mixin;

import baguchan.slash_illager.entity.BladeMaster;
import com.min01.guardillagers.entity.GuardIllager;
import com.solegendary.reignofnether.util.MiscUtil;
import fuzs.mutantmonsters.world.entity.mutant.MutantZombie;
import fuzs.mutantmonsters.world.entity.mutant.MutantSkeleton;
import fuzs.mutantmonsters.world.entity.mutant.MutantCreeper;
import net.mcreator.villagergolemhealer.entity.VillagergolemhealerEntity;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={MiscUtil.class}, remap=false)
public abstract class MiscUtilMixin {
    @Inject(method={"getSimpleEntityName"}, at={@At(value="HEAD")}, cancellable=true, remap=false)
    private static void ronGolemHealerIntegration$fixSimpleEntityName(Entity entity, CallbackInfoReturnable<String> cir) {
        if (entity == null) {
            return;
        }
        net.minecraft.resources.ResourceLocation key = net.minecraftforge.registries.ForgeRegistries.ENTITY_TYPES.getKey(entity.m_6095_());
        if (key != null) {
            String keyStr = key.toString();
            if ("stalwart_dungeons:awful_ghast".equals(keyStr)) {
                cir.setReturnValue("awful_ghast");
                return;
            } else if ("stalwart_dungeons:nether_keeper".equals(keyStr)) {
                cir.setReturnValue("nether_keeper");
                return;
            } else if ("stalwart_dungeons:giddy_blaze".equals(keyStr)) {
                cir.setReturnValue("giddy_blaze");
                return;
            } else if ("stalwart_dungeons:incomplete_wither".equals(keyStr)) {
                cir.setReturnValue("incomplete_wither");
                return;
            } else if ("stalwart_dungeons:reinforced_blaze".equals(keyStr)) {
                cir.setReturnValue("reinforced_blaze");
                return;
            } else if ("stalwart_dungeons:propulk".equals(keyStr)) {
                cir.setReturnValue("propulk");
                return;
            } else if ("stalwart_dungeons:shelterer".equals(keyStr)) {
                cir.setReturnValue("shelterer");
                return;
            } else if ("stalwart_dungeons:shelterer_without_armor".equals(keyStr)) {
                cir.setReturnValue("shelterer_without_armor");
                return;
            } else if ("stalwart_dungeons:shulker_cannon".equals(keyStr)) {
                cir.setReturnValue("shulker_cannon");
                return;
            }
        }
        if (entity instanceof VillagergolemhealerEntity) {
            cir.setReturnValue("golem_healer");
        } else if (entity instanceof GuardIllager) {
            cir.setReturnValue("guard_illager");
        } else if (entity instanceof BladeMaster) {
            cir.setReturnValue("blade_master");
        } else if (entity instanceof MutantZombie) {
            cir.setReturnValue("mutant_zombie");
        } else if (entity instanceof MutantSkeleton) {
            cir.setReturnValue("mutant_skeleton");
        } else if (entity instanceof MutantCreeper) {
            cir.setReturnValue("mutant_creeper");
        }
    }
}

