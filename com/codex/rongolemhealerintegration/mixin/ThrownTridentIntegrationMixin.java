package com.codex.rongolemhealerintegration.mixin;

import com.solegendary.reignofnether.unit.interfaces.AttackerUnit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {ThrownTrident.class}, priority = 1100, remap = false)
public abstract class ThrownTridentIntegrationMixin {
    @Shadow
    private ItemStack f_37555_; // Shadows tridentItem
    @Shadow
    private boolean f_37556_;   // Shadows dealtDamage

    /**
     * Intercepts trident entity hits. If the trident was thrown by a Reign of Nether Unit
     * (which implements AttackerUnit), we cancel the default hit logic (including the
     * reignofnether:ThrownTridentMixin logic) to calculate correct stats-based damage
     * instead of a hardcoded 8.0f damage. We also check for null items/owners to prevent crashes.
     */
    @Inject(method = "m_5790_", at = @At("HEAD"), cancellable = true)
    protected void ronGolemHealerIntegration$onHitEntity(EntityHitResult pResult, CallbackInfo ci) {
        ThrownTrident self = (ThrownTrident) (Object) this;
        Entity shooter = self.m_19749_(); // getOwner()
        if (shooter instanceof AttackerUnit) {
            // Cancel the normal logic (so both vanilla and RoN's ThrownTridentMixin are bypassed!)
            ci.cancel();

            AttackerUnit attacker = (AttackerUnit) shooter;
            Entity hitEntity = pResult.m_82443_(); // getEntity()

            // Calculate actual damage from AttackerUnit stats
            float baseDamage = attacker.getUnitAttackDamage();

            // Impaling enchantment bonus
            if (hitEntity instanceof LivingEntity) {
                LivingEntity livingHit = (LivingEntity) hitEntity;
                if (this.f_37555_ != null) {
                    baseDamage += EnchantmentHelper.m_44833_(this.f_37555_, livingHit.m_6336_());
                }
            }

            // Create proper DamageSource matching the shooter and projectile
            DamageSource dmgSource = self.m_269291_().m_269525_((Entity) self, shooter);
            SoundEvent hitSound = SoundEvents.f_12514_;

            // Deal damage
            if (hitEntity.m_6469_(dmgSource, baseDamage)) {
                if (hitEntity.m_6095_() == EntityType.f_20566_) { // ENDER_DRAGON
                    return;
                }
                if (hitEntity instanceof LivingEntity) {
                    LivingEntity livingHit = (LivingEntity) hitEntity;
                    this.f_37556_ = true;
                    self.m_20256_(self.m_20184_().m_82542_(-0.01, -0.1, -0.01));
                    EnchantmentHelper.m_44823_(livingHit, shooter);
                    EnchantmentHelper.m_44896_((LivingEntity) shooter, livingHit);
                }
            }
            self.m_5496_(hitSound, 1.0f, 1.0f);
        }
    }
}
