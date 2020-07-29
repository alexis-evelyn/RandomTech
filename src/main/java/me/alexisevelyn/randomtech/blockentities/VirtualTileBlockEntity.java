package me.alexisevelyn.randomtech.blockentities;

import me.alexisevelyn.randomtech.utility.BlockEntities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;

public class VirtualTileBlockEntity extends BlockEntity implements BlockEntityClientSerializable {
    private Color color; // #399419 For Cool Green

    public VirtualTileBlockEntity() {
        super(BlockEntities.VIRTUAL_TILE);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        Color color = getColor();

        if (color == null)
            return tag;

        // Save the current value of the color to the tag
        tag.putInt("red", color.getRed());
        tag.putInt("green", color.getGreen());
        tag.putInt("blue", color.getBlue());

        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);

        int red = tag.getInt("red");
        int green = tag.getInt("green");
        int blue = tag.getInt("blue");

        setColor(new Color(red, green, blue));
    }

    @Nullable
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;

        markDirty(); // This by default updates comparators

        // TODO: Make sure client receives correct color on block placement!!!
        if (world != null && !world.isClient)
            sync();
    }


    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        int red = compoundTag.getInt("red");
        int green = compoundTag.getInt("green");
        int blue = compoundTag.getInt("blue");

        setColor(new Color(red, green, blue));
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        Color color = getColor();

        if (color != null) {
            compoundTag.putInt("red", color.getRed());
            compoundTag.putInt("green", color.getGreen());
            compoundTag.putInt("blue", color.getBlue());
        }

        return compoundTag;
    }
}
