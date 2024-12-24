package techguns2.datagen.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import org.jetbrains.annotations.NotNull;
import techguns2.TGItems;
import techguns2.Techguns;
import techguns2.datagen.RecipeMetadata;
import techguns2.datagen.extension.ForgeTagExtensions;
import techguns2.datagen.providers.IRecipeMetadataProvider;
import techguns2.machine.chemical_laboratory.ChemicalLaboratoryRecipe;
import techguns2.machine.reaction_chamber.LaserFocuses;
import techguns2.machine.reaction_chamber.ReactionChamberRecipe;
import techguns2.machine.reaction_chamber.ReactionRisks;

public class ModRecipeProvider extends RecipeProvider
{
    private final ArrayList<RecipeMetadata<?>> _metadatas;
    private final List<IRecipeMetadataProvider> _metadataProviders;

    public ModRecipeProvider(PackOutput output, Stream<IRecipeMetadataProvider> metadataProviders)
    {
        super(output);
        
        this._metadatas = new ArrayList<>();
        this._metadataProviders = metadataProviders.toList();
    }
    
    @Override
    public final @NotNull CompletableFuture<?> run(@NotNull CachedOutput output)
    {
        return this.collectMetadata()
                .thenCompose((x) -> super.run(output));
    }
    
    private CompletableFuture<?> collectMetadata()
    {
        CompletableFuture<?> completableFuture = CompletableFuture.runAsync(() -> {
            this._metadatas.clear();
            this.collectBuiltInMetadatas();
        });
        
        for (IRecipeMetadataProvider metadataProvider : this._metadataProviders)
        {
            completableFuture = completableFuture
                    .thenComposeAsync(x -> metadataProvider.fetchRecipes())
                    .thenAccept(this._metadatas::addAll);
        }
        
        return completableFuture;
    }
    
    private void collectBuiltInMetadatas()
    {
        {
            ResourceLocation id = new ResourceLocation(Techguns.MODID, "gunpowder_in_chemical_laboratory");
            this._metadatas.add(RecipeMetadata.fromConditional(
                    id,
                    builder -> builder
                        .addRecipe(x -> new ChemicalLaboratoryRecipe.Builder()
                                .addPrimaryInput(Tags.Items.DUSTS_REDSTONE)
                                .addSecondaryInput(Items.COAL)
                                .addFluidInput(FluidTags.WATER, 250)
                                .withEnergyDrainPerTick(5)
                                .withItemResult(Items.GUNPOWDER)
                                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                        ItemPredicate.Builder.item().of(Tags.Items.DUSTS_REDSTONE).build()))
                                .save(x))
                        .addCondition(new NotCondition(new TagEmptyCondition(ForgeTagExtensions.Items.DUST_COAL.location())))
                        .addRecipe(x -> new ChemicalLaboratoryRecipe.Builder()
                                .addPrimaryInput(Tags.Items.DUSTS_REDSTONE)
                                .addSecondaryInput(Items.COAL)
                                .addFluidInput(FluidTags.WATER, 250)
                                .withEnergyDrainPerTick(5)
                                .withItemResult(Items.GUNPOWDER)
                                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                        ItemPredicate.Builder.item().of(Tags.Items.DUSTS_REDSTONE).build()))
                                .save(x))
                        .generateAdvancement(id)
                    ));
        }

//        this._metadatas.add(RecipeMetadata.chemicalLaboratory(
//                new ResourceLocation(Techguns.MODID, "acid_in_chemical_laboratory"),
//                builder -> builder
//                    .addPrimaryInput(TGItems.BIOMASS)
//                    .addSecondaryInput(Items.GUNPOWDER)
//                    .addFluidInput(FluidTags.WATER, 1000)
//                    .withEnergyDrainPerTick(20)
//                    .withFluidResult((Fluid)TGFluids.ACID, 1000)
//                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
//                            ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))));
        
        {
            ResourceLocation id = new ResourceLocation(Techguns.MODID, "bio_fuel_in_chemical_laboratory");
            this._metadatas.add(RecipeMetadata.fromConditional(
                    id,
                    builder -> builder
                        .addCondition(new NotCondition(new TagEmptyCondition(ForgeTagExtensions.Items.BIO_FUEL.location())))
                        .addRecipe(x -> new ChemicalLaboratoryRecipe.Builder()
                                .addPrimaryInput(ForgeTagExtensions.Items.BIO_FUEL, 4)
                                .addSecondaryInput(TGItems.EMPTY_BIO_TANK)
                                .addFluidInput(FluidTags.WATER, 500)
                                .withEnergyDrainPerTick(1)
                                .withItemResult(TGItems.BIO_TANK)
                                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                                        ItemPredicate.Builder.item().of(TGItems.EMPTY_BIO_TANK.get()).build()))
                                .save(x))
                        .generateAdvancement(id)
                    ));
        }
        
