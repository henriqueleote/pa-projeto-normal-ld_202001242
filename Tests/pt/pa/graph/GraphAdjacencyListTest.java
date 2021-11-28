package pt.pa.graph;

import com.brunomnsilva.smartgraph.example.City;
import com.brunomnsilva.smartgraph.example.Distance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphAdjacencyListTest {
    GraphAdjacencyList<City, Distance> distances;
    Vertex<City> prague;
    Vertex<City> tokyo;
    Vertex<City> beijing;
    Vertex<City> newYork;
    Vertex<City> london;
    Vertex<City> helsinky;
    @BeforeEach
    void setUp() {
        distances = new GraphAdjacencyList<>();

        //Vértices
        prague = distances.insertVertex(new City("Prague", 0));
        tokyo = distances.insertVertex(new City("Tokyo", 0));
        beijing = distances.insertVertex(new City("Beijing", 0));
        newYork = distances.insertVertex(new City("New York", 0));
        london = distances.insertVertex(new City("London", 0));
        helsinky = distances.insertVertex(new City("Helsinky", 0));

        //Arestas
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
        assertEquals(6,distances.vertices().size());
    }

    @Test
    void edges() {
        assertEquals(8,distances.edges().size());
    }

    @Test
    void incidentEdges() {
        assertEquals(2,distances.incidentEdges(prague).size());
    }

    @Test
    void opposite() {
        
    }

    @Test
    void areAdjacent() {
        assertTrue(distances.areAdjacent(tokyo, newYork));
    }

    @Test
    void insertVertex() {
        Vertex<City> barreiro = distances.insertVertex(new City("Barreiro", 0));
        assertTrue(distances.vertices().contains(barreiro));
    }

    @Test
    void insertEdge1() {
        //Edge<> e1 = new Edge<>();
        //assertTrue(distances.edges().contains(e1));
    }

    @Test
    void insertEdge2() {
    }

    @Test
    void removeVertex() {
        distances.removeVertex(newYork);
        assertFalse(distances.vertices().contains(newYork));
    }

    @Test
    void removeEdge() {
        //distances.removeEdge(cenas);
        //assertFalse(distances.edges().contains(cenas));
    }

    @Test
    void replaceV() {

        City v1 = new City("OK", 5);
        distances.replace(prague,v1);
        assertTrue(prague.element()==v1);

    }

    @Test
    void replaceE() {
    }
}