package me.alexisevelyn.randomtech.waila;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.overlay.tooltiprenderers.TooltipRendererStack;
import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.api.blocks.glass.BasePoweredGlass;
import me.alexisevelyn.randomtech.api.blocks.machines.PowerAcceptorBlock;
import me.alexisevelyn.randomtech.blockitems.VirtualTile;
import me.alexisevelyn.randomtech.blocks.FuserBlock;
import me.alexisevelyn.randomtech.blocks.TeleporterBlock;
import me.alexisevelyn.randomtech.blocks.glass.PoweredGlass;
import me.alexisevelyn.randomtech.blocks.wires.CobaltWire;
import me.alexisevelyn.randomtech.waila.glass.PoweredGlassInfo;
import me.alexisevelyn.randomtech.waila.machines.Machines;
import me.alexisevelyn.randomtech.waila.virtualtile.VirtualTileInfo;
import me.alexisevelyn.randomtech.waila.wires.CobaltWireInfo;
import net.minecraft.util.Identifier;

public class WailaRegistry implements IWailaPlugin {
    public static final Identifier RENDER_POWER = new Identifier(Main.MODID, "power_level");

    public static final Identifier CONFIG_DISPLAY_POWER = new Identifier(Main.MODID, "display_power");
    public static final Identifier CONFIG_DISPLAY_REDSTONE_STRENGTH = new Identifier(Main.MODID, "display_redstone_signal_strength");
    public static final Identifier CONFIG_DISPLAY_TANK = new Identifier(Main.MODID, "display_tank");
    public static final Identifier CONFIG_DISPLAY_COLOR = new Identifier(Main.MODID, "display_color");

    @Override
    public void register(IRegistrar iRegistrar) {
        // Server Side Config Options
        iRegistrar.addSyncedConfig(CONFIG_DISPLAY_POWER, true);
        iRegistrar.addSyncedConfig(CONFIG_DISPLAY_TANK, true);

        // Client Only Config Options
        iRegistrar.addConfig(CONFIG_DISPLAY_REDSTONE_STRENGTH, true);
        iRegistrar.addConfig(CONFIG_DISPLAY_COLOR, true);

        // ToolTip Rendering
        iRegistrar.registerTooltipRenderer(RENDER_POWER, new TooltipRendererStack());

        // Powered Machines Base Class - Includes Teleporter and Fuser
        iRegistrar.registerComponentProvider(Machines.INSTANCE, TooltipPosition.BODY, PowerAcceptorBlock.class);
        iRegistrar.registerBlockDataProvider(Machines.INSTANCE, PowerAcceptorBlock.class);

        // Base Powered Glass - Includes Powered Glass
        iRegistrar.registerComponentProvider(PoweredGlassInfo.INSTANCE, TooltipPosition.BODY, BasePoweredGlass.class);

        // Cobalt Wire
        iRegistrar.registerComponentProvider(CobaltWireInfo.INSTANCE, TooltipPosition.BODY, CobaltWire.class);

        // Virtual Tile
        iRegistrar.registerComponentProvider(VirtualTileInfo.INSTANCE, TooltipPosition.BODY, VirtualTile.VirtualTileBlock.class);
    }
}
