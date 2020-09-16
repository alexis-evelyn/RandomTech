package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.entity.damage.DamageSource;
import org.apiguardian.api.API;

/**
 * Extend to create your own custom damage sources
 */
public class CustomDamageSource extends DamageSource {
    // Setting to out of world allows damaging creative mode players
    public static final DamageSource KILL_COMMAND = new CustomDamageSource("killCommand").setBypassesArmor().setOutOfWorld();

    /**
     * Override to create custom sources of damage
     *
     * This class extends {@link DamageSource} and also uses access wideners to make certain methods of {@link DamageSource} available to the public
     *
     * For name, an example of the translation would look like: {@code "death.attack.killCommand": "%1$s was /killed by an angry server owner ;)"} if the name was {@code killCommand}
     *
     * @param name the internal name of the damage type to be used in translation files
     */
    @API(status = API.Status.STABLE)
    public CustomDamageSource(String name) {
        super(name);
    }
}
