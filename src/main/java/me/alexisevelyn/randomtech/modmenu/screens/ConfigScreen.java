package me.alexisevelyn.randomtech.modmenu.screens;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.modmenu.Theme;
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
    // Example Mod: https://github.com/shedaniel/AutoConfig/blob/91924f2ab24aacff7e6096240f6a0ef341323f64/src/main/java/me/sargunvohra/mcmods/autoconfig1u/example/ExampleConfig.java#L16

//    protected boolean toggleA = true;
//    protected boolean toggleB = false;
//
//    @ConfigEntry.Gui.CollapsibleObject
//    protected InnerStuff stuff = new InnerStuff();
//
//    @ConfigEntry.Gui.Excluded
//    protected InnerStuff invisibleStuff = new InnerStuff();
//
//    /**
//     * The type Inner stuff.
//     */
//    static class InnerStuff {
//        protected int a = 0;
//        protected int b = 1;
//    }

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public Theme guiTheme = Theme.THEME_VANILLA;
}