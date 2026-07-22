package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.fogofwar.FogOfWarClientEvents;
import com.solegendary.reignofnether.unit.interfaces.RangedAttackerUnit;
import com.solegendary.reignofnether.unit.interfaces.Unit;
import com.solegendary.reignofnether.unit.UnitClientEvents;
import com.solegendary.reignofnether.unit.Relationship;
import com.solegendary.reignofnether.alliance.AlliancesClient;
import com.solegendary.reignofnether.building.BuildingPlacement;
import com.solegendary.reignofnether.building.BuildingClientEvents;
import com.solegendary.reignofnether.unit.units.monsters.WardenUnit;
import com.solegendary.reignofnether.unit.units.monsters.HuskUnit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = {FogOfWarClientEvents.class}, remap = false)
public class FogOfWarClientEventsMixin {

    @Shadow
    private static void onChunkUnexplore(ChunkPos chunkPos) {
        throw new AssertionError();
    }

    @Shadow
    private static void onChunkExplore(ChunkPos chunkPos) {
        throw new AssertionError();
    }

    @Shadow
    private static int updateTicksLeft;

    @Unique
    private static double getUnitVisionRange(LivingEntity entity) {
        if (!(entity instanceof Unit)) {
            return 0.0;
        }
        
        Unit unit = (Unit) entity;
        
        // Faction check
        com.solegendary.reignofnether.faction.Faction faction = unit.getFaction();
        
        // Day/night check
        net.minecraft.world.level.Level level = entity.m_9236_();
        boolean isDay = level.m_46461_();
        
        // 1. Warden Check (0 blocks completely)
        if (entity instanceof WardenUnit) {
            return 0.0;
        }
        
        // 2. Husk Check (Day: 10, Night: 40)
        if (entity instanceof HuskUnit) {
            return isDay ? 10.0 : 40.0;
        }
        
        // 3. Monsters / Zombie Faction (Day: 0, Night: 40)
        if (faction == com.solegendary.reignofnether.faction.Faction.MONSTERS) {
            return isDay ? 0.0 : 40.0;
        }
        
        // 4. Villagers Faction (Day: 25, Night: 15)
        if (faction == com.solegendary.reignofnether.faction.Faction.VILLAGERS) {
            return isDay ? 25.0 : 15.0;
        }
        
        // 5. Piglins Faction (Default 20)
        if (faction == com.solegendary.reignofnether.faction.Faction.PIGLINS) {
            return 20.0;
        }
        
        return 20.0; // Default fallback
    }

    @Unique
    private static boolean isChunkInUnitFov(LivingEntity entity, ChunkPos chunkPos, double visionRange) {
        ChunkPos unitChunk = entity.m_146902_();
        if (unitChunk.equals(chunkPos)) {
            return visionRange > 0.0; // Only reveal its own chunk if it has vision
        }
        
        // Calculate block positions
        double ux = entity.m_20185_(); // X position
        double uz = entity.m_20189_(); // Z position
        
        // Center of the target chunk
        double cx = (chunkPos.f_45578_ << 4) + 8.0;
        double cz = (chunkPos.f_45579_ << 4) + 8.0;
        
        double vx = cx - ux;
        double vz = cz - uz;
        double len = Math.sqrt(vx * vx + vz * vz);
        
        // Check if within the vision range (with 8 blocks buffer for chunk edges)
        if (len > visionRange + 8.0) {
            return false;
        }
        
        // If close enough (e.g. less than 8 blocks away), always reveal it to prevent blind spots
        if (len < 8.0) {
            return true;
        }
        
        // Get unit forward vector based on rotationYaw
        float yaw = entity.m_146908_(); // rotationYaw
        double yawRad = Math.toRadians(yaw);
        double fx = -Math.sin(yawRad);
        double fz = Math.cos(yawRad);
        
        // Dot product
        double dot = (vx * fx + vz * fz) / len;
        
        // cos(55 degrees) = 0.573576 (for 110 degrees total FOV)
        return dot >= 0.573576;
    }

