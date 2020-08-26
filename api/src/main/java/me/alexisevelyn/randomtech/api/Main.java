package me.alexisevelyn.randomtech.api;

import me.alexisevelyn.randomtech.api.utilities.cardinalcomponents.BrokenItemComponent;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

/**
 * The type Main.
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
