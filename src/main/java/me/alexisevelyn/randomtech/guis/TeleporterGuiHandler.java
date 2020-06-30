package me.alexisevelyn.randomtech.guis;

import me.alexisevelyn.randomtech.blockentities.TeleporterBlockEntity;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.common.util.Color;

public class TeleporterGuiHandler implements IMachineGuiHandler {
    @Override
    public void open(PlayerEntity player, BlockPos pos, World world) {
        int syncID = 1;

        TeleporterBlockEntity teleporterBlockEntity = (TeleporterBlockEntity) world.getBlockEntity(pos);

        if (teleporterBlockEntity == null) {
            // Failed to Find Block Entity. Fatal Error.
            // Playing Sound to Indicate Failure
            player.playSound(SoundEvents.ENTITY_ENDERMAN_DEATH, 1.0F, 1.0F);
            return;
        }

        BuiltScreenHandler builtScreenHandler = teleporterBlockEntity.createScreenHandler(syncID, player);

        TeleporterGui teleporterGui = new TeleporterGui(player, teleporterBlockEntity, builtScreenHandler);

        //MatrixStack matrixStack = new MatrixStack();
        //teleporterGui.builder.drawText(matrixStack, teleporterGui, new LiteralText("Hello World!!!"), 100, 100, Color.BLUE.getColor());

        player.playSound(SoundEvents.ITEM_FLINTANDSTEEL_USE, 1.0F, 1.0F);
    }
}
