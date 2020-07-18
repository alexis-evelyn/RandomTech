package me.alexisevelyn.randomtech.api.utilities;

import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredTool;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import reborncore.common.powerSystem.PowerSystem;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergyHandler;
import team.reborn.energy.EnergyHolder;

import java.util.List;

public class ItemManager {
    public static void initPoweredItems(Item item, DefaultedList<ItemStack> itemList) {
        // Can be used to add multiple versions of items (e.g. a charged and discharged variant of each armor piece)
        ItemStack uncharged = new ItemStack(item);
        ItemStack charged = new ItemStack(item);

        Energy.of(charged).set(Energy.of(charged).getMaxStored());

        itemList.add(uncharged);
        itemList.add(charged);
    }

    // This converts the old Item Stack's durability to energy based on the current durability, max durability, and max energy capacity.
    protected static double calculateCurrentPowerForConversion(ItemStack oldItemStack, ItemStack newItemStack) {
        EnergyHandler energyHandler = Energy.of(newItemStack);

        double oldDurability = oldItemStack.getMaxDamage() - oldItemStack.getDamage();

        return (oldDurability * energyHandler.getMaxStored()) / oldItemStack.getMaxDamage();
    }

    public static ItemStack convertStackToEnergyItemStack(ItemStack oldStack, ItemStack newStack, CompoundTag tag) {
        // Copy over existing NBT Data such as Enchants
        if (tag != null) {
            CompoundTag oldTag = tag.copy();
            oldTag.remove("Damage");

            newStack.setTag(oldTag);
        }

        if (!(oldStack.getItem() instanceof EnergyHolder))
            // Keep Durability of Armor and Tools as Charge By Default When First Crafted
            Energy.of(newStack).set(ItemManager.calculateCurrentPowerForConversion(oldStack, newStack));

        return newStack;
    }

    @Environment(EnvType.CLIENT)
    public static void powerLevelTooltip(ItemStack itemStack, List<Text> tooltip) {
        Item item = itemStack.getItem();

        if (!(item instanceof EnergyHolder))
            return;

        double currentEnergy = Energy.of(itemStack).getEnergy();
        double maxEnergy = ((EnergyHolder) item).getMaxStoredPower();

        if (currentEnergy == maxEnergy)
            return;

        // Could use item.durability
        Text energy = new TranslatableText("text.randomtech.power_level", PowerSystem.getLocaliszedPowerNoSuffix(currentEnergy), PowerSystem.getLocaliszedPower(maxEnergy));
        tooltip.add(energy);
    }
}
