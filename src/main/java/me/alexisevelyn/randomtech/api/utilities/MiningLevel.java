package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.item.ToolMaterials;

public enum MiningLevel {
    WOOD(ToolMaterials.WOOD.getMiningLevel()),
    STONE(ToolMaterials.STONE.getMiningLevel()),
    IRON(ToolMaterials.IRON.getMiningLevel()),
    DIAMOND(ToolMaterials.DIAMOND.getMiningLevel()),
    GOLD(ToolMaterials.GOLD.getMiningLevel()),
    NETHERITE(ToolMaterials.NETHERITE.getMiningLevel()),
    POWERED(5);

    private final int miningLevel;

    MiningLevel(int id) {
        this.miningLevel = id;
    }

    public int getValue() {
        return miningLevel;
    }
}
