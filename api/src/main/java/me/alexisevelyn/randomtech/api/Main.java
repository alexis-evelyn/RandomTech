package me.alexisevelyn.randomtech.api;

import me.alexisevelyn.randomtech.api.utilities.cardinalcomponents.BrokenItemComponent;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

/**
 * Procedurally Generating Everything
 *
 * Idea from playing Starbound
 * Potential Implementation Starting Point From: https://www.youtube.com/watch?v=CS5DQVSp058
 *
 * Fork of Spreadsheet From Video: https://docs.google.com/spreadsheets/d/1f1b7R4_hjlasOuSA6R7w309LwG3n43KKolTr_pERtnc
 * Reused Grayscale Texture Issue Can Be Solved By Overlaying Translucent Textures With Different Colors Shaded In During Runtime (See Pure Redstone Block Gimp File)
 *
 * The Infinity Snapshot showed how awesome this idea would be (although it's very unbalanced)
 * To solve the balance issue, we'll need to use metadata and a set of limits.
 * E.g. for swords, by default, don't allow higher than some amount of damage when generating one.
 * This will be very, very complicated to implement and give the new game feel while staying balanced.
 * Since Minecraft is more data driven than ever, I'll probably hook into the datapack system to read the config.
 * For storing, I'll need a cross between blockstate and nbt data. This will in turn be able to help me with cross-save,
 * cross-server item/entity transfer if implemented with cross-save transfer in mind.
 *
 * Both the procedural generation and cross-save/server transfer will be a setting (gamerule) to turn it on or off.
 * Disabling procedural generation won't delete anything already generated, it'll just keep future generation non-procedural.
 *
 * Both ideas came from my time playing Starbound. I just heard about this video by accident a long time ago (before the infinity snapshot was released and everything).
 * I plan on implementing this idea in my api sometime in the distant future so that other mods can addon to the procedural generation through the api.
 * I'll have to figure out how to dynamically color different textures and layer them on top of each other during runtime.
 *
 * I'll also have to figure out how to create new item/block/entity ids during runtime and figure out the best way to store/transfer the metadata that makes the procedural blocks/entities/items possible.
 *
 * Block Entities are too laggy during load because they each have their own instance of the custom block entity class.
 * BlockStates have a new model generated for every permutation of the Blockstate during game startup.
 *
 * Storing the custom block's properties in every copy of the block is going to waste so much space in the world save.
 * So, I'll most likely have a separate local database and use packets to send data between server/client as well as storing info about the
 * blocks/items/entities with their associated randomly generated id. Since blocks have names via translation keys, it's not necessary for the ids to make since to a human.
 * So, most likely, it'll be something along the lines of `procedural:some_uuid_converted_to_friendly_string`.
 *
 * Also, send the translation file between server/client. That way mods like AE2 can sort the items in inventory on the server side.
 * More notes will be added in the future and probably moved to a separate file (Markdown File?).
 */

// This runs on both the server and the client
public class Main implements ModInitializer {
	public static final String MODID = "randomtechapi";

	public static final ComponentType<BrokenItemComponent> BROKEN_ITEM_COMPONENT =
			ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(Main.MODID, "broken_item_component"), BrokenItemComponent.class);

    /**
     * On initialize.
     */
    @Override
	public void onInitialize() {
		// Client and Server Side!!!


	}
}
