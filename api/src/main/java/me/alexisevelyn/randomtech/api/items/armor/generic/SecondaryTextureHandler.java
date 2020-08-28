package me.alexisevelyn.randomtech.api.items.armor.generic;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * For allowing armor to have more than one texture visible to other players of the wearer
 */
public interface SecondaryTextureHandler {
    @Nullable String getSecondaryArmorTexture(LivingEntity livingEntity, ItemStack itemStack);

    /**
     * The armor slot meant to be used by this armor.
     *
     * @return Slot armor was meant to be worn on
     */
    EquipmentSlot getSlotType();
}
