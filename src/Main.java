import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartGraphProperties;
import com.brunomnsilva.smartgraph.graphview.SmartGraphVertex;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.Command.NetworkController;
import pt.pa.graph.*;
import pt.pa.model.Hub;
import pt.pa.model.Route;
import pt.pa.model.Network;

import java.io.FileNotFoundException;


public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        Network graphLoader = new Network();
        graphLoader.loadFiles("dataset/sgb32/", "routes_1");
        Graph<Hub, Route> graph = graphLoader.getGraph();
        NetworkController networkController = new NetworkController(graphLoader);


        // Mudar para uma outra classe que será responsavel para a visualização do programa - por exemplo (MainScreenPanel)
        BorderPane root = new BorderPane();
        VBox box = new VBox(30);
        box.setBackground(new Background((new BackgroundFill(Color.AQUAMARINE, new CornerRadii(10), Insets.EMPTY))));
        box.setFillWidth(true);
        SmartGraphProperties properties = new SmartGraphProperties();
        SmartGraphPanel<Hub, Route> graphView = new SmartGraphPanel<>(graph, properties, new SmartCircularSortedPlacementStrategy());
        SmartGraphDemoContainer smc = new SmartGraphDemoContainer(graphView);

        Menus menu = new Menus(graph, graphView , graphLoader, networkController);

        root.setLeft(box);
        root.setCenter(smc);

        box.getChildren().add(menu.Menu());

        primaryStage.setTitle("Produtos");
        primaryStage.setScene(new Scene(root, 1124, 768));
        primaryStage.show();
        graphView.init();

        for(Vertex<Hub> vertex : graphLoader.getGraph().vertices()){
            graphView.setVertexPosition(vertex, vertex.element().getX(),vertex.element().getY());
        }

        graphView.setVertexDoubleClickAction((SmartGraphVertex<Hub> graphVertex) -> {
            System.out.println("Vertex contains element: " + graphVertex.getUnderlyingVertex().element());
            if(!graphVertex.removeStyleClass("myVertex") ) {
                graphVertex.addStyleClass("myVertex");
            }
        });

        graphView.setEdgeDoubleClickAction(graphEdge -> {
            System.out.println("Edge contains element: " + graphEdge.getUnderlyingEdge().element());
            //dynamically change the style when clicked
            if (!graphEdge.removeStyleClass("myEdge")) {
                graphEdge.setStyle("-fx-stroke: black; -fx-stroke-width: 3;");
                graphEdge.addStyleClass("myEdge");
            } else{
                graphEdge.removeStyleClass("myEdge");
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}