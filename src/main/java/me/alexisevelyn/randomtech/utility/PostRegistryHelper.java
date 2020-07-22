package me.alexisevelyn.randomtech.utility;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredTool;
import me.alexisevelyn.randomtech.api.utilities.cardinalcomponents.BrokenItemComponent;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.ItemComponentCallback;
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
    }
}
