package pt.pa.graph;

import java.util.List;

public class DijkstraResult<Hub> {

    double cost;
    List<Vertex<Hub>> path;

    public DijkstraResult(double cost, List<Vertex<Hub>> path) {
        this.cost = cost;
        this.path = path;
    }

    public double getCost() {
        return cost;
    }

    public List<Vertex<Hub>> getPath() {
        return path;
    }
}