package pt.pa.model;

import pt.pa.graph.Edge;
import pt.pa.graph.Graph;
import pt.pa.graph.GraphAdjacencyList;
import pt.pa.graph.Vertex;
import pt.pa.observerpattern.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Network extends Subject {
    private Graph<Hub, Route> graph;
    private ArrayList<Hub> hubs;
    private FileLoader fm;
    public Network() {
        graph  = new GraphAdjacencyList<>();
        hubs = new ArrayList<>();
    }

    public void loadFiles (String foldername, String routeFileName) throws FileNotFoundException{
        this.fm = new FileLoader(foldername, routeFileName);
        this.inicializeGraph(foldername, routeFileName);
    }

    private void inicializeGraph(String foldername, String routeFileName){
        hubs = fm.loadHubs();
        Map<Integer, ArrayList<Integer>> routes =  fm.importRoutes(foldername, routeFileName);

        ArrayList<Vertex> vertexArrayList = new ArrayList<>();
        for(int i = 0; i < hubs.size(); i++) {
            Vertex<Hub> v = graph.insertVertex(hubs.get(i));
            vertexArrayList.add(v);
            System.out.println("HUB: "+ hubs.get(i).getName() + " " + hubs.get(i).getX() + " - " + hubs.get(i).getY());
        }

        Map<Integer, Edge<Route,Hub>> as = new HashMap<>();
        for(int j = 0; j < hubs.size(); j++){
            for(int i = j; i < routes.size(); i++) {
                if ((routes.get(j).get(i) != 0)) {
                    Edge<Route, Hub> e = graph.insertEdge(vertexArrayList.get(j), vertexArrayList.get(i), new Route(routes.get(j).get(i)));
                    as.put(j, e);
                }
            }
        }

        System.out.println("PRIVATE METHOD"+graph);
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
                return hub;
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


    public void addHub(String hubName){
        if(!existHub(hubName)){
            this.graph.insertVertex(new Hub(hubName));
            notifyObservers(null);
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
