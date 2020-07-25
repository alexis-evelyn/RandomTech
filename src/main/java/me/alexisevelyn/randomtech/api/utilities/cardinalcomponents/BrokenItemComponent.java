package me.alexisevelyn.randomtech.api.utilities.cardinalcomponents;

import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredTool;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.PostRegistryHelper;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.Component;
import nerdhub.cardinal.components.api.component.extension.CopyableComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

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

    public BrokenItemComponent(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void fromTag(CompoundTag compoundTag) {
        // this.customModelData = compoundTag.getInt("CustomModelData");

        this.customModelData = getStackModelData(itemStack);
    }

    @Override
    public @NotNull CompoundTag toTag(CompoundTag compoundTag) {
        // compoundTag.putInt("CustomModelData", this.customModelData);

        setIfStackBroken(itemStack);
        return compoundTag;
    }

    public @NotNull ComponentType<?> getComponentType() {
        return PostRegistryHelper.BROKEN_ITEM_COMPONENT;
    }

    @Override
    public boolean isComponentEqual(Component other) {
        return isStackModelDataSet(itemStack);
    }

    public boolean isStackModelDataSet(ItemStack itemStack) {
        return customModelData == getStackModelData(itemStack);
    }

    public int getStackModelData(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag == null)
            return 0;

        return tag.getInt("CustomModelData");
    }

    public void setStackModelData(ItemStack itemStack, int modelData) {
        CompoundTag tag = itemStack.getTag();
        if (tag == null)
            return;

        tag.putInt("CustomModelData", modelData);
    }

    public void removeStackModelData(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag == null)
            return;

        tag.remove("CustomModelData");
    }

    public void setIfStackBroken(ItemStack itemStack) {
        Item item = itemStack.getItem();

        int brokenModelData = 1337;
        if (item instanceof GenericPoweredTool) {
            GenericPoweredTool tool = (GenericPoweredTool) item;

            if (tool.isUsable(itemStack))
                removeStackModelData(itemStack);
            else
                setStackModelData(itemStack, brokenModelData);
        }

        if (item instanceof GenericPoweredArmor) {
            GenericPoweredArmor armor = (GenericPoweredArmor) item;

            if (armor.isUsable(itemStack))
                removeStackModelData(itemStack);
            else
                setStackModelData(itemStack, brokenModelData);
        }
    }
}
