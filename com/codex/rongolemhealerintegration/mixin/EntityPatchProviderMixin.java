package com.codex.rongolemhealerintegration.mixin;

import com.codex.rongolemhealerintegration.RonGuardIllagerIntegrationValues;
import com.codex.rongolemhealerintegration.RonSlashIllagerIntegrationValues;
import com.codex.rongolemhealerintegration.entity.SimpleBladeMasterPatch;
import com.solegendary.reignofnether.registrars.EntityRegistrar;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.entitypatch.mob.EvokerPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.VindicatorPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.WitchPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.ZombiePatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.DrownedPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.SkeletonPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.StrayPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.WitherSkeletonPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.CreeperPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.PiglinPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.PiglinBrutePatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.ZombifiedPiglinPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.IronGolemPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.PillagerPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.RavagerPatch;
import yesman.epicfight.world.capabilities.provider.EntityPatchProvider;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;

@Mixin(value={EntityPatchProvider.class}, remap=false)
public abstract class EntityPatchProviderMixin {
    @Shadow
    private EntityPatch<?> capability;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void ronGolemHealerIntegration$onInit(Entity entity, CallbackInfo ci) {
        // Disable Epic Fight for Stalwart Dungeons mobs so they use their native animations
        if (entity.getClass().getName().startsWith("net.mcreator.stalwartdungeons.")) {
            this.capability = null;
        }
        // Disable Epic Fight for specific piglin units: HeadhunterUnit, hero units, and mod units
        if (entity instanceof com.solegendary.reignofnether.unit.units.piglins.HeadhunterUnit
            || entity instanceof com.solegendary.reignofnether.unit.units.piglins.PiglinMerchantUnit
            || entity instanceof com.solegendary.reignofnether.unit.units.piglins.WildfireUnit) {
            this.capability = null;
        }
        // Disable Epic Fight for ZombiePiglinUnit
        if (entity instanceof com.solegendary.reignofnether.unit.units.monsters.ZombiePiglinUnit) {
            this.capability = null;
        }
        // Disable Epic Fight for ThrownTrident projectiles
        if (entity instanceof net.minecraft.world.entity.projectile.ThrownTrident) {
            this.capability = null;
        }
    }

    @Inject(method={"registerEntityPatches"}, at={@At(value="TAIL")})
    private static void onRegisterEntityPatches(CallbackInfo callbackInfo) {
        try {
            EntityType<?> entityType;

            // --- Villager faction units ---
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.IRON_GOLEM_UNIT.get(), entity -> IronGolemPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.IRON_GOLEM_UNIT.get(), Armatures.IRON_GOLEM);

            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.VILLAGER_UNIT.get(), entity -> VindicatorPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.VILLAGER_UNIT.get(), Armatures.BIPED);

            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.MILITIA_UNIT.get(), entity -> VindicatorPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.MILITIA_UNIT.get(), Armatures.BIPED);

            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.ROYAL_GUARD_UNIT.get(), entity -> VindicatorPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.ROYAL_GUARD_UNIT.get(), Armatures.BIPED);

            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.PILLAGER_UNIT.get(), entity -> PillagerPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.PILLAGER_UNIT.get(), Armatures.BIPED);

            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.WINDCALLER_UNIT.get(), entity -> PillagerPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.WINDCALLER_UNIT.get(), Armatures.BIPED);

            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.ENCHANTER_UNIT.get(), entity -> VindicatorPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.ENCHANTER_UNIT.get(), Armatures.BIPED);

            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.RAVAGER_UNIT.get(), entity -> RavagerPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.RAVAGER_UNIT.get(), Armatures.RAVAGER);

            // --- Illager-like units ---
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.VINDICATOR_UNIT.get(), entity -> VindicatorPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.VINDICATOR_UNIT.get(), Armatures.BIPED);
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.WITCH_UNIT.get(), entity -> WitchPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.WITCH_UNIT.get(), Armatures.BIPED);
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.EVOKER_UNIT.get(), entity -> EvokerPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.EVOKER_UNIT.get(), Armatures.BIPED);

            // --- Zombie-like units ---
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.ZOMBIE_UNIT.get(), entity -> ZombiePatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.ZOMBIE_UNIT.get(), Armatures.BIPED);
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.HUSK_UNIT.get(), entity -> ZombiePatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.HUSK_UNIT.get(), Armatures.BIPED);
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.DROWNED_UNIT.get(), entity -> DrownedPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.DROWNED_UNIT.get(), Armatures.BIPED);

            // --- Skeleton-like units ---
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.SKELETON_UNIT.get(), entity -> SkeletonPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.SKELETON_UNIT.get(), Armatures.SKELETON);
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.STRAY_UNIT.get(), entity -> StrayPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.STRAY_UNIT.get(), Armatures.SKELETON);
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.BOGGED_UNIT.get(), entity -> SkeletonPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.BOGGED_UNIT.get(), Armatures.SKELETON);
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.WITHER_SKELETON_UNIT.get(), entity -> WitherSkeletonPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.WITHER_SKELETON_UNIT.get(), Armatures.SKELETON);

            // --- Creeper-like units ---
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.CREEPER_UNIT.get(), entity -> CreeperPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.CREEPER_UNIT.get(), Armatures.CREEPER);

            // --- Piglin faction humanoid units (excluding HeadhunterUnit, heroes, and mod units) ---
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.GRUNT_UNIT.get(), entity -> PiglinPatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.GRUNT_UNIT.get(), Armatures.PIGLIN);
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.BRUTE_UNIT.get(), entity -> PiglinBrutePatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.BRUTE_UNIT.get(), Armatures.PIGLIN);
            EntityPatchProvider.putCustomEntityPatch((EntityType)EntityRegistrar.MARAUDER_UNIT.get(), entity -> PiglinBrutePatch::new);
            Armatures.registerEntityTypeArmature((EntityType)EntityRegistrar.MARAUDER_UNIT.get(), Armatures.PIGLIN);
            // ZombiePiglinUnit disabled - uses Goal AI incompatible with ZombifiedPiglinPatch's Brain AI

            // --- Blade Master (SlashIllager mod) ---
            EntityType<?> entityType2 = RonSlashIllagerIntegrationValues.resolveSourceEntityType();
            if (entityType2 != null) {
                EntityPatchProvider.putCustomEntityPatch(entityType2, entity -> () -> new SimpleBladeMasterPatch());
                Armatures.registerEntityTypeArmature(entityType2, Armatures.BIPED);
            }
            // --- Guard Illager (GuardIllagers mod) ---
            if ((entityType = RonGuardIllagerIntegrationValues.resolveSourceEntityType()) != null) {
                EntityPatchProvider.putCustomEntityPatch(entityType, entity -> VindicatorPatch::new);
                Armatures.registerEntityTypeArmature(entityType, Armatures.BIPED);
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
