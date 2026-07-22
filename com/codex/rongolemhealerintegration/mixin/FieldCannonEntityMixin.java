package com.codex.rongolemhealerintegration.mixin;

import com.camdenscottc.colonialcannons.entity.FieldCannonEntity;
import com.camdenscottc.colonialcannons.projectile.CannonballEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.codex.rongolemhealerintegration.ron.FieldCannonEntityAccess;

@Mixin(value = {FieldCannonEntity.class}, remap = false)
public abstract class FieldCannonEntityMixin extends Entity implements FieldCannonEntityAccess {

    @Shadow @Final private static EntityDataAccessor<Float> BARREL_PITCH;
    @Shadow @Final private static EntityDataAccessor<Float> CANNON_YAW;
    @Shadow @Final private static EntityDataAccessor<Boolean> IS_LOADED;
    @Shadow @Final private static EntityDataAccessor<Boolean> HAS_GUNPOWDER;
    @Shadow @Final private static EntityDataAccessor<Boolean> LOADED_IS_EXPLOSIVE;
    @Shadow @Final private static EntityDataAccessor<Boolean> SYNCED_IS_MOVING;
    @Shadow @Final private static EntityDataAccessor<Boolean> SYNCED_IS_BACKWARDS;

    @Shadow private boolean isFiring;
    @Shadow private int recoilTicks;
    @Shadow private int pushCooldown;

    @Shadow public abstract float getBarrelPitch();
    @Shadow public abstract float getCannonYaw();
    @Shadow public abstract void setCannonYaw(float yaw);
    @Shadow public abstract void setBarrelPitch(float pitch);
    @Shadow public abstract boolean isLoaded();
    @Shadow public abstract void setLoaded(boolean loaded);
    @Shadow public abstract boolean hasGunpowder();
    @Shadow public abstract void setHasGunpowder(boolean gp);
    @Shadow public abstract boolean isLoadedExplosive();
    @Shadow public abstract void setLoadedExplosive(boolean exp);

    @Shadow public abstract boolean m_6087_();
    @Shadow public abstract boolean m_5829_();
    @Shadow public abstract boolean m_6469_(net.minecraft.world.damagesource.DamageSource source, float amount);
    @Shadow public abstract LivingEntity m_6688_();
    @Shadow public abstract double m_6048_();
    @Shadow public abstract void m_19956_(Entity passenger, Entity.MoveFunction moveFunction);
    @Shadow public abstract void m_7340_(Entity passenger);
    @Shadow public abstract net.minecraft.world.InteractionResult m_6096_(net.minecraft.world.entity.player.Player player, net.minecraft.world.InteractionHand hand);
    @Shadow protected abstract void m_20351_(Entity passenger);
    @Shadow public abstract boolean m_20160_();
    @Shadow public abstract net.minecraft.world.phys.Vec3 m_7688_(LivingEntity passenger);

    @Unique private String ron$ownerName = "";
    @Unique private int ron$reloadCooldown = 0;
    @Unique private float ron$nextShotDamage = 0.0f;
    @Unique private BlockPos ron$targetBlockPos = null;

    public FieldCannonEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public void ron$setOwnerName(String name) {
        this.ron$ownerName = name != null ? name : "";
    }

    @Override
    public String ron$getOwnerName() {
        return this.ron$ownerName;
    }

    @Override
    public void ron$setReloadCooldown(int ticks) {
        this.ron$reloadCooldown = ticks;
    }

    @Override
    public int ron$getReloadCooldown() {
        return this.ron$reloadCooldown;
    }

    @Override
    public void ron$setNextShotDamage(float damage) {
        this.ron$nextShotDamage = damage;
    }

    @Override
    public float ron$getNextShotDamage() {
        return this.ron$nextShotDamage;
    }

    @Override
    public void ron$setTargetBlockPos(BlockPos pos) {
        this.ron$targetBlockPos = pos;
    }

    @Override
    public BlockPos ron$getTargetBlockPos() {
        return this.ron$targetBlockPos;
    }

    @Inject(method = "m_7378_", at = @At("TAIL"))
    private void ron$readOwnerData(CompoundTag tag, CallbackInfo ci) {
        this.ron$ownerName = tag.m_128461_("ronOwnerName");
    }

    @Inject(method = "m_7380_", at = @At("TAIL"))
    private void ron$writeOwnerData(CompoundTag tag, CallbackInfo ci) {
        tag.m_128359_("ronOwnerName", this.ron$ownerName);
    }

