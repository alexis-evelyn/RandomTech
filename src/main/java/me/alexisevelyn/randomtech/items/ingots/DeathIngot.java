package me.alexisevelyn.randomtech.items.ingots;

import net.minecraft.item.Item;

// So, the current idea is to sacrifice a player's health all the way to zero (permanently)
// even if it's not the player performing the sacrifice
// This may change if I find a more evil and less permanent way to create a death ingot

// Idea 2: ingot kills with void damage when held. Ore is less severe. Poison, hunger, etc...

// This idea came from X33N misreading Galacticraft's Desh ingot in CaptainSparklez' Sevtech Ages Episode 57.
// Video at Timestamp: https://youtu.be/lf5yLC25LNk?t=1430 (23:50)
// Desh Ingot: https://ftbwiki.org/Desh_Ingot_(Galacticraft)

/**
 * The type Death ingot.
 */
// This death ingot will most likely be the purest black that can be portrayed in Minecraft
public class DeathIngot extends Item {
    /**
     * Instantiates a new Death ingot.
     *
     * @param settings the settings
     */
    public DeathIngot(Settings settings) {
        super(settings);
    }
}
