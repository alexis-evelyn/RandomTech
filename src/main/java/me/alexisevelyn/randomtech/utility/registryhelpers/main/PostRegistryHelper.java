package me.alexisevelyn.randomtech.utility.registryhelpers.main;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredTool;
import me.alexisevelyn.randomtech.api.utilities.cardinalcomponents.BrokenItemComponent;
import me.alexisevelyn.randomtech.items.armor.powered.PoweredHelmet;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.ItemComponentCallback;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PostRegistryHelper {
    public static final ComponentType<BrokenItemComponent> BROKEN_ITEM_COMPONENT =
            ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(Main.MODID, "broken_item_component"), BrokenItemComponent.class);

    public void postRegister() {
        // Register All Powered Tools and Armor With Callback
        Registry.ITEM.stream().forEach(item -> {
            if (item instanceof GenericPoweredTool || item instanceof GenericPoweredArmor) {
                ItemComponentCallback.event(item).register((stack, components) -> components.put(BROKEN_ITEM_COMPONENT, new BrokenItemComponent(stack)));
            }
        });

        ServerSidePacketRegistry.INSTANCE.register(PreRegistryHelper.keybindPacketIdentifier, (packetContext, attachedData) -> {
            String key = attachedData.readString();

            packetContext.getTaskQueue().execute(() -> {
                // Execute on the main thread

                switch (key) {
                    case PreRegistryHelper.zoom:
                        zoomPressed(packetContext, attachedData);
                    case PreRegistryHelper.unzoom:
                        zoomReleased(packetContext, attachedData);
                }
            });
        });
    }

    public void zoomPressed(PacketContext packetContext, PacketByteBuf attachedData) {
        ItemStack helmetStack = packetContext.getPlayer().getEquippedStack(EquipmentSlot.HEAD);

        if (helmetStack == null || !(helmetStack.getItem() instanceof PoweredHelmet))
            return;

        PoweredHelmet helmet = (PoweredHelmet) helmetStack.getItem();

        // Set Zooming
        // helmet.setPlayerZooming(true);

        packetContext.getPlayer().sendMessage(new LiteralText("Zoom Pressed"), true);
    }

    public void zoomReleased(PacketContext packetContext, PacketByteBuf attachedData) {
        ItemStack helmetStack = packetContext.getPlayer().getEquippedStack(EquipmentSlot.HEAD);

        if (helmetStack == null || !(helmetStack.getItem() instanceof PoweredHelmet))
            return;

        PoweredHelmet helmet = (PoweredHelmet) helmetStack.getItem();

        // Set Zooming
        // helmet.setPlayerZooming(false);

        packetContext.getPlayer().sendMessage(new LiteralText("Zoom Released"), true);
    }
}
