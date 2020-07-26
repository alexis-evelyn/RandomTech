package me.alexisevelyn.randomtech.utility.registryhelpers.main;

import me.alexisevelyn.randomtech.Main;
import net.minecraft.util.Identifier;

public class PreRegistryHelper {
    public static final Identifier keybindPacketIdentifier = new Identifier(Main.MODID, "keybind_packet");

    public static final String zoom = "zoom";
    public static final String unzoom = "unzoom";

    // This might be useful for detecting if certain mods exist and adding crafting recipes accordingly
    @SuppressWarnings("EmptyMethod")
    public void preRegister() {

    }
}