package me.alexisevelyn.randomtech.utility.registryhelpers.main;

import me.alexisevelyn.randomtech.api.items.energy.EnergyHelper;
import me.alexisevelyn.randomtech.items.armor.powered.PoweredHelmet;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;

/**
 * The type Post registry helper.
 */
public class PostRegistryHelper {
    /**
     * Post register.
     */
    public void postRegister() {
        this.registerZoomHandler();
    }

    @SuppressWarnings("PMD.SwitchStmtsShouldHaveDefault") // Cause the switch statement doesn't need a default statement
    private void registerZoomHandler() {
        // To Handle Hotkey Presses From Client
        ServerSidePacketRegistry.INSTANCE.register(PreRegistryHelper.keybindPacketIdentifier, (packetContext, attachedData) -> {
            byte[] key = attachedData.readByteArray();
            String encodedString = new String(key, StandardCharsets.UTF_8);

            packetContext.getTaskQueue().execute(() -> {
                // Execute on the main thread

                // System.out.println("Packet Received: " + Arrays.toString(key));
                switch (encodedString) {
                    case PreRegistryHelper.zoom:
                        zoomPressed(packetContext, attachedData);
                        break;
                    case PreRegistryHelper.unzoom:
                        zoomReleased(packetContext, attachedData);
                        break;
                }
            });
        });
    }

    /**
     * Zoom pressed.
     *
     * @param packetContext the packet context
     * @param attachedData  the attached data
     */
    public void zoomPressed(PacketContext packetContext, PacketByteBuf attachedData) {
        ItemStack helmetStack = packetContext.getPlayer().getEquippedStack(EquipmentSlot.HEAD);

        if (helmetStack == null || !(helmetStack.getItem() instanceof PoweredHelmet))
            return;

        CompoundTag rootTag = helmetStack.getTag();

        if (rootTag == null)
            return;

        if (rootTag.getInt("zoom") == 0)
            rootTag.putInt("zoom", 1);
    }

    /**
     * Zoom released.
     *
     * @param packetContext the packet context
     * @param attachedData  the attached data
     */
    public void zoomReleased(PacketContext packetContext, PacketByteBuf attachedData) {
        ItemStack helmetStack = packetContext.getPlayer().getEquippedStack(EquipmentSlot.HEAD);

        if (helmetStack == null || !(helmetStack.getItem() instanceof PoweredHelmet))
            return;

        CompoundTag rootTag = helmetStack.getTag();

        if (rootTag == null)
            return;

        if (rootTag.getInt("zoom") > 0)
            rootTag.remove("zoom");
    }
}
