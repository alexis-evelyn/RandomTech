package me.alexisevelyn.randomtech.modmenu.screens;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "randomtech")
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

    static class InnerStuff {
        int a = 0;
        int b = 1;
    }
}