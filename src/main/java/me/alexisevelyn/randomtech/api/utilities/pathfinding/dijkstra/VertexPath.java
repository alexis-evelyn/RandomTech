package me.alexisevelyn.randomtech.api.utilities.pathfinding.dijkstra;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class VertexPath implements Iterable<Vertex> {
    private final LinkedList<Vertex> path;
    private int currentIndex = 0; // Set to 0, so we can skip the first cable. If you need the first cable, grab it manually.

    public VertexPath() {
        this.path = new LinkedList<>();
    }

    public void add(Vertex vertex) {
        this.path.add(vertex);
    }

    public void reverse() {
        Collections.reverse(this.path);
    }

    public void setCurrentIndex(int index) {
        this.currentIndex = index;
    }

    @Nullable
    public Vertex getNext() {
        try {
            currentIndex++;
            return get(currentIndex);
        } catch (IndexOutOfBoundsException exception) {
            return null;
        }
    }

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

    public int size() {
        return path.size();
    }

    public Vertex get(int i) throws IndexOutOfBoundsException {
        currentIndex = i;
        return path.get(i);
    }
}
