package me.alexisevelyn.randomtech.api.utilities;

import me.alexisevelyn.randomtech.api.items.energy.EnergyHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.apiguardian.api.API;
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
     * Creates a charged/discharged variant of powered items
     *
     * NOTE: Must be called from an override of {@link Item#appendStacks(ItemGroup, DefaultedList)}
     *
     * @param item     the powered item
     * @param itemList the provided item list
     */
    @API(status = API.Status.STABLE)
    public static void initPoweredItems(Item item, DefaultedList<ItemStack> itemList) {
        // Ensure An Energy Based Item
        if (!(item instanceof EnergyHolder))
            return;

        // Can be used to add multiple versions of items (e.g. a charged and discharged variant of each armor piece)
        ItemStack uncharged = new ItemStack(item);
        ItemStack charged = new ItemStack(item);

        Energy.of(uncharged).set(0.0);
        Energy.of(charged).set(Energy.of(charged).getMaxStored());

        itemList.add(uncharged);
        itemList.add(charged);
    }

    /**
     * Converts vanilla tool with durability to powered tool with equivalent power (percentage-wise)
     *
     * @param oldItemStack the durability based tool
     * @param newItemStack the power based tool (either {@link EnergyHelper} or {@link EnergyHolder})
     * @return the amount of power to set for the powered tool
     */
    @API(status = API.Status.INTERNAL)
    private static double calculateCurrentPowerForConversion(ItemStack oldItemStack, ItemStack newItemStack) {
         EnergyHandler energyHandler = Energy.of(newItemStack);
         double oldDurability = oldItemStack.getMaxDamage() - oldItemStack.getDamage();

         if (newItemStack.getItem() instanceof EnergyHelper) {
             EnergyHelper customEnergyItem = (EnergyHelper) newItemStack.getItem();

             return (oldDurability * customEnergyItem.getMaxEnergy(newItemStack)) / oldItemStack.getMaxDamage();
         }

         return (oldDurability * energyHandler.getMaxStored()) / oldItemStack.getMaxDamage();
    }

    /**
     * Converts vanilla tool with durability to powered tool with equivalent power (percentage-wise)
     *
     * @param oldStack the durability based tool
     * @param newStack the power based tool (either {@link EnergyHelper} or {@link EnergyHolder})
     * @param tag      the durability based tool's {@link CompoundTag}
     * @return the powered tool's {@link ItemStack} with the energy set to the equivalent of the durability
     */
    @API(status = API.Status.STABLE)
    public static ItemStack convertStackToEnergyItemStack(ItemStack oldStack, ItemStack newStack, CompoundTag tag) {
        // Copy over existing NBT Data such as Enchants
        if (tag != null) {
            CompoundTag oldTag = tag.copy();
            oldTag.remove("Damage");

            newStack.setTag(oldTag);
        }

        if (!(oldStack.getItem() instanceof EnergyHolder) && newStack.getItem() instanceof EnergyHolder)
            // Calculate's the equivalent power level of the tool from the durability
            Energy.of(newStack).set(calculateCurrentPowerForConversion(oldStack, newStack));

        return newStack;
    }

    /**
     * Append energy level tooltip in similar fashion to vanilla's durability tooltip
     *
     * NOTE: Must be called from an override of {@link Item#appendTooltip(ItemStack, World, List, TooltipContext)}
     *
     * @param itemStack the item stack that the tooltip will be applied to (Item must be an instance of {@link EnergyHelper}
     * @param tooltip   the provided tooltip
     */
    @API(status = API.Status.STABLE)
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
     * Helper method to aid in using energy of the item in question.
     *
     * Automatically handles using energy greater than the total energy left (by depleting the energy to 0)
     * Also ensures the energy is not spent while the player is in creative mode.
     *
     * @param livingEntity the entity using the item in question
     * @param stack        the ItemStack of the item in question (Item must be an instance of {@link EnergyHolder}
     * @param cost         the amount of energy to consume
     */
    public static void useEnergy(LivingEntity livingEntity, ItemStack stack, double cost) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;

            if (playerEntity.isCreative())
                return;
        }

        // Check to make sure item is an energy based item
        if (!(stack.getItem() instanceof EnergyHolder))
            return;

        // If more than max amount of energy, just set to rest of energy level
        EnergyHandler currentEnergy = Energy.of(stack);
        if (cost > currentEnergy.getEnergy()) {
            Energy.of(stack).use(currentEnergy.getEnergy());
            return;
        }

        Energy.of(stack).use(cost);
    }
}
