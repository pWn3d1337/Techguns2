package techguns2.datagen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.common.collect.ImmutableList;

import net.minecraft.Util;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.EntryGroup;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import techguns2.TGBlocks;
import techguns2.TGItems;
import techguns2.Techguns;
import techguns2.datagen.providers.ILangMetadataProvider;
import techguns2.datagen.providers.ILootTableMetadataProvider;
import techguns2.datagen.providers.IRecipeMetadataProvider;
import techguns2.datagen.providers.ITagProvider;

public final class BlockMetadata<TBlock extends Block> implements ILangMetadataProvider, IRecipeMetadataProvider, ILootTableMetadataProvider, ITagProvider
{
    public final TBlock block;
    public final ResourceLocation id;
    public final String langId;
    public String langName;
    public LootTable.Builder lootTable;
    public final List<RecipeMetadata<?>> recipeMetadatas;
    public final List<TagKey<Block>> tags;
    public final List<TagKey<Item>> itemTags;
    
    public BlockMetadata(RegistryObject<TBlock> registeredBlock)
    {
        this(registeredBlock.get(), registeredBlock.getId());
    }
    
    public BlockMetadata(TBlock block, ResourceLocation id)
    {
        this.block = block;
        this.id = id;
        this.langId = Util.makeDescriptionId("block", id);
        this.langName = this.langId;
        this.lootTable = LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1f))
                        .add(LootItem.lootTableItem(this.block))
                        .when(ExplosionCondition.survivesExplosion()));
        this.recipeMetadatas = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.itemTags = new ArrayList<>();
    }
    
    @Override
    public CompletableFuture<List<LanguageMetadata>> fetchLanguageEntries()
    {
        return CompletableFuture.completedFuture(ImmutableList.of(new LanguageMetadata(this.langId, this.langName)));
    }
    
    @Override
    public CompletableFuture<List<RecipeMetadata<?>>> fetchRecipes()
    {
        return CompletableFuture.completedFuture(ImmutableList.copyOf(this.recipeMetadatas));
    }
    
    @Override
    public CompletableFuture<List<LootTableMetadata>> fetchLootTables()
    {
        return CompletableFuture.completedFuture(
                ImmutableList.of(
                        new LootTableMetadata(
                                new ResourceLocation(this.id.getNamespace(), "blocks/" + this.id.getPath()),
                                this.lootTable,
                                LootContextParamSets.BLOCK)));
    }
    
    @Override
    public CompletableFuture<List<TagKey<?>>> fetchTags()
    {
        ImmutableList.Builder<TagKey<?>> tags = ImmutableList.builder();
        for (TagKey<Block> blockTag : this.tags)
        {
            tags.add(blockTag);
        }
        
        for (TagKey<Item> itemTag : this.itemTags)
        {
            tags.add(itemTag);
        }
        
        return CompletableFuture.completedFuture(tags.build());
    }
    
    public static <TBlock extends Block> BlockMetadata<TBlock> of(RegistryObject<TBlock> block)
    {
        return new BlockMetadata<>(block);
    }
    
    
    
    private void buildLootTable()
    {
        new LootTable.Builder()
            .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(AlternativesEntry.alternatives(
                            LootItem.lootTableItem(TGBlocks.CRATE.get())
                                .when(MatchTool.toolMatches(
                                        ItemPredicate.Builder.item()
                                            .hasEnchantment(new EnchantmentPredicate(
                                                    Enchantments.SILK_TOUCH,
                                                    MinMaxBounds.Ints.atLeast(1))))),
                            EntryGroup.list(
                                    LootItem.lootTableItem(TGItems.PISTOL_ROUNDS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))),
                                    LootItem.lootTableItem(TGItems.SHOTGUN_SHELLS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))),
                                    LootItem.lootTableItem(TGItems.RIFLE_ROUNDS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))),
                                    LootItem.lootTableItem(TGItems.SNIPER_ROUNDS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))),
                                    LootItem.lootTableItem(TGItems.SMG_MAGAZINE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))),
                                    LootItem.lootTableItem(TGItems.PISTOL_MAGAZINE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))),
                                    LootItem.lootTableItem(TGItems.ASSAULT_RIFLE_MAGAZINE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))),
                                    LootItem.lootTableItem(TGItems.INCENDIARY_PISTOL_ROUNDS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))),
                                    LootItem.lootTableItem(TGItems.INCENDIARY_SHOTGUN_SHELLS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))),
                                    LootItem.lootTableItem(TGItems.INCENDIARY_RIFLE_ROUNDS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))),
                                    LootItem.lootTableItem(TGItems.INCENDIARY_SNIPER_ROUNDS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))),
                                    LootItem.lootTableItem(TGItems.INCENDIARY_SMG_MAGAZINE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))),
                                    LootItem.lootTableItem(TGItems.INCENDIARY_PISTOL_MAGAZINE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))),
                                    LootItem.lootTableItem(TGItems.INCENDIARY_ASSAULT_RIFLE_MAGAZINE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))))))
            .setRandomSequence(new ResourceLocation(Techguns.MODID, "crates/ammo"))
            .setParamSet(LootContextParamSets.BLOCK);
    }
}
