package me.alexisevelyn.randomtech.items.tools;

import me.alexisevelyn.randomtech.toolmaterials.FirstToolMaterial;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

public class ShovelBase extends ShovelItem {

    public ShovelBase(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public ShovelBase(ToolMaterial material) {
        super(material, -1, -2.2F, new Settings().group(ItemGroup.TOOLS));
    }

    public ShovelBase(Settings settings) {
        super(new FirstToolMaterial(), -1, -2.2F, settings);
    }
}
