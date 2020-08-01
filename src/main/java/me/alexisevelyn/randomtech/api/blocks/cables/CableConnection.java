package me.alexisevelyn.randomtech.api.blocks.cables;

import net.minecraft.util.StringIdentifiable;

public enum CableConnection implements StringIdentifiable {
    MACHINE("machine"), // When connected to Machine or Inventory Holder
    CABLE("cable"), // When connected to Other Cable
    NONE("none"); // When Not Connected To Anything

    private final String name;

    private CableConnection(String name) {
        this.name = name;
    }

    public String toString() {
        return this.asString();
    }

    public String asString() {
        return this.name;
    }

    public boolean isConnected() {
        return this != NONE;
    }
}
