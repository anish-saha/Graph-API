package graph;

/* See restrictions in Graph.java. */

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.AbstractQueue;
import java.util.Iterator;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Anish Saha
 *  contributor: Puya Muoyer
 */

public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        pqueue = new MyQueue();
        a = new AStarTraversal(_G, pqueue);
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        for (int v : _G.vertices()) {
            setWeight(v, Double.MAX_VALUE);
        }
        a = new AStarTraversal(_G, pqueue);
        setWeight(_source, 0);
        a.traverse(_source);
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        int copy = v;
        ArrayList<Integer> arr = new ArrayList<Integer>();
        ArrayList<Integer> res = new ArrayList<Integer>();
        arr.add(copy);
        while (copy != getSource()) {
            arr.add(getPredecessor(copy));
            copy = getPredecessor(copy);
        }
        for (int i : arr) {
            res.add(0, i);
        }
        return res;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /** My Queue is a class that allows us to use TreeSet as a queue.
     * @author Anish Saha
     */
    private class MyQueue extends AbstractQueue<Integer> {
        /** Constructor takes in a treeset, which uses a custom comparator. */
        MyQueue() {
            tset = new TreeSet<Integer>(new MyComparator());
        }

        @Override
        public Iterator<Integer> iterator() {
            return tset.iterator();
        }

        @Override
        public Integer poll() {
            return tset.pollFirst();
        }

        @Override
        public Integer peek() {
            return tset.first();
        }

        @Override
        public boolean offer(Integer i) {
            return tset.add(i);
        }

        /** This method removes i from the treeset.
        @param i is the integert to be removed.
        @return This method returns if i could be removed. */
        public boolean remove(Integer i) {
            return tset.remove(i);
        }

        @Override
        public int size() {
            return tset.size();
        }

        /** Treeset that prioritizes based on comparator; PriorityQueue avoided
        due to runtime issues with dynamic applications associated. */
        private TreeSet<Integer> tset;
        /** Helps overwrite the comparator so it works for our queue. */
        private MyComparator compare;
    }

    /** Custom comparator for use with treeset above.
     * @author Anish Saha
     */
    private class MyComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer u, Integer v) {
            double from = getWeight(u) + estimatedDistance(u);
            double to = getWeight(v) + estimatedDistance(v);
            if (from < to) {
                return -1;
            }
            if (from >= to) {
                return 1;
            }
            return 0;
        }
    }

    /** AStarTraversal is a class that overrides Traversal. Used in this
    implementation of finding shortest paths.
     * @author Anish Saha
     */
    private class AStarTraversal extends Traversal {
        /** The constructor takes in the same variables as Traversal.
        @param G is the graph that is passed in.
        @param fringe is the queue used in the traversal. */
        AStarTraversal(Graph G, Queue<Integer> fringe) {
            super(G, fringe);
        }

        @Override
        protected boolean shouldPostVisit(int v) {
            return false;
        }

        @Override
        protected boolean visit(int v) {
            if (v == _dest) {
                return false;
            }
            return true;
        }

        @Override
        protected boolean processSuccessor(int u, int v) {
            if (!marked(v)) {
                double c = getWeight(u) + getWeight(u, v);
                if (c < getWeight(v)) {
                    setPredecessor(v, u);
                    setWeight(v, c);
                    _fringe.remove(v);
                    return true;
                }
            }
            return false;
        }
    }

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    /** The modified priotity queue. */
    private final MyQueue pqueue;
    /** Astar traversal variable. */
    private AStarTraversal a;
}
