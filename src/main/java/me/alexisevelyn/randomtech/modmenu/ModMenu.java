package me.alexisevelyn.randomtech.modmenu;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.alexisevelyn.randomtech.modmenu.screens.MainScreen;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;

public class ModMenu implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            //MainScreen config = AutoConfig.getConfigHolder(MainScreen.class).getConfig();

            return AutoConfig.getConfigScreen(MainScreen.class, parent).get();
        };
    }
}