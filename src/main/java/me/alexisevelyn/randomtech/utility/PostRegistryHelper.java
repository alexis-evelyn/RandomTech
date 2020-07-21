package me.alexisevelyn.randomtech.utility;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.api.utilities.cardinalcomponents.BrokenItemComponent;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.ItemComponentCallback;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class PostRegistryHelper {
    public static final ComponentType<BrokenItemComponent> BROKEN_ITEM_COMPONENT =
            ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(Main.MODID, "broken_item_component"), BrokenItemComponent.class);

    public void postRegister() {
        ItemComponentCallback.event(RegistryHelper.EDIBLE_POWER).register((stack, components) -> components.put(BROKEN_ITEM_COMPONENT, new BrokenItemComponent()));
    }
}
