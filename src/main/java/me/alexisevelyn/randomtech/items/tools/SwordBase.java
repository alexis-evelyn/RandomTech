package me.alexisevelyn.randomtech.items.tools;

import me.alexisevelyn.randomtech.toolmaterials.FirstToolMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class SwordBase extends SwordItem {

    public SwordBase(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public SwordBase(ToolMaterial material) {
        super(material, -1, -2.2F, new Settings().group(ItemGroup.TOOLS));
    }

    public SwordBase(Settings settings) {
        super(new FirstToolMaterial(), -1, -2.2F, settings);
    }
}
