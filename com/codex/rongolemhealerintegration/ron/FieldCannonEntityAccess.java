package com.codex.rongolemhealerintegration.ron;

public interface FieldCannonEntityAccess {
    void ron$setOwnerName(String name);
    String ron$getOwnerName();
    void ron$setReloadCooldown(int ticks);
    int ron$getReloadCooldown();
    void ron$setNextShotDamage(float damage);
    float ron$getNextShotDamage();
    void ron$setTargetBlockPos(net.minecraft.core.BlockPos pos);
    net.minecraft.core.BlockPos ron$getTargetBlockPos();
}
