package pt.pa.view;

import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graphview.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.Command.NetworkController;
import pt.pa.graph.Dijkstra;
import pt.pa.graph.DijkstraResult;
import pt.pa.graph.Vertex;
import pt.pa.model.Hub;
import pt.pa.model.Network;
import pt.pa.model.Route;
import pt.pa.observerpattern.*;


public class NetworkView extends BorderPane implements NetworkUI{
    private Network graph;
    private SmartGraphPanel<Hub, Route> graphView;
    private Scene scene;

    private Button btAddRoute;
    private Button btRemoveRoute;
    private Button btUndo;
    private Button btAddGroupRelationship;
    private Button btAddClassRelationship;
    private Button btRemoveRelationship;
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

    private Label lblNumberOfHubs;
    private Label lblNumberOfRoutes;

    public NetworkView(Network graph) {
        this.graph = graph;
        createLayout();
    }

    @Override
    public void update(Observable subject, Object arg) {
        if(subject == graph) {
            graphView.update();

            lblNumberOfHubs.setText(String.valueOf(graph.size()));
            lblNumberOfRoutes.setText(String.valueOf(graph.getGraph().numEdges()));
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
            Alert alert = makeConfirmationDialog("Delete Route", "Are you sure?");
            alert.showAndWait().ifPresent(response -> {

                if (response.getButtonData() == ButtonBar.ButtonData.YES) {
                    controller.removeRoute();
                }

            });
        });

        btUndo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    controller.undo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Alert makeConfirmationDialog(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
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
        graphView = new SmartGraphPanel<>(graph.getGraph(),properties,  new SmartCircularSortedPlacementStrategy());
        SmartGraphDemoContainer smc = new SmartGraphDemoContainer(graphView);
        setCenter(smc);

        /* RIGHT PANEL */
        setRight(createSidePanel());

        /* BOTTOM */
        lblError = new Label("Ready");
        HBox bottom = new HBox(lblError);
        bottom.setPadding(new Insets(10, 10, 10, 10));
        setBottom(bottom);
        bottom.setBackground(new Background((new BackgroundFill(Color.AQUAMARINE, new CornerRadii(10,0,0,0,false), Insets.EMPTY))));
        bottom.setFillHeight(true);
        /* bind double click on vertex */
        graphView.setVertexDoubleClickAction((SmartGraphVertex<Hub> graphVertex) -> {
            //Fill the person id textfield with the selected person's id
            System.out.println(graphVertex.getPositionCenterX() +"  "+ graphVertex.getPositionCenterY());

            txtPersonName.setText( String.valueOf( graphVertex.getUnderlyingVertex().element().getName()) );
        });

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
            btnArray[i].setBackground(new Background((new BackgroundFill(Color.WHITE, new CornerRadii(5), Insets.EMPTY))));
            btnArray[i].setStyle("-fx-border-color:black;"+"-fx-border-width: 1 1 1 1;" + "-fx-border-radius: 5;");
        }



