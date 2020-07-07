package me.alexisevelyn.randomtech;

import net.fabricmc.api.ModInitializer;

// This runs on both the server and the client
public class Main implements ModInitializer {
	public static final String MODID = "randomtech";

	protected RegistryHelper registryHelper = new RegistryHelper();

	@Override
	public void onInitialize() {
		// Client and Server Side!!!

		registryHelper.register();



		// vazkii.patchouli.api.PatchouliAPI;
	}
}
