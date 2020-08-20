package me.alexisevelyn.randomtech.utility.registryhelpers.main;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.armormaterials.PoweredArmorMaterial;
import me.alexisevelyn.randomtech.blockitems.BottledDemon;
import me.alexisevelyn.randomtech.blockitems.VirtualTile;
import me.alexisevelyn.randomtech.blocks.BasicComputerBlock;
import me.alexisevelyn.randomtech.blocks.FuserBlock;
import me.alexisevelyn.randomtech.blocks.TeleporterBlock;
import me.alexisevelyn.randomtech.blocks.cables.EnergyCable;
import me.alexisevelyn.randomtech.blocks.cables.FluidCable;
import me.alexisevelyn.randomtech.blocks.cables.ItemCable;
import me.alexisevelyn.randomtech.blocks.glass.*;
import me.alexisevelyn.randomtech.blocks.metals.CobaltBlock;
import me.alexisevelyn.randomtech.blocks.ores.CobaltOre;
import me.alexisevelyn.randomtech.blocks.wires.CobaltWire;
import me.alexisevelyn.randomtech.chunkgenerators.VoidChunkGenerator;
import me.alexisevelyn.randomtech.dimensionhelpers.VoidDimensionHelper;
import me.alexisevelyn.randomtech.entities.mob.CloudDemonEntity;
import me.alexisevelyn.randomtech.entities.mob.WizardEntity;
import me.alexisevelyn.randomtech.fluids.*;
import me.alexisevelyn.randomtech.fluids.blocks.*;
import me.alexisevelyn.randomtech.guis.*;
import me.alexisevelyn.randomtech.items.TeleporterLinker;
import me.alexisevelyn.randomtech.items.armor.powered.PoweredBoots;
import me.alexisevelyn.randomtech.items.armor.powered.PoweredChestplate;
import me.alexisevelyn.randomtech.items.armor.powered.PoweredHelmet;
import me.alexisevelyn.randomtech.items.armor.powered.PoweredLeggings;
import me.alexisevelyn.randomtech.items.books.Manual;
import me.alexisevelyn.randomtech.items.ingots.CobaltIngot;
import me.alexisevelyn.randomtech.items.ingots.DeathIngot;
import me.alexisevelyn.randomtech.items.ingots.RedstoneIngot;
import me.alexisevelyn.randomtech.items.tools.powered.*;
import me.alexisevelyn.randomtech.utility.BlockEntities;
import me.alexisevelyn.randomtech.utility.Materials;
import me.alexisevelyn.randomtech.utility.Recipes;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.*;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * The type Registry helper.
 */
public class RegistryHelper {
    // Blocks
    public static final Block CLEAR_GLASS = new ClearGlass();
    public static final Block DARK_GLASS = new DarkGlass();
    public static final Block INTANGIBLE_GLASS = new IntangibleGlass();
    public static final Block DARK_INTANGIBLE_GLASS = new DarkIntangibleGlass();

    public static final Block INVERSE_INTANGIBLE_GLASS = new InverseIntangibleGlass();
    public static final Block INVERSE_DARK_INTANGIBLE_GLASS = new InverseDarkIntangibleGlass();

    public static final Block POWERED_GLASS = new PoweredGlass();

    public static final Block VIRTUAL_TILE_BLOCK = new VirtualTile.VirtualTileBlock();

    public static final Block COBALT_WIRE = new CobaltWire();

    public static final Block BOTTLED_DEMON_BLOCK = new BottledDemon.BottledDemonBlock();

    // Cables
    public static final Block ITEM_CABLE_BLOCK = new ItemCable();
    public static final Block FLUID_CABLE_BLOCK = new FluidCable();
    public static final Block ENERGY_CABLE_BLOCK = new EnergyCable();

