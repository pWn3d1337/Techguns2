package techguns2.datagen;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.data.recipes.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import techguns2.machine.alloy_furnace.AlloyFurnaceRecipe;
import techguns2.machine.ammo_press.AmmoPressRecipe;
import techguns2.machine.charging_station.SimpleChargingStationRecipe;
import techguns2.machine.chemical_laboratory.ChemicalLaboratoryRecipe;
import techguns2.machine.fabricator.FabricatorRecipe;
import techguns2.machine.grinder.ChancedGrinderRecipe;
import techguns2.machine.grinder.GrinderRecipe;
import techguns2.machine.metal_press.MetalPressRecipe;
import techguns2.machine.reaction_chamber.ReactionChamberRecipe;

public final class RecipeMetadata<T>
{
    public final ResourceLocation id;
    private final Supplier<T> recipeBuilderFactory;
    @Nullable
    private T recipeBuilder;
    private final BiConsumer<RecipeMetadata<T>, Consumer<FinishedRecipe>> recipeSaver;

    public RecipeMetadata(ResourceLocation id, Supplier<T> recipeBuilderFactory, BiConsumer<RecipeMetadata<T>, Consumer<FinishedRecipe>> recipeSaver)
    {
        this.id = id;
        this.recipeBuilderFactory = recipeBuilderFactory;
        this.recipeSaver = recipeSaver;
    }
    
    public void save(Consumer<FinishedRecipe> writer)
    {
        this.recipeSaver.accept(this, writer);
    }
    
