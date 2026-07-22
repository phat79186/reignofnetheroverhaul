package com.codex.rongolemhealerintegration.client;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.other.ThirdPersonManager;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.event.common.GunReloadEvent;
import com.tacz.guns.api.event.common.GunShootEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.resource.GunDisplayInstance;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.pojo.data.gun.GunReloadData;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = "ron_golem_healer_integration", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PillagerGunAnimationHelper {
    public static final Map<UUID, Long> reloadStartTimes = new ConcurrentHashMap<>();
    public static final Map<UUID, Long> reloadEndTimes = new ConcurrentHashMap<>();
    public static final Map<UUID, Long> shootTimes = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void onGunReload(GunReloadEvent event) {
        if (event.getLogicalSide().isClient() && event.getEntity() != null) {
            LivingEntity entity = event.getEntity();
            UUID uuid = entity.m_20148_();
            long now = System.currentTimeMillis();
            reloadStartTimes.put(uuid, now);

            long duration = 3000; // Default fallback 3 seconds
            try {
                ItemStack itemStack = event.getGunItemStack();
                if (itemStack != null) {
                    IGun iGun = IGun.getIGunOrNull(itemStack);
                    if (iGun != null) {
                        Optional<CommonGunIndex> optional = TimelessAPI.getCommonGunIndex(iGun.getGunId(itemStack));
                        if (optional.isPresent()) {
                            GunReloadData reloadData = optional.get().getGunData().getReloadData();
                            if (reloadData != null && reloadData.getCooldown() != null) {
                                float cooldown = reloadData.getCooldown().getEmptyTime();
                                if (cooldown <= 0.0f) {
                                    cooldown = reloadData.getCooldown().getTacticalTime();
                                }
                                if (cooldown > 0.0f) {
                                    duration = (long) ((cooldown + 0.75f) * 1000.0f);
                                }
                            }
                        }
                    }
                }
            } catch (Throwable ignored) {}
            reloadEndTimes.put(uuid, now + duration);
        }
    }

    @SubscribeEvent
    public static void onGunShoot(GunShootEvent event) {
        if (event.getLogicalSide().isClient() && event.getShooter() != null) {
            shootTimes.put(event.getShooter().m_20148_(), System.currentTimeMillis());
        }
    }

    public static void applyGunAnimations(LivingEntity entity, ModelPart rightArm, ModelPart leftArm, ModelPart body, ModelPart head, float limbSwing, float limbSwingAmount, float ageInTicks) {
        ItemStack mainHandItem = entity.m_21205_();
        if (mainHandItem == null || mainHandItem.m_41619_() || !"tacz".equals(ForgeRegistries.ITEMS.getKey(mainHandItem.m_41720_()).m_135827_())) {
            return;
        }

        UUID uuid = entity.m_20148_();
        long now = System.currentTimeMillis();

        // Reset positions to default bind pose to prevent offset accumulation
        rightArm.f_104200_ = -5.0f; // x
        rightArm.f_104201_ = 2.0f;  // y
        rightArm.f_104202_ = 0.0f;  // z
        
        leftArm.f_104200_ = 5.0f;
        leftArm.f_104201_ = 2.0f;
        leftArm.f_104202_ = 0.0f;
        
        body.f_104200_ = 0.0f;
        body.f_104201_ = 0.0f;
        body.f_104202_ = 0.0f;
        
        body.f_104203_ = 0.0f;

        // 1. Determine base pose (Hold vs Aim)
        String animName = "rifle";
        Optional<GunDisplayInstance> displayOpt = TimelessAPI.getGunDisplay(mainHandItem);
        if (displayOpt.isPresent()) {
            animName = displayOpt.get().getThirdPersonAnimation();
        }

        // Check if entity is aiming (aggressive or aiming progress > 0)
        boolean isAiming = false;
        if (entity instanceof net.minecraft.world.entity.Mob) {
            isAiming = ((net.minecraft.world.entity.Mob) entity).m_5912_();
        }
        IGunOperator operator = IGunOperator.fromLivingEntity(entity);
        float aimingProgress = 0.0f;
        if (operator != null) {
            aimingProgress = operator.getSynAimingProgress();
            if (aimingProgress > 0.1f) {
                isAiming = true;
            }
        }

        // Apply base pose from TACZ ThirdPersonManager
        if (isAiming) {
            float progress = aimingProgress > 0.0f ? aimingProgress : 1.0f;
            ThirdPersonManager.getAnimation(animName).animateGunAim(entity, rightArm, leftArm, body, head, progress);
        } else {
            ThirdPersonManager.getAnimation(animName).animateGunHold(entity, rightArm, leftArm, body, head);
        }

        // 2. Additive walk bobbing
        if (limbSwingAmount > 0.05f) {
            float bobY = Mth.m_14089_(limbSwing * 2.0f) * 0.05f * limbSwingAmount;
            float bobX = Mth.m_14031_(limbSwing) * 0.04f * limbSwingAmount;
            rightArm.f_104203_ += bobY; // pitch bobbing
            rightArm.f_104204_ += bobX; // yaw sway
            leftArm.f_104203_ += bobY;
            leftArm.f_104204_ += bobX;
        }

        // 3. Additive reload animation
        Long reloadStart = reloadStartTimes.get(uuid);
        Long reloadEnd = reloadEndTimes.get(uuid);
        boolean isReloading = reloadStart != null && reloadEnd != null && now < reloadEnd;

        if (isReloading) {
            long timeReloading = now - reloadStart;
            // Loop reload animation cycle (each load cycle is 800ms)
            float cycle = (float) (timeReloading % 800) / 800.0f;
            // Simulate hand moving to pocket and inserting shell
            float armMove = Mth.m_14031_(cycle * (float) Math.PI * 2.0f);
            
            // Move left hand away from barrel to simulate reload
            leftArm.f_104203_ += 0.4f + armMove * 0.3f;
            leftArm.f_104204_ -= 0.2f + armMove * 0.1f;
            leftArm.f_104202_ += armMove * 1.5f;
        }

        // 4. Additive bolting animation (Lever-action cycle / bolt action)
        boolean isBolting = false;
        if (operator != null) {
            isBolting = operator.getSynIsBolting();
        }

        if (isBolting && !isReloading) {
            float boltCycle = Mth.m_14031_((float) (now % 800) / 800.0f * (float) Math.PI * 2.0f);
            // Move right arm down and slightly forward to cycle the lever/bolt
            rightArm.f_104203_ += 0.25f + boltCycle * 0.15f;
            rightArm.f_104204_ -= 0.1f + boltCycle * 0.05f;
        }

        // 5. Additive shoot recoil animation
        Long lastShoot = shootTimes.get(uuid);
        if (lastShoot != null && now - lastShoot < 200) {
            long timeSinceShoot = now - lastShoot;
            float t = timeSinceShoot / 200.0f;
            float recoilFactor = 0.0f;
            if (t < 0.15f) {
                recoilFactor = t / 0.15f; // Rapid rise
            } else {
                recoilFactor = (1.0f - t) / 0.85f; // Slower return
            }

            // Apply recoil rotation and translation
            float pitchKick = recoilFactor * 0.2f;
            float pushBack = recoilFactor * 2.5f;

            rightArm.f_104203_ -= pitchKick; // Kick up
            leftArm.f_104203_ -= pitchKick;
            rightArm.f_104202_ += pushBack; // Kick back (Z)
            leftArm.f_104202_ += pushBack;

            body.f_104203_ -= recoilFactor * 0.04f; // Body recoil
        }
    }
}
