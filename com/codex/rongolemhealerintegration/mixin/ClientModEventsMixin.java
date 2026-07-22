/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.min01.guardillagers.event.ClientModEvents
 *  com.min01.guardillagers.init.ModEntityTypes
 *  net.minecraft.world.entity.EntityType
 *  net.minecraftforge.client.event.EntityRenderersEvent$RegisterRenderers
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.codex.rongolemhealerintegration.mixin;

import com.codex.rongolemhealerintegration.client.render.GuardIllagerUnitRenderer;
import com.min01.guardillagers.event.ClientModEvents;
import com.min01.guardillagers.init.ModEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ClientModEvents.class}, remap=false)
public class ClientModEventsMixin {
    @Inject(method={"entityRenderers"}, at={@At(value="TAIL")}, remap=false)
    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers registerRenderers, CallbackInfo callbackInfo) {
        registerRenderers.registerEntityRenderer((EntityType)ModEntityTypes.GUARD_ILLAGER.get(), GuardIllagerUnitRenderer::new);
    }
}

