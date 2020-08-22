package me.alexisevelyn.randomtech.api.utilities.pathfinding.dijkstra;

/**
 * The type Vertex.
 */
public class Vertex {
    final private Object position;
    final private String name;

    /**
     * Instantiates a new Vertex.
     *
     * @param position the position
     * @param name     the name
     */
    public Vertex(Object position, String name) {
        this.position = position;
        this.name = name;
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public Object getPosition() {
        return position;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    /**
     * Equals boolean.
     *
     * @param obj the obj
     * @return the boolean
     */
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

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return name;
    }
}
