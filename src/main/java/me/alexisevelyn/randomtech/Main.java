package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.utility.PostRegistryHelper;
import me.alexisevelyn.randomtech.utility.RegistryHelper;
import net.fabricmc.api.ModInitializer;

/* TODO: Ordered by importance and logical steps
 *
 * Work on Fuser Gui
 * Work on entities
 *
 * Fix texture for Cobalt Wire
 * Fix cobalt wire to not connect to Redstone wire
 *
 * Consider how to obtain Death Ingot
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
