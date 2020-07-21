package me.alexisevelyn.randomtech.api.utilities.cardinalcomponents;

import me.alexisevelyn.randomtech.utility.PostRegistryHelper;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.extension.CopyableComponent;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class BrokenItemComponent implements CopyableComponent<BrokenItemComponent> {
    private int customModelData = 1337;

    @Override
    public void fromTag(CompoundTag compoundTag) {
        if (compoundTag.getInt("CustomModelData") == 1337)
            this.customModelData = compoundTag.getInt("CustomModelData");
    }

    // This causes the item to have data repeatedly added to it making the item unusable for breaking blocks
    @Override
    public @NotNull CompoundTag toTag(CompoundTag compoundTag) {
        if (compoundTag.getInt("CustomModelData") != 0)
            return compoundTag;

        compoundTag.putInt("CustomModelData", this.customModelData);

        return compoundTag;
    }

    @Override
    public @NotNull ComponentType<?> getComponentType() {
        return PostRegistryHelper.BROKEN_ITEM_COMPONENT;
    }
}
