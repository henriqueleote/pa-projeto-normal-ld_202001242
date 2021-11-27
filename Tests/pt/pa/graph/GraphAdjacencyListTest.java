package pt.pa.graph;

import com.brunomnsilva.smartgraph.example.City;
import com.brunomnsilva.smartgraph.example.Distance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphAdjacencyListTest {
    GraphAdjacencyList<City, Distance> distances = new GraphAdjacencyList<>();
    @BeforeEach
    void setUp() {


        Vertex<City> prague = distances.insertVertex(new City("Prague", 0));
        Vertex<City> tokyo = distances.insertVertex(new City("Tokyo", 0));
        Vertex<City> beijing = distances.insertVertex(new City("Beijing", 0));
        Vertex<City> newYork = distances.insertVertex(new City("New York", 0));
        Vertex<City> london = distances.insertVertex(new City("London", 0));
        Vertex<City> helsinky = distances.insertVertex(new City("Helsinky", 0));

        distances.insertEdge(tokyo, newYork, new Distance(10838));
        distances.insertEdge(beijing, newYork, new Distance(11550));
        distances.insertEdge(beijing, tokyo, new Distance(1303));
        distances.insertEdge(london, newYork, new Distance(5567));
        distances.insertEdge(london, prague, new Distance(1264));
        distances.insertEdge(helsinky, tokyo, new Distance(7815));
        distances.insertEdge(prague, helsinky, new Distance(1845));
        distances.insertEdge(beijing, london, new Distance(8132));
    }

    @Test
    void numVertices() {
    assertEquals(6,distances.numVertices());
    }

    @Test
    void numEdges() {
        assertEquals(8,distances.numEdges());
    }

    @Test
    void vertices() {


    }

    @Test
    void edges() {

    }

    @Test
    void incidentEdges() {
    }

    @Test
    void opposite() {
    }

    @Test
    void areAdjacent() {
    }

    @Test
    void insertVertex() {
    }

    @Test
    void insertEdge() {
    }

    @Test
    void testInsertEdge() {
    }

    @Test
    void removeVertex() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void replace() {
    }

    @Test
    void testReplace() {
    }
}