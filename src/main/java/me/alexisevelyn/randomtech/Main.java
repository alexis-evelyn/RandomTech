package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.armormaterials.FirstArmorMaterial;
import me.alexisevelyn.randomtech.blocks.FirstBlock;
import me.alexisevelyn.randomtech.items.EdiblePower;
import me.alexisevelyn.randomtech.items.FirstItem;
import me.alexisevelyn.randomtech.items.armor.ArmorBase;
import me.alexisevelyn.randomtech.items.tools.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import reborncore.RebornRegistry;

public class Main implements ModInitializer {
	public static final String MODID = "randomtech";

	// Blocks
	public static final Block FIRST_BLOCK = new FirstBlock();

	// Item Groups
	public static ItemGroup MACHINERY_GROUP = FabricItemGroupBuilder.build(
			new Identifier("random_tech", "machinery_group"),
			() -> new ItemStack(FIRST_BLOCK));

	// Items
	public static final Item FIRST_ITEM = new FirstItem(new Item.Settings().group(ItemGroup.MISC));
	public static final Item EDIBLE_POWER = new EdiblePower(new Item.Settings().group(ItemGroup.FOOD).food(Foods.EDIBLE_POWER));

	// Tools
	public static final Item SWORD_BASE = new SwordBase(new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item PICKAXE_BASE = new PickaxeBase(new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item AXE_BASE = new AxeBase(new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item SHOVEL_BASE = new ShovelBase(new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item HOE_BASE = new HoeBase(new Item.Settings().group(ItemGroup.TOOLS));

	// Armor Materials
	public static final ArmorMaterial FIRST_ARMOR_MATERIAL = new FirstArmorMaterial();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// Blocks
		Registry.register(Registry.BLOCK, new Identifier(MODID, "first_block"), FIRST_BLOCK);

		// Items
		Registry.register(Registry.ITEM, new Identifier(MODID, "first_item"), FIRST_ITEM);
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
		Registry.register(Registry.ITEM, new Identifier(MODID, "first_block"), new BlockItem(FIRST_BLOCK, new Item.Settings().group(MACHINERY_GROUP)));

		// Register Fuel
		FuelRegistry.INSTANCE.add(EDIBLE_POWER, 20*10); // 20*3 = 0.3 Items According to REI
	}
}
