package me.alexisevelyn.randomtech.utility;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

/**
 * The type Foods.
 */
public final class FoodsHelper {
    public static final FoodComponent EDIBLE_POWER = new FoodComponent
            .Builder()
            .hunger(3)
            .saturationModifier(6F)
            .statusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 20*3, 1), 0.5F)
            .build();
}
