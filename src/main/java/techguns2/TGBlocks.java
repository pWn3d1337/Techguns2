package techguns2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import techguns2.machine.ammo_press.AmmoPressBlock;
import techguns2.machine.charging_station.ChargingStationBlock;
import techguns2.machine.metal_press.MetalPressBlock;
import techguns2.machine.reaction_chamber.ReactionChamberControllerBlock;
import techguns2.machine.reaction_chamber.ReactionChamberPartBlock;
import techguns2.machine.chemical_laboratory.ChemicalLaboratoryBlock;
import techguns2.block.BioBlobBlock;
import techguns2.block.CrateBlock;
import techguns2.block.NPCTurretBlock;
import techguns2.block.NeonLightBlock;
import techguns2.block.NeonLightRotatableBlock;
import techguns2.block.SandBagsBlock;
import techguns2.block.TGLadderBlock;
import techguns2.block.TGLampBlock;
import techguns2.block.TGLanternBlock;
import techguns2.machine.AbstractMultiBlockMachineControllerBlockEntity;
import techguns2.machine.AbstractMultiBlockMachinePartBlock;
import techguns2.machine.AbstractMultiBlockMachinePartBlockEntity;
import techguns2.machine.alloy_furnace.AlloyFurnaceBlock;
import techguns2.machine.fabricator.FabricatorControllerBlock;
import techguns2.machine.fabricator.FabricatorPartBlock;
import techguns2.machine.grinder.GrinderBlock;

public final class TGBlocks implements TGInitializer
{

    TGBlocks()
    { }

