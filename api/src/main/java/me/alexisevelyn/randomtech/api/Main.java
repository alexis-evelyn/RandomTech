package me.alexisevelyn.randomtech.api;

import net.fabricmc.api.ModInitializer;
import org.apiguardian.api.API;

/**
 * Internal Class For API Startup
 */
@API(status = API.Status.INTERNAL)
public class Main implements ModInitializer {
	public static final String MODID = "randomtechapi";

    /**
     * {@inheritDoc}
     *
     * Intentionally Empty For Now
     */
    @API(status = API.Status.INTERNAL)
    @Override
	public void onInitialize() {
		// Client and Server Side!!!
	}
}
