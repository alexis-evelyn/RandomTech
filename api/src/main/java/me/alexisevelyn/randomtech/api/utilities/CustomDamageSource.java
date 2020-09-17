package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.entity.damage.DamageSource;
import org.apiguardian.api.API;

/**
 * Custom Damage Sources.
 * <br><br>
 *
 * Example:
 * <pre><code>
 * public static final DamageSource KILL_COMMAND = new CustomDamageSource("killCommand").setBypassesArmor().setOutOfWorld();
 * </code></pre>
 */
public class CustomDamageSource extends DamageSource {
    // Setting to out of world allows damaging creative mode players
    public static final DamageSource KILL_COMMAND = new CustomDamageSource("killCommand").setBypassesArmor().setOutOfWorld();

    /**
     * Instantiate to create your own custom damage source.
     *
     * This class extends {@link DamageSource} and also uses access wideners to make certain methods of {@link DamageSource} available to the public.
     *
     * For name, an example of the translation would look like: {@code "death.attack.killCommand": "%1$s was /killed by an angry server owner ;)"} if the name was {@code killCommand}.
     *
     * To apply your damage source, use {@code entity.damage(YOUR_DAMAGE_SOURCE, 4.0F);} to deal damage to your entity of choice.
     *
     * @param name the internal name of the damage type to be used in translation files.
     */
    @API(status = API.Status.STABLE)
    public CustomDamageSource(String name) {
        super(name);
    }
}
