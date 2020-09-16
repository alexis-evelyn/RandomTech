package me.alexisevelyn.randomtech.api.utilities.pathfinding.dijkstra;

import org.apiguardian.api.API;

import java.util.List;

/**
 * The type Graph.
 */
@API(status = API.Status.EXPERIMENTAL)
public class Graph {
    private final List<Vertex> vertexes;
    private final List<Edge> edges;

    /**
     * Instantiates a new Graph.
     *
     * @param vertexes the vertexes
     * @param edges    the edges
     */
    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    /**
     * Gets vertexes.
     *
     * @return the vertexes
     */
    public List<Vertex> getVertexes() {
        return vertexes;
    }

    /**
     * Gets edges.
     *
     * @return the edges
     */
    public List<Edge> getEdges() {
        return edges;
    }
}
