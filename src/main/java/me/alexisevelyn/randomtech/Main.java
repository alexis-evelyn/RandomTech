package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.utility.registryhelpers.main.PostRegistryHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.PreRegistryHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.fabricmc.api.ModInitializer;

/* TODO: Ordered by importance and logical steps
 *
 * Work on Fuser Gui
 * Work on entities
 *
 * Consider how to obtain Death Ingot
 * Move api to separate jar so people can implement it without needing my mod to be installed.
 */

// This runs on both the server and the client
public class Main implements ModInitializer {
	public static final String MODID = "randomtech";

	final protected PreRegistryHelper preRegistryHelper = new PreRegistryHelper();
	final protected PostRegistryHelper postRegistryHelper = new PostRegistryHelper();
	final protected RegistryHelper registryHelper = new RegistryHelper();

	@Override
	public void onInitialize() {
		// Client and Server Side!!!

		preRegistryHelper.preRegister();
		registryHelper.register();
		postRegistryHelper.postRegister();
	}
}
