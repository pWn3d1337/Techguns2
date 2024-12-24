package techguns2.datagen;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

import com.google.common.collect.ImmutableMap;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import net.minecraftforge.registries.RegistryObject;
import techguns2.TGAmmo;
import techguns2.TGBlocks;
import techguns2.TGItems;
import techguns2.TGTags;
import techguns2.datagen.extension.ForgeTagExtensions;
import techguns2.datagen.extension.ShapedRecipeBuilderNbtExtension;
import techguns2.item.AmmoItem;
import techguns2.machine.ammo_press.AmmoPressTemplates;
import techguns2.machine.chemical_laboratory.ChemicalLaboratoryRecipe;
import techguns2.machine.reaction_chamber.LaserFocuses;
import techguns2.machine.reaction_chamber.ReactionRisks;
import techguns2.util.EmptyFluidTagCondition;
import techguns2.util.LavaRecipeCondition;

public final class ItemMetadatas
{
    public static final ItemMetadata<Item> IRON_MECHANICAL_PARTS;
    public static final ItemMetadata<Item> OBSIDIAN_STEEL_MECHANICAL_PARTS;
    public static final ItemMetadata<Item> CARBON_MECHANICAL_PARTS;

    public static final ItemMetadata<Item> IRON_RECEIVER;
    public static final ItemMetadata<Item> STEEL_RECEIVER;
    public static final ItemMetadata<Item> OBSIDIAN_STEEL_RECEIVER;
    public static final ItemMetadata<Item> CARBON_RECEIVER;
    public static final ItemMetadata<Item> TITANIUM_RECEIVER;

    public static final ItemMetadata<Item> WOOD_STOCK;
    public static final ItemMetadata<Item> PLASTIC_STOCK;
    public static final ItemMetadata<Item> CARBON_STOCK;

    public static final ItemMetadata<Item> STONE_BARREL;
    public static final ItemMetadata<Item> IRON_BARREL;
    public static final ItemMetadata<Item> OBSIDIAN_STEEL_BARREL;
    public static final ItemMetadata<Item> CARBON_BARREL;
    public static final ItemMetadata<Item> LASER_BARREL;
    public static final ItemMetadata<Item> GAUSS_RIFLE_BARREL;
    public static final ItemMetadata<Item> SHIELDED_TITANIUM_BARREL;

    public static final ItemMetadata<Item> OBSIDIAN_STEEL_MINING_DRILL_HEAD;
    public static final ItemMetadata<Item> CARBON_MINING_DRILL_HEAD;

    public static final ItemMetadata<Item> OBSIDIAN_STEEL_POWER_HAMMER_HEAD;
    public static final ItemMetadata<Item> CARBON_POWER_HAMMER_HEAD;

    public static final ItemMetadata<Item> OBSIDIAN_STEEL_CHAINSAW_BLADE;
    public static final ItemMetadata<Item> CARBON_CHAINSAW_BLADE;

    public static final ItemMetadata<Item> SMALL_STEEL_ORE_DRILL;
    public static final ItemMetadata<Item> SMALL_OBSIDIAN_STEEL_ORE_DRILL;
    public static final ItemMetadata<Item> SMALL_CARBON_ORE_DRILL;

    public static final ItemMetadata<Item> MEDIUM_STEEL_ORE_DRILL;
    public static final ItemMetadata<Item> MEDIUM_OBSIDIAN_STEEL_ORE_DRILL;
    public static final ItemMetadata<Item> MEDIUM_CARBON_ORE_DRILL;

    public static final ItemMetadata<Item> LARGE_STEEL_ORE_DRILL;
    public static final ItemMetadata<Item> LARGE_OBSIDIAN_STEEL_ORE_DRILL;
    public static final ItemMetadata<Item> LARGE_CARBON_ORE_DRILL;

    public static final ItemMetadata<Item> GLIDER_BACKPACK;
    public static final ItemMetadata<Item> GLIDER_WING;

    public static final ItemMetadata<Item> ANTI_GRAVITY_CORE;
    public static final ItemMetadata<Item> PLASMA_GENERATOR;

    public static final ItemMetadata<Item> STEAM_ARMOR_PLATE;
    public static final ItemMetadata<Item> POWER_ARMOR_PLATE;

    public static final ItemMetadata<Item> REACTION_CHAMBER_HEAT_RAY_FOCUS;
    public static final ItemMetadata<Item> REACTION_CHAMBER_UV_EMITTER;

    public static final ItemMetadata<Item> COPPER_NUGGET;
    public static final ItemMetadata<Item> COPPER_WIRE;
    public static final ItemMetadata<Item> COPPER_PLATE;
    public static final ItemMetadata<Item> RAW_TIN;
    public static final ItemMetadata<Item> TIN_INGOT;
    public static final ItemMetadata<Item> TIN_PLATE;
    public static final ItemMetadata<Item> BRONZE_INGOT;
    public static final ItemMetadata<Item> BRONZE_PLATE;
    public static final ItemMetadata<Item> RAW_LEAD;
    public static final ItemMetadata<Item> LEAD_INGOT;
    public static final ItemMetadata<Item> LEAD_NUGGET;
    public static final ItemMetadata<Item> LEAD_PLATE;
    public static final ItemMetadata<Item> IRON_PLATE;
    public static final ItemMetadata<Item> STEEL_INGOT;
    public static final ItemMetadata<Item> STEEL_NUGGET;
    public static final ItemMetadata<Item> STEEL_PLATE;
    public static final ItemMetadata<Item> GOLD_WIRE;
    public static final ItemMetadata<Item> OBSIDIAN_STEEL_INGOT;
    public static final ItemMetadata<Item> OBSIDIAN_STEEL_PLATE;
    public static final ItemMetadata<Item> CARBON_FIBERS;
    public static final ItemMetadata<Item> CARBON_PLATE;
    public static final ItemMetadata<Item> RAW_TITANIUM;
    public static final ItemMetadata<Item> RAW_TITANIUM_CHUNK;
    public static final ItemMetadata<Item> TITANIUM_INGOT;
    public static final ItemMetadata<Item> TITANIUM_PLATE;
    public static final ItemMetadata<Item> RAW_PLASTIC;
    public static final ItemMetadata<Item> PLASTIC_PLATE;
    public static final ItemMetadata<Item> RAW_RUBBER;
    public static final ItemMetadata<Item> RUBBER_PLATE;
    public static final ItemMetadata<Item> RAW_URANIUM;
    public static final ItemMetadata<Item> YELLOWCAKE;
    public static final ItemMetadata<Item> ENRICHED_URANIUM;
    public static final ItemMetadata<Item> QUARTZ_ROD;
    public static final ItemMetadata<Item> HEAVY_CLOTH;
    public static final ItemMetadata<Item> PROTECTIVE_FIBER;
    public static final ItemMetadata<Item> TREATED_LEATHER;
    public static final ItemMetadata<Item> BIOMASS;
    public static final ItemMetadata<Item> CIRCUIT_BOARD;
    public static final ItemMetadata<Item> ELITE_CIRCUIT_BOARD;
    public static final ItemMetadata<Item> COIL;
    public static final ItemMetadata<Item> CYBERNETIC_PARTS;
    public static final ItemMetadata<Item> ELECTRIC_ENGINE;
    public static final ItemMetadata<Item> LASER_FOCUS;
    public static final ItemMetadata<Item> PUMP_MECHANISM;
    public static final ItemMetadata<Item> RAD_EMITTER;
    public static final ItemMetadata<Item> SONIC_EMITTER;
    public static final ItemMetadata<Item> INFUSION_BAG;
    public static final ItemMetadata<Item> TGX;
    public static final ItemMetadata<Item> NUCLEAR_WARHEAD;

    public static final ItemMetadata<AmmoItem<TGAmmo.StoneBullets>> STONE_BULLETS;

    public static final ItemMetadata<AmmoItem<TGAmmo.PistolRounds>> PISTOL_ROUNDS;
    public static final ItemMetadata<AmmoItem<TGAmmo.PistolRounds>> INCENDIARY_PISTOL_ROUNDS;

    public static final ItemMetadata<AmmoItem<TGAmmo.ShotgunShells>> SHOTGUN_SHELLS;
    public static final ItemMetadata<AmmoItem<TGAmmo.ShotgunShells>> INCENDIARY_SHOTGUN_SHELLS;

    public static final ItemMetadata<AmmoItem<TGAmmo.RifleRounds>> RIFLE_ROUNDS;
    public static final ItemMetadata<AmmoItem<TGAmmo.RifleRounds>> INCENDIARY_RIFLE_ROUNDS;

    public static final ItemMetadata<AmmoItem<TGAmmo.RifleRoundClip<TGAmmo.RifleRounds>>> RIFLE_ROUND_CLIP;
    public static final ItemMetadata<AmmoItem<TGAmmo.RifleRoundClip<TGAmmo.RifleRounds>>> INCENDIARY_RIFLE_ROUND_CLIP;

    public static final ItemMetadata<AmmoItem<TGAmmo.SniperRounds>> SNIPER_ROUNDS;
    public static final ItemMetadata<AmmoItem<TGAmmo.SniperRounds>> INCENDIARY_SNIPER_ROUNDS;
    public static final ItemMetadata<AmmoItem<TGAmmo.SniperRounds>> EXPLOSIVE_SNIPER_ROUNDS;

    public static final ItemMetadata<AmmoItem<TGAmmo.Rocket>> ROCKET;
    public static final ItemMetadata<AmmoItem<TGAmmo.Rocket>> HIGH_VELOCITY_ROCKET;
    public static final ItemMetadata<AmmoItem<TGAmmo.Rocket>> NUCLEAR_ROCKET;

    public static final ItemMetadata<AmmoItem<TGAmmo.AdvancedRounds>> ADVANCED_ROUNDS;

    public static final ItemMetadata<AmmoItem<TGAmmo.NetherCharge>> NETHER_CHARGE;

    public static final ItemMetadata<AmmoItem<TGAmmo.GaussRifleSlugRound>> GAUSS_RIFLE_SLUGS;

    public static final ItemMetadata<AmmoItem<TGAmmo.PistolRoundMagazine<TGAmmo.PistolRounds>>> PISTOL_MAGAZINE;
    public static final ItemMetadata<AmmoItem<TGAmmo.PistolRoundMagazine<TGAmmo.PistolRounds>>> INCENDIARY_PISTOL_MAGAZINE;

    public static final ItemMetadata<AmmoItem<TGAmmo.SmgRoundMagazine<TGAmmo.PistolRounds>>> SMG_MAGAZINE;
    public static final ItemMetadata<AmmoItem<TGAmmo.SmgRoundMagazine<TGAmmo.PistolRounds>>> INCENDIARY_SMG_MAGAZINE;

    public static final ItemMetadata<AmmoItem<TGAmmo.AssaultRifleRoundMagazine<TGAmmo.RifleRounds>>> ASSAULT_RIFLE_MAGAZINE;
    public static final ItemMetadata<AmmoItem<TGAmmo.AssaultRifleRoundMagazine<TGAmmo.RifleRounds>>> INCENDIARY_ASSAULT_RIFLE_MAGAZINE;

    public static final ItemMetadata<AmmoItem<TGAmmo.LmgRoundMagazine<TGAmmo.RifleRounds>>> LMG_MAGAZINE;
    public static final ItemMetadata<AmmoItem<TGAmmo.LmgRoundMagazine<TGAmmo.RifleRounds>>> INCENDIARY_LMG_MAGAZINE;

    public static final ItemMetadata<AmmoItem<TGAmmo.MinigunRoundMagazine<TGAmmo.RifleRounds>>> MINIGUN_MAGAZINE;
    public static final ItemMetadata<AmmoItem<TGAmmo.MinigunRoundMagazine<TGAmmo.RifleRounds>>> INCENDIARY_MINIGUN_MAGAZINE;

    public static final ItemMetadata<AmmoItem<TGAmmo.As50RoundMagazine<TGAmmo.SniperRounds>>> AS50_MAGAZINE;
    public static final ItemMetadata<AmmoItem<TGAmmo.As50RoundMagazine<TGAmmo.SniperRounds>>> INCENDIARY_AS50_MAGAZINE;
    public static final ItemMetadata<AmmoItem<TGAmmo.As50RoundMagazine<TGAmmo.SniperRounds>>> EXPLOSIVE_AS50_MAGAZINE;

    public static final ItemMetadata<AmmoItem<TGAmmo.AdvancedRoundMagazine<TGAmmo.AdvancedRounds>>> ADVANCED_MAGAZINE;

    public static final ItemMetadata<AmmoItem<TGAmmo.Grenade40MM>> GRENADE_40MM;

    public static final ItemMetadata<Item> COMPRESSED_AIR_TANK;

    public static final ItemMetadata<Item> BIO_TANK;

    public static final ItemMetadata<Item> FUEL_TANK;

    public static final ItemMetadata<Item> REDSTONE_BATTERY;

    public static final ItemMetadata<Item> ENERGY_CELL;

    public static final ItemMetadata<Item> NUCLEAR_POWER_CELL;

    public static final ItemMetadata<Item> GAS_MASK_FILTER;

    public static final ItemMetadata<Item> EMPTY_PISTOL_MAGAZINE;
    public static final ItemMetadata<Item> EMPTY_SMG_MAGAZINE;
    public static final ItemMetadata<Item> EMPTY_ASSAULT_RIFLE_MAGAZINE;
    public static final ItemMetadata<Item> EMPTY_LMG_MAGAZINE;
    public static final ItemMetadata<Item> EMPTY_MINIGUN_MAGAZINE;
    public static final ItemMetadata<Item> EMPTY_AS50_MAGAZINE;
    public static final ItemMetadata<Item> EMPTY_ADVANCED_MAGAZINE;
    public static final ItemMetadata<Item> EMPTY_COMPRESSED_AIR_TANK;
    public static final ItemMetadata<Item> EMPTY_BIO_TANK;
    public static final ItemMetadata<Item> EMPTY_FUEL_TANK;
    public static final ItemMetadata<Item> DEPLETED_REDSTONE_BATTERY;
    public static final ItemMetadata<Item> DEPLETED_ENERGY_CELL;
    public static final ItemMetadata<Item> DEPLETED_NUCLEAR_POWER_CELL;

    public static final ItemMetadata<Item> STACK_UPGRADE;
    public static final ItemMetadata<Item> IRON_TURRET_ARMOR;
    public static final ItemMetadata<Item> STEEL_TURRET_ARMOR;
    public static final ItemMetadata<Item> OBSIDIAN_STEEL_TURRET_ARMOR;
    public static final ItemMetadata<Item> CARBON_TURRET_ARMOR;

    public static final ItemMetadata<Item> PROTECTION_1_ARMOR_UPGRADE;
    public static final ItemMetadata<Item> PROTECTION_2_ARMOR_UPGRADE;
    public static final ItemMetadata<Item> PROTECTION_3_ARMOR_UPGRADE;
    public static final ItemMetadata<Item> PROTECTION_4_ARMOR_UPGRADE;
    public static final ItemMetadata<Item> PROJECTILE_PROTECTION_1_ARMOR_UPGRADE;
    public static final ItemMetadata<Item> PROJECTILE_PROTECTION_2_ARMOR_UPGRADE;
    public static final ItemMetadata<Item> PROJECTILE_PROTECTION_3_ARMOR_UPGRADE;
    public static final ItemMetadata<Item> PROJECTILE_PROTECTION_4_ARMOR_UPGRADE;
    public static final ItemMetadata<Item> BLAST_PROTECTION_1_ARMOR_UPGRADE;
    public static final ItemMetadata<Item> BLAST_PROTECTION_2_ARMOR_UPGRADE;
    public static final ItemMetadata<Item> BLAST_PROTECTION_3_ARMOR_UPGRADE;
    public static final ItemMetadata<Item> BLAST_PROTECTION_4_ARMOR_UPGRADE;

    public static final ItemMetadata<Item> WORKING_GLOVES;
    public static final ItemMetadata<Item> GAS_MASK;
    public static final ItemMetadata<Item> GLIDER;
    public static final ItemMetadata<Item> JUMP_PACK;
    public static final ItemMetadata<Item> OXYGEN_TANKS;
    public static final ItemMetadata<Item> NIGHT_VISION_GOGGLES;
    public static final ItemMetadata<Item> JETPACK;
    public static final ItemMetadata<Item> OXYGEN_MASK;
    public static final ItemMetadata<Item> TACTICAL_MASK;
    public static final ItemMetadata<Item> ANTI_GRAVITY_DEVICE;
    public static final ItemMetadata<Item> COMBAT_KNIFE;
    public static final ItemMetadata<Item> CROWBAR;
    public static final ItemMetadata<Item> RIOT_SHIELD;
    public static final ItemMetadata<Item> BALLISTIC_SHIELD;
    public static final ItemMetadata<Item> ADVANCED_SHIELD;
    public static final ItemMetadata<Item> STICK_GRENADE;
    public static final ItemMetadata<Item> FRAG_GRENADE;
    public static final ItemMetadata<Item> POWER_HAMMER;
    public static final ItemMetadata<Item> CHAINSAW;
    public static final ItemMetadata<Item> MINING_DRILL;
    public static final ItemMetadata<Item> RAD_AWAY;
    public static final ItemMetadata<Item> ANTI_RAD_PILLS;

    public static final ItemMetadata<Item> SOLDIER_HELMET;
    public static final ItemMetadata<Item> SOLDIER_CHESTPLATE;
    public static final ItemMetadata<Item> SOLDIER_LEGGINGS;
    public static final ItemMetadata<Item> SOLDIER_BOOTS;

    public static final ItemMetadata<Item> BANDIT_HELMET;
    public static final ItemMetadata<Item> BANDIT_CHESTPLATE;
    public static final ItemMetadata<Item> BANDIT_LEGGINGS;
    public static final ItemMetadata<Item> BANDIT_BOOTS;

    public static final ItemMetadata<Item> MINER_HELMET;
    public static final ItemMetadata<Item> MINER_CHESTPLATE;
    public static final ItemMetadata<Item> MINER_LEGGINGS;
    public static final ItemMetadata<Item> MINER_BOOTS;

    public static final ItemMetadata<Item> STEAM_HELMET;
    public static final ItemMetadata<Item> STEAM_CHESTPLATE;
    public static final ItemMetadata<Item> STEAM_LEGGINGS;
    public static final ItemMetadata<Item> STEAM_BOOTS;

    public static final ItemMetadata<Item> HAZMAT_HELMET;
    public static final ItemMetadata<Item> HAZMAT_CHESTPLATE;
    public static final ItemMetadata<Item> HAZMAT_LEGGINGS;
    public static final ItemMetadata<Item> HAZMAT_BOOTS;

    public static final ItemMetadata<Item> COMBAT_HELMET;
    public static final ItemMetadata<Item> COMBAT_CHESTPLATE;
    public static final ItemMetadata<Item> COMBAT_LEGGINGS;
    public static final ItemMetadata<Item> COMBAT_BOOTS;

    public static final ItemMetadata<Item> COMMADO_HELMET;
    public static final ItemMetadata<Item> COMMADO_CHESTPLATE;
    public static final ItemMetadata<Item> COMMADO_LEGGINGS;
    public static final ItemMetadata<Item> COMMADO_BOOTS;

    public static final ItemMetadata<Item> RANGER_VETERAN_HELMET;
    public static final ItemMetadata<Item> RANGER_VETERAN_CHESTPLATE;
    public static final ItemMetadata<Item> RANGER_VETERAN_LEGGINGS;
    public static final ItemMetadata<Item> RANGER_VETERAN_BOOTS;

    public static final ItemMetadata<Item> ADVANCED_COMBAT_HELMET;
    public static final ItemMetadata<Item> ADVANCED_COMBAT_CHESTPLATE;
    public static final ItemMetadata<Item> ADVANCED_COMBAT_LEGGINGS;
    public static final ItemMetadata<Item> ADVANCED_COMBAT_BOOTS;

    public static final ItemMetadata<Item> POWER_HELMET;
    public static final ItemMetadata<Item> POWER_CHESTPLATE;
    public static final ItemMetadata<Item> POWER_LEGGINGS;
    public static final ItemMetadata<Item> POWER_BOOTS;

    public static final ItemMetadata<Item> HEV_HELMET;
    public static final ItemMetadata<Item> HEV_CHESTPLATE;
    public static final ItemMetadata<Item> HEV_LEGGINGS;
    public static final ItemMetadata<Item> HEV_BOOTS;

    public static final ItemMetadata<Item> EXO_HELMET;
    public static final ItemMetadata<Item> EXO_CHESTPLATE;
    public static final ItemMetadata<Item> EXO_LEGGINGS;
    public static final ItemMetadata<Item> EXO_BOOTS;

    public static final ItemMetadata<Item> BERET;

