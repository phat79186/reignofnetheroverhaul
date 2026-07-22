package com.codex.rongolemhealerintegration.ron;

import com.solegendary.reignofnether.ability.Ability;
import com.solegendary.reignofnether.building.BuildingPlacement;
import com.solegendary.reignofnether.building.buildings.monsters.Laboratory;
import com.solegendary.reignofnether.cursor.CursorClientEvents;
import com.solegendary.reignofnether.hud.AbilityButton;
import com.solegendary.reignofnether.keybinds.Keybinding;
import com.solegendary.reignofnether.keybinds.Keybindings;
import com.solegendary.reignofnether.unit.UnitAction;
import com.solegendary.reignofnether.util.MiscUtil;
import com.solegendary.reignofnether.util.MyMath;
import com.solegendary.reignofnether.util.MyRenderer;
import java.util.List;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import fuzs.mutantmonsters.init.ModRegistry;
import com.solegendary.reignofnether.research.ResearchClient;
import com.solegendary.reignofnether.research.ResearchServerEvents;
import com.codex.rongolemhealerintegration.ron.RonGolemHealerProductionItems;

public class ThrowChemicalXAbility extends Ability {
    public ThrowChemicalXAbility() {
        super(UnitAction.DEBUG1, 1200, 25.0f, 4.0f, false, true);
        this.defaultHotkey = Keybindings.abilitySlot7;
    }

    @Override
    public AbilityButton getButton(Keybinding hotkey, BuildingPlacement placement) {
        if (!(placement.getBuilding() instanceof Laboratory)) {
            return null;
        }
        return new AbilityButton(
            "Chemical X Potion", 
            new ResourceLocation("mutantmonsters", "textures/item/lingering_chemical_x.png"), 
            hotkey, 
            () -> CursorClientEvents.getLeftClickAction() == UnitAction.DEBUG1, 
            () -> false, 
            () -> placement.isBuilt && ResearchClient.hasResearch(RonGolemHealerProductionItems.RESEARCH_CHEMICAL_X), 
            () -> CursorClientEvents.setLeftClickAction(UnitAction.DEBUG1), 
            null, 
            List.of(
                MiscUtil.fcs("Lingering Chemical X"),
                FormattedCharSequence.m_13714_("Cooldown: 60s, Range: 25", (Style)MyRenderer.iconStyle),
                FormattedCharSequence.m_13714_("", (Style)Style.f_131099_),
                FormattedCharSequence.m_13714_("Throws Lingering Potion of Chemical X.", (Style)Style.f_131099_),
                FormattedCharSequence.m_13714_("Transforms Zombies, Skeletons, and Creepers", (Style)Style.f_131099_),
                FormattedCharSequence.m_13714_("into mutants with a 50% success rate.", (Style)Style.f_131099_)
            ), 
            this, 
            placement
        );
    }

    @Override
    public void use(Level level, BuildingPlacement buildingUsing, BlockPos targetBp) {
        if (!level.m_5776_() && buildingUsing.getBuilding() instanceof Laboratory) {
            if (!ResearchServerEvents.playerHasResearch(buildingUsing.ownerName, RonGolemHealerProductionItems.RESEARCH_CHEMICAL_X)) {
                return;
            }
            if (buildingUsing.isAbilityOffCooldown(UnitAction.DEBUG1)) {
                BlockPos labPos = buildingUsing.centrePos;
                BlockPos limitedBp = MyMath.getXZRangeLimitedBlockPos(labPos, targetBp, this.range);
                limitedBp = MiscUtil.getHighestNonAirBlock(level, limitedBp);

                double tx = limitedBp.m_123341_() + 0.5;
                double ty = limitedBp.m_123342_() + 0.5;
                double tz = limitedBp.m_123343_() + 0.5;

                double lx = labPos.m_123341_() + 0.5;
                double ly = labPos.m_123342_() + 0.5;
                double lz = labPos.m_123343_() + 0.5;

                double dx = tx - lx;
                double dz = tz - lz;
                double distXZ = Math.sqrt(dx * dx + dz * dz);

                double spawnX, spawnY, spawnZ;
                double vx, vy, vz;

                if (distXZ < 5.0) {
                    spawnX = tx;
                    spawnY = ty + 10.0;
                    spawnZ = tz;
                    vx = 0.0;
                    vy = -1.0;
                    vz = 0.0;
                } else {
                    double dirX = dx / distXZ;
                    double dirZ = dz / distXZ;
                    spawnX = lx + dirX * 4.0;
                    spawnY = ly + 5.0;
                    spawnZ = lz + dirZ * 4.0;
                    
                    vx = tx - spawnX;
                    vy = ty - spawnY;
                    vz = tz - spawnZ;
                    
                    double newDistXZ = Math.sqrt(vx * vx + vz * vz);
                    vy += newDistXZ * 0.1;
                }

                ThrownPotion thrownPotion = new ThrownPotion(level, spawnX, spawnY, spawnZ);
                ItemStack potionStack = new ItemStack(Items.f_42426_); // LINGERING_POTION
                PotionUtils.m_43549_(potionStack, ModRegistry.CHEMICAL_X_POTION.get());
                thrownPotion.m_37446_(potionStack);
                
                if (distXZ < 5.0) {
                    thrownPotion.m_6686_(vx, vy, vz, 0.5f, 1.0f);
                } else {
                    thrownPotion.m_6686_(vx, vy, vz, 0.75f, 1.0f);
                }
                level.m_7967_(thrownPotion);
            }
        }
        this.setToMaxCooldown(buildingUsing);
    }
}
