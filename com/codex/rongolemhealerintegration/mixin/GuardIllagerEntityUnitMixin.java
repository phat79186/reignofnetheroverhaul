/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.min01.guardillagers.entity.GuardIllager
 *  com.solegendary.reignofnether.ability.Abilities
 *  com.solegendary.reignofnether.ability.Ability
 *  com.solegendary.reignofnether.faction.Faction
 *  com.solegendary.reignofnether.resources.ResourceCost
 *  com.solegendary.reignofnether.unit.Checkpoint
 *  com.solegendary.reignofnether.unit.EnemySearchBehaviour
 *  com.solegendary.reignofnether.unit.Relationship
 *  com.solegendary.reignofnether.unit.UnitServerEvents
 *  com.solegendary.reignofnether.unit.goals.AbstractMeleeAttackUnitGoal
 *  com.solegendary.reignofnether.unit.goals.GarrisonGoal
 *  com.solegendary.reignofnether.unit.goals.MeleeAttackBuildingGoal
 *  com.solegendary.reignofnether.unit.goals.MeleeAttackUnitGoal
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
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.AttributeInstance
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.animal.IronGolem
 *  net.minecraft.world.entity.monster.AbstractIllager
 *  net.minecraft.world.entity.npc.AbstractVillager
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.ShieldItem
 *  net.minecraft.world.item.alchemy.Potion
 *  net.minecraft.world.item.alchemy.PotionUtils
 *  net.minecraft.world.item.alchemy.Potions
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.codex.rongolemhealerintegration.mixin;

