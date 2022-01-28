package pt.pa.model;

import pt.pa.graph.Edge;
import pt.pa.graph.Graph;
import pt.pa.graph.GraphAdjacencyList;
import pt.pa.graph.Vertex;
import pt.pa.observerpattern.*;

import java.io.FileNotFoundException;
import java.util.*;


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
        if(vertexToRemove != null){
            graph.removeVertex(vertexToRemove);
            notifyObservers(null);
            return vertexToRemove;
        }
        return null;
    }

    public void addRoute(String firstHubName, String secondHubName, int route) throws Exception {
        if(!existRoute(firstHubName, secondHubName)){
            graph.insertEdge(findHub(firstHubName), findHub(secondHubName) ,new Route(route));
            notifyObservers(null);
        }
    }

    public Edge<Route, Hub> removeRoute(String firstHubName, String secondHubName) throws Exception {
        Edge<Route, Hub> routeToRemove = findRoute(firstHubName,secondHubName);
        if(routeToRemove != null){
            graph.removeEdge(routeToRemove);
            notifyObservers(null);
            return routeToRemove;
        }
        return null;
    }

    public Map<Vertex<Hub>,Integer> hubsOrdered(){
        Map<Vertex<Hub>,Integer> centrality = new HashMap<>();
        for (Vertex<Hub> i: graph.vertices()) {
            centrality.put(i, graph.incidentEdges(i).size());
        }
        Map<Vertex<Hub>, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Vertex<Hub>, Integer> entry : sortValues(centrality)) sortedMap.put(entry.getKey(), entry.getValue());
        return sortedMap;
    }

    private List<Map.Entry<Vertex<Hub>, Integer>> sortValues(Map<Vertex<Hub>,Integer> map){
        List<Map.Entry<Vertex<Hub>, Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, (o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));
        Collections.reverse(list);
        return list;
    }

    public List<Map.Entry<Vertex<Hub>, Integer>> moreCentredHubs(Map<Vertex<Hub>,Integer> map){
        List<Map.Entry<Vertex<Hub>, Integer>> five = new LinkedList<Map.Entry<Vertex<Hub>, Integer>>();
        for(int i = 0; i<5; i++) {
            five.add(sortValues(map).get(i));
        }
        return five;
    }
}