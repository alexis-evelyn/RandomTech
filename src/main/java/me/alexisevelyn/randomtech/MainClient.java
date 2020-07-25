package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.utility.registryhelpers.client.ClientPostRegistryHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.client.ClientPreRegistryHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.client.ClientRegistryHelper;
import net.fabricmc.api.ClientModInitializer;

public class MainClient implements ClientModInitializer {
	protected ClientPreRegistryHelper clientPreRegistryHelper = new ClientPreRegistryHelper();
	protected ClientPostRegistryHelper clientPostRegistryHelper = new ClientPostRegistryHelper();
	protected ClientRegistryHelper clientRegistryHelper = new ClientRegistryHelper();

	@Override
	public void onInitializeClient() {
		// Client Side Only!!!

		clientPreRegistryHelper.preRegister();
		clientRegistryHelper.register();
		clientPostRegistryHelper.postRegister();
	}
}