        btn1.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
                GridPane root1 = new GridPane();
                Scene scene1;
                Stage stage1 = new Stage();
                stage1.initModality(Modality.APPLICATION_MODAL);
                Text title = new Text("Adicionar Caminho");
                title.setFont(Font.font("Verdana", FontWeight.BOLD,20));
                Text caminho = new Text("Caminho");
                Text ponto1 = new Text("Ponto 1");
                Text ponto2 = new Text("Ponto 2");
                txtOriginHubName = new TextField();
                txtDestinationHubName = new TextField();
                txtRouteValue = new TextField();
                Button submit = new Button("Submeter");
                Button voltar = new Button("Voltar");
                submit.setBackground(new Background((new BackgroundFill(Color.BLUE, new CornerRadii(10), Insets.EMPTY))));
                voltar.setBackground(new Background((new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY))));
                submit.setTextFill(Color.WHITE);
                voltar.setTextFill(Color.WHITE);
                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            btAddRoute.fire();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        stage1.close();
                    }
                });
                voltar.setOnAction(new EventHandler< ActionEvent >() {
                    @Override
                    public void handle(ActionEvent event) {
                        stage1.close();
                    }
                });
                root1.setAlignment(Pos.CENTER);
                root1.setMinSize(400, 200);
                root1.setPadding(new Insets(10, 10, 10, 10));
                root1.setVgap(5);
                root1.setHgap(10);
                root1.add(title,2,1);
                root1.add(ponto1,1,2);
                root1.add(txtOriginHubName,1,3,3,1);
                root1.add(ponto2,1,4);
                root1.add(txtDestinationHubName,1,5,3,1);
                root1.add(caminho,1,6);
                root1.add(txtRouteValue,1,7,3,1);
                root1.add(voltar,1,8);
                root1.add(submit,3,8);
                scene = new Scene(root1);
                stage1.setTitle("Adicionar Caminho");
                stage1.setScene(scene);
                stage1.show();
            }
        });

        btn2.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
                GridPane root1 = new GridPane();
                Stage stage1 = new Stage();
                stage1.initModality(Modality.APPLICATION_MODAL);
                Text title = new Text("Remover Caminho");
                title.setFont(Font.font("Verdana", FontWeight.BOLD,20));
                Text ponto1 = new Text("Ponto 1");
                Text ponto2 = new Text("Ponto 2");
                txtOriginHubName = new TextField();
                txtDestinationHubName = new TextField();
                Button submit = new Button("Submeter");
                Button voltar = new Button("Voltar");
                submit.setBackground(new Background((new BackgroundFill(Color.BLUE, new CornerRadii(10), Insets.EMPTY))));
                voltar.setBackground(new Background((new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY))));
                submit.setTextFill(Color.WHITE);
                voltar.setTextFill(Color.WHITE);
                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            btRemoveRoute.fire();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        stage1.close();
                    }
                });
                voltar.setOnAction(new EventHandler< ActionEvent >() {
                    @Override
                    public void handle(ActionEvent event) {
                        stage1.close();
                    }
                });
                root1.setAlignment(Pos.CENTER);
                root1.setMinSize(400, 200);
                root1.setPadding(new Insets(10, 10, 10, 10));
                root1.setVgap(5);
                root1.setHgap(10);
                root1.add(title,2,1);
                root1.add(ponto1,1,2);
                root1.add(txtOriginHubName,1,3,3,1);
                root1.add(ponto2,1,4);
                root1.add(txtDestinationHubName,1,5,3,1);
                root1.add(voltar,1,8);
                root1.add(submit,3,8);
                scene = new Scene(root1);
                stage1.setTitle("Remover Caminho");
                stage1.setScene(scene);
                stage1.show();
            }
        });

        btn3.setOnAction(new EventHandler< ActionEvent >(){
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                ChartView c = new ChartView(graph, stage);
            }
        });

        Dijkstra dijkstra = new Dijkstra(graph.getGraph());
        btn4.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
                GridPane root1 = new GridPane();
                Stage stage1 = new Stage();
                stage1.initModality(Modality.APPLICATION_MODAL);
                Text title = new Text("Curta Distancia");
                title.setFont(Font.font("Verdana", FontWeight.BOLD,20));
                Text ponto1 = new Text("Ponto 1");
                Text ponto2 = new Text("Ponto 2");
                txtOriginHubName = new TextField();
                txtDestinationHubName = new TextField();
                Button submit = new Button("Submeter");
                Button voltar = new Button("Voltar");
                submit.setBackground(new Background((new BackgroundFill(Color.BLUE, new CornerRadii(10), Insets.EMPTY))));
                voltar.setBackground(new Background((new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY))));
                submit.setTextFill(Color.WHITE);
                voltar.setTextFill(Color.WHITE);
                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            DijkstraResult<Hub> dijkstraResult = dijkstra.calculateShortestPathFromOrigin(graph.findHub(txtOriginHubName.getText()), graph.findHub(txtDestinationHubName.getText()));
                            for (Vertex<Hub> i:dijkstraResult.getPath()) {
                                graphView.getStylableVertex(i).setStyle("-fx-stroke: red; -fx-fill: red;");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        stage1.close();
                    }
                });
                voltar.setOnAction(new EventHandler< ActionEvent >() {
                    @Override
                    public void handle(ActionEvent event) {
                        stage1.close();
                    }
                });
                root1.setAlignment(Pos.CENTER);
                root1.setMinSize(400, 200);
                root1.setPadding(new Insets(10, 10, 10, 10));
                root1.setVgap(5);
                root1.setHgap(10);
                root1.add(title,2,1);
                root1.add(ponto1,1,2);
                root1.add(txtOriginHubName,1,3,3,1);
                root1.add(ponto2,1,4);
                root1.add(txtDestinationHubName,1,5,3,1);
                root1.add(voltar,1,8);
                root1.add(submit,3,8);
                scene = new Scene(root1);
                stage1.setTitle("Remover Caminho");
                stage1.setScene(scene);
                stage1.show();
            }
        });


        btn5.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
                for (Vertex<Hub> i:dijkstra.farthestHubs().getPath()) {
                    graphView.getStylableVertex(i).setStyle("-fx-stroke: red; -fx-fill: red;");
                }
            }
        });


        btn6.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
                for (Vertex<Hub> i: graph.getGraph().vertices()) {
                    graphView.getStylableVertex(i).setStyle("");
                }
            }
        });

        btn7.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
                btUndo.fire();
            }
        });
        /* ADD PERSON CONTROLS */
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
        txtPersonName = new TextField("");
        cbRoles = new ComboBox<>();
        cbRoles.getItems().addAll("STUDENT", "TEACHER");
        cbRoles.setMaxWidth(Double.MAX_VALUE); //hack to hgrow

        txtPersonName.setPrefColumnCount(10);

        btAddRoute = new Button("Add");
        btRemoveRoute = new Button("Remove");
        btUndo = new Button("Undo");

        /* Relationship Controls */
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


        btAddClassRelationship = new Button("Add Class");
        btAddGroupRelationship = new Button("Add Group");
        HBox addRel = new HBox(btAddClassRelationship, btAddGroupRelationship);
        btRemoveRelationship = new Button("Remove");


        /* STATS */
        Label labelCount = new Label("Number of Hubs: ");
        labelCount.setStyle("-fx-font-weight: bold;");
        Label labelPopular = new Label("Number of Routes: ");
        labelPopular.setStyle("-fx-font-weight: bold;");
        lblNumberOfHubs = new Label(String.valueOf(graph.size()));
        lblNumberOfRoutes = new Label(String.valueOf(graph.getGraph().numEdges()));
        VBox statsPane = new VBox(labelCount, lblNumberOfHubs, labelPopular, lblNumberOfRoutes);
        statsPane.setSpacing(10);

        /* COMPOSE */

        Label pHelp = new Label("You can double click on a person to select its ID.");
        pHelp.setStyle("-fx-font-size: 10px;");
        pHelp.setWrapText(true);
        pHelp.setMaxWidth(200);

        VBox panel = new VBox(new Label(""),
                personPane,
                new Separator(),
                new Label("Statistics"),
                statsPane);
        panel.setPadding(new Insets(10, 10, 10, 10));
        panel.setSpacing(10);
        panel.setBackground(new Background((new BackgroundFill(Color.AQUAMARINE, new CornerRadii(10,0,0,0,false), Insets.EMPTY))));
        panel.setFillWidth(true);

        return panel;
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

        Text x = new Text("Cooredenada X");
        TextField txtX = new TextField();

        Text y = new Text("Cooredenada Y");
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
        for(Vertex<Hub> vertex : graph.getGraph().vertices()){
            graphView.setVertexPosition(vertex, vertex.element().getX(),vertex.element().getY());
        }

        update(graph, null);
    }
}