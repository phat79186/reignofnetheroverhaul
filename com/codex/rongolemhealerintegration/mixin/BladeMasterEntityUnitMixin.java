/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  baguchan.slash_illager.entity.BladeMaster
 *  com.solegendary.reignofnether.ability.Abilities
 *  com.solegendary.reignofnether.ability.Ability
 *  com.solegendary.reignofnether.faction.Faction
 *  com.solegendary.reignofnether.resources.ResourceCost
 *  com.solegendary.reignofnether.unit.Checkpoint
 *  com.solegendary.reignofnether.unit.EnemySearchBehaviour
 *  com.solegendary.reignofnether.unit.goals.AbstractMeleeAttackUnitGoal
 *  com.solegendary.reignofnether.unit.goals.GarrisonGoal
 *  com.solegendary.reignofnether.unit.goals.MeleeAttackBuildingGoal
 *  com.solegendary.reignofnether.unit.goals.MoveToTargetBlockGoal
 *  com.solegendary.reignofnether.unit.goals.ReturnResourcesGoal
 *  com.solegendary.reignofnether.unit.goals.SelectedTargetGoal
 *  com.solegendary.reignofnether.unit.goals.UsePortalGoal
 *  com.solegendary.reignofnether.unit.interfaces.AttackerUnit
 *  com.solegendary.reignofnether.unit.interfaces.Unit
 *  it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.monster.SpellcasterIllager
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.codex.rongolemhealerintegration.mixin;

