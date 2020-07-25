package me.alexisevelyn.randomtech.items.armor.powered;

import io.netty.buffer.Unpooled;
import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.utility.registryhelpers.client.ClientRegistryHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.PreRegistryHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import team.reborn.energy.EnergyTier;

public class PoweredHelmet extends GenericPoweredArmor {
    private static final int energyCapacity = 512;
    private static final EnergyTier energyTier = EnergyTier.HIGH;
    private static final int cost = 1;
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_helmet";

    public PoweredHelmet(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, energyCapacity, energyTier, cost, settings, dischargedTranslationKey);
    }

    @Override
    public float changeFov(float oldFOV, ItemStack stack, PlayerEntity playerEntity) {
        return super.changeFov(oldFOV, stack, playerEntity);
    }

    @Override
    public boolean isFireproof() {
        return true;
    }

    @Override
    public void tickArmor(ItemStack stack, PlayerEntity playerEntity) {
        super.tickArmor(stack, playerEntity);

        if (this.slot == EquipmentSlot.HEAD) {
            sendZoomKeybindToServer();
        }
    }

    @Environment(EnvType.CLIENT)
    public void sendZoomKeybindToServer() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());

            // TODO: Figure out what I need to detect for key press/release
            if (ClientRegistryHelper.poweredHelmetZoom.isPressed()) {
                passedData.writeString(PreRegistryHelper.zoom);

                ClientSidePacketRegistry.INSTANCE.sendToServer(PreRegistryHelper.keybindPacketIdentifier, passedData);
            } else if (ClientRegistryHelper.poweredHelmetZoom.wasPressed()) {
                passedData.writeString(PreRegistryHelper.unzoom);

                ClientSidePacketRegistry.INSTANCE.sendToServer(PreRegistryHelper.keybindPacketIdentifier, passedData);
            }
        });
    }
}
