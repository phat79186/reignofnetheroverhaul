package com.codex.rongolemhealerintegration.ron;

import com.solegendary.reignofnether.api.ReignOfNetherRegistries;
import com.solegendary.reignofnether.resources.ResourceCost;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import java.util.List;

public final class RonStalwartDungeonsProductionItems {
    public static final ResourceLocation AWFUL_GHAST_ID = new ResourceLocation("ron_golem_healer_integration", "awful_ghast");
    public static final StalwartDungeonsUnitProd AWFUL_GHAST = new StalwartDungeonsUnitProd(
        "Awful Ghast",
        new ResourceLocation("stalwart_dungeons", "awful_ghast"),
        new ResourceLocation("ron_golem_healer_integration", "textures/icons/awful_ghast.png"),
        "entity.stalwart_dungeons.awful_ghast",
        "entity.stalwart_dungeons.awful_ghast",
        ResourceCost.Unit(25, 200, 100, 300, 4),
        List.of("Ranged Flying Unit", "Shoots massive fireballs", "Spawns smaller minions")
    );

    public static final ResourceLocation NETHER_KEEPER_ID = new ResourceLocation("ron_golem_healer_integration", "nether_keeper");
    public static final StalwartDungeonsUnitProd NETHER_KEEPER = new StalwartDungeonsUnitProd(
        "Nether Keeper",
        new ResourceLocation("stalwart_dungeons", "nether_keeper"),
        new ResourceLocation("ron_golem_healer_integration", "textures/icons/nether_keeper.png"),
        "entity.stalwart_dungeons.nether_keeper",
        "entity.stalwart_dungeons.nether_keeper",
        ResourceCost.Unit(30, 250, 50, 400, 5),
        List.of("Heavy Melee unit", "High health and armor", "Performs spin attacks")
    );

    public static final ResourceLocation GIDDY_BLAZE_ID = new ResourceLocation("ron_golem_healer_integration", "giddy_blaze");
    public static final StalwartDungeonsUnitProd GIDDY_BLAZE = new StalwartDungeonsUnitProd(
        "Giddy Blaze",
        new ResourceLocation("stalwart_dungeons", "giddy_blaze"),
        new ResourceLocation("ron_golem_healer_integration", "textures/icons/giddy_blaze.png"),
        "entity.stalwart_dungeons.giddy_blaze",
        "entity.stalwart_dungeons.giddy_blaze",
        ResourceCost.Unit(15, 100, 50, 150, 2),
        List.of("Fast Ranged Unit", "Shoots rapid fireballs", "Moves quickly")
    );

    public static final ResourceLocation INCOMPLETE_WITHER_ID = new ResourceLocation("ron_golem_healer_integration", "incomplete_wither");
    public static final StalwartDungeonsUnitProd INCOMPLETE_WITHER = new StalwartDungeonsUnitProd(
        "Incomplete Wither",
        new ResourceLocation("stalwart_dungeons", "incomplete_wither"),
        new ResourceLocation("ron_golem_healer_integration", "textures/icons/incomplete_wither.png"),
        "entity.stalwart_dungeons.incomplete_wither",
        "entity.stalwart_dungeons.incomplete_wither",
        ResourceCost.Unit(40, 300, 100, 500, 6),
        List.of("Boss Class Unit", "Shoots wither skulls", "Applies wither effect")
    );

    public static final ResourceLocation REINFORCED_BLAZE_ID = new ResourceLocation("ron_golem_healer_integration", "reinforced_blaze");
    public static final StalwartDungeonsUnitProd REINFORCED_BLAZE = new StalwartDungeonsUnitProd(
        "Reinforced Blaze",
        new ResourceLocation("stalwart_dungeons", "reinforced_blaze"),
        new ResourceLocation("ron_golem_healer_integration", "textures/icons/reinforced_blaze.png"),
        "entity.stalwart_dungeons.reinforced_blaze",
        "entity.stalwart_dungeons.reinforced_blaze",
        ResourceCost.Unit(20, 150, 50, 200, 3),
        List.of("Armored Ranged Unit", "High defense blaze", "Shoots burst fireballs")
    );

