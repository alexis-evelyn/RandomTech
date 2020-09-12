package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.entity.damage.DamageSource;

/**
 * The type Custom damage source.
 */
public class CustomDamageSource extends DamageSource {
    // Setting to out of world allows damaging creative mode players
    public static final DamageSource KILL_COMMAND = new CustomDamageSource("killCommand").setBypassesArmor().setOutOfWorld();

    /**
     * Instantiates a new Custom damage source.
     *
     * @param name the name
     */
    public CustomDamageSource(String name) {
        super(name);
    }
}
