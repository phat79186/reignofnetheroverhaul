package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.unit.interfaces.AttackerUnit;
import com.solegendary.reignofnether.unit.interfaces.Unit;
import com.solegendary.reignofnether.unit.units.monsters.WardenUnit;
import com.solegendary.reignofnether.unit.units.monsters.HuskUnit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import com.solegendary.reignofnether.registrars.AttributeRegistrar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = AttackerUnit.class, remap = false)
public interface AttackerUnitMixin {

    /**
     * @author Antigravity
     * @reason Dynamic getAggroRange based on faction, type, and day/night vision range
     */
    @Overwrite
    default float getAggroRange() {
        LivingEntity entity = (LivingEntity) this;
        
        // Default aggro range from attributes
        AttributeInstance attr = entity.m_21051_((Attribute) AttributeRegistrar.AGGRO_RANGE.get());
        float baseAggro = (float)(attr != null ? attr.m_22135_() : ((Attribute) AttributeRegistrar.AGGRO_RANGE.get()).m_22082_());
        
        if (!(entity instanceof Unit)) {
            return baseAggro;
        }
        
        Unit unit = (Unit) entity;
        com.solegendary.reignofnether.faction.Faction faction = unit.getFaction();
        
        net.minecraft.world.level.Level level = entity.m_9236_();
        boolean isDay = level.m_46461_();
        
        double visionRange = baseAggro;
        
        // 1. Warden Check (Always 0 vision)
        if (entity instanceof WardenUnit) {
            visionRange = 0.0;
        }
        // 2. Husk Check (Day: 10, Night: 40)
        else if (entity instanceof HuskUnit) {
            visionRange = isDay ? 10.0 : 40.0;
        }
        // 3. Monsters / Zombie Faction (Day: 0, Night: 40)
        else if (faction == com.solegendary.reignofnether.faction.Faction.MONSTERS) {
            visionRange = isDay ? 0.0 : 40.0;
        }
        // 4. Villagers Faction (Day: 25, Night: 15)
        else if (faction == com.solegendary.reignofnether.faction.Faction.VILLAGERS) {
            visionRange = isDay ? 25.0 : 15.0;
        }
        // 5. Piglins Faction (Always 20)
        else if (faction == com.solegendary.reignofnether.faction.Faction.PIGLINS) {
            visionRange = 20.0;
        }
        
        return (float) visionRange;
    }
}