    private static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Techguns.MODID);
    private static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Techguns.MODID);

    @Override
    public void setup(IEventBus eventBus)
    {
        BLOCK_REGISTER.register(eventBus);
        ITEM_REGISTER.register(eventBus);
    }

    static final List<RegistryObject<? extends Block>> MACHINE_BLOCKS = new ArrayList<>();

    public static final RegistryObject<Block> TIN_ORE = registerCommon(
            "tin_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .strength(3.0F, 3.0F)),
            (block) -> new BlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> DEEPSLATE_TIN_ORE = registerCommon(
            "deepslate_tin_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)
                    .strength(4.5F, 3.0F)),
            (block) -> new BlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> RAW_TIN_BLOCK = registerCommon(
            "raw_tin_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GLOW_LICHEN)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .strength(5.0F, 6.0F)),
            (block) -> new BlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> LEAD_ORE = registerCommon(
            "lead_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .strength(3.0F, 3.0F)),
            (block) -> new BlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> DEEPSLATE_LEAD_ORE = registerCommon(
            "deepslate_lead_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)
                    .strength(4.5F, 3.0F)),
            (block) -> new BlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> RAW_LEAD_BLOCK = registerCommon(
            "raw_lead_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    // TODO: Proper map color
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .strength(5.0F, 6.0F)),
            (block) -> new BlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> TITANIUM_ORE = registerCommon(
            "titanium_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .strength(3.0F, 3.0F)),
            (block) -> new BlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> DEEPSLATE_TITANIUM_ORE = registerCommon(
            "deepslate_titanium_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)
                    .strength(4.5F, 3.0F)),
            (block) -> new BlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> RAW_TITANIUM_BLOCK = registerCommon(
            "raw_titanium_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.RAW_IRON)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .strength(5.0F, 6.0F)),
            (block) -> new BlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> URANIUM_ORE = registerCommon(
            "uranium_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .strength(3.0F, 3.0F)),
            (block) -> new BlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> DEEPSLATE_URANIUM_ORE = registerCommon(
            "deepslate_uranium_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)
                    .strength(4.5F, 3.0F)),
            (block) -> new BlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> RAW_URANIUM_BLOCK = registerCommon(
            "raw_uranium_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    // TODO: Proper map color
                    .mapColor(MapColor.COLOR_GREEN)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .strength(5.0F, 6.0F)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<BioBlobBlock> BIO_BLOB = registerCommon(
            "bio_blob",
            () -> new BioBlobBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.CLAY)
                    .lightLevel(x -> 7)
                    .sound(SoundType.SLIME_BLOCK)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<SandBagsBlock> SANDBAGS = registerCommon(
            "sandbags",
            () -> new SandBagsBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .sound(SoundType.WOOL)
                    .forceSolidOn()
                    .strength(3.0F, 15.0F)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<TGLampBlock> YELLOW_LAMP = registerCommon(
            "yellow_lamp",
            () -> new TGLampBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> 15)
                    .strength(4.0f)
                    .noOcclusion()
                    .sound(SoundType.GLASS)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<TGLampBlock> WHITE_LAMP = registerCommon(
            "white_lamp",
            () -> new TGLampBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> 15)
                    .strength(4.0f)
                    .noOcclusion()
                    .sound(SoundType.GLASS)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<TGLanternBlock> LANTERN = registerCommon(
            "yellow_lantern",
            () -> new TGLanternBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> 15)
                    .strength(4.0f)
                    .noOcclusion()
                    .sound(SoundType.GLASS)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<TGLanternBlock> WHITE_LANTERN = registerCommon(
            "white_lantern",
            () -> new TGLanternBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> 15)
                    .strength(4.0f)
                    .noOcclusion()
                    .sound(SoundType.GLASS)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<TGLadderBlock> METAL_LADDER = registerCommon(
            "metal_ladder",
            () -> new TGLadderBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(6.0F)
                    .sound(SoundType.METAL)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<TGLadderBlock> SHINY_LADDER = registerCommon(
            "shiny_ladder",
            () -> new TGLadderBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(6.0F)
                    .sound(SoundType.METAL)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<TGLadderBlock> RUSTY_LADDER = registerCommon(
            "rusty_ladder",
            () -> new TGLadderBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(6.0F)
                    .sound(SoundType.METAL)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<TGLadderBlock> CARBON_LADDER = registerCommon(
            "carbon_ladder",
            () -> new TGLadderBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(6.0F)
                    .sound(SoundType.METAL)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<NeonLightRotatableBlock> NEON_LIGHT = registerCommon(
            "neon_light",
            () -> new NeonLightRotatableBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> 15)
                    .strength(4.0F)
                    .sound(SoundType.GLASS)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<NeonLightRotatableBlock> QUAD_NEON_LIGHT = registerCommon(
            "quad_neon_light",
            () -> new NeonLightRotatableBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> 15)
                    .strength(4.0F)
                    .sound(SoundType.GLASS)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<NeonLightBlock> SQUARE_NEON_LIGHT = registerCommon(
            "square_neon_light",
            () -> new NeonLightBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> 15)
                    .strength(4.0F)
                    .sound(SoundType.GLASS)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<CrateBlock> CRATE = registerCommon(
            "ammo_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.of()
                    // TODO: Properties
                    .sound(SoundType.STONE)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<CrateBlock> WEAPON_CRATE = registerCommon(
            "weapon_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.of()
                    // TODO: Properties
                    .sound(SoundType.STONE)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<CrateBlock> ARMOR_CRATE = registerCommon(
            "armor_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.of()
                    // TODO: Properties
                    .sound(SoundType.STONE)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<CrateBlock> MEDICAL_CRATE = registerCommon(
            "medical_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.of()
                    // TODO: Properties
                    .sound(SoundType.STONE)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<CrateBlock> EXPLOSIVES_CRATE = registerCommon(
            "explosives_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.of()
                    // TODO: Properties
                    .sound(SoundType.STONE)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<CrateBlock> OAK_SUPPLY_CRATE = registerCommon(
            "oak_supply_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.of()
                    // TODO: Properties
                    .sound(SoundType.STONE)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<CrateBlock> SPRUCE_SUPPLY_CRATE = registerCommon(
            "spruce_supply_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.of()
                    // TODO: Properties
                    .sound(SoundType.STONE)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<CrateBlock> BIRCH_SUPPLY_CRATE = registerCommon(
            "birch_supply_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.of()
                    // TODO: Properties
                    .sound(SoundType.STONE)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<CrateBlock> JUNGLE_SUPPLY_CRATE = registerCommon(
            "jungle_supply_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.of()
                    // TODO: Properties
                    .sound(SoundType.STONE)),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<AmmoPressBlock> AMMO_PRESS = registerMachine("ammo_press",
            () -> new AmmoPressBlock(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<MetalPressBlock> METAL_PRESS = registerMachine("metal_press",
            () -> new MetalPressBlock(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<ChemicalLaboratoryBlock> CHEMICAL_LABORATORY = registerMachine("chemical_laboratory",
            () -> new ChemicalLaboratoryBlock(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<NPCTurretBlock> TURRET = registerMachine("turret",
            () -> new NPCTurretBlock(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<Block> CAMO_BENCH = registerCommon(
            "camo_bench",
            () -> new Block(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<Block> REPAIR_BENCH = registerCommon(
            "repair_bench",
            () -> new Block(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<ChargingStationBlock> CHARGING_STATION = registerMachine("charging_station",
            () -> new ChargingStationBlock(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<AlloyFurnaceBlock> ALLOY_FURNACE = registerMachine("alloy_furnace",
            () -> new AlloyFurnaceBlock(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<GrinderBlock> GRINDER = registerMachine("grinder",
            () -> new GrinderBlock(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<Block> UPGRADE_BENCH = registerCommon(
            "upgrade_bench",
            () -> new Block(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<Block> FABRICATOR_HOUSING = registerCommon(
            "fabricator_housing",
            () -> new Block(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<Block> FABRICATOR_GLASS = registerCommon(
            "fabricator_glass",
            () -> new Block(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<FabricatorPartBlock> FABRICATOR_PART = registerMBMachinePart("fabricator_part",
            () -> new FabricatorPartBlock(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<FabricatorControllerBlock> FABRICATOR_CONTROLLER = registerMachine("fabricator_controller",
            () -> new FabricatorControllerBlock(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<Block> REACTION_CHAMBER_HOUSING = registerCommon(
            "reaction_chamber_housing",
            () -> new Block(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<Block> REACTION_CHAMBER_GLASS = registerCommon(
            "reaction_chamber_glass",
            () -> new Block(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<ReactionChamberPartBlock> REACTION_CHAMBER_PART = registerMBMachinePart("reaction_chamber_part",
            () -> new ReactionChamberPartBlock(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    public static final RegistryObject<ReactionChamberControllerBlock> REACTION_CHAMBER_CONTROLLER = registerMachine("reaction_chamber_controller",
            () -> new ReactionChamberControllerBlock(BlockBehaviour.Properties.of()),
            (block) -> new BlockItem(block, new Item.Properties()));

    private static <
            T extends AbstractMultiBlockMachinePartBlock<TPartBlockEntity, TControllerBlockEntity>,
            TPartBlockEntity extends AbstractMultiBlockMachinePartBlockEntity<TPartBlockEntity, TControllerBlockEntity>,
            TControllerBlockEntity extends AbstractMultiBlockMachineControllerBlockEntity<TPartBlockEntity, TControllerBlockEntity>
            > RegistryObject<T> registerMBMachinePart(String name, Supplier<T> blockSupplier, Function<T, Item> itemSupplier)
    {
        return registerCommon(name, blockSupplier, itemSupplier);
    }

    private static <T extends Block> RegistryObject<T> registerMachine(String name, Supplier<T> blockSupplier, Function<T, Item> itemSupplier)
    {
        var result = registerCommon(name, blockSupplier, itemSupplier);
        MACHINE_BLOCKS.add(result);
        return result;
    }

    private static <T extends Block> RegistryObject<T> registerCommon(String name, Supplier<T> blockSupplier, Function<T, Item> itemSupplier)
    {
        var result = BLOCK_REGISTER.register(name, blockSupplier);
        ITEM_REGISTER.register(name, () -> itemSupplier.apply(result.get()));
        return result;
    }

}
