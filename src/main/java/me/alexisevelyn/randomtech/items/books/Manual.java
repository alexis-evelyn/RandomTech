package me.alexisevelyn.randomtech.items.books;

import me.alexisevelyn.randomtech.Main;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;

/**
 * The type Manual.
 */
public class Manual extends Item {
    public static final String itemID = "manual";

    private final Identifier bookIdentifier = new Identifier(Main.MODID, itemID);
    private final Identifier patchouliGuidebookIdentifier = new Identifier("patchouli", "guide_book");

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
        if (!isPatchouliInstalled()) {
            alertFailedOpen(playerEntity);
            return new TypedActionResult<>(ActionResult.FAIL, playerEntity.getStackInHand(hand));
        }

        PatchouliAPI.IPatchouliAPI bookAPI = PatchouliAPI.instance;

        // This method is called both by client and server.
        // This will crash if not preventing client from calling openBookGUI(...)
        if (!world.isClient) {
            bookAPI.openBookGUI((ServerPlayerEntity) playerEntity, bookIdentifier);
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
    }

    /**
     * Is patchouli installed boolean.
     *
     * @return the boolean
     */
    public boolean isPatchouliInstalled() {
        return Registry.ITEM.getOrEmpty(patchouliGuidebookIdentifier).isPresent();
    }

    /**
     * Alert failed open.
     *
     * @param playerEntity the player entity
     */
    private void alertFailedOpen(PlayerEntity playerEntity) {
        if (playerEntity == null)
            return;

        Text message = new TranslatableText("patchouli.install.fail",
                new TranslatableText("patchouli.manual.name")
                        .formatted(Formatting.DARK_GREEN, Formatting.BOLD))
                .formatted(Formatting.GOLD, Formatting.BOLD);

        playerEntity.sendMessage(message, true);
    }
}