package me.alexisevelyn.randomtech.api.utilities;

import me.alexisevelyn.randomtech.api.items.tools.generic.BreakableBlocksHelper;
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
import org.apiguardian.api.API;

/**
 * The type Mining manager.
 */
public class MiningManagerHelper {
    /**
     * Checks if the tool is able to mine the block.
     *
     * Internally calls {@link #canMine(PlayerEntity, ItemStack, BlockState, World, BlockPos)}.
     *
     * @param context the context (ItemUsageContext)
     * @return true if can mine, false if cannot mine
     */
    @API(status = API.Status.STABLE)
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
     * Checks if the tool is able to mine the block.
     *
     * NOTE: Currently cannot tell if block needs to be silk touched or if it's the `correct` tool for the job.
     * It can only determine if the block is able to be dropped at all under non-silk touch conditions.
     *
     * @param playerEntity  the player that wants to mine
     * @param toolItemStack the ItemStack of the tool in question
     * @param blockState    the queried block's BlockState
     * @param world         the world the player is located in
     * @param blockPos      the queried block's position in the world
     * @return true if can mine, false if cannot mine
     */
    @API(status = API.Status.STABLE)
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
     * Checks if an unbreakable block can be broken with the tool in question.
     *
     * This requires the tool to implement the {@link BreakableBlocksHelper}
     *
     * @param playerEntity  the player entity
     * @param toolItemStack the tool item stack
     * @param blockState    the block state
     * @param world         the world
     * @param blockPos      the block pos
     * @return false if normally breakable block or if the tool is not a tool meant for breaking unbreakable blocks. Also false if tool cannot mine particular block. true if tool can mine unbreakable block.
     */
    @API(status = API.Status.EXPERIMENTAL)
    public static boolean checkUnbreakableBlock(PlayerEntity playerEntity, ItemStack toolItemStack, BlockState blockState, World world, BlockPos blockPos) {
        if (blockState.getHardness(world, blockPos) >= 0)
            return false;

        if (!(toolItemStack.getItem() instanceof BreakableBlocksHelper))
            return false;

        BreakableBlocksHelper tool = (BreakableBlocksHelper) toolItemStack.getItem();
        return tool.canBreakUnbreakableBlock(blockState, playerEntity, world, blockPos);
    }

    /**
     * Returns an integer that is the level of silk touch required to mine a block.
     *
     * Internally calls {@link #needsSilkTouch(BlockState, World, BlockPos)}
     *
     * @param context the ItemUsageContext
     * @return the level of silk touch required to mine the queried block
     */
    @API(status = API.Status.EXPERIMENTAL)
    public static int needsSilkTouch(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        return needsSilkTouch(blockState, world, blockPos);
    }

    /**
     * Returns an integer that is the level of silk touch required to mine a block.
     *
     * Currently not implemented.
     *
     * @param blockState the block's BlockState
     * @param world      the world the block is located in
     * @param blockPos   the position in the world the block is located in
     * @return the level of silk touch required to mine the queried block
     */
    @API(status = API.Status.EXPERIMENTAL)
    public static int needsSilkTouch(BlockState blockState, World world, BlockPos blockPos) {
//        Identifier blockLootTableID = blockState.getBlock().getLootTableId();
//        LootPoolEntryType lootPoolEntryType = Registry.LOOT_POOL_ENTRY_TYPE.get(blockLootTableID);
//        LootPoolEntry lootPoolEntry;

        return 0;
    }

    /**
     * Returns the level of silk touch on the queried tool
     *
     * @param itemStack the tool's ItemStack
     * @return the level of silk touch on the tool
     */
    @API(status = API.Status.EXPERIMENTAL)
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
