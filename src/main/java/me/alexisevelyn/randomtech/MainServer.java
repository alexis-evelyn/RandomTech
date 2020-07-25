package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.utility.registryhelpers.server.ServerPostRegistryHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.server.ServerPreRegistryHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.server.ServerRegistryHelper;
import net.fabricmc.api.DedicatedServerModInitializer;

public class MainServer implements DedicatedServerModInitializer {
	protected ServerPreRegistryHelper serverPreRegistryHelper = new ServerPreRegistryHelper();
	protected ServerPostRegistryHelper serverPostRegistryHelper = new ServerPostRegistryHelper();
	protected ServerRegistryHelper serverRegistryHelper = new ServerRegistryHelper();

	@Override
	public void onInitializeServer() {
		// Server Side Only!!!

		serverPreRegistryHelper.preRegister();
		serverRegistryHelper.register();
		serverPostRegistryHelper.postRegister();
	}
}
