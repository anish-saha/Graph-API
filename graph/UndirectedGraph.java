package graph;

import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** Represents an undirected graph.  Out edges and in edges are not
 *  distinguished.  Likewise for successors and predecessors.
 *  @author Anish Saha
 */
public class UndirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public int inDegree(int v) {
        if (v > getAllVert().size() || getAllVert().get(v - 1) == null) {
            return 0;
        }
        return getAllVert().get(v - 1).getEdges().size();
    }

    @Override
    public int outDegree(int v) {
        return inDegree(v);
    }

    @Override
    public void remove(int u, int v) {
        if (getAllVert().size() <= v - 1 || getAllVert().get(v - 1) == null
            || getAllVert().size() <= u - 1
            || getAllVert().get(u - 1) == null) {
            return;
        }
        ArrayList<Edge> neighbors = getAllVert().get(u - 1).getEdges();
        Edge e = null;
        for (int i = 0; i < getAllEdges().size(); i += 1) {
            if (getAllEdges().get(i) != null
                && (getAllEdges().get(i).connects(u, v, getAllVert())
                || getAllEdges().get(i).connects(v, u, getAllVert()))) {
                e = getAllEdges().get(i);
            }
        }
        if (e != null && neighbors.contains(e)) {
            e.destroy();
            getAllEdges().remove(e);
        }
    }

    @Override
    public boolean contains(int u, int v) {
        if (u > getAllVert().size() || v > getAllVert().size()
            || u < 1 || v < 1) {
            return false;
        }
        if (getAllVert().get(u - 1) == null
            || getAllVert().get(v - 1) == null) {
            return false;
        }
        Edge e = null;
        for (int i = 0; i < getAllEdges().size(); i += 1) {
            if (getAllEdges().get(i) != null
                && (getAllEdges().get(i).connects(u, v, getAllVert())
                || getAllEdges().get(i).connects(v, u, getAllVert()))) {
                e = getAllEdges().get(i);
            }
        }
        if (e != null && getAllEdges().contains(e)) {
            return true;
        }
        return false;
    }

    @Override
    public int predecessor(int v, int k) {
        if (v > getAllVert().size() || v < 1) {
            return 0;
        }
        if (getAllVert().get(v - 1) == null) {
            return 0;
        }
        if (k >= getAllVert().get(v - 1).getNeighbors().size()) {
            return 0;
        }
        int x;
        x = getAllVert().indexOf(getAllVert().get(v - 1).
            getNeighbors().get(k)) + 1;
        return x;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        if (v > getAllVert().size() || v < 1) {
            ArrayList<Integer> blank = new ArrayList<Integer>();
            return Iteration.iteration(blank.iterator());
        }
        if (getAllVert().get(v - 1) == null) {
            ArrayList<Integer> blank = new ArrayList<Integer>();
            return Iteration.iteration(blank.iterator());
        }
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        ArrayList<Vertex> nVertices = getAllVert().get(v - 1).getNeighbors();
        for (int i = 0; i < nVertices.size(); i += 1) {
            Vertex v2 = nVertices.get(i);
            if (v2 != null) {
                neighbors.add(getAllVert().indexOf(v2) + 1);
            }
        }
        return Iteration.iteration(neighbors.iterator());
    }

    @Override
    public int successor(int v, int k) {
        return predecessor(v, k);
    }

    @Override
    public Iteration<Integer> successors(int v) {
        return predecessors(v);
    }
}
