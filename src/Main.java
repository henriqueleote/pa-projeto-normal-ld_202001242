import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graphview.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.Command.NetworkController;
import pt.pa.graph.*;
import pt.pa.model.Hub;
import pt.pa.model.Route;
import pt.pa.model.Network;

import pt.pa.view.NetworkView;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static sun.management.Agent.error;


public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        Network graphLoader = new Network();
        graphLoader.loadFiles("dataset/sgb32/", "routes_1");
        Graph<Hub, Route> graph = graphLoader.getGraph();
        NetworkView view = new NetworkView(graphLoader);
        NetworkController networkController = new NetworkController(graphLoader, view);

        Scene scene = new Scene(view, 1300, 800);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("University Network");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        view.initGraphDisplay();
    }


    public static void main(String[] args) {
        launch(args);
    }
}