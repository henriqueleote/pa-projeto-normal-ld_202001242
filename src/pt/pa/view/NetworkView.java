package pt.pa.view;

import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graphview.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.Command.NetworkController;
import pt.pa.graph.*;
import pt.pa.model.Hub;
import pt.pa.model.Network;
import pt.pa.model.Route;
import pt.pa.observerpattern.*;


public class NetworkView extends BorderPane implements NetworkUI{
    private final Network graph;
    private SmartGraphPanel<Hub, Route> graphView;
    private Scene scene;

    private Button btAddRoute;
    private Button btRemoveRoute;
    private Button btUndo;
    private Button submit;
    private Button voltar;
    private Label lblError;
    private ComboBox<String> cbRoles;
    private ComboBox<String> cbPersonId1;
    private ComboBox<String> cbPersonId2;
    private TextField txtPersonName;
    private TextField txtRelationshipDescription;
    private TextField txt;

    private TextField txtOriginHubName;
    private TextField txtDestinationHubName;
    private TextField txtRouteValue;

    private Label lblCost;
    private Label lblNumberOfHubs;
    private Label lblNumberOfRoutes;

    public NetworkView(Network graph) {
        this.graph = graph;
        createLayout();
    }

    public Graph<Hub, Route> getGraph(){
        return graph.getGraph();
    }

    @Override
    public void update(Observable subject, Object arg) {
        if(subject == graph) {
            graphView.update();
            lblNumberOfHubs.setText("Número de Hubs: " + String.valueOf(graph.size()));
            lblNumberOfRoutes.setText("Número de Caminhos: " + String.valueOf(getGraph().numEdges()));
        }
    }

    @Override
    public String getOriginHubName() {
        return txtOriginHubName.getText();
    }

    @Override
    public String getDestinationHubName() {
        return txtDestinationHubName.getText();
    }

    @Override
    public int getRouteValue() {
        return Integer.parseInt(txtRouteValue.getText());
    }

