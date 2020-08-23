package me.alexisevelyn.randomtech.api.utilities.cardinalcomponents;

import me.alexisevelyn.randomtech.api.Main;
import me.alexisevelyn.randomtech.api.items.energy.EnergyHelper;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.Component;
import nerdhub.cardinal.components.api.component.extension.CopyableComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

/**
 * The type Broken item component.
 */
/*
 Note to developers. Until I figure out how to register the items based on my Generic Tools/Armor,
  you'll have to register your item with this component
  if you want to have custom model data when the item is broken.

 You can use my post registry class for an example on how to register with this component
  as that's where I registered my items with this component.
 */
public class BrokenItemComponent implements CopyableComponent<BrokenItemComponent> {
    private int customModelData = 1337;
    private final ItemStack itemStack;

    /**
     * Instantiates a new Broken item component.
     *
     * @param itemStack the item stack
     */
    public BrokenItemComponent(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * From tag.
     *
     * @param compoundTag the compound tag
     */
    @Override
    public void fromTag(CompoundTag compoundTag) {
        // this.customModelData = compoundTag.getInt("CustomModelData");

        this.customModelData = getStackModelData(itemStack);
    }

    /**
     * To tag compound tag.
     *
     * @param compoundTag the compound tag
     * @return the compound tag
     */
    @Override
    public @NotNull CompoundTag toTag(CompoundTag compoundTag) {
        // compoundTag.putInt("CustomModelData", this.customModelData);

        setIfStackBroken(itemStack);
        return compoundTag;
    }

    /**
     * Gets component type.
     *
     * @return the component type
     */
    public @NotNull ComponentType<?> getComponentType() {
        return Main.BROKEN_ITEM_COMPONENT;
    }

    /**
     * Is component equal boolean.
     *
     * @param other the other
     * @return the boolean
     */
    @Override
    public boolean isComponentEqual(Component other) {
        return isStackModelDataSet(itemStack);
    }

    /**
     * Is stack model data set boolean.
     *
     * @param itemStack the item stack
     * @return the boolean
     */
    public boolean isStackModelDataSet(ItemStack itemStack) {
        return customModelData == getStackModelData(itemStack);
    }

    /**
     * Gets stack model data.
     *
     * @param itemStack the item stack
     * @return the stack model data
     */
    public int getStackModelData(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag == null)
            return 0;

        return tag.getInt("CustomModelData");
    }

    /**
     * Sets stack model data.
     *
     * @param itemStack the item stack
     * @param modelData the model data
     */
    public void setStackModelData(ItemStack itemStack, int modelData) {
        CompoundTag tag = itemStack.getTag();
        if (tag == null)
            return;

        tag.putInt("CustomModelData", modelData);
    }

    /**
     * Remove stack model data.
     *
     * @param itemStack the item stack
     */
    public void removeStackModelData(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag == null)
            return;

        tag.remove("CustomModelData");
    }

    /**
     * Sets if stack broken.
     *
     * @param itemStack the item stack
     */
    public void setIfStackBroken(ItemStack itemStack) {
        Item item = itemStack.getItem();

        int brokenModelData = 1337;
        if (item instanceof EnergyHelper) {
            EnergyHelper tool = (EnergyHelper) item;

            if (tool.isUsable(itemStack))
                removeStackModelData(itemStack);
            else
                setStackModelData(itemStack, brokenModelData);
        }
    }
}
