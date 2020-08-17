package me.alexisevelyn.randomtech.utility.registryhelpers.main;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.api.items.energy.EnergyHelper;
import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredTool;
import me.alexisevelyn.randomtech.api.utilities.cardinalcomponents.BrokenItemComponent;
import me.alexisevelyn.randomtech.blocks.ores.CobaltOre;
import me.alexisevelyn.randomtech.items.armor.powered.PoweredHelmet;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.ItemComponentCallback;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * The type Post registry helper.
 */
public class PostRegistryHelper {
    public static final ComponentType<BrokenItemComponent> BROKEN_ITEM_COMPONENT =
            ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(Main.MODID, "broken_item_component"), BrokenItemComponent.class);

    /**
     * Post register.
     */
    public void postRegister() {
        // Register All Powered Tools and Armor With Callback
        Registry.ITEM.stream().forEach(item -> {
            if (item instanceof EnergyHelper) {
                ItemComponentCallback.event(item).register((stack, components) -> components.put(BROKEN_ITEM_COMPONENT, new BrokenItemComponent(stack)));
            }
        });

        // TODO (IMPORTANT): Fix this for 1.16.2
        // Register Post-Generation in Existing Biomes
        // Registry.BIOME.forEach(this::handleBiome);

        // TODO (IMPORTANT): Fix this for 1.16.2
        // Register Post-Generation in Future Added Biomes
        // RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> handleBiome(biome));

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
    public void zoomPressed(PacketContext packetContext, @SuppressWarnings("unused") PacketByteBuf attachedData) {
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
    public void zoomReleased(PacketContext packetContext, @SuppressWarnings("unused") PacketByteBuf attachedData) {
        ItemStack helmetStack = packetContext.getPlayer().getEquippedStack(EquipmentSlot.HEAD);

        if (helmetStack == null || !(helmetStack.getItem() instanceof PoweredHelmet))
            return;

        CompoundTag rootTag = helmetStack.getTag();

        if (rootTag == null)
            return;

        if (rootTag.getInt("zoom") > 0)
            rootTag.remove("zoom");
    }

    /**
     * Handle biome.
     *
     * @param biome the biome
     */
    private void handleBiome(Biome biome) {
        // Add Structure Features to Biomes Here
        CobaltOre.addOreFeature(biome);
    }
}
