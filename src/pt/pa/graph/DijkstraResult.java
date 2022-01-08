package pt.pa.graph;

import java.util.List;

public class DijkstraResult<V> {

    double cost;
    List<Vertex<V>> path;

    public DijkstraResult(double cost, List<Vertex<V>> path) {
        this.cost = cost;
        this.path = path;
    }

    public double getCost() {
        return cost;
    }

    public List<Vertex<V>> getPath() {
        return path;
    }
}