    public static final ResourceLocation PROPULK_ID = new ResourceLocation("ron_golem_healer_integration", "propulk");
    public static final StalwartDungeonsUnitProd PROPULK = new StalwartDungeonsUnitProd(
        "Propulk",
        new ResourceLocation("stalwart_dungeons", "propulk"),
        new ResourceLocation("ron_golem_healer_integration", "textures/icons/propulk.png"),
        "entity.stalwart_dungeons.propulk",
        "entity.stalwart_dungeons.propulk",
        ResourceCost.Unit(15, 120, 100, 100, 2),
        List.of("End Ranged Unit", "Fires levitating shulker bullets")
    );

    public static final ResourceLocation SHELTERER_ID = new ResourceLocation("ron_golem_healer_integration", "shelterer");
    public static final StalwartDungeonsUnitProd SHELTERER = new StalwartDungeonsUnitProd(
        "Shelterer",
        new ResourceLocation("stalwart_dungeons", "shelterer"),
        new ResourceLocation("ron_golem_healer_integration", "textures/icons/shelterer.png"),
        "entity.stalwart_dungeons.shelterer",
        "entity.stalwart_dungeons.shelterer",
        ResourceCost.Unit(25, 150, 50, 300, 4),
        List.of("Armored End Defender", "Shields itself from damage", "Heavy shield slam")
    );

    public static final ResourceLocation SHELTERER_WITHOUT_ARMOR_ID = new ResourceLocation("ron_golem_healer_integration", "shelterer_without_armor");
    public static final StalwartDungeonsUnitProd SHELTERER_WITHOUT_ARMOR = new StalwartDungeonsUnitProd(
        "Shelterer Without Armor",
        new ResourceLocation("stalwart_dungeons", "shelterer_without_armor"),
        new ResourceLocation("ron_golem_healer_integration", "textures/icons/shelterer_without_armor.png"),
        "entity.stalwart_dungeons.shelterer_without_armor",
        "entity.stalwart_dungeons.shelterer_without_armor",
        ResourceCost.Unit(15, 100, 50, 150, 2),
        List.of("Unarmored End Mob", "Fast melee unit")
    );

    public static final ResourceLocation SHULKER_CANNON_ID = new ResourceLocation("ron_golem_healer_integration", "shulker_cannon");
    public static final StalwartDungeonsUnitProd SHULKER_CANNON = new StalwartDungeonsUnitProd(
        "Shulker Cannon",
        new ResourceLocation("stalwart_dungeons", "shulker_cannon"),
        new ResourceLocation("ron_golem_healer_integration", "textures/icons/shulker_cannon.png"),
        "entity.stalwart_dungeons.shulker_cannon",
        "entity.stalwart_dungeons.shulker_cannon",
        ResourceCost.Unit(20, 150, 150, 250, 3),
        List.of("End Artillery Unit", "Stationary shulker bullet turret")
    );

    private RonStalwartDungeonsProductionItems() {
    }

    public static void register() {
        registerItem(AWFUL_GHAST_ID, AWFUL_GHAST);
        registerItem(NETHER_KEEPER_ID, NETHER_KEEPER);
        registerItem(GIDDY_BLAZE_ID, GIDDY_BLAZE);
        registerItem(INCOMPLETE_WITHER_ID, INCOMPLETE_WITHER);
        registerItem(REINFORCED_BLAZE_ID, REINFORCED_BLAZE);
        registerItem(PROPULK_ID, PROPULK);
        registerItem(SHELTERER_ID, SHELTERER);
        registerItem(SHELTERER_WITHOUT_ARMOR_ID, SHELTERER_WITHOUT_ARMOR);
        registerItem(SHULKER_CANNON_ID, SHULKER_CANNON);
    }

    private static void registerItem(ResourceLocation id, StalwartDungeonsUnitProd item) {
        if (!ReignOfNetherRegistries.PRODUCTION_ITEM.m_7804_(id)) {
            Registry.m_122965_((Registry)ReignOfNetherRegistries.PRODUCTION_ITEM, id, item);
        }
    }
}
