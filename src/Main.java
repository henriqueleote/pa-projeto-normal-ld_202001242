import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.example.City;
import com.brunomnsilva.smartgraph.example.Distance;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartGraphProperties;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.graph.Graph;
import pt.pa.graph.GraphAdjacencyList;
import pt.pa.graph.GraphEdgeList;
import pt.pa.graph.Vertex;
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
        Graph<Hub, Route> graph = new GraphEdgeList<>();


        ArrayList<Hub> as = fm.importHubs("name");
        ArrayList<Hub> bs = fm.importCoordinates("xy");
        Map<Integer, List<String>> routes =  fm.importRoutes("routes_1");

        ArrayList<Vertex> vv = new ArrayList<>();
        for(int i = 0; i < bs.size(); i++) {
            Vertex<Hub> v = graph.insertVertex(bs.get(i));
            vv.add(v);
            System.out.println("HUB: "+bs.get(i).getName() + " " + bs.get(i).getX() + " - " + bs.get(i).getY());
        }
        System.out.println(routes.get(0).get(5));
        graph.insertEdge(vv.get(0), vv.get(5), new Route(Integer.parseInt(routes.get(0).get(6))));
       /* for(int j = 0; j < vv.size(); j++){
            for(int i = 0; i < routes.size(); i++){
                graph.insertEdge(vv.get(j), vv.get(i), new Route(Integer.parseInt(routes.get(j).get(i))));
            }
        }*/




        String customProps = "edge.label = true" + "\n" + "edge.arrow = false";
        SmartGraphProperties properties = new SmartGraphProperties(customProps);
        SmartGraphPanel<Hub, Route> graphView = new SmartGraphPanel<>(graph, properties, new SmartCircularSortedPlacementStrategy());

        Scene scene = new Scene(new SmartGraphDemoContainer(graphView), 1024, 768);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("JavaFX SmartGraph pt.pa.model.City Distances");
        stage.setMinHeight(500);
        stage.setMinWidth(800);
        stage.setScene(scene);
        stage.show();

        graphView.init();

        for(Vertex<Hub> vvv:vv){
            graphView.setVertexPosition(vvv, vvv.element().getX(),vvv.element().getY());
        }
        //Vertex<Hub> v = graph.insertVertex(new Hub("s"));


        /* for(Hub s : as){
            Vertex<Hub> v = graph.insertVertex(s);
            System.out.println(s.getX());
        }*/




    }


}
