package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.utility.registryhelpers.server.ServerPostRegistryHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.server.ServerPreRegistryHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.server.ServerRegistryHelper;
import net.fabricmc.api.DedicatedServerModInitializer;

/**
 * The type Main server.
 */
public class MainServer implements DedicatedServerModInitializer {
	final protected ServerPreRegistryHelper serverPreRegistryHelper = new ServerPreRegistryHelper();
	final protected ServerPostRegistryHelper serverPostRegistryHelper = new ServerPostRegistryHelper();
	final protected ServerRegistryHelper serverRegistryHelper = new ServerRegistryHelper();

    /**
     * On initialize server.
     */
    @Override
	public void onInitializeServer() {
		// Server Side Only!!!

		serverPreRegistryHelper.preRegister();
		serverRegistryHelper.register();
		serverPostRegistryHelper.postRegister();
	}
}
