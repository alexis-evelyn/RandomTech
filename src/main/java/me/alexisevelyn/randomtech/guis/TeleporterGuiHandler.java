package me.alexisevelyn.randomtech.guis;

import me.alexisevelyn.randomtech.GuiFactory;
import me.alexisevelyn.randomtech.ScreenHandlerFactory;
import me.alexisevelyn.randomtech.blockentities.TeleporterBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.client.screen.builder.BuiltScreenHandler;

public class TeleporterGuiHandler<TeleporterGui> implements IMachineGuiHandler {
    private final ScreenHandlerType<BuiltScreenHandler> screenHandlerType;

    public TeleporterGuiHandler() {
        ScreenHandlerRegistry.ExtendedClientHandlerFactory<BuiltScreenHandler> screenHandlerFactory = new ScreenHandlerFactory().getScreenHandlerFactory();
        screenHandlerType = ScreenHandlerRegistry.registerExtended(new Identifier("randomtech", "teleporter_gui_handler"), screenHandlerFactory);

        // Register GuiFactory with Fabric
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            // System.out.println("TeleportGuiHandler - init - Client Side");
            ScreenRegistry.register(screenHandlerType, getGuiFactory());
        }
    }

    @SuppressWarnings("unchecked") // The Unchecked Casts are in fact correctly casted. There's no way to properly check it afaik.
    private GuiFactory getGuiFactory() {
        // Responsible For Allowing The Gui to Be Linked to The Block Entity
        return (syncId, playerEntity, blockEntity) -> {
            TeleporterGui teleporterGui = (TeleporterGui) new me.alexisevelyn.randomtech.guis.TeleporterGui(syncId, playerEntity, (TeleporterBlockEntity) blockEntity);

            return (HandledScreen<BuiltScreenHandler>) teleporterGui;
        };
    }

    @Override
    public void open(PlayerEntity player, BlockPos pos, World world) {
        player.openHandledScreen(new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
                // System.out.println("TeleportGuiHandler - writeScreenOpeningData - Server Side");
                packetByteBuf.writeBlockPos(pos);
            }

            @Override
            public Text getDisplayName() {
                return new LiteralText("Teleporter - Add Me To Translator!!!");
            }

            @Nullable
            @Override
            public ScreenHandler createMenu(int syncID, PlayerInventory inv, PlayerEntity player) {
                // System.out.println("TeleportGuiHandler - createMenu - Server Side - Sync ID: " + syncID);

                final TeleporterBlockEntity teleporterBlockEntity = (TeleporterBlockEntity) player.getEntityWorld().getBlockEntity(pos);

                if (teleporterBlockEntity == null) {
                    System.out.println("TeleportGuiHandler - createMenu - teleporterBlockEntity is null!!!");
                    return null;
                }

                BuiltScreenHandler screenHandler = teleporterBlockEntity.createScreenHandler(syncID, player);

                screenHandler.setType(screenHandlerType);

                return screenHandler;
            }
        });
    }
}
