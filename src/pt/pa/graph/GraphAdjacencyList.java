package pt.pa.graph;

import java.util.*;

/* *****************************************************
    PA 2021/2022 EN - Rede de Logistica
    David Vaz - 201601644
    Guilherme Oliveira - 202000719
    Henrique Leote - 202001242
    Venelin Arguirov - 202001104
   ***************************************************** */


public class GraphAdjacencyList<V,E> implements Graph<V, E>{

    private Map<V, Vertex<V>> vertices;


    public GraphAdjacencyList() {
        this.vertices = new HashMap<>();
    }

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public int numEdges() {
        int count = 0;

        for (Vertex<V> i:vertices.values()) {
            MyVertex myU = checkVertex(i);
            count += myU.incidentEdges.size();
        }
        return count/2;
    }

    @Override
    public Collection<Vertex<V>> vertices() {
        List<Vertex<V>> vertexList = new ArrayList<>(vertices.values());
        return vertexList;
    }

    @Override
    public Collection<Edge<E, V>> edges() {

        List<Edge<E,V>> edgeList = new ArrayList<>();
        for (Vertex<V> i:vertices.values()) {
            MyVertex myU = checkVertex(i);
            for (Edge<E,V> k:myU.incidentEdges) {
                if(!edgeList.contains(k))
                    edgeList.add(k);
            }
        }
        return edgeList;
    }

