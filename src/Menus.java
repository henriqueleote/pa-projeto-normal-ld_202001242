import com.brunomnsilva.smartgraph.example.City;
import com.brunomnsilva.smartgraph.example.Distance;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pt.pa.graph.Dijkstra;
import pt.pa.graph.DijkstraResult;
import pt.pa.graph.GraphAdjacencyList;
import javafx.event.ActionEvent;


public class Menus{
    private GraphAdjacencyList adjacency;
    private Dijkstra dijkstra;
    private Stage stage;
    private GridPane root;
    private Scene scene;
    private Label lbl;

    public Menus(){
        adjacency = new GraphAdjacencyList();
        dijkstra = new Dijkstra();
        stage = new Stage();
        root = new GridPane();
        lbl = new Label();
        Menu();
    }

    public void Menu(){
        lbl.setText("Escolha a dificuldade:");
        Button btn1 = new Button("Adicionar Cidade");
        Button btn2 = new Button("Remover Cidade");
        Button btn3 = new Button("Adicionar Caminho");
        Button btn4 = new Button("Remover Caminho");
        Button btn5 = new Button("Curta Distancia");
        Button btn6 = new Button("Longa Distancia");
        Button btn7 = new Button("3.1.7");
        Button btn8 = new Button("Exportar");
        Button btn0 = new Button("Sair");

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
                        String pName = txt3.getText();
                        //adjacency.insertEdge(adjacency.getVertexByName(p1),adjacency.getVertexByName(p2), new Distance(???));
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
        btn0.setOnAction(new EventHandler< ActionEvent >() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setVgap(5);
        root.setHgap(20);
        root.add(btn1,1,1);
        root.add(btn2,1,2);
        root.add(btn3,1,3);
        root.add(btn4,1,4);
        root.add(btn5,1,5);
        root.add(btn6,1,6);
        root.add(btn7,1,7);
        root.add(btn8,1,8);
        root.add(btn0,1,9);
        scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();
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
                    String city = txt.getText();
                    //adjacency.insertVertex(new City(city));
                }
                if(function.equalsIgnoreCase("Remover Cidade")){
                    String city = txt.getText();
                    //adjacency.removeVertex(adjacency.getVertexByName(city));
                }
                if(function.equalsIgnoreCase("Remover Caminho")){
                    String path = txt.getText();
                    //adjacency.removeEdge(adjacency.getEdgeByName(path));
                }

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
                    //DijkstraResult result = dijkstra.calculateShortestPathFromOrigin(adjacency, adjacency.getVertexByName(p1),adjacency.getVertexByName(p2));

                }
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
        root1.add(voltar,1,6);
        root1.add(submit,3,6);
        scene1 = new Scene(root1);
        stage1.setTitle(function);
        stage1.setScene(scene1);
        stage1.show();
    }

}
