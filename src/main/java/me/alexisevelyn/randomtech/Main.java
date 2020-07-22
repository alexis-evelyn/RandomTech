package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.utility.PostRegistryHelper;
import me.alexisevelyn.randomtech.utility.RegistryHelper;
import net.fabricmc.api.ModInitializer;

/* TODO:
 *
 * Fix Cobalt Recipes
 * Add Cobalt Dust
 * Fix Fuser to not run when containing nothing
 * Fix Cobalt ore and Cobalt block to respect Mining Level
 * Fix output recipes for lava production
 * Make it possible to not output item in some recipes in Fuser
 * Fix REI Recipes to show output item if exists
 * Work on Fuser Gui
 * Work on entities
 * Consider how to obtain Death Ingot
 * Create texture for Cobalt dust and Death Ingot
 */

// This runs on both the server and the client
public class Main implements ModInitializer {
	public static final String MODID = "randomtech";

	protected PostRegistryHelper postRegistryHelper = new PostRegistryHelper();
	protected RegistryHelper registryHelper = new RegistryHelper();

	@Override
	public void onInitialize() {
		// Client and Server Side!!!

		registryHelper.register();
		postRegistryHelper.postRegister();
	}
}
