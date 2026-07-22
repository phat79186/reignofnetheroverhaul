/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.min01.guardillagers.entity.GuardIllager
 *  com.solegendary.reignofnether.unit.modelling.models.VillagerUnitModel
 *  com.solegendary.reignofnether.unit.units.villagers.PillagerUnit
 *  com.tacz.guns.api.TimelessAPI
 *  com.tacz.guns.api.client.other.ThirdPersonManager
 *  com.tacz.guns.api.entity.IGunOperator
 *  com.tacz.guns.api.event.common.GunReloadEvent
 *  com.tacz.guns.api.event.common.GunShootEvent
 *  com.tacz.guns.api.item.IGun
 *  com.tacz.guns.client.resource.GunDisplayInstance
 *  com.tacz.guns.resource.index.CommonGunIndex
 *  com.tacz.guns.resource.pojo.data.gun.Bolt
 *  com.tacz.guns.resource.pojo.data.gun.GunData
 *  com.tacz.guns.resource.pojo.data.gun.GunReloadData
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.HumanoidArm
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.monster.AbstractIllager
 *  net.minecraft.world.entity.monster.Pillager
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.registries.ForgeRegistries
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.codex.rongolemhealerintegration.mixin;

import com.min01.guardillagers.entity.GuardIllager;
import com.solegendary.reignofnether.unit.modelling.models.VillagerUnitModel;
import com.solegendary.reignofnether.ability.PillagerGunEnchantBridge;
import com.solegendary.reignofnether.unit.units.villagers.PillagerUnit;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.other.ThirdPersonManager;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.event.common.GunReloadEvent;
import com.tacz.guns.api.event.common.GunShootEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.resource.GunDisplayInstance;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.pojo.data.gun.Bolt;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.resource.pojo.data.gun.GunReloadData;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={VillagerUnitModel.class}, remap=false)
public abstract class VillagerUnitModelMixin<T extends AbstractIllager>
extends HumanoidModel<T> {
    private static final Map<UUID, Long> client$reloadingMobs = new ConcurrentHashMap<UUID, Long>();
    private static final Map<UUID, Long> client$boltingMobs = new ConcurrentHashMap<UUID, Long>();
    private static boolean client$registeredEvents = false;

    protected VillagerUnitModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    private static synchronized void client$registerEvents() {
        if (client$registeredEvents) {
            return;
        }
        client$registeredEvents = true;
        MinecraftForge.EVENT_BUS.register(new Object(){

            @SubscribeEvent
            public void onGunReload(GunReloadEvent gunReloadEvent) {
                LivingEntity livingEntity;
                if (gunReloadEvent.getLogicalSide().isClient() && (livingEntity = gunReloadEvent.getEntity()) instanceof PillagerUnit) {
                    GunReloadData gunReloadData;
                    Optional optional;
                    float f = 3.0f;
                    ItemStack itemStack = gunReloadEvent.getGunItemStack();
                    if (livingEntity instanceof PillagerUnit && PillagerGunEnchantBridge.isSpringfieldPillager(livingEntity)) {
                        f = 5.0f;
                    } else if (itemStack != null && IGun.getIGunOrNull((ItemStack)itemStack) != null && (optional = TimelessAPI.getCommonGunIndex((ResourceLocation)IGun.getIGunOrNull((ItemStack)itemStack).getGunId(itemStack))).isPresent() && (gunReloadData = ((CommonGunIndex)optional.get()).getGunData().getReloadData()) != null && gunReloadData.getCooldown() != null && (f = gunReloadData.getCooldown().getEmptyTime()) <= 0.0f) {
                        f = gunReloadData.getCooldown().getTacticalTime();
                    }
                    if (f <= 0.0f) {
                        f = 5.0f;
                    }
                    long l = (long)((f + 0.75f) * 1000.0f);
                    client$reloadingMobs.put(livingEntity.m_20148_(), System.currentTimeMillis() + l);
                }
            }

            @SubscribeEvent
            public void onGunShoot(GunShootEvent gunShootEvent) {
                GunData gunData;
                Optional optional;
                ItemStack itemStack;
                LivingEntity livingEntity;
                if (gunShootEvent.getLogicalSide().isClient() && (livingEntity = gunShootEvent.getShooter()) instanceof PillagerUnit && (itemStack = gunShootEvent.getGunItemStack()) != null && IGun.getIGunOrNull((ItemStack)itemStack) != null && (optional = TimelessAPI.getCommonGunIndex((ResourceLocation)IGun.getIGunOrNull((ItemStack)itemStack).getGunId(itemStack))).isPresent() && (gunData = ((CommonGunIndex)optional.get()).getGunData()) != null && gunData.getBolt() == Bolt.MANUAL_ACTION) {
                    client$boltingMobs.put(livingEntity.m_20148_(), System.currentTimeMillis() + 1200L);
                }
            }
        });
    }

    @Inject(method={"setupAnim", "m_6973_"}, at={@At(value="TAIL")}, remap=false)
    private void onSetupAnim(T t, float f, float f2, float f3, float f4, float f5, CallbackInfo callbackInfo) {
        VillagerUnitModel villagerUnitModel;
        GuardIllager guardIllager;
        if (!client$registeredEvents) {
            VillagerUnitModelMixin.client$registerEvents();
        }
        if (t instanceof GuardIllager) {
            guardIllager = (GuardIllager)t;
            if (guardIllager.isGuardSelf()) {
                this.f_102812_.f_104207_ = true;
                this.f_102811_.f_104207_ = true;
                if (guardIllager.m_5737_() == HumanoidArm.LEFT) {
                    this.f_102811_.f_104203_ = this.f_102811_.f_104203_ * 0.5f - 0.9424778f;
                    this.f_102811_.f_104204_ = -0.5235988f;
                } else {
                    this.f_102812_.f_104203_ = this.f_102812_.f_104203_ * 0.5f - 0.9424778f;
                    this.f_102812_.f_104204_ = 0.5235988f;
                }
                villagerUnitModel = (VillagerUnitModel)(Object)this;
                if (villagerUnitModel.crossedArms != null) {
                    villagerUnitModel.crossedArms.f_104207_ = false;
                }
            }
            if (guardIllager.isDrinkingPotion()) {
                this.f_102812_.f_104207_ = true;
                this.f_102811_.f_104207_ = true;
                if (guardIllager.m_5737_() == HumanoidArm.LEFT) {
                    this.f_102811_.f_104203_ = -1.3962634f;
                    this.f_102811_.f_104204_ = -0.31415927f;
                    this.f_102811_.f_104205_ = -0.07853982f;
                } else {
                    this.f_102812_.f_104203_ = -1.3962634f;
                    this.f_102812_.f_104204_ = 0.31415927f;
                    this.f_102812_.f_104205_ = 0.07853982f;
                }
                villagerUnitModel = (VillagerUnitModel)(Object)this;
                if (villagerUnitModel.crossedArms != null) {
                    villagerUnitModel.crossedArms.f_104207_ = false;
                }
            }
            if (guardIllager.m_5912_()) {
                this.f_102812_.f_104207_ = true;
                this.f_102811_.f_104207_ = true;
                villagerUnitModel = (VillagerUnitModel)(Object)this;
                if (villagerUnitModel.crossedArms != null) {
                    villagerUnitModel.crossedArms.f_104207_ = false;
                }
            }
        }
        if (t instanceof PillagerUnit && t.m_21205_() != null && "tacz".equals(ForgeRegistries.ITEMS.getKey((net.minecraft.world.item.Item)t.m_21205_().m_41720_()).m_135827_())) {
            com.solegendary.reignofnether.unit.modelling.models.VillagerUnitModel villagerModel = (com.solegendary.reignofnether.unit.modelling.models.VillagerUnitModel)(Object)this;
            if (villagerModel.crossedArms != null) {
                villagerModel.crossedArms.f_104207_ = false;
            }
            this.f_102811_.f_104207_ = true;
            this.f_102812_.f_104207_ = true;
            com.codex.rongolemhealerintegration.client.PillagerGunAnimationHelper.applyGunAnimations(
                t, this.f_102811_, this.f_102812_, this.f_102810_, this.f_102808_, f, f2, f3
            );
        }
    }
}

