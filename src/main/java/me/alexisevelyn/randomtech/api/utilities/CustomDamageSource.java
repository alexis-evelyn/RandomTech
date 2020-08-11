package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.entity.damage.DamageSource;

public class CustomDamageSource extends DamageSource {
    public static final DamageSource KILL_COMMAND = new CustomDamageSource("killCommand").setBypassesArmor();

    protected CustomDamageSource(String name) {
        super(name);
    }
}
