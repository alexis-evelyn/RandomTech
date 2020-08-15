package me.alexisevelyn.randomtech.api.utilities.pathfinding.dijkstra;

/**
 * The type Edge.
 */
public class Edge {
    private final String id;
    private final Vertex source;
    private final Vertex destination;
    private final int weight;

    /**
     * Instantiates a new Edge.
     *
     * @param id          the id
     * @param source      the source
     * @param destination the destination
     * @param weight      the weight
     */
    public Edge(String id, Vertex source, Vertex destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets destination.
     *
     * @return the destination
     */
    public Vertex getDestination() {
        return destination;
    }

    /**
     * Gets source.
     *
     * @return the source
     */
    public Vertex getSource() {
        return source;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return source + " -> " + destination;
    }
}
