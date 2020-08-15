package me.alexisevelyn.randomtech.modmenu;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.alexisevelyn.randomtech.modmenu.screens.MainScreen;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;

/**
 * The type Mod menu.
 */
public class ModMenu implements ModMenuApi {
    /**
     * Gets mod config screen factory.
     *
     * @return the mod config screen factory
     */
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            //MainScreen config = AutoConfig.getConfigHolder(MainScreen.class).getConfig();

            return AutoConfig.getConfigScreen(MainScreen.class, parent).get();
        };
    }
}