//        {
//            ResourceLocation id = new ResourceLocation(Techguns.MODID, "acid_from_bio_fuel_in_chemical_laboratory");
//            this._metadatas.add(RecipeMetadata.fromConditional(
//                    id,
//                    builder -> builder
//                        .addCondition(new NotCondition(new TagEmptyCondition(ForgeTagExtensions.Items.BIO_FUEL.location())))
//                        .addRecipe(x -> new ChemicalLaboratoryRecipe.Builder()
//                                .addPrimaryInput(ForgeTagExtensions.Items.BIO_FUEL, 4)
//                                .addSecondaryInput(TGItems.EMPTY_BIO_TANK)
//                                .addFluidInput(FluidTags.WATER, 1000)
//                                .withEnergyDrainPerTick(20)
//                                .withFluidResult((Fluid)TGFluids.ACID, 1000)
//                                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
//                                        ItemPredicate.Builder.item().of(ForgeTagExtensions.Items.BIO_FUEL).build()))
//                                .save(x))
//                        .generateAdvancement(id)
//                    ));
//        }
        
        this._metadatas.add(RecipeMetadata.chemicalLaboratory(
                new ResourceLocation(Techguns.MODID, "slimeball_in_chemical_laboratory"),
                builder -> builder
                    .addPrimaryInput(Tags.Items.DYES_GREEN)
                    .addSecondaryInput(TGItems.RAW_RUBBER)
                    .addFluidInput(ForgeMod.MILK, 500)
                    .withEnergyDrainPerTick(25)
                    .withItemResult(Items.SLIME_BALL)
                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(TGItems.RAW_RUBBER.get()).build()))));
        
        this._metadatas.add(RecipeMetadata.chemicalLaboratory(
                new ResourceLocation(Techguns.MODID, "leather_in_chemical_laboratory"),
                builder -> builder
                    .addPrimaryInput(Items.ROTTEN_FLESH)
                    .addFluidInput(FluidTags.WATER, 500)
                    .withEnergyDrainPerTick(15)
                    .withItemResult(Items.LEATHER)
                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(Items.ROTTEN_FLESH).build()))));
        
        this._metadatas.add(RecipeMetadata.chemicalLaboratory(
                new ResourceLocation(Techguns.MODID, "clay_in_chemical_laboratory"),
                builder -> builder
                    .addPrimaryInput(Blocks.DIRT)
                    .addSecondaryInput(Blocks.GRAVEL)
                    .addFluidInput(FluidTags.WATER, 250)
                    .withEnergyDrainPerTick(5)
                    .withItemResult(Blocks.CLAY, 2)
                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(Blocks.DIRT).build()))));
        
        this._metadatas.add(RecipeMetadata.reactionChamber(
                new ResourceLocation(Techguns.MODID, "wheat_in_chemical_laboratory"),
                builder -> builder
                    .addInputItem(Items.WHEAT_SEEDS)
                    .addInputFluid(FluidTags.WATER, 1000)
                    .withLaserFocus(LaserFocuses.UV_EMITTER)
                    .withResult(Items.WHEAT)
                    .withResult(Items.WHEAT_SEEDS, 2)
                    .withFluidLevel(1)
                    .withCycles(10, 5)
                    .withStartingIntensity(3)
                    .withInstability(0.5f, 1)
                    .withRisk(ReactionRisks.SMALL_EXPLOSION)
                    .withTicksPerCycle(60)
                    .withEnergyDrainPerTick(50)
                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(Items.WHEAT_SEEDS).build()))));
    }

    @Override
    protected final void buildRecipes(@NotNull Consumer<FinishedRecipe> builder)
    {
        for (RecipeMetadata<?> recipeMetadata : this._metadatas)
        {
            recipeMetadata.save(builder);
        }
    }

}
