package me.alexisevelyn.randomtech.api.utilities.pathfinding.dijkstra;

import net.minecraft.util.math.BlockPos;

public class Vertex {
    final private BlockPos position;
    final private String name;

    public Vertex(BlockPos position, String name) {
        this.position = position;
        this.name = name;
    }

    public BlockPos getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Vertex other = (Vertex) obj;
        if (position == null)
            return other.position == null;

        return position.equals(other.position);
    }

    @Override
    public String toString() {
        return name;
    }
}