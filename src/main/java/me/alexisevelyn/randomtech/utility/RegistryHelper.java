package me.alexisevelyn.randomtech.utility;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.armormaterials.RedstoneArmorMaterial;
import me.alexisevelyn.randomtech.blocks.glass.*;
import me.alexisevelyn.randomtech.blocks.FuserBlock;
import me.alexisevelyn.randomtech.blocks.TeleporterBlock;
import me.alexisevelyn.randomtech.blocks.VirtualTile;
import me.alexisevelyn.randomtech.entities.mob.WizardEntity;
import me.alexisevelyn.randomtech.fluids.ExperienceFluid;
import me.alexisevelyn.randomtech.fluids.MagicFluid;
import me.alexisevelyn.randomtech.fluids.RedstoneFluid;
import me.alexisevelyn.randomtech.fluids.blocks.ExperienceFluidBlock;
import me.alexisevelyn.randomtech.fluids.blocks.MagicFluidBlock;
import me.alexisevelyn.randomtech.fluids.blocks.RedstoneFluidBlock;
import me.alexisevelyn.randomtech.guis.FuserGui;
import me.alexisevelyn.randomtech.guis.FuserGuiHandler;
import me.alexisevelyn.randomtech.guis.TeleporterGui;
import me.alexisevelyn.randomtech.guis.TeleporterGuiHandler;
import me.alexisevelyn.randomtech.items.EdiblePower;
import me.alexisevelyn.randomtech.items.TeleporterControlItem;
import me.alexisevelyn.randomtech.items.armor.ArmorBase;
import me.alexisevelyn.randomtech.items.books.Manual;
import me.alexisevelyn.randomtech.items.ingots.RedstoneIngot;
import me.alexisevelyn.randomtech.items.tools.*;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegistryHelper {
    // Blocks
    public static final Block CLEAR_GLASS = new ClearGlass();
    public static final Block DARK_GLASS = new DarkGlass();
    public static final Block INTANGIBLE_GLASS = new IntangibleGlass();
    public static final Block DARK_INTANGIBLE_GLASS = new DarkIntangibleGlass();

    public static final Block INVERSE_INTANGIBLE_GLASS = new InverseIntangibleGlass();
    public static final Block INVERSE_DARK_INTANGIBLE_GLASS = new InverseDarkIntangibleGlass();

    public static final Block POWERED_GLASS = new PoweredGlass();

    public static final Block VIRTUAL_TILE = new VirtualTile();

    // Machines
    public static final Block TELEPORTER = new TeleporterBlock();
    public static final Block FUSER = new FuserBlock();

    // Item Groups
    public static ItemGroup MACHINERY_GROUP = FabricItemGroupBuilder.build(
            new Identifier(Main.MODID, "machinery_group"),
            () -> new ItemStack(TELEPORTER));

    // Items
    public static final Item MANUAL = new Manual(new Item.Settings().group(MACHINERY_GROUP));

    public static final Item TELEPORTER_CONTROL = new TeleporterControlItem(new Item.Settings().group(MACHINERY_GROUP).maxCount(1)); // Max Count Sets Stack Size
    public static final Item EDIBLE_POWER = new EdiblePower(new Item.Settings().group(ItemGroup.FOOD).food(Foods.EDIBLE_POWER));

    // Ingots
    public static final Item REDSTONE_INGOT = new RedstoneIngot(new Item.Settings().group(ItemGroup.MISC));

    // Tools
    public static final Item POWERED_SWORD = new PoweredSword(new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item POWERED_PICKAXE = new PoweredPickaxe(new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item POWERED_AXE = new PoweredAxe(new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item POWERED_SHOVEL = new PoweredShovel(new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item POWERED_HOE = new PoweredHoe(new Item.Settings().group(ItemGroup.TOOLS));

    // Fluids
    public static final FlowableFluid EXPERIENCE_FLUID = Registry.register(Registry.FLUID, new Identifier(Main.MODID, "liquid_experience"), new ExperienceFluid.Still());
    public static final FlowableFluid EXPERIENCE_FLUID_FLOWING = Registry.register(Registry.FLUID, new Identifier(Main.MODID, "liquid_experience_flowing"), new ExperienceFluid.Flowing());
    public static final FluidBlock EXPERIENCE_FLUID_BLOCK = new ExperienceFluidBlock(EXPERIENCE_FLUID){};

    public static final FlowableFluid MAGIC_FLUID = Registry.register(Registry.FLUID, new Identifier(Main.MODID, "liquid_magic"), new MagicFluid.Still());
    public static final FlowableFluid MAGIC_FLUID_FLOWING = Registry.register(Registry.FLUID, new Identifier(Main.MODID, "liquid_magic_flowing"), new MagicFluid.Flowing());
    public static final FluidBlock MAGIC_FLUID_BLOCK = new MagicFluidBlock(MAGIC_FLUID){};

    public static final FlowableFluid REDSTONE_FLUID = Registry.register(Registry.FLUID, new Identifier(Main.MODID, "liquid_redstone"), new RedstoneFluid.Still());
    public static final FlowableFluid REDSTONE_FLUID_FLOWING = Registry.register(Registry.FLUID, new Identifier(Main.MODID, "liquid_redstone_flowing"), new RedstoneFluid.Flowing());
    public static final FluidBlock REDSTONE_FLUID_BLOCK = new RedstoneFluidBlock(REDSTONE_FLUID){};

    // Armor Materials
    public static final ArmorMaterial REDSTONE_ARMOR_MATERIAL = new RedstoneArmorMaterial();

    // Buckets
    public static final Item EXPERIENCE_BUCKET = Registry.register(Registry.ITEM, new Identifier(Main.MODID, "experience_bucket"), new BucketItem(EXPERIENCE_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
    public static final Item MAGIC_BUCKET = Registry.register(Registry.ITEM, new Identifier(Main.MODID, "magic_bucket"), new BucketItem(MAGIC_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
    public static final Item REDSTONE_BUCKET = Registry.register(Registry.ITEM, new Identifier(Main.MODID, "redstone_bucket"), new BucketItem(REDSTONE_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));

    // Gui Handlers - Needs to be run on both client and server for gui open screen to work
    public static final TeleporterGuiHandler<TeleporterGui> teleporterGuiHandler = new TeleporterGuiHandler<>();
    public static final FuserGuiHandler<FuserGui> fuserGuiHandler = new FuserGuiHandler<>();

    // Entities
    public static final EntityType<WizardEntity> WIZARD = Registry.register(Registry.ENTITY_TYPE, new Identifier(Main.MODID, "wizard"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ((EntityType.EntityFactory<WizardEntity>) WizardEntity::new))
                    .dimensions(EntityDimensions.fixed(0.75f, 0.75f))
                    .build()
    );

    // Force Load BlockEntities.java Early On
    // This is important to make sure that BlockEntities are loaded before a world is loaded
    public static final BlockEntities blockEntities = new BlockEntities();

    // Force Load Recipes.java Early On
    // This is important to make sure that recipes are loaded before a world is loaded
    public static final Recipes customRecipes = new Recipes();
    
    public void register() {
        // Blocks
        registerGeneralBlocks();
        registerMachines();

        // Items
        registerGeneralItems();
        registerTools();
        registerArmor();

        // Item Blocks
        registerGeneralItemBlocks();
        registerMachineItemBlocks();

        // Fluids
        registerFluids();

        // Fuel
        registerFuel();

        // Entities
        registerEntities();
    }

    protected void registerGeneralBlocks() {
        // Blocks
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "clear_glass"), CLEAR_GLASS);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "dark_glass"), DARK_GLASS);

        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "intangible_glass"), INTANGIBLE_GLASS);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "dark_intangible_glass"), DARK_INTANGIBLE_GLASS);

        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "inverse_intangible_glass"), INVERSE_INTANGIBLE_GLASS);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "inverse_dark_intangible_glass"), INVERSE_DARK_INTANGIBLE_GLASS);

        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "powered_glass"), POWERED_GLASS);

        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "virtual_tile"), VIRTUAL_TILE);
    }

    protected void registerMachines() {
        // Machines
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "teleporter"), TELEPORTER);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "fuser"), FUSER);
    }

    protected void registerGeneralItems() {
        // Items
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, Manual.itemID), MANUAL);

        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "teleporter_control"), TELEPORTER_CONTROL);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "edible_power"), EDIBLE_POWER);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "redstone_ingot"), REDSTONE_INGOT);
    }

    protected void registerTools() {
        // Tools
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_sword"), POWERED_SWORD);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_pickaxe"), POWERED_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_axe"), POWERED_AXE);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_shovel"), POWERED_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_hoe"), POWERED_HOE);
    }

    protected void registerArmor() {
        // Armor
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_helmet"), new ArmorBase(REDSTONE_ARMOR_MATERIAL, EquipmentSlot.HEAD));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_chestplate"), new ArmorBase(REDSTONE_ARMOR_MATERIAL, EquipmentSlot.CHEST));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_leggings"), new ArmorBase(REDSTONE_ARMOR_MATERIAL, EquipmentSlot.LEGS));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_boots"), new ArmorBase(REDSTONE_ARMOR_MATERIAL, EquipmentSlot.FEET));
    }

    protected void registerGeneralItemBlocks() {
        // ItemBlocks
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "clear_glass"), new BlockItem(CLEAR_GLASS, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "dark_glass"), new BlockItem(DARK_GLASS, new Item.Settings().group(ItemGroup.DECORATIONS)));

        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "intangible_glass"), new BlockItem(INTANGIBLE_GLASS, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "dark_intangible_glass"), new BlockItem(DARK_INTANGIBLE_GLASS, new Item.Settings().group(ItemGroup.DECORATIONS)));

        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "inverse_intangible_glass"), new BlockItem(INVERSE_INTANGIBLE_GLASS, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "inverse_dark_intangible_glass"), new BlockItem(INVERSE_DARK_INTANGIBLE_GLASS, new Item.Settings().group(ItemGroup.DECORATIONS)));

        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "powered_glass"), new BlockItem(POWERED_GLASS, new Item.Settings().group(ItemGroup.DECORATIONS)));

        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "virtual_tile"), new BlockItem(VIRTUAL_TILE, new Item.Settings().group(ItemGroup.DECORATIONS)));
    }

    protected void registerMachineItemBlocks() {
        // ItemBlocks Machines
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "teleporter"), new BlockItem(TELEPORTER, new Item.Settings().group(MACHINERY_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "fuser"), new BlockItem(FUSER, new Item.Settings().group(MACHINERY_GROUP)));
    }

    protected void registerFluids() {
        // Register Fluids
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "liquid_experience"), EXPERIENCE_FLUID_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "liquid_magic"), MAGIC_FLUID_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "liquid_redstone"), REDSTONE_FLUID_BLOCK);
    }

    protected void registerFuel() {
        // Register Fuel
        FuelRegistry.INSTANCE.add(EDIBLE_POWER, 20*10); // 20*3 = 0.3 Items According to REI
    }

    protected void registerEntities() {
        // Registry.register(Registry.ENTITY_TYPE, new Identifier(Main.MODID, "wizard"), WIZARD);

        FabricDefaultAttributeRegistry.register(WIZARD, WizardEntity.createMobAttributes());
    }
}
