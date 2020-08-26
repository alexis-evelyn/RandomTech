package me.alexisevelyn.randomtech.api.utilities;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntity;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;

/**
 * The type Screen handler factory.
 */
public class ScreenHandlerFactory {
    /**
     * Gets screen handler factory.
     *
     * @return the screen handler factory
     */
    public ScreenHandlerRegistry.ExtendedClientHandlerFactory<BuiltScreenHandler> getScreenHandlerFactory() {
        return (syncID, playerInventory, packetByteBuf) -> {
            final BlockEntity blockEntity = playerInventory.player.world.getBlockEntity(packetByteBuf.readBlockPos());

            if (blockEntity == null) {
                return null;
            }

            //screenHandler.setType(screenHandlerType);

            return ((BuiltScreenHandlerProvider) blockEntity).createScreenHandler(syncID, playerInventory.player);
        };
    }
}
