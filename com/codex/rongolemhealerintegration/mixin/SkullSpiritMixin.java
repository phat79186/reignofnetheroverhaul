package com.codex.rongolemhealerintegration.mixin;

import fuzs.mutantmonsters.world.entity.SkullSpirit;
import fuzs.mutantmonsters.world.level.MutatedExplosion;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = {SkullSpirit.class}, remap = false)
public abstract class SkullSpiritMixin {
    @ModifyConstant(method = "m_8119_", constant = @Constant(floatValue = 0.75f), remap = false)
    private float ronGolemHealerIntegration$changeConversionChance(float original) {
        return 0.50f;
    }

    @Redirect(
        method = "m_8119_",
        at = @At(
            value = "INVOKE",
            target = "Lfuzs/mutantmonsters/world/level/MutatedExplosion;create(Lnet/minecraft/world/entity/Entity;FZLnet/minecraft/world/level/Level$ExplosionInteraction;)Lfuzs/mutantmonsters/world/level/MutatedExplosion;"
        ),
        remap = false
    )
    private MutatedExplosion ronGolemHealerIntegration$redirectExplosion(Entity exploder, float size, boolean causesFire, Level.ExplosionInteraction interaction) {
        SkullSpirit spirit = (SkullSpirit) exploder;
        if (!spirit.isAttached()) {
            // Mutation failed path: reduce explosion size/damage to 2.0f
            return MutatedExplosion.create(exploder, 2.0f, causesFire, interaction);
        }
        return MutatedExplosion.create(exploder, size, causesFire, interaction);
    }
}
