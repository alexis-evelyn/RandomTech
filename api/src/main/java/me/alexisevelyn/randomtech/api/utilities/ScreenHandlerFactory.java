package me.alexisevelyn.randomtech.api.utilities;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntity;
import org.apiguardian.api.API;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;

/**
 * Helper class to create a {@link BuiltScreenHandler} for RebornCore
 */
public class ScreenHandlerFactory {
    /**
     * Creates a {@link BuiltScreenHandler} for RebornCore
     *
     * Used to aid in producing screenhandlers for Fabric's {@link ScreenHandlerRegistry}.
     *
     * <br><br>
     * Example:
     * <br>
     * <code>
     * ScreenHandlerRegistry.ExtendedClientHandlerFactory<BuiltScreenHandler> screenHandlerFactory = new ScreenHandlerFactory().getScreenHandlerFactory();
     * screenHandlerType = ScreenHandlerRegistry.registerExtended(new Identifier(Main.MODID, "basic_computer_gui_handler"), screenHandlerFactory);
     * </code>
     *
     * @return the screen handler factory
     */
    @API(status = API.Status.MAINTAINED)
    public ScreenHandlerRegistry.ExtendedClientHandlerFactory<BuiltScreenHandler> getScreenHandlerFactory() {
        return (syncID, playerInventory, packetByteBuf) -> {
            final BlockEntity blockEntity = playerInventory.player.world.getBlockEntity(packetByteBuf.readBlockPos());

            if (blockEntity == null)
                return null;

            return ((BuiltScreenHandlerProvider) blockEntity).createScreenHandler(syncID, playerInventory.player);
        };
    }
}
