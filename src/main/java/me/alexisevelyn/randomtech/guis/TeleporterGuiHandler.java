package me.alexisevelyn.randomtech.guis;

import me.alexisevelyn.randomtech.blockentities.TeleporterBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import reborncore.RebornCore;
import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;

import java.util.function.Supplier;

// TODO: Look at Below Links
// https://github.com/FabLabsMC/ScreenHandlers/blob/1.16/example/src/main/java/io/github/fablabsmc/fablabs/impl/screenhandler/example/screen/BagScreenHandler.java
// https://github.com/TechReborn/TechReborn/blob/b309635eaaf313e91780d3aadc14a77789d11182/src/main/java/techreborn/client/GuiType.java

// TODO: Clean Up!!!!
// This code is a mess, but it works!!! :)

public class TeleporterGuiHandler<TeleporterGui> implements IMachineGuiHandler {
    private final ScreenHandlerType<BuiltScreenHandler> screenHandlerType;
    private final Identifier guiIdentifier;
    private final Supplier<Supplier<GuiFactory<TeleporterBlockEntity>>> guiFactory;

    public TeleporterGuiHandler() {
        // getFactory is ExtendedClientHandlerFactory. Is that going to allow me to link my Gui with my Handler?
        screenHandlerType = ScreenHandlerRegistry.registerExtended(new Identifier("randomtech", "teleporter_gui_handler"), getScreenHandlerFactory());
        guiIdentifier = new Identifier("randomtech", "teleporter_gui");
        guiFactory = () -> () -> new GuiFactory<TeleporterBlockEntity>() {
            @Override
            public HandledScreen<BuiltScreenHandler> create(int syncId, PlayerEntity playerEntity, BlockEntity blockEntity) {
                TeleporterGui teleporterGui = (TeleporterGui) new me.alexisevelyn.randomtech.guis.TeleporterGui(syncId, playerEntity, (TeleporterBlockEntity) blockEntity);

                return (HandledScreen<BuiltScreenHandler>) teleporterGui;
            }
        };

        RebornCore.clientOnly(() -> () -> ScreenRegistry.register(screenHandlerType, getGuiFactory()));
    }

    @Override
    public void open(PlayerEntity player, BlockPos pos, World world) {
        if (!world.isClient) {
            player.openHandledScreen(new ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
                    System.out.println("TeleportGuiHandler - writeScreenOpeningData - Server Side");
                    packetByteBuf.writeBlockPos(pos);
                }

                @Override
                public Text getDisplayName() {
                    return new LiteralText("Teleporter - Add Me To Translator!!!");
                }

                @Nullable
                @Override
                public ScreenHandler createMenu(int syncID, PlayerInventory inv, PlayerEntity player) {
                    System.out.println("TeleportGuiHandler - createMenu - Server Side - Sync ID: " + syncID);

                    final TeleporterBlockEntity teleporterBlockEntity = (TeleporterBlockEntity) player.getEntityWorld().getBlockEntity(pos);

                    if (teleporterBlockEntity == null) {
                        System.out.println("TeleportGuiHandler - createMenu - teleporterBlockEntity is null!!!");
                        return null;
                    }

                    BuiltScreenHandler screenHandler = teleporterBlockEntity.createScreenHandler(syncID, player);

                    //screenHandlerType.create(syncID, inv);
                    // Screen not registered for screen handler randomtech:teleporter_gui_handler!
                    screenHandler.setType(screenHandlerType);

                    return screenHandler;
                }
            });
        }
    }

    final ScreenHandlerRegistry.ExtendedClientHandlerFactory<BuiltScreenHandler> getScreenHandlerFactory() {
        return (syncID, playerInventory, packetByteBuf) -> {
            final BlockEntity blockEntity = playerInventory.player.world.getBlockEntity(packetByteBuf.readBlockPos());

            if (blockEntity == null) {
                return null;
            }

            final BuiltScreenHandler screenHandler = ((BuiltScreenHandlerProvider) blockEntity).createScreenHandler(syncID, playerInventory.player);
            screenHandler.setType(screenHandlerType);

            return screenHandler;
        };
    }

    public Identifier getIdentifier() {
        return guiIdentifier;
    }

    public GuiFactory<TeleporterBlockEntity> getGuiFactory() {
        return guiFactory.get().get();
    }

    @Environment(EnvType.CLIENT)
    public interface GuiFactory<T extends BlockEntity> extends ScreenRegistry.Factory<BuiltScreenHandler, HandledScreen<BuiltScreenHandler>> {
        HandledScreen<BuiltScreenHandler> create(int syncId, PlayerEntity playerEntity, BlockEntity blockEntity);

        @Override
        default HandledScreen<BuiltScreenHandler> create(BuiltScreenHandler builtScreenHandler, PlayerInventory playerInventory, Text text) {
            PlayerEntity playerEntity = playerInventory.player;

            BlockEntity blockEntity = (BlockEntity) builtScreenHandler.getBlockEntity();
            return create(builtScreenHandler.syncId, playerEntity, blockEntity);
        }
    }
}
