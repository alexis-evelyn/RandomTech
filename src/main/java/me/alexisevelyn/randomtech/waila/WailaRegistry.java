package me.alexisevelyn.randomtech.waila;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.overlay.tooltiprenderers.TooltipRendererStack;
import me.alexisevelyn.randomtech.blocks.TeleporterBlock;
import me.alexisevelyn.randomtech.blocks.glass.PoweredGlass;
import me.alexisevelyn.randomtech.blocks.wires.CobaltWire;
import me.alexisevelyn.randomtech.waila.glass.PoweredGlassInfo;
import me.alexisevelyn.randomtech.waila.machines.Machines;
import me.alexisevelyn.randomtech.waila.wires.CobaltWireInfo;
import net.minecraft.util.Identifier;

public class WailaRegistry implements IWailaPlugin {
    public static final Identifier RENDER_POWER = new Identifier("randomtech", "power_level");

    public static final Identifier CONFIG_DISPLAY_POWER = new Identifier("randomtech", "display_power");
    public static final Identifier CONFIG_DISPLAY_REDSTONE_STRENGTH = new Identifier("randomtech", "display_redstone_signal_strength");

    @Override
    public void register(IRegistrar iRegistrar) {
        // WAILA Configuration
        iRegistrar.addSyncedConfig(CONFIG_DISPLAY_POWER, true);
        iRegistrar.addConfig(CONFIG_DISPLAY_REDSTONE_STRENGTH, true);

        // ToolTip Rendering
        iRegistrar.registerTooltipRenderer(RENDER_POWER, new TooltipRendererStack());

        // Teleporter
        iRegistrar.registerComponentProvider(Machines.INSTANCE, TooltipPosition.BODY, TeleporterBlock.class);
        iRegistrar.registerBlockDataProvider(Machines.INSTANCE, TeleporterBlock.class);

        // Powered Glass
        iRegistrar.registerComponentProvider(PoweredGlassInfo.INSTANCE, TooltipPosition.BODY, PoweredGlass.class);

        // Cobalt Wire
        iRegistrar.registerComponentProvider(CobaltWireInfo.INSTANCE, TooltipPosition.BODY, CobaltWire.class);
    }
}
