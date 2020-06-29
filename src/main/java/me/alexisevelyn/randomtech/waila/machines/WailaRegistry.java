package me.alexisevelyn.randomtech.waila.machines;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.overlay.tooltiprenderers.TooltipRendererProgressBar;
import mcp.mobius.waila.overlay.tooltiprenderers.TooltipRendererSpacer;
import mcp.mobius.waila.overlay.tooltiprenderers.TooltipRendererStack;
import me.alexisevelyn.randomtech.blocks.FirstBlock;
import net.minecraft.util.Identifier;

public class WailaRegistry implements IWailaPlugin {
    static final Identifier RENDER_POWER = new Identifier("power_level");

    static final Identifier CONFIG_DISPLAY_POWER = new Identifier("display_power");

    @Override
    public void register(IRegistrar iRegistrar) {
        // WAILA Configuration
        iRegistrar.addConfig(CONFIG_DISPLAY_POWER, true);

        // ToolTip Rendering
        iRegistrar.registerTooltipRenderer(RENDER_POWER, new TooltipRendererStack());

        iRegistrar.registerComponentProvider(Machines.INSTANCE, TooltipPosition.BODY, FirstBlock.class);
        iRegistrar.registerBlockDataProvider(Machines.INSTANCE, FirstBlock.class);
    }
}