import baguchan.slash_illager.entity.BladeMaster;
import com.codex.rongolemhealerintegration.RonSlashIllagerIntegrationValues;
import com.solegendary.reignofnether.ability.Abilities;
import com.solegendary.reignofnether.ability.Ability;
import com.solegendary.reignofnether.faction.Faction;
import com.solegendary.reignofnether.resources.ResourceCost;
import com.solegendary.reignofnether.unit.Checkpoint;
import com.solegendary.reignofnether.unit.EnemySearchBehaviour;
import com.solegendary.reignofnether.unit.Relationship;
import com.solegendary.reignofnether.unit.UnitServerEvents;
import com.solegendary.reignofnether.unit.goals.AbstractMeleeAttackUnitGoal;
import com.solegendary.reignofnether.unit.goals.GarrisonGoal;
import com.solegendary.reignofnether.unit.goals.MeleeAttackBuildingGoal;
import com.solegendary.reignofnether.unit.goals.MoveToTargetBlockGoal;
import com.solegendary.reignofnether.unit.goals.ReturnResourcesGoal;
import com.solegendary.reignofnether.unit.goals.SelectedTargetGoal;
import com.solegendary.reignofnether.unit.goals.UsePortalGoal;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={BladeMaster.class}, remap=false)
public abstract class BladeMasterEntityUnitMixin
extends SpellcasterIllager
implements Unit,
AttackerUnit {
    @Unique
    private static final EntityDataAccessor<String> RON_OWNER = SynchedEntityData.m_135353_(BladeMaster.class, (EntityDataSerializer)EntityDataSerializers.f_135030_);
    @Unique
    private static final EntityDataAccessor<Integer> RON_SCENARIO_ROLE = SynchedEntityData.m_135353_(BladeMaster.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
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
    private AbstractMeleeAttackUnitGoal ronAttackGoal;
    @Unique
    private MeleeAttackBuildingGoal ronAttackBuildingGoal;
    @Unique
    private BlockPos ronAttackMoveTarget;
    @Unique
    private LivingEntity ronFollowTarget;
    @Unique
    private boolean ronHoldPosition;

    protected BladeMasterEntityUnitMixin(EntityType<? extends SpellcasterIllager> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean m_7490_() {
        return false;
    }

    @Override
    public boolean m_33067_() {
        return false;
    }

    @Override
    public void m_33075_(boolean bl) {
        super.m_33075_(false);
    }

    @Inject(method={"m_6518_"}, at={@At(value="TAIL")}, remap=false)
    private void ronGolemHealerIntegration$removeBanner(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, SpawnGroupData spawnGroupData, CompoundTag compoundTag, CallbackInfoReturnable<SpawnGroupData> callbackInfoReturnable) {
        this.m_33075_(false);
        if (this.m_6844_(EquipmentSlot.HEAD).m_41720_() == Raid.m_37779_().m_41720_()) {
            this.m_8061_(EquipmentSlot.HEAD, ItemStack.f_41583_);
        }
    }

    @Inject(method={"<init>"}, at={@At(value="TAIL")}, remap=false)
    private void ronGolemHealerIntegration$defineUnitData(EntityType<? extends BladeMaster> entityType, Level level, CallbackInfo callbackInfo) {
        this.f_19804_.m_135372_(RON_OWNER, "");
        this.f_19804_.m_135372_(RON_SCENARIO_ROLE, -1);

    }

    @Inject(method={"m_7380_"}, at={@At(value="TAIL")}, require=0, remap=false)
    private void ronGolemHealerIntegration$saveUnitData(CompoundTag compoundTag, CallbackInfo callbackInfo) {
        this.addUnitSaveData(compoundTag);
    }

    @Inject(method={"m_7378_"}, at={@At(value="TAIL")}, require=0, remap=false)
    private void ronGolemHealerIntegration$loadUnitData(CompoundTag compoundTag, CallbackInfo callbackInfo) {
        this.readUnitSaveData(compoundTag);
    }

    @Inject(method={"m_8099_"}, at={@At(value="TAIL")}, remap=false)
    private void ronGolemHealerIntegration$registerRtsGoals(CallbackInfo callbackInfo) {
        if (this.ronMoveGoal != null) {
            return;
        }
        this.initialiseGoals();
        this.f_21345_.m_25352_(0, (Goal)this.ronUsePortalGoal);
        if (this.ronAttackGoal != null) {
            this.f_21345_.m_25352_(1, (Goal)this.ronAttackGoal);
        }
        this.f_21345_.m_25352_(1, (Goal)this.ronAttackBuildingGoal);
        this.f_21345_.m_25352_(1, (Goal)this.ronReturnResourcesGoal);
        this.f_21345_.m_25352_(1, (Goal)this.ronGarrisonGoal);
        this.f_21346_.m_25352_(2, this.ronTargetGoal);
        this.f_21346_.m_25352_(3, (Goal)this.ronMoveGoal);
    }

    @Inject(method={"m_8119_"}, at={@At(value="TAIL")}, remap=false)
    private void ronGolemHealerIntegration$tickAsUnit(CallbackInfo callbackInfo) {
        Unit.tick((Unit)this);
        AttackerUnit.tick((AttackerUnit)this);
    }

    public void updateAbilityButtons() {
    }

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
        return Faction.VILLAGERS;
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
        return 14.0f * this.getAttackCooldownMultiplier();
    }

    public float getAttacksPerSecond() {
        return 20.0f / this.getAttackCooldown();
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
        return 0.3f;
    }

    public float getUnitMaxHealth() {
        return 50.0f;
    }

    public ResourceCost getCost() {
        return RonSlashIllagerIntegrationValues.RESOURCE_COST;
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
        return (String)this.f_19804_.m_135370_(RON_OWNER);
    }

    public void setOwnerName(String string) {
        this.f_19804_.m_135381_(RON_OWNER, string);

    }

    public int getScenarioRoleIndex() {
        return (Integer)this.f_19804_.m_135370_(RON_SCENARIO_ROLE);
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
        this.ronUsePortalGoal = new UsePortalGoal((Mob)this);
        this.ronMoveGoal = new MoveToTargetBlockGoal((Mob)this, false, 0);
        this.ronTargetGoal = new SelectedTargetGoal((Mob)this, true, true);
        this.ronGarrisonGoal = new GarrisonGoal((Mob)this);
        this.ronAttackGoal = new com.codex.rongolemhealerintegration.entity.ai.BladeMasterMeleeAttackGoal((Mob)this, true);
        this.ronAttackBuildingGoal = new MeleeAttackBuildingGoal((Mob)this);
        this.ronReturnResourcesGoal = new ReturnResourcesGoal((Mob)this);
    }


    public void resetBehaviours() {
        AttackerUnit.resetBehaviours((AttackerUnit)this);
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
        if (relationship == Relationship.FRIENDLY) {
            return true;
        }
        return false;
    }
}

