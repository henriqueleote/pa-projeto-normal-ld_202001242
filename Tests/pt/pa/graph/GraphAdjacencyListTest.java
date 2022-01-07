package pt.pa.graph;

import com.brunomnsilva.smartgraph.example.City;
import com.brunomnsilva.smartgraph.example.Distance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/* *****************************************************
    PA 2021/2022 EN - Rede de Logistica

    David Vaz - 201601644
    Guilherme Oliveira - 202000719
    Henrique Leote - 202001242
    Venelin Arguirov - 202001104
   ***************************************************** */

class GraphAdjacencyListTest {
    GraphAdjacencyList<City, Distance> distances;
    Vertex<City> prague;
    Vertex<City> tokyo;
    Vertex<City> beijing;
    Vertex<City> newYork;
    Vertex<City> london;
    Vertex<City> helsinky;

    Distance d1 = new Distance(10838);
    Distance d2 = new Distance(11550);
    Distance d3 = new Distance(1303);
    Distance d4 = new Distance(5567);
    Distance d5 = new Distance(1264);
    Distance d6 = new Distance(7815);
    Distance d7 = new Distance(1845);
    Distance d8 = new Distance(8132);
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
        distances.insertEdge(tokyo, newYork, d1);
        distances.insertEdge(beijing, newYork, d2);
        distances.insertEdge(beijing, tokyo, d3);
        distances.insertEdge(london, newYork, d4);
        distances.insertEdge(london, prague, d5);
        distances.insertEdge(helsinky, tokyo, d6);
        distances.insertEdge(prague, helsinky, d7);
        distances.insertEdge(beijing, london, d8);
    }

    @Test
    void numVertices() {
        Vertex<City> lisbon;
        assertEquals(6,distances.numVertices());
        lisbon = distances.insertVertex(new City("Lisbon", 0));
        assertEquals(7,distances.numVertices());
        distances.removeVertex(lisbon);
        assertEquals(6,distances.numVertices());
    }

    @Test
    void numEdges() {
        Vertex<City> lisbon;
        lisbon = distances.insertVertex(new City("Lisbon", 0));
        Distance dteste = new Distance(1000);
        assertEquals(8,distances.numEdges());
        distances.insertEdge(lisbon, newYork, dteste);
        assertEquals(9,distances.numEdges());
        distances.removeEdge(distances.getEdgeFromVertex(lisbon, dteste));
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
        Distance d = new Distance(14151);
        distances.insertEdge(london, helsinky, d);
        assertTrue(distances.opposite(london,distances.getEdgeFromVertex(london, d)).equals(helsinky));
    }

    @Test
    void areAdjacent() {
        assertTrue(distances.areAdjacent(tokyo, newYork));
    }

    @Test
    void removeVertex() {
        distances.removeVertex(newYork);
        assertFalse(distances.vertices().contains(newYork));
        assertEquals(5,distances.numVertices());
    }

    @Test
    void removeEdge() {
        distances.removeEdge(distances.getEdgeFromVertex(tokyo, d1));
        assertEquals(7,distances.edges().size());
    }

    @Test
    void replaceV() {
        City v1 = new City("OK", 5);
        distances.replace(prague,v1);
        assertTrue(prague.element()==v1);
    }

    @Test
    void replaceE() {
        Distance d = new Distance(14151);
        distances.replace(distances.getEdgeFromVertex(tokyo, d1),d);
    }

    @Test
    void throwInvalidEdgeExceptionOnInsert(){
        Vertex<City> lisbon = distances.insertVertex(new City("Lisbon",0));
        Vertex<City> porto = distances.insertVertex(new City("Porto",0));
        distances.insertEdge(lisbon,porto,new Distance(1000));
        assertThrows(InvalidEdgeException.class, ()->distances.insertEdge(lisbon,porto,null));
    }

    @Test
    void throwInvalidVertexExceptionOnInsert(){
        Vertex<City> lisbon = distances.insertVertex(new City("Lisbon",0));
        assertThrows(InvalidVertexException.class, ()->distances.insertVertex(null));
    }

    @Test
    void hubsOrdered(){
        assertEquals(6,distances.hubsOrdered().size());
    }

    @Test
    void moreCentredHubs(){
        assertEquals(5, distances.moreCentredHubs(distances.hubsOrdered()).size());
    }
}