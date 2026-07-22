package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.api.ReignOfNetherRegistries;
import com.solegendary.reignofnether.building.Building;
import com.solegendary.reignofnether.building.BuildingClientEvents;
import com.solegendary.reignofnether.building.BuildingPlaceButton;
import com.solegendary.reignofnether.building.Buildings;
import com.solegendary.reignofnether.building.buildings.piglins.BasaltSprings;
import com.solegendary.reignofnether.keybinds.Keybinding;
import com.solegendary.reignofnether.research.ResearchClient;
import com.solegendary.reignofnether.resources.ResourceCosts;
import java.util.List;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = {BasaltSprings.class}, remap = false)
public abstract class BasaltSpringsMixin {

    @Overwrite
    public BuildingPlaceButton getBuildButton(Keybinding hotkey) {
        BasaltSprings self = (BasaltSprings) (Object) this;
        ResourceLocation key = ReignOfNetherRegistries.BUILDING.m_7981_((Building) (Object) self);
        String name = I18n.m_118938_((String)("buildings." + self.getFaction().name().toLowerCase() + "." + key.m_135827_() + "." + key.m_135815_()), (Object[])new Object[0]);
        return new BuildingPlaceButton(
            name, 
            new ResourceLocation("minecraft", "textures/block/polished_basalt_top.png"), 
            hotkey, 
            () -> BuildingClientEvents.getBuildingToPlace() == Buildings.BASALT_SPRINGS, 
            () -> false, 
            () -> BuildingClientEvents.hasFinishedBuilding(Buildings.FLAME_SANCTUARY) || ResearchClient.hasCheat("modifythephasevariance"), 
            List.of(
                FormattedCharSequence.m_13714_((String)I18n.m_118938_((String)"buildings.piglins.reignofnether.basalt_springs", (Object[])new Object[0]), (Style)Style.f_131099_.m_131136_(Boolean.valueOf(true))), 
                ResourceCosts.getFormattedCost(BasaltSprings.cost), 
                FormattedCharSequence.m_13714_((String)"", (Style)Style.f_131099_), 
                FormattedCharSequence.m_13714_((String)I18n.m_118938_((String)"buildings.piglins.reignofnether.basalt_springs.tooltip1", (Object[])new Object[0]), (Style)Style.f_131099_), 
                FormattedCharSequence.m_13714_((String)I18n.m_118938_((String)"buildings.piglins.reignofnether.basalt_springs.tooltip2", (Object[])new Object[0]), (Style)Style.f_131099_), 
                FormattedCharSequence.m_13714_((String)"", (Style)Style.f_131099_), 
                FormattedCharSequence.m_13714_((String)I18n.m_118938_((String)"units.piglins.reignofnether.blaze.tooltip3", (Object[])new Object[0]), (Style)Style.f_131099_)
            ), 
            self
        );
    }
}
