package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.entity.damage.DamageSource;

/**
 * The type Custom damage source.
 */
public class CustomDamageSource extends DamageSource {
    public static final DamageSource KILL_COMMAND = new CustomDamageSource("killCommand").setBypassesArmor();

    /**
     * Instantiates a new Custom damage source.
     *
     * @param name the name
     */
    protected CustomDamageSource(String name) {
        super(name);
    }
}
