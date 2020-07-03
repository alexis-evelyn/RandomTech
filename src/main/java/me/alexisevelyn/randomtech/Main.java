package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.armormaterials.FirstArmorMaterial;
import me.alexisevelyn.randomtech.blocks.TeleporterBlock;
import me.alexisevelyn.randomtech.guis.TeleporterGui;
import me.alexisevelyn.randomtech.guis.TeleporterGuiHandler;
import me.alexisevelyn.randomtech.items.EdiblePower;
import me.alexisevelyn.randomtech.items.TeleporterControlItem;
import me.alexisevelyn.randomtech.items.armor.ArmorBase;
import me.alexisevelyn.randomtech.items.tools.*;
import me.alexisevelyn.randomtech.modmenu.screens.MainScreen;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

// This runs on both the server and the client
public class Main implements ModInitializer {
	public static final String MODID = "randomtech";

	// Blocks
	public static final Block TELEPORTER = new TeleporterBlock();

	// Item Groups
	public static ItemGroup MACHINERY_GROUP = FabricItemGroupBuilder.build(
			new Identifier(MODID, "machinery_group"),
			() -> new ItemStack(TELEPORTER));

	// Items
	public static final Item TELEPORTER_CONTROL = new TeleporterControlItem(new Item.Settings().group(MACHINERY_GROUP).maxCount(1)); // Max Count Sets Stack Size
	public static final Item EDIBLE_POWER = new EdiblePower(new Item.Settings().group(ItemGroup.FOOD).food(Foods.EDIBLE_POWER));

	// Tools
	public static final Item SWORD_BASE = new SwordBase(new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item PICKAXE_BASE = new PickaxeBase(new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item AXE_BASE = new AxeBase(new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item SHOVEL_BASE = new ShovelBase(new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item HOE_BASE = new HoeBase(new Item.Settings().group(ItemGroup.TOOLS));

	// Armor Materials
	public static final ArmorMaterial FIRST_ARMOR_MATERIAL = new FirstArmorMaterial();

	// Gui Handlers - Needs to be run on both client and server for gui open screen to work
	public static final TeleporterGuiHandler<TeleporterGui> teleporterGuiHandler = new TeleporterGuiHandler<>();

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

		// Items
		Registry.register(Registry.ITEM, new Identifier(MODID, "teleporter_control"), TELEPORTER_CONTROL);
		Registry.register(Registry.ITEM, new Identifier(MODID, "edible_power"), EDIBLE_POWER);

		// Tools
		Registry.register(Registry.ITEM, new Identifier(MODID, "sword_base"), SWORD_BASE);
		Registry.register(Registry.ITEM, new Identifier(MODID, "pickaxe_base"), PICKAXE_BASE);
		Registry.register(Registry.ITEM, new Identifier(MODID, "axe_base"), AXE_BASE);
		Registry.register(Registry.ITEM, new Identifier(MODID, "shovel_base"), SHOVEL_BASE);
		Registry.register(Registry.ITEM, new Identifier(MODID, "hoe_base"), HOE_BASE);

		// Armor
		Registry.register(Registry.ITEM, new Identifier(MODID, "first_helmet"), new ArmorBase(FIRST_ARMOR_MATERIAL, EquipmentSlot.HEAD));
		Registry.register(Registry.ITEM, new Identifier(MODID, "first_chestplate"), new ArmorBase(FIRST_ARMOR_MATERIAL, EquipmentSlot.CHEST));
		Registry.register(Registry.ITEM, new Identifier(MODID, "first_leggings"), new ArmorBase(FIRST_ARMOR_MATERIAL, EquipmentSlot.LEGS));
		Registry.register(Registry.ITEM, new Identifier(MODID, "first_boots"), new ArmorBase(FIRST_ARMOR_MATERIAL, EquipmentSlot.FEET));

		// ItemBlocks
		Registry.register(Registry.ITEM, new Identifier(MODID, "teleporter"), new BlockItem(TELEPORTER, new Item.Settings().group(MACHINERY_GROUP)));

		// Register Fuel
		FuelRegistry.INSTANCE.add(EDIBLE_POWER, 20*10); // 20*3 = 0.3 Items According to REI
	}
}
