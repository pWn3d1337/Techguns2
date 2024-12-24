package techguns2.datagen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import techguns2.Techguns;
import techguns2.datagen.providers.IItemModelProvider;
import techguns2.datagen.providers.IRecipeMetadataProvider;
import techguns2.datagen.recipes.ModRecipeProvider;

@Mod.EventBusSubscriber(modid = Techguns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators
{
    @SubscribeEvent
    public static void gatherData(@NotNull GatherDataEvent event) throws IOException
    {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        List<Object> possibleProviders = new ArrayList<>(ItemMetadatas.getAll());
        
        generator.addProvider(false, new ModRecipeProvider(packOutput, 
                possibleProviders
                    .stream()
                    .filter(x -> x instanceof IRecipeMetadataProvider)
                    .map(x -> (IRecipeMetadataProvider)x)));
        generator.addProvider(false, new ModItemModelProvider(packOutput, existingFileHelper, 
                possibleProviders
                    .stream()
                    .filter(x -> x instanceof IItemModelProvider)
                    .map(x -> (IItemModelProvider)x)));
        
        generator.run();
    }
}