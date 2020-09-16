package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.item.ToolMaterials;
import org.apiguardian.api.API;

/**
 * The enum Mining level.
 */
@API(status = API.Status.INTERNAL)
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
    @API(status = API.Status.INTERNAL)
    MiningLevel(int id) {
        this.miningLevel = id;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    @API(status = API.Status.INTERNAL)
    public int getValue() {
        return miningLevel;
    }
}
