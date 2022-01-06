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

    public Vertex<Hub> findHub(String hubName){
        for(Vertex<Hub> hub: graph.vertices()){
            if(hub.element().getName().equals(hubName)){
                System.out.println(hub.element().getName() + "\n"+ hub.element().getPopulation() + "\n"+ hub.element().getX() + "\n" + hub.element().getY());
            }
        }
        return null;
    }

    public boolean existHub(String name) {
        if(findHub(name) != null){
            return true;
        }
        return false;
    }

    public Edge<Route, Hub> findRoute(String firstHubName, String secondHubName) throws Exception {
        if(!existHub(firstHubName) || !existHub(secondHubName)){
            throw new Exception("Um ou os dois Hubs não foram encontrados");
        }
        Vertex<Hub> firstVertex = findHub(firstHubName);
        Vertex<Hub> secondVertex = findHub(secondHubName);

        for(Edge<Route, Hub> e : graph.incidentEdges(firstVertex)){
                if(graph.opposite(firstVertex, e).equals(secondVertex)){
                    return e;
                }
        }
        return null;
    }

    public boolean existRoute(String firstHubName, String secondHubName) throws Exception {
        if(findRoute(firstHubName, secondHubName) != null){
            return true;
        }
        return false;
    }

    //FALTA ADICIONAR/REMOVER HUBS ADICIONAR/REMOVER ROTAS

    public void addHub(String hubName){
        if(!existHub(hubName)){
            graph.insertVertex(new Hub(hubName));
        }
    }

    public Vertex<Hub> removeHub(String name){
        Vertex<Hub> vertexToRemove = findHub(name);
        if(findHub(name) != null){
           graph.removeVertex(findHub(name));
        }
        return vertexToRemove;
    }

    public void addRoute(String firstHubName, String secondHubName, int route) throws Exception {
        if(!existRoute(firstHubName, secondHubName)){
            graph.insertEdge(findHub(firstHubName), findHub(secondHubName) ,new Route(route));
        }
    }

    public Vertex<Hub> removeRoute(String name){
        Vertex<Hub> vertexToRemove = findHub(name);
        if(findHub(name) != null){
            graph.removeVertex(findHub(name));
        }
        return vertexToRemove;
    }


}