    public static final ItemMetadata<Item> NETHER_COMBAT_HELMET;
    public static final ItemMetadata<Item> NETHER_COMBAT_CHESTPLATE;
    public static final ItemMetadata<Item> NETHER_COMBAT_LEGGINGS;
    public static final ItemMetadata<Item> NETHER_COMBAT_BOOTS;

    public static final ItemMetadata<Item> MK2_POWER_HELMET;
    public static final ItemMetadata<Item> MK2_POWER_CHESTPLATE;
    public static final ItemMetadata<Item> MK2_POWER_LEGGINGS;
    public static final ItemMetadata<Item> MK2_POWER_BOOTS;

    public static final ItemMetadata<Item> HAND_CANNON;
    public static final ItemMetadata<Item> DOUBLE_BARREL_SHOTGUN;
    public static final ItemMetadata<Item> REVOLVER;
    public static final ItemMetadata<Item> GOLDEN_REVOLVER;
    public static final ItemMetadata<Item> THOMPSON_SMG;
    public static final ItemMetadata<Item> AKM;
    public static final ItemMetadata<Item> BOLT_ACTION_RIFLE;
    public static final ItemMetadata<Item> M4;
    public static final ItemMetadata<Item> INFILTRATOR;
    public static final ItemMetadata<Item> PISTOL;
    public static final ItemMetadata<Item> COMBAT_SHOTGUN;
    public static final ItemMetadata<Item> MAC10;
    public static final ItemMetadata<Item> FLAMETHROWER;
    public static final ItemMetadata<Item> ROCKET_LAUNCHER;
    public static final ItemMetadata<Item> GRIM_REAPER;
    public static final ItemMetadata<Item> GRENADE_LAUNCHER;
    public static final ItemMetadata<Item> AUG;
    public static final ItemMetadata<Item> NETHER_BLASTER;
    public static final ItemMetadata<Item> BIO_GUN;
    public static final ItemMetadata<Item> TESLA_GUN;
    public static final ItemMetadata<Item> LIGHT_MACHINE_GUN;
    public static final ItemMetadata<Item> MINIGUN;
    public static final ItemMetadata<Item> AS50;
    public static final ItemMetadata<Item> VECTOR;
    public static final ItemMetadata<Item> SCAR;
    public static final ItemMetadata<Item> LASER_RIFLE;
    public static final ItemMetadata<Item> BLASTER_RIFLE;
    public static final ItemMetadata<Item> BLASTER_SHOTGUN;
    public static final ItemMetadata<Item> SONIC_RIFLE;
    public static final ItemMetadata<Item> PDW;
    public static final ItemMetadata<Item> PULSE_RIFLE;
    public static final ItemMetadata<Item> DEATOMIZER;
    public static final ItemMetadata<Item> ALIEN_BLASTER;
    public static final ItemMetadata<Item> NUCLEAR_DEATH_RAY;
    public static final ItemMetadata<Item> GAUSS_RIFLE;
    public static final ItemMetadata<Item> GUIDED_ROCKET_LAUNCHER;
    public static final ItemMetadata<Item> TFG;
    public static final ItemMetadata<Item> LASER_PISTOL;

    private static final Map<ResourceLocation, ItemMetadata<?>> METADATA_MAP;

    public static Collection<ItemMetadata<?>> getAll()
    {
        return METADATA_MAP.values();
    }

    private static <TItem extends Item> ItemMetadata<TItem> createAndPut(
            ImmutableMap.Builder<ResourceLocation, ItemMetadata<?>> builder, RegistryObject<TItem> item,
            BiConsumer<ResourceLocation, ItemMetadata<TItem>> metadataBuilder)
    {
        ResourceLocation id = item.getId();
        ItemMetadata<TItem> metadata = ItemMetadata.of(item.get(), id);
        metadataBuilder.accept(id, metadata);
        builder.put(id, metadata);
        return metadata;
    }

    static
    {
        ImmutableMap.Builder<ResourceLocation, ItemMetadata<?>> metadataMap = ImmutableMap.builder();
        
        IRON_MECHANICAL_PARTS = createAndPut(metadataMap, TGItems.IRON_MECHANICAL_PARTS, (id, metadata) -> metadata
                .withLangName("Mechanical Parts (Iron)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.IRON_MECHANICAL_PARTS,
                        builder -> builder
                            .define('I', Tags.Items.INGOTS_IRON)
                            .define('F', Items.FLINT)
                            .pattern(" I ")
                            .pattern("IFI")
                            .pattern(" I ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.INGOTS_IRON).build()))))
                .addRecipe(RecipeMetadata.metalPress(
                        id.withSuffix("_pressed"),
                        TGItems.IRON_MECHANICAL_PARTS,
                        builder -> builder
                            .addPrimary(ForgeTagExtensions.Items.PLATES_IRON)
                            .addSecondary(Items.FLINT)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .unlockedBy("block_placed", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(TGBlocks.METAL_PRESS.get())))));
        OBSIDIAN_STEEL_MECHANICAL_PARTS = createAndPut(metadataMap, TGItems.OBSIDIAN_STEEL_MECHANICAL_PARTS, (id, metadata) -> metadata
                .withLangName("Mechanical Parts (Obsidian Steel)")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.OBSIDIAN_STEEL_MECHANICAL_PARTS,
                        builder -> builder
                            .addPrimary(ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .addSecondary(Items.QUARTZ)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .unlockedBy("block_placed", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(TGBlocks.METAL_PRESS.get())))));
        CARBON_MECHANICAL_PARTS = createAndPut(metadataMap, TGItems.CARBON_MECHANICAL_PARTS, (id, metadata) -> metadata
                .withLangName("Mechanical Parts (Carbon)")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.CARBON_MECHANICAL_PARTS,
                        builder -> builder
                            .addPrimary(ForgeTagExtensions.Items.PLATES_CARBON)
                            .addSecondary(Items.BLAZE_ROD)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .unlockedBy("block_placed", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(TGBlocks.METAL_PRESS.get())))));
        
