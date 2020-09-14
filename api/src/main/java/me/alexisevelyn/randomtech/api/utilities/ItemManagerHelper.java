package me.alexisevelyn.randomtech.api.utilities;

import me.alexisevelyn.randomtech.api.items.energy.EnergyHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import reborncore.common.powerSystem.PowerSystem;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergyHandler;
import team.reborn.energy.EnergyHolder;

import java.util.List;

/**
 * The type Item manager.
 */
public class ItemManagerHelper {
    /**
     * Init powered items.
     *
     * @param item     the item
     * @param itemList the item list
     */
    public static void initPoweredItems(Item item, DefaultedList<ItemStack> itemList) {
        // Ensure An Energy Based Item
        if (!(item instanceof EnergyHolder))
            return;

        // Can be used to add multiple versions of items (e.g. a charged and discharged variant of each armor piece)
        ItemStack uncharged = new ItemStack(item);
        ItemStack charged = new ItemStack(item);

        Energy.of(charged).set(Energy.of(charged).getMaxStored());

        itemList.add(uncharged);
        itemList.add(charged);
    }

    /**
     * Calculate current power for conversion double.
     *
     * @param oldItemStack the old item stack
     * @param newItemStack the new item stack
     * @return the double
     */
    // This converts the old Item Stack's durability to energy based on the current durability, max durability, and max energy capacity.
    protected static double calculateCurrentPowerForConversion(ItemStack oldItemStack, ItemStack newItemStack) {
         EnergyHandler energyHandler = Energy.of(newItemStack);
         double oldDurability = oldItemStack.getMaxDamage() - oldItemStack.getDamage();

         if (newItemStack.getItem() instanceof EnergyHelper) {
             EnergyHelper customEnergyItem = (EnergyHelper) newItemStack.getItem();

             return (oldDurability * customEnergyItem.getMaxEnergy(newItemStack)) / oldItemStack.getMaxDamage();
        }

         return (oldDurability * energyHandler.getMaxStored()) / oldItemStack.getMaxDamage();
    }

    /**
     * Convert stack to energy item stack item stack.
     *
     * @param oldStack the old stack
     * @param newStack the new stack
     * @param tag      the tag
     * @return the item stack
     */
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

    /**
     * Power level tooltip.
     *
     * @param itemStack the item stack
     * @param tooltip   the tooltip
     */
    @Environment(EnvType.CLIENT)
    public static void powerLevelTooltip(ItemStack itemStack, List<Text> tooltip) {
        Item item = itemStack.getItem();

        if (!(item instanceof EnergyHelper))
            return;

        double currentEnergy = Energy.of(itemStack).getEnergy();
        double maxEnergy = ((EnergyHelper) item).getMaxEnergy(itemStack);

        if (currentEnergy == maxEnergy)
            return;

        // Could use item.durability
        Text energy = new TranslatableText("text.randomtech.power_level", PowerSystem.getLocaliszedPowerNoSuffix(currentEnergy), PowerSystem.getLocaliszedPower(maxEnergy));
        tooltip.add(energy);

        // Just to put a newline below dead tool energy display
        if (currentEnergy == 0)
            tooltip.add(new LiteralText(""));
    }

    /**
     * Use energy.
     *
     * @param livingEntity the living entity
     * @param stack        the stack
     * @param cost         the cost
     */
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
}
