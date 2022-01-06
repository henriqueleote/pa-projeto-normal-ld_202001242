import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartGraphProperties;
import com.brunomnsilva.smartgraph.graphview.SmartGraphVertex;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

        Graph<Hub, Route> graph = new GraphAdjacencyList<>();
        FileLoader fm = new FileLoader("dataset/sgb32/", "routes_1");

        ArrayList<Hub> hubs = fm.loadHubs();
        Map<Integer, ArrayList<Integer>> routes =  fm.importRoutes("dataset/sgb32/", "routes_1");

        ArrayList<Vertex> vertexArrayList = new ArrayList<>();
        for(int i = 0; i < hubs.size(); i++) {
            Vertex<Hub> v = graph.insertVertex(hubs.get(i));
            vertexArrayList.add(v);
            System.out.println("HUB: "+ hubs.get(i).getName() + " " + hubs.get(i).getX() + " - " + hubs.get(i).getY());
        }

        Map<Integer, Edge<Route,Hub>> as = new HashMap<>();
        for(int j = 0; j < vertexArrayList.size(); j++){
            for(int i = j; i < routes.size(); i++) {
                if ((routes.get(j).get(i) != 0)) {
                    Edge<Route, Hub> e = graph.insertEdge(vertexArrayList.get(j), vertexArrayList.get(i), new Route(routes.get(j).get(i)));
                    as.put(j, e);
                }
            }
        }
        System.out.println(graph);

        //String para mostrar no javafx o valor de cada aresta
        //String customProps = "edge.label = true" + "\n" + "edge.arrow = false";

        BorderPane root = new BorderPane();
        VBox box = new VBox(30);



        SmartGraphProperties properties = new SmartGraphProperties();
        SmartGraphPanel<Hub, Route> graphView = new SmartGraphPanel<>(graph, properties, new SmartCircularSortedPlacementStrategy());
        SmartGraphDemoContainer smc = new SmartGraphDemoContainer(graphView);



        root.setLeft(box);
        root.setRight(smc);

        VBox editButtons = new VBox(8);
        Button addProductButton = new Button("Add");
        Button editProductButton = new Button("Edit");
        Button removeProductButton = new Button("Remove");
        editButtons.getChildren().add(addProductButton);
        editButtons.getChildren().add(editProductButton);
        editButtons.getChildren().add(removeProductButton);
        editButtons.setAlignment(Pos.TOP_CENTER);
        editButtons.setPadding(new Insets(5));
        editButtons.getStyleClass().add("color-palette");
        editButtons.setMinHeight(1024);
        editButtons.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        editButtons.setBorder(new Border(new BorderStroke(Color.valueOf("#9E9E9E"),
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));

        box.getChildren().add(editButtons);


        primaryStage.setTitle("Produtos");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();

        /*Scene scene = new Scene(smc, 1280, 1024);

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("JavaFX SmartGraph pt.pa.model.City Distances");
        stage.setMinHeight(500);
        stage.setMinWidth(800);
        stage.setScene(scene);
        stage.show();
*/
        graphView.init();

        for(Vertex<Hub> vertex : vertexArrayList){
            graphView.setVertexPosition(vertex, vertex.element().getX(),vertex.element().getY());
        }

        // Testes para a criação da outra classe que ira gerir a graphview
        // Procurar uma cidade (HUB) pelo nome
        for(Vertex<Hub> hub: graph.vertices()){
            if(hub.element().getName().equals("Williamson, WV")){
                System.out.println("TESTESTESTESTES \n" + hub.element().getName() + "\n"+ hub.element().getPopulation() + "\n"+ hub.element().getX() + "\n" + hub.element().getY());
            }
        }


    /*    //Obter as rotas entre dois Hubs

*/
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

        graphView.setEdgeDoubleClickAction(graphEdge -> {
            System.out.println("Edge contains element: " + graphEdge.getUnderlyingEdge().element());
            //dynamically change the style when clicked
            graphEdge.setStyle("-fx-stroke: black; -fx-stroke-width: 3;");
        });



    }
}
