package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.unit.Relationship;
import com.solegendary.reignofnether.unit.UnitServerEvents;
import com.solegendary.reignofnether.unit.interfaces.AttackerUnit;
import com.solegendary.reignofnether.unit.interfaces.Unit;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.extensions.IForgeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = {"net.mcreator.stalwartdungeons.procedures.NetherKeeperOnEntityTickUpdateProcedure"}, remap = false)
public class NetherKeeperSummonMixin {

    @Inject(method = "execute", at = @At("HEAD"), remap = false)
    private static void onExecute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        if (!(entity instanceof Mob mob)) {
            return;
        }

        // 1. Check if Nether Keeper is in combat or has enemies nearby
        boolean inCombat = mob.m_5448_() != null && mob.m_5448_().m_6084_();
        if (!inCombat && entity instanceof AttackerUnit attackerUnit) {
            inCombat = (attackerUnit.getAttackMoveTarget() != null) || (mob.m_21216_() > 0);
        }

        if (!inCombat && entity instanceof Unit unit) {
            // Scan 20 blocks around for any hostile entities
            List<LivingEntity> nearbyLiving = mob.m_9236_().m_6443_(LivingEntity.class, mob.m_20191_().m_82400_(20.0D), e -> {
                if (e == mob || !e.m_6084_()) return false;
                Relationship rel = UnitServerEvents.getUnitToEntityRelationship(unit, e);
                return rel == Relationship.HOSTILE;
            });
            if (!nearbyLiving.isEmpty()) {
                inCombat = true;
            }
        }

        CompoundTag persistentData = ((IForgeEntity) entity).getPersistentData();

        // If not in combat, keep spawnCouldown at 0 so it never summons out of combat
        if (!inCombat) {
            persistentData.m_128347_("spawnCouldown", 0.0D);
            return;
        }

        // 2. Check if there are already 4 or more Giddy Blazes nearby
        try {
            Class<?> giddyClass = Class.forName("net.mcreator.stalwartdungeons.entity.GiddyBlazeEntity");
            @SuppressWarnings("unchecked")
            List<?> nearbyBlazes = mob.m_9236_().m_45976_((Class<Entity>) giddyClass, mob.m_20191_().m_82400_(24.0D));
            if (nearbyBlazes.size() >= 4) {
                persistentData.m_128347_("spawnCouldown", 0.0D);
                return;
            }
        } catch (Exception e) {
            // Ignore if class not found
        }

        // 3. Extend cooldown to 600 ticks (30 seconds) instead of 200 ticks (10 seconds)
        double currentCooldown = persistentData.m_128459_("spawnCouldown");
        if (currentCooldown >= 200.0D && currentCooldown < 600.0D) {
            persistentData.m_128347_("spawnCouldown", currentCooldown + 1.0D);
        } else if (currentCooldown >= 600.0D) {
            persistentData.m_128347_("spawnCouldown", 200.0D); // Set to 200 so procedure's == 200.0D check triggers spawn!
        }
    }
}
