package me.alexisevelyn.randomtech.items.tools;

import me.alexisevelyn.randomtech.toolmaterials.FirstToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class PoweredPickaxe extends PickaxeItem {

    public PoweredPickaxe(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public PoweredPickaxe(ToolMaterial material) {
        super(material, -1, -2.2F, new Item.Settings().group(ItemGroup.TOOLS));
    }

    public PoweredPickaxe(Settings settings) {
        super(new FirstToolMaterial(), -1, -2.2F, settings);
    }
}