    @Overwrite
    protected boolean m_7310_(Entity passenger) {
        return this.m_20197_().isEmpty() && (passenger instanceof net.minecraft.world.entity.player.Player || passenger instanceof Mob);
    }

    @Inject(method = "m_8119_", at = @At("TAIL"))
    private void ron$tickAI(CallbackInfo ci) {
        if (this.m_9236_().f_46443_) {
            return;
        }

        // 1. If NO passenger, scan for a nearby allied unit
        if (this.m_20197_().isEmpty()) {
            if (!this.ron$ownerName.isEmpty()) {
                List<Mob> candidates = this.m_9236_().m_6443_(Mob.class, this.m_20191_().m_82400_(5.0), m -> {
                    if (m instanceof com.solegendary.reignofnether.unit.interfaces.Unit) {
                        com.solegendary.reignofnether.unit.interfaces.Unit u = (com.solegendary.reignofnether.unit.interfaces.Unit) m;
                        return u.getOwnerName().equals(this.ron$ownerName) && m.m_20197_().isEmpty();
                    }
                    return false;
                });
                if (!candidates.isEmpty()) {
                    Mob driver = candidates.get(0);
                    driver.m_7998_((Entity) (Object) this, true); // startRiding
                }
            }
            return;
        }

        // 2. If passenger is an allied unit, handle auto-driving & aiming & reloading & shooting
        Entity passenger = this.m_20197_().get(0);
        if (passenger instanceof Mob) {
            Mob driver = (Mob) passenger;

            // Check if driver is still allied
            boolean isAllied = false;
            if (driver instanceof com.solegendary.reignofnether.unit.interfaces.Unit) {
                com.solegendary.reignofnether.unit.interfaces.Unit u = (com.solegendary.reignofnether.unit.interfaces.Unit) driver;
                isAllied = u.getOwnerName().equals(this.ron$ownerName);
            }
            if (!isAllied) {
                driver.m_8127_(); // dismount
                return;
            }

            // Pathfinding movement control
            boolean isAttackingBuilding = false;
            if (driver instanceof com.solegendary.reignofnether.unit.interfaces.AttackerUnit) {
                isAttackingBuilding = com.solegendary.reignofnether.unit.interfaces.AttackerUnit.isAttackingBuilding((com.solegendary.reignofnether.unit.interfaces.AttackerUnit) driver);
            }
            boolean canMove = false;
            if (driver instanceof com.solegendary.reignofnether.unit.interfaces.Unit) {
                com.solegendary.reignofnether.unit.interfaces.Unit u = (com.solegendary.reignofnether.unit.interfaces.Unit) driver;
                if (u.getMoveGoal().getMoveTarget() != null || (u.getFollowTarget() != null && driver.m_5448_() == null && !isAttackingBuilding)) {
                    canMove = true;
                }
            }
            net.minecraft.world.entity.ai.navigation.PathNavigation nav = driver.m_21573_();
            if (nav != null) {
                if (!canMove) {
                    if (!nav.m_26571_()) {
                        nav.m_26573_();
                    }
                } else if (!nav.m_26571_()) {
                    net.minecraft.world.level.pathfinder.Path path = nav.m_26570_();
                    Vec3 targetPos = null;
                    if (path != null && !path.m_77403_()) {
                        targetPos = path.m_77380_((Entity) driver);
                    } else {
                        BlockPos tPos = nav.m_26567_();
                        if (tPos != null) {
                            targetPos = new Vec3(tPos.m_123341_() + 0.5, tPos.m_123342_(), tPos.m_123343_() + 0.5);
                        }
                    }
                    if (targetPos != null) {
                        Vec3 cannonPos = this.m_20182_();
                        double dx = targetPos.f_82479_ - cannonPos.f_82479_;
                        double dz = targetPos.f_82481_ - cannonPos.f_82481_;
                        double distance = Math.sqrt(dx * dx + dz * dz);
                        if (distance > 0.6) {
                            float targetYaw = (float) (Math.atan2(dz, dx) * 57.29577951308232) - 90.0f;
                            float currentYaw = this.getCannonYaw();
                            float yawDiff = Mth.m_14177_(targetYaw - currentYaw);
                            float yawStep = 1.0f;
                            if (Math.abs(yawDiff) < yawStep) {
                                currentYaw = targetYaw;
                            } else {
                                currentYaw += Math.copySign(yawStep, yawDiff);
                            }
                            this.m_146922_(currentYaw);
                            this.setCannonYaw(currentYaw);

                            double moveX = dx / distance * 0.03;
                            double moveZ = dz / distance * 0.03;
                            this.m_20334_(moveX, this.m_20184_().f_82480_ - 0.04, moveZ);
                            this.f_19804_.m_135381_(SYNCED_IS_MOVING, true);
                            this.pushCooldown = 5;
                        } else {
                            if (path != null && !path.m_77403_()) {
                                path.m_77374_(); // advance node
                            } else {
                                nav.m_26573_(); // stop
                                this.f_19804_.m_135381_(SYNCED_IS_MOVING, false);
                            }
                        }
                    }
                }
            }

            // Automatic reload
            if (!this.isLoaded()) {
                if (this.ron$reloadCooldown > 0) {
                    --this.ron$reloadCooldown;
                } else {
                    this.setLoaded(true);
                    this.setLoadedExplosive(true);
                    this.setHasGunpowder(true);
                    this.ron$reloadCooldown = 400; // 20 second reload
                }
            }

            // Target and shoot
            LivingEntity target = null;
            double tx = 0, ty = 0, tz = 0;
            boolean hasTarget = false;

            if (this.ron$targetBlockPos != null) {
                tx = this.ron$targetBlockPos.m_123341_() + 0.5;
                ty = this.ron$targetBlockPos.m_123342_() + 0.5;
                tz = this.ron$targetBlockPos.m_123343_() + 0.5;
                hasTarget = true;
            } else {
                BlockPos bPos = null;
                if (driver instanceof com.solegendary.reignofnether.unit.interfaces.AttackerUnit) {
                    com.solegendary.reignofnether.unit.interfaces.AttackerUnit au = (com.solegendary.reignofnether.unit.interfaces.AttackerUnit) driver;
                    net.minecraft.world.entity.ai.goal.Goal goal = au.getAttackBuildingGoal();
                    if (goal instanceof com.solegendary.reignofnether.unit.goals.MeleeAttackBuildingGoal) {
                        com.solegendary.reignofnether.building.BuildingPlacement bp = ((com.solegendary.reignofnether.unit.goals.MeleeAttackBuildingGoal) goal).getBuildingTarget();
                        if (bp != null) {
                            bPos = bp.centrePos;
                        }
                    } else if (goal instanceof com.solegendary.reignofnether.unit.goals.RangedAttackBuildingGoal) {
                        com.solegendary.reignofnether.building.BuildingPlacement bp = ((com.solegendary.reignofnether.unit.goals.RangedAttackBuildingGoal) goal).getBuildingTarget();
                        if (bp != null) {
                            bPos = bp.centrePos;
                        }
                    }
                }

                if (bPos != null) {
                    tx = bPos.m_123341_() + 0.5;
                    ty = bPos.m_123342_() + 0.5;
                    tz = bPos.m_123343_() + 0.5;
                    hasTarget = true;
                } else if (driver.m_5448_() != null && driver.m_5448_().m_6084_()) {
                    target = driver.m_5448_();
                    tx = target.m_20185_();
                    ty = target.m_20186_() + target.m_20192_();
                    tz = target.m_20189_();
                    hasTarget = true;
                }
            }

            if (hasTarget) {
                double cx = this.m_20185_();
                double cy = this.m_20186_() + 1.1;
                double cz = this.m_20189_();

                double dx = tx - cx;
                double dy = ty - cy;
                double dz = tz - cz;
                double hDist = Math.sqrt(dx * dx + dz * dz);

                float targetYaw = (float) (Math.atan2(dz, dx) * 57.29577951308232) - 90.0f;
                float targetPitch = (float) (Math.atan2(dy, hDist) * 57.29577951308232);
                targetPitch = Math.max(-40.0f, Math.min(9.5f, targetPitch));

                float currentYaw = this.getCannonYaw();
                float currentPitch = this.getBarrelPitch();

                float yawDiff = Mth.m_14177_(targetYaw - currentYaw);
                float yawStep = 1.0f;
                if (Math.abs(yawDiff) < yawStep) {
                    currentYaw = targetYaw;
                } else {
                    currentYaw += Math.copySign(yawStep, yawDiff);
                }
                this.setCannonYaw(currentYaw);
                this.m_146922_(currentYaw);

                float pitchDiff = targetPitch - currentPitch;
                float pitchStep = 0.5f;
                if (Math.abs(pitchDiff) < pitchStep) {
                    currentPitch = targetPitch;
                } else {
                    currentPitch += Math.copySign(pitchStep, pitchDiff);
                }
                this.setBarrelPitch(currentPitch);

                if (Math.abs(yawDiff) < 10.0f && Math.abs(pitchDiff) < 5.0f && this.isLoaded() && this.hasGunpowder()) {
                    this.ron$nextShotDamage = 100.0f;
                    this.fire();
                }
            } else {
                // Smoothly return barrel to neutral position: aligned with body yaw (getYRot / m_146908_), pitch 0.0f
                float targetYaw = this.m_146908_();
                float targetPitch = 0.0f;

                float currentYaw = this.getCannonYaw();
                float currentPitch = this.getBarrelPitch();

                float yawDiff = Mth.m_14177_(targetYaw - currentYaw);
                float yawStep = 1.0f;
                if (Math.abs(yawDiff) < yawStep) {
                    currentYaw = targetYaw;
                } else {
                    currentYaw += Math.copySign(yawStep, yawDiff);
                }
                this.setCannonYaw(currentYaw);
                this.m_146922_(currentYaw);

                float pitchDiff = targetPitch - currentPitch;
                float pitchStep = 0.5f;
                if (Math.abs(pitchDiff) < pitchStep) {
                    currentPitch = targetPitch;
                } else {
                    currentPitch += Math.copySign(pitchStep, pitchDiff);
                }
                this.setBarrelPitch(currentPitch);
            }
        }
    }

