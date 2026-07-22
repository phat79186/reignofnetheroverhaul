package com.codex.rongolemhealerintegration.mixin;

import fuzs.mutantmonsters.world.effect.ChemicalXMobEffect;
import fuzs.mutantmonsters.init.ModRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {ChemicalXMobEffect.class}, remap = false)
public abstract class ChemicalXMobEffectMixin {
    @Inject(method = "getMutantOf", at = @At("HEAD"), cancellable = true, remap = false)
    private static void ronGolemHealerIntegration$overrideGetMutantOf(LivingEntity target, CallbackInfoReturnable<EntityType<?>> cir) {
        String className = target.getClass().getName();
        if (className.equals("com.solegendary.reignofnether.unit.units.monsters.ZombieUnit") 
            || className.equals("com.solegendary.reignofnether.unit.units.monsters.ZombieVillagerUnit")
            || className.equals("com.solegendary.reignofnether.unit.units.monsters.HuskUnit")
            || className.equals("com.solegendary.reignofnether.unit.units.monsters.DrownedUnit")) {
            cir.setReturnValue(ModRegistry.MUTANT_ZOMBIE_ENTITY_TYPE.get());
        } else if (className.equals("com.solegendary.reignofnether.unit.units.monsters.SkeletonUnit")
            || className.equals("com.solegendary.reignofnether.unit.units.monsters.StrayUnit")
            || className.equals("com.solegendary.reignofnether.unit.units.monsters.BoggedUnit")) {
            cir.setReturnValue(ModRegistry.MUTANT_SKELETON_ENTITY_TYPE.get());
        } else if (className.equals("com.solegendary.reignofnether.unit.units.monsters.CreeperUnit")) {
            if (target instanceof net.minecraft.world.entity.monster.Creeper && ((net.minecraft.world.entity.monster.Creeper) target).m_7090_()) {
                cir.setReturnValue(null);
            } else {
                cir.setReturnValue(ModRegistry.MUTANT_CREEPER_ENTITY_TYPE.get());
            }
        }
    }
}
