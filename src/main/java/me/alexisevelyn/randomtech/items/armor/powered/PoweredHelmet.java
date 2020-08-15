package me.alexisevelyn.randomtech.items.armor.powered;

import io.netty.buffer.Unpooled;
import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.api.utilities.ItemManager;
import me.alexisevelyn.randomtech.utility.registryhelpers.client.ClientRegistryHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.PreRegistryHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import team.reborn.energy.EnergyTier;

import java.nio.charset.StandardCharsets;

/**
 * The type Powered helmet.
 */
public class PoweredHelmet extends GenericPoweredArmor {
    private static final int energyCapacity = 512;
    private static final EnergyTier energyTier = EnergyTier.HIGH;
    private static final int cost = 1;
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_helmet";

    /**
     * Instantiates a new Powered helmet.
     *
     * @param material the material
     * @param slot     the slot
     * @param settings the settings
     */
    public PoweredHelmet(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, energyCapacity, energyTier, cost, settings, dischargedTranslationKey);

        // Only run on the client
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
            registerZoomKeybindSender();
    }

    /**
     * Change fov float.
     *
     * @param oldFOV       the old fov
     * @param stack        the stack
     * @param playerEntity the player entity
     * @return the float
     */
    @Override
    public float changeFov(float oldFOV, ItemStack stack, PlayerEntity playerEntity) {
        // This runs client side
        CompoundTag rootTag = stack.getTag();

        if (rootTag != null) {
            if (rootTag.getInt("zoom") > 0 && isUsable(stack)) {
                return 0;
            }
        }

        return super.changeFov(oldFOV, stack, playerEntity);
    }

    /**
     * Is fireproof boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isFireproof() {
        return true;
    }

    /**
     * Tick armor.
     *
     * @param stack        the stack
     * @param playerEntity the player entity
     */
    @Override
    public void tickArmor(ItemStack stack, PlayerEntity playerEntity) {
        super.tickArmor(stack, playerEntity);

        CompoundTag rootTag = stack.getTag();

        if (rootTag != null) {
            if (rootTag.getInt("zoom") > 0 && isUsable(stack)) {
                ItemManager.useEnergy(playerEntity, stack, cost);
            }
        }
    }

    /**
     * Register zoom keybind sender.
     */
    @Environment(EnvType.CLIENT)
    public void registerZoomKeybindSender() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());

            // Only Apply to Helmet
            if (this.slot != EquipmentSlot.HEAD)
                return;

            if (ClientRegistryHelper.poweredHelmetZoom.isPressed()) {
                passedData.writeByteArray(PreRegistryHelper.zoom.getBytes(StandardCharsets.UTF_8));

                ClientSidePacketRegistry.INSTANCE.sendToServer(PreRegistryHelper.keybindPacketIdentifier, passedData);
            } else if (ClientRegistryHelper.poweredHelmetZoom.wasPressed()) {
                passedData.writeByteArray(PreRegistryHelper.unzoom.getBytes(StandardCharsets.UTF_8));

                ClientSidePacketRegistry.INSTANCE.sendToServer(PreRegistryHelper.keybindPacketIdentifier, passedData);
            }
        });
    }
}
