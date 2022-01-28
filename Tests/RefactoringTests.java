
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.pa.graph.Dijkstra;
import pt.pa.graph.DijkstraResult;
import pt.pa.graph.GraphAdjacencyList;
import pt.pa.graph.Vertex;
import pt.pa.model.Hub;
import pt.pa.model.Route;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class RefactoringTests {


    //O insetEdges da class GraphAdjacencyList tinha Dupicate Code, criou-se o getMyVertex
    @Test
    public void insertEdges()
    {
        GraphAdjacencyList<Hub, Route> distances = new GraphAdjacencyList<>();
        Vertex<Hub> tokyo;
        Vertex<Hub> newYork;
        Route d1 = new Route(10838);
        tokyo = distances.insertVertex(new Hub("Tokyo"));
        newYork = distances.insertVertex(new Hub("New York"));
        distances.insertEdge(tokyo.element(),newYork.element(),d1);
        assertTrue(distances.areAdjacent(newYork,tokyo));
    }

    @Test
    public void calculateShortestPathFromOrigin()
    {
        GraphAdjacencyList<Hub, Route> graph = new GraphAdjacencyList<>();
        Dijkstra dijkstra = new Dijkstra(graph);
        Vertex<Hub> a = graph.insertVertex(new Hub("A"));
        Vertex<Hub> b = graph.insertVertex(new Hub("B"));
        Vertex<Hub> c = graph.insertVertex(new Hub("C"));
        Vertex<Hub> d = graph.insertVertex(new Hub("D"));
        Vertex<Hub> e = graph.insertVertex(new Hub("E"));

        graph.insertEdge(a, b, new Route( 6));
        graph.insertEdge(a, d, new Route(1));
        graph.insertEdge(b, d, new Route(2));
        graph.insertEdge(b, e, new Route(2));
        graph.insertEdge(d, e, new Route(1));
        graph.insertEdge(b, c, new Route(5));
        graph.insertEdge(c, e, new Route(5));

        List<Vertex> list=new ArrayList<>();
        list.add(a);
        list.add(d);
        list.add(e);
        list.add(c);

        DijkstraResult<Hub> result = dijkstra.calculateShortestPathFromOrigin( a, c);


      assertTrue(result.getCost()==7);
    }
}
