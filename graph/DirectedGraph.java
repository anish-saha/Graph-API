package graph;

import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *  @author Anish Saha
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        if (v > getAllVert().size() || getAllVert().get(v - 1) == null) {
            return 0;
        }
        return getAllVert().get(v - 1).getInEdges().size();
    }

    @Override
    public int predecessor(int v, int k) {
        if (v > getAllVert().size() || v < 1) {
            return 0;
        }
        if (getAllVert().get(v - 1) == null) {
            return 0;
        } else if (k >= getAllVert().get(v - 1).predecessors().size()) {
            return 0;
        }
        return getAllVert().indexOf(getAllVert().get(v - 1).
            predecessors().get(k)) + 1;
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
        ArrayList<Integer> pred = new ArrayList<Integer>();
        ArrayList<Vertex> predecessorVertices
            = getAllVert().get(v - 1).predecessors();
        for (int i = 0; i < predecessorVertices.size(); i += 1) {
            Vertex v2 = predecessorVertices.get(i);
            if (v2 != null) {
                pred.add(getAllVert().indexOf(v2) + 1);
            }
        }
        return Iteration.iteration(pred.iterator());
    }
}
