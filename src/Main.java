import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartGraphProperties;
import com.brunomnsilva.smartgraph.graphview.SmartGraphVertex;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.graph.*;
import pt.pa.model.Hub;
import pt.pa.model.Route;

import java.io.FileNotFoundException;
import java.util.*;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        FileManager fm = new FileManager();
        Graph<Hub, Route> graph = new GraphAdjacencyList<>();

        fm.importHubs("dataset/sgb32/name");
        fm.importWeight("dataset/sgb32/weight");
        fm.importCoordinates("dataset/sgb32/xy");
        ArrayList<Hub> hubs = fm.loadHubs();
        Map<Integer, ArrayList<Integer>> routes =  fm.importRoutes("dataset/sgb32/routes_1");


        /*
        fm.importHubs("dataset/sgb128/name");
        fm.importWeight("dataset/sgb128/weight");
        fm.importCoordinates("dataset/sgb128/xy");
        ArrayList<Hub> hubs = fm.loadHubs();

      //Map<Integer, ArrayList<Integer>> routes =  fm.importRoutes("dataset/sgb128/routes_1");
        Map<Integer, ArrayList<Integer>> routes =  fm.importRoutes("dataset/sgb128/routes_2");
        */
        ArrayList<Vertex> vertexArrayList = new ArrayList<>();
        for(int i = 0; i < hubs.size(); i++) {
            Vertex<Hub> v = graph.insertVertex(hubs.get(i));
            vertexArrayList.add(v);
            System.out.println("HUB: "+hubs.get(i).getName() + " " + hubs.get(i).getX() + " - " + hubs.get(i).getY());
        }

        Map<Integer, Edge<Route,Hub>> as = new HashMap<>();
        for(int j = 0; j < vertexArrayList.size(); j++){
            for(int i = j; i < routes.size(); i++) {
                if ((Integer.compare(routes.get(j).get(i), 0) != 0)) {
                    Edge<Route,Hub> e = graph.insertEdge(vertexArrayList.get(j), vertexArrayList.get(i), new Route(routes.get(j).get(i)));
                    as.put(j, e);
                }

            }
        }
        System.out.println(graph);

        //String para mostrar no javafx o valor de cada aresta
        //String customProps = "edge.label = true" + "\n" + "edge.arrow = false";
        SmartGraphProperties properties = new SmartGraphProperties();
        SmartGraphPanel<Hub, Route> graphView = new SmartGraphPanel<>(graph, properties, new SmartCircularSortedPlacementStrategy());

        Scene scene = new Scene(new SmartGraphDemoContainer(graphView), 1024, 768);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("JavaFX SmartGraph pt.pa.model.City Distances");
        stage.setMinHeight(500);
        stage.setMinWidth(800);
        stage.setScene(scene);
        stage.show();

        graphView.init();

        for(Vertex<Hub> vvv:vertexArrayList){
            graphView.setVertexPosition(vvv, vvv.element().getX(),vvv.element().getY());
        }

        graphView.setVertexDoubleClickAction((SmartGraphVertex<Hub> graphVertex) -> {
            System.out.println("Vertex contains element: " + graphVertex.getUnderlyingVertex().element());

            //toggle different styling
            if( !graphVertex.removeStyleClass("myVertex") ) {
                /* for the golden vertex, this is necessary to clear the inline
                css class. Otherwise, it has priority. Test and uncomment. */
                //graphVertex.setStyle(null);

                graphVertex.addStyleClass("myVertex");
            }

            //want fun? uncomment below with automatic layout
            //graph.removeVertex(graphVertex.getUnderlyingVertex());
            //graphView.update();
        });

        /*
        graphView.setEdgeDoubleClickAction(graphEdge -> {
            System.out.println("Edge contains element: " + graphEdge.getUnderlyingEdge().element());
            //dynamically change the style when clicked
            graphEdge.setStyle("-fx-stroke: black; -fx-stroke-width: 3;");

            graphEdge.getStylableArrow().setStyle("-fx-stroke: black; -fx-stroke-width: 3;");

            //uncomment to see edges being removed after click
            Edge<Route, Hub> underlyingEdge = graphEdge.getUnderlyingEdge();
            graph.removeEdge(underlyingEdge);
            graphView.update();
        });

        */

    }
}
