package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.modmenu.screens.MainScreen;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

public class MainClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Client Side Only!!!

		// Register Configuration Screen for Mod Menu
		AutoConfig.register(MainScreen.class, GsonConfigSerializer::new);
	}
}
