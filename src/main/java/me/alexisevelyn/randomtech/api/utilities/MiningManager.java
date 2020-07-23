package me.alexisevelyn.randomtech.api.utilities;

import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredTool;
import me.alexisevelyn.randomtech.toolmaterials.poweredtools.PoweredToolMaterial;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class MiningManager {
    public static boolean canMine(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();

        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (playerEntity == null)
            return false;

        return canMine(playerEntity, itemStack, blockState, world, blockPos);
    }

    // TODO: Check if block requires silk touch.
    public static boolean canMine(PlayerEntity playerEntity, ItemStack toolItemStack, BlockState blockState, World world, BlockPos blockPos) {
        boolean canMine = playerEntity.isUsingEffectiveTool(blockState);
        Item item = toolItemStack.getItem();

        // This checks if the tool is dead
        if (item instanceof GenericPoweredTool && !((GenericPoweredTool) item).isUsable(toolItemStack))
            return false;

        // Checks if block needs silk touch and if so, check if tool has silk touch
        if (!(toolHasSilkTouch(toolItemStack) > 0) && (needsSilkTouch(blockState, world, blockPos) > 0))
            return false;

        return !(blockState.getHardness(world, blockPos) < 0) && canMine;
    }

    public static int needsSilkTouch(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        return needsSilkTouch(blockState, world, blockPos);
    }

    // This is an integer so I can return the level of silk touch needed
    public static int needsSilkTouch(BlockState blockState, World world, BlockPos blockPos) {
        Identifier blockLootTableID = blockState.getBlock().getLootTableId();
        LootPoolEntryType lootPoolEntryType = Registry.LOOT_POOL_ENTRY_TYPE.get(blockLootTableID);
        LootPoolEntry lootPoolEntry;

        // TODO: Figure out how to tell if a block needs silk touch
        return 0;
    }

    // This is an integer so I can return the level of silk touch on the tool
    public static short toolHasSilkTouch(ItemStack itemStack) {
        // Enchantments: [{lvl: 5s, id: "minecraft:efficiency"}, {lvl: 1s, id: "minecraft:silk_touch"}]

        ListTag enchantments = itemStack.getEnchantments();
        CompoundTag compoundTag;
        Tag currentID;

        short silkTouchLevel = 0;

        for (int i = 0; i < enchantments.size(); i++) {
            compoundTag = enchantments.getCompound(i);
            currentID = compoundTag.get("id");

            if (currentID == null)
                break;

            if (currentID.asString().equals("minecraft:silk_touch")) {
                silkTouchLevel = compoundTag.getShort("lvl");

                break;
            }
        }

        // TODO: Check if this works on a non-integrated server
        System.out.println("Silk Touch Level: " + silkTouchLevel);

        return silkTouchLevel;
    }
}
