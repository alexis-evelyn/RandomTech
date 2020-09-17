package me.alexisevelyn.randomtech.utility;

import net.minecraft.item.ToolMaterials;

/**
 * Used to determine the mining levels of the tools provided by vanilla and
 */
public enum MiningLevel {
    WOOD(ToolMaterials.WOOD.getMiningLevel()),
    STONE(ToolMaterials.STONE.getMiningLevel()),
    IRON(ToolMaterials.IRON.getMiningLevel()),
    DIAMOND(ToolMaterials.DIAMOND.getMiningLevel()),
    GOLD(ToolMaterials.GOLD.getMiningLevel()),
    NETHERITE(ToolMaterials.NETHERITE.getMiningLevel()),
    POWERED(5);

    private final int miningLevel;

    /**
     * Instantiates a new Mining level.
     *
     * @param id the id
     */
    MiningLevel(int id) {
        this.miningLevel = id;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return miningLevel;
    }
}
