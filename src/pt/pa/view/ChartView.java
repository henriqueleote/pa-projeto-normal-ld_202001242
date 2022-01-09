package pt.pa.view;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.graph.Vertex;
import pt.pa.model.Hub;
import pt.pa.model.Network;

import java.util.Map;

public class ChartView {

    public ChartView(Network graphLoader, Stage stage) {
        stage.setTitle("Centralidade dos hubs");
        stage.setResizable(true);


        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> barChart = new BarChart<>(xAxis,yAxis);
        barChart.setTitle("Top 5 hubs");
        xAxis.setLabel("Hubs");
        yAxis.setLabel("Número de Hubs");
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Hubs");
        for(
                Map.Entry<Vertex<Hub>, Integer> i : graphLoader.moreCentredHubs(graphLoader.hubsOrdered())){
            series3.getData().add(new XYChart.Data(i.getKey().element().getName(), i.getValue()));
        }

        Scene scene1  = new Scene(barChart,800,600);
        barChart.getData().addAll(series3);
        stage.setScene(scene1);
        stage.show();
    }
}
