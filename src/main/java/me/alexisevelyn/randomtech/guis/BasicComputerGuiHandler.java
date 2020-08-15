package me.alexisevelyn.randomtech.guis;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.api.utilities.GuiFactory;
import me.alexisevelyn.randomtech.api.utilities.ScreenHandlerFactory;
import me.alexisevelyn.randomtech.blockentities.BasicComputerBlockEntity;
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

/**
 * The type Basic computer gui handler.
 *
 * @param <BasicComputerGui> the type parameter
 */
public class BasicComputerGuiHandler<BasicComputerGui> implements IMachineGuiHandler {
    private final ScreenHandlerType<BuiltScreenHandler> screenHandlerType;

    /**
     * Instantiates a new Basic computer gui handler.
     */
    public BasicComputerGuiHandler() {
        ScreenHandlerRegistry.ExtendedClientHandlerFactory<BuiltScreenHandler> screenHandlerFactory = new ScreenHandlerFactory().getScreenHandlerFactory();
        screenHandlerType = ScreenHandlerRegistry.registerExtended(new Identifier(Main.MODID, "basic_computer_gui_handler"), screenHandlerFactory);

        // Register GuiFactory with Fabric
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ScreenRegistry.register(screenHandlerType, getGuiFactory());
        }
    }

    /**
     * Gets gui factory.
     *
     * @return the gui factory
     */
    @SuppressWarnings("unchecked") // The Unchecked Casts are in fact correctly casted. There's no way to properly check it afaik.
    private GuiFactory getGuiFactory() {
        // Responsible For Allowing The Gui to Be Linked to The Block Entity
        return (syncId, playerEntity, blockEntity) -> {
            BasicComputerGui basicComputerGui = (BasicComputerGui) new me.alexisevelyn.randomtech.guis.BasicComputerGui(syncId, playerEntity, (BasicComputerBlockEntity) blockEntity);

            return (HandledScreen<BuiltScreenHandler>) basicComputerGui;
        };
    }

    /**
     * Open.
     *
     * @param player the player
     * @param pos    the pos
     * @param world  the world
     */
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
                return new LiteralText("Basic Computer - Add Me To Translator!!!");
            }

            @Nullable
            @Override
            public ScreenHandler createMenu(int syncID, PlayerInventory inv, PlayerEntity player) {
                final BasicComputerBlockEntity basicComputerBlockEntity = (BasicComputerBlockEntity) player.getEntityWorld().getBlockEntity(pos);

                if (basicComputerBlockEntity == null) {
                    System.out.println("BasicComputerGuiHandler - createMenu - basicComputerBlockEntity is null!!!");
                    return null;
                }

                BuiltScreenHandler screenHandler = basicComputerBlockEntity.createScreenHandler(syncID, player);

                if (screenHandler == null)
                    return null;

                screenHandler.setType(screenHandlerType);

                return screenHandler;
            }
        });
    }
}
