package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.blocks.FirstBlock;
import me.alexisevelyn.randomtech.items.FirstItem;
import net.fabricmc.api.ModInitializer;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {

	public static final Item FIRST_ITEM = new FirstItem(new Item.Settings().group(ItemGroup.MISC));
	public static final Block FIRST_BLOCK = new FirstBlock();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		//System.out.println("Hello Fabric world!");

		// Blocks
		Registry.register(Registry.BLOCK, new Identifier("randomtech", "first_block"), FIRST_BLOCK);

		// Items
		Registry.register(Registry.ITEM, new Identifier("randomtech", "first_item"), FIRST_ITEM);

		// ItemBlocks
		Registry.register(Registry.ITEM, new Identifier("randomtech", "first_block"), new BlockItem(FIRST_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
	}
}
