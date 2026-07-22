package com.codex.rongolemhealerintegration.mixin;

import fuzs.mutantmonsters.core.ZombieResurrection;
import fuzs.mutantmonsters.world.entity.mutant.MutantZombie;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = {ZombieResurrection.class}, remap = false)
public abstract class ZombieResurrectionMixin {
    @SuppressWarnings("rawtypes")
    @Redirect(
        method = "update",
        at = @At(
            value = "INVOKE",
            target = "Lfuzs/mutantmonsters/core/ZombieResurrection;getZombieByLocation(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/entity/EntityType;"
        ),
        remap = false
    )
    private EntityType<? extends Zombie> ronGolemHealerIntegration$redirectSpawnType(Level level, BlockPos pos, MutantZombie mutantZombie) {
        EntityType<? extends Zombie> vanillaType = ZombieResurrection.getZombieByLocation(level, pos);
        String owner = ((com.solegendary.reignofnether.unit.interfaces.Unit) mutantZombie).getOwnerName();
        if (owner != null && !owner.isEmpty()) {
            if (vanillaType == EntityType.f_20458_) {
                return (EntityType) com.solegendary.reignofnether.registrars.EntityRegistrar.DROWNED_UNIT.get();
            } else if (vanillaType == EntityType.f_20562_) {
                return (EntityType) com.solegendary.reignofnether.registrars.EntityRegistrar.HUSK_UNIT.get();
            } else if (vanillaType == EntityType.f_20530_) {
                return (EntityType) com.solegendary.reignofnether.registrars.EntityRegistrar.ZOMBIE_VILLAGER_UNIT.get();
            } else {
                return (EntityType) com.solegendary.reignofnether.registrars.EntityRegistrar.ZOMBIE_UNIT.get();
            }
        }
        return vanillaType;
    }

    @Redirect(
        method = "update",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;m_7967_(Lnet/minecraft/world/entity/Entity;)Z"
        ),
        remap = false
    )
    private boolean ronGolemHealerIntegration$setOwnerOnSpawn(Level level, Entity entity, MutantZombie mutantZombie) {
        String owner = ((com.solegendary.reignofnether.unit.interfaces.Unit) mutantZombie).getOwnerName();
        if (owner != null && !owner.isEmpty()) {
            if (entity instanceof com.solegendary.reignofnether.unit.interfaces.Unit) {
                ((com.solegendary.reignofnether.unit.interfaces.Unit) entity).setOwnerName(owner);
            }
        }
        return level.m_7967_(entity);
    }
}
