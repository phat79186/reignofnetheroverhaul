package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.ability.Abilities;
import com.solegendary.reignofnether.ability.Ability;
import com.solegendary.reignofnether.faction.Faction;
import com.solegendary.reignofnether.resources.ResourceCost;
import com.solegendary.reignofnether.unit.Checkpoint;
import com.solegendary.reignofnether.unit.EnemySearchBehaviour;
import com.solegendary.reignofnether.unit.Relationship;
import com.solegendary.reignofnether.unit.UnitServerEvents;
import com.solegendary.reignofnether.unit.goals.*;
import com.solegendary.reignofnether.unit.interfaces.AttackerUnit;
import com.solegendary.reignofnether.unit.interfaces.Unit;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {WitherBoss.class}, remap = false)
public abstract class WitherBossUnitMixin extends Mob implements Unit, AttackerUnit {
    @Unique
    private static final EntityDataAccessor<String> RON_OWNER = SynchedEntityData.m_135353_(WitherBoss.class, EntityDataSerializers.f_135030_);
    @Unique
    private static final EntityDataAccessor<Integer> RON_SCENARIO_ROLE = SynchedEntityData.m_135353_(WitherBoss.class, EntityDataSerializers.f_135028_);

    @Unique
    private final Object2ObjectArrayMap<Ability, Float> ronCooldowns = Unit.createCooldownMap();
    @Unique
    private final Object2ObjectArrayMap<Ability, Integer> ronCharges = new Object2ObjectArrayMap();
    @Unique
    private final ArrayList<Checkpoint> ronCheckpoints = new ArrayList();
    @Unique
    private final Abilities ronAbilities = new Abilities();
    @Unique
    private final List<ItemStack> ronItems = new ArrayList<ItemStack>();
    @Unique
    private Set<BlockPos> ronHighlightBps = new HashSet<BlockPos>();
    @Unique
    private Ability ronAutocast;
    @Unique
    private int ronEatingTicksLeft;
    @Unique
    private BlockPos ronAnchor = BlockPos.f_121853_;
    @Unique
    private GarrisonGoal ronGarrisonGoal;
    @Unique
    private UsePortalGoal ronUsePortalGoal;
    @Unique
    private MoveToTargetBlockGoal ronMoveGoal;
    @Unique
    private SelectedTargetGoal<? extends LivingEntity> ronTargetGoal;
    @Unique
    private ReturnResourcesGoal ronReturnResourcesGoal;
    @Unique
    private EnemySearchBehaviour ronAttackSearchBehaviour = EnemySearchBehaviour.NONE;
    @Unique
    private Goal ronAttackGoal;
    @Unique
    private Goal ronAttackBuildingGoal;
    @Unique
    private BlockPos ronAttackMoveTarget;
    @Unique
    private LivingEntity ronFollowTarget;
    @Unique
    private boolean ronHoldPosition;

