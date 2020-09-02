package me.alexisevelyn.randomtech.api.blocks.cables;

import net.minecraft.util.StringIdentifiable;

/**
 * The enum Cable connection.
 */
public enum CableConnection implements StringIdentifiable {
    INTERFACEABLE("interfaceable"), // When connected to an Interfaceable Block
    CABLE("cable"), // When connected to Other Cable
    NONE("none"); // When Not Connected To Anything

    private final String name;

    /**
     * Instantiates a new Cable connection.
     *
     * @param name the name
     */
    CableConnection(String name) {
        this.name = name;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    public String toString() {
        return this.asString();
    }

    /**
     * As string string.
     *
     * @return the string
     */
    public String asString() {
        return this.name;
    }

    /**
     * Is connected boolean.
     *
     * @return the boolean
     */
    public boolean isConnected() {
        return this != NONE;
    }
}