    public T setupRecipeBuilder()
    {
        if (this.recipeBuilder == null)
        {
            this.recipeBuilder = this.recipeBuilderFactory.get();
        }
        
        return this.recipeBuilder;
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ShapelessRecipeBuilder> shapeless(
            ResourceLocation id,
            RecipeCategory recipeCategory,
            ItemLike result,
            Consumer<ShapelessRecipeBuilder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = ShapelessRecipeBuilder.shapeless(recipeCategory, result);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ShapelessRecipeBuilder> shapeless(
            ResourceLocation id,
            RecipeCategory recipeCategory,
            RegistryObject<? extends ItemLike> resultObject,
            Consumer<ShapelessRecipeBuilder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = ShapelessRecipeBuilder.shapeless(recipeCategory, resultObject.get());
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ShapelessRecipeBuilder> shapeless(
            ResourceLocation id,
            RecipeCategory recipeCategory,
            ItemLike result,
            int amount,
            Consumer<ShapelessRecipeBuilder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = ShapelessRecipeBuilder.shapeless(recipeCategory, result, amount);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ShapelessRecipeBuilder> shapeless(
            ResourceLocation id,
            RecipeCategory recipeCategory,
            RegistryObject<? extends ItemLike> resultObject,
            int amount,
            Consumer<ShapelessRecipeBuilder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = ShapelessRecipeBuilder.shapeless(recipeCategory, resultObject.get(), amount);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ShapedRecipeBuilder> shaped(
            ResourceLocation id,
            RecipeCategory recipeCategory,
            ItemLike result,
            Consumer<ShapedRecipeBuilder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = ShapedRecipeBuilder.shaped(recipeCategory, result);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ShapedRecipeBuilder> shaped(
            ResourceLocation id,
            RecipeCategory recipeCategory,
            RegistryObject<? extends ItemLike> resultObject,
            Consumer<ShapedRecipeBuilder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = ShapedRecipeBuilder.shaped(recipeCategory, resultObject.get());
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ShapedRecipeBuilder> shaped(
            ResourceLocation id,
            RecipeCategory recipeCategory,
            ItemLike result,
            int amount,
            Consumer<ShapedRecipeBuilder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = ShapedRecipeBuilder.shaped(recipeCategory, result, amount);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ShapedRecipeBuilder> shaped(
            ResourceLocation id,
            RecipeCategory recipeCategory,
            RegistryObject<? extends ItemLike> resultObject,
            int amount,
            Consumer<ShapedRecipeBuilder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = ShapedRecipeBuilder.shaped(recipeCategory, resultObject.get(), amount);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    

    @Contract(value = "_, _, _, _, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<SimpleCookingRecipeBuilder> genericCooking(
            ResourceLocation id,
            Ingredient input,
            RecipeCategory category,
            ItemLike result,
            float experience,
            int ticks,
            RecipeSerializer<? extends AbstractCookingRecipe> serializer,
            Consumer<SimpleCookingRecipeBuilder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = SimpleCookingRecipeBuilder.generic(
                    input,
                    category,
                    result,
                    experience,
                    ticks,
                    serializer);
            recipeBuilder.accept(builder);
            return builder;
        });
    }

    @Contract(value = "_, _, _, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<SimpleCookingRecipeBuilder> campfireCooking(
            ResourceLocation id,
            Ingredient input,
            RecipeCategory category,
            ItemLike result,
            float experience,
            int ticks,
            Consumer<SimpleCookingRecipeBuilder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = SimpleCookingRecipeBuilder.campfireCooking(
                    input,
                    category,
                    result,
                    experience,
                    ticks);
            recipeBuilder.accept(builder);
            return builder;
        });
    }

    @Contract(value = "_, _, _, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<SimpleCookingRecipeBuilder> blasting(
            ResourceLocation id,
            Ingredient input,
            RecipeCategory category,
            ItemLike result,
            float experience,
            int ticks,
            Consumer<SimpleCookingRecipeBuilder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = SimpleCookingRecipeBuilder.blasting(
                    input,
                    category,
                    result,
                    experience,
                    ticks);
            recipeBuilder.accept(builder);
            return builder;
        });
    }

    @Contract(value = "_, _, _, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<SimpleCookingRecipeBuilder> smelting(
            ResourceLocation id,
            Ingredient input,
            RecipeCategory category,
            ItemLike result,
            float experience,
            int ticks,
            Consumer<SimpleCookingRecipeBuilder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = SimpleCookingRecipeBuilder.smelting(
                    input,
                    category,
                    result,
                    experience,
                    ticks);
            recipeBuilder.accept(builder);
            return builder;
        });
    }

    @Contract(value = "_, _, _, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<SimpleCookingRecipeBuilder> smoking(
            ResourceLocation id,
            Ingredient input,
            RecipeCategory category,
            ItemLike result,
            float experience,
            int ticks,
            Consumer<SimpleCookingRecipeBuilder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = SimpleCookingRecipeBuilder.smoking(
                    input,
                    category,
                    result,
                    experience,
                    ticks);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AmmoPressRecipe.Builder> ammoPress(
            ResourceLocation id,
            Consumer<AmmoPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AmmoPressRecipe.Builder();
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AmmoPressRecipe.Builder> ammoPress(
            ResourceLocation id,
            ItemLike result,
            Consumer<AmmoPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AmmoPressRecipe.Builder();
            builder.withResult(result);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AmmoPressRecipe.Builder> ammoPress(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            Consumer<AmmoPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AmmoPressRecipe.Builder();
            builder.withResult(resultObject);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AmmoPressRecipe.Builder> ammoPress(
            ResourceLocation id,
            ItemLike result,
            @Nullable CompoundTag nbt,
            Consumer<AmmoPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AmmoPressRecipe.Builder();
            builder.withResult(result, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AmmoPressRecipe.Builder> ammoPress(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            @Nullable CompoundTag nbt,
            Consumer<AmmoPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AmmoPressRecipe.Builder();
            builder.withResult(resultObject, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AmmoPressRecipe.Builder> ammoPress(
            ResourceLocation id,
            ItemLike result,
            int amount,
            Consumer<AmmoPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AmmoPressRecipe.Builder();
            builder.withResult(result, amount);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AmmoPressRecipe.Builder> ammoPress(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            int amount,
            Consumer<AmmoPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AmmoPressRecipe.Builder();
            builder.withResult(resultObject, amount);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AmmoPressRecipe.Builder> ammoPress(
            ResourceLocation id,
            ItemLike result,
            int amount,
            @Nullable CompoundTag nbt,
            Consumer<AmmoPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AmmoPressRecipe.Builder();
            builder.withResult(result, amount, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AmmoPressRecipe.Builder> ammoPress(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            int amount,
            @Nullable CompoundTag nbt,
            Consumer<AmmoPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AmmoPressRecipe.Builder();
            builder.withResult(resultObject, amount, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AlloyFurnaceRecipe.Builder> alloyFurnace(
            ResourceLocation id,
            Consumer<AlloyFurnaceRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AlloyFurnaceRecipe.Builder();
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AlloyFurnaceRecipe.Builder> alloyFurnace(
            ResourceLocation id,
            ItemLike result,
            Consumer<AlloyFurnaceRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AlloyFurnaceRecipe.Builder();
            builder.withResult(result);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AlloyFurnaceRecipe.Builder> alloyFurnace(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            Consumer<AlloyFurnaceRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AlloyFurnaceRecipe.Builder();
            builder.withResult(resultObject);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AlloyFurnaceRecipe.Builder> alloyFurnace(
            ResourceLocation id,
            ItemLike result,
            @Nullable CompoundTag nbt,
            Consumer<AlloyFurnaceRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AlloyFurnaceRecipe.Builder();
            builder.withResult(result, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AlloyFurnaceRecipe.Builder> alloyFurnace(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            @Nullable CompoundTag nbt,
            Consumer<AlloyFurnaceRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AlloyFurnaceRecipe.Builder();
            builder.withResult(resultObject, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AlloyFurnaceRecipe.Builder> alloyFurnace(
            ResourceLocation id,
            ItemLike result,
            int amount,
            Consumer<AlloyFurnaceRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AlloyFurnaceRecipe.Builder();
            builder.withResult(result, amount);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AlloyFurnaceRecipe.Builder> alloyFurnace(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            int amount,
            Consumer<AlloyFurnaceRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AlloyFurnaceRecipe.Builder();
            builder.withResult(resultObject, amount);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AlloyFurnaceRecipe.Builder> alloyFurnace(
            ResourceLocation id,
            ItemLike result,
            int amount,
            @Nullable CompoundTag nbt,
            Consumer<AlloyFurnaceRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AlloyFurnaceRecipe.Builder();
            builder.withResult(result, amount, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<AlloyFurnaceRecipe.Builder> alloyFurnace(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            int amount,
            @Nullable CompoundTag nbt,
            Consumer<AlloyFurnaceRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new AlloyFurnaceRecipe.Builder();
            builder.withResult(resultObject, amount, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<SimpleChargingStationRecipe.Builder> charging(
            ResourceLocation id,
            Consumer<SimpleChargingStationRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new SimpleChargingStationRecipe.Builder();
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<SimpleChargingStationRecipe.Builder> charging(
            ResourceLocation id,
            ItemLike result,
            Consumer<SimpleChargingStationRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new SimpleChargingStationRecipe.Builder();
            builder.withResult(result);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<SimpleChargingStationRecipe.Builder> charging(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            Consumer<SimpleChargingStationRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new SimpleChargingStationRecipe.Builder();
            builder.withResult(resultObject);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<SimpleChargingStationRecipe.Builder> charging(
            ResourceLocation id,
            ItemLike result,
            @Nullable CompoundTag nbt,
            Consumer<SimpleChargingStationRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new SimpleChargingStationRecipe.Builder();
            builder.withResult(result, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<SimpleChargingStationRecipe.Builder> charging(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            @Nullable CompoundTag nbt,
            Consumer<SimpleChargingStationRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new SimpleChargingStationRecipe.Builder();
            builder.withResult(resultObject, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<SimpleChargingStationRecipe.Builder> charging(
            ResourceLocation id,
            ItemLike result,
            int amount,
            Consumer<SimpleChargingStationRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new SimpleChargingStationRecipe.Builder();
            builder.withResult(result, amount);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<SimpleChargingStationRecipe.Builder> charging(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            int amount,
            Consumer<SimpleChargingStationRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new SimpleChargingStationRecipe.Builder();
            builder.withResult(resultObject, amount);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<SimpleChargingStationRecipe.Builder> charging(
            ResourceLocation id,
            ItemLike result,
            int amount,
            @Nullable CompoundTag nbt,
            Consumer<SimpleChargingStationRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new SimpleChargingStationRecipe.Builder();
            builder.withResult(result, amount, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<SimpleChargingStationRecipe.Builder> charging(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            int amount,
            @Nullable CompoundTag nbt,
            Consumer<SimpleChargingStationRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new SimpleChargingStationRecipe.Builder();
            builder.withResult(resultObject, amount, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ChemicalLaboratoryRecipe.Builder> chemicalLaboratory(
            ResourceLocation id,
            Consumer<ChemicalLaboratoryRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new ChemicalLaboratoryRecipe.Builder();
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<FabricatorRecipe.Builder> fabricator(
            ResourceLocation id,
            Consumer<FabricatorRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new FabricatorRecipe.Builder();
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<FabricatorRecipe.Builder> fabricator(
            ResourceLocation id,
            ItemLike result,
            Consumer<FabricatorRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new FabricatorRecipe.Builder();
            builder.withResult(result);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<FabricatorRecipe.Builder> fabricator(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            Consumer<FabricatorRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new FabricatorRecipe.Builder();
            builder.withResult(resultObject);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<FabricatorRecipe.Builder> fabricator(
            ResourceLocation id,
            ItemLike result,
            @Nullable CompoundTag nbt,
            Consumer<FabricatorRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new FabricatorRecipe.Builder();
            builder.withResult(result, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<FabricatorRecipe.Builder> fabricator(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            @Nullable CompoundTag nbt,
            Consumer<FabricatorRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new FabricatorRecipe.Builder();
            builder.withResult(resultObject, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<FabricatorRecipe.Builder> fabricator(
            ResourceLocation id,
            ItemLike result,
            int amount,
            Consumer<FabricatorRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new FabricatorRecipe.Builder();
            builder.withResult(result, amount);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<FabricatorRecipe.Builder> fabricator(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            int amount,
            Consumer<FabricatorRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new FabricatorRecipe.Builder();
            builder.withResult(resultObject, amount);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<FabricatorRecipe.Builder> fabricator(
            ResourceLocation id,
            ItemLike result,
            int amount,
            @Nullable CompoundTag nbt,
            Consumer<FabricatorRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new FabricatorRecipe.Builder();
            builder.withResult(result, amount, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<FabricatorRecipe.Builder> fabricator(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            int amount,
            @Nullable CompoundTag nbt,
            Consumer<FabricatorRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new FabricatorRecipe.Builder();
            builder.withResult(resultObject, amount, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<GrinderRecipe.Builder> grinder(
            ResourceLocation id,
            Consumer<GrinderRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new GrinderRecipe.Builder();
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<GrinderRecipe.Builder> grinder(
            ResourceLocation id,
            ItemLike inputItem,
            Consumer<GrinderRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new GrinderRecipe.Builder();
            builder.addInput(inputItem);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<GrinderRecipe.Builder> grinder(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> inputItemObject,
            Consumer<GrinderRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new GrinderRecipe.Builder();
            builder.addInput(inputItemObject);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<GrinderRecipe.Builder> grinder(
            ResourceLocation id,
            TagKey<Item> inputItemTag,
            Consumer<GrinderRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new GrinderRecipe.Builder();
            builder.addInput(inputItemTag);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ChancedGrinderRecipe.Builder> grinderChanced(
            ResourceLocation id,
            Consumer<ChancedGrinderRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new ChancedGrinderRecipe.Builder();
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ChancedGrinderRecipe.Builder> grinderChanced(
            ResourceLocation id,
            ItemLike inputItem,
            Consumer<ChancedGrinderRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new ChancedGrinderRecipe.Builder();
            builder.addInput(inputItem);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ChancedGrinderRecipe.Builder> grinderChanced(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> inputItemObject,
            Consumer<ChancedGrinderRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new ChancedGrinderRecipe.Builder();
            builder.addInput(inputItemObject);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ChancedGrinderRecipe.Builder> grinderChanced(
            ResourceLocation id,
            TagKey<Item> inputItemTag,
            Consumer<ChancedGrinderRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new ChancedGrinderRecipe.Builder();
            builder.addInput(inputItemTag);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<MetalPressRecipe.Builder> metalPress(
            ResourceLocation id,
            Consumer<MetalPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new MetalPressRecipe.Builder();
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<MetalPressRecipe.Builder> metalPress(
            ResourceLocation id,
            ItemLike result,
            Consumer<MetalPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new MetalPressRecipe.Builder();
            builder.withResult(result);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<MetalPressRecipe.Builder> metalPress(
            ResourceLocation id,
            ItemLike result,
            @Nullable
            CompoundTag nbt,
            Consumer<MetalPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new MetalPressRecipe.Builder();
            builder.withResult(result, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<MetalPressRecipe.Builder> metalPress(
            ResourceLocation id,
            ItemLike result,
            int count,
            Consumer<MetalPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new MetalPressRecipe.Builder();
            builder.withResult(result, count);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<MetalPressRecipe.Builder> metalPress(
            ResourceLocation id,
            ItemLike result,
            int count,
            @Nullable
            CompoundTag nbt,
            Consumer<MetalPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new MetalPressRecipe.Builder();
            builder.withResult(result, count, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<MetalPressRecipe.Builder> metalPress(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            Consumer<MetalPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new MetalPressRecipe.Builder();
            builder.withResult(resultObject);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<MetalPressRecipe.Builder> metalPress(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            @Nullable
            CompoundTag nbt,
            Consumer<MetalPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new MetalPressRecipe.Builder();
            builder.withResult(resultObject, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<MetalPressRecipe.Builder> metalPress(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            int count,
            Consumer<MetalPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new MetalPressRecipe.Builder();
            builder.withResult(resultObject, count);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<MetalPressRecipe.Builder> metalPress(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            int count,
            @Nullable
            CompoundTag nbt,
            Consumer<MetalPressRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new MetalPressRecipe.Builder();
            builder.withResult(resultObject, count, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ReactionChamberRecipe.Builder> reactionChamber(
            ResourceLocation id,
            Consumer<ReactionChamberRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new ReactionChamberRecipe.Builder();
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ReactionChamberRecipe.Builder> reactionChamber(
            ResourceLocation id,
            ItemLike result,
            Consumer<ReactionChamberRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new ReactionChamberRecipe.Builder();
            builder.withResult(result);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ReactionChamberRecipe.Builder> reactionChamber(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            Consumer<ReactionChamberRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new ReactionChamberRecipe.Builder();
            builder.withResult(resultObject);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ReactionChamberRecipe.Builder> reactionChamber(
            ResourceLocation id,
            ItemLike result,
            @Nullable CompoundTag nbt,
            Consumer<ReactionChamberRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new ReactionChamberRecipe.Builder();
            builder.withResult(result, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ReactionChamberRecipe.Builder> reactionChamber(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            @Nullable CompoundTag nbt,
            Consumer<ReactionChamberRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new ReactionChamberRecipe.Builder();
            builder.withResult(resultObject, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ReactionChamberRecipe.Builder> reactionChamber(
            ResourceLocation id,
            ItemLike result,
            int amount,
            Consumer<ReactionChamberRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new ReactionChamberRecipe.Builder();
            builder.withResult(result, amount);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ReactionChamberRecipe.Builder> reactionChamber(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            int amount,
            Consumer<ReactionChamberRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new ReactionChamberRecipe.Builder();
            builder.withResult(resultObject, amount);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ReactionChamberRecipe.Builder> reactionChamber(
            ResourceLocation id,
            ItemLike result,
            int amount,
            @Nullable CompoundTag nbt,
            Consumer<ReactionChamberRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new ReactionChamberRecipe.Builder();
            builder.withResult(result, amount, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ReactionChamberRecipe.Builder> reactionChamber(
            ResourceLocation id,
            RegistryObject<? extends ItemLike> resultObject,
            int amount,
            @Nullable CompoundTag nbt,
            Consumer<ReactionChamberRecipe.Builder> recipeBuilder)
    {
        return RecipeMetadata.fromBuilder(id, () -> {
            var builder = new ReactionChamberRecipe.Builder();
            builder.withResult(resultObject, amount, nbt);
            recipeBuilder.accept(builder);
            return builder;
        });
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    public static <T extends RecipeBuilder> @NotNull RecipeMetadata<T> fromBuilder(ResourceLocation id, Supplier<T> builderFactory)
    {
        return new RecipeMetadata<>(id, builderFactory, RecipeMetadata::recipeBuilderSaver);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull RecipeMetadata<ConditionalRecipe.Builder> fromConditional(ResourceLocation id, Consumer<ConditionalRecipe.Builder> recipeBuilder)
    {
        return new RecipeMetadata<>(id, () -> {
            var builder = new ConditionalRecipe.Builder();
            recipeBuilder.accept(builder);
            return builder;
        }, RecipeMetadata::conditionalRecipeSaver);
    }
    
    private static void conditionalRecipeSaver(@NotNull RecipeMetadata<ConditionalRecipe.Builder> metadata, Consumer<FinishedRecipe> writer)
    {
        metadata.setupRecipeBuilder().build(writer, metadata.id);
    }
    
    private static <T extends RecipeBuilder> void recipeBuilderSaver(@NotNull RecipeMetadata<T> metadata, Consumer<FinishedRecipe> writer)
    {
        metadata.setupRecipeBuilder().save(writer, metadata.id);
    }
}