    protected WitherBossUnitMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "m_8097_", at = @At("TAIL"), remap = false)
    private void ronGolemHealerIntegration$defineSynchedData(CallbackInfo ci) {
        this.f_19804_.m_135372_(RON_OWNER, "");
        this.f_19804_.m_135372_(RON_SCENARIO_ROLE, -1);
    }

    @Override
    public void m_7380_(CompoundTag compoundTag) {
        super.m_7380_(compoundTag);
        this.addUnitSaveData(compoundTag);
    }

    @Override
    public void m_7378_(CompoundTag compoundTag) {
        super.m_7378_(compoundTag);
        this.readUnitSaveData(compoundTag);
    }

    @Inject(method = "m_8099_", at = @At("TAIL"), remap = false)
    private void ronGolemHealerIntegration$registerRtsGoals(CallbackInfo callbackInfo) {
        if (this.ronMoveGoal != null) {
            return;
        }
        this.initialiseGoals();
        this.f_21345_.m_25352_(0, (Goal) this.ronUsePortalGoal);
        this.f_21345_.m_25352_(1, (Goal) this.ronAttackGoal);
        this.f_21345_.m_25352_(1, (Goal) this.ronAttackBuildingGoal);
        this.f_21345_.m_25352_(1, (Goal) this.ronReturnResourcesGoal);
        this.f_21345_.m_25352_(1, (Goal) this.ronGarrisonGoal);
        this.f_21346_.m_262460_(goal -> true); // Clear vanilla target selection goals so Wither aligns with RTS target choices
        this.f_21346_.m_25352_(2, this.ronTargetGoal);
        this.f_21346_.m_25352_(3, (Goal) this.ronMoveGoal);
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (!this.m_9236_().f_46443_) {
            Unit.tick((Unit) this);
            AttackerUnit.tick((AttackerUnit) this);
        }
    }

    public void updateAbilityButtons() {}

    public Object2ObjectArrayMap<Ability, Float> getCooldowns() {
        return this.ronCooldowns;
    }

    public boolean hasAutocast(Ability ability) {
        return this.ronAutocast == ability;
    }

    public void setAutocast(Ability ability) {
        this.ronAutocast = ability;
    }

    public Object2ObjectArrayMap<Ability, Integer> getCharges() {
        return this.ronCharges;
    }

    public void setEatingTicksLeft(int n) {
        this.ronEatingTicksLeft = n;
    }

    public int getEatingTicksLeft() {
        return this.ronEatingTicksLeft;
    }

    public void setAnchor(BlockPos blockPos) {
        this.ronAnchor = blockPos;
    }

    public BlockPos getAnchor() {
        return this.ronAnchor;
    }

    public ArrayList<Checkpoint> getCheckpoints() {
        return this.ronCheckpoints;
    }

    public GarrisonGoal getGarrisonGoal() {
        return this.ronGarrisonGoal;
    }

    public boolean canGarrison() {
        return this.ronGarrisonGoal != null;
    }

    public UsePortalGoal getUsePortalGoal() {
        return this.ronUsePortalGoal;
    }

    public boolean canUsePortal() {
        return this.ronUsePortalGoal != null;
    }

    public Faction getFaction() {
        return Faction.PIGLINS;
    }

    public Abilities getAbilities() {
        return this.ronAbilities;
    }

    public List<ItemStack> getItems() {
        return this.ronItems;
    }

    public MoveToTargetBlockGoal getMoveGoal() {
        return this.ronMoveGoal;
    }

    public SelectedTargetGoal<? extends LivingEntity> getTargetGoal() {
        return this.ronTargetGoal;
    }

    public ReturnResourcesGoal getReturnResourcesGoal() {
        return this.ronReturnResourcesGoal;
    }

    public boolean getWillRetaliate() {
        return true;
    }

    public float getAttackCooldown() {
        return 40.0f;
    }

    public float getAttacksPerSecond() {
        return 0.5f;
    }

    public boolean getAggressiveWhenIdle() {
        return true;
    }

    public BlockPos getAttackMoveTarget() {
        return this.ronAttackMoveTarget;
    }

    public boolean canAttackBuildings() {
        return this.ronAttackBuildingGoal != null;
    }

    public Goal getAttackGoal() {
        return this.ronAttackGoal;
    }

    public Goal getAttackBuildingGoal() {
        return this.ronAttackBuildingGoal;
    }

    public EnemySearchBehaviour getEnemySearchBehaviour() {
        return this.ronAttackSearchBehaviour;
    }

    public void setEnemySearchBehaviour(EnemySearchBehaviour enemySearchBehaviour) {
        this.ronAttackSearchBehaviour = enemySearchBehaviour;
    }

    public float getMovementSpeed() {
        return (float) this.m_21133_(net.minecraft.world.entity.ai.attributes.Attributes.f_22279_);
    }

    public float getUnitMaxHealth() {
        return this.m_21233_();
    }

    public ResourceCost getCost() {
        return com.codex.rongolemhealerintegration.ron.RonGolemHealerProductionItems.WITHER_BOSS.COST;
    }

    public int getMaxResources() {
        return 0;
    }

    public LivingEntity getFollowTarget() {
        return this.ronFollowTarget;
    }

    public boolean getHoldPosition() {
        return this.ronHoldPosition;
    }

    public void setHoldPosition(boolean bl) {
        this.ronHoldPosition = bl;
    }

    public String getOwnerName() {
        return (String) this.f_19804_.m_135370_(RON_OWNER);
    }

    public void setOwnerName(String string) {
        this.f_19804_.m_135381_(RON_OWNER, string);
    }

    public int getScenarioRoleIndex() {
        return (Integer) this.f_19804_.m_135370_(RON_SCENARIO_ROLE);
    }

    public void setScenarioRoleIndex(int n) {
        this.f_19804_.m_135381_(RON_SCENARIO_ROLE, n);
    }

    public void setAttackMoveTarget(BlockPos blockPos) {
        this.ronAttackMoveTarget = blockPos;
    }

    public void setFollowTarget(LivingEntity livingEntity) {
        this.ronFollowTarget = livingEntity;
    }

    public void initialiseGoals() {
        if (this.ronMoveGoal != null) {
            return;
        }
        this.ronUsePortalGoal = new UsePortalGoal((Mob) this);
        this.ronMoveGoal = new MoveToTargetBlockGoal((Mob) this, false, 0);
        this.ronTargetGoal = new SelectedTargetGoal((Mob) this, true, true);
        this.ronGarrisonGoal = new GarrisonGoal((Mob) this);
        this.ronAttackGoal = new MeleeAttackUnitGoal((Mob) this, true);
        this.ronAttackBuildingGoal = new MeleeAttackBuildingGoal((Mob) this);
        this.ronReturnResourcesGoal = new ReturnResourcesGoal((Mob) this);
    }

    public void resetBehaviours() {
        AttackerUnit.resetBehaviours((AttackerUnit) this);
    }

    public Set<BlockPos> getHighlightBps() {
        return this.ronHighlightBps;
    }

    public void setHighlightBps(Set<BlockPos> set) {
        this.ronHighlightBps = set;
    }

    @Override
    public boolean m_7307_(Entity target) {
        if (target == this) {
            return true;
        }
        if (!(target instanceof LivingEntity)) {
            return false;
        }
        String owner1 = this.getOwnerName();
        String owner2 = "";
        if (target instanceof Unit) {
            owner2 = ((Unit) target).getOwnerName();
        } else if (target instanceof Player) {
            owner2 = ((Player) target).m_7755_().getString();
        }
        if (owner1.isEmpty() && owner2.isEmpty()) {
            return super.m_7307_(target);
        }
        Relationship relationship = UnitServerEvents.getUnitToEntityRelationship((Unit) this, target);
        if (relationship == Relationship.FRIENDLY || relationship == Relationship.OWNED) {
            return true;
        }
        return false;
    }

    @Inject(method = "shootSkullAtCoords", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void ronGolemHealerIntegration$onShootSkullMojang(int headIndex, double x, double y, double z, boolean isDangerous, CallbackInfo ci) {
        boolean hasAttackCommand = this.getAttackMoveTarget() != null 
            || this.hasLivingTarget() 
            || AttackerUnit.isAttackingBuilding(this);
        if (!hasAttackCommand) {
            ci.cancel();
        }
    }

    @Inject(method = "m_31448_", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void ronGolemHealerIntegration$onShootSkullSrg(int headIndex, double x, double y, double z, boolean isDangerous, CallbackInfo ci) {
        boolean hasAttackCommand = this.getAttackMoveTarget() != null 
            || this.hasLivingTarget() 
            || AttackerUnit.isAttackingBuilding(this);
        if (!hasAttackCommand) {
            ci.cancel();
        }
    }

    @Override
    public void m_6457_(net.minecraft.server.level.ServerPlayer player) {
        super.m_6457_(player);
    }

    @Override
    public void m_6452_(net.minecraft.server.level.ServerPlayer player) {
        super.m_6452_(player);
    }
}
