package me.alexisevelyn.randomtech.items.armor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

// TODO: Make enchants useless if item is broken.
public class ArmorBase extends ArmorItem {
    private boolean isDamageable = true;

    public ArmorBase(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    public ArmorBase(ArmorMaterial material, EquipmentSlot slot) {
        super(material, slot, new Item.Settings().group(ItemGroup.COMBAT));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Text getName() {
        return new TranslatableText(this.getTranslationKey());
    }

    public boolean isUsable(ItemStack stack) {
        if (stack.getDamage() < stack.getMaxDamage() - 1) {
            this.isDamageable = false;

            return false;
        }

        this.isDamageable = true;
        return true;
    }

    // Prevents damage from occurring to the item. Multiple sources of damage can occur.
    // That means that we may not be able to catch all sources of damage directly.
    // This should function as a catch all.
    @Override
    public boolean isDamageable() {
        return this.isDamageable;
    }

    @Override
    public boolean isFireproof() {
        return super.isFireproof();
    }
}
