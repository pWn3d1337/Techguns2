package techguns2;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.mojang.datafixers.types.Type;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import techguns2.block.entity.BioBlobBlockEntity;
import techguns2.block.entity.NPCTurretBlockEntity;
import techguns2.machine.alloy_furnace.AlloyFurnaceBlockEntity;
import techguns2.machine.ammo_press.AmmoPressBlockEntity;
import techguns2.machine.ammo_press.AmmoPressRenderer;
import techguns2.machine.charging_station.ChargingStationBlockEntity;
import techguns2.machine.chemical_laboratory.ChemicalLaboratoryBlockEntity;
import techguns2.machine.fabricator.FabricatorControllerBlockEntity;
import techguns2.machine.fabricator.FabricatorPartBlockEntity;
import techguns2.machine.grinder.GrinderBlockEntity;
import techguns2.machine.metal_press.MetalPressBlockEntity;
import techguns2.machine.reaction_chamber.ReactionChamberControllerBlockEntity;
import techguns2.machine.reaction_chamber.ReactionChamberPartBlockEntity;

public final class TGBlockEntityTypes implements TGInitializer
{
    protected TGBlockEntityTypes()
    {
    }

    @Override
    public final void setup(IEventBus eventBus)
    {
        REGISTER.register(eventBus);

        eventBus.addListener(TGBlockEntityTypes::registerEntityRendererLayers);
        eventBus.addListener(TGBlockEntityTypes::registerEntityRenderers);
    }

    private static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Techguns.MODID);

    public static final RegistryObject<BlockEntityType<BioBlobBlockEntity>> BIO_BLOB = register("bio_blob", BioBlobBlockEntity::new, null, TGBlocks.BIO_BLOB);

    public static final RegistryObject<BlockEntityType<NPCTurretBlockEntity>> NPC_TURRET = register("turret", NPCTurretBlockEntity::new, null, TGBlocks.TURRET);
    public static final RegistryObject<BlockEntityType<AmmoPressBlockEntity>> AMMO_PRESS = register("ammo_press", AmmoPressBlockEntity::new, null, TGBlocks.AMMO_PRESS);
    public static final RegistryObject<BlockEntityType<GrinderBlockEntity>> GRINDER = register("grinder", GrinderBlockEntity::new, null, TGBlocks.GRINDER);
    public static final RegistryObject<BlockEntityType<MetalPressBlockEntity>> METAL_PRESS = register("metal_press", MetalPressBlockEntity::new, null, TGBlocks.METAL_PRESS);
    public static final RegistryObject<BlockEntityType<AlloyFurnaceBlockEntity>> ALLOY_FURNACE = register("alloy_furnace", AlloyFurnaceBlockEntity::new, null, TGBlocks.ALLOY_FURNACE);
    public static final RegistryObject<BlockEntityType<ChemicalLaboratoryBlockEntity>> CHEMICAL_LABORATORY = register("chemical_laboratory", ChemicalLaboratoryBlockEntity::new, null, TGBlocks.CHEMICAL_LABORATORY);
    public static final RegistryObject<BlockEntityType<FabricatorControllerBlockEntity>> FABRICATOR_CONTROLLER = register("fabricator_controller", FabricatorControllerBlockEntity::new, null, TGBlocks.FABRICATOR_CONTROLLER);
    public static final RegistryObject<BlockEntityType<FabricatorPartBlockEntity>> FABRICATOR_PART = register("fabricator_part", FabricatorPartBlockEntity::new, null, TGBlocks.FABRICATOR_PART);
    public static final RegistryObject<BlockEntityType<ReactionChamberControllerBlockEntity>> REACTION_CHAMBER_CONTROLLER = register("reaction_chamber_controller", ReactionChamberControllerBlockEntity::new, null, TGBlocks.REACTION_CHAMBER_CONTROLLER);
    public static final RegistryObject<BlockEntityType<ReactionChamberPartBlockEntity>> REACTION_CHAMBER_PART = register("reaction_chamber_part", ReactionChamberPartBlockEntity::new, null, TGBlocks.REACTION_CHAMBER_PART);
    public static final RegistryObject<BlockEntityType<ChargingStationBlockEntity>> CHARGING_STATION = register("charging_station", ChargingStationBlockEntity::new, null, TGBlocks.CHARGING_STATION);

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(
            String name,
            BlockEntitySupplier<T> blockEntitySupplier,
            @Nullable
            Type<?> dataFixerType,
            Supplier<? extends Block> blockSupplier)
    {
        return REGISTER.register(name, () -> BlockEntityType.Builder.of(blockEntitySupplier, blockSupplier.get()).build(dataFixerType));
    }

    private static void registerEntityRendererLayers(EntityRenderersEvent.RegisterLayerDefinitions registerLayerDefinitionsEvent)
    {
        registerLayerDefinitionsEvent.registerLayerDefinition(AmmoPressRenderer.LAYER_LOCATION, AmmoPressRenderer::createLayer);
    }

    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers registerEntityRenderersEvent)
    {
        registerEntityRenderersEvent.registerBlockEntityRenderer(TGBlockEntityTypes.AMMO_PRESS.get(), AmmoPressRenderer::new);
    }

}
