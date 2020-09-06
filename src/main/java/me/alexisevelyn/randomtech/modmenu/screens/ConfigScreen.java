package me.alexisevelyn.randomtech.modmenu.screens;

import me.alexisevelyn.randomtech.Main;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

/**
 * The type Main screen.
 */
@Config(name = Main.MODID)
public class ConfigScreen implements ConfigData {
    // Experimental Settings
    // Debug Level
    // Procedural Generation (World Seed Based) - https://www.youtube.com/watch?v=CS5DQVSp058&ab_channel=JayExci

    protected boolean toggleA = true;
    protected boolean toggleB = false;

    @ConfigEntry.Gui.CollapsibleObject
    protected InnerStuff stuff = new InnerStuff();

    @ConfigEntry.Gui.Excluded
    protected InnerStuff invisibleStuff = new InnerStuff();

    /**
     * The type Inner stuff.
     */
    static class InnerStuff {
        protected int a = 0;
        protected int b = 1;
    }
}