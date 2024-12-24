package techguns2.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;

public record LootTableMetadata(ResourceLocation id, LootTable.Builder table, LootContextParamSet context) {
}
