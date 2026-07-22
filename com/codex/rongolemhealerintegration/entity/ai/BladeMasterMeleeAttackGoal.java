package com.codex.rongolemhealerintegration.entity.ai;

import com.solegendary.reignofnether.unit.goals.MeleeAttackUnitGoal;
import mods.flammpfeil.slashblade.capability.slashblade.ISlashBladeState;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.registry.ComboStateRegistry;
import mods.flammpfeil.slashblade.registry.combo.ComboState;
import mods.flammpfeil.slashblade.util.InputCommand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.IForgeRegistry;

public class BladeMasterMeleeAttackGoal extends MeleeAttackUnitGoal {
    private final Mob myMob;

    public BladeMasterMeleeAttackGoal(Mob mob, boolean bl) {
        super(mob, bl);
        this.myMob = mob;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity livingEntity, double d) {
        double d2 = this.getAttackReachSqr(livingEntity);
        if (this.getTicksUntilNextAttack() <= 0 && d <= d2) {
            this.ticksUntilNextAttack = this.m_183277_(this.getAttackInterval());

            ItemStack mainHandStack = this.myMob.m_6844_(EquipmentSlot.MAINHAND);
            if (!mainHandStack.m_41619_() && mainHandStack.m_41720_() instanceof ItemSlashBlade) {
                ((ICapabilityProvider) (Object) mainHandStack).getCapability(ItemSlashBlade.BLADESTATE).ifPresent(iSlashBladeState -> {
                    ((ICapabilityProvider) this.myMob).getCapability(ItemSlashBlade.INPUT_STATE).ifPresent(iInputState -> {
                        iInputState.getCommands().add(InputCommand.R_CLICK);
                    });
                    if (!((IForgeRegistry) ComboStateRegistry.REGISTRY.get()).containsKey(iSlashBladeState.getComboSeq()) || !((IForgeRegistry) ComboStateRegistry.REGISTRY.get()).containsKey(iSlashBladeState.getComboRoot())) {
                        iSlashBladeState.setComboRoot(ComboStateRegistry.STANDBY.getId());
                        iSlashBladeState.updateComboSeq((LivingEntity) this.myMob, ComboStateRegistry.NONE.getId());
                    }
                    this.progressCombo(iSlashBladeState, (LivingEntity) this.myMob, false);
                    ((ICapabilityProvider) this.myMob).getCapability(ItemSlashBlade.INPUT_STATE).ifPresent(iInputState -> {
                        iInputState.getCommands().remove(InputCommand.R_CLICK);
                    });
                });
            }

            this.myMob.m_6674_(InteractionHand.MAIN_HAND);
            this.myMob.m_7327_((Entity) livingEntity);

            if (!mainHandStack.m_41619_()) {
                int fireAspectLvl = EnchantmentHelper.m_44843_(Enchantments.f_44983_, mainHandStack);
                if (fireAspectLvl > 0) {
                    livingEntity.m_20254_(fireAspectLvl * 4);
                }
            }
        }
    }

    public ResourceLocation progressCombo(ISlashBladeState iSlashBladeState, LivingEntity livingEntity, boolean bl) {
        ResourceLocation resourceLocation = iSlashBladeState.resolvCurrentComboState(livingEntity);
        ComboState comboState = (ComboState) ((IForgeRegistry) ComboStateRegistry.REGISTRY.get()).getValue(resourceLocation);
        if (comboState != null) {
            ResourceLocation resourceLocation2 = comboState.getNext(livingEntity);
            if (!resourceLocation2.equals((Object) ComboStateRegistry.NONE.getId()) && resourceLocation2.equals((Object) resourceLocation)) {
                return ComboStateRegistry.NONE.getId();
            }
            ComboState comboState2 = (ComboState) ((IForgeRegistry) ComboStateRegistry.REGISTRY.get()).getValue(iSlashBladeState.getComboRoot());
            if (comboState2 != null) {
                ResourceLocation resourceLocation3;
                ResourceLocation resourceLocation4 = comboState2.getNext(livingEntity);
                ComboState comboState3 = (ComboState) ((IForgeRegistry) ComboStateRegistry.REGISTRY.get()).getValue(resourceLocation2);
                ComboState comboState4 = (ComboState) ((IForgeRegistry) ComboStateRegistry.REGISTRY.get()).getValue(resourceLocation4);
                ResourceLocation resourceLocation5 = resourceLocation3 = comboState3.getPriority() <= comboState4.getPriority() ? resourceLocation2 : resourceLocation4;
                if (!bl) {
                    iSlashBladeState.updateComboSeq(livingEntity, resourceLocation3);
                }
                return resourceLocation3;
            }
            return ComboStateRegistry.NONE.getId();
        }
        return ComboStateRegistry.NONE.getId();
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.myMob.m_20205_() * 2.0f * this.myMob.m_20205_() * 2.0f + target.m_20205_() + 5.0f;
    }
}
