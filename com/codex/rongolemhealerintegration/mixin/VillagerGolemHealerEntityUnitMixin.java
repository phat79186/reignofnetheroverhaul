/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.solegendary.reignofnether.ability.Abilities
 *  com.solegendary.reignofnether.ability.Ability
 *  com.solegendary.reignofnether.building.RangeIndicator
 *  com.solegendary.reignofnether.faction.Faction
 *  com.solegendary.reignofnether.resources.ResourceCost
 *  com.solegendary.reignofnether.unit.Checkpoint
 *  com.solegendary.reignofnether.unit.Relationship
 *  com.solegendary.reignofnether.unit.UnitServerEvents
 *  com.solegendary.reignofnether.unit.goals.GarrisonGoal
 *  com.solegendary.reignofnether.unit.goals.MoveToTargetBlockGoal
 *  com.solegendary.reignofnether.unit.goals.ReturnResourcesGoal
 *  com.solegendary.reignofnether.unit.goals.SelectedTargetGoal
 *  com.solegendary.reignofnether.unit.goals.UsePortalGoal
 *  com.solegendary.reignofnether.unit.interfaces.Unit
 *  it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
 *  net.mcreator.villagergolemhealer.entity.VillagergolemhealerEntity
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.RandomStrollGoal
 *  net.minecraft.world.entity.animal.IronGolem
 *  net.minecraft.world.entity.npc.AbstractVillager
 *  net.minecraft.world.entity.npc.Villager
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.codex.rongolemhealerintegration.mixin;

import com.codex.rongolemhealerintegration.RonGolemHealerIntegrationValues;
import com.solegendary.reignofnether.ability.Abilities;
import com.solegendary.reignofnether.ability.Ability;
import com.solegendary.reignofnether.building.RangeIndicator;
import com.solegendary.reignofnether.faction.Faction;
import com.solegendary.reignofnether.resources.ResourceCost;
import com.solegendary.reignofnether.unit.Checkpoint;
import com.solegendary.reignofnether.unit.Relationship;
import com.solegendary.reignofnether.unit.UnitServerEvents;
import com.solegendary.reignofnether.unit.goals.GarrisonGoal;
import com.solegendary.reignofnether.unit.goals.MoveToTargetBlockGoal;
import com.solegendary.reignofnether.unit.goals.ReturnResourcesGoal;
import com.solegendary.reignofnether.unit.goals.SelectedTargetGoal;
import com.solegendary.reignofnether.unit.goals.UsePortalGoal;
import com.solegendary.reignofnether.unit.interfaces.Unit;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.mcreator.villagergolemhealer.entity.VillagergolemhealerEntity;
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
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={VillagergolemhealerEntity.class}, remap=false)
public abstract class VillagerGolemHealerEntityUnitMixin
extends Villager
implements Unit,
RangeIndicator {
    @Unique
    private static final EntityDataAccessor<String> RON_OWNER = SynchedEntityData.m_135353_(VillagergolemhealerEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135030_);
    @Unique
    private static final EntityDataAccessor<Integer> RON_SCENARIO_ROLE = SynchedEntityData.m_135353_(VillagergolemhealerEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
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
    private LivingEntity ronFollowTarget;
    @Unique
    private boolean ronHoldPosition;

    protected VillagerGolemHealerEntityUnitMixin(EntityType<? extends Villager> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method={"m_8097_"}, at={@At(value="TAIL")}, remap=false)
    private void ronGolemHealerIntegration$defineSynchedData(CallbackInfo callbackInfo) {
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
        this.f_21345_.m_25352_(0, (Goal)this.ronMoveGoal);
        this.f_21345_.m_25352_(1, (Goal)this.ronGarrisonGoal);
        this.f_21345_.m_25352_(1, (Goal)this.ronReturnResourcesGoal);
        this.f_21346_.m_262460_(goal -> true);
        this.f_21346_.m_25352_(0, this.ronTargetGoal);
        this.f_21345_.m_262460_(goal -> goal instanceof RandomStrollGoal);
    }

    @Inject(method={"m_6075_"}, at={@At(value="TAIL")}, remap=false)
    private void ronGolemHealerIntegration$tickAsUnit(CallbackInfo callbackInfo) {
        Unit.tick((Unit)this);
    }

    protected void m_8024_() {
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

    public float getMovementSpeed() {
        return 0.3f;
    }

    public float getUnitMaxHealth() {
        return 50.0f;
    }

    public ResourceCost getCost() {
        return RonGolemHealerIntegrationValues.RESOURCE_COST;
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
        this.ronReturnResourcesGoal = new ReturnResourcesGoal((Mob)this);
    }

    public void resetBehaviours() {
    }

    public Set<BlockPos> getHighlightBps() {
        return this.ronHighlightBps;
    }

    public void setHighlightBps(Set<BlockPos> set) {
        this.ronHighlightBps = set;
    }

    public boolean m_7307_(Entity entity) {
        if (super.m_7307_(entity)) {
            return true;
        }
        if (entity instanceof Player || entity instanceof AbstractVillager || entity instanceof IronGolem) {
            return true;
        }
        Relationship relationship = UnitServerEvents.getUnitToEntityRelationship((Unit)this, (Entity)entity);
        return relationship == Relationship.FRIENDLY || relationship == Relationship.OWNED;
    }

    public boolean canPickUpEquipment(ItemStack itemStack) {
        return false;
    }
}