    @Unique
    private static boolean hasLineOfSightToChunk(LivingEntity entity, ChunkPos targetChunk) {
        ChunkPos unitChunk = entity.m_146902_();
        if (unitChunk.equals(targetChunk)) {
            return true;
        }
        
        net.minecraft.world.level.Level level = entity.m_9236_();
        if (level == null) {
            return false;
        }
        
        // Eye position of the unit
        net.minecraft.world.phys.Vec3 eyePos = new net.minecraft.world.phys.Vec3(
            entity.m_20185_(),
            entity.m_20186_() + entity.m_20192_(),
            entity.m_20189_()
        );
        
        // Center coordinates of the target chunk
        int targetX = (targetChunk.f_45578_ << 4) + 8;
        int targetZ = (targetChunk.f_45579_ << 4) + 8;
        
        int terrainY;
        try {
            net.minecraft.world.level.chunk.ChunkAccess chunk = level.m_6325_(targetChunk.f_45578_, targetChunk.f_45579_);
            if (chunk != null) {
                terrainY = chunk.m_5885_(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE, targetX, targetZ);
            } else {
                terrainY = (int) entity.m_20186_();
            }
        } catch (Exception e) {
            terrainY = (int) entity.m_20186_();
        }
        
        // Eye height of a potential entity at the center of the target chunk
        net.minecraft.world.phys.Vec3 targetPos = new net.minecraft.world.phys.Vec3(
            targetX + 0.5,
            terrainY + 1.8,
            targetZ + 0.5
        );
        
        net.minecraft.world.level.ClipContext context = new net.minecraft.world.level.ClipContext(
            eyePos,
            targetPos,
            net.minecraft.world.level.ClipContext.Block.VISUAL,
            net.minecraft.world.level.ClipContext.Fluid.NONE,
            entity
        );
        
        try {
            net.minecraft.world.phys.BlockHitResult hitResult = level.m_45547_(context);
            return hitResult.m_6662_() == net.minecraft.world.phys.HitResult.Type.MISS;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * @author Antigravity
     * @reason Fix BlockPos vs ChunkPos comparison bug in brightChunks.contains
     */
    @Overwrite
    public static boolean isInBrightChunk(Entity entity) {
        if (entity == null) {
            return false;
        }
        if (!FogOfWarClientEvents.isEnabled() || Minecraft.m_91087_().f_91073_ == null) {
            return true;
        }
        // 1. Always reveal local player
        if (entity == Minecraft.m_91087_().f_91074_) {
            return true;
        }
        // 2. Always reveal owned/allied units
        if (entity instanceof Unit) {
            Unit unit = (Unit) entity;
            net.minecraft.client.player.LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
            String localPlayerName = localPlayer != null ? localPlayer.m_7755_().getString() : "";
            
            if (UnitClientEvents.getPlayerToEntityRelationship((LivingEntity) entity) == Relationship.OWNED 
                || FogOfWarClientEvents.isPlayerRevealed(unit.getOwnerName()) 
                || AlliancesClient.isAllied(localPlayerName, unit.getOwnerName())) {
                return true;
            }
        }
        // 3. Otherwise, check brightChunks
        if (FogOfWarClientEvents.brightChunks.contains(entity.m_146902_())) {
            return true;
        }
        RangedAttackerUnit rangedAttackerUnit;
        return entity instanceof RangedAttackerUnit && (rangedAttackerUnit = (RangedAttackerUnit)entity).getFogRevealDuration() > 0;
    }

    /**
     * @author Antigravity
     * @reason Fix allies being treated as enemies in occupied chunks, and use ChunkPos for contains
     */
    @Overwrite
    public static java.util.Set<ChunkPos> getEnemyOccupiedChunks() {
        java.util.concurrent.ConcurrentHashMap.KeySetView<ChunkPos, Boolean> enemyOccupiedChunks = java.util.concurrent.ConcurrentHashMap.newKeySet();
        net.minecraft.client.player.LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
        String localPlayerName = localPlayer != null ? localPlayer.m_7755_().getString() : "";

        for (LivingEntity entity : UnitClientEvents.getAllUnits()) {
            if (UnitClientEvents.getPlayerToEntityRelationship(entity) == Relationship.OWNED) {
                continue;
            }
            if (entity instanceof Unit) {
                String owner = ((Unit) entity).getOwnerName();
                if (AlliancesClient.isAllied(localPlayerName, owner)) {
                    continue;
                }
            }
            enemyOccupiedChunks.add(entity.m_146902_()); // Fix: use m_146902_() / chunkPosition() instead of m_20097_() / blockPosition()
        }
        for (BuildingPlacement building : BuildingClientEvents.getBuildings()) {
            if (BuildingClientEvents.getPlayerToBuildingRelationship(building) == Relationship.OWNED 
                || FogOfWarClientEvents.isPlayerRevealed(building.ownerName) 
                || AlliancesClient.isAllied(localPlayerName, building.ownerName)
                || Minecraft.m_91087_().f_91073_ == null) {
                continue;
            }
            for (BlockPos bp : building.getRenderChunkOrigins(true)) {
                ChunkPos pos = Minecraft.m_91087_().f_91073_.m_46865_(bp).m_7697_();
                enemyOccupiedChunks.add(pos);
            }
        }
        return enemyOccupiedChunks;
    }

    /**
     * @author Antigravity
     * @reason Overwrite updateFogChunks to support 110-degree FOV for units and allied buildings with dynamic block vision ranges
     */
    @Overwrite
    public static void updateFogChunks() {
        FogOfWarClientEvents.brightChunks.clear();
        
        net.minecraft.client.player.LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
        if (localPlayer == null) {
            return;
        }
        String localPlayerName = localPlayer.m_7755_().getString();

        // 1. Units
        for (LivingEntity entity : UnitClientEvents.getAllUnits()) {
            if (!(entity instanceof Unit)) continue;
            Unit unit = (Unit)entity;
            if (UnitClientEvents.getPlayerToEntityRelationship(entity) != Relationship.OWNED 
                && !FogOfWarClientEvents.isPlayerRevealed(unit.getOwnerName()) 
                && !AlliancesClient.isAllied(localPlayerName, unit.getOwnerName())) {
                continue;
            }
            
            double visionRange = getUnitVisionRange(entity);
            if (visionRange <= 0.0) {
                continue; // No vision
            }
            
            ChunkPos unitChunk = entity.m_146902_();
            int range = (int) Math.ceil(visionRange / 16.0);
            
            for (int x = -range; x <= range; ++x) {
                for (int z = -range; z <= range; ++z) {
                    ChunkPos targetChunk = new ChunkPos(unitChunk.f_45578_ + x, unitChunk.f_45579_ + z);
                    if (isChunkInUnitFov(entity, targetChunk, visionRange)) {
                        if (hasLineOfSightToChunk(entity, targetChunk)) {
                            FogOfWarClientEvents.brightChunks.add(targetChunk);
                        }
                    }
                }
            }
        }

        // 2. Buildings
        for (BuildingPlacement building : BuildingClientEvents.getBuildings()) {
            if (BuildingClientEvents.getPlayerToBuildingRelationship(building) != Relationship.OWNED 
                && !FogOfWarClientEvents.isPlayerRevealed(building.ownerName)
                && !AlliancesClient.isAllied(localPlayerName, building.ownerName)) {
                continue;
            }
            ChunkPos buildingChunk = new ChunkPos(building.centrePos);
            int range = (building.getBuilding().hasActiveAddon(com.solegendary.reignofnether.building.addon.GarrisonableBuildingAddon.class) 
                         && com.solegendary.reignofnether.building.addon.GarrisonableBuildingAddon.getNumOccupants(building) > 0 
                         && building.isBuilt 
                         || building.isCapitol) ? 2 : 1;
                         
            for (int x = -range; x <= range; ++x) {
                for (int z = -range; z <= range; ++z) {
                    FogOfWarClientEvents.brightChunks.add(new ChunkPos(buildingChunk.f_45578_ + x, buildingChunk.f_45579_ + z));
                }
            }
        }

        // 3. Process dark/bright chunk transitions
        java.util.concurrent.ConcurrentHashMap.KeySetView<ChunkPos, Boolean> newlyDarkChunks = java.util.concurrent.ConcurrentHashMap.newKeySet();
        newlyDarkChunks.addAll(FogOfWarClientEvents.lastBrightChunks);
        newlyDarkChunks.removeAll(FogOfWarClientEvents.brightChunks);
        for (ChunkPos chunkPos : newlyDarkChunks) {
            onChunkUnexplore(chunkPos);
            for (int x = -1; x <= 1; ++x) {
                for (int z = -1; z <= 1; ++z) {
                    FogOfWarClientEvents.rerenderChunks.add(new ChunkPos(chunkPos.f_45578_ + x, chunkPos.f_45579_ + z));
                }
            }
        }
        
        java.util.concurrent.ConcurrentHashMap.KeySetView<ChunkPos, Boolean> newlyBrightChunks = java.util.concurrent.ConcurrentHashMap.newKeySet();
        newlyBrightChunks.addAll(FogOfWarClientEvents.brightChunks);
        newlyBrightChunks.removeAll(FogOfWarClientEvents.lastBrightChunks);
        for (ChunkPos chunkPos : newlyBrightChunks) {
            onChunkExplore(chunkPos);
        }
        
        if (com.solegendary.reignofnether.orthoview.OrthoviewClientEvents.isEnabled()) {
            FogOfWarClientEvents.semiFrozenChunks.removeIf(p -> FogOfWarClientEvents.brightChunks.contains(new ChunkPos(p)));
        } else {
            FogOfWarClientEvents.semiFrozenChunks.removeIf(p -> FogOfWarClientEvents.brightChunks.contains(new ChunkPos(p)));
        }
        FogOfWarClientEvents.semiFrozenChunks.removeIf(p -> FogOfWarClientEvents.brightChunks.contains(new ChunkPos(p)));
        
        FogOfWarClientEvents.lastBrightChunks.clear();
        FogOfWarClientEvents.lastBrightChunks.addAll(FogOfWarClientEvents.brightChunks);
    }

    @Overwrite
    @net.minecraftforge.eventbus.api.SubscribeEvent
    public static void onClientTick(net.minecraftforge.event.TickEvent.ClientTickEvent evt) {
        if (!FogOfWarClientEvents.isEnabled() || Minecraft.m_91087_().f_91073_ == null || Minecraft.m_91087_().f_91074_ == null || evt.phase != net.minecraftforge.event.TickEvent.Phase.END) {
            return;
        }
        if (updateTicksLeft > 0) {
            --updateTicksLeft;
        } else {
            updateTicksLeft = 2;
            FogOfWarClientEvents.updateFogChunks();
        }
    }
}
