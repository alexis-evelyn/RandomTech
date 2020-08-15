package me.alexisevelyn.randomtech.waila.virtualtile;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import me.alexisevelyn.randomtech.blockentities.VirtualTileBlockEntity;
import me.alexisevelyn.randomtech.blockitems.VirtualTile;
import me.alexisevelyn.randomtech.blocks.glass.PoweredGlass;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import me.alexisevelyn.randomtech.waila.WailaRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.awt.*;
import java.util.List;

/**
 * The type Virtual tile info.
 */
public class VirtualTileInfo implements IComponentProvider {
    public static final VirtualTileInfo INSTANCE = new VirtualTileInfo();

    /**
     * Gets stack.
     *
     * @param accessor the accessor
     * @param config   the config
     * @return the stack
     */
    @Override
    public ItemStack getStack(IDataAccessor accessor, IPluginConfig config) {
        return null;
    }

    /**
     * Append head.
     *
     * @param tooltip  the tooltip
     * @param accessor the accessor
     * @param config   the config
     */
    @Override
    public void appendHead(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {

    }

    /**
     * Append body.
     *
     * @param tooltip  the tooltip
     * @param accessor the accessor
     * @param config   the config
     */
    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        boolean configDisplayColor = config.get(WailaRegistry.CONFIG_DISPLAY_COLOR, true);

        if (configDisplayColor) {
            if (accessor.getBlock() == RegistryHelper.VIRTUAL_TILE_BLOCK) {
                VirtualTileBlockEntity virtualTileBlockEntity = (VirtualTileBlockEntity) accessor.getBlockEntity();

                Color color = virtualTileBlockEntity.getColor();

                if (color == null)
                    return;

                TranslatableText virtualTileColorLine = new TranslatableText("message.randomtech.virtual_tile_color", color.getRed(), color.getGreen(), color.getBlue());

                tooltip.add(virtualTileColorLine);
            }
        }
    }

    /**
     * Append tail.
     *
     * @param tooltip  the tooltip
     * @param accessor the accessor
     * @param config   the config
     */
    @Override
    public void appendTail(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {

    }
}
