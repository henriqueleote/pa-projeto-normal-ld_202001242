package pt.pa.graph;

import pt.pa.model.Hub;
import pt.pa.model.Route;

import java.util.*;

public class Dijkstra {

    /*
     * 6 - Cálculo de caminhos mais curtos entre um par de hubs
     */

    public static DijkstraResult<Hub> calculateShortestPathFromOrigin(Graph<Hub, Route> graph, Vertex<Hub> origin, Vertex<Hub> dest) {
        Map<Vertex<Hub>, Double> minDist = new HashMap<>();
        Map<Vertex<Hub>, Vertex<Hub>> predecessors = new HashMap<>();
        List<Vertex<Hub>> unvisited = new ArrayList<>();
        for(Vertex<Hub> v : graph.vertices()){
            unvisited.add(v);
            predecessors.put(v, null);
            minDist.put(v, Double.MAX_VALUE);
        }
        minDist.put(origin, 0.0);

        while(!unvisited.isEmpty()){
            Vertex<Hub> currentV = findMinCostVertex(minDist, unvisited);
            for (Edge<Route, Hub> e : graph.incidentEdges(currentV)) {
                Vertex<Hub> oppositeV = graph.opposite(currentV, e);
                if(unvisited.contains(oppositeV))
                {
                    double currentCost = minDist.get(currentV);
                    double edgeCost = e.element().getRoute();
                    double totalCost = currentCost + edgeCost;

                    if(totalCost < minDist.get(oppositeV))
                    {
                        minDist.put(oppositeV, totalCost);
                        predecessors.put(oppositeV, currentV);
                    }
                }
            }

            unvisited.remove(currentV);
        }
        Double cost = minDist.get(dest);
        if(cost == Double.MAX_VALUE)
        {
            return new DijkstraResult<>(cost, null);
        }

        List<Vertex<Hub>> path = new ArrayList<>();

        Vertex<Hub> current = dest;
        while(current != origin) {

            path.add(current);
            current = predecessors.get(current);
        }

        path.add(origin);

        Collections.reverse(path);

        return new DijkstraResult<>(cost, path);
    }


    public static Vertex<Hub> findMinCostVertex(Map<Vertex<Hub>, Double> distances, List<Vertex<Hub>> unvisited)
    {
        Vertex<Hub> minCostVertex = null;
        double minCostValue = Double.MAX_VALUE;

        for(Vertex<Hub> v : unvisited)
        {
            Double distV = distances.get(v);
            if(distV < minCostValue)
            {
                minCostValue = distV;;
                minCostVertex = v;
            }
        }

        return minCostVertex;
    }
    /*
     * 7 - Par de hubs mais distante
     */
    public static DijkstraResult<Hub> farthestHubs(Graph<Hub, Route> graph){
        double cost=0;
        List<Vertex<Hub>> listV = new ArrayList<>();
        for (Vertex<Hub> i:graph.vertices()) {
            for (Vertex<Hub> k:graph.vertices()) {
                if(calculateShortestPathFromOrigin(graph,i,k).cost>cost){
                    cost=calculateShortestPathFromOrigin(graph,i,k).cost;
                    listV.clear();
                    listV.add(i);
                    listV.add(k);
                }
            }

        }
        return new DijkstraResult<Hub>(cost, calculateShortestPathFromOrigin(graph,listV.get(0), listV.get(1)).path);
    }
}