    @Override
    public Collection<Edge<E, V>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
        MyVertex vertex = checkVertex(v);
        return vertex.incidentEdges;
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e) throws InvalidVertexException, InvalidEdgeException {
        MyVertex myV = checkVertex(v);
        if(!myV.incidentEdges.contains(e)) throw new InvalidEdgeException("Edge is not connected to the origin vertex");
        for (Vertex<V> i:vertices.values()) {
            MyVertex myU = checkVertex(i);
            if( myU.incidentEdges.contains(e) && i != v) {
                return myU;
            }
        }
        return null;
    }

    @Override
    public boolean areAdjacent(Vertex<V> u, Vertex<V> v) throws InvalidVertexException {
        MyVertex myU = checkVertex(u);
        MyVertex myV = checkVertex(v);

        //is there a common edge between myU.incidentEdges and myV.incidentEdges ?

        Set<Edge<E,V>> intersection = new HashSet<>(myU.incidentEdges);
        intersection.retainAll(myV.incidentEdges);

        return !intersection.isEmpty();
    }

    @Override
    public Vertex<V> insertVertex(V vElement) throws InvalidVertexException {
        if(vElement==null) throw new InvalidVertexException("Vertex element is null");
        MyVertex v = new MyVertex(vElement);
        vertices.put(vElement,v);
        return v;
    }



    @Override
    public Edge<E, V> insertEdge(Vertex<V> u, Vertex<V> v, E edgeElement) throws InvalidVertexException, InvalidEdgeException {
        if(edgeElement==null) throw new InvalidEdgeException("Edge element is null");
        MyVertex myU = checkVertex(u);
        MyVertex myV = checkVertex(v);
        MyEdge e = new MyEdge(edgeElement);
        myU.incidentEdges.add(e);
        myV.incidentEdges.add(e);
        return e;
    }

    @Override
    public Edge<E, V> insertEdge(V vElement1, V vElement2, E edgeElement) throws InvalidVertexException, InvalidEdgeException {
        if(vElement1==null || vElement2==null) throw new InvalidVertexException("Vertex element is null");
        if(edgeElement==null) throw new InvalidEdgeException("Edge element is null");
        MyVertex myU = null;
        MyVertex myV = null;
        MyEdge e = new MyEdge(edgeElement);
        for (Vertex<V> i: vertices.values()) {
            myU = getMyVertex(vElement1, myU, i);
            myV = getMyVertex(vElement2, myV, (Vertex<V>) i);
        }
        myU.incidentEdges.add(e);
        myV.incidentEdges.add(e);
        return e;
    }

    private MyVertex getMyVertex(V vElement2, MyVertex myV, Vertex<V> i) {
        if (vElement2.equals(i.element()))
            myV = checkVertex(i);
        return myV;
    }

    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {
        MyVertex toRemoveVertex = checkVertex(v);
        Set<Edge<E,V>> toRemoveEdges = new HashSet<>(toRemoveVertex.incidentEdges);
        for(Edge<E,V> e : toRemoveEdges) {
            MyVertex opVertex = checkVertex(opposite(v, e));
            opVertex.incidentEdges.remove(e);
        }
        vertices.remove(v.element());
        return toRemoveVertex.element;
    }

    @Override
    public E removeEdge(Edge<E, V> e) throws InvalidEdgeException {
        MyEdge myEdge = checkEdge(e);
        for (Vertex<V> i:vertices.values()) {
            MyVertex myU = checkVertex(i);
            myU.incidentEdges.removeIf(k -> k == e);
        }
        return e.element();
    }


    @Override
    public V replace(Vertex<V> v, V newElement) throws InvalidVertexException {
        if(newElement==null) throw new InvalidVertexException("Vertex element is null");
        MyVertex myV = checkVertex(v);
        myV.element = newElement;

        for (Vertex<V> i:vertices.values()) {
            if(i.equals(myV))
                i = myV;
        }
        return myV.element();
    }

    @Override
    public E replace(Edge<E, V> e, E newElement) throws InvalidEdgeException {
        if(newElement==null) throw new InvalidEdgeException("Edge element is null");
        MyEdge myEdge = checkEdge(e);
        MyEdge myEdge1 = myEdge;
        myEdge1.element = newElement;
        for (Vertex<V> i:vertices.values()) {
            MyVertex myU = checkVertex(i);
            for (Edge<E,V> k:myU.incidentEdges) {
                if(k==e)
                    k = myEdge1;
            }
        }
        return myEdge.element;
    }

    private class MyVertex implements Vertex<V> {
        private V element;
        private List<Edge<E,V>> incidentEdges;

        public MyVertex(V element) {
            this.element = element;
            this.incidentEdges = new ArrayList<>();
        }

        @Override
        public V element() {
            return element;
        }

        @Override
        public String toString() {
            return "Vertex{" + element + '}' + " --> " + incidentEdges.toString() + "\n";
        }
    }

    private class MyEdge implements Edge<E, V> {
        private E element;

        public MyEdge(E element) {
            this.element = element;
        }

        @Override
        public E element() {
            return element;
        }

        @Override
        public Vertex<V>[] vertices() {
            //if the edge exists, then two existing vertices have the edge
            //in their incidentEdges lists
            List<Vertex<V>> adjacentVertices = new ArrayList<>();

            for(Vertex<V> v : GraphAdjacencyList.this.vertices.values()) {
                MyVertex myV = (MyVertex) v;

                if( myV.incidentEdges.contains(this)) {
                    adjacentVertices.add(v);
                }
            }

            if(adjacentVertices.isEmpty()) {
                return new Vertex[]{null, null}; //edge was removed meanwhile
            } else {
                return new Vertex[]{adjacentVertices.get(0), adjacentVertices.get(1)};
            }
        }

        @Override
        public String toString() {
            return "Edge{" + element + "}";
        }
    }

    private MyVertex checkVertex(Vertex<V> v) throws InvalidVertexException {
        if(v == null) throw new InvalidVertexException("Null vertex.");

        MyVertex vertex;
        try {
            vertex = (MyVertex) v;
        } catch (ClassCastException e) {
            throw new InvalidVertexException("Not a vertex.");
        }

        if (!vertices.containsValue(v)) {
            throw new InvalidVertexException("Vertex does not belong to this graph.");
        }

        return vertex;
    }

    private MyEdge checkEdge(Edge<E, V> e) throws InvalidEdgeException {
        if(e == null) throw new InvalidEdgeException("Null edge.");

        MyEdge edge;
        try {
            edge = (MyEdge) e;
        } catch (ClassCastException ex) {
            throw new InvalidVertexException("Not an edge.");
        }

        if (!edges().contains(edge)) {
            throw new InvalidEdgeException("Edge does not belong to this graph.");
        }

        return edge;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Graph | Adjacency List : \n");

        for(Vertex<V> v : vertices.values()) {
            sb.append( String.format("%s", v) );
            sb.append("\n");
        }

        return sb.toString();
    }

    public Edge<E, V> getEdgeFromVertex(Vertex<V> v, E edgeElement){
        if(edgeElement == null) throw new InvalidEdgeException("Edge element is null");
        MyVertex myV = checkVertex(v);
        for (Edge<E,V> k:myV.incidentEdges) {
            if(k.element()==edgeElement)
                return k;
        }
        return null;
    }
}