import com.codex.rongolemhealerintegration.RonGuardIllagerIntegrationValues;
import com.codex.rongolemhealerintegration.entity.ai.GuardIllagerCustomAttackGoal;
import com.min01.guardillagers.entity.GuardIllager;
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
import com.solegendary.reignofnether.unit.goals.MeleeAttackUnitGoal;
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
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={GuardIllager.class}, remap=false)
public abstract class GuardIllagerEntityUnitMixin
extends AbstractIllager
implements Unit,
AttackerUnit {
    @Shadow(remap=false)
    private int itemUseTimer;
    @Shadow(remap=false)
    private int guardCooldown;
    @Unique
    private static final UUID SPEED_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
    @Unique
    private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(SPEED_UUID, "Drinking speed penalty", -0.24, AttributeModifier.Operation.ADDITION);
    @Unique
    private static final UUID KNOCKBACK_UUID = UUID.fromString("8742C557-FCD8-4EDC-8B97-D295E2F415FF");
    @Unique
    private static final AttributeModifier KNOCKBACK_MODIFIER = new AttributeModifier(KNOCKBACK_UUID, "Shield knockback resistance", 0.75, AttributeModifier.Operation.ADDITION);
    @Unique
    private static final EntityDataAccessor<String> RON_OWNER = SynchedEntityData.m_135353_(GuardIllager.class, (EntityDataSerializer)EntityDataSerializers.f_135030_);
    @Unique
    private static final EntityDataAccessor<Integer> RON_SCENARIO_ROLE = SynchedEntityData.m_135353_(GuardIllager.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
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
    @Unique
    private int ronHealingPotionsUsed = 0;
    @Unique
    private int ronPotionCooldown = 0;

    protected GuardIllagerEntityUnitMixin(EntityType<? extends AbstractIllager> entityType, Level level) {
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
    private void ronGolemHealerIntegration$removeBanner(net.minecraft.world.level.ServerLevelAccessor serverLevelAccessor, net.minecraft.world.DifficultyInstance difficultyInstance, net.minecraft.world.entity.MobSpawnType mobSpawnType, net.minecraft.world.entity.SpawnGroupData spawnGroupData, CompoundTag compoundTag, CallbackInfoReturnable<net.minecraft.world.entity.SpawnGroupData> callbackInfoReturnable) {
        this.m_33075_(false);
        if (this.m_6844_(EquipmentSlot.HEAD).m_41720_() == Raid.m_37779_().m_41720_()) {
            this.m_8061_(EquipmentSlot.HEAD, ItemStack.f_41583_);
        }
    }

    @Inject(method={"m_8097_"}, at={@At(value="TAIL")}, remap=false)
    private void ronGolemHealerIntegration$defineSynchedData(CallbackInfo callbackInfo) {
        this.f_19804_.m_135372_(RON_OWNER, "");
        this.f_19804_.m_135372_(RON_SCENARIO_ROLE, -1);
    }

    @Inject(method={"m_7380_"}, at={@At(value="TAIL")}, require=0, remap=false)
    private void ronGolemHealerIntegration$saveUnitData(CompoundTag compoundTag, CallbackInfo callbackInfo) {
        this.addUnitSaveData(compoundTag);
        compoundTag.m_128405_("RonHealingPotionsUsed", this.ronHealingPotionsUsed);
        compoundTag.m_128405_("RonPotionCooldown", this.ronPotionCooldown);
    }

    @Inject(method={"m_7378_"}, at={@At(value="TAIL")}, require=0, remap=false)
    private void ronGolemHealerIntegration$loadUnitData(CompoundTag compoundTag, CallbackInfo callbackInfo) {
        this.readUnitSaveData(compoundTag);
        this.ronHealingPotionsUsed = compoundTag.m_128451_("RonHealingPotionsUsed");
        this.ronPotionCooldown = compoundTag.m_128451_("RonPotionCooldown");
    }

    @Inject(method={"m_8099_"}, at={@At(value="TAIL")}, remap=false)
    private void ronGolemHealerIntegration$registerRtsGoals(CallbackInfo callbackInfo) {
        if (this.ronMoveGoal != null) {
            return;
        }
        this.initialiseGoals();
        this.f_21345_.m_262460_(goal -> true);
        this.f_21345_.m_25352_(0, (Goal)new FloatGoal((Mob)this));
        this.f_21345_.m_25352_(0, (Goal)this.ronUsePortalGoal);
        this.f_21345_.m_25352_(1, (Goal)new GuardIllagerCustomAttackGoal((GuardIllager)(Object)this));
        this.f_21345_.m_25352_(3, (Goal)this.ronMoveGoal);
        this.f_21346_.m_262460_(goal -> true);
        this.f_21346_.m_25352_(2, this.ronTargetGoal);
    }

    @Inject(method={"m_7307_"}, at={@At(value="HEAD")}, cancellable=true, remap=false)
    private void ronGolemHealerIntegration$isAlliedTo(Entity entity, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (entity instanceof Player || entity instanceof AbstractVillager || entity instanceof IronGolem) {
            callbackInfoReturnable.setReturnValue(true);
            return;
        }
        Relationship relationship = UnitServerEvents.getUnitToEntityRelationship((Unit)this, (Entity)entity);
        if (relationship == Relationship.FRIENDLY || relationship == Relationship.OWNED) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }

    @Inject(method={"m_8107_"}, at={@At(value="HEAD")}, cancellable=true, remap=false)
    private void ronGolemHealerIntegration$tickAsUnit(CallbackInfo callbackInfo) {
        Unit.tick((Unit)this);
        AttackerUnit.tick((AttackerUnit)this);
        if (!this.m_9236_().f_46443_) {
            this.m_21559_(false);
            GuardIllager guardIllager = (GuardIllager)(Object)this;
            if (guardIllager.isDrinkingPotion()) {
                guardIllager.setGuardSelf(false);
                this.m_21051_(Attributes.f_22278_).m_22130_(KNOCKBACK_MODIFIER);
                if (this.itemUseTimer-- <= 0) {
                    guardIllager.setDrinkingPotion(false);
                    ItemStack itemStack = this.m_21206_();
                    if (guardIllager.getGuardLevel() >= 1) {
                        this.m_8061_(EquipmentSlot.OFFHAND, GuardIllager.getIllagerShield());
                    } else {
                        this.m_8061_(EquipmentSlot.OFFHAND, ItemStack.f_41583_);
                    }
                    if (itemStack.m_41720_() == Items.f_42589_) {
                        for (MobEffectInstance mobEffectInstance : PotionUtils.m_43547_((ItemStack)itemStack)) {
                            if (mobEffectInstance.m_19544_() == MobEffects.f_19596_) {
                                this.m_7292_(new MobEffectInstance(mobEffectInstance.m_19544_(), 200, mobEffectInstance.m_19564_()));
                            } else {
                                this.m_7292_(new MobEffectInstance(mobEffectInstance));
                            }
                        }
                    }
                    this.m_21051_(Attributes.f_22279_).m_22130_(SPEED_MODIFIER);
                }
            } else if (this.getHoldPosition()) {
                if (!guardIllager.isGuardSelf() && this.guardCooldown < 1) {
                    this.itemUseTimer = 100;
                    guardIllager.setGuardSelf(true);
                    this.m_6672_(InteractionHand.OFF_HAND);
                    AttributeInstance attributeInstance = this.m_21051_(Attributes.f_22279_);
                    AttributeInstance attributeInstance2 = this.m_21051_(Attributes.f_22278_);
                    if (attributeInstance2 != null) {
                        attributeInstance2.m_22130_(KNOCKBACK_MODIFIER);
                        attributeInstance2.m_22125_(KNOCKBACK_MODIFIER);
                    }
                    if (attributeInstance != null) {
                        attributeInstance.m_22130_(SPEED_MODIFIER);
                        attributeInstance.m_22125_(SPEED_MODIFIER);
                    }
                } else if (guardIllager.isGuardSelf()) {
                    this.itemUseTimer = 100;
                }
            } else if (guardIllager.isGuardSelf()) {
                if (this.itemUseTimer-- <= 0 || this.m_5448_() == null) {
                    guardIllager.setGuardSelf(false);
                    this.m_21051_(Attributes.f_22279_).m_22130_(SPEED_MODIFIER);
                    this.m_21051_(Attributes.f_22278_).m_22130_(KNOCKBACK_MODIFIER);
                    this.m_5810_();
                }
            } else {
                Potion potion = null;
                if (this.ronPotionCooldown <= 0) {
                    float f = this.m_21223_() / this.m_21233_();
                    if (this.ronHealingPotionsUsed == 0 && f <= 0.5f) {
                        potion = Potions.f_43623_;
                        this.ronHealingPotionsUsed = 1;
                        this.ronPotionCooldown = 400;
                    } else if (this.ronHealingPotionsUsed == 1 && f <= 0.25f) {
                        potion = Potions.f_43623_;
                        this.ronHealingPotionsUsed = 2;
                        this.ronPotionCooldown = 400;
                    } else if (this.m_5448_() != null && !this.m_21023_(MobEffects.f_19596_) && this.m_5448_().m_20280_((Entity)this) > 121.0) {
                        potion = Potions.f_43612_;
                        this.ronPotionCooldown = 600;
                    }
                }
                if (potion != null) {
                    this.m_5810_();
                    this.m_8061_(EquipmentSlot.OFFHAND, PotionUtils.m_43549_((ItemStack)new ItemStack((ItemLike)Items.f_42589_), potion));
                    this.itemUseTimer = this.m_21206_().m_41779_();
                    guardIllager.setDrinkingPotion(true);
                    this.m_9236_().m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12551_, this.m_5720_(), 1.0f, 0.8f + this.f_19796_.m_188501_() * 0.4f);
                    AttributeInstance attributeInstance = this.m_21051_(Attributes.f_22279_);
                    if (attributeInstance != null) {
                        attributeInstance.m_22130_(SPEED_MODIFIER);
                        attributeInstance.m_22125_(SPEED_MODIFIER);
                    }
                } else if (this.guardCooldown < 1 && this.m_21206_().m_41720_() instanceof ShieldItem && this.f_19796_.m_188501_() < 0.0055f && this.m_5448_() != null) {
                    AttributeInstance attributeInstance;
                    this.itemUseTimer = 100;
                    guardIllager.setGuardSelf(true);
                    this.m_6672_(InteractionHand.OFF_HAND);
                    AttributeInstance attributeInstance3 = this.m_21051_(Attributes.f_22279_);
                    if (attributeInstance3 != null) {
                        attributeInstance3.m_22130_(SPEED_MODIFIER);
                        attributeInstance3.m_22125_(SPEED_MODIFIER);
                    }
                    if ((attributeInstance = this.m_21051_(Attributes.f_22278_)) != null) {
                        attributeInstance.m_22130_(KNOCKBACK_MODIFIER);
                        attributeInstance.m_22125_(KNOCKBACK_MODIFIER);
                    }
                }
            }
            if (this.guardCooldown > 0) {
                --this.guardCooldown;
            }
            if (this.ronPotionCooldown > 0) {
                --this.ronPotionCooldown;
            }
        }
        super.m_8107_();
        callbackInfo.cancel();
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
        return 40.0f * this.getAttackCooldownMultiplier();
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
        return 0.348f;
    }

    public float getUnitMaxHealth() {
        return 24.0f;
    }

    public ResourceCost getCost() {
        return RonGuardIllagerIntegrationValues.RESOURCE_COST;
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
        this.ronTargetGoal = new GuardIllagerSelectedTargetGoal((Mob)this, true, true);
        this.ronGarrisonGoal = new GarrisonGoal((Mob)this);
        this.ronAttackGoal = new MeleeAttackUnitGoal((Mob)this, false);
        this.ronAttackBuildingGoal = new MeleeAttackBuildingGoal((Mob)this);
        this.ronReturnResourcesGoal = new ReturnResourcesGoal((Mob)this);
    }

    public void resetBehaviours() {
        AttackerUnit.resetBehaviours((AttackerUnit)this);
    }

    public void setupEquipmentAndUpgradesServer() {
        ItemStack itemStack = GuardIllager.getIllagerShield();
        ItemStack itemStack2 = this.m_6844_(EquipmentSlot.OFFHAND);
        if (itemStack2.m_41619_() || !ItemStack.m_150942_((ItemStack)itemStack2, (ItemStack)itemStack)) {
            this.m_8061_(EquipmentSlot.OFFHAND, itemStack);
        }
    }

    public Set<BlockPos> getHighlightBps() {
        return this.ronHighlightBps;
    }

    public void setHighlightBps(Set<BlockPos> set) {
        this.ronHighlightBps = set;
    }

    private static class GuardIllagerSelectedTargetGoal
    extends SelectedTargetGoal<LivingEntity> {
        public GuardIllagerSelectedTargetGoal(Mob mob, boolean bl, boolean bl2) {
            super(mob, bl, bl2);
        }

        public LivingEntity getTarget() {
            LivingEntity livingEntity = super.getTarget();
            if (livingEntity != null) {
                return livingEntity;
            }
            if (this.f_26135_ instanceof GuardIllager) {
                GuardIllager guardIllager = (GuardIllager)this.f_26135_;
                if (guardIllager.m_5448_() != null) {
                    return guardIllager.m_5448_();
                }
                if (guardIllager.m_6117_() || guardIllager.isDrinkingPotion()) {
                    return guardIllager;
                }
            }
            return null;
        }
    }
}

