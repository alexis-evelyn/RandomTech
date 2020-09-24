package me.alexisevelyn.randomtech.items.books;

import me.alexisevelyn.randomtech.Main;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * The type Manual.
 */
public class Manual extends Item {
    public static final String itemID = "manual";

    /**
     * Instantiates a new Manual.
     *
     * @param settings the settings
     */
    public Manual(Settings settings) {
        super(settings);
    }

    /**
     * Use typed action result.
     *
     * @param world        the world
     * @param playerEntity the player entity
     * @param hand         the hand
     * @return the typed action result
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        playerEntity.sendMessage(new TranslatableText(Main.MODID + "manual.not_implemented"), true);

        return new TypedActionResult<>(ActionResult.FAIL, playerEntity.getStackInHand(hand));
    }
}