    // Natural Cables
    private static final AbstractBlock.Settings CHORUS_CABLE_SETTINGS = FabricBlockSettings
            .of(Materials.CABLE_MATERIAL, MaterialColor.PURPLE)
            .sounds(BlockSoundGroup.WOOD) // Wood is what chorus plants use.
            .nonOpaque() // Fixes xray issue. Also allows light pass through block
            .allowsSpawning(GenericBlockHelper::never) // Allows or denies spawning
            .solidBlock(GenericBlockHelper::never) // ??? - Seems to have no apparent effect
            .suffocates(GenericBlockHelper::never) // Suffocates player
            .blockVision(GenericBlockHelper::never) // Blocks Vision inside of block
            .strength(0.3F, 0.3F)
            .ticksRandomly();

    public static final Block CHORUS_ITEM_CABLE_BLOCK = new ItemCable(CHORUS_CABLE_SETTINGS, null);
    public static final Block CHORUS_FLUID_CABLE_BLOCK = new FluidCable(CHORUS_CABLE_SETTINGS, null);
    public static final Block CHORUS_ENERGY_CABLE_BLOCK = new EnergyCable(CHORUS_CABLE_SETTINGS, null);

    // Metals
    public static final Block COBALT_BLOCK = new CobaltBlock();

    // Ores
    public static final Block COBALT_ORE = new CobaltOre();

    // Machines
    public static final Block TELEPORTER = new TeleporterBlock();
    public static final Block FUSER = new FuserBlock();
    public static final Block BASIC_COMPUTER = new BasicComputerBlock();

    // Item Groups
    public static final ItemGroup MACHINERY_GROUP = FabricItemGroupBuilder.build(
            new Identifier(Main.MODID, "machinery_group"),
            () -> new ItemStack(TELEPORTER));

