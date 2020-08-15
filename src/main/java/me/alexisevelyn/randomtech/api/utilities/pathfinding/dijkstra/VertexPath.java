package me.alexisevelyn.randomtech.api.utilities.pathfinding.dijkstra;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * The type Vertex path.
 */
public class VertexPath implements Iterable<Vertex> {
    private final LinkedList<Vertex> path;
    private int currentIndex = 0; // Set to 0, so we can skip the first cable. If you need the first cable, grab it manually.

    /**
     * Instantiates a new Vertex path.
     */
    public VertexPath() {
        this.path = new LinkedList<>();
    }

    /**
     * Add.
     *
     * @param vertex the vertex
     */
    public void add(Vertex vertex) {
        this.path.add(vertex);
    }

    /**
     * Reverse.
     */
    public void reverse() {
        Collections.reverse(this.path);
    }

    /**
     * Sets current index.
     *
     * @param index the index
     */
    public void setCurrentIndex(int index) {
        this.currentIndex = index;
    }

    /**
     * Gets next.
     *
     * @return the next
     */
    @Nullable
    public Vertex getNext() {
        try {
            currentIndex++;
            return get(currentIndex);
        } catch (IndexOutOfBoundsException exception) {
            return null;
        }
    }

    /**
     * Gets position index.
     *
     * @param blockPos the block pos
     * @return the position index
     */
    public int getPositionIndex(Object blockPos) {
        int index = -1;
        for (int i = 0; i < this.path.size(); i++) {
            if (this.path.get(i).getPosition().equals(blockPos)) {
                index = i;
            }
        }

        // currentIndex = index;
        return index;
    }

    /**
     * Gets index.
     *
     * @param vertex the vertex
     * @return the index
     */
    public int getIndex(Vertex vertex) {
        int index = -1;
        for (int i = 0; i < this.path.size(); i++) {
            if (this.path.get(i).equals(vertex)) {
                index = i;
            }
        }

        // currentIndex = index;
        return index;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public LinkedList<Vertex> getPath() {
        return this.path;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NotNull
    @Override
    public Iterator<Vertex> iterator() {
        return path.iterator();
    }

    /**
     * Size int.
     *
     * @return the int
     */
    public int size() {
        return path.size();
    }

    /**
     * Get vertex.
     *
     * @param i the
     * @return the vertex
     * @throws IndexOutOfBoundsException the index out of bounds exception
     */
    public Vertex get(int i) throws IndexOutOfBoundsException {
        return path.get(i);
    }
}
