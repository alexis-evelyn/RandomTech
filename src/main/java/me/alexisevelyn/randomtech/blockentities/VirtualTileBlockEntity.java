package me.alexisevelyn.randomtech.blockentities;

import me.alexisevelyn.randomtech.utility.BlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

import java.awt.*;

public class VirtualTileBlockEntity extends BlockEntity {
    private Color color = new Color(57, 148, 25); // #399419 For Cool Green

    public VirtualTileBlockEntity() {
        super(BlockEntities.VIRTUAL_TILE);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        // Save the current value of the number to the tag
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

        color = new Color(red, green, blue);
    }

    public Color getColor() {
        return color;
    }
}
