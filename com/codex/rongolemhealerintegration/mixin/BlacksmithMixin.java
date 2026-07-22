package com.codex.rongolemhealerintegration.mixin;

import com.codex.rongolemhealerintegration.ron.RonGolemHealerProductionItems;
import com.solegendary.reignofnether.building.buildings.villagers.Blacksmith;
import com.solegendary.reignofnether.building.production.ProductionItem;
import com.solegendary.reignofnether.keybinds.Keybindings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Blacksmith.class}, remap=false)
public abstract class BlacksmithMixin {
    private static boolean autocastSetupDone = false;

    @Inject(method={"<init>"}, at={@At(value="RETURN")}, remap=false)
    private void ronGolemHealerIntegration$addProduction(CallbackInfo ci) {
        Blacksmith self = (Blacksmith)(Object)this;
        self.productions.add((ProductionItem)RonGolemHealerProductionItems.GOLEM_HEALER, Keybindings.abilitySlot7);
        self.productions.add((ProductionItem)RonGolemHealerProductionItems.FIELD_CANNON, Keybindings.abilitySlot9);
        
        try {
            java.lang.reflect.Field abilitiesField = com.solegendary.reignofnether.building.Building.class.getDeclaredField("abilities");
            abilitiesField.setAccessible(true);
            com.solegendary.reignofnether.ability.Abilities abilities = (com.solegendary.reignofnether.ability.Abilities) abilitiesField.get(self);
            abilities.add(com.codex.rongolemhealerintegration.ron.EquipYamato.INSTANCE, Keybindings.abilitySlot8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setupYamatoAutocast();
    }

    private static void setupYamatoAutocast() {
        if (autocastSetupDone) return;
        autocastSetupDone = true;
        try {
            java.lang.reflect.Field decodeField = com.solegendary.reignofnether.building.data.DataType.class.getDeclaredField("decode");
            decodeField.setAccessible(true);
            java.util.function.BiFunction<net.minecraft.nbt.CompoundTag, net.minecraft.server.MinecraftServer, com.solegendary.reignofnether.ability.EquipAbility> originalDecode = 
                (java.util.function.BiFunction<net.minecraft.nbt.CompoundTag, net.minecraft.server.MinecraftServer, com.solegendary.reignofnether.ability.EquipAbility>) decodeField.get(Blacksmith.AUTO_CAST_EQUIP);
            
            decodeField.set(Blacksmith.AUTO_CAST_EQUIP, (java.util.function.BiFunction<net.minecraft.nbt.CompoundTag, net.minecraft.server.MinecraftServer, com.solegendary.reignofnether.ability.EquipAbility>) (tag, server) -> {
                int id = tag.m_128451_("autocast-id");
                if (id == 3) {
                    return com.codex.rongolemhealerintegration.ron.EquipYamato.INSTANCE;
                }
                return originalDecode.apply(tag, server);
            });

            java.lang.reflect.Field encodeField = com.solegendary.reignofnether.building.data.DataType.class.getDeclaredField("encode");
            encodeField.setAccessible(true);
            java.util.function.Function<com.solegendary.reignofnether.ability.EquipAbility, net.minecraft.nbt.CompoundTag> originalEncode = 
                (java.util.function.Function<com.solegendary.reignofnether.ability.EquipAbility, net.minecraft.nbt.CompoundTag>) encodeField.get(Blacksmith.AUTO_CAST_EQUIP);
            
            encodeField.set(Blacksmith.AUTO_CAST_EQUIP, (java.util.function.Function<com.solegendary.reignofnether.ability.EquipAbility, net.minecraft.nbt.CompoundTag>) equipAbility -> {
                if (equipAbility instanceof com.codex.rongolemhealerintegration.ron.EquipYamato) {
                    net.minecraft.nbt.CompoundTag tag = new net.minecraft.nbt.CompoundTag();
                    tag.m_128405_("autocast-id", 3);
                    return tag;
                }
                return originalEncode.apply(equipAbility);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
