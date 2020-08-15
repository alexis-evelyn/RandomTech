package me.alexisevelyn.randomtech.blockentities;

import me.alexisevelyn.randomtech.blockitems.VirtualTile;
import me.alexisevelyn.randomtech.utility.BlockEntities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;

/**
 * The type Virtual tile block entity.
 */
public class VirtualTileBlockEntity extends BlockEntity implements BlockEntityClientSerializable {
    private Color color; // #399419 For Cool Green

    /**
     * Instantiates a new Virtual tile block entity.
     */
    public VirtualTileBlockEntity() {
        super(BlockEntities.VIRTUAL_TILE);
    }

    /**
     * To tag compound tag.
     *
     * @param tag the tag
     * @return the compound tag
     */
    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        if (getColor() == null)
            return tag;

        return setTagFromColor(tag, getColor());
    }

    /**
     * From tag.
     *
     * @param state the state
     * @param tag   the tag
     */
    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);

        setColor(readColorFromTag(tag));
    }

    /**
     * From client tag.
     *
     * @param compoundTag the compound tag
     */
// Ran by client
    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        setColor(readColorFromTag(compoundTag));

        updateBlockForRendering();
    }

    /**
     * Update block for rendering.
     */
    public void updateBlockForRendering() {
        if (world == null)
            return;

        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();

        if (!(block instanceof VirtualTile.VirtualTileBlock))
            return;

        VirtualTile.VirtualTileBlock virtualTileBlock = (VirtualTile.VirtualTileBlock) block;
        virtualTileBlock.updateBlockForRender(world, pos);
    }

    /**
     * To client tag compound tag.
     *
     * @param compoundTag the compound tag
     * @return the compound tag
     */
// Ran by server
    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        if (getColor() == null)
            return compoundTag;

        return setTagFromColor(compoundTag, getColor());
    }


    /**
     * Gets color.
     *
     * @return the color
     */
// Ran by client and server
    @Nullable
    public Color getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
// Ran by client and server
    public void setColor(@NotNull Color color) {
        this.color = color;
    }

    /**
     * Read color from tag color.
     *
     * @param compoundTag the compound tag
     * @return the color
     */
// Ran by client and server
    public Color readColorFromTag(@NotNull CompoundTag compoundTag) {
        if (compoundTag.contains("red") && compoundTag.contains("green") && compoundTag.contains("blue")) {
            int red = compoundTag.getInt("red");
            int green = compoundTag.getInt("green");
            int blue = compoundTag.getInt("blue");

            return new Color(red, green, blue);
        }

        // Set to default color if color not set
        return VirtualTile.defaultColor;
    }

    /**
     * Sets tag from color.
     *
     * @param compoundTag the compound tag
     * @param color       the color
     * @return the tag from color
     */
// Ran by client and server
    public CompoundTag setTagFromColor(@NotNull CompoundTag compoundTag, @NotNull Color color) {
        compoundTag.putInt("red", color.getRed());
        compoundTag.putInt("green", color.getGreen());
        compoundTag.putInt("blue", color.getBlue());

        return compoundTag;
    }
}
