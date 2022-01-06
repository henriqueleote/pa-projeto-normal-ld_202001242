import pt.pa.graph.Edge;
import pt.pa.graph.Graph;
import pt.pa.graph.GraphAdjacencyList;
import pt.pa.graph.Vertex;
import pt.pa.model.Hub;
import pt.pa.model.Route;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Network {
    private Graph<Hub, Route> graph;
    private FileLoader fm = new FileLoader("dataset/sgb32/", "routes_1");
    public Network() {
        graph  = new GraphAdjacencyList<>();
    }

    public void loadFiles (String foldername, String routeFileName) throws FileNotFoundException{
        fm.importRoutes(foldername, routeFileName);
    }

    public int size(){
        return graph.numVertices();
    }

    public Graph<Hub, Route> getGraph(){
        return graph;
    }

    public Vertex<Hub> findHub(String cityName){
        for(Vertex<Hub> hub: graph.vertices()){
            if(hub.element().getName().equals(cityName)){
                System.out.println(hub.element().getName() + "\n"+ hub.element().getPopulation() + "\n"+ hub.element().getX() + "\n" + hub.element().getY());
            }
        }
        return null;
    }

    public boolean existHub(String cityName) {
        if(findHub(cityName) != null){
            return true;
        }
        return false;
    }

    public Edge<Route, Hub> findRoute(String firstHub, String secondHub) throws Exception {
        if(!existHub(firstHub) || !existHub(secondHub)){
            throw new Exception("Um ou os dois Hubs não foram encontrados");
        }
        Vertex<Hub> firstVertex = findHub(firstHub);
        Vertex<Hub> secondVertex = findHub(secondHub);

        for(Edge<Route, Hub> e : graph.incidentEdges(firstVertex)){
                if(graph.opposite(firstVertex, e).equals(secondVertex)){
                    return e;
                }
        }
        return null;
    }

    public boolean existRoute(String firstHub, String secondHub) throws Exception {
        if(findRoute(firstHub, secondHub) != null){
            return true;
        }
        return false;
    }

    //FALTA ADICIONAR/REMOVER HUBS ADICIONAR/REMOVER ROTAS

}
