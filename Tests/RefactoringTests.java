
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.pa.graph.GraphAdjacencyList;
import pt.pa.graph.Vertex;
import pt.pa.model.Hub;
import pt.pa.model.Route;
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


}
