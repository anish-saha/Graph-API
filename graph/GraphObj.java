package graph;

import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *  @author Anish Saha
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        allVertices = new ArrayList<Vertex>();
        allEdges = new ArrayList<Edge>();
    }


    @Override
    public int vertexSize() {
        int c = 0;
        for (Vertex v : allVertices) {
            if (v != null) {
                c += 1;
            }
        }
        return c;
    }

    @Override
    public int maxVertex() {
        int max = 0;
        for (int i = 0; i < allVertices.size(); i += 1) {
            if (allVertices.get(i) != null && i >= max) {
                max = i + 1;
            }
        }
        return max;
    }

    @Override
    public int edgeSize() {
        ArrayList<Edge> count = new ArrayList<Edge>();
        for (Vertex v : allVertices) {
            if (v != null) {
                for (Edge e : v.getEdges()) {
                    if (e != null) {
                        if (!count.contains(e)) {
                            count.add(e);
                        }
                    }
                }
            }
        }
        return count.size();
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (v > allVertices.size() || allVertices.get(v - 1) == null) {
            return 0;
        }
        return allVertices.get(v - 1).getOutEdges().size();
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        if (u > allVertices.size() || u < 1) {
            return false;
        }
        if (allVertices.get(u - 1) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(int u, int v) {
        if (u > allVertices.size() || v > allVertices.size()
            || u < 1 || v < 1) {
            return false;
        }
        if (allVertices.get(u - 1) == null || allVertices.get(v - 1) == null) {
            return false;
        }
        Edge e = null;
        for (int i = 0; i < allEdges.size(); i += 1) {
            if (allEdges.get(i) != null
                && allEdges.get(i).connects(u, v, allVertices)) {
                e = allEdges.get(i);
            }
        }
        if (e != null && allEdges.contains(e)) {
            return true;
        }
        return false;
    }

    @Override
    public int add() {
        int i = allVertices.indexOf(null);
        if (i == 0) {
            allVertices.remove(0);
            allVertices.add(0, new Vertex());
            return 1;
        }
        if (i == -1) {
            allVertices.add(new Vertex());
            return allVertices.size();
        }
        allVertices.remove(i);
        allVertices.add(i, new Vertex());
        return i + 1;
    }

    @Override
    public int add(int u, int v) {
        int label;
        if (allVertices.size() <= v - 1 || allVertices.get(v - 1) == null
            || allVertices.size() <= u - 1 || allVertices.get(u - 1) == null) {
            return -1;
        } else if (contains(u, v)) {
            return -1;
        } else {
            label = edgeId(u - 1, v - 1);
            ArrayList<Edge> neighbors = allVertices.get(u - 1).getEdges();
            Edge e = new Edge(allVertices.get(u - 1), allVertices.get(v - 1));
            e.setLabel(label);
            allEdges.add(e);
            return edgeId(u, v);
        }
    }

    @Override
    public void remove(int v) {
        if (allVertices.size() <= v - 1 || allVertices.get(v - 1) == null) {
            return;
        }
        Vertex vert = allVertices.get(v - 1);
        allEdges = vert.destroy(allEdges);
        allVertices.remove(vert);
        allVertices.add(v - 1, null);
    }

    @Override
    public void remove(int u, int v) {
        if (allVertices.size() <= v || allVertices.get(v - 1) == null
            || allVertices.size() <= u || allVertices.get(u - 1) == null) {
            return;
        }
        ArrayList<Edge> neighbors = allVertices.get(u - 1).getEdges();
        Edge e = null;
        for (int i = 0; i < allEdges.size(); i += 1) {
            if (allEdges.get(i) != null
                && allEdges.get(i).connects(u, v, allVertices)) {
                e = allEdges.get(i);
            }
        }
        if (e != null && neighbors.contains(e)) {
            e.destroy();
            allEdges.remove(e);
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        ArrayList<Integer> vert = new ArrayList<Integer>();
        for (int i = 0; i < allVertices.size(); i += 1) {
            Vertex v2 = allVertices.get(i);
            if (v2 != null) {
                vert.add(new Integer(i + 1));
            }
        }

        return Iteration.iteration(vert.iterator());
    }

    @Override
    public int successor(int v, int k) {
        if (v > allVertices.size() || v < 1) {
            return 0;
        }
        if (allVertices.get(v - 1) == null) {
            return 0;
        }
        if (k >= allVertices.get(v - 1).successors().size()) {
            return 0;
        }
        return allVertices.indexOf(allVertices.get(v - 1).successors().get(k))
            + 1;
    }

    @Override
    public abstract int predecessor(int v, int k);

    @Override
    public Iteration<Integer> successors(int v) {
        if (v > allVertices.size() ||  v < 1) {
            ArrayList<Integer> blank = new ArrayList<Integer>();
            return Iteration.iteration(blank.iterator());
        }
        if (allVertices.get(v - 1) == null) {
            ArrayList<Integer> blank = new ArrayList<Integer>();
            return Iteration.iteration(blank.iterator());
        }
        ArrayList<Integer> succ = new ArrayList<Integer>();
        ArrayList<Vertex> successorVertices
            = allVertices.get(v - 1).successors();
        for (int i = 0; i < successorVertices.size(); i += 1) {
            Vertex v2 = successorVertices.get(i);
            if (v2 != null) {
                succ.add(allVertices.indexOf(v2) + 1);
            }
        }
        return Iteration.iteration(succ.iterator());
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        ArrayList<int[]> i = new ArrayList<int[]>();
        for (Edge e : allEdges) {
            if (e != null) {
                i.add(new int[]{(allVertices.indexOf(e.getV1()) + 1),
                    (allVertices.indexOf(e.getV2()) + 1)});
            }
        }
        return Iteration.iteration(i.iterator());
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!contains(v)) {
            throw new IllegalArgumentException("vertex not from Graph!");
        }
    }

    /** Formula inspired by CS61B Slack Group. */
    @Override
    protected int edgeId(int u, int v) {
        if (isDirected()) {
            if (u >= v) {
                return u * u + u + v;
            } else {
                return v * v + u;
            }
        } else {
            int x = Math.max(u, v);
            int y = Math.min(u, v);
            return ((x * x + x) / 2) + y;
        }
    }

    /** Getter method for private allVertices.
    @return gets allVertices. */
    public ArrayList<Vertex> getAllVert() {
        return allVertices;
    }

    /** Getter method for private allEdges.
    @return get allEdges. */
    public ArrayList<Edge> getAllEdges() {
        return allEdges;
    }

    /** Keeps track of all vertices in the graph. See Vertex class.
    Replaces with null when removed, lowest available vertex added.
    Order is relevant. Indices offset by -1 to account for user preferences. */
    private ArrayList<Vertex> allVertices;
    /** Keeps track of all edges in the graph. No repeated/multiedges.
    Self-edges OK. Preserves order of edges added/removed. */
    private ArrayList<Edge> allEdges;


    /** Vertex class.
     * @author Anish Saha
     */
    class Vertex {
        /** Constructor for vertex. Stores arraylist of its edges. */
        Vertex() {
            edges = new ArrayList<Edge>();
        }

        /** Gets all edges associated with this vertex.
        @return This method returns the vertex's edges. */
        ArrayList<Edge> getEdges() {
            ArrayList<Edge> copied = (ArrayList<Edge>) edges.clone();
            ArrayList<Edge> dups = new ArrayList<Edge>();
            for (Edge e : copied) {
                if (dups.contains(e)) {
                    edges.remove(e);
                }
                dups.add(e);
            }
            return edges;
        }

        /** Gets all incident vertices to this vertex.
        @return This method returns the list of incident vertices. */
        ArrayList<Vertex> getNeighbors() {
            if (getEdges() == null) {
                return new ArrayList<Vertex>();
            }
            ArrayList<Vertex> l = new ArrayList<Vertex>();
            for (Edge e : getEdges()) {
                l.add(e.getOtherVertex(this));
            }
            return l;
        }

        /** Removes the vertex and all edges associated with it.
        @param allEdge is the list of all edges in the graph.
        @return This method returns the arraylist of all edges after removal. */
        ArrayList<Edge> destroy(ArrayList<Edge> allEdge) {
            for (Edge e : (ArrayList<Edge>) edges.clone()) {
                e.destroy();
                allEdge.remove(e);
            }
            return allEdge;
        }

        /** List of all inedges of this vertex.
        @return This method returns the list of all inedges to this vertex. */
        ArrayList<Edge> getInEdges() {
            ArrayList<Edge> inEdges = new ArrayList<Edge>();
            for (Edge e : edges) {
                if (!e.isOrigin(this)) {
                    inEdges.add(e);
                }
                if (e.getV1().equals(this) && e.getV2().equals(this)) {
                    inEdges.add(e);
                }
            }
            ArrayList<Edge> copied = (ArrayList<Edge>) inEdges.clone();
            ArrayList<Edge> dups = new ArrayList<Edge>();
            for (Edge e : copied) {
                if (dups.contains(e)) {
                    inEdges.remove(e);
                }
                dups.add(e);
            }
            return inEdges;
        }

        /** List of all outedges of this vertex.
        @return This method returns the list of all outedges to this vertex. */
        ArrayList<Edge> getOutEdges() {
            ArrayList<Edge> outEdges = new ArrayList<Edge>();
            for (Edge e : edges) {
                if (e.isOrigin(this)) {
                    outEdges.add(e);
                }
                if (e.getV1().equals(this) && e.getV2().equals(this)) {
                    outEdges.add(e);
                }
            }
            ArrayList<Edge> copied = (ArrayList<Edge>) outEdges.clone();
            ArrayList<Edge> dups = new ArrayList<Edge>();
            for (Edge e : copied) {
                if (dups.contains(e)) {
                    outEdges.remove(e);
                }
                dups.add(e);
            }
            return outEdges;
        }

        /** Get index of vertex in allVertices.
        @param allVert in the arraylist we are finding index in.
        @return This method return the index. */
        int getIndex(ArrayList<Vertex> allVert) {
            return allVert.indexOf(this) + 1;
        }

        /** List of predecessor vertices of this vertex.
        @return This method returns list of all predecessors to this vertex. */
        ArrayList<Vertex> predecessors() {
            ArrayList<Edge> inEdges = getInEdges();
            ArrayList<Vertex> pred = new ArrayList<Vertex>();
            for (Edge e : inEdges) {
                pred.add(e.getOtherVertex(this));
            }
            return pred;
        }

        /** List of successor vertices of this vertex.
        @return This method returns list of all successors to this vertex. */
        ArrayList<Vertex> successors() {
            ArrayList<Edge> outEdges = getOutEdges();
            ArrayList<Vertex> succ = new ArrayList<Vertex>();
            for (Edge e: outEdges) {
                succ.add(e.getOtherVertex(this));
            }
            return succ;
        }

        /** Adds edge to edgelist of this vertex.
        @param e is the edge that is to be added. */
        void addEdge(Edge e) {
            edges.add(e);
        }

        /** Removes edge from edgelist of this vertex.
        @param e is the edge that is to be added. */
        void removeEdge(Edge e) {
            edges.remove(e);
        }

        /** List of all edges associated with this vertex. */
        private ArrayList<Edge> edges;
    }

    /** Edge class.
     * @author Anish Saha
     */
    class Edge {
        /** This is the constructor for the edge class. It takes in 2 vertices,
        to and from. Also, automatically adds to the edgelist of said vertices.
        @param vert1 is the from vertex of the edge, irrelevant in undirected.
        @param vert2 is the to vertex of the edge, irrelevant in undirected. */
        Edge(Vertex vert1, Vertex vert2) {
            v1 = vert1;
            v2 = vert2;
            v1.addEdge(this);
            v2.addEdge(this);
            weight = 0;
            label = 0;
        }

        /** Helper to create the edge.
        @param vert1 is the from vertex.
        @param vert2 is the to vertex.
        @param allVert is the list of all vertices in the graph.
        @return This method returns the boolean if it is to be selected. */
        boolean connects(int vert1, int vert2, ArrayList<Vertex> allVert) {
            return v1 == allVert.get(vert1 - 1) && v2 == allVert.get(vert2 - 1);
        }

        /** Overrides the typical .equals method of object, functions for edge.
        @param e is the edge that is passed in to be checked against.
        @return This method returns the boolean of whether it is equal. */
        boolean equals(Edge e) {
            return v1 == e.v1 && v2 == e.v2;
        }

        /** Gets the other vertex of an edge.
        @param v is one of the vertices of the edge.
        @return This method returns the other vertex of the edge. */
        Vertex getOtherVertex(Vertex v) {
            if (v.equals(v1)) {
                return v2;
            }
            if (v.equals(v2)) {
                return v1;
            }
            return null;
        }

        /** Gets the from vertex of the edge.
        @return v1 of the vertex is returned. */
        Vertex getV1() {
            return v1;
        }

        /** Gets the to vertex of the edge.
        @return v2 of the vertex is returned. */
        Vertex getV2() {
            return v2;
        }

        /** Sets the weight of the edge.
        @param w is the weight that it will be set to. */
        void setWeight(int w) {
            weight = w;
        }

        /** Sets the label to id.
        @param id is what the label will now be. */
        void setLabel(int id) {
            label = id;
        }

        /** Returns the weight of the edge.
        @return weight of the vertex is returned. */
        int weight() {
            return weight;
        }

        /** Returns if a vertex is the from vertex of an edge.
        @param v is the vertex that is checked.
        @return the boolean of whether the from vertex is v is returned. */
        boolean isOrigin(Vertex v) {
            return v1.equals(v);
        }

        /** Removes the edge from both associated vertices. */
        void destroy() {
            v1.getEdges().remove(this);
            v2.getEdges().remove(this);
        }

        /** This vertex is the "from" vertex, for scope purposes. */
        private Vertex v1;
        /** This vertex is the "to" vertex, for scope purposes. */
        private Vertex v2;
        /** This is the weight of the edge, for scope purposes. */
        private int weight;
        /** This is the label of the edge, as with the edgeID formula. */
        private int label;
    }
}