    // Block Items
    public static final BlockItem BOTTLED_DEMON = new BottledDemon(BOTTLED_DEMON_BLOCK, new Item.Settings().group(MACHINERY_GROUP));
    public static final BlockItem VIRTUAL_TILE = new VirtualTile(VIRTUAL_TILE_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS));

    // Items
    public static final Item MANUAL = new Manual(new Item.Settings().group(MACHINERY_GROUP));

    public static final Item TELEPORTER_LINKER = new TeleporterLinker(new Item.Settings().group(MACHINERY_GROUP).maxCount(1)); // Max Count Sets Stack Size

    // Ingots
    public static final Item REDSTONE_INGOT = new RedstoneIngot(new Item.Settings().group(ItemGroup.MISC));
    public static final Item COBALT_INGOT = new CobaltIngot(new Item.Settings().group(ItemGroup.MISC));
    public static final Item DEATH_INGOT = new DeathIngot(new Item.Settings().group(ItemGroup.MISC));

    // Tools
    public static final Item POWERED_SWORD = new PoweredSword(new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item POWERED_PICKAXE = new PoweredPickaxe(new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item POWERED_AXE = new PoweredAxe(new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item POWERED_SHOVEL = new PoweredShovel(new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item POWERED_HOE = new PoweredHoe(new Item.Settings().group(ItemGroup.TOOLS));

    // Fluids
    public static final FlowableFluid EXPERIENCE_FLUID = new ExperienceFluid.Still();
    public static final FlowableFluid EXPERIENCE_FLUID_FLOWING = new ExperienceFluid.Flowing();
    public static final FluidBlock EXPERIENCE_FLUID_BLOCK = new ExperienceFluidBlock(EXPERIENCE_FLUID){};

    public static final FlowableFluid MAGIC_FLUID = new MagicFluid.Still();
    public static final FlowableFluid MAGIC_FLUID_FLOWING = new MagicFluid.Flowing();
    public static final FluidBlock MAGIC_FLUID_BLOCK = new MagicFluidBlock(MAGIC_FLUID){};

    public static final FlowableFluid REDSTONE_FLUID = new RedstoneFluid.Still();
    public static final FlowableFluid REDSTONE_FLUID_FLOWING = new RedstoneFluid.Flowing();
    public static final FluidBlock REDSTONE_FLUID_BLOCK = new RedstoneFluidBlock(REDSTONE_FLUID){};

    public static final FlowableFluid HONEY_FLUID = new HoneyFluid.Still();
    public static final FlowableFluid HONEY_FLUID_FLOWING = new HoneyFluid.Flowing();
    public static final FluidBlock HONEY_FLUID_BLOCK = new HoneyFluidBlock(HONEY_FLUID){};

    public static final FlowableFluid COBALT_FLUID = new CobaltFluid.Still();
    public static final FlowableFluid COBALT_FLUID_FLOWING = new CobaltFluid.Flowing();
    public static final FluidBlock COBALT_FLUID_BLOCK = new CobaltFluidBlock(COBALT_FLUID){};

    // Armor Materials
    public static final ArmorMaterial POWERED_ARMOR_MATERIAL = new PoweredArmorMaterial();

    // Buckets
    public static final Item EXPERIENCE_BUCKET = new BucketItem(EXPERIENCE_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1));
    public static final Item MAGIC_BUCKET = new BucketItem(MAGIC_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1));
    public static final Item REDSTONE_BUCKET = new BucketItem(REDSTONE_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1));
    public static final Item HONEY_BUCKET = new BucketItem(HONEY_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1));
    public static final Item COBALT_BUCKET = new BucketItem(COBALT_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1));

    // Gui Handlers - Handled By RebornCore - Needs to be run on both client and server for gui open screen to work
    public static final TeleporterGuiHandler<TeleporterGui> teleporterGuiHandler = new TeleporterGuiHandler<>();
    public static final FuserGuiHandler<FuserGui> fuserGuiHandler = new FuserGuiHandler<>();
    public static final BasicComputerGuiHandler<BasicComputerGui> basicComputerGuiHandler = new BasicComputerGuiHandler<>();

    // Gui Handlers - Handled Myself
    public static final ScreenHandlerType<ItemCableGuiHandler> itemCableScreenHandlerType = ScreenHandlerRegistry.registerExtended(new Identifier(Main.MODID, "item_cable_screen_handler"), ItemCableGuiHandler::new);

    // Entities
    public static final EntityType<WizardEntity> WIZARD = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ((EntityType.EntityFactory<WizardEntity>) WizardEntity::new))
            .dimensions(EntityDimensions.fixed(0.75f, 0.75f))
            .build();

    public static final EntityType<CloudDemonEntity> CLOUD_DEMON = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ((EntityType.EntityFactory<CloudDemonEntity>) CloudDemonEntity::new))
            .dimensions(EntityDimensions.fixed(0.75f, 0.75f))
            .build();

    // Spawn Eggs
    public static final SpawnEggItem WIZARD_SPAWN_EGG = new SpawnEggItem(WIZARD, Color.BLUE.getRGB(), Color.RED.getRGB(), new Item.Settings().group(ItemGroup.MISC));
    public static final SpawnEggItem CLOUD_DEMON_SPAWN_EGG = new SpawnEggItem(CLOUD_DEMON, Color.BLACK.getRGB(), Color.DARK_GRAY.getRGB(), new Item.Settings().group(ItemGroup.MISC));

    // Chunk Generators
    public static final Identifier voidGenerator = new Identifier(Main.MODID, "void");

    // Dimensions
    // public static final Identifier voidDimensionIdentifier = new Identifier(Main.MODID, "void");
    // private static final RegistryKey<World> voidDimension = RegistryKey.of(Registry.DIMENSION, voidDimensionIdentifier);

    // Force Load BlockEntities.java Early On
    // This is important to make sure that BlockEntities are loaded before a world is loaded
    // Yes, I know about the warning of instantiating a utility class. That's intentional for the reason stated above.
    @SuppressWarnings({"InstantiationOfUtilityClass", "unused"})
    public static final BlockEntities blockEntities = new BlockEntities();

    // Force Load Recipes.java Early On
    // This is important to make sure that recipes are loaded before a world is loaded
    // Yes, I know about the warning of instantiating a utility class. That's intentional for the reason stated above.
    @SuppressWarnings({"InstantiationOfUtilityClass", "unused"})
    public static final Recipes customRecipes = new Recipes();

    // Gamerules and Gamerule Categories
    public static final CustomGameRuleCategory RANDOM_TECH_GAMERULES_CATEGORY = new CustomGameRuleCategory(new Identifier(Main.MODID, "gamerules"), new TranslatableText(Main.MODID + ".gamerules").styled(style -> style.withBold(true).withColor(Formatting.DARK_PURPLE)));
    public static final GameRules.Key<GameRules.BooleanRule> HIDE_EXPERIMENTAL_SCREEN_GAMERULE = GameRuleRegistry.register("disableExperimentalScreen", RANDOM_TECH_GAMERULES_CATEGORY, GameRuleFactory.createBooleanRule(false));

    // Sounds
    public static final Identifier TELEPORTER_TELEPORTS_SOUND_IDENTIFIER = new Identifier(Main.MODID, "teleporter_teleports");
    public static final SoundEvent TELEPORTER_TELEPORTS_SOUND = new SoundEvent(TELEPORTER_TELEPORTS_SOUND_IDENTIFIER);

    /**
     * Register.
     */
    public void register() {
        // Blocks
        registerGeneralBlocks();
        registerOreBlocks();
        registerMachines();

        // Items
        registerGeneralItems();
        registerTools();
        registerBuckets();
        registerArmor();

        // Item Blocks
        registerGeneralItemBlocks();
        registerOreItemBlocks();
        registerMachineItemBlocks();
        registerSpecialBlockItems();

        // Fluids
        registerFluids();

        // Ores
        registerOres();

        // Fuel
        registerFuel();

        // Entities
        registerEntities();

        // Spawn Eggs
        registerSpawnEggs();

        // Chunk Generators
        registerChunkGenerators();

        // Dimensions
        registerDimensions();

        // Sounds
        registerSounds();
    }

    /**
     * Register general blocks.
     */
    private void registerGeneralBlocks() {
        // Blocks
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "clear_glass"), CLEAR_GLASS);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "dark_glass"), DARK_GLASS);

        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "intangible_glass"), INTANGIBLE_GLASS);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "dark_intangible_glass"), DARK_INTANGIBLE_GLASS);

        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "inverse_intangible_glass"), INVERSE_INTANGIBLE_GLASS);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "inverse_dark_intangible_glass"), INVERSE_DARK_INTANGIBLE_GLASS);

        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "powered_glass"), POWERED_GLASS);

        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "virtual_tile"), VIRTUAL_TILE_BLOCK);

        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "cobalt_block"), COBALT_BLOCK);

        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "cobalt_wire"), COBALT_WIRE);

        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "bottled_demon"), BOTTLED_DEMON_BLOCK);

        // Cables
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "item_cable"), ITEM_CABLE_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "fluid_cable"), FLUID_CABLE_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "energy_cable"), ENERGY_CABLE_BLOCK);

        // Natural Cables
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "chorus_item_cable"), CHORUS_ITEM_CABLE_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "chorus_fluid_cable"), CHORUS_FLUID_CABLE_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "chorus_energy_cable"), CHORUS_ENERGY_CABLE_BLOCK);
    }

    /**
     * Register ore blocks.
     */
    private void registerOreBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "cobalt_ore"), COBALT_ORE);
    }

    /**
     * Register machines.
     */
    private void registerMachines() {
        // Machines
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "teleporter"), TELEPORTER);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "fuser"), FUSER);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "basic_computer"), BASIC_COMPUTER);
    }

    /**
     * Register general items.
     */
    private void registerGeneralItems() {
        // Items
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, Manual.itemID), MANUAL);

        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "teleporter_linker"), TELEPORTER_LINKER);

        // Ingots
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "redstone_ingot"), REDSTONE_INGOT);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "cobalt_ingot"), COBALT_INGOT);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "death_ingot"), DEATH_INGOT);
    }

    /**
     * Register tools.
     */
    private void registerTools() {
        // Tools
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_sword"), POWERED_SWORD);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_pickaxe"), POWERED_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_axe"), POWERED_AXE);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_shovel"), POWERED_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_hoe"), POWERED_HOE);
    }

    /**
     * Register buckets.
     */
    private void registerBuckets() {
        // Buckets
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "experience_bucket"), EXPERIENCE_BUCKET);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "magic_bucket"), MAGIC_BUCKET);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "redstone_bucket"), REDSTONE_BUCKET);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "honey_bucket"), HONEY_BUCKET);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "cobalt_bucket"), COBALT_BUCKET);
    }

    /**
     * Register armor.
     */
    private void registerArmor() {
        // Armor
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_helmet"), new PoweredHelmet(POWERED_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_chestplate"), new PoweredChestplate(POWERED_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_leggings"), new PoweredLeggings(POWERED_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_boots"), new PoweredBoots(POWERED_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT)));
    }

    /**
     * Register general item blocks.
     */
    private void registerGeneralItemBlocks() {
        // ItemBlocks
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "clear_glass"), new BlockItem(CLEAR_GLASS, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "dark_glass"), new BlockItem(DARK_GLASS, new Item.Settings().group(ItemGroup.DECORATIONS)));

        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "intangible_glass"), new BlockItem(INTANGIBLE_GLASS, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "dark_intangible_glass"), new BlockItem(DARK_INTANGIBLE_GLASS, new Item.Settings().group(ItemGroup.DECORATIONS)));

        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "inverse_intangible_glass"), new BlockItem(INVERSE_INTANGIBLE_GLASS, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "inverse_dark_intangible_glass"), new BlockItem(INVERSE_DARK_INTANGIBLE_GLASS, new Item.Settings().group(ItemGroup.DECORATIONS)));

        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_glass"), new BlockItem(POWERED_GLASS, new Item.Settings().group(ItemGroup.DECORATIONS)));

        // Metals
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "cobalt_block"), new BlockItem(COBALT_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));

        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "cobalt_dust"), new BlockItem(COBALT_WIRE, new Item.Settings().group(ItemGroup.REDSTONE)));

        // Cables
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "item_cable"), new BlockItem(ITEM_CABLE_BLOCK, new Item.Settings().group(MACHINERY_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "fluid_cable"), new BlockItem(FLUID_CABLE_BLOCK, new Item.Settings().group(MACHINERY_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "energy_cable"), new BlockItem(ENERGY_CABLE_BLOCK, new Item.Settings().group(MACHINERY_GROUP)));

        // Natural Cables
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "chorus_item_cable"), new BlockItem(CHORUS_ITEM_CABLE_BLOCK, new Item.Settings().group(MACHINERY_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "chorus_fluid_cable"), new BlockItem(CHORUS_FLUID_CABLE_BLOCK, new Item.Settings().group(MACHINERY_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "chorus_energy_cable"), new BlockItem(CHORUS_ENERGY_CABLE_BLOCK, new Item.Settings().group(MACHINERY_GROUP)));
    }

    /**
     * Register ore item blocks.
     */
    private void registerOreItemBlocks() {
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "cobalt_ore"), new BlockItem(COBALT_ORE, new Item.Settings().group(ItemGroup.MATERIALS)));
    }

    /**
     * Register machine item blocks.
     */
    private void registerMachineItemBlocks() {
        // ItemBlocks Machines
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "teleporter"), new BlockItem(TELEPORTER, new Item.Settings().group(MACHINERY_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "fuser"), new BlockItem(FUSER, new Item.Settings().group(MACHINERY_GROUP)));
        // Registry.register(Registry.ITEM, new Identifier(Main.MODID, "basic_computer"), new BlockItem(BASIC_COMPUTER, new Item.Settings().group(MACHINERY_GROUP))); // Add to Machine Group When Basic Computer is Usable
    }

    /**
     * Register special block items.
     */
    private void registerSpecialBlockItems() {
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "virtual_tile"), VIRTUAL_TILE);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "bottled_demon"), BOTTLED_DEMON);
    }

    /**
     * Register fluids.
     */
    private void registerFluids() {
        // Flowing Fluids
        Registry.register(Registry.FLUID, new Identifier(Main.MODID, "experience_flowing"), EXPERIENCE_FLUID_FLOWING);
        Registry.register(Registry.FLUID, new Identifier(Main.MODID, "magic_flowing"), MAGIC_FLUID_FLOWING);
        Registry.register(Registry.FLUID, new Identifier(Main.MODID, "redstone_flowing"), REDSTONE_FLUID_FLOWING);
        Registry.register(Registry.FLUID, new Identifier(Main.MODID, "honey_flowing"), HONEY_FLUID_FLOWING);
        Registry.register(Registry.FLUID, new Identifier(Main.MODID, "cobalt_flowing"), COBALT_FLUID_FLOWING);

        // Still Fluids
        Registry.register(Registry.FLUID, new Identifier(Main.MODID, "experience"), EXPERIENCE_FLUID);
        Registry.register(Registry.FLUID, new Identifier(Main.MODID, "magic"), MAGIC_FLUID);
        Registry.register(Registry.FLUID, new Identifier(Main.MODID, "redstone"), REDSTONE_FLUID);
        Registry.register(Registry.FLUID, new Identifier(Main.MODID, "honey"), HONEY_FLUID);
        Registry.register(Registry.FLUID, new Identifier(Main.MODID, "cobalt"), COBALT_FLUID);

        // Fluid Blocks
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "experience"), EXPERIENCE_FLUID_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "magic"), MAGIC_FLUID_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "redstone"), REDSTONE_FLUID_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "honey"), HONEY_FLUID_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "cobalt"), COBALT_FLUID_BLOCK);
    }

    /**
     * Register fuel.
     */
    private void registerFuel() {
        // Fuel
        // FuelRegistry.INSTANCE.add(EDIBLE_POWER, 20*10); // 20*3 = 0.3 Items According to REI
    }

    /**
     * Register entities.
     */
    private void registerEntities() {
        // Entity Types
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Main.MODID, "wizard"), WIZARD);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Main.MODID, "cloud_demon"), CLOUD_DEMON);

        // Default Entity Attributes
        FabricDefaultAttributeRegistry.register(WIZARD, WizardEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(CLOUD_DEMON, CloudDemonEntity.createHostileAttributes());
    }

    /**
     * Register spawn eggs.
     */
    private void registerSpawnEggs() {
        // Spawn Eggs
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "wizard_spawn_egg"), WIZARD_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "cloud_demon_spawn_egg"), CLOUD_DEMON_SPAWN_EGG);
    }

    /**
     * Register chunk generators.
     */
    private void registerChunkGenerators() {
        // Chunk Generators
        Registry.register(Registry.CHUNK_GENERATOR, voidGenerator, VoidChunkGenerator.CODEC);
    }

    /**
     * Register dimensions.
     */
    // Dimensions are actually registered as JSON files now. So, we just set up the chunk generator and player placement code.
    private void registerDimensions() {
        // This is not currently needed at the moment to register and load the dimension and chunk generator.
        // FabricDimensions.registerDefaultPlacer(voidDimension, new VoidDimensionHelper());
    }

    private void registerOres() {
        // Handle Existing Biomes
        for (Biome biome : BuiltinRegistries.BIOME) {
            handleBiome(biome);
        }

        // Handle Future Biomes
        RegistryEntryAddedCallback.event(BuiltinRegistries.BIOME).register((i, identifier, biome) -> handleBiome(biome));
    }

    /**
     * Register sounds.
     */
    private void registerSounds() {
        // Sounds
        Registry.register(Registry.SOUND_EVENT, TELEPORTER_TELEPORTS_SOUND_IDENTIFIER, TELEPORTER_TELEPORTS_SOUND);
    }

    /**
     *
     * @param biome
     */
    private void handleBiome(Biome biome) {
        // Register Cobalt Ore
        CobaltOre.addOreFeature(biome);
    }

    /**
     *
     * @param configuredFeature
     * @param feature
     * @param biome
     */
    public static void insertIntoBiome(ConfiguredFeature<?, ?> configuredFeature, GenerationStep.Feature feature, Biome biome) {
        List<List<Supplier<ConfiguredFeature<?, ?>>>> features = biome.getGenerationSettings().getFeatures();

        while (features.size() <= feature.ordinal())
            features.add(Lists.newArrayList());

        List<Supplier<ConfiguredFeature<?, ?>>> featureList = features.get(feature.ordinal());
        if (featureList instanceof ImmutableList) {
            featureList = new ArrayList<>(featureList);
            features.set(feature.ordinal(), featureList);
        }

        featureList.add(() -> configuredFeature);
    }
}
