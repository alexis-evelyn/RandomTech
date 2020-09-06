package me.alexisevelyn.randomtech.waila.glass;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import me.alexisevelyn.randomtech.blocks.glass.PoweredGlass;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import me.alexisevelyn.randomtech.waila.WailaRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;

/**
 * The type Powered glass info.
 */
public class PoweredGlassInfo implements IComponentProvider {
    public static final PoweredGlassInfo INSTANCE = new PoweredGlassInfo();

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
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    @Override
    public void appendHead(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        // Intentionally Left Empty
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
        boolean configDisplayRedstoneStrength = config.get(WailaRegistry.CONFIG_DISPLAY_REDSTONE_STRENGTH, true);

        if (configDisplayRedstoneStrength && accessor.getBlock().equals(RegistryHelper.POWERED_GLASS)) {
            PoweredGlass poweredGlass = (PoweredGlass) accessor.getBlock();

            int signalStrengthLine = poweredGlass.getRedstoneStrength(accessor.getBlockState());

            TranslatableText redstoneStrengthLine = new TranslatableText("tooltip.waila.redstone_signal_strength", signalStrengthLine);

            tooltip.add(redstoneStrengthLine);
        }
    }

    /**
     * Append tail.
     *
     * @param tooltip  the tooltip
     * @param accessor the accessor
     * @param config   the config
     */
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    @Override
    public void appendTail(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        // Intentionally Left Empty
    }
}
