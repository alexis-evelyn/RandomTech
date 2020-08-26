package me.alexisevelyn.randomtech.api.utilities.pathfinding.dijkstra;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * The type Dijkstra algorithm.
 */
// Code pulled from https://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
// Found code from https://stackoverflow.com/questions/17480022/java-find-shortest-path-between-2-points-in-a-distance-weighted-map#comment61831401_17480244
public class DijkstraAlgorithm {
    // private final List<Vertex> nodes;
    private final List<Edge> edges;
    private Set<Vertex> settledNodes;
    private Set<Vertex> unSettledNodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Double> distance;

    /**
     * Instantiates a new Dijkstra algorithm.
     *
     * @param graph the graph
     */
    public DijkstraAlgorithm(Graph graph) {
        // create a copy of the array so that we can operate on this array
        // this.nodes = new ArrayList<>(graph.getVertexes());
        this.edges = new ArrayList<>(graph.getEdges());
    }

    /**
     * Execute.
     *
     * @param source the source
     */
    public void execute(Vertex source) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();

        distance.put(source, 0.0);
        unSettledNodes.add(source);

        while (unSettledNodes.size() > 0) {
            Vertex node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    /**
     * Find minimal distances.
     *
     * @param node the node
     */
    private void findMinimalDistances(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbors(node);
        for (Vertex target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }

    /**
     * Gets distance.
     *
     * @param node   the node
     * @param target the target
     * @return the distance
     */
    private double getDistance(Vertex node, Vertex target) {
        for (Edge edge : edges)
            if (edge.getSource().equals(node) && edge.getDestination().equals(target))
                return edge.getWeight();

        return Double.POSITIVE_INFINITY;
    }

    /**
     * Gets neighbors.
     *
     * @param node the node
     * @return the neighbors
     */
    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<>();

        for (Edge edge : edges)
            if (edge.getSource().equals(node) && !isSettled(edge.getDestination()))
                neighbors.add(edge.getDestination());

        return neighbors;
    }

    /**
     * Gets minimum.
     *
     * @param vertexes the vertexes
     * @return the minimum
     */
    @Nullable
    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;

        for (Vertex vertex : vertexes) {
            if (minimum == null)
                minimum = vertex;
            else
                if (getShortestDistance(vertex) < getShortestDistance(minimum))
                    minimum = vertex;
        }

        return minimum;
    }

    /**
     * Is settled boolean.
     *
     * @param vertex the vertex
     * @return the boolean
     */
    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }

    /**
     * Gets shortest distance.
     *
     * @param destination the destination
     * @return the shortest distance
     */
    private double getShortestDistance(Vertex destination) {
        Double d = distance.get(destination);
        if (d == null)
            return Double.MAX_VALUE;

        return d;
    }

    /**
     * Gets path.
     *
     * @param target the target
     * @return the path
     */
    /*
     * This method returns the path from the source to the selected target and
     * Empty Path if no path exists
     */
    @NotNull
    public VertexPath getPath(Vertex target) {
        VertexPath path = new VertexPath();
        Vertex step = target;

        // check if a path exists
        if (predecessors.get(step) == null)
            return path;

        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }

        // Put it into the correct order
        path.reverse();
        return path;
    }
}