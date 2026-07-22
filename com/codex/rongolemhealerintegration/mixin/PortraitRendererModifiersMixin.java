package com.codex.rongolemhealerintegration.mixin;

import com.mojang.datafixers.util.Pair;
import com.solegendary.reignofnether.hud.PortraitRendererModifiers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {PortraitRendererModifiers.class}, remap = false)
public abstract class PortraitRendererModifiersMixin {
    @Inject(method = "getPortraitRendererModifiers", at = @At("HEAD"), cancellable = true, remap = false)
    private static void ronGolemHealerIntegration$fixPortraitModifiers(LivingEntity entity, CallbackInfoReturnable<Pair<Integer, Integer>> cir) {
        if (entity == null) {
            return;
        }
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.m_6095_());
        if (key != null) {
            String keyStr = key.toString();
            if ("stalwart_dungeons:awful_ghast".equals(keyStr)) {
                // Adjust yOffset and scale offset to render Awful Ghast correctly (preventing camera clipping)
                cir.setReturnValue(new Pair<>(-205, -44));
            } else if ("stalwart_dungeons:nether_keeper".equals(keyStr)) {
                // Adjust yOffset and scale offset for Nether Keeper to reduce its size further
                cir.setReturnValue(new Pair<>(-32, -26));
            }
        }
    }
}
