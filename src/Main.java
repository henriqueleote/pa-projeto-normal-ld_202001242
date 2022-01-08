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

import javax.naming.NamingEnumeration;
import java.io.FileNotFoundException;
import java.util.*;

import java.awt.*;

import static sun.management.Agent.error;


public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        Network graphLoader = new Network();
        graphLoader.loadFiles("dataset/sgb32/", "routes_1");
        Graph<Hub, Route> graph = graphLoader.getGraph();
        NetworkController networkController = new NetworkController(graphLoader);


        Menus menu = new Menus();















// Mudar para uma outra classe que será responsavel para a visualização do programa - por exemplo (MainScreenPanel)
        BorderPane root = new BorderPane();
        VBox box = new VBox(30);

        SmartGraphProperties properties = new SmartGraphProperties();
        SmartGraphPanel<Hub, Route> graphView = new SmartGraphPanel<>(graph, properties, new SmartCircularSortedPlacementStrategy());
        SmartGraphDemoContainer smc = new SmartGraphDemoContainer(graphView);

        root.setLeft(box);
        root.setCenter(smc);

        VBox editButtons = new VBox(8);
        Button addProductButton = new Button("Add");
        Button editProductButton = new Button("Remove");
        Button removeProductButton = new Button("Undo");
        editButtons.getChildren().add(addProductButton);
        editButtons.getChildren().add(editProductButton);
        editButtons.getChildren().add(removeProductButton);
        editButtons.setAlignment(Pos.TOP_CENTER);
        editButtons.setPadding(new Insets(5));
        editButtons.getStyleClass().add("color-palette");
        editButtons.setMinHeight(768);



        TextField textFieldProductName;

        GridPane gridPaneAddProduct = new GridPane();
        gridPaneAddProduct.add(new Label("Name"), 0, 1);
        textFieldProductName = new TextField();
        gridPaneAddProduct.add(textFieldProductName, 1, 1);


        addProductButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (textFieldProductName.getText().isEmpty()) {
                    error("Missing product information.");
                } else {
                    try {
                        String name = textFieldProductName.getText();
                        networkController.addVertex(name);
                        graphView.update();
                        System.out.println("TESTES DO ADD \n"+graphLoader.getGraph().vertices());
                    } catch (NumberFormatException nfe) {
                        System.out.println("dsaDsadsadasdas");
                    }
                }

            }
        });




box.getChildren().add(textFieldProductName);


        editButtons.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        editButtons.setBorder(new Border(new BorderStroke(Color.valueOf("#9E9E9E"),
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));

        box.getChildren().add(editButtons);

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