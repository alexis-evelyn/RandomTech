package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.item.ToolMaterials;

public enum MiningLevel {
    @SuppressWarnings("unused") WOOD(ToolMaterials.WOOD.getMiningLevel()),
    @SuppressWarnings("unused") STONE(ToolMaterials.STONE.getMiningLevel()),
    @SuppressWarnings("unused") IRON(ToolMaterials.IRON.getMiningLevel()),
    @SuppressWarnings("unused") DIAMOND(ToolMaterials.DIAMOND.getMiningLevel()),
    @SuppressWarnings("unused") GOLD(ToolMaterials.GOLD.getMiningLevel()),
    @SuppressWarnings("unused") NETHERITE(ToolMaterials.NETHERITE.getMiningLevel()),
    POWERED(5);

    private final int miningLevel;

    MiningLevel(int id) {
        this.miningLevel = id;
    }

    public int getValue() {
        return miningLevel;
    }
}
