package me.alexisevelyn.randomtech.modmenu.screens;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.modmenu.screens.MainScreen.InnerStuff;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

/**
 * The type Main screen.
 */
@Config(name = Main.MODID)
@SuppressWarnings("unused")
public class MainScreen implements ConfigData {
    // Experimental Settings
    // Debug Level
    // Procedural Generation (World Seed Based) - https://www.youtube.com/watch?v=CS5DQVSp058&ab_channel=JayExci

    boolean toggleA = true;
    boolean toggleB = false;

    @ConfigEntry.Gui.CollapsibleObject
    InnerStuff stuff = new InnerStuff();

    @ConfigEntry.Gui.Excluded
    InnerStuff invisibleStuff = new InnerStuff();

    /**
     * The type Inner stuff.
     */
    static class InnerStuff {
        int a = 0;
        int b = 1;
    }
}