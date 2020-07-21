package me.alexisevelyn.randomtech.api.utilities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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

        CompoundTag tag = uncharged.getTag();

        if (tag != null)
            tag.putInt("CustomModelData", 1337); // Sets the model to show as dead in the creative inventory and REI

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
            Energy.of(newStack).set(calculateCurrentPowerForConversion(oldStack, newStack));

        // Sets item model to discharged state if crafted while dead
//        if (Energy.of(newStack).getEnergy() == 0)
//            setDischargedModelData(newStack, false);

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

    // This method exists to allow energy to not be used if the player is in Creative mode
    public static void useEnergy(LivingEntity livingEntity, ItemStack stack, int cost) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;

            if (playerEntity.isCreative())
                return;
        }

        // If more than max amount of energy, just set to rest of energy level
        EnergyHandler currentEnergy = Energy.of(stack);
        if (cost > currentEnergy.getEnergy()) {
            Energy.of(stack).use(currentEnergy.getEnergy());
            return;
        }

        Energy.of(stack).use(cost);
    }

    public static void setDischargedModelData(ItemStack stack, boolean isUsable) {
        CompoundTag tag = stack.getTag();

        if (tag == null)
            return;

        // This returns 0 if the tag does not exist.
        int modelData = tag.getInt("CustomModelData");

        if (isUsable && modelData != 0) {
            // This removes the custom model data tag if it exists and the item is usable
            tag.remove("CustomModelData");
        } else if (!isUsable && modelData == 0) {
            // This puts a custom model data tag on the item so I can have the resource pack display a different texture if the tool is dead.
            tag.putInt("CustomModelData", 1337);
        }
    }
}
