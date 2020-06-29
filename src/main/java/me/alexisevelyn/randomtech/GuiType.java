// TODO: FIGURE OUT HOW TO MAKE GUIS MYSELF!!!

package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.blockentities.FirstBlockEntity;
import me.alexisevelyn.randomtech.guis.FirstGui;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import reborncore.RebornCore;
import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

// This GuiType class is borrowed from TechReborn. I plan on replacing it when I learn how to develop my own Gui Handler!!!
public class GuiType<T extends BlockEntity> implements IMachineGuiHandler {
    private static final Map<Identifier, GuiType<?>> TYPES = new HashMap<>();

    public static final GuiType<FirstBlockEntity> FIRST_GUI = register("first_gui", () -> () -> FirstGui::new);

    private final Identifier identifier;
    private final Supplier<Supplier<GuiFactory<T>>> guiFactory;
    private final ScreenHandlerType<BuiltScreenHandler> screenHandlerType;

    private GuiType(Identifier identifier, Supplier<Supplier<GuiFactory<T>>> factorySupplierMeme) {
        this.identifier = identifier;
        this.guiFactory = factorySupplierMeme;
        this.screenHandlerType = ScreenHandlerRegistry.registerExtended(identifier, getScreenHandlerFactory());
        RebornCore.clientOnly(() -> () -> ScreenRegistry.register(screenHandlerType, getGuiFactory()));
    }

    private static <T extends BlockEntity> GuiType<T> register(String id, Supplier<Supplier<GuiFactory<T>>> factorySupplierMeme) {
        return register(new Identifier("randomtech", id), factorySupplierMeme);
    }

    public static <T extends BlockEntity> GuiType<T> register(Identifier identifier, Supplier<Supplier<GuiFactory<T>>> factorySupplierMeme) {
        if (TYPES.containsKey(identifier)) {
            throw new RuntimeException("Duplicate GUI Type!!!");
        }

        GuiType<T> type = new GuiType<>(identifier, factorySupplierMeme);
        TYPES.put(identifier, type);
        return type;
    }

    private ScreenHandlerRegistry.ExtendedClientHandlerFactory<BuiltScreenHandler> getScreenHandlerFactory() {
        return (syncId, playerInventory, packetByteBuf) -> {
            final BlockEntity blockEntity = playerInventory.player.world.getBlockEntity(packetByteBuf.readBlockPos());
            BuiltScreenHandler screenHandler = ((BuiltScreenHandlerProvider) blockEntity).createScreenHandler(syncId, playerInventory.player);

            //Set the screen handler type, not ideal but works lol
            screenHandler.setType(screenHandlerType);

            return screenHandler;
        };
    }

    @Environment(EnvType.CLIENT)
    private GuiFactory<T> getGuiFactory() {
        return guiFactory.get().get();
    }

    @Override
    public void open(PlayerEntity playerEntity, BlockPos blockPos, World world) {
        // TODO: Add Code For Sending GUI From Server
    }

    @Environment(EnvType.CLIENT)
    public interface GuiFactory<T extends BlockEntity> extends ScreenRegistry.Factory<BuiltScreenHandler, HandledScreen<BuiltScreenHandler>> {
        HandledScreen<?> create(int syncId, PlayerEntity playerEntity, T blockEntity);

        @Override
        default HandledScreen create(BuiltScreenHandler builtScreenHandler, PlayerInventory playerInventory, Text text) {
            PlayerEntity playerEntity = playerInventory.player;
            //noinspection unchecked
            T blockEntity = (T) builtScreenHandler.getBlockEntity();
            return create(builtScreenHandler.syncId, playerEntity, blockEntity);
        }
    }
}
