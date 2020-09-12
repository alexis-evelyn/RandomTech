package me.alexisevelyn.randomtech.api.utilities;

import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredTool;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * The type Mining manager.
 */
public class MiningManagerHelper {
    /**
     * Can mine boolean.
     *
     * @param context the context
     * @return the boolean
     */
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

    /**
     * Can mine boolean.
     *
     * @param playerEntity  the player entity
     * @param toolItemStack the tool item stack
     * @param blockState    the block state
     * @param world         the world
     * @param blockPos      the block pos
     * @return the boolean
     */
    public static boolean canMine(PlayerEntity playerEntity, ItemStack toolItemStack, BlockState blockState, World world, BlockPos blockPos) {
        boolean canMine = playerEntity.isUsingEffectiveTool(blockState);
        Item item = toolItemStack.getItem();

        // This checks if the tool is dead
        if (item instanceof GenericPoweredTool && !((GenericPoweredTool) item).isUsable(toolItemStack))
            return false;

        // Checks if block needs silk touch and if so, check if tool has silk touch
        if ((toolHasSilkTouch(toolItemStack) == 0) && (needsSilkTouch(blockState, world, blockPos) > 0))
            return false;

        if (checkUnbreakableBlock(playerEntity, toolItemStack, blockState, world, blockPos))
            return true;

        return (blockState.getHardness(world, blockPos) >= 0) && canMine;
    }

    /**
     * Check unbreakable block boolean.
     *
     * @param playerEntity  the player entity
     * @param toolItemStack the tool item stack
     * @param blockState    the block state
     * @param world         the world
     * @param blockPos      the block pos
     * @return the boolean
     */
    // Checks if the block can be broken by the tool. Requires special override in the tool's class.
    public static boolean checkUnbreakableBlock(PlayerEntity playerEntity, ItemStack toolItemStack, BlockState blockState, World world, BlockPos blockPos) {
        if (blockState.getHardness(world, blockPos) >= 0)
            return false;

        if (!(toolItemStack.getItem() instanceof GenericPoweredTool))
            return false;

        GenericPoweredTool genericPoweredTool = (GenericPoweredTool) toolItemStack.getItem();
        return genericPoweredTool.canBreakUnbreakableBlock(blockState, playerEntity, world, blockPos);
    }

    /**
     * Needs silk touch int.
     *
     * @param context the context
     * @return the int
     */
    public static int needsSilkTouch(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        return needsSilkTouch(blockState, world, blockPos);
    }

    /**
     * Needs silk touch int.
     *
     * @param blockState the block state
     * @param world      the world
     * @param blockPos   the block pos
     * @return the int
     */
    // This is an integer so I can return the level of silk touch needed
    public static int needsSilkTouch(BlockState blockState, World world, BlockPos blockPos) {
//        Identifier blockLootTableID = blockState.getBlock().getLootTableId();
//        LootPoolEntryType lootPoolEntryType = Registry.LOOT_POOL_ENTRY_TYPE.get(blockLootTableID);
//        LootPoolEntry lootPoolEntry;

        return 0;
    }

    /**
     * Tool has silk touch short.
     *
     * @param itemStack the item stack
     * @return the short
     */
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

        // System.out.println("Silk Touch Level: " + silkTouchLevel);

        return silkTouchLevel;
    }
}