        IRON_RECEIVER = createAndPut(metadataMap, TGItems.IRON_RECEIVER, (id, metadata) -> metadata
                 .withLangName("Iron Receiver")
                 .addRecipe(RecipeMetadata.shaped(
                         id,
                         RecipeCategory.MISC,
                         TGItems.IRON_RECEIVER.get(),
                         builder -> builder
                             .define('F', Tags.Items.INGOTS_IRON)
                             .define('G', Tags.Items.NUGGETS_IRON)
                             .define('P', TGItems.IRON_MECHANICAL_PARTS.get())
                             .pattern("FFF")
                             .pattern(" PG")
                             .pattern("  G")
                             .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                     ItemPredicate.Builder.item().of(TGItems.IRON_MECHANICAL_PARTS.get()).build()))))
                 
        );
        STEEL_RECEIVER = createAndPut(metadataMap, TGItems.STEEL_RECEIVER, (id, metadata) -> metadata
                .withLangName("Steel Receiver")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.STEEL_RECEIVER.get(),
                        builder -> builder
                            .define('F', ForgeTagExtensions.Items.INGOTS_STEEL)
                            .define('G', ForgeTagExtensions.Items.NUGGETS_STEEL)
                            .define('P', TGItems.OBSIDIAN_STEEL_MECHANICAL_PARTS.get())
                            .pattern("FFF")
                            .pattern(" PG")
                            .pattern("  G")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.OBSIDIAN_STEEL_MECHANICAL_PARTS.get()).build())))));
        OBSIDIAN_STEEL_RECEIVER = createAndPut(metadataMap, TGItems.OBSIDIAN_STEEL_RECEIVER, (id, metadata) -> metadata
                .withLangName("Obsidian Steel Receiver").addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.OBSIDIAN_STEEL_RECEIVER.get(),
                        builder -> builder
                            .define('F', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .define('G', ForgeTagExtensions.Items.INGOTS_OBSIDIAN_STEEL)
                            .define('P', TGItems.CARBON_MECHANICAL_PARTS.get())
                            .pattern("FFF")
                            .pattern(" PG")
                            .pattern("  G")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.CARBON_MECHANICAL_PARTS.get()).build())))));
        CARBON_RECEIVER = createAndPut(metadataMap, TGItems.CARBON_RECEIVER, (id, metadata) -> metadata
                .withLangName("Carbon Receiver")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.CARBON_RECEIVER.get(),
                        builder -> builder
                            .define('F', ForgeTagExtensions.Items.PLATES_CARBON)
                            .define('G', ForgeTagExtensions.Items.INGOTS_OBSIDIAN_STEEL)
                            .define('P', TGItems.CARBON_MECHANICAL_PARTS.get())
                            .pattern("FFF")
                            .pattern(" PG")
                            .pattern("  G")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.CARBON_MECHANICAL_PARTS.get()).build())))));
        TITANIUM_RECEIVER = createAndPut(metadataMap, TGItems.TITANIUM_RECEIVER, (id, metadata) -> metadata
                .withLangName("Titanium Receiver")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.TITANIUM_RECEIVER.get(),
                        builder -> builder
                            .define('T', ForgeTagExtensions.Items.PLATES_TITANIUM)
                            .define('P', TGItems.CARBON_RECEIVER.get())
                            .define('C', TGItems.ELITE_CIRCUIT_BOARD.get())
                            .pattern("TTT")
                            .pattern(" PC")
                            .pattern("  T")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.CARBON_RECEIVER.get()).build())))));

        WOOD_STOCK = createAndPut(metadataMap, TGItems.WOOD_STOCK, (id, metadata) -> metadata
                .withLangName("Wood Stock")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.WOOD_STOCK.get(),
                        builder -> builder
                            .define('L', ItemTags.LOGS)
                            .pattern("LL")
                            .pattern(" L")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ItemTags.LOGS).build())))));
        PLASTIC_STOCK = createAndPut(metadataMap, TGItems.PLASTIC_STOCK, (id, metadata) -> metadata
                .withLangName("Plastic Stock")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.PLASTIC_STOCK.get(),
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .pattern("PPP")
                            .pattern("  P")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_PLASTIC).build())))));
        CARBON_STOCK = createAndPut(metadataMap, TGItems.CARBON_STOCK, (id, metadata) -> metadata
                .withLangName("Carbon Stock")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.CARBON_STOCK.get(),
                        builder -> builder
                            .define('C', ForgeTagExtensions.Items.PLATES_CARBON)
                            .define('P', ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .pattern("CCC")
                            .pattern(" PP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_CARBON).build())))));

        STONE_BARREL = createAndPut(metadataMap, TGItems.STONE_BARREL, (id, metadata) -> metadata
                .withLangName("Stone Barrel")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.STONE_BARREL.get(),
                        builder -> builder
                            .define('M', Tags.Items.STONE)
                            .pattern("MMM")
                            .pattern("   ")
                            .pattern("MMM")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.STONE).build())))));
        IRON_BARREL = createAndPut(metadataMap, TGItems.IRON_BARREL, (id, metadata) -> metadata
                .withLangName("Iron Barrel")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.IRON_BARREL.get(),
                        builder -> builder
                        .define('M', Tags.Items.INGOTS_IRON)
                        .pattern("MMM")
                        .pattern("   ")
                        .pattern("MMM")
                        .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                ItemPredicate.Builder.item().of(Tags.Items.INGOTS_IRON).build())))));
        OBSIDIAN_STEEL_BARREL = createAndPut(metadataMap, TGItems.OBSIDIAN_STEEL_BARREL, (id, metadata) -> metadata
                .withLangName("Obsidian Steel Barrel")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.OBSIDIAN_STEEL_BARREL.get(),
                        builder -> builder
                            .define('M', ForgeTagExtensions.Items.INGOTS_OBSIDIAN_STEEL)
                            .pattern("MMM")
                            .pattern("   ")
                            .pattern("MMM")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.INGOTS_OBSIDIAN_STEEL).build())))));
        CARBON_BARREL = createAndPut(metadataMap, TGItems.CARBON_BARREL, (id, metadata) -> metadata
                .withLangName("Carbon Barrel")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.CARBON_BARREL.get(),
                        builder -> builder
                            .define('M', ForgeTagExtensions.Items.PLATES_CARBON)
                            .pattern("MMM")
                            .pattern("   ")
                            .pattern("MMM")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_CARBON).build())))));
        LASER_BARREL = createAndPut(metadataMap, TGItems.LASER_BARREL, (id, metadata) -> metadata
                .withLangName("Laser Rifle Barrel")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.LASER_BARREL.get(),
                        builder -> builder
                            .define('M', TGTags.Items.ELECTRUM_INGOT)
                            .define('G', Tags.Items.GLASS)
                            .define('F', TGItems.LASER_FOCUS.get())
                            .pattern("MMM")
                            .pattern("GGF")
                            .pattern("MMM")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.LASER_FOCUS.get()).build())))));
        GAUSS_RIFLE_BARREL = createAndPut(metadataMap, TGItems.GAUSS_RIFLE_BARREL, (id, metadata) -> metadata
                .withLangName("Gauss Rifle Barrel")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.GAUSS_RIFLE_BARREL.get(),
                        builder -> builder
                            .define('T', ForgeTagExtensions.Items.PLATES_TITANIUM)
                            .define('M', ForgeTagExtensions.Items.WIRES_GOLD)
                            .define('C', TGItems.ELITE_CIRCUIT_BOARD.get())
                            .define('B', TGItems.CARBON_BARREL.get())
                            .pattern("TMM")
                            .pattern("BBC")
                            .pattern("TMM")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.CARBON_BARREL.get()).build())))));
        SHIELDED_TITANIUM_BARREL = createAndPut(metadataMap, TGItems.SHIELDED_TITANIUM_BARREL, (id, metadata) -> metadata
                .withLangName("Shielded Titanium Barrel")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.SHIELDED_TITANIUM_BARREL.get(),
                        builder -> builder
                            .define('T', ForgeTagExtensions.Items.PLATES_TITANIUM)
                            .define('L', ForgeTagExtensions.Items.PLATES_LEAD)
                            .define('C', TGItems.ELITE_CIRCUIT_BOARD.get())
                            .define('B', TGItems.CARBON_BARREL.get())
                            .pattern("TCT")
                            .pattern("LBL")
                            .pattern("TCT")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.CARBON_BARREL.get()).build())))));

        OBSIDIAN_STEEL_MINING_DRILL_HEAD = createAndPut(metadataMap, TGItems.OBSIDIAN_STEEL_MINING_DRILL_HEAD, (id, metadata) -> metadata
                .withLangName("Mining Drill Head (Obsidian Steel)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.OBSIDIAN_STEEL_MINING_DRILL_HEAD.get(),
                        builder -> builder
                            .define('M', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .pattern(" MM")
                            .pattern("MMM")
                            .pattern(" MM")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL).build()))))
                .addRecipe(RecipeMetadata.shaped(
                        id.withSuffix("_flipped"),
                        RecipeCategory.MISC,
                        TGItems.OBSIDIAN_STEEL_MINING_DRILL_HEAD.get(),
                        builder -> builder
                            .define('M', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .pattern("MM ")
                            .pattern("MMM")
                            .pattern("MM ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL).build())))));
        CARBON_MINING_DRILL_HEAD = createAndPut(metadataMap, TGItems.CARBON_MINING_DRILL_HEAD, (id, metadata) -> metadata
                .withLangName("Mining Drill Head (Carbon)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.CARBON_MINING_DRILL_HEAD.get(),
                        builder -> builder
                            .define('M', ForgeTagExtensions.Items.PLATES_CARBON)
                            .pattern(" MM")
                            .pattern("MMM")
                            .pattern(" MM")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_CARBON).build()))))
                .addRecipe(RecipeMetadata.shaped(
                        id.withSuffix("_flipped"),
                        RecipeCategory.MISC,
                        TGItems.CARBON_MINING_DRILL_HEAD.get(),
                        builder -> builder
                            .define('M', ForgeTagExtensions.Items.PLATES_CARBON)
                            .pattern("MM ")
                            .pattern("MMM")
                            .pattern("MM ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_CARBON).build())))));

        OBSIDIAN_STEEL_POWER_HAMMER_HEAD = createAndPut(metadataMap, TGItems.OBSIDIAN_STEEL_POWER_HAMMER_HEAD, (id, metadata) -> metadata
                .withLangName("Power Hammer Head (Obsidian Steel)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.OBSIDIAN_STEEL_POWER_HAMMER_HEAD.get(),
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .define('I', ForgeTagExtensions.Items.INGOTS_STEEL)
                            .pattern("P  ")
                            .pattern("PII")
                            .pattern("P  ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL).build())))));
        CARBON_POWER_HAMMER_HEAD = createAndPut(metadataMap, TGItems.CARBON_POWER_HAMMER_HEAD, (id, metadata) -> metadata
                .withLangName("Power Hammer Head (Carbon)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.CARBON_POWER_HAMMER_HEAD.get(),
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_CARBON)
                            .define('I', ForgeTagExtensions.Items.INGOTS_OBSIDIAN_STEEL)
                            .pattern("P  ")
                            .pattern("PII")
                            .pattern("P  ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_CARBON).build())))));

        OBSIDIAN_STEEL_CHAINSAW_BLADE = createAndPut(metadataMap, TGItems.OBSIDIAN_STEEL_CHAINSAW_BLADE, (id, metadata) -> metadata
                .withLangName("Chainsaw Blade (Steel)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.OBSIDIAN_STEEL_CHAINSAW_BLADE.get(),
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_STEEL)
                            .define('M', TGItems.OBSIDIAN_STEEL_MECHANICAL_PARTS.get())
                            .pattern(" PM")
                            .pattern("M  ")
                            .pattern(" PM")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.OBSIDIAN_STEEL_MECHANICAL_PARTS.get()).build())))));
        CARBON_CHAINSAW_BLADE = createAndPut(metadataMap, TGItems.CARBON_CHAINSAW_BLADE, (id, metadata) -> metadata
                .withLangName("Chainsaw Blade (Carbon)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.CARBON_CHAINSAW_BLADE.get(),
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .define('M', TGItems.CARBON_MECHANICAL_PARTS.get())
                            .pattern(" PM")
                            .pattern("M  ")
                            .pattern(" PM")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.CARBON_MECHANICAL_PARTS.get()).build())))));

        SMALL_STEEL_ORE_DRILL = createAndPut(metadataMap, TGItems.SMALL_STEEL_ORE_DRILL, (id, metadata) -> metadata
                .withLangName("Small Ore Drill (Steel)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.SMALL_STEEL_ORE_DRILL.get(),
                        builder -> builder
                            .define('M', ForgeTagExtensions.Items.INGOTS_STEEL)
                            .define('P', ForgeTagExtensions.Items.PLATES_STEEL)
                            .pattern("MPM")
                            .pattern("MPM")
                            .pattern(" M ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.INGOTS_STEEL).build())))));
        SMALL_OBSIDIAN_STEEL_ORE_DRILL = createAndPut(metadataMap, TGItems.SMALL_OBSIDIAN_STEEL_ORE_DRILL, (id, metadata) -> metadata
                .withLangName("Small Ore Drill (Obsidian Steel)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.SMALL_OBSIDIAN_STEEL_ORE_DRILL.get(),
                        builder -> builder
                            .define('M', ForgeTagExtensions.Items.INGOTS_OBSIDIAN_STEEL)
                            .define('P', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .pattern("MPM")
                            .pattern("MPM")
                            .pattern(" M ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.INGOTS_OBSIDIAN_STEEL).build())))));
        SMALL_CARBON_ORE_DRILL = createAndPut(metadataMap, TGItems.SMALL_CARBON_ORE_DRILL, (id, metadata) -> metadata
                .withLangName("Small Ore Drill (Carbon)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.SMALL_CARBON_ORE_DRILL.get(),
                        builder -> builder
                            .define('M', ForgeTagExtensions.Items.PLATES_CARBON)
                            .pattern("MMM")
                            .pattern("MMM")
                            .pattern(" M ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_CARBON).build())))));

        MEDIUM_STEEL_ORE_DRILL = createAndPut(metadataMap, TGItems.MEDIUM_STEEL_ORE_DRILL, (id, metadata) -> metadata
                .withLangName("Medium Ore Drill (Steel)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.MEDIUM_STEEL_ORE_DRILL.get(),
                        builder -> builder
                            .define('D', TGItems.SMALL_STEEL_ORE_DRILL.get())
                            .pattern("D D")
                            .pattern(" D ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.SMALL_STEEL_ORE_DRILL.get()).build())))));
        MEDIUM_OBSIDIAN_STEEL_ORE_DRILL = createAndPut(metadataMap, TGItems.MEDIUM_OBSIDIAN_STEEL_ORE_DRILL, (id, metadata) -> metadata
                .withLangName("Medium Ore Drill (Obsidian Steel)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.MEDIUM_OBSIDIAN_STEEL_ORE_DRILL.get(),
                        builder -> builder
                            .define('D', TGItems.SMALL_OBSIDIAN_STEEL_ORE_DRILL.get())
                            .pattern("D D")
                            .pattern(" D ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.SMALL_OBSIDIAN_STEEL_ORE_DRILL.get()).build())))));
        MEDIUM_CARBON_ORE_DRILL = createAndPut(metadataMap, TGItems.MEDIUM_CARBON_ORE_DRILL, (id, metadata) -> metadata
                .withLangName("Medium Ore Drill (Carbon)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.MEDIUM_CARBON_ORE_DRILL.get(),
                        builder -> builder
                            .define('D', TGItems.SMALL_CARBON_ORE_DRILL.get())
                            .pattern("D D")
                            .pattern(" D ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.SMALL_CARBON_ORE_DRILL.get()).build())))));

        LARGE_STEEL_ORE_DRILL = createAndPut(metadataMap, TGItems.LARGE_STEEL_ORE_DRILL, (id, metadata) -> metadata
                .withLangName("Large Ore Drill (Steel)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.LARGE_STEEL_ORE_DRILL.get(),
                        builder -> builder
                            .define('D', TGItems.MEDIUM_STEEL_ORE_DRILL.get())
                            .pattern("D D")
                            .pattern(" D ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.MEDIUM_STEEL_ORE_DRILL.get()).build())))));
        LARGE_OBSIDIAN_STEEL_ORE_DRILL = createAndPut(metadataMap, TGItems.LARGE_OBSIDIAN_STEEL_ORE_DRILL, (id, metadata) -> metadata
                .withLangName("Large Ore Drill (Obsidian Steel)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.LARGE_OBSIDIAN_STEEL_ORE_DRILL.get(),
                        builder -> builder
                            .define('D', TGItems.MEDIUM_OBSIDIAN_STEEL_ORE_DRILL.get())
                            .pattern("D D")
                            .pattern(" D ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.MEDIUM_OBSIDIAN_STEEL_ORE_DRILL.get()).build())))));
        LARGE_CARBON_ORE_DRILL = createAndPut(metadataMap, TGItems.LARGE_CARBON_ORE_DRILL, (id, metadata) -> metadata
                .withLangName("Large Ore Drill (Carbon)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.LARGE_CARBON_ORE_DRILL.get(),
                        builder -> builder
                            .define('D', TGItems.MEDIUM_CARBON_ORE_DRILL.get())
                            .pattern("D D")
                            .pattern(" D ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.MEDIUM_CARBON_ORE_DRILL.get()).build())))));

        GLIDER_BACKPACK = createAndPut(metadataMap, TGItems.GLIDER_BACKPACK, (id, metadata) -> metadata
                .withLangName("Glider Backpack")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.GLIDER_BACKPACK.get(),
                        builder -> builder
                            .define('C', TGItems.HEAVY_CLOTH.get())
                            .define('I', Tags.Items.INGOTS_IRON)
                            .pattern("CCC")
                            .pattern("ICI")
                            .pattern("CCC")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.HEAVY_CLOTH.get()).build())))));
        GLIDER_WING = createAndPut(metadataMap, TGItems.GLIDER_WING, (id, metadata) -> metadata
                .withLangName("Glider Wing")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.GLIDER_WING.get(),
                        builder -> builder
                            .define('C', TGItems.HEAVY_CLOTH.get())
                            .define('I', Tags.Items.INGOTS_IRON)
                            .pattern("III")
                            .pattern("CCC")
                            .pattern("CCC")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.HEAVY_CLOTH.get()).build())))));

        ANTI_GRAVITY_CORE = createAndPut(metadataMap, TGItems.ANTI_GRAVITY_CORE, (id, metadata) -> metadata
                .withLangName("Anti-Gravity Core")
                .addRecipe(RecipeMetadata.reactionChamber(
                        id,
                        TGItems.ANTI_GRAVITY_CORE,
                        builder -> builder
                            .addInputItem(Items.NETHER_STAR)
                            .addInputFluid(TGTags.Fluids.LIQUID_ENDER, 4000)
                            .withLaserFocus(LaserFocuses.HEAT_RAY)
                            .withInstability(1f, 2)
                            .withStartingIntensity(8)
                            .withTicksPerCycle(60)
                            .withCycles(10, 7)
                            .withRisk(ReactionRisks.SMALL_EXPLOSION)
                            .withEnergyDrainPerTick(500)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.NETHER_STAR).build())))));
        PLASMA_GENERATOR = createAndPut(metadataMap, TGItems.PLASMA_GENERATOR, (id, metadata) -> metadata
                .withLangName("Plasma Generator")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.PLASMA_GENERATOR.get(),
                        builder -> builder
                            .define('L', ForgeTagExtensions.Items.PLATES_LEAD)
                            .define('C', TGItems.COIL.get())
                            .define('U', TGItems.ENRICHED_URANIUM.get())
                            .define('G', TGItems.ANTI_GRAVITY_CORE.get())
                            .pattern("LCL")
                            .pattern("UGU")
                            .pattern("LCL")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.ANTI_GRAVITY_CORE.get()).build())))));

        STEAM_ARMOR_PLATE = createAndPut(metadataMap, TGItems.STEAM_ARMOR_PLATE, (id, metadata) -> metadata
                .withLangName("Steam Armor Plate")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.STEAM_ARMOR_PLATE,
                        builder -> builder
                            .addPrimary(ForgeTagExtensions.Items.PLATES_STEEL)
                            .addSecondary(ForgeTagExtensions.Items.PLATES_BRONZE)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .unlockedBy("block_placed", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(TGBlocks.METAL_PRESS.get())))));
        POWER_ARMOR_PLATE = createAndPut(metadataMap, TGItems.POWER_ARMOR_PLATE, (id, metadata) -> metadata
                .withLangName("Power Armor Plate")
                .addRecipe(RecipeMetadata.fabricator(
                        id,
                        TGItems.POWER_ARMOR_PLATE,
                        2,
                        builder -> builder
                            .addInput(ForgeTagExtensions.Items.INGOTS_TITANIUM, 2)
                            .addWire(TGItems.CIRCUIT_BOARD, 4)
                            .addRedstone(TGItems.CARBON_MECHANICAL_PARTS)
                            .addPlate(ForgeTagExtensions.Items.PLATES_CARBON, 4)
                            .withEnergyDrainPerTick(80)
                            .withProcessingTime(100)
                            .unlockedBy("block_placed", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(TGBlocks.FABRICATOR_CONTROLLER.get())))));

        REACTION_CHAMBER_HEAT_RAY_FOCUS = createAndPut(metadataMap, TGItems.REACTION_CHAMBER_HEAT_RAY_FOCUS, (id, metadata) -> metadata
                .withLangName("Reaction Chamber Heat Ray Focus")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.REACTION_CHAMBER_HEAT_RAY_FOCUS.get(),
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_STEEL)
                            .define('L', Items.REDSTONE_LAMP)
                            .define('C', TGItems.ELITE_CIRCUIT_BOARD.get())
                            .define('W', ForgeTagExtensions.Items.WIRES_GOLD)
                            .pattern("CWC")
                            .pattern("PLP")
                            .pattern("PLP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.ELITE_CIRCUIT_BOARD.get()).build())))));
        REACTION_CHAMBER_UV_EMITTER = createAndPut(metadataMap, TGItems.REACTION_CHAMBER_UV_EMITTER, (id, metadata) -> metadata
                .withLangName("Reaction Chamber UV Emitter")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.REACTION_CHAMBER_UV_EMITTER.get(),
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .define('U', TGItems.ENRICHED_URANIUM.get())
                            .define('C', TGItems.ELITE_CIRCUIT_BOARD.get())
                            .define('W', ForgeTagExtensions.Items.WIRES_GOLD)
                            .pattern("CWC")
                            .pattern("PUP")
                            .pattern(" P ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.ENRICHED_URANIUM.get()).build())))));

        COPPER_NUGGET = createAndPut(metadataMap, TGItems.COPPER_NUGGET, (id, metadata) -> metadata
                .withLangName("Copper Nugget")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.COPPER_NUGGET.get(),
                        9,
                        builder -> builder
                            .requires(Tags.Items.INGOTS_COPPER)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.INGOTS_COPPER).build())))));
        COPPER_WIRE = createAndPut(metadataMap, TGItems.COPPER_WIRE, (id, metadata) -> metadata
                .withLangName("Copper Wire")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.COPPER_WIRE.get(),
                        builder -> builder
                            .define('N', ForgeTagExtensions.Items.NUGGETS_COPPER)
                            .pattern(" NN")
                            .pattern(" N ")
                            .pattern("NN ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.NUGGETS_COPPER).build())))));
        COPPER_PLATE = createAndPut(metadataMap, TGItems.COPPER_PLATE, (id, metadata) -> metadata
                .withLangName("Copper Plate")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.COPPER_PLATE,
                        2,
                        builder -> builder
                            .addPrimary(Tags.Items.INGOTS_COPPER)
                            .addSecondary(Tags.Items.INGOTS_COPPER)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.INGOTS_COPPER).build())))));
        RAW_TIN = createAndPut(metadataMap, TGItems.RAW_TIN, (id, metadata) -> metadata
                .withLangName("Raw Tin")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.RAW_TIN.get(),
                        9,
                        builder -> builder
                            .requires(TGBlocks.RAW_TIN_BLOCK.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGBlocks.RAW_TIN_BLOCK.get()).build())))));
        TIN_INGOT = createAndPut(metadataMap, TGItems.TIN_INGOT, (id, metadata) -> metadata
                .withLangName("Tin Ingot")
                .addRecipe(RecipeMetadata.blasting(
                        id.withSuffix("_from_raw"),
                        Ingredient.of(TGItems.RAW_TIN.get()),
                        RecipeCategory.MISC,
                        TGItems.TIN_INGOT.get(),
                        0.5f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.RAW_TIN.get()).build()))))
                .addRecipe(RecipeMetadata.smelting(
                        id.withSuffix("_from_plate"),
                        Ingredient.of(TGItems.TIN_PLATE.get()),
                        RecipeCategory.MISC,
                        TGItems.TIN_INGOT.get(),
                        0.5f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.TIN_PLATE.get()).build()))))
                .addRecipe(RecipeMetadata.blasting(
                        id.withSuffix("_from_ore"),
                        Ingredient.of(TGBlocks.TIN_ORE.get()),
                        RecipeCategory.MISC,
                        TGItems.TIN_INGOT.get(),
                        0.5f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGBlocks.TIN_ORE.get()).build()))))
                .addRecipe(RecipeMetadata.blasting(
                        id.withSuffix("_from_deepslate_ore"),
                        Ingredient.of(TGBlocks.TIN_ORE.get()),
                        RecipeCategory.MISC,
                        TGItems.TIN_INGOT.get(),
                        0.5f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGBlocks.DEEPSLATE_TIN_ORE.get()).build())))));
        TIN_PLATE = createAndPut(metadataMap, TGItems.TIN_PLATE, (id, metadata) -> metadata
                .withLangName("Tin Plate")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.TIN_PLATE,
                        2,
                        builder -> builder
                            .addPrimary(ForgeTagExtensions.Items.INGOTS_TIN)
                            .addSecondary(ForgeTagExtensions.Items.INGOTS_TIN)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.INGOTS_TIN).build())))));
        BRONZE_INGOT = createAndPut(metadataMap, TGItems.BRONZE_INGOT, (id, metadata) -> metadata
                .withLangName("Bronze Ingot")
                .addRecipe(RecipeMetadata.smelting(
                        id.withSuffix("_from_plate"),
                        Ingredient.of(TGItems.BRONZE_PLATE.get()),
                        RecipeCategory.MISC,
                        TGItems.BRONZE_INGOT.get(),
                        0f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.BRONZE_PLATE.get()).build()))))
                .addRecipe(RecipeMetadata.alloyFurnace(
                        id.withSuffix("_alloy"),
                        TGItems.BRONZE_INGOT,
                        4,
                        builder -> builder
                            .addPrimary(Tags.Items.INGOTS_COPPER, 3)
                            .addSecondary(ForgeTagExtensions.Items.INGOTS_TIN, 1)
                            .withEnergyDrainPerTick(10)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.INGOTS_TIN).build())))));
        BRONZE_PLATE = createAndPut(metadataMap, TGItems.BRONZE_PLATE, (id, metadata) -> metadata
                .withLangName("Bronze Plate")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.BRONZE_PLATE,
                        2,
                        builder -> builder
                            .addPrimary(ForgeTagExtensions.Items.INGOTS_BRONZE)
                            .addSecondary(ForgeTagExtensions.Items.INGOTS_BRONZE)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.INGOTS_BRONZE).build())))));
        RAW_LEAD = createAndPut(metadataMap, TGItems.RAW_LEAD, (id, metadata) -> metadata
                .withLangName("Raw Lead")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.RAW_LEAD.get(),
                        9,
                        builder -> builder
                            .requires(TGBlocks.RAW_LEAD_BLOCK.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGBlocks.RAW_LEAD_BLOCK.get()).build())))));
        LEAD_INGOT = createAndPut(metadataMap, TGItems.LEAD_INGOT, (id, metadata) -> metadata
                .withLangName("Lead Ingot")
                .addRecipe(RecipeMetadata.shaped(
                        id.withSuffix("_from_nugget"),
                        RecipeCategory.MISC,
                        TGItems.LEAD_INGOT.get(),
                        builder -> builder
                            .define('N', TGItems.LEAD_NUGGET.get())
                            .pattern("NNN")
                            .pattern("NNN")
                            .pattern("NNN")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.LEAD_NUGGET.get()).build()))))
                .addRecipe(RecipeMetadata.blasting(
                        id.withSuffix("_from_raw"),
                        Ingredient.of(TGItems.RAW_LEAD.get()),
                        RecipeCategory.MISC,
                        TGItems.LEAD_INGOT.get(),
                        1f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.RAW_LEAD.get()).build()))))
                .addRecipe(RecipeMetadata.smelting(
                        id.withSuffix("_from_plate"),
                        Ingredient.of(TGItems.LEAD_PLATE.get()),
                        RecipeCategory.MISC,
                        TGItems.LEAD_INGOT.get(),
                        1f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.LEAD_PLATE.get()).build()))))
                .addRecipe(RecipeMetadata.blasting(
                        id.withSuffix("_from_ore"),
                        Ingredient.of(TGBlocks.LEAD_ORE.get()),
                        RecipeCategory.MISC,
                        TGItems.LEAD_INGOT.get(),
                        1f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGBlocks.LEAD_ORE.get()).build()))))
                .addRecipe(RecipeMetadata.blasting(
                        id.withSuffix("_from_deepslate_ore"),
                        Ingredient.of(TGBlocks.DEEPSLATE_LEAD_ORE.get()),
                        RecipeCategory.MISC,
                        TGItems.LEAD_INGOT.get(),
                        1f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGBlocks.DEEPSLATE_LEAD_ORE.get()).build())))));
        LEAD_NUGGET = createAndPut(metadataMap, TGItems.LEAD_NUGGET, (id, metadata) -> metadata
                .withLangName("Lead Nugget")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.LEAD_NUGGET.get(),
                        9,
                        builder -> builder
                            .requires(TGItems.LEAD_INGOT.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.LEAD_INGOT.get()).build())))));
        LEAD_PLATE = createAndPut(metadataMap, TGItems.LEAD_PLATE, (id, metadata) -> metadata
                .withLangName("Lead Plate")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.LEAD_PLATE,
                        2,
                        builder -> builder
                            .addPrimary(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addSecondary(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.INGOTS_LEAD).build())))));
        IRON_PLATE = createAndPut(metadataMap, TGItems.IRON_PLATE, (id, metadata) -> metadata
                .withLangName("Iron Plate")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.IRON_PLATE,
                        2,
                        builder -> builder
                            .addPrimary(Tags.Items.INGOTS_IRON)
                            .addSecondary(Tags.Items.INGOTS_IRON)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.INGOTS_IRON).build())))));
        STEEL_INGOT = createAndPut(metadataMap, TGItems.STEEL_INGOT, (id, metadata) -> metadata
                .withLangName("Steel Ingot")
                .addRecipe(RecipeMetadata.shaped(
                        id.withSuffix("_from_nugget"), 
                        RecipeCategory.MISC,
                        TGItems.STEEL_INGOT.get(),
                        builder -> builder
                            .define('N', TGItems.STEEL_NUGGET.get())
                            .pattern("NNN")
                            .pattern("NNN")
                            .pattern("NNN")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.STEEL_INGOT.get()).build()))))
                .addRecipe(RecipeMetadata.smelting(
                        id.withSuffix("_from_plate"),
                        Ingredient.of(TGItems.STEEL_PLATE.get()),
                        RecipeCategory.MISC,
                        TGItems.STEEL_INGOT.get(),
                        0f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.LEAD_PLATE.get()).build()))))
                .addRecipe(RecipeMetadata.alloyFurnace(
                        id.withSuffix("_alloy"),
                        TGItems.STEEL_INGOT,
                        4,
                        builder -> builder
                            .addPrimary(Tags.Items.INGOTS_IRON, 4)
                            .addSecondary(Items.COAL, 1)
                            .addSecondary(Items.CHARCOAL, 1)
                            .withEnergyDrainPerTick(10)
                            .withProcessingTime(800)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.INGOTS_IRON).build())))));
        STEEL_NUGGET = createAndPut(metadataMap, TGItems.STEEL_NUGGET, (id, metadata) -> metadata
                .withLangName("Steel Nugget")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.STEEL_NUGGET.get(),
                        9,
                        builder -> builder
                            .requires(TGItems.STEEL_INGOT.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.STEEL_INGOT.get()).build())))));
        STEEL_PLATE = createAndPut(metadataMap, TGItems.STEEL_PLATE, (id, metadata) -> metadata
                .withLangName("Steel Plate")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.STEEL_PLATE,
                        2,
                        builder -> builder
                            .addPrimary(ForgeTagExtensions.Items.INGOTS_STEEL)
                            .addSecondary(ForgeTagExtensions.Items.INGOTS_STEEL)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.INGOTS_STEEL).build())))));
        GOLD_WIRE = createAndPut(metadataMap, TGItems.GOLD_WIRE, (id, metadata) -> metadata
                .withLangName("Gold Wire")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.GOLD_WIRE.get(),
                        2,
                        builder -> builder
                            .define('N', Tags.Items.NUGGETS_GOLD)
                            .pattern(" NN")
                            .pattern(" N ")
                            .pattern("NN ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.NUGGETS_GOLD).build())))));
        OBSIDIAN_STEEL_INGOT = createAndPut(metadataMap, TGItems.OBSIDIAN_STEEL_INGOT, (id, metadata) -> metadata
                .withLangName("Obsidian Steel Ingot")
                .addRecipe(RecipeMetadata.smelting(
                        id.withSuffix("_from_plate"),
                        Ingredient.of(TGItems.OBSIDIAN_STEEL_PLATE.get()),
                        RecipeCategory.MISC,
                        TGItems.OBSIDIAN_STEEL_INGOT.get(),
                        0f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.OBSIDIAN_STEEL_PLATE.get()).build()))))
                .addRecipe(RecipeMetadata.alloyFurnace(
                        id.withSuffix("_alloy"), 
                        TGItems.OBSIDIAN_STEEL_INGOT,
                        builder -> builder
                            .addPrimary(ForgeTagExtensions.Items.INGOTS_STEEL)
                            .addSecondary(Items.OBSIDIAN)
                            .withEnergyDrainPerTick(10)
                            .withProcessingTime(200)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.INGOTS_STEEL).build())))));
        OBSIDIAN_STEEL_PLATE = createAndPut(metadataMap, TGItems.OBSIDIAN_STEEL_PLATE, (id, metadata) -> metadata
                .withLangName("Obsidian Steel Plate")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.OBSIDIAN_STEEL_PLATE,
                        2,
                        builder -> builder
                            .addPrimary(ForgeTagExtensions.Items.INGOTS_OBSIDIAN_STEEL)
                            .addSecondary(ForgeTagExtensions.Items.INGOTS_OBSIDIAN_STEEL)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.INGOTS_OBSIDIAN_STEEL).build())))));
        CARBON_FIBERS = createAndPut(metadataMap, TGItems.CARBON_FIBERS, (id, metadata) -> metadata
                .withLangName("Carbon Fibers")
                .addRecipe(RecipeMetadata.chemicalLaboratory(
                        id,
                        builder -> builder
                            .addFluidInput(FluidTags.LAVA, 1000)
                            .addPrimaryInput(Tags.Items.GEMS_DIAMOND)
                            .addSecondaryInput(Items.BLAZE_POWDER)
                            .withEnergyDrainPerTick(25)
                            .withProcessingTime(100)
                            .withItemResult(TGItems.CARBON_FIBERS, 2)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.GEMS_DIAMOND).build())))));
        CARBON_PLATE = createAndPut(metadataMap, TGItems.CARBON_PLATE, (id, metadata) -> metadata
                .withLangName("Carbon Plate")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.CARBON_PLATE,
                        2,
                        builder -> builder
                            .addPrimary(TGItems.CARBON_FIBERS)
                            .addSecondary(TGItems.CARBON_FIBERS)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.INGOTS_OBSIDIAN_STEEL).build())))));
        RAW_TITANIUM = createAndPut(metadataMap, TGItems.RAW_TITANIUM, (id, metadata) -> metadata
                .withLangName("Raw Titanium")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.RAW_TITANIUM.get(),
                        9,
                        builder -> builder
                            .requires(TGBlocks.RAW_TITANIUM_BLOCK.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGBlocks.RAW_TITANIUM_BLOCK.get()).build())))));
        RAW_TITANIUM_CHUNK = createAndPut(metadataMap, TGItems.RAW_TITANIUM_CHUNK, (id, metadata) -> metadata
                .withLangName("Raw Titanium Chunk")
                .addRecipe(RecipeMetadata.reactionChamber(
                        id.withSuffix("_from_chunk"),
                        TGItems.RAW_TITANIUM_CHUNK,
                        2,
                        builder -> builder
                            .addInputItem(TGItems.RAW_TITANIUM)
                            .addInputFluid(TGTags.Fluids.ACID, 3000)
                            .withLaserFocus(LaserFocuses.HEAT_RAY)
                            .withoutInstability()
                            .withStartingIntensity(5)
                            .withTicksPerCycle(60)
                            .withCycles(2, 1)
                            .withRisk(ReactionRisks.BREAK_ITEM)
                            .withEnergyDrainPerTick(25)
                            .withResult(Items.RAW_IRON)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.RAW_TITANIUM.get()).build()))))
                .addRecipe(RecipeMetadata.reactionChamber(
                        id.withSuffix("_from_ore"),
                        TGItems.RAW_TITANIUM,
                        2,
                        builder -> builder
                            .addInputItem(TGBlocks.TITANIUM_ORE)
                            .addInputFluid(TGTags.Fluids.ACID, 3000)
                            .withLaserFocus(LaserFocuses.HEAT_RAY)
                            .withoutInstability()
                            .withStartingIntensity(5)
                            .withTicksPerCycle(60)
                            .withCycles(2, 1)
                            .withRisk(ReactionRisks.BREAK_ITEM)
                            .withEnergyDrainPerTick(25)
                            .withResult(Blocks.IRON_ORE)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGBlocks.TITANIUM_ORE.get()).build()))))
                .addRecipe(RecipeMetadata.reactionChamber(
                        id.withSuffix("_from_deepslate_ore"),
                        TGItems.RAW_TITANIUM_CHUNK,
                        2,
                        builder -> builder
                            .addInputItem(TGBlocks.DEEPSLATE_TITANIUM_ORE)
                            .addInputFluid(TGTags.Fluids.ACID, 3000)
                            .withLaserFocus(LaserFocuses.HEAT_RAY)
                            .withoutInstability()
                            .withStartingIntensity(5)
                            .withTicksPerCycle(60)
                            .withCycles(2, 1)
                            .withRisk(ReactionRisks.BREAK_ITEM)
                            .withEnergyDrainPerTick(25)
                            .withResult(Blocks.DEEPSLATE_IRON_ORE)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGBlocks.DEEPSLATE_TITANIUM_ORE.get()).build())))));
        TITANIUM_INGOT = createAndPut(metadataMap, TGItems.TITANIUM_INGOT, (id, metadata) -> metadata
                .withLangName("Titanium Ingot")
                .addRecipe(RecipeMetadata.blasting(
                        id.withSuffix("_from_chunk"),
                        Ingredient.of(TGItems.RAW_TITANIUM_CHUNK.get()),
                        RecipeCategory.MISC,
                        TGItems.TITANIUM_INGOT.get(),
                        0f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.RAW_TITANIUM_CHUNK.get()).build()))))
                .addRecipe(RecipeMetadata.smelting(
                        id.withSuffix("_from_plate"),
                        Ingredient.of(TGItems.TITANIUM_PLATE.get()),
                        RecipeCategory.MISC,
                        TGItems.TITANIUM_INGOT.get(),
                        0f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.TITANIUM_PLATE.get()).build())))));
        TITANIUM_PLATE = createAndPut(metadataMap, TGItems.TITANIUM_PLATE, (id, metadata) -> metadata
                .withLangName("Titanium Plate")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.TITANIUM_PLATE,
                        2,
                        builder -> builder
                            .addPrimary(ForgeTagExtensions.Items.INGOTS_TITANIUM)
                            .addSecondary(ForgeTagExtensions.Items.INGOTS_TITANIUM)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.INGOTS_TITANIUM).build())))));
        RAW_PLASTIC = createAndPut(metadataMap, TGItems.RAW_PLASTIC, (id, metadata) -> metadata
                .withLangName("Raw Plastic")
                .addRecipe(RecipeMetadata.fromConditional(
                        id,
                        builder -> builder
                            .addCondition(new EmptyFluidTagCondition(ForgeTagExtensions.Fluids.OIL.location()))
                            .addRecipe(x -> new ChemicalLaboratoryRecipe.Builder()
                                    .addFluidInput(Fluids.WATER, 1000)
                                    .addPrimaryInput(TGItems.RAW_RUBBER)
                                    .addSecondaryInput(Items.COAL)
                                    .withEnergyDrainPerTick(25)
                                    .withProcessingTime(100)
                                    .withItemResult(TGItems.RAW_PLASTIC)
                                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                            ItemPredicate.Builder.item().of(TGItems.RAW_RUBBER.get()).build()))
                                    .save(x, id.withSuffix("_from_water")))
                            .addCondition(new NotCondition(new EmptyFluidTagCondition(ForgeTagExtensions.Fluids.OIL.location())))
                            .addRecipe(x -> new ChemicalLaboratoryRecipe.Builder()
                                    .addFluidInput(ForgeTagExtensions.Fluids.OIL, 500)
                                    .addPrimaryInput(TGItems.RAW_RUBBER)
                                    .addSecondaryInput(Items.COAL)
                                    .withEnergyDrainPerTick(25)
                                    .withProcessingTime(100)
                                    .withItemResult(TGItems.RAW_PLASTIC)
                                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                            ItemPredicate.Builder.item().of(TGItems.RAW_RUBBER.get()).build()))
                                    .save(x, id))
                            .generateAdvancement(id))));
        PLASTIC_PLATE = createAndPut(metadataMap, TGItems.PLASTIC_PLATE, (id, metadata) -> metadata
                .withLangName("Plastic Plate")
                .addRecipe(RecipeMetadata.smelting(
                        id,
                        Ingredient.of(TGItems.RAW_PLASTIC.get()),
                        RecipeCategory.MISC,
                        TGItems.PLASTIC_PLATE.get(),
                        0f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.RAW_PLASTIC.get()).build())))));
        RAW_RUBBER = createAndPut(metadataMap, TGItems.RAW_RUBBER, (id, metadata) -> metadata
                .withLangName("Raw Rubber")
                .addRecipe(RecipeMetadata.chemicalLaboratory(
                        id,
                        builder -> builder
                            .addFluidInput(FluidTags.WATER, 1000)
                            .addPrimaryInput(ItemTags.LOGS)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .withItemResult(TGItems.RAW_RUBBER)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ItemTags.LOGS).build())))));
        RUBBER_PLATE = createAndPut(metadataMap, TGItems.RUBBER_PLATE, (id, metadata) -> metadata
                .withLangName("Rubber Plate")
                .addRecipe(RecipeMetadata.smelting(
                        id,
                        Ingredient.of(TGItems.RAW_RUBBER.get()),
                        RecipeCategory.MISC,
                        TGItems.RUBBER_PLATE.get(),
                        0f,
                        100,
                        builder -> builder
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.RAW_RUBBER.get()).build())))));
        RAW_URANIUM = createAndPut(metadataMap, TGItems.RAW_URANIUM, (id, metadata) -> metadata
                .withLangName("Raw Uranium")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.RAW_URANIUM.get(),
                        9,
                        builder -> builder
                            .requires(TGBlocks.RAW_URANIUM_BLOCK.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGBlocks.RAW_URANIUM_BLOCK.get()).build())))));
        YELLOWCAKE = createAndPut(metadataMap, TGItems.YELLOWCAKE, (id, metadata) -> metadata
                .withLangName("Yellowcake")
                .addRecipe(RecipeMetadata.chemicalLaboratory(
                        id,
                        builder -> builder
                            .addFluidInput(TGTags.Fluids.ACID, 250)
                            .addPrimaryInput(TGBlocks.URANIUM_ORE)
                            .addPrimaryInput(TGBlocks.DEEPSLATE_URANIUM_ORE)
                            .addPrimaryInput(TGItems.RAW_URANIUM)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .withItemResult(TGItems.YELLOWCAKE, 3)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGBlocks.URANIUM_ORE.get()).build(),
                                    ItemPredicate.Builder.item().of(TGBlocks.DEEPSLATE_URANIUM_ORE.get()).build(),
                                    ItemPredicate.Builder.item().of(TGItems.RAW_URANIUM.get()).build())))));
        ENRICHED_URANIUM = createAndPut(metadataMap, TGItems.ENRICHED_URANIUM, (id, metadata) -> metadata
                .withLangName("Enriched Uranium")
                .addRecipe(RecipeMetadata.reactionChamber(
                        id,
                        TGItems.ENRICHED_URANIUM,
                        builder -> builder
                            .addInputItem(TGItems.YELLOWCAKE)
                            .addInputFluid(FluidTags.WATER, 3000)
                            .withLaserFocus(LaserFocuses.HEAT_RAY)
                            .withoutInstability()
                            .withStartingIntensity(7)
                            .withTicksPerCycle(60)
                            .withCycles(5, 4)
                            .withRisk(ReactionRisks.BREAK_ITEM)
                            .withEnergyDrainPerTick(250)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.YELLOWCAKE.get()).build())))));
        QUARTZ_ROD = createAndPut(metadataMap, TGItems.QUARTZ_ROD, (id, metadata) -> metadata
                .withLangName("Quartz Rod")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.QUARTZ_ROD.get(),
                        builder -> builder
                            .define('M', Items.QUARTZ)
                            .pattern("  M")
                            .pattern(" M ")
                            .pattern("M  ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.QUARTZ).build()))))
                .addRecipe(RecipeMetadata.shaped(
                        id.withSuffix("_flipped"),
                        RecipeCategory.MISC,
                        TGItems.QUARTZ_ROD.get(),
                        builder -> builder
                            .define('M', Items.QUARTZ)
                            .pattern("M  ")
                            .pattern(" M ")
                            .pattern("  M")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.QUARTZ).build())))));
        HEAVY_CLOTH = createAndPut(metadataMap, TGItems.HEAVY_CLOTH, (id, metadata) -> metadata
                .withLangName("Heavy Cloth")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.HEAVY_CLOTH.get(),
                        3,
                        builder -> builder
                            .define('W', ItemTags.WOOL)
                            .define('L', Items.LEATHER)
                            .pattern(" W ")
                            .pattern("WLW")
                            .pattern(" W ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.LEATHER).build())))));
        PROTECTIVE_FIBER = createAndPut(metadataMap, TGItems.PROTECTIVE_FIBER, (id, metadata) -> metadata
                .withLangName("Protective Fiber")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.PROTECTIVE_FIBER.get(),
                        3,
                        builder -> builder
                            .requires(TGItems.HEAVY_CLOTH.get())
                            .requires(ForgeTagExtensions.Items.PLATES_RUBBER)
                            .requires(ForgeTagExtensions.Items.PLATES_LEAD)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.HEAVY_CLOTH.get()).build())))));
        TREATED_LEATHER = createAndPut(metadataMap, TGItems.TREATED_LEATHER, (id, metadata) -> metadata
                .withLangName("Treated Leather")
                .addRecipe(RecipeMetadata.chemicalLaboratory(
                        id,
                        builder -> builder
                            .addFluidInput(TGTags.Fluids.ACID, 500)
                            .addPrimaryInput(Items.LEATHER, 2)
                            .addSecondaryInput(Tags.Items.SLIMEBALLS)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .withItemResult(TGItems.TREATED_LEATHER, 2)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.LEATHER).build())))));
        BIOMASS = createAndPut(metadataMap, TGItems.BIOMASS, (id, metadata) -> metadata
                .withLangName("Biomass")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.BIOMASS.get(),
                        4,
                        builder -> builder
                            .define('D', Tags.Items.DYES_GREEN)
                            .define('S', Tags.Items.SLIMEBALLS)
                            .pattern(" D ")
                            .pattern("DSD")
                            .pattern(" D ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.SLIMEBALLS).build())))));
        CIRCUIT_BOARD = createAndPut(metadataMap, TGItems.CIRCUIT_BOARD, (id, metadata) -> metadata
                .withLangName("Circuit Board")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.CIRCUIT_BOARD.get(),
                        4,
                        builder -> builder
                            .define('W', ForgeTagExtensions.Items.WIRES_COPPER)
                            .define('D', Tags.Items.DYES_GREEN)
                            .define('R', Tags.Items.DUSTS_REDSTONE)
                            .define('P', ForgeTagExtensions.Items.PLATES_IRON)
                            .pattern("WDW")
                            .pattern("RPR")
                            .pattern("WDW")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.DUSTS_REDSTONE).build())))));
        ELITE_CIRCUIT_BOARD = createAndPut(metadataMap, TGItems.ELITE_CIRCUIT_BOARD, (id, metadata) -> metadata
                .withLangName("Elite Circuit Board")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.ELITE_CIRCUIT_BOARD.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.CIRCUIT_BOARD.get())
                            .requires(ForgeTagExtensions.Items.WIRES_GOLD)
                            .requires(Items.LAPIS_LAZULI)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.CIRCUIT_BOARD.get()).build())))));
        COIL = createAndPut(metadataMap, TGItems.COIL, (id, metadata) -> metadata
                .withLangName("Coil")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.COIL.get(),
                        1,
                        builder -> builder
                            .define('W', ForgeTagExtensions.Items.WIRES_COPPER)
                            .define('I', Tags.Items.INGOTS_IRON)
                            .pattern(" WI")
                            .pattern("WIW")
                            .pattern("IW ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.WIRES_COPPER).build())))));
        CYBERNETIC_PARTS = createAndPut(metadataMap, TGItems.CYBERNETIC_PARTS, (id, metadata) -> metadata
                .withLangName("Cybernetic Parts")
                .addRecipe(RecipeMetadata.fabricator(
                        id,
                        TGItems.CYBERNETIC_PARTS,
                        builder -> builder
                            .addInput(Items.SOUL_SAND)
                            .addWire(ForgeTagExtensions.Items.WIRES_GOLD)
                            .addRedstone(Tags.Items.DUSTS_REDSTONE)
                            .addPlate(ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .withEnergyDrainPerTick(80)
                            .withProcessingTime(100)
                            .unlockedBy("block_placed", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(TGBlocks.FABRICATOR_CONTROLLER.get())))));
        ELECTRIC_ENGINE = createAndPut(metadataMap, TGItems.ELECTRIC_ENGINE, (id, metadata) -> metadata
                .withLangName("Electric Engine")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.ELECTRIC_ENGINE.get(),
                        1,
                        builder -> builder
                            .define('W', ForgeTagExtensions.Items.WIRES_COPPER)
                            .define('R', Tags.Items.DUSTS_REDSTONE)
                            .define('I', Tags.Items.INGOTS_IRON)
                            .define('M', TGItems.IRON_MECHANICAL_PARTS.get())
                            .pattern("WRW")
                            .pattern("IMI")
                            .pattern("WRW")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.IRON_MECHANICAL_PARTS.get()).build())))));
        LASER_FOCUS = createAndPut(metadataMap, TGItems.LASER_FOCUS, (id, metadata) -> metadata
                .withLangName("Laser Focus")
                .addRecipe(RecipeMetadata.reactionChamber(
                        id,
                        TGItems.LASER_FOCUS,
                        builder -> builder
                            .addInputItem(Tags.Items.GEMS_DIAMOND)
                            .addInputFluid(TGTags.Fluids.LIQUID_REDSTONE, 4000)
                            .withLaserFocus(LaserFocuses.HEAT_RAY)
                            .withInstability(0.5f, 1)
                            .withStartingIntensity(3)
                            .withTicksPerCycle(60)
                            .withCycles(10, 5)
                            .withRisk(ReactionRisks.BREAK_ITEM)
                            .withEnergyDrainPerTick(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.GEMS_DIAMOND).build())))));
        PUMP_MECHANISM = createAndPut(metadataMap, TGItems.PUMP_MECHANISM, (id, metadata) -> metadata
                .withLangName("Pump Mechanism")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.PUMP_MECHANISM.get(),
                        1,
                        builder -> builder
                            .define('N', Tags.Items.NUGGETS_IRON)
                            .define('P', Items.PISTON)
                            .define('G', Tags.Items.GLASS)
                            .pattern("NNN")
                            .pattern("GPG")
                            .pattern("NNN")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.PISTON).build())))));
        RAD_EMITTER = createAndPut(metadataMap, TGItems.RAD_EMITTER, (id, metadata) -> metadata
                .withLangName("RAD Emitter")
                .addRecipe(RecipeMetadata.fabricator(
                        id,
                        TGItems.RAD_EMITTER,
                        builder -> builder
                            .addInput(TGItems.ENRICHED_URANIUM)
                            .addWire(TGItems.ELITE_CIRCUIT_BOARD, 2)
                            .addRedstone(TGItems.CARBON_MECHANICAL_PARTS, 2)
                            .addPlate(TGItems.LEAD_PLATE, 2)
                            .withEnergyDrainPerTick(80)
                            .withProcessingTime(100)
                            .unlockedBy("block_placed", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(TGBlocks.FABRICATOR_CONTROLLER.get())))));
        SONIC_EMITTER = createAndPut(metadataMap, TGItems.SONIC_EMITTER, (id, metadata) -> metadata
                .withLangName("Sonic Emitter")
                .addRecipe(RecipeMetadata.fabricator(
                        id,
                        TGItems.SONIC_EMITTER,
                        builder -> builder
                            .addInput(TGItems.COIL)
                            .addWire(TGItems.ELITE_CIRCUIT_BOARD, 2)
                            .addRedstone(TGItems.CARBON_MECHANICAL_PARTS)
                            .addPlate(ForgeTagExtensions.Items.PLATES_TITANIUM)
                            .withEnergyDrainPerTick(80)
                            .withProcessingTime(100)
                            .unlockedBy("block_placed", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(TGBlocks.FABRICATOR_CONTROLLER.get())))));
        INFUSION_BAG = createAndPut(metadataMap, TGItems.INFUSION_BAG, (id, metadata) -> metadata
                .withLangName("Infusion Bag")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.INFUSION_BAG.get(),
                        4,
                        builder -> builder
                            .requires(ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .requires(ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .requires(ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .requires(ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_PLASTIC).build())))));
        TGX = createAndPut(metadataMap, TGItems.TGX, (id, metadata) -> metadata
                .withLangName("TGX")
                .addRecipe(RecipeMetadata.fromConditional(
                        id,
                        builder -> builder
                            .addCondition(new NotCondition(new EmptyFluidTagCondition(TGTags.Fluids.FUEL.location())))
                            .addRecipe((x) -> new ChemicalLaboratoryRecipe.Builder()
                                    .addFluidInput(TGTags.Fluids.FUEL, 250)
                                    .addPrimaryInput(Items.GUNPOWDER)
                                    .addSecondaryInput(Tags.Items.GEMS_LAPIS)
                                    .withEnergyDrainPerTick(20)
                                    .withProcessingTime(100)
                                    .withItemResult(TGItems.TGX)
                                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                            ItemPredicate.Builder.item().of(Tags.Items.GEMS_LAPIS).build()))
                                    .save(x, id))
                            .addCondition(new OrCondition(
                                    new EmptyFluidTagCondition(TGTags.Fluids.FUEL.location()),
                                    LavaRecipeCondition.INSTANCE))
                            .addRecipe((x) -> new ChemicalLaboratoryRecipe.Builder()
                                    .addFluidInput(Fluids.LAVA, 500)
                                    .addPrimaryInput(Items.GUNPOWDER)
                                    .addSecondaryInput(Tags.Items.GEMS_LAPIS)
                                    .withEnergyDrainPerTick(20)
                                    .withProcessingTime(100)
                                    .withItemResult(TGItems.TGX)
                                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                            ItemPredicate.Builder.item().of(Tags.Items.GEMS_LAPIS).build()))
                                    .save(x, id.withPrefix("_from_lava")))
                            .generateAdvancement(id))));
        NUCLEAR_WARHEAD = createAndPut(metadataMap, TGItems.NUCLEAR_WARHEAD, (id, metadata) -> metadata
                .withLangName("Nuclear Warhead")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.NUCLEAR_WARHEAD.get(),
                        1,
                        builder -> builder
                            .define('C', TGItems.CIRCUIT_BOARD.get())
                            .define('P', ForgeTagExtensions.Items.PLATES_LEAD)
                            .define('T', TGItems.TGX.get())
                            .define('U', TGItems.ENRICHED_URANIUM.get())
                            .pattern("PCP")
                            .pattern("TUT")
                            .pattern("PCP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.ENRICHED_URANIUM.get()).build())))));

        STONE_BULLETS = createAndPut(metadataMap, TGItems.STONE_BULLETS, (id, metadata) -> metadata
                .withLangName("Stone Bullets")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.STONE_BULLETS.get(),
                        16,
                        builder -> builder
                            .requires(Tags.Items.COBBLESTONE)
                            .requires(Tags.Items.COBBLESTONE)
                            .requires(Tags.Items.COBBLESTONE)
                            .requires(Items.GUNPOWDER)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build())))));

        PISTOL_ROUNDS = createAndPut(metadataMap, TGItems.PISTOL_ROUNDS, (id, metadata) -> metadata
                .withLangName("Pistol Rounds")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.PISTOL_ROUNDS.get(),
                        8,
                        builder -> builder
                            .define('C', ForgeTagExtensions.Items.NUGGETS_COPPER)
                            .define('P', Items.GUNPOWDER)
                            .define('B', ForgeTagExtensions.Items.INGOTS_LEAD)
                            .pattern("CBC")
                            .pattern("CPC")
                            .pattern("CCC")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_bronze"),
                        TGItems.PISTOL_ROUNDS,
                        16,
                        builder -> builder
                            .template(AmmoPressTemplates.PISTOL_ROUNDS)
                            .addCasing(ForgeTagExtensions.Items.INGOTS_BRONZE)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_copper"),
                        TGItems.PISTOL_ROUNDS,
                        10,
                        builder -> builder
                            .template(AmmoPressTemplates.PISTOL_ROUNDS)
                            .addCasing(Tags.Items.INGOTS_COPPER)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_iron"),
                        TGItems.PISTOL_ROUNDS,
                        16,
                        builder -> builder
                            .template(AmmoPressTemplates.PISTOL_ROUNDS)
                            .addCasing(Tags.Items.INGOTS_IRON)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(200)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_tin"), 
                        TGItems.PISTOL_ROUNDS,
                        12,
                        builder -> builder
                            .template(AmmoPressTemplates.PISTOL_ROUNDS)
                            .addCasing(ForgeTagExtensions.Items.INGOTS_TIN)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(120)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build())))));
        INCENDIARY_PISTOL_ROUNDS = createAndPut(metadataMap, TGItems.INCENDIARY_PISTOL_ROUNDS, (id, metadata) -> metadata
                .withLangName(ChatFormatting.GOLD + "Pistol Rounds (Incendiary)")
                .addRecipe(RecipeMetadata.chemicalLaboratory(
                        id,
                        builder -> builder
                            .addFluidInput(FluidTags.LAVA, 250)
                            .addPrimaryInput(TGItems.PISTOL_ROUNDS, 2)
                            .addSecondaryInput(Items.BLAZE_POWDER)
                            .withEnergyDrainPerTick(25)
                            .withProcessingTime(100)
                            .withItemResult(TGItems.INCENDIARY_PISTOL_ROUNDS, 2)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.PISTOL_ROUNDS.get()).build())))));

        SHOTGUN_SHELLS = createAndPut(metadataMap, TGItems.SHOTGUN_SHELLS, (id, metadata) -> metadata
                .withLangName("Shotgun Shells")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.SHOTGUN_SHELLS.get(),
                        5,
                        builder -> builder
                            .define('C', ForgeTagExtensions.Items.NUGGETS_COPPER)
                            .define('P', Items.GUNPOWDER)
                            .define('B', ForgeTagExtensions.Items.NUGGETS_LEAD)
                            .pattern("BBB")
                            .pattern("CPC")
                            .pattern("CCC")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_bronze"),
                        TGItems.SHOTGUN_SHELLS,
                        24,
                        builder -> builder
                            .template(AmmoPressTemplates.SHOTGUN_SHELLS)
                            .addCasing(ForgeTagExtensions.Items.INGOTS_BRONZE, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_copper"),
                        TGItems.SHOTGUN_SHELLS, 
                        16,
                        builder -> builder
                            .template(AmmoPressTemplates.SHOTGUN_SHELLS)
                            .addCasing(Tags.Items.INGOTS_COPPER, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_iron"),
                        TGItems.SHOTGUN_SHELLS,
                        24,
                        builder -> builder
                            .template(AmmoPressTemplates.SHOTGUN_SHELLS)
                            .addCasing(Tags.Items.INGOTS_IRON, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(200)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_tin"),
                        TGItems.SHOTGUN_SHELLS,
                        20,
                        builder -> builder
                            .template(AmmoPressTemplates.SHOTGUN_SHELLS)
                            .addCasing(ForgeTagExtensions.Items.INGOTS_TIN, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(120)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build())))));
        INCENDIARY_SHOTGUN_SHELLS = createAndPut(metadataMap, TGItems.INCENDIARY_SHOTGUN_SHELLS, (id, metadata) -> metadata
                .withLangName(ChatFormatting.GOLD + "Shotgun Shells (Incendiary)")
                .addRecipe(RecipeMetadata.chemicalLaboratory(
                        id,
                        builder -> builder
                        .addFluidInput(FluidTags.LAVA, 250)
                        .addPrimaryInput(TGItems.SHOTGUN_SHELLS, 8)
                        .addSecondaryInput(Items.BLAZE_POWDER)
                        .withEnergyDrainPerTick(25)
                        .withProcessingTime(100)
                        .withItemResult(TGItems.INCENDIARY_SHOTGUN_SHELLS, 8)
                        .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                ItemPredicate.Builder.item().of(TGItems.SHOTGUN_SHELLS.get()).build())))));

        RIFLE_ROUNDS = createAndPut(metadataMap, TGItems.RIFLE_ROUNDS, (id, metadata) -> metadata
                .withLangName("Rifle Rounds")
                .addRecipe(RecipeMetadata.shapeless(
                        id.withSuffix("_from_clip"),
                        RecipeCategory.MISC,
                        TGItems.RIFLE_ROUNDS.get(),
                        4,
                        builder -> builder
                            .requires(TGItems.RIFLE_ROUND_CLIP.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.RIFLE_ROUND_CLIP.get()).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_bronze"),
                        TGItems.RIFLE_ROUNDS,
                        16,
                        builder -> builder
                            .template(AmmoPressTemplates.RIFLE_ROUNDS)
                            .addCasing(ForgeTagExtensions.Items.INGOTS_BRONZE)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_copper"),
                        TGItems.RIFLE_ROUNDS, 
                        8,
                        builder -> builder
                            .template(AmmoPressTemplates.RIFLE_ROUNDS)
                            .addCasing(Tags.Items.INGOTS_COPPER)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_iron"),
                        TGItems.RIFLE_ROUNDS,
                        16,
                        builder -> builder
                            .template(AmmoPressTemplates.RIFLE_ROUNDS)
                            .addCasing(Tags.Items.INGOTS_IRON)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(200)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_tin"),
                        TGItems.RIFLE_ROUNDS,
                        12,
                        builder -> builder
                            .template(AmmoPressTemplates.RIFLE_ROUNDS)
                            .addCasing(ForgeTagExtensions.Items.INGOTS_TIN)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(120)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build())))));
        INCENDIARY_RIFLE_ROUNDS = createAndPut(metadataMap, TGItems.INCENDIARY_RIFLE_ROUNDS, (id, metadata) -> metadata
                .withLangName(ChatFormatting.GOLD + "Rifle Rounds (Incendiary)")
                .addRecipe(RecipeMetadata.shapeless(
                        id.withSuffix("_from_clip"),
                        RecipeCategory.MISC,
                        TGItems.INCENDIARY_RIFLE_ROUNDS.get(),
                        4,
                        builder -> builder
                            .requires(TGItems.INCENDIARY_RIFLE_ROUND_CLIP.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.INCENDIARY_RIFLE_ROUND_CLIP.get()).build()))))
                .addRecipe(RecipeMetadata.chemicalLaboratory(
                        id,
                        builder -> builder
                            .addFluidInput(FluidTags.LAVA, 250)
                            .addPrimaryInput(TGItems.RIFLE_ROUNDS, 1)
                            .addSecondaryInput(Items.BLAZE_POWDER)
                            .withEnergyDrainPerTick(25)
                            .withProcessingTime(100)
                            .withItemResult(TGItems.INCENDIARY_RIFLE_ROUNDS, 1)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.RIFLE_ROUNDS.get()).build())))));

        RIFLE_ROUND_CLIP = createAndPut(metadataMap, TGItems.RIFLE_ROUND_CLIP, (id, metadata) -> metadata
                .withLangName("Rifle Round Clip")
                .addRecipe(RecipeMetadata.shapeless(
                        id.withSuffix("_from_rounds"),
                        RecipeCategory.MISC,
                        TGItems.RIFLE_ROUND_CLIP.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.RIFLE_ROUNDS.get()).build())))));
        INCENDIARY_RIFLE_ROUND_CLIP = createAndPut(metadataMap, TGItems.INCENDIARY_RIFLE_ROUND_CLIP, (id, metadata) -> metadata
                .withLangName(ChatFormatting.GOLD + "Rifle Round Clip (Incendiary)")
                .addRecipe(RecipeMetadata.shapeless(
                        id.withSuffix("_from_rounds"),
                        RecipeCategory.MISC,
                        TGItems.INCENDIARY_RIFLE_ROUND_CLIP.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.INCENDIARY_RIFLE_ROUNDS.get()).build())))));

        SNIPER_ROUNDS = createAndPut(metadataMap, TGItems.SNIPER_ROUNDS, (id, metadata) -> metadata
                .withLangName("Sniper Rounds")
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_bronze"),
                        TGItems.SNIPER_ROUNDS,
                        8,
                        builder -> builder
                            .template(AmmoPressTemplates.SNIPER_ROUNDS)
                            .addCasing(ForgeTagExtensions.Items.INGOTS_BRONZE)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_copper"),
                        TGItems.SNIPER_ROUNDS,
                        4,
                        builder -> builder
                            .template(AmmoPressTemplates.SNIPER_ROUNDS)
                            .addCasing(Tags.Items.INGOTS_COPPER)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_iron"),
                        TGItems.SNIPER_ROUNDS,
                        8,
                        builder -> builder
                            .template(AmmoPressTemplates.SNIPER_ROUNDS)
                            .addCasing(Tags.Items.INGOTS_IRON)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(200)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))))
                .addRecipe(RecipeMetadata.ammoPress(
                        id.withSuffix("_pressed_from_tin"),
                        TGItems.SNIPER_ROUNDS,
                        6,
                        builder -> builder
                            .template(AmmoPressTemplates.SNIPER_ROUNDS)
                            .addCasing(ForgeTagExtensions.Items.INGOTS_TIN)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_STEEL, 2)
                            .addBullet(ForgeTagExtensions.Items.INGOTS_LEAD)
                            .addPowder(Items.GUNPOWDER)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(120)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.GUNPOWDER).build())))));
        INCENDIARY_SNIPER_ROUNDS = createAndPut(metadataMap, TGItems.INCENDIARY_SNIPER_ROUNDS, (id, metadata) -> metadata
                .withLangName(ChatFormatting.GOLD + "Sniper Rounds (Incendiary)")
                .addRecipe(RecipeMetadata.chemicalLaboratory(
                        id,
                        builder -> builder
                            .addFluidInput(FluidTags.LAVA, 250)
                            .addPrimaryInput(TGItems.SNIPER_ROUNDS, 1)
                            .addSecondaryInput(Items.BLAZE_POWDER)
                            .withEnergyDrainPerTick(25)
                            .withProcessingTime(100)
                            .withItemResult(TGItems.INCENDIARY_SNIPER_ROUNDS, 1)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.SNIPER_ROUNDS.get()).build())))));
        EXPLOSIVE_SNIPER_ROUNDS = createAndPut(metadataMap, TGItems.EXPLOSIVE_SNIPER_ROUNDS, (id, metadata) -> metadata
                .withLangName(ChatFormatting.DARK_RED + "Sniper Rounds (Explosive)")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.EXPLOSIVE_SNIPER_ROUNDS,
                        builder -> builder
                            .addPrimary(TGItems.INCENDIARY_SNIPER_ROUNDS)
                            .addSecondary(TGItems.TGX)
                            .withProcessingTime(100)
                            .withEnergyDrainPerTick(20)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.INCENDIARY_SNIPER_ROUNDS.get()).build())))));

        ROCKET = createAndPut(metadataMap, TGItems.ROCKET, (id, metadata) -> metadata
                .withLangName("Rocket")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.ROCKET.get(),
                        4,
                        builder -> builder
                            .define('N', Tags.Items.NUGGETS_IRON)
                            .define('P', Items.GUNPOWDER)
                            .define('T', Items.TNT)
                            .pattern(" N ")
                            .pattern("NTN")
                            .pattern("NGN")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.TNT).build())))));
        HIGH_VELOCITY_ROCKET = createAndPut(metadataMap, TGItems.HIGH_VELOCITY_ROCKET, (id, metadata) -> metadata
                .withLangName(ChatFormatting.YELLOW + "Rocket (High Velocity)")
                .addRecipe(RecipeMetadata.fromConditional(
                        id,
                        builder -> builder
                            .addCondition(new NotCondition(new EmptyFluidTagCondition(TGTags.Fluids.FUEL.location())))
                            .addRecipe((x) -> new ChemicalLaboratoryRecipe.Builder()
                                    .addFluidInput(TGTags.Fluids.FUEL, 125)
                                    .addPrimaryInput(TGItems.ROCKET, 1)
                                    .withEnergyDrainPerTick(5)
                                    .withProcessingTime(100)
                                    .withItemResult(TGItems.HIGH_VELOCITY_ROCKET, 1)
                                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                            ItemPredicate.Builder.item().of(TGItems.ROCKET.get()).build()))
                                    .save(x, id))
                            .addCondition(new OrCondition(
                                    new EmptyFluidTagCondition(TGTags.Fluids.FUEL.location()),
                                    LavaRecipeCondition.INSTANCE))
                            .addRecipe((x) -> new ChemicalLaboratoryRecipe.Builder()
                                    .addFluidInput(Fluids.LAVA, 250)
                                    .addPrimaryInput(TGItems.ROCKET, 1)
                                    .withEnergyDrainPerTick(5)
                                    .withProcessingTime(100)
                                    .withItemResult(TGItems.HIGH_VELOCITY_ROCKET, 1)
                                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                            ItemPredicate.Builder.item().of(TGItems.ROCKET.get()).build()))
                                    .save(x, id.withSuffix("_from_lava")))
                            .generateAdvancement(id))));
        NUCLEAR_ROCKET = createAndPut(metadataMap, TGItems.NUCLEAR_ROCKET, (id, metadata) -> metadata
                .withLangName(ChatFormatting.GREEN + "Rocket (Tactical Nuke)")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.NUCLEAR_ROCKET.get(),
                        4,
                        builder -> builder
                            .requires(TGItems.ROCKET.get())
                            .requires(TGItems.NUCLEAR_WARHEAD.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.ROCKET.get()).build())))));

        ADVANCED_ROUNDS = createAndPut(metadataMap, TGItems.ADVANCED_ROUNDS, (id, metadata) -> metadata
                .withLangName("Advanced Rounds")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.ADVANCED_ROUNDS,
                        16,
                        builder -> builder
                            .addPrimary(ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .addSecondary(TGItems.TGX)
                            .withProcessingTime(100)
                            .withEnergyDrainPerTick(20)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.TGX.get()).build())))));

        NETHER_CHARGE = createAndPut(metadataMap, TGItems.NETHER_CHARGE, (id, metadata) -> metadata
                .withLangName("Nether Charge")
                .addRecipe(RecipeMetadata.chemicalLaboratory(
                        id,
                        builder -> builder
                            .addFluidInput(FluidTags.LAVA, 1000)
                            .addPrimaryInput(Items.NETHERRACK)
                            .addSecondaryInput(Items.SOUL_SAND)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .withItemResult(TGItems.NETHER_CHARGE, 4)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.NETHERRACK).build())))));

        GAUSS_RIFLE_SLUGS = createAndPut(metadataMap, TGItems.GAUSS_RIFLE_SLUGS, (id, metadata) -> metadata
                .withLangName("Gauss Rifle Slugs")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.GAUSS_RIFLE_SLUGS,
                        4,
                        builder -> builder
                            .addPrimary(ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .addSecondary(ForgeTagExtensions.Items.PLATES_TITANIUM)
                            .withProcessingTime(100)
                            .withEnergyDrainPerTick(20)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_TITANIUM).build())))));

        PISTOL_MAGAZINE = createAndPut(metadataMap, TGItems.PISTOL_MAGAZINE, (id, metadata) -> metadata
                .withLangName("Pistol Magazine")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.PISTOL_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_PISTOL_MAGAZINE.get())
                            .requires(TGItems.PISTOL_ROUNDS.get())
                            .requires(TGItems.PISTOL_ROUNDS.get())
                            .requires(TGItems.PISTOL_ROUNDS.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.PISTOL_ROUNDS.get()).build())))));
        INCENDIARY_PISTOL_MAGAZINE = createAndPut(metadataMap, TGItems.INCENDIARY_PISTOL_MAGAZINE, (id, metadata) -> metadata
                .withLangName(ChatFormatting.GOLD + "Pistol Magazine (Incendiary)")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.INCENDIARY_PISTOL_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_PISTOL_MAGAZINE.get())
                            .requires(TGItems.INCENDIARY_PISTOL_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_PISTOL_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_PISTOL_ROUNDS.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.INCENDIARY_PISTOL_ROUNDS.get()).build())))));

        SMG_MAGAZINE = createAndPut(metadataMap, TGItems.SMG_MAGAZINE, (id, metadata) -> metadata
                .withLangName("SMG Magazine")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.SMG_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_PISTOL_MAGAZINE.get())
                            .requires(TGItems.PISTOL_ROUNDS.get())
                            .requires(TGItems.PISTOL_ROUNDS.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.PISTOL_ROUNDS.get()).build())))));
        INCENDIARY_SMG_MAGAZINE = createAndPut(metadataMap, TGItems.INCENDIARY_SMG_MAGAZINE, (id, metadata) -> metadata
                .withLangName(ChatFormatting.GOLD + "SMG Magazine (Incendiary)")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.INCENDIARY_SMG_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_PISTOL_MAGAZINE.get())
                            .requires(TGItems.INCENDIARY_PISTOL_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_PISTOL_ROUNDS.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.INCENDIARY_PISTOL_ROUNDS.get()).build())))));

        ASSAULT_RIFLE_MAGAZINE = createAndPut(metadataMap, TGItems.ASSAULT_RIFLE_MAGAZINE, (id, metadata) -> metadata
                .withLangName("Assault Rifle Magazine")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.ASSAULT_RIFLE_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_ASSAULT_RIFLE_MAGAZINE.get())
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.RIFLE_ROUNDS.get()).build())))));
        INCENDIARY_ASSAULT_RIFLE_MAGAZINE = createAndPut(metadataMap, TGItems.INCENDIARY_ASSAULT_RIFLE_MAGAZINE, (id, metadata) -> metadata
                .withLangName(ChatFormatting.GOLD + "Assault Rifle Magazine (Incendiary)")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.INCENDIARY_ASSAULT_RIFLE_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_ASSAULT_RIFLE_MAGAZINE.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.INCENDIARY_RIFLE_ROUNDS.get()).build())))));

        LMG_MAGAZINE = createAndPut(metadataMap, TGItems.LMG_MAGAZINE, (id, metadata) -> metadata
                .withLangName("LMG Magazine")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.LMG_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_LMG_MAGAZINE.get())
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .requires(TGItems.RIFLE_ROUNDS.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.RIFLE_ROUNDS.get()).build()))))
                .addRecipe(RecipeMetadata.shapeless(
                        id.withSuffix("_from_clip"),
                        RecipeCategory.MISC,
                        TGItems.LMG_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_LMG_MAGAZINE.get())
                            .requires(TGItems.RIFLE_ROUND_CLIP.get())
                            .requires(TGItems.RIFLE_ROUND_CLIP.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.RIFLE_ROUND_CLIP.get()).build())))));
        INCENDIARY_LMG_MAGAZINE = createAndPut(metadataMap, TGItems.INCENDIARY_LMG_MAGAZINE, (id, metadata) -> metadata
                .withLangName(ChatFormatting.GOLD + "LMG Magazine (Incendiary)")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.LMG_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_LMG_MAGAZINE.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.INCENDIARY_RIFLE_ROUNDS.get()).build()))))
                .addRecipe(RecipeMetadata.shapeless(
                        id.withSuffix("_from_clip"),
                        RecipeCategory.MISC,
                        TGItems.INCENDIARY_LMG_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_LMG_MAGAZINE.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUND_CLIP.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUND_CLIP.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.INCENDIARY_RIFLE_ROUND_CLIP.get()).build())))));

        MINIGUN_MAGAZINE = createAndPut(metadataMap, TGItems.MINIGUN_MAGAZINE, (id, metadata) -> metadata
                .withLangName("Minigun Drum")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.MINIGUN_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_MINIGUN_MAGAZINE.get())
                            .requires(TGItems.RIFLE_ROUND_CLIP.get())
                            .requires(TGItems.RIFLE_ROUND_CLIP.get())
                            .requires(TGItems.RIFLE_ROUND_CLIP.get())
                            .requires(TGItems.RIFLE_ROUND_CLIP.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.RIFLE_ROUND_CLIP.get()).build())))));
        INCENDIARY_MINIGUN_MAGAZINE = createAndPut(metadataMap, TGItems.INCENDIARY_MINIGUN_MAGAZINE, (id, metadata) -> metadata
                .withLangName(ChatFormatting.GOLD + "Minigun Drum (Incendiary)")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.INCENDIARY_MINIGUN_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_MINIGUN_MAGAZINE.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUND_CLIP.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUND_CLIP.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUND_CLIP.get())
                            .requires(TGItems.INCENDIARY_RIFLE_ROUND_CLIP.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.INCENDIARY_RIFLE_ROUND_CLIP.get()).build())))));

        AS50_MAGAZINE = createAndPut(metadataMap, TGItems.AS50_MAGAZINE, (id, metadata) -> metadata
                .withLangName("AS50 Magazine")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.AS50_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_AS50_MAGAZINE.get())
                            .requires(TGItems.SNIPER_ROUNDS.get())
                            .requires(TGItems.SNIPER_ROUNDS.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.SNIPER_ROUNDS.get()).build())))));
        INCENDIARY_AS50_MAGAZINE = createAndPut(metadataMap, TGItems.INCENDIARY_AS50_MAGAZINE, (id, metadata) -> metadata
                .withLangName(ChatFormatting.GOLD + "AS50 Magazine (Incendiary)")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.INCENDIARY_AS50_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_AS50_MAGAZINE.get())
                            .requires(TGItems.INCENDIARY_SNIPER_ROUNDS.get())
                            .requires(TGItems.INCENDIARY_SNIPER_ROUNDS.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.INCENDIARY_SNIPER_ROUNDS.get()).build())))));
        EXPLOSIVE_AS50_MAGAZINE = createAndPut(metadataMap, TGItems.EXPLOSIVE_AS50_MAGAZINE, (id, metadata) -> metadata
                .withLangName(ChatFormatting.DARK_RED + "AS50 Magazine (Explosive)")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.EXPLOSIVE_AS50_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_AS50_MAGAZINE.get())
                            .requires(TGItems.EXPLOSIVE_SNIPER_ROUNDS.get())
                            .requires(TGItems.EXPLOSIVE_SNIPER_ROUNDS.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.EXPLOSIVE_SNIPER_ROUNDS.get()).build())))));

        ADVANCED_MAGAZINE = createAndPut(metadataMap, TGItems.ADVANCED_MAGAZINE, (id, metadata) -> metadata
                .withLangName("Advanced Magazine")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.ADVANCED_MAGAZINE.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.EMPTY_ADVANCED_MAGAZINE.get())
                            .requires(TGItems.ADVANCED_ROUNDS.get())
                            .requires(TGItems.ADVANCED_ROUNDS.get())
                            .requires(TGItems.ADVANCED_ROUNDS.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.ADVANCED_ROUNDS.get()).build())))));

        GRENADE_40MM = createAndPut(metadataMap, TGItems.GRENADE_40MM, (id, metadata) -> metadata
                .withLangName("40MM Grenade")
                .addRecipe(RecipeMetadata.metalPress(
                        id,
                        TGItems.GRENADE_40MM,
                        16,
                        builder -> builder
                            .addPrimary(ForgeTagExtensions.Items.PLATES_IRON)
                            .addSecondary(Items.TNT)
                            .withProcessingTime(100)
                            .withEnergyDrainPerTick(20)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.TNT).build())))));

        COMPRESSED_AIR_TANK = createAndPut(metadataMap, TGItems.COMPRESSED_AIR_TANK, (id, metadata) -> metadata
                .withLangName("Compressed Air Tank")
                .addRecipe(RecipeMetadata.chemicalLaboratory(
                        id,
                        builder -> builder
                            .addFluidInput(FluidTags.WATER, 250)
                            .addPrimaryInput(Items.COAL, 1)
                            .addTankInput(TGItems.EMPTY_COMPRESSED_AIR_TANK)
                            .withEnergyDrainPerTick(5)
                            .withProcessingTime(100)
                            .withItemResult(TGItems.COMPRESSED_AIR_TANK)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.EMPTY_COMPRESSED_AIR_TANK.get()).build())))));

        BIO_TANK = createAndPut(metadataMap, TGItems.BIO_TANK, (id, metadata) -> metadata
                .withLangName("Bio Tank")
                .addRecipe(RecipeMetadata.chemicalLaboratory(
                        id,
                        builder -> builder
                            .addFluidInput(FluidTags.WATER, 500)
                            .addPrimaryInput(TGItems.BIOMASS, 1)
                            .addTankInput(TGItems.EMPTY_BIO_TANK)
                            .withEnergyDrainPerTick(1)
                            .withProcessingTime(100)
                            .withItemResult(TGItems.BIO_TANK)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.EMPTY_BIO_TANK.get()).build())))));

        FUEL_TANK = createAndPut(metadataMap, TGItems.FUEL_TANK, (id, metadata) -> metadata
                .withLangName("Fuel Tank")
                .addRecipe(RecipeMetadata.fromConditional(
                        id,
                        builder -> builder
                            .addCondition(new NotCondition(new EmptyFluidTagCondition(TGTags.Fluids.FUEL.location())))
                            .addRecipe(x -> new ChemicalLaboratoryRecipe.Builder()
                                    .addFluidInput(TGTags.Fluids.FUEL, 250)
                                    .addTankInput(TGItems.EMPTY_FUEL_TANK)
                                    .withEnergyDrainPerTick(1)
                                    .withProcessingTime(100)
                                    .withItemResult(TGItems.FUEL_TANK)
                                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                            ItemPredicate.Builder.item().of(TGItems.EMPTY_FUEL_TANK.get()).build()))
                                    .save(x, id))
                            .addCondition(new OrCondition(
                                    new EmptyFluidTagCondition(TGTags.Fluids.FUEL.location()),
                                    LavaRecipeCondition.INSTANCE))
                            .addRecipe(x -> new ChemicalLaboratoryRecipe.Builder()
                                    .addFluidInput(FluidTags.LAVA, 500)
                                    .addTankInput(TGItems.EMPTY_FUEL_TANK)
                                    .withEnergyDrainPerTick(1)
                                    .withProcessingTime(100)
                                    .withItemResult(TGItems.FUEL_TANK)
                                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                            ItemPredicate.Builder.item().of(TGItems.EMPTY_FUEL_TANK.get()).build()))
                                    .save(x, id.withSuffix("_from_lava")))
                            .generateAdvancement(id))));

        REDSTONE_BATTERY = createAndPut(metadataMap, TGItems.REDSTONE_BATTERY, (id, metadata) -> metadata
                .withLangName("Redstone Battery")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.REDSTONE_BATTERY.get(),
                        2,
                        builder -> builder
                            .define('N', ForgeTagExtensions.Items.NUGGETS_COPPER)
                            .define('R', Tags.Items.DUSTS_REDSTONE)
                            .define('W', ForgeTagExtensions.Items.WIRES_COPPER)
                            .pattern("NWN")
                            .pattern("NRN")
                            .pattern("NRN")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.DUSTS_REDSTONE).build()))))
                .addRecipe(RecipeMetadata.shapeless(
                        id.withSuffix("from_depleted"),
                        RecipeCategory.MISC,
                        TGItems.REDSTONE_BATTERY.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.DEPLETED_REDSTONE_BATTERY.get())
                            .requires(Tags.Items.DUSTS_REDSTONE)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.DEPLETED_REDSTONE_BATTERY.get()).build()))))
                .addRecipe(RecipeMetadata.charging(
                        id.withSuffix("_charged_from_depleted"),
                        TGItems.REDSTONE_BATTERY,
                        builder -> builder
                            .addInput(TGItems.DEPLETED_REDSTONE_BATTERY)
                            .withEnergyNeeded(800 * 25)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.DEPLETED_REDSTONE_BATTERY.get()).build())))));

        ENERGY_CELL = createAndPut(metadataMap, TGItems.ENERGY_CELL, (id, metadata) -> metadata
                .withLangName("Energy Cell")
                .addRecipe(RecipeMetadata.charging(
                        id,
                        TGItems.ENERGY_CELL,
                        builder -> builder
                            .addInput(TGItems.DEPLETED_ENERGY_CELL)
                            .withEnergyNeeded(800 * 65)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.DEPLETED_ENERGY_CELL.get()).build())))));

        NUCLEAR_POWER_CELL = createAndPut(metadataMap, TGItems.NUCLEAR_POWER_CELL, (id, metadata) -> metadata
                .withLangName("Nuclear Power Cell")
                .addRecipe(RecipeMetadata.chemicalLaboratory(
                        id,
                        builder -> builder
                            .addFluidInput(FluidTags.WATER, 1000)
                            .addPrimaryInput(TGItems.ENRICHED_URANIUM, 1)
                            .addTankInput(TGItems.DEPLETED_NUCLEAR_POWER_CELL)
                            .withEnergyDrainPerTick(40)
                            .withProcessingTime(100)
                            .withItemResult(TGItems.NUCLEAR_POWER_CELL)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.DEPLETED_NUCLEAR_POWER_CELL.get()).build())))));

        GAS_MASK_FILTER = createAndPut(metadataMap, TGItems.GAS_MASK_FILTER, (id, metadata) -> metadata
                .withLangName("Gask Mask Filter")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.REDSTONE_BATTERY.get(),
                        2,
                        builder -> builder
                            .define('N', Tags.Items.NUGGETS_IRON)
                            .define('C', Items.COAL)
                            .pattern("NNN")
                            .pattern("NCN")
                            .pattern("NNN")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.COAL).build())))));

        EMPTY_PISTOL_MAGAZINE = createAndPut(metadataMap, TGItems.EMPTY_PISTOL_MAGAZINE, (id, metadata) -> metadata
                .withLangName("Pistol Magazine (Empty)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.EMPTY_PISTOL_MAGAZINE.get(),
                        4,
                        builder -> builder
                            .define('N', Tags.Items.NUGGETS_IRON)
                            .define('I', Tags.Items.INGOTS_IRON)
                            .define('M', TGItems.IRON_MECHANICAL_PARTS.get())
                            .pattern("N N")
                            .pattern("NMN")
                            .pattern("NIN")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.IRON_MECHANICAL_PARTS.get()).build())))));
        EMPTY_SMG_MAGAZINE = createAndPut(metadataMap, TGItems.EMPTY_SMG_MAGAZINE, (id, metadata) -> metadata
                .withLangName("SMG Magazine (Empty)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.EMPTY_SMG_MAGAZINE.get(),
                        4,
                        builder -> builder
                            .define('N', Tags.Items.NUGGETS_IRON)
                            .define('M', TGItems.IRON_MECHANICAL_PARTS.get())
                            .pattern("N N")
                            .pattern("N N")
                            .pattern("NMN")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.IRON_MECHANICAL_PARTS.get()).build())))));
        EMPTY_ASSAULT_RIFLE_MAGAZINE = createAndPut(metadataMap, TGItems.EMPTY_ASSAULT_RIFLE_MAGAZINE, (id, metadata) -> metadata
                .withLangName("Assault Rifle Magazine (Empty)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.EMPTY_ASSAULT_RIFLE_MAGAZINE.get(),
                        4,
                        builder -> builder
                            .define('N', ForgeTagExtensions.Items.NUGGETS_STEEL)
                            .define('M', TGItems.IRON_MECHANICAL_PARTS.get())
                            .pattern("N N")
                            .pattern("N N")
                            .pattern("NMN")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.IRON_MECHANICAL_PARTS.get()).build())))));
        EMPTY_LMG_MAGAZINE = createAndPut(metadataMap, TGItems.EMPTY_LMG_MAGAZINE, (id, metadata) -> metadata
                .withLangName("LMG Magazine (Empty)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.EMPTY_LMG_MAGAZINE.get(),
                        4,
                        builder -> builder
                            .define('I', ForgeTagExtensions.Items.INGOTS_STEEL)
                            .define('M', TGItems.IRON_MECHANICAL_PARTS.get())
                            .pattern(" II")
                            .pattern("IMI")
                            .pattern("III")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.IRON_MECHANICAL_PARTS.get()).build())))));
        EMPTY_MINIGUN_MAGAZINE = createAndPut(metadataMap, TGItems.EMPTY_MINIGUN_MAGAZINE, (id, metadata) -> metadata
                .withLangName("Minigun Drum (Empty)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.EMPTY_MINIGUN_MAGAZINE.get(),
                        4,
                        builder -> builder
                            .define('I', ForgeTagExtensions.Items.INGOTS_STEEL)
                            .define('P', ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .define('M', TGItems.OBSIDIAN_STEEL_MECHANICAL_PARTS.get())
                            .pattern("III")
                            .pattern("PMP")
                            .pattern("III")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.OBSIDIAN_STEEL_MECHANICAL_PARTS.get()).build())))));
        EMPTY_AS50_MAGAZINE = createAndPut(metadataMap, TGItems.EMPTY_AS50_MAGAZINE, (id, metadata) -> metadata
                .withLangName("AS50 Magazine (Empty)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.EMPTY_AS50_MAGAZINE.get(),
                        4,
                        builder -> builder
                            .define('I', ForgeTagExtensions.Items.INGOTS_STEEL)
                            .define('M', TGItems.IRON_MECHANICAL_PARTS.get())
                            .pattern("I I")
                            .pattern("I I")
                            .pattern("IMI")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.IRON_MECHANICAL_PARTS.get()).build())))));
        EMPTY_ADVANCED_MAGAZINE = createAndPut(metadataMap, TGItems.EMPTY_ADVANCED_MAGAZINE, (id, metadata) -> metadata
                .withLangName("Advanced Magazine (Empty)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.EMPTY_ADVANCED_MAGAZINE.get(),
                        4,
                        builder -> builder
                            .define('I', ForgeTagExtensions.Items.INGOTS_OBSIDIAN_STEEL)
                            .define('M', TGItems.OBSIDIAN_STEEL_MECHANICAL_PARTS.get())
                            .pattern("I I")
                            .pattern("I I")
                            .pattern("IMI")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.INGOTS_OBSIDIAN_STEEL).build())))));
        EMPTY_COMPRESSED_AIR_TANK = createAndPut(metadataMap, TGItems.EMPTY_COMPRESSED_AIR_TANK, (id, metadata) -> metadata
                .withLangName("Compressed Air Tank (Empty)")
                .addRecipe(RecipeMetadata.shaped(
                        id.withSuffix("_from_iron"),
                        RecipeCategory.MISC,
                        TGItems.EMPTY_COMPRESSED_AIR_TANK.get(),
                        7,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_IRON)
                            .define('L', Items.LEVER)
                            .pattern("PLP")
                            .pattern("P P")
                            .pattern("PPP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_IRON).build()))))
                .addRecipe(RecipeMetadata.shaped(
                        id.withSuffix("_from_tin"),
                        RecipeCategory.MISC,
                        TGItems.EMPTY_COMPRESSED_AIR_TANK.get(),
                        7,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_TIN)
                            .define('L', Items.LEVER)
                            .pattern("PLP")
                            .pattern("P P")
                            .pattern("PPP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_IRON).build())))));
        EMPTY_BIO_TANK = createAndPut(metadataMap, TGItems.EMPTY_BIO_TANK, (id, metadata) -> metadata
                .withLangName("Bio Tank (Empty)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.EMPTY_BIO_TANK.get(),
                        1,
                        builder -> builder
                            .define('N', ForgeTagExtensions.Items.NUGGETS_STEEL)
                            .define('G', Tags.Items.GLASS)
                            .pattern("NNN")
                            .pattern("NGN")
                            .pattern("NGN")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.GLASS).build())))));
        EMPTY_FUEL_TANK = createAndPut(metadataMap, TGItems.EMPTY_FUEL_TANK, (id, metadata) -> metadata
                .withLangName("Fuel Tank (Empty)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.EMPTY_FUEL_TANK.get(),
                        4,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .define('G', Tags.Items.GLASS)
                            .pattern("P")
                            .pattern("G")
                            .pattern("P")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.GLASS).build())))));
        DEPLETED_REDSTONE_BATTERY = createAndPut(metadataMap, TGItems.DEPLETED_REDSTONE_BATTERY, (id, metadata) -> metadata
                .withLangName("Redstone Battery (Depleted)"));
        DEPLETED_ENERGY_CELL = createAndPut(metadataMap, TGItems.DEPLETED_ENERGY_CELL, (id, metadata) -> metadata
                .withLangName("Energy Cell (Depleted)")
                .addRecipe(RecipeMetadata.fabricator(
                        id,
                        TGItems.DEPLETED_ENERGY_CELL,
                        builder -> builder
                            .addInput(Tags.Items.INGOTS_GOLD, 1)
                            .addWire(ForgeTagExtensions.Items.WIRES_COPPER, 1)
                            .addRedstone(Tags.Items.DUSTS_REDSTONE, 3)
                            .addPlate(ForgeTagExtensions.Items.PLATES_PLASTIC, 1)
                            .withEnergyDrainPerTick(80)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.INGOTS_GOLD).build())))));
        DEPLETED_NUCLEAR_POWER_CELL = createAndPut(metadataMap, TGItems.DEPLETED_NUCLEAR_POWER_CELL, (id, metadata) -> metadata
                .withLangName("Nuclear Power Cell (Depleted)")
                .addRecipe(RecipeMetadata.fabricator(
                        id,
                        TGItems.NUCLEAR_POWER_CELL,
                        builder -> builder
                            .addInput(ForgeTagExtensions.Items.INGOTS_STEEL, 1)
                            .addWire(TGItems.CIRCUIT_BOARD, 1)
                            .addRedstone(Tags.Items.DUSTS_REDSTONE, 4)
                            .addPlate(ForgeTagExtensions.Items.PLATES_LEAD, 2)
                            .withEnergyDrainPerTick(80)
                            .withProcessingTime(100)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_LEAD).build())))));

        STACK_UPGRADE = createAndPut(metadataMap, TGItems.STACK_UPGRADE, (id, metadata) -> metadata
                .withLangName("Stack Upgrade")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.STACK_UPGRADE.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_IRON)
                            .define('G', Tags.Items.INGOTS_GOLD)
                            .define('D', Tags.Items.DYES_GREEN)
                            .define('C', Tags.Items.CHESTS_WOODEN)
                            .pattern("PDP")
                            .pattern("GCG")
                            .pattern("PGP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.CHESTS_WOODEN).build())))));
        IRON_TURRET_ARMOR = createAndPut(metadataMap, TGItems.IRON_TURRET_ARMOR, (id, metadata) -> metadata
                .withLangName("Turret Armor (Iron)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.IRON_TURRET_ARMOR.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_IRON)
                            .pattern("PPP")
                            .pattern(" P ")
                            .pattern(" P ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_IRON).build())))));
        STEEL_TURRET_ARMOR = createAndPut(metadataMap, TGItems.STEEL_TURRET_ARMOR, (id, metadata) -> metadata
                .withLangName("Turret Armor (Steel)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.STEEL_TURRET_ARMOR.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_STEEL)
                            .pattern("PPP")
                            .pattern(" P ")
                            .pattern(" P ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_STEEL).build())))));
        OBSIDIAN_STEEL_TURRET_ARMOR = createAndPut(metadataMap, TGItems.OBSIDIAN_STEEL_TURRET_ARMOR, (id, metadata) -> metadata
                .withLangName("Turret Armor (Obsidian Steel)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.OBSIDIAN_STEEL_TURRET_ARMOR.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .pattern("PPP")
                            .pattern(" P ")
                            .pattern(" P ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL).build())))));
        CARBON_TURRET_ARMOR = createAndPut(metadataMap, TGItems.CARBON_TURRET_ARMOR, (id, metadata) -> metadata
                .withLangName("Turret Armor (Carbon)")
                    .addRecipe(RecipeMetadata.shaped(
                            id,
                            RecipeCategory.MISC,
                            TGItems.CARBON_TURRET_ARMOR.get(),
                            1,
                            builder -> builder
                                .define('P', ForgeTagExtensions.Items.PLATES_CARBON)
                                .pattern("PPP")
                                .pattern(" P ")
                                .pattern(" P ")
                                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                        ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_CARBON).build())))));

        PROTECTION_1_ARMOR_UPGRADE = createAndPut(metadataMap, TGItems.PROTECTION_1_ARMOR_UPGRADE, (id, metadata) -> metadata
                .withLangName("Armor Upgrade (Protection I)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.PROTECTION_1_ARMOR_UPGRADE.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_STEEL)
                            .define('G', Tags.Items.INGOTS_GOLD)
                            .define('I', ItemTags.WOOL)
                            .pattern("PIP")
                            .pattern("IGI")
                            .pattern("PIP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.INGOTS_GOLD).build())))));
        PROTECTION_2_ARMOR_UPGRADE = createAndPut(metadataMap, TGItems.PROTECTION_2_ARMOR_UPGRADE, (id, metadata) -> metadata
                .withLangName("Armor Upgrade (Protection II)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.PROTECTION_2_ARMOR_UPGRADE.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_STEEL)
                            .define('G', TGItems.PROTECTION_1_ARMOR_UPGRADE.get())
                            .define('I', ForgeTagExtensions.Items.PLATES_IRON)
                            .pattern("PIP")
                            .pattern("IGI")
                            .pattern("PIP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.PROTECTION_1_ARMOR_UPGRADE.get()).build())))));
        PROTECTION_3_ARMOR_UPGRADE = createAndPut(metadataMap, TGItems.PROTECTION_3_ARMOR_UPGRADE, (id, metadata) -> metadata
                .withLangName("Armor Upgrade (Protection III)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.PROTECTION_3_ARMOR_UPGRADE.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_STEEL)
                            .define('G', TGItems.PROTECTION_2_ARMOR_UPGRADE.get())
                            .define('I', TGItems.STEAM_ARMOR_PLATE.get())
                            .pattern("PIP")
                            .pattern("IGI")
                            .pattern("PIP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.PROTECTION_2_ARMOR_UPGRADE.get()).build())))));
        PROTECTION_4_ARMOR_UPGRADE = createAndPut(metadataMap, TGItems.PROTECTION_4_ARMOR_UPGRADE, (id, metadata) -> metadata
                .withLangName("Armor Upgrade (Protection IV)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.PROTECTION_4_ARMOR_UPGRADE.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_STEEL)
                            .define('G', TGItems.PROTECTION_3_ARMOR_UPGRADE.get())
                            .define('I', Items.OBSIDIAN)
                            .pattern("PIP")
                            .pattern("IGI")
                            .pattern("PIP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.PROTECTION_3_ARMOR_UPGRADE.get()).build())))));
        PROJECTILE_PROTECTION_1_ARMOR_UPGRADE = createAndPut(metadataMap, TGItems.PROJECTILE_PROTECTION_1_ARMOR_UPGRADE, (id, metadata) -> metadata
                .withLangName("Armor Upgrade (Projectile Protection I)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.PROJECTILE_PROTECTION_2_ARMOR_UPGRADE.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .define('G', Tags.Items.INGOTS_GOLD)
                            .define('I', ItemTags.WOOL)
                            .pattern("PIP")
                            .pattern("IGI")
                            .pattern("PIP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.INGOTS_GOLD).build())))));
        PROJECTILE_PROTECTION_2_ARMOR_UPGRADE = createAndPut(metadataMap, TGItems.PROJECTILE_PROTECTION_2_ARMOR_UPGRADE, (id, metadata) -> metadata
                .withLangName("Armor Upgrade (Projectile Protection II)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.PROJECTILE_PROTECTION_2_ARMOR_UPGRADE.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .define('G', TGItems.PROJECTILE_PROTECTION_1_ARMOR_UPGRADE.get())
                            .define('I', ForgeTagExtensions.Items.PLATES_IRON)
                            .pattern("PIP")
                            .pattern("IGI")
                            .pattern("PIP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.PROJECTILE_PROTECTION_1_ARMOR_UPGRADE.get()).build())))));
        PROJECTILE_PROTECTION_3_ARMOR_UPGRADE = createAndPut(metadataMap, TGItems.PROJECTILE_PROTECTION_3_ARMOR_UPGRADE, (id, metadata) -> metadata
                .withLangName("Armor Upgrade (Projectile Protection III)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.PROJECTILE_PROTECTION_3_ARMOR_UPGRADE.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .define('G', TGItems.PROJECTILE_PROTECTION_2_ARMOR_UPGRADE.get())
                            .define('I', TGItems.STEAM_ARMOR_PLATE.get())
                            .pattern("PIP")
                            .pattern("IGI")
                            .pattern("PIP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.PROJECTILE_PROTECTION_2_ARMOR_UPGRADE.get()).build())))));
        PROJECTILE_PROTECTION_4_ARMOR_UPGRADE = createAndPut(metadataMap, TGItems.PROJECTILE_PROTECTION_4_ARMOR_UPGRADE, (id, metadata) -> metadata
                .withLangName("Armor Upgrade (Projectile Protection IV)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.PROJECTILE_PROTECTION_4_ARMOR_UPGRADE.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .define('G', TGItems.PROJECTILE_PROTECTION_3_ARMOR_UPGRADE.get())
                            .define('I', Items.OBSIDIAN)
                            .pattern("PIP")
                            .pattern("IGI")
                            .pattern("PIP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.PROJECTILE_PROTECTION_3_ARMOR_UPGRADE.get()).build())))));
        BLAST_PROTECTION_1_ARMOR_UPGRADE = createAndPut(metadataMap, TGItems.BLAST_PROTECTION_1_ARMOR_UPGRADE, (id, metadata) -> metadata
                .withLangName("Armor Upgrade (Blast Protection I)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.BLAST_PROTECTION_1_ARMOR_UPGRADE.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_CARBON)
                            .define('G', Tags.Items.GEMS_DIAMOND)
                            .define('I', ItemTags.WOOL)
                            .pattern("PIP")
                            .pattern("IGI")
                            .pattern("PIP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Tags.Items.GEMS_DIAMOND).build())))));
        BLAST_PROTECTION_2_ARMOR_UPGRADE = createAndPut(metadataMap, TGItems.BLAST_PROTECTION_2_ARMOR_UPGRADE, (id, metadata) -> metadata
                .withLangName("Armor Upgrade (Blast Protection II)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.BLAST_PROTECTION_2_ARMOR_UPGRADE.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_CARBON)
                            .define('G', TGItems.BLAST_PROTECTION_1_ARMOR_UPGRADE.get())
                            .define('I', ForgeTagExtensions.Items.PLATES_IRON)
                            .pattern("PIP")
                            .pattern("IGI")
                            .pattern("PIP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.BLAST_PROTECTION_1_ARMOR_UPGRADE.get()).build())))));
        BLAST_PROTECTION_3_ARMOR_UPGRADE = createAndPut(metadataMap, TGItems.BLAST_PROTECTION_3_ARMOR_UPGRADE, (id, metadata) -> metadata
                .withLangName("Armor Upgrade (Blast Protection III)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.BLAST_PROTECTION_3_ARMOR_UPGRADE.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_CARBON)
                            .define('G', TGItems.BLAST_PROTECTION_2_ARMOR_UPGRADE.get())
                            .define('I', TGItems.STEAM_ARMOR_PLATE.get())
                            .pattern("PIP")
                            .pattern("IGI")
                            .pattern("PIP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.BLAST_PROTECTION_2_ARMOR_UPGRADE.get()).build())))));
        BLAST_PROTECTION_4_ARMOR_UPGRADE = createAndPut(metadataMap, TGItems.BLAST_PROTECTION_4_ARMOR_UPGRADE, (id, metadata) -> metadata
                .withLangName("Armor Upgrade (Blast Protection IV)")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.BLAST_PROTECTION_4_ARMOR_UPGRADE.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_CARBON)
                            .define('G', TGItems.BLAST_PROTECTION_3_ARMOR_UPGRADE.get())
                            .define('I', Items.OBSIDIAN)
                            .pattern("PIP")
                            .pattern("IGI")
                            .pattern("PIP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.BLAST_PROTECTION_3_ARMOR_UPGRADE.get()).build())))));

        WORKING_GLOVES = createAndPut(metadataMap, TGItems.WORKING_GLOVES, (id, metadata) -> metadata
                .withLangName("Working Gloves")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.WORKING_GLOVES.get(),
                        1,
                        builder -> builder
                            .define('C', TGItems.HEAVY_CLOTH.get())
                            .pattern(" C")
                            .pattern("CC")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.HEAVY_CLOTH.get()).build())))));
        GAS_MASK = createAndPut(metadataMap, TGItems.GAS_MASK, (id, metadata) -> metadata
                .withLangName("Gas Mask")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.GAS_MASK.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_RUBBER)
                            .define('G', Tags.Items.GLASS_PANES)
                            .define('F', TGItems.GAS_MASK_FILTER.get())
                            .pattern("GPG")
                            .pattern("PPP")
                            .pattern("PFP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.GAS_MASK_FILTER.get()).build())))));
        GLIDER = createAndPut(metadataMap, TGItems.GLIDER, (id, metadata) -> metadata
                .withLangName("Glider")
                .addRecipe(RecipeMetadata.shapeless(
                        id,
                        RecipeCategory.MISC,
                        TGItems.GLIDER.get(),
                        1,
                        builder -> builder
                            .requires(TGItems.GLIDER_BACKPACK.get())
                            .requires(TGItems.GLIDER_WING.get())
                            .requires(TGItems.GLIDER_WING.get())
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.GLIDER_BACKPACK.get()).build())))));
        JUMP_PACK = createAndPut(metadataMap, TGItems.JUMP_PACK, (id, metadata) -> metadata
                .withLangName("Jump Pack")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.JUMP_PACK.get(),
                        1,
                        builder -> builder
                            .define('P', ForgeTagExtensions.Items.PLATES_IRON)
                            .define('N', Tags.Items.NUGGETS_IRON)
                            .define('T', TGItems.COMPRESSED_AIR_TANK.get())
                            .define('B', TGItems.GLIDER_BACKPACK.get())
                            .pattern("N N")
                            .pattern("PBP")
                            .pattern("T T")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.COMPRESSED_AIR_TANK.get()).build())))));
        OXYGEN_TANKS = createAndPut(metadataMap, TGItems.OXYGEN_TANKS, (id, metadata) -> metadata
                .withLangName("Oxygen Tanks")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.OXYGEN_TANKS.get(),
                        1,
                        builder -> builder
                            .define('I', ForgeTagExtensions.Items.PLATES_IRON)
                            .define('P', ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .define('T', TGItems.COMPRESSED_AIR_TANK.get())
                            .pattern("P P")
                            .pattern("TIT")
                            .pattern("P P")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.COMPRESSED_AIR_TANK.get()).build())))));
        NIGHT_VISION_GOGGLES = createAndPut(metadataMap, TGItems.NIGHT_VISION_GOGGLES, (id, metadata) -> metadata
                .withLangName("Night Vision Goggles")
                .addRecipe(RecipeMetadata.fromBuilder(
                        id.withSuffix("_depleted"),
                        () -> {
                            CompoundTag tag = new CompoundTag();
                            tag.putShort("Energy", (short)0);
                            return new ShapedRecipeBuilderNbtExtension(RecipeCategory.MISC,
                                        TGItems.NIGHT_VISION_GOGGLES.get(),
                                        1,
                                        tag)
                                    .define('P', ForgeTagExtensions.Items.PLATES_RUBBER)
                                    .define('H', TGItems.HEAVY_CLOTH.get())
                                    .define('C', TGItems.CIRCUIT_BOARD.get())
                                    .define('G', Tags.Items.GLASS)
                                    .define('I', Tags.Items.INGOTS_IRON)
                                    .define('B', TGItems.DEPLETED_REDSTONE_BATTERY.get())
                                    .pattern("PHP")
                                    .pattern("CBC")
                                    .pattern("GIG")
                                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                            ItemPredicate.Builder.item().of(TGItems.DEPLETED_REDSTONE_BATTERY.get()).build()));
                        }))
                .addRecipe(RecipeMetadata.fromBuilder(
                        id,
                        () -> {
                            CompoundTag tag = new CompoundTag();
                            tag.putShort("Energy", (short)24000);
                            return new ShapedRecipeBuilderNbtExtension(RecipeCategory.MISC,
                                        TGItems.NIGHT_VISION_GOGGLES.get(),
                                        1,
                                        tag)
                                    .define('P', ForgeTagExtensions.Items.PLATES_RUBBER)
                                    .define('H', TGItems.HEAVY_CLOTH.get())
                                    .define('C', TGItems.CIRCUIT_BOARD.get())
                                    .define('G', Tags.Items.GLASS)
                                    .define('I', Tags.Items.INGOTS_IRON)
                                    .define('B', TGItems.REDSTONE_BATTERY.get())
                                    .pattern("PHP")
                                    .pattern("CBC")
                                    .pattern("GIG")
                                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                            ItemPredicate.Builder.item().of(TGItems.REDSTONE_BATTERY.get()).build()));
                        })));
        JETPACK = createAndPut(metadataMap, TGItems.JETPACK, (id, metadata) -> metadata
                .withLangName("Jetpack")
                .addRecipe(RecipeMetadata.shaped(
                        id.withSuffix("_from_jumppack"),
                        RecipeCategory.MISC,
                        TGItems.OXYGEN_TANKS.get(),
                        1,
                        builder -> builder
                            .define('F', TGItems.FUEL_TANK.get())
                            .define('P', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .define('M', TGItems.OBSIDIAN_STEEL_MECHANICAL_PARTS.get())
                            .define('B', TGItems.JUMP_PACK.get())
                            .define('R', TGItems.ROCKET.get())
                            .pattern("F F")
                            .pattern("PBP")
                            .pattern("RMR")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.JUMP_PACK.get()).build()))))
                .addRecipe(RecipeMetadata.shaped(
                        id.withSuffix("_from_glider"),
                        RecipeCategory.MISC,
                        TGItems.OXYGEN_TANKS.get(),
                        1,
                        builder -> builder
                            .define('F', TGItems.FUEL_TANK.get())
                            .define('P', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .define('M', TGItems.OBSIDIAN_STEEL_MECHANICAL_PARTS.get())
                            .define('B', TGItems.GLIDER.get())
                            .define('R', TGItems.ROCKET.get())
                            .pattern("F F")
                            .pattern("PBP")
                            .pattern("RMR")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.GLIDER.get()).build())))));
        OXYGEN_MASK = createAndPut(metadataMap, TGItems.OXYGEN_MASK, (id, metadata) -> metadata
                .withLangName("Oxygen Mask")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.OXYGEN_TANKS.get(),
                        1,
                        builder -> builder
                            .define('R', ForgeTagExtensions.Items.PLATES_RUBBER)
                            .define('P', ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .pattern(" P ")
                            .pattern("RPR")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_PLASTIC).build())))));
        TACTICAL_MASK = createAndPut(metadataMap, TGItems.TACTICAL_MASK, (id, metadata) -> metadata
                .withLangName("Tactical Mask")
                .addRecipe(RecipeMetadata.shaped(
                        id.withSuffix("_oxygenmask_nv"),
                        RecipeCategory.MISC,
                        TGItems.TACTICAL_MASK.get(),
                        1,
                        builder -> builder
                            .define('N', TGItems.NIGHT_VISION_GOGGLES.get())
                            .define('O', TGItems.OXYGEN_MASK.get())
                            .define('G', TGItems.GAS_MASK.get())
                            .define('P', ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .define('E', TGItems.ENERGY_CELL.get())
                            .define('C', TGItems.CIRCUIT_BOARD.get())
                            .pattern("OGN")
                            .pattern("CPC")
                            .pattern("PEP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.GAS_MASK.get()).build()))))
                .addRecipe(RecipeMetadata.shaped(
                        id.withSuffix("_nv_oxygenmask"),
                        RecipeCategory.MISC,
                        TGItems.TACTICAL_MASK.get(),
                        1,
                        builder -> builder
                            .define('N', TGItems.NIGHT_VISION_GOGGLES.get())
                            .define('O', TGItems.OXYGEN_MASK.get())
                            .define('G', TGItems.GAS_MASK.get())
                            .define('P', ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .define('E', TGItems.ENERGY_CELL.get())
                            .define('C', TGItems.CIRCUIT_BOARD.get())
                            .pattern("NGO")
                            .pattern("CPC")
                            .pattern("PEP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.GAS_MASK.get()).build())))));
        ANTI_GRAVITY_DEVICE = createAndPut(metadataMap, TGItems.ANTI_GRAVITY_DEVICE, (id, metadata) -> metadata
                .withLangName("Anti-Gravity Device")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.ANTI_GRAVITY_DEVICE.get(),
                        1,
                        builder -> builder
                            .define('B', TGItems.GLIDER_BACKPACK.get())
                            .define('G', TGItems.ANTI_GRAVITY_CORE.get())
                            .define('M', TGItems.CARBON_MECHANICAL_PARTS.get())
                            .define('P', ForgeTagExtensions.Items.PLATES_TITANIUM)
                            .define('N', TGItems.NUCLEAR_POWER_CELL.get())
                            .define('C', TGItems.ELITE_CIRCUIT_BOARD.get())
                            .pattern("PNP")
                            .pattern("CGC")
                            .pattern("MBM")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.ANTI_GRAVITY_CORE.get()).build())))));
        COMBAT_KNIFE = createAndPut(metadataMap, TGItems.COMBAT_KNIFE, (id, metadata) -> metadata
                .withLangName("Combat Knife")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.COMBAT_KNIFE.get(),
                        1,
                        builder -> builder
                            .define('S', ForgeTagExtensions.Items.PLATES_STEEL)
                            .define('P', ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .pattern(" S")
                            .pattern("P ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_STEEL).build()))))
                .addRecipe(RecipeMetadata.shaped(
                        id.withSuffix("_flipped"),
                        RecipeCategory.MISC,
                        TGItems.COMBAT_KNIFE.get(),
                        1,
                        builder -> builder
                            .define('S', ForgeTagExtensions.Items.PLATES_STEEL)
                            .define('P', ForgeTagExtensions.Items.PLATES_PLASTIC)
                            .pattern("S ")
                            .pattern(" P")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_STEEL).build())))));
        CROWBAR = createAndPut(metadataMap, TGItems.CROWBAR, (id, metadata) -> metadata
                .withLangName("Crowbar")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.CROWBAR.get(),
                        1,
                        builder -> builder
                            .define('N', ForgeTagExtensions.Items.NUGGETS_STEEL)
                            .define('P', ForgeTagExtensions.Items.PLATES_STEEL)
                            .pattern(" NP")
                            .pattern(" P ")
                            .pattern("P  ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_STEEL).build()))))
                .addRecipe(RecipeMetadata.shaped(
                        id.withSuffix("_flipped"),
                        RecipeCategory.MISC,
                        TGItems.CROWBAR.get(),
                        1,
                        builder -> builder
                            .define('N', ForgeTagExtensions.Items.NUGGETS_STEEL)
                            .define('P', ForgeTagExtensions.Items.PLATES_STEEL)
                            .pattern("PN ")
                            .pattern(" P ")
                            .pattern("  P")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.PLATES_STEEL).build())))));
        RIOT_SHIELD = createAndPut(metadataMap, TGItems.RIOT_SHIELD, (id, metadata) -> metadata
                .withLangName("Riot Shield")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.RIOT_SHIELD.get(),
                        1,
                        builder -> builder
                            .define('G', Tags.Items.GLASS)
                            .define('I', ForgeTagExtensions.Items.INGOTS_STEEL)
                            .define('S', Items.SHIELD)
                            .pattern("IGI")
                            .pattern("ISI")
                            .pattern("IGI")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.SHIELD).build())))));
        BALLISTIC_SHIELD = createAndPut(metadataMap, TGItems.BALLISTIC_SHIELD, (id, metadata) -> metadata
                .withLangName("Ballistic Shield")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.BALLISTIC_SHIELD.get(),
                        1,
                        builder -> builder
                            .define('G', Tags.Items.GLASS_PANES)
                            .define('P', ForgeTagExtensions.Items.PLATES_OBSIDIAN_STEEL)
                            .define('S', Items.SHIELD)
                            .pattern("PGP")
                            .pattern("PSP")
                            .pattern("PPP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.SHIELD).build())))));
        ADVANCED_SHIELD = createAndPut(metadataMap, TGItems.ADVANCED_SHIELD, (id, metadata) -> metadata
                .withLangName("Advanced Shield")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.ADVANCED_SHIELD.get(),
                        1,
                        builder -> builder
                            .define('G', Tags.Items.GLASS_PANES)
                            .define('P', ForgeTagExtensions.Items.PLATES_CARBON)
                            .define('S', Items.SHIELD)
                            .pattern("PGP")
                            .pattern("PSP")
                            .pattern("PPP")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.SHIELD).build())))));
        STICK_GRENADE = createAndPut(metadataMap, TGItems.STICK_GRENADE, (id, metadata) -> metadata
                .withLangName("Stick Grenade")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.STICK_GRENADE.get(),
                        16,
                        builder -> builder
                            .define('I', Tags.Items.INGOTS_IRON)
                            .define('T', Items.TNT)
                            .define('P', ItemTags.PLANKS)
                            .pattern(" IT")
                            .pattern(" PI")
                            .pattern("I  ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.TNT).build())))));
        FRAG_GRENADE = createAndPut(metadataMap, TGItems.FRAG_GRENADE, (id, metadata) -> metadata
                .withLangName("Frag Grenade")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.FRAG_GRENADE.get(),
                        16,
                        builder -> builder
                            .define('I', ForgeTagExtensions.Items.INGOTS_STEEL)
                            .define('F', Items.FLINT_AND_STEEL)
                            .define('T', Items.TNT)
                            .pattern(" IF")
                            .pattern("ITI")
                            .pattern(" I ")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.TNT).build())))));
        POWER_HAMMER = createAndPut(metadataMap, TGItems.POWER_HAMMER, (id, metadata) -> metadata
                .withLangName("Power Hammer")
                .addRecipe(RecipeMetadata.shaped(
                        id,
                        RecipeCategory.MISC,
                        TGItems.POWER_HAMMER.get(),
                        1,
                        builder -> builder
                            .define('I', ForgeTagExtensions.Items.PLATES_IRON)
                            .define('R', TGItems.IRON_RECEIVER.get())
                            .define('M', TGItems.IRON_MECHANICAL_PARTS.get())
                            .define('P', Items.PISTON)
                            .define('C', TGItems.EMPTY_COMPRESSED_AIR_TANK.get())
                            .pattern(" M ")
                            .pattern("IPR")
                            .pattern(" MC")
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(TGItems.EMPTY_COMPRESSED_AIR_TANK.get()).build())))));
                // TODO: add support for filled canisters
        CHAINSAW = createAndPut(metadataMap, TGItems.CHAINSAW, (id, metadata) -> metadata
                .withLangName("Chainsaw"));
        MINING_DRILL = createAndPut(metadataMap, TGItems.MINING_DRILL, (id, metadata) -> metadata
                .withLangName("Mining Drill"));
        RAD_AWAY = createAndPut(metadataMap, TGItems.RAD_AWAY, (id, metadata) -> metadata
                .withLangName("RAD Away")
                .addRecipe(RecipeMetadata.chemicalLaboratory(
                        id,
                        builder -> builder
                            .addFluidInput(TGTags.Fluids.ACID, 250)
                            .addPrimaryInput(Items.NETHER_WART)
                            .addSecondaryInput(Items.GLISTERING_MELON_SLICE)
                            .addTankInput(TGItems.INFUSION_BAG)
                            .withEnergyDrainPerTick(25)
                            .withProcessingTime(100)
                            .withItemResult(TGItems.RAD_AWAY)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.NETHER_WART).build())))));
        ANTI_RAD_PILLS = createAndPut(metadataMap, TGItems.ANTI_RAD_PILLS, (id, metadata) -> metadata
                .withLangName("Anti-RAD Pills")
                .addRecipe(RecipeMetadata.chemicalLaboratory(
                        id,
                        builder -> builder
                            .addFluidInput(ForgeMod.MILK, 1000)
                            .addPrimaryInput(Items.SUGAR, 4)
                            .addSecondaryInput(Items.GLISTERING_MELON_SLICE)
                            .addTankInput(Items.GLASS_BOTTLE)
                            .withEnergyDrainPerTick(20)
                            .withProcessingTime(100)
                            .withItemResult(TGItems.ANTI_RAD_PILLS, 4)
                            .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                    ItemPredicate.Builder.item().of(Items.SUGAR).build())))));

        SOLDIER_HELMET = createAndPut(metadataMap, TGItems.SOLDIER_HELMET, (id, metadata) -> metadata
                .withLangName("Soldier Helmet"));
        SOLDIER_CHESTPLATE = createAndPut(metadataMap, TGItems.SOLDIER_CHESTPLATE, (id, metadata) -> metadata
                .withLangName("Soldier Vest"));
        SOLDIER_LEGGINGS = createAndPut(metadataMap, TGItems.SOLDIER_LEGGINGS, (id, metadata) -> metadata
                .withLangName("Soldier Pants"));
        SOLDIER_BOOTS = createAndPut(metadataMap, TGItems.SOLDIER_BOOTS, (id, metadata) -> metadata
                .withLangName("Soldier Boots"));

        BANDIT_HELMET = createAndPut(metadataMap, TGItems.BANDIT_HELMET, (id, metadata) -> metadata
                .withLangName("Bandit Mask"));
        BANDIT_CHESTPLATE = createAndPut(metadataMap, TGItems.BANDIT_CHESTPLATE, (id, metadata) -> metadata
                .withLangName("Bandit Jacket"));
        BANDIT_LEGGINGS = createAndPut(metadataMap, TGItems.BANDIT_LEGGINGS, (id, metadata) -> metadata
                .withLangName("Bandit Pants"));
        BANDIT_BOOTS = createAndPut(metadataMap, TGItems.BANDIT_BOOTS, (id, metadata) -> metadata
                .withLangName("Bandit Boots"));

        MINER_HELMET = createAndPut(metadataMap, TGItems.MINER_HELMET, (id, metadata) -> metadata
                .withLangName("Miner Helmet"));
        MINER_CHESTPLATE = createAndPut(metadataMap, TGItems.MINER_CHESTPLATE, (id, metadata) -> metadata
                .withLangName("Miner Jacket"));
        MINER_LEGGINGS = createAndPut(metadataMap, TGItems.MINER_LEGGINGS, (id, metadata) -> metadata
                .withLangName("Miner Pants"));
        MINER_BOOTS = createAndPut(metadataMap, TGItems.MINER_BOOTS, (id, metadata) -> metadata
                .withLangName("Miner Boots"));

        STEAM_HELMET = createAndPut(metadataMap, TGItems.STEAM_HELMET, (id, metadata) -> metadata
                .withLangName("Steam Helmet"));
        STEAM_CHESTPLATE = createAndPut(metadataMap, TGItems.STEAM_CHESTPLATE, (id, metadata) -> metadata
                .withLangName("Steam Chestplate"));
        STEAM_LEGGINGS = createAndPut(metadataMap, TGItems.STEAM_LEGGINGS, (id, metadata) -> metadata
                .withLangName("Steam Legplates"));
        STEAM_BOOTS = createAndPut(metadataMap, TGItems.STEAM_BOOTS, (id, metadata) -> metadata
                .withLangName("Steam Boots"));

        HAZMAT_HELMET = createAndPut(metadataMap, TGItems.HAZMAT_HELMET, (id, metadata) -> metadata
                .withLangName("Hazmat Suit Helmet"));
        HAZMAT_CHESTPLATE = createAndPut(metadataMap, TGItems.HAZMAT_CHESTPLATE, (id, metadata) -> metadata
                .withLangName("Hazmat Suit Chestplate"));
        HAZMAT_LEGGINGS = createAndPut(metadataMap, TGItems.HAZMAT_LEGGINGS, (id, metadata) -> metadata
                .withLangName("Hazmat Suit Leggings"));
        HAZMAT_BOOTS = createAndPut(metadataMap, TGItems.HAZMAT_BOOTS, (id, metadata) -> metadata
                .withLangName("Hazmat Suit Boots"));

        COMBAT_HELMET = createAndPut(metadataMap, TGItems.COMBAT_HELMET, (id, metadata) -> metadata
                .withLangName("Combat Helmet"));
        COMBAT_CHESTPLATE = createAndPut(metadataMap, TGItems.COMBAT_CHESTPLATE, (id, metadata) -> metadata
                .withLangName("Combat Vest"));
        COMBAT_LEGGINGS = createAndPut(metadataMap, TGItems.COMBAT_LEGGINGS, (id, metadata) -> metadata
                .withLangName("Combat Pants"));
        COMBAT_BOOTS = createAndPut(metadataMap, TGItems.COMBAT_BOOTS, (id, metadata) -> metadata
                .withLangName("Combat Boots"));

        COMMADO_HELMET = createAndPut(metadataMap, TGItems.COMMADO_HELMET, (id, metadata) -> metadata
                .withLangName("Commando Headgear"));
        COMMADO_CHESTPLATE = createAndPut(metadataMap, TGItems.COMMADO_CHESTPLATE, (id, metadata) -> metadata
                .withLangName("Commando Vest"));
        COMMADO_LEGGINGS = createAndPut(metadataMap, TGItems.COMMADO_LEGGINGS, (id, metadata) -> metadata
                .withLangName("Commando Pants"));
        COMMADO_BOOTS = createAndPut(metadataMap, TGItems.COMMADO_BOOTS, (id, metadata) -> metadata
                .withLangName("Commando Boots"));

        RANGER_VETERAN_HELMET = createAndPut(metadataMap, TGItems.RANGER_VETERAN_HELMET, (id, metadata) -> metadata
                .withLangName("Ranger Veteran Helmet"));
        RANGER_VETERAN_CHESTPLATE = createAndPut(metadataMap, TGItems.RANGER_VETERAN_CHESTPLATE, (id, metadata) -> metadata
                .withLangName("Ranger Veteran Jacket"));
        RANGER_VETERAN_LEGGINGS = createAndPut(metadataMap, TGItems.RANGER_VETERAN_LEGGINGS, (id, metadata) -> metadata
                .withLangName("Ranger Veteran Pants"));
        RANGER_VETERAN_BOOTS = createAndPut(metadataMap, TGItems.RANGER_VETERAN_BOOTS, (id, metadata) -> metadata
                .withLangName("Ranger Veteran Boots"));

        ADVANCED_COMBAT_HELMET = createAndPut(metadataMap, TGItems.ADVANCED_COMBAT_HELMET, (id, metadata) -> metadata
                .withLangName("Advanced Combat Helmet"));
        ADVANCED_COMBAT_CHESTPLATE = createAndPut(metadataMap, TGItems.ADVANCED_COMBAT_CHESTPLATE, (id, metadata) -> metadata
                .withLangName("Advanced Combat Chestplate"));
        ADVANCED_COMBAT_LEGGINGS = createAndPut(metadataMap, TGItems.ADVANCED_COMBAT_LEGGINGS, (id, metadata) -> metadata
                .withLangName("Advanced Combat Leggings"));
        ADVANCED_COMBAT_BOOTS = createAndPut(metadataMap, TGItems.ADVANCED_COMBAT_BOOTS, (id, metadata) -> metadata
                .withLangName("Advanced Combat Boots"));

        POWER_HELMET = createAndPut(metadataMap, TGItems.POWER_HELMET, (id, metadata) -> metadata
                .withLangName("Power Armor Helmet"));
        POWER_CHESTPLATE = createAndPut(metadataMap, TGItems.POWER_CHESTPLATE, (id, metadata) -> metadata
                .withLangName("Power Armor Chestplate"));
        POWER_LEGGINGS = createAndPut(metadataMap, TGItems.POWER_LEGGINGS, (id, metadata) -> metadata
                .withLangName("Power Armor Leggings"));
        POWER_BOOTS = createAndPut(metadataMap, TGItems.POWER_BOOTS, (id, metadata) -> metadata
                .withLangName("Power Armor Boots"));

        HEV_HELMET = createAndPut(metadataMap, TGItems.HEV_HELMET, (id, metadata) -> metadata
                .withLangName("HEV Suit Helmet"));
        HEV_CHESTPLATE = createAndPut(metadataMap, TGItems.HEV_CHESTPLATE, (id, metadata) -> metadata
                .withLangName("HEV Suit Chestplate"));
        HEV_LEGGINGS = createAndPut(metadataMap, TGItems.HEV_LEGGINGS, (id, metadata) -> metadata
                .withLangName("HEV Suit Leggings"));
        HEV_BOOTS = createAndPut(metadataMap, TGItems.HEV_BOOTS, (id, metadata) -> metadata
                .withLangName("HEV Suit Boots"));

        EXO_HELMET = createAndPut(metadataMap, TGItems.EXO_HELMET, (id, metadata) -> metadata
                .withLangName("Exo Suit Helmet"));
        EXO_CHESTPLATE = createAndPut(metadataMap, TGItems.EXO_CHESTPLATE, (id, metadata) -> metadata
                .withLangName("Exo Suit Chestplate"));
        EXO_LEGGINGS = createAndPut(metadataMap, TGItems.EXO_LEGGINGS, (id, metadata) -> metadata
                .withLangName("Exo Suit Leggings"));
        EXO_BOOTS = createAndPut(metadataMap, TGItems.EXO_BOOTS, (id, metadata) -> metadata
                .withLangName("Exo Suit Boots"));

        BERET = createAndPut(metadataMap, TGItems.BERET, (id, metadata) -> metadata
                .withLangName("Beret"));

        NETHER_COMBAT_HELMET = createAndPut(metadataMap, TGItems.NETHER_COMBAT_HELMET, (id, metadata) -> metadata
                .withLangName("Nether Combat Helmet"));
        NETHER_COMBAT_CHESTPLATE = createAndPut(metadataMap, TGItems.NETHER_COMBAT_CHESTPLATE, (id, metadata) -> metadata
                .withLangName("Nether Combat Chestplate"));
        NETHER_COMBAT_LEGGINGS = createAndPut(metadataMap, TGItems.NETHER_COMBAT_LEGGINGS, (id, metadata) -> metadata
                .withLangName("Nether Combat Leggings"));
        NETHER_COMBAT_BOOTS = createAndPut(metadataMap, TGItems.NETHER_COMBAT_BOOTS, (id, metadata) -> metadata
                .withLangName("Nether Combat Boots"));

        MK2_POWER_HELMET = createAndPut(metadataMap, TGItems.MK2_POWER_HELMET, (id, metadata) -> metadata
                .withLangName("Power Armor Mk2 Helmet"));
        MK2_POWER_CHESTPLATE = createAndPut(metadataMap, TGItems.MK2_POWER_CHESTPLATE, (id, metadata) -> metadata
                .withLangName("Power Armor Mk2 Chestplate"));
        MK2_POWER_LEGGINGS = createAndPut(metadataMap, TGItems.MK2_POWER_LEGGINGS, (id, metadata) -> metadata
                .withLangName("Power Armor Mk2 Leggings"));
        MK2_POWER_BOOTS = createAndPut(metadataMap, TGItems.MK2_POWER_BOOTS, (id, metadata) -> metadata
                .withLangName("Power Armor Mk2 Boots"));

        HAND_CANNON = createAndPut(metadataMap, TGItems.HAND_CANNON, (id, metadata) -> metadata
                .withLangName("Hand Cannon"));
        DOUBLE_BARREL_SHOTGUN = createAndPut(metadataMap, TGItems.DOUBLE_BARREL_SHOTGUN, (id, metadata) -> metadata
                .withLangName("Double Barrel Shotgun"));
        REVOLVER = createAndPut(metadataMap, TGItems.REVOLVER, (id, metadata) -> metadata
                .withLangName("Revolver"));
        GOLDEN_REVOLVER = createAndPut(metadataMap, TGItems.GOLDEN_REVOLVER, (id, metadata) -> metadata
                .withLangName("Golden Revolver"));
        THOMPSON_SMG = createAndPut(metadataMap, TGItems.THOMPSON_SMG, (id, metadata) -> metadata
                .withLangName("Thompson SMG"));
        AKM = createAndPut(metadataMap, TGItems.AKM, (id, metadata) -> metadata
                .withLangName("AKM"));
        BOLT_ACTION_RIFLE = createAndPut(metadataMap, TGItems.BOLT_ACTION_RIFLE, (id, metadata) -> metadata
                .withLangName("Bolt Action Rifle"));
        M4 = createAndPut(metadataMap, TGItems.M4, (id, metadata) -> metadata
                .withLangName("M4 Assault Rifle"));
        INFILTRATOR = createAndPut(metadataMap, TGItems.INFILTRATOR, (id, metadata) -> metadata
                .withLangName("The Infiltrator"));
        PISTOL = createAndPut(metadataMap, TGItems.PISTOL, (id, metadata) -> metadata
                .withLangName("Pistol"));
        COMBAT_SHOTGUN = createAndPut(metadataMap, TGItems.COMBAT_SHOTGUN, (id, metadata) -> metadata
                .withLangName("Combat Shotgun"));
        MAC10 = createAndPut(metadataMap, TGItems.MAC10, (id, metadata) -> metadata
                .withLangName("Mac 10"));
        FLAMETHROWER = createAndPut(metadataMap, TGItems.FLAMETHROWER, (id, metadata) -> metadata
                .withLangName("Flamethrower"));
        ROCKET_LAUNCHER = createAndPut(metadataMap, TGItems.ROCKET_LAUNCHER, (id, metadata) -> metadata
                .withLangName("Rocket Launcher"));
        GRIM_REAPER = createAndPut(metadataMap, TGItems.GRIM_REAPER, (id, metadata) -> metadata
                .withLangName("Grim Reaper"));
        GRENADE_LAUNCHER = createAndPut(metadataMap, TGItems.GRENADE_LAUNCHER, (id, metadata) -> metadata
                .withLangName("Grenade Launcher"));
        AUG = createAndPut(metadataMap, TGItems.AUG, (id, metadata) -> metadata
                .withLangName("AUG"));
        NETHER_BLASTER = createAndPut(metadataMap, TGItems.NETHER_BLASTER, (id, metadata) -> metadata
                .withLangName("Nether Blaster"));
        BIO_GUN = createAndPut(metadataMap, TGItems.BIO_GUN, (id, metadata) -> metadata
                .withLangName("Bio Gun"));
        TESLA_GUN = createAndPut(metadataMap, TGItems.TESLA_GUN, (id, metadata) -> metadata
                .withLangName("Tesla Gun"));
        LIGHT_MACHINE_GUN = createAndPut(metadataMap, TGItems.LIGHT_MACHINE_GUN, (id, metadata) -> metadata
                .withLangName("Light Machine Gun"));
        MINIGUN = createAndPut(metadataMap, TGItems.MINIGUN, (id, metadata) -> metadata
                .withLangName("Minigun"));
        AS50 = createAndPut(metadataMap, TGItems.AS50, (id, metadata) -> metadata
                .withLangName("AS50 Sniper Rifle"));
        VECTOR = createAndPut(metadataMap, TGItems.VECTOR, (id, metadata) -> metadata
                .withLangName("Vector"));
        SCAR = createAndPut(metadataMap, TGItems.SCAR, (id, metadata) -> metadata
                .withLangName("SCAR"));
        LASER_RIFLE = createAndPut(metadataMap, TGItems.LASER_RIFLE, (id, metadata) -> metadata
                .withLangName("Laser Rifle"));
        BLASTER_RIFLE = createAndPut(metadataMap, TGItems.BLASTER_RIFLE, (id, metadata) -> metadata
                .withLangName("Blaster Rifle"));
        BLASTER_SHOTGUN = createAndPut(metadataMap, TGItems.BLASTER_SHOTGUN, (id, metadata) -> metadata
                .withLangName("Blaster Shotgun"));
        SONIC_RIFLE = createAndPut(metadataMap, TGItems.SONIC_RIFLE, (id, metadata) -> metadata
                .withLangName("Sonic Rifle"));
        PDW = createAndPut(metadataMap, TGItems.PDW, (id, metadata) -> metadata
                .withLangName("PDW"));
        PULSE_RIFLE = createAndPut(metadataMap, TGItems.PULSE_RIFLE, (id, metadata) -> metadata
                .withLangName("Pulse Rifle"));
        DEATOMIZER = createAndPut(metadataMap, TGItems.DEATOMIZER, (id, metadata) -> metadata
                .withLangName("De-Atomizer Pistol"));
        ALIEN_BLASTER = createAndPut(metadataMap, TGItems.ALIEN_BLASTER, (id, metadata) -> metadata
                .withLangName("Alien Blaster"));
        NUCLEAR_DEATH_RAY = createAndPut(metadataMap, TGItems.NUCLEAR_DEATH_RAY, (id, metadata) -> metadata
                .withLangName("Nuclear Death Ray"));
        GAUSS_RIFLE = createAndPut(metadataMap, TGItems.GAUSS_RIFLE, (id, metadata) -> metadata
                .withLangName("Gauss Rifle"));
        GUIDED_ROCKET_LAUNCHER = createAndPut(metadataMap, TGItems.GUIDED_ROCKET_LAUNCHER, (id, metadata) -> metadata
                .withLangName("Guided Rocket Launcher"));
        TFG = createAndPut(metadataMap, TGItems.TFG, (id, metadata) -> metadata
                .withLangName("TFG 10K"));
        LASER_PISTOL = createAndPut(metadataMap, TGItems.LASER_PISTOL, (id, metadata) -> metadata
                .withLangName("Laser Pistol"));
        
        METADATA_MAP = metadataMap.build();
    }
}
