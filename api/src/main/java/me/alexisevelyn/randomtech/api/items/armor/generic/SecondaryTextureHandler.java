package me.alexisevelyn.randomtech.api.items.armor.generic;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * For allowing armor to have more than one texture visible to other players of the wearer
 */
public interface SecondaryTextureHandler extends Wearable {
    /**
     * Retrieve secondary texture that the armor may have based on it's itemstack and the wearer.
     *
     * @param livingEntity
     * @param itemStack
     * @return
     */
    @Nullable String getSecondaryArmorTexture(LivingEntity livingEntity, ItemStack itemStack);

    /**
     * The armor slot meant to be used by this armor.
     *
     * @return Slot armor was meant to be worn on
     */
    @NotNull EquipmentSlot getArmorSlot();
}
