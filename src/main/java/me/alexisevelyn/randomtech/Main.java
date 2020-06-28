package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.blocks.FirstBlock;
import me.alexisevelyn.randomtech.items.EdiblePower;
import me.alexisevelyn.randomtech.items.FirstItem;
import me.alexisevelyn.randomtech.items.PickaxeBase;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {
	public static final String MODID = "randomtech";

	// Items
	public static final Item FIRST_ITEM = new FirstItem(new Item.Settings().group(ItemGroup.MISC));
	public static final Item EDIBLE_POWER = new EdiblePower(new Item.Settings().group(ItemGroup.FOOD).food(Foods.EDIBLE_POWER));

	public static final Item PICKAXE_BASE = new PickaxeBase(new Item.Settings().group(ItemGroup.TOOLS));

	// Blocks
	public static final Block FIRST_BLOCK = new FirstBlock();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		//System.out.println("Hello Fabric world!");

		// Blocks
		Registry.register(Registry.BLOCK, new Identifier(MODID, "first_block"), FIRST_BLOCK);

		// Items
		Registry.register(Registry.ITEM, new Identifier(MODID, "first_item"), FIRST_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "edible_power"), EDIBLE_POWER);

		Registry.register(Registry.ITEM, new Identifier(MODID, "pickaxe_base"), PICKAXE_BASE);

		// ItemBlocks
		Registry.register(Registry.ITEM, new Identifier(MODID, "first_block"), new BlockItem(FIRST_BLOCK, new Item.Settings().group(ItemGroup.MISC)));

		// Register Fuel
		FuelRegistry.INSTANCE.add(EDIBLE_POWER, 20*3);
	}
}
