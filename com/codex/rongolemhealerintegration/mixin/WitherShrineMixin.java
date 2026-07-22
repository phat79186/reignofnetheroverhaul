package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.api.ReignOfNetherRegistries;
import com.solegendary.reignofnether.building.Building;
import com.solegendary.reignofnether.building.BuildingClientEvents;
import com.solegendary.reignofnether.building.BuildingPlaceButton;
import com.solegendary.reignofnether.building.Buildings;
import com.solegendary.reignofnether.building.buildings.piglins.WitherShrine;
import com.solegendary.reignofnether.keybinds.Keybinding;
import com.solegendary.reignofnether.research.ResearchClient;
import com.solegendary.reignofnether.resources.ResourceCost;
import com.solegendary.reignofnether.resources.ResourceCosts;
import java.util.List;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = {WitherShrine.class}, remap = false)
public abstract class WitherShrineMixin {

    @Overwrite
    public BuildingPlaceButton getBuildButton(Keybinding hotkey) {
        WitherShrine self = (WitherShrine) (Object) this;
        ResourceLocation key = ReignOfNetherRegistries.BUILDING.m_7981_((Building) (Object) self);
        String name = I18n.m_118938_((String)("buildings." + self.getFaction().name().toLowerCase() + "." + key.m_135827_() + "." + key.m_135815_()), (Object[])new Object[0]);
        return new BuildingPlaceButton(
            name, 
            new ResourceLocation("minecraft", "textures/block/gilded_blackstone.png"), 
            hotkey, 
            () -> BuildingClientEvents.getBuildingToPlace() == Buildings.WITHER_SHRINE, 
            () -> false, 
            () -> BuildingClientEvents.hasFinishedBuilding(Buildings.HOGLIN_STABLES) || ResearchClient.hasCheat("modifythephasevariance"), 
            List.of(
                FormattedCharSequence.m_13714_((String)I18n.m_118938_((String)"buildings.piglins.reignofnether.wither_shrine", (Object[])new Object[0]), (Style)Style.f_131099_.m_131136_(Boolean.valueOf(true))), 
                ResourceCosts.getFormattedCost(WitherShrine.cost), 
                FormattedCharSequence.m_13714_((String)"", (Style)Style.f_131099_), 
                FormattedCharSequence.m_13714_((String)I18n.m_118938_((String)"buildings.piglins.reignofnether.wither_shrine.tooltip1", (Object[])new Object[0]), (Style)Style.f_131099_), 
                FormattedCharSequence.m_13714_((String)I18n.m_118938_((String)"buildings.piglins.reignofnether.wither_shrine.tooltip2", (Object[])new Object[0]), (Style)Style.f_131099_), 
                FormattedCharSequence.m_13714_((String)"", (Style)Style.f_131099_), 
                FormattedCharSequence.m_13714_((String)I18n.m_118938_((String)"buildings.piglins.reignofnether.flame_sanctuary.tooltip3", (Object[])new Object[0]), (Style)Style.f_131099_)
            ), 
            self
        );
    }
}
