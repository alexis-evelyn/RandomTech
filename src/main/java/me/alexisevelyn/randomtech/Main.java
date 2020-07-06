package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.armormaterials.RedstoneArmorMaterial;
import me.alexisevelyn.randomtech.blocks.FuserBlock;
import me.alexisevelyn.randomtech.blocks.TeleporterBlock;
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
import me.alexisevelyn.randomtech.items.buckets.ExperienceBucket;
import me.alexisevelyn.randomtech.items.buckets.MagicBucket;
import me.alexisevelyn.randomtech.items.buckets.RedstoneBucket;
import me.alexisevelyn.randomtech.items.ingots.RedstoneIngot;
import me.alexisevelyn.randomtech.items.tools.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

// This runs on both the server and the client
public class Main implements ModInitializer {
	public static final String MODID = "randomtech";

	// Blocks
	public static final Block TELEPORTER = new TeleporterBlock();
	public static final Block FUSER = new FuserBlock();

	// Item Groups
	public static ItemGroup MACHINERY_GROUP = FabricItemGroupBuilder.build(
			new Identifier(MODID, "machinery_group"),
			() -> new ItemStack(TELEPORTER));

	// Items
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
	public static final FlowableFluid EXPERIENCE_FLUID = Registry.register(Registry.FLUID, new Identifier(MODID, "liquid_experience"), new ExperienceFluid.Still());
	public static final FlowableFluid EXPERIENCE_FLUID_FLOWING = Registry.register(Registry.FLUID, new Identifier(MODID, "liquid_experience_flowing"), new ExperienceFluid.Flowing());
	public static final FluidBlock EXPERIENCE_FLUID_BLOCK = new ExperienceFluidBlock(EXPERIENCE_FLUID, FabricBlockSettings.copy(Blocks.WATER)){};

	public static final FlowableFluid MAGIC_FLUID = Registry.register(Registry.FLUID, new Identifier(MODID, "liquid_magic"), new MagicFluid.Still());
	public static final FlowableFluid MAGIC_FLUID_FLOWING = Registry.register(Registry.FLUID, new Identifier(MODID, "liquid_magic_flowing"), new MagicFluid.Flowing());
	public static final FluidBlock MAGIC_FLUID_BLOCK = new MagicFluidBlock(MAGIC_FLUID, FabricBlockSettings.copy(Blocks.WATER)){};

	public static final FlowableFluid REDSTONE_FLUID = Registry.register(Registry.FLUID, new Identifier(MODID, "liquid_redstone"), new RedstoneFluid.Still());
	public static final FlowableFluid REDSTONE_FLUID_FLOWING = Registry.register(Registry.FLUID, new Identifier(MODID, "liquid_redstone_flowing"), new RedstoneFluid.Flowing());
	public static final FluidBlock REDSTONE_FLUID_BLOCK = new RedstoneFluidBlock(REDSTONE_FLUID, FabricBlockSettings.copy(Blocks.WATER)){};

	// Armor Materials
	public static final ArmorMaterial REDSTONE_ARMOR_MATERIAL = new RedstoneArmorMaterial();

	// Buckets
	public static final Item EXPERIENCE_BUCKET = Registry.register(Registry.ITEM, new Identifier(MODID, "experience_bucket"), new BucketItem(EXPERIENCE_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
	public static final Item MAGIC_BUCKET = Registry.register(Registry.ITEM, new Identifier(MODID, "magic_bucket"), new BucketItem(MAGIC_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
	public static final Item REDSTONE_BUCKET = Registry.register(Registry.ITEM, new Identifier(MODID, "redstone_bucket"), new BucketItem(REDSTONE_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));

	// Gui Handlers - Needs to be run on both client and server for gui open screen to work
	public static final TeleporterGuiHandler<TeleporterGui> teleporterGuiHandler = new TeleporterGuiHandler<>();
	public static final FuserGuiHandler<FuserGui> fuserGuiHandler = new FuserGuiHandler<>();

	// Force Load BlockEntities.java Early On
	// This is important to make sure that BlockEntities are loaded before a world is loaded
	public static final BlockEntities blockEntities = new BlockEntities();

	@Override
	public void onInitialize() {
		// Client and Server Side!!!

		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// Blocks
		Registry.register(Registry.BLOCK, new Identifier(MODID, "teleporter"), TELEPORTER);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "fuser"), FUSER);

		// Items
		Registry.register(Registry.ITEM, new Identifier(MODID, "teleporter_control"), TELEPORTER_CONTROL);
		Registry.register(Registry.ITEM, new Identifier(MODID, "edible_power"), EDIBLE_POWER);
		Registry.register(Registry.ITEM, new Identifier(MODID, "redstone_ingot"), REDSTONE_INGOT);

		// Tools
		Registry.register(Registry.ITEM, new Identifier(MODID, "powered_sword"), POWERED_SWORD);
		Registry.register(Registry.ITEM, new Identifier(MODID, "powered_pickaxe"), POWERED_PICKAXE);
		Registry.register(Registry.ITEM, new Identifier(MODID, "powered_axe"), POWERED_AXE);
		Registry.register(Registry.ITEM, new Identifier(MODID, "powered_shovel"), POWERED_SHOVEL);
		Registry.register(Registry.ITEM, new Identifier(MODID, "powered_hoe"), POWERED_HOE);

		// Armor
		Registry.register(Registry.ITEM, new Identifier(MODID, "powered_helmet"), new ArmorBase(REDSTONE_ARMOR_MATERIAL, EquipmentSlot.HEAD));
		Registry.register(Registry.ITEM, new Identifier(MODID, "powered_chestplate"), new ArmorBase(REDSTONE_ARMOR_MATERIAL, EquipmentSlot.CHEST));
		Registry.register(Registry.ITEM, new Identifier(MODID, "powered_leggings"), new ArmorBase(REDSTONE_ARMOR_MATERIAL, EquipmentSlot.LEGS));
		Registry.register(Registry.ITEM, new Identifier(MODID, "powered_boots"), new ArmorBase(REDSTONE_ARMOR_MATERIAL, EquipmentSlot.FEET));

		// ItemBlocks
		Registry.register(Registry.ITEM, new Identifier(MODID, "teleporter"), new BlockItem(TELEPORTER, new Item.Settings().group(MACHINERY_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(MODID, "fuser"), new BlockItem(FUSER, new Item.Settings().group(MACHINERY_GROUP)));

		// Register Fluids
		Registry.register(Registry.BLOCK, new Identifier(MODID, "liquid_experience"), EXPERIENCE_FLUID_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "liquid_magic"), MAGIC_FLUID_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "liquid_redstone"), REDSTONE_FLUID_BLOCK);

		// Register Fuel
		FuelRegistry.INSTANCE.add(EDIBLE_POWER, 20*10); // 20*3 = 0.3 Items According to REI
	}
}
