package pt.pa.view;

import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
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
import javafx.stage.Stage;
import pt.pa.Command.NetworkController;
import pt.pa.graph.Dijkstra;
import pt.pa.graph.DijkstraResult;
import pt.pa.graph.Graph;
import javafx.event.ActionEvent;
import pt.pa.model.Hub;
import pt.pa.model.Network;
import pt.pa.model.Route;

import static sun.management.Agent.error;


public class Menus{

    private final Dijkstra dijkstra;
    private final Network network;
    private final Stage stage;
    private final GridPane root;
    private Scene scene;

    private final NetworkController networkController;
    private final SmartGraphPanel<Hub, Route> graphView;

    public Menus(Graph<Hub, Route> graph, SmartGraphPanel<Hub, Route> graphView, Network network, NetworkController networkController){
        this.graphView = graphView;
        this.network = network;
        this.networkController = networkController;

        dijkstra = new Dijkstra(network.getGraph());
        stage = new Stage();
        root = new GridPane();
    }

    public GridPane Menu(){

        Button btn1 = new Button("Adicionar Cidade");
        Button btn2 = new Button("Remover Cidade");
        Button btn3 = new Button("Adicionar Caminho");
        Button btn4 = new Button("Remover Caminho");
        Button btn5 = new Button("Curta Distancia");
        Button btn6 = new Button("Longa Distancia");
        Button btn7 = new Button("3.1.7");
        Button btn8 = new Button("Exportar");
        Button btn0 = new Button("Sair");

        Button[] btnArray = {btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8};

        for(int i=0; i<btnArray.length; i++){
            btnArray[i].setBackground(new Background((new BackgroundFill(Color.WHITE, new CornerRadii(5), Insets.EMPTY))));
            btnArray[i].setStyle("-fx-border-color:black;"+"-fx-border-width: 1 1 1 1;" + "-fx-border-radius: 5;");
        }

        //Adicionar Cidade
        btn1.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
                Text title = new Text("Adicionar Cidade");
                manageCity(title,"Adicionar Cidade");
            }
        });

        //Remover Cidade
        btn2.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
                Text title = new Text("Remover Cidade");
                manageCity(title, "Remover Cidade");
            }
        });

        //Adicionar Caminho
        btn3.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
                GridPane root1 = new GridPane();
                Scene scene1;
                Stage stage1 = new Stage();
                Text title = new Text("Adicionar Caminho");
                title.setFont(Font.font("Verdana", FontWeight.BOLD,20));
                Text caminho = new Text("Caminho");
                Text ponto1 = new Text("Ponto 1");
                Text ponto2 = new Text("Ponto 2");
                TextField txt1 = new TextField();
                TextField txt2 = new TextField();
                TextField txt3 = new TextField();
                Button submit = new Button("Submeter");
                Button voltar = new Button("Voltar");
                submit.setBackground(new Background((new BackgroundFill(Color.BLUE, new CornerRadii(10), Insets.EMPTY))));
                voltar.setBackground(new Background((new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY))));
                submit.setTextFill(Color.WHITE);
                voltar.setTextFill(Color.WHITE);
                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String p1 = txt1.getText();
                        String p2 = txt2.getText();
                        int route = Integer.parseInt(txt3.getText());
                        try {
                            network.addRoute(p1,p2,route);
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
                root1.add(txt1,1,3,3,1);
                root1.add(ponto2,1,4);
                root1.add(txt2,1,5,3,1);
                root1.add(caminho,1,6);
                root1.add(txt3,1,7,3,1);
                root1.add(voltar,1,8);
                root1.add(submit,3,8);
                scene = new Scene(root1);
                stage1.setTitle("Adicionar Caminho");
                stage1.setScene(scene);
                stage1.show();
            }
        });

        //Remover Caminho
        btn4.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
                Text title = new Text("Remover Caminho");
                manageCity(title, "Remover Caminho");
            }
        });

        //Distancia mais curta
        btn5.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
                Text title = new Text("Distancia mais curta");
                caminho(title, "Distancia mais curta");
            }
        });

        //Distancia mais longa
        btn6.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
                Text title = new Text("Distancia mais longa");
                caminho(title, "Distancia mais longa");
            }
        });

        /*
        //Ponto 3.1.7
        btn7.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        //Exportar
        btn8.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        */

        //Sair
        btn0.setOnAction(event -> stage.close());

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setVgap(20);
        root.setHgap(20);
        root.add(btn1,1,0);
        root.add(btn2,1,8);
        root.add(btn3,1,9);
        root.add(btn4,1,10);
        root.add(btn5,1,11);
        root.add(btn6,1,12);
        root.add(btn7,1,13);
        root.add(btn8,1,14);
        root.add(btn0,1,15);
        return root;
    }

    private void manageCity(Text title, String function) {
        GridPane root1 = new GridPane();
        Scene scene1;
        Stage stage1 = new Stage();
        title.setFont(Font.font("Verdana", FontWeight.BOLD,20));
        Text cidade = new Text("Cidade");
        TextField txt = new TextField();
        Button submit = new Button("Submeter");
        Button voltar = new Button("Voltar");
        submit.setBackground(new Background((new BackgroundFill(Color.BLUE, new CornerRadii(10), Insets.EMPTY))));
        voltar.setBackground(new Background((new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY))));
        submit.setTextFill(Color.WHITE);
        voltar.setTextFill(Color.WHITE);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(function.equalsIgnoreCase("Adicionar Cidade")){

                    if (txt.getText().isEmpty()) {
                        error("Missing product information.");
                    } else {
                        try {
                            //networkController.addHub();
                        } catch (NumberFormatException nfe) {
                            System.out.println("dsaDsadsadasdas");
                        }
                    }
                }
                if(function.equalsIgnoreCase("Remover Cidade")){
                    if (txt.getText().isEmpty()) {
                        error("Missing product information.");
                    } else {
                        try {
                            String name = txt.getText();
                            //networkController.removeVertex(name);
                            graphView.update();
                        } catch (NumberFormatException nfe) {
                            System.out.println("dsaDsadsadasdas");
                        }
                    }
                }
                if(function.equalsIgnoreCase("Remover Caminho")){

                }
                stage1.close();
            }
        });
        voltar.setOnAction(event -> stage1.close());
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
        scene1 = new Scene(root1);
        stage1.setTitle(function);
        stage1.setScene(scene1);
        stage1.show();
    }

    private void caminho(Text title, String function) {
        GridPane root1 = new GridPane();
        Scene scene1;
        Stage stage1 = new Stage();
        title.setFont(Font.font("Verdana", FontWeight.BOLD,20));
        Text ponto1 = new Text("Cidade");
        TextField txt1 = new TextField();
        Text ponto2 = new Text("Cidade");
        TextField txt2 = new TextField();
        Button submit = new Button("Submeter");
        Button voltar = new Button("Voltar");
        submit.setBackground(new Background((new BackgroundFill(Color.BLUE, new CornerRadii(10), Insets.EMPTY))));
        voltar.setBackground(new Background((new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY))));
        submit.setTextFill(Color.WHITE);
        voltar.setTextFill(Color.WHITE);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(function.equalsIgnoreCase("Adicionar Cidade")){
                    String p1 = txt1.getText();
                    String p2 = txt2.getText();
                    DijkstraResult<Hub> result = dijkstra.calculateShortestPathFromOrigin(network.findHub(p1), network.findHub(p2));

                }
                stage1.close();
            }
        });
        voltar.setOnAction(event -> stage1.close());
        root1.setAlignment(Pos.CENTER);
        root1.setMinSize(400, 200);
        root1.setPadding(new Insets(10, 10, 10, 10));
        root1.setVgap(5);
        root1.setHgap(10);
        root1.add(title,2,1);
        root1.add(ponto1,1,2);
        root1.add(txt1,1,3,3,1);
        root1.add(ponto2,1,4);
        root1.add(txt2,1,5,3,1);
        root1.add(voltar,1,6);
        root1.add(submit,3,6);
        scene1 = new Scene(root1);
        stage1.setTitle(function);
        stage1.setScene(scene1);
        stage1.show();
    }
}
