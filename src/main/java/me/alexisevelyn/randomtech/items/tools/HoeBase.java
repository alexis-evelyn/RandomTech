package me.alexisevelyn.randomtech.items.tools;

import me.alexisevelyn.randomtech.toolmaterials.FirstToolMaterial;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class HoeBase extends HoeItem {

    public HoeBase(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public HoeBase(ToolMaterial material) {
        super(material, -1, -2.2F, new Settings().group(ItemGroup.TOOLS));
    }

    public HoeBase(Settings settings) {
        super(new FirstToolMaterial(), -1, -2.2F, settings);
    }
}
