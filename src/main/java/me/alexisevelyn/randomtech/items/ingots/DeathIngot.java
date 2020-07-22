package me.alexisevelyn.randomtech.items.ingots;

import net.minecraft.item.Item;

// So, the current idea is to sacrifice a player's health all the way to zero (permanently)
// even if it's not the player performing the sacrifice
// This may change if I find a more evil and less permanent way to create a death ingot

// This idea came from X33N misreading what I believe to be Dess ingot in CaptainSparklez' Sevtech Ages Episode 57.
// TODO: Find exact timestamp from when X33N misread the ingot and figure out what the actual ingot was.

// This death ingot will most likely be the purest black that can be portrayed in Minecraft
public class DeathIngot extends Item {
    public DeathIngot(Settings settings) {
        super(settings);
    }
}