    @Overwrite
    private void fire() {
        Level level = this.m_9236_();
        if (level instanceof ServerLevel) {
            int i;
            ServerLevel serverLevel = (ServerLevel) level;
            boolean isExplosive = this.isLoadedExplosive();
            CannonballEntity cannonball = new CannonballEntity(this.m_9236_(), (Entity) (Object) this, isExplosive);
            if (isExplosive) {
                cannonball.setExplosionPower(1.33f);
            }
            float pitch = this.getBarrelPitch();
            float yaw = this.getCannonYaw();
            double speed = 4.0;
            double vx = -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * speed;
            double vy = Math.sin(Math.toRadians(pitch)) * speed;
            double vz = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * speed;
            cannonball.m_20334_(vx, vy, vz);
            double barrelLength = 3.1;
            double barrelBaseHeight = 1.1;
            double spawnX = this.m_20185_() - Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * barrelLength;
            double spawnY = this.m_20186_() + barrelBaseHeight + Math.sin(Math.toRadians(pitch)) * barrelLength;
            double spawnZ = this.m_20189_() + Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * barrelLength;
            cannonball.m_6034_(spawnX, spawnY, spawnZ);

            if (this.ron$nextShotDamage > 0.0f) {
                cannonball.setDamage(this.ron$nextShotDamage);
                this.ron$nextShotDamage = 0.0f;
            }

            serverLevel.m_7967_((Entity) cannonball);
            this.m_5496_(SoundEvents.f_11913_, 3.0f, 0.8f + this.f_19796_.m_188501_() * 0.2f);
            for (i = 0; i < 35; ++i) {
                double offsetX = (this.f_19796_.m_188500_() - 0.5) * 1.5;
                double offsetY = this.f_19796_.m_188500_() * 1.0;
                double offsetZ = (this.f_19796_.m_188500_() - 0.5) * 1.5;
                serverLevel.m_8767_((ParticleOptions) ParticleTypes.f_123796_, spawnX + offsetX, spawnY + offsetY, spawnZ + offsetZ, 1, 0.4, 0.4, 0.4, 0.08);
            }
            for (i = 0; i < 25; ++i) {
                serverLevel.m_8767_((ParticleOptions) ParticleTypes.f_123777_, spawnX, spawnY, spawnZ, 1, 0.4, 0.4, 0.4, 0.05);
            }
            double fuseX = this.m_20185_() + Math.sin(Math.toRadians(yaw)) * 0.5;
            double fuseZ = this.m_20189_() - Math.cos(Math.toRadians(yaw)) * 0.5;
            for (int i2 = 0; i2 < 5; ++i2) {
                serverLevel.m_8767_((ParticleOptions) ParticleTypes.f_123744_, fuseX, this.m_20186_() + 1.5, fuseZ, 1, 0.1, 0.1, 0.1, 0.02);
            }
            double recoilStrength = 0.25;
            double recoilX = Math.sin(Math.toRadians(yaw)) * recoilStrength;
            double recoilZ = -Math.cos(Math.toRadians(yaw)) * recoilStrength;
            this.m_20334_(recoilX, 0.03, recoilZ);
            this.setLoaded(false);
            this.setHasGunpowder(false);
            this.isFiring = true;
            this.recoilTicks = 8;
            this.f_19804_.m_135381_(SYNCED_IS_MOVING, true);
            this.f_19804_.m_135381_(SYNCED_IS_BACKWARDS, true);
        }
    }
}
