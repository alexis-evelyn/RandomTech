package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.utility.PostRegistryHelper;
import me.alexisevelyn.randomtech.utility.RegistryHelper;
import net.fabricmc.api.ModInitializer;

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
