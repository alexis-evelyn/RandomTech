package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.item.ToolMaterials;

/**
 * The enum Mining level.
 */
public enum MiningLevel {
    @SuppressWarnings("unused") WOOD(ToolMaterials.WOOD.getMiningLevel()),
    @SuppressWarnings("unused") STONE(ToolMaterials.STONE.getMiningLevel()),
    @SuppressWarnings("unused") IRON(ToolMaterials.IRON.getMiningLevel()),
    @SuppressWarnings("unused") DIAMOND(ToolMaterials.DIAMOND.getMiningLevel()),
    @SuppressWarnings("unused") GOLD(ToolMaterials.GOLD.getMiningLevel()),
    @SuppressWarnings("unused") NETHERITE(ToolMaterials.NETHERITE.getMiningLevel()),
    POWERED(5);

    private final int miningLevel;

    /**
     * Instantiates a new Mining level.
     *
     * @param id the id
     */
    @SuppressWarnings("unused")
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