    @Override
    public void setTriggers(NetworkController controller) {
        btAddRoute.setOnAction(event -> {
            controller.addRoute();
        });

        btRemoveRoute.setOnAction(event -> {
            Alert alert = makeConfirmationDialog("Remover caminho", "Tem a certeza que quer remover?");
            alert.showAndWait().ifPresent(response -> {

                if (response.getButtonData() == ButtonBar.ButtonData.YES) {
                    controller.removeRoute();
                }

            });
        });

        btUndo.setOnAction(actionEvent -> {
            try {
                controller.undo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private Alert makeConfirmationDialog(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        ButtonType yesButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);
        return alert;
    }

    @Override
    public void displayError(String msg) {
        lblError.setText(msg);
    }

    @Override
    public void clearError() {
        lblError.setText("");
    }

    @Override
    public void clearControls() {
        txtPersonName.clear();
        txtRelationshipDescription.clear();
    }

    private void createLayout() {
        /* CENTER PANEL */
        SmartGraphProperties properties = new SmartGraphProperties();
        graphView = new SmartGraphPanel<>(getGraph(),properties,  new SmartCircularSortedPlacementStrategy());
        SmartGraphDemoContainer smc = new SmartGraphDemoContainer(graphView);
        setCenter(smc);

        /* LEFT PANEL */
        setLeft(createSidePanel());

        /* BOTTOM PANEL */
        lblError = new Label("Ready");
        HBox bottom = new HBox(lblError);
        bottom.setPadding(new Insets(10, 10, 10, 10));
        setBottom(bottom);
        bottom.setBackground(new Background((new BackgroundFill(Color.LIGHTGREY, new CornerRadii(0,0,0,0,false), Insets.EMPTY))));
        bottom.setFillHeight(true);
        /* bind double click on vertex */
        graphView.setVertexDoubleClickAction((SmartGraphVertex<Hub> graphVertex) -> {
            //Fill the person id textfield with the selected person's id
            System.out.println(graphVertex.getPositionCenterX() +"  "+ graphVertex.getPositionCenterY());
            Vertex<Hub> underlyingVertex = graphVertex.getUnderlyingVertex();
            txtPersonName.setText( String.valueOf( underlyingVertex.element().getName()) );
        });

        graphView.setVertexDoubleClickAction((SmartGraphVertex<Hub> graphVertex) -> {
            Vertex<Hub> underlyingVertex = graphVertex.getUnderlyingVertex();
            System.out.println("Vertex contains element: " + underlyingVertex.element());
            if(!graphVertex.removeStyleClass("myVertex") ) {
                graphVertex.addStyleClass("myVertex");
            }
        });

        graphView.setEdgeDoubleClickAction(graphEdge -> {
            Edge<Route, Hub> underlyingEdge = graphEdge.getUnderlyingEdge();
            System.out.println("Edge contains element: " + underlyingEdge.element());
            //dynamically change the style when clicked
            if (!graphEdge.removeStyleClass("myEdge")) {
                graphEdge.setStyle("-fx-stroke: black; -fx-stroke-width: 3;");
                graphEdge.addStyleClass("myEdge");
            } else{
                graphEdge.removeStyleClass("myEdge");
            }
        });
    }

    private VBox createSidePanel() {

        Button btn1 = new Button("Adicionar Caminho");
        Button btn2 = new Button("Remover Caminho");
        Button btn3 = new Button("Cálculo de métricas");
        Button btn4 = new Button("Curta Distancia");
        Button btn5 = new Button("Longa Distancia");
        Button btn6 = new Button("Repor Estilo");
        Button btn7 = new Button("Undo");
        Button btn8 = new Button("3.1.7");
        Button btn9 = new Button("Exportar");
        Button btn0 = new Button("Sair");

        Button[] btnArray = {btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9};

        for(int i=0; i<btnArray.length; i++){
            btnArray[i].setBackground(new Background((new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY))));
            btnArray[i].setMaxWidth(150);
            btnArray[i].setStyle("-fx-border-color:grey;"+ "-fx-border-radius: 10;"+"-fx-background-radius: 10;"+"-fx-border-radius: 10;");
            btnArray[i].setFocusTraversable(false);
        }

        btn0.setOnAction(event -> System.exit(0));

        btn1.setOnAction(event -> btn1Method());

        btn2.setOnAction(event -> btn2Method());

        btn3.setOnAction(event -> {
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            ChartView c = new ChartView(graph, stage);
        });

        Dijkstra dijkstra = new Dijkstra(getGraph());

        btn4.setOnAction(event -> btn4Method(dijkstra));

        btn5.setOnAction(event -> {
            for (Vertex<Hub> i:dijkstra.farthestHubs().getPath()) {
                graphView.getStylableVertex(i).setStyle("-fx-stroke: red; -fx-fill: red;");
            }
        });

        btn6.setOnAction(event -> {
            for (Vertex<Hub> i: getGraph().vertices()) {
                graphView.getStylableVertex(i).setStyle("");
            }
        });

        btn7.setOnAction(event -> btUndo.fire());


        /* ADD PERSON CONTROLS */
        GridPane personPane = personControls(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0);

        /* Relationship Controls */
        RelationshipControls();


        /* STATS */

        lblCost = new Label("Cost");
        lblNumberOfHubs = new Label("Number of Hubs: " + graph.size());
        lblNumberOfRoutes = new Label("Number of Routes: " + getGraph().numEdges());
        lblCost.setVisible(false);
        VBox statsPane = new VBox(lblNumberOfHubs, lblNumberOfRoutes, lblCost);
        statsPane.setSpacing(10);

        /* COMPOSE */

        Label pHelp = new Label("You can double click on a person to select its ID.");
        pHelp.setStyle("-fx-font-size: 10px;");
        pHelp.setWrapText(true);
        pHelp.setMaxWidth(200);

        Label title1 = new Label("Botões");
        title1.setFont(Font.font("Arial", FontWeight.BOLD,15));
        Label title2 = new Label("Estatísticas");
        title2.setFont(Font.font("Arial", FontWeight.BOLD,15));
        title1.setAlignment(Pos.CENTER);
        title1.setTextAlignment(TextAlignment.CENTER);

        VBox panel = new VBox(title1,
                personPane,
                new Separator(),
                title2,
                statsPane);
        panel.setPadding(new Insets(10, 10, 10, 10));
        panel.setSpacing(10);
        panel.setBackground(new Background((new BackgroundFill(Color.LIGHTGREY, new CornerRadii(0,0,0,0,false), Insets.EMPTY))));
        panel.setFillWidth(true);

        return panel;
    }

    private void RelationshipControls() {
        GridPane relationPane = new GridPane();
        relationPane.setAlignment(Pos.CENTER);
        relationPane.setHgap(5);
        relationPane.setVgap(5);
        relationPane.setPadding(new Insets(10,10,10,10)); // set top, right, bottom, left

        txtRelationshipDescription = new TextField("");
        cbPersonId1 = new ComboBox<>();
        cbPersonId1.setMaxWidth(Double.MAX_VALUE); //hack to hgrow
        cbPersonId2 = new ComboBox<>();
        cbPersonId2.setMaxWidth(Double.MAX_VALUE); //hack to hgrow

        txtPersonName.setPrefColumnCount(12);

    }

    private GridPane personControls(Button btn1, Button btn2, Button btn3, Button btn4, Button btn5, Button btn6, Button btn7, Button btn8, Button btn9, Button btn0) {
        GridPane personPane = new GridPane();
        personPane.setAlignment(Pos.CENTER);
        personPane.setPadding(new Insets(10,10,10,10)); // set top, right, bottom, left
        personPane.setHgap(5);
        personPane.setVgap(20);


        personPane.add(btn1,1,0);
        personPane.add(btn2,1,1);
        personPane.add(btn3,1,2);
        personPane.add(btn4,1,3);
        personPane.add(btn5,1,4);
        personPane.add(btn6,1,5);
        personPane.add(btn7,1,6);
        personPane.add(btn8,1,7);
        personPane.add(btn9,1,8);
        personPane.add(btn0,1,9);

        btn8.setDisable(true);

        txtPersonName = new TextField("");
        cbRoles = new ComboBox<>();
        cbRoles.getItems().addAll("STUDENT", "TEACHER");
        cbRoles.setMaxWidth(Double.MAX_VALUE); //hack to hgrow

        txtPersonName.setPrefColumnCount(10);

        btAddRoute = new Button("Add");
        btRemoveRoute = new Button("Remove");
        btUndo = new Button("Undo");
        return personPane;
    }

    private void btn4Method(Dijkstra dijkstra) {
        StackPane root1 = new StackPane();
        Stage stage1 = new Stage();
        stage1.initModality(Modality.APPLICATION_MODAL);

        Text title = new Text("Curta Distância");
        txtFields(root1, title);


        submit.setOnAction(event -> {
            try {
                DijkstraResult<Hub> dijkstraResult = dijkstra.calculateShortestPathFromOrigin(graph.findHub(txtOriginHubName.getText()), graph.findHub(txtDestinationHubName.getText()));
                for (Vertex<Hub> i:dijkstraResult.getPath()) {
                    graphView.getStylableVertex(i).setStyle("-fx-stroke: red; -fx-fill: red;");
                }
                lblCost.setVisible(true);
                lblCost.setTextFill(Color.DARKGREEN);
                lblCost.setText("Custo: " + String.valueOf(dijkstraResult.getCost()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage1.close();
        });
        voltar.setOnAction(event -> stage1.close());


        scene = new Scene(root1, 400, 260);
        stage1.setTitle("Curta Distância");
        stage1.setResizable(false);
        stage1.setScene(scene);
        stage1.show();
    }

    private void btn2Method() {
        StackPane root1 = new StackPane();
        Stage stage1 = new Stage();
        stage1.initModality(Modality.APPLICATION_MODAL);

        Text title = new Text("Remover Caminho");
        txtFields(root1, title);

        submit.setOnAction(event -> {
            try {
                btRemoveRoute.fire();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage1.close();
        });
        voltar.setOnAction(event -> stage1.close());

        scene = new Scene(root1, 400, 260);
        stage1.setTitle("Remover Caminho");
        stage1.setResizable(false);
        stage1.setScene(scene);
        stage1.show();
    }

    private void btn1Method() {
        StackPane root1 = new StackPane();
        Stage stage1 = new Stage();
        stage1.initModality(Modality.APPLICATION_MODAL);

        Text title = new Text("Adicionar caminho");
        Text caminho = new Text("Caminho");
        Text ponto1Lbl = new Text("Ponto 1");
        Text ponto2Lbl = new Text("Ponto 2");
        submit = new Button("Submeter");
        voltar = new Button("Voltar");

        txtOriginHubName = new TextField();
        txtDestinationHubName = new TextField();
        txtRouteValue = new TextField();
        txtOriginHubName.setMaxWidth(200);
        txtDestinationHubName.setMaxWidth(200);
        txtRouteValue.setMaxWidth(200);

        title.setFont(Font.font("Arial", FontWeight.BOLD,20));
        title.setTranslateX(0);
        title.setTranslateY(-120);

        caminho.setTranslateX(-75);
        caminho.setTranslateY(-70);
        txtRouteValue.setTranslateX(0);
        txtRouteValue.setTranslateY(-40);

        ponto1Lbl.setTranslateX(-75);
        ponto1Lbl.setTranslateY(-10);
        txtOriginHubName.setTranslateX(0);
        txtOriginHubName.setTranslateY(20);

        ponto2Lbl.setTranslateX(-75);
        ponto2Lbl.setTranslateY(50);
        txtDestinationHubName.setTranslateX(0);
        txtDestinationHubName.setTranslateY(80);

        voltar.setTranslateX(-60);
        voltar.setTranslateY(120);
        submit.setTranslateX(60);
        submit.setTranslateY(120);

        voltar.setBackground(new Background((new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY))));
        voltar.setMaxWidth(80);
        voltar.setTextFill(Color.WHITE);
        voltar.setFocusTraversable(false);


        submit.setBackground(new Background((new BackgroundFill(Color.BLUE, new CornerRadii(10), Insets.EMPTY))));
        submit.setMaxWidth(80);
        submit.setTextFill(Color.WHITE);
        submit.setFocusTraversable(false);


        root1.getChildren().addAll(title,caminho,txtRouteValue,ponto1Lbl,txtOriginHubName,ponto2Lbl,txtDestinationHubName,submit,voltar);

        submit.setOnAction(event -> {
            try {
                btAddRoute.fire();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage1.close();
        });
        voltar.setOnAction(event -> stage1.close());

        scene = new Scene(root1, 400, 330);
        stage1.setTitle("Adicionar Caminho");
        stage1.setResizable(false);
        stage1.setScene(scene);
        stage1.show();
    }

    private void txtFields(StackPane root1, Text title) {
        Text ponto1Lbl = new Text("Ponto 1");
        Text ponto2Lbl = new Text("Ponto 2");
        submit = new Button("Submeter");
        voltar = new Button("Voltar");

        txtOriginHubName = new TextField();
        txtDestinationHubName = new TextField();
        txtOriginHubName.setMaxWidth(200);
        txtDestinationHubName.setMaxWidth(200);

        title.setFont(Font.font("Arial", FontWeight.BOLD,20));
        title.setTranslateX(0);
        title.setTranslateY(-100);

        ponto1Lbl.setTranslateX(-75);
        ponto1Lbl.setTranslateY(-50);
        txtOriginHubName.setTranslateX(0);
        txtOriginHubName.setTranslateY(-20);

        ponto2Lbl.setTranslateX(-75);
        ponto2Lbl.setTranslateY(10);
        txtDestinationHubName.setTranslateX(0);
        txtDestinationHubName.setTranslateY(40);

        voltar.setTranslateX(-60);
        voltar.setTranslateY(80);
        submit.setTranslateX(60);
        submit.setTranslateY(80);

        voltar.setBackground(new Background((new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY))));
        voltar.setMaxWidth(80);
        voltar.setTextFill(Color.WHITE);
        voltar.setFocusTraversable(false);


        submit.setBackground(new Background((new BackgroundFill(Color.BLUE, new CornerRadii(10), Insets.EMPTY))));
        submit.setMaxWidth(80);
        submit.setTextFill(Color.WHITE);
        submit.setFocusTraversable(false);


        root1.getChildren().addAll(title,ponto1Lbl,txtOriginHubName,ponto2Lbl,txtDestinationHubName,submit,voltar);
    }

    private void manageCity(Text title, String function) {
        GridPane root1 = new GridPane();
        Scene scene1;

        Stage stage1 = new Stage();
        title.setFont(Font.font("Verdana", FontWeight.BOLD,20));
        Text cidade = new Text("Cidade");
        txt = new TextField();
        stage1.initModality(Modality.APPLICATION_MODAL);
        Button submit = new Button("Submeter");
        Button voltar = new Button("Voltar");
        submit.setBackground(new Background((new BackgroundFill(Color.BLUE, new CornerRadii(10), Insets.EMPTY))));
        voltar.setBackground(new Background((new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY))));
        submit.setTextFill(Color.WHITE);
        voltar.setTextFill(Color.WHITE);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btRemoveRoute.fire();
            }
        });
        voltar.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
                stage1.close();
            }
        });

        Text x = new Text("Coordenada X");
        TextField txtX = new TextField();

        Text y = new Text("Coordenada Y");
        TextField txtY = new TextField();

        root1.setAlignment(Pos.CENTER);
        root1.setMinSize(400, 200);
        root1.setPadding(new Insets(10, 10, 10, 10));
        root1.setVgap(5);
        root1.setHgap(10);
        root1.add(title,2,1);
        root1.add(cidade,1,2);
        root1.add(txt,1,3,3,1);
        root1.add(voltar,1,4);
        root1.add(submit,3,4);

        stage1.initModality(Modality.APPLICATION_MODAL);

        scene1 = new Scene(root1);
        stage1.setTitle(function);
        stage1.setScene(scene1);
        stage1.show();
    }

    public void initGraphDisplay() {
        graphView.init();
        for(Vertex<Hub> vertex : getGraph().vertices()){
            graphView.setVertexPosition(vertex, vertex.element().getX(),vertex.element().getY());
        }

        update(graph, null);
    }
}