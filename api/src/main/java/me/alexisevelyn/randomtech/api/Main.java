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
