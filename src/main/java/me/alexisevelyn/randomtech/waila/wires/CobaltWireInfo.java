package me.alexisevelyn.randomtech.waila.wires;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import me.alexisevelyn.randomtech.blocks.glass.PoweredGlass;
import me.alexisevelyn.randomtech.blocks.wires.CobaltWire;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import me.alexisevelyn.randomtech.waila.WailaRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;

public class CobaltWireInfo implements IComponentProvider {
    public static final CobaltWireInfo INSTANCE = new CobaltWireInfo();

    @Override
    public ItemStack getStack(IDataAccessor accessor, IPluginConfig config) {
        return null;
    }

    @Override
    public void appendHead(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {

    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        boolean configDisplayRedstoneStrength = config.get(WailaRegistry.CONFIG_DISPLAY_REDSTONE_STRENGTH, true);

        if (configDisplayRedstoneStrength) {
            if (accessor.getBlock() == RegistryHelper.COBALT_WIRE) {
                int signalStrengthLine = CobaltWire.getPower(accessor.getBlockState());

                TranslatableText redstoneStrengthLine = new TranslatableText("tooltip.waila.redstone_signal_strength", signalStrengthLine);

                tooltip.add(redstoneStrengthLine);
            }
        }
    }

    @Override
    public void appendTail(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {

    }
}
