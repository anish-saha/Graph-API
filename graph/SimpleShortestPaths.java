package graph;

/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Anish Saha
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
        predecessors = new int[G.vertexSize() + 1];
        weights = new double[G.vertexSize() + 1];
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    @Override
    protected abstract double getWeight(int u, int v);

    @Override
    public double getWeight(int v) {
        if (_G.contains(v)) {
            return weights[v];
        }
        return Double.MAX_VALUE;
    }

    @Override
    protected void setWeight(int v, double w) {
        weights[v] = w;
    }

    @Override
    public int getPredecessor(int v) {
        if (_G.contains(v)) {
            return predecessors[v];
        }
        return 0;
    }

    @Override
    protected void setPredecessor(int v, int u) {
        predecessors[v] = u;
    }

    /** Stores the predecessors as ints. */
    private int[] predecessors;
    /** Stores the weights of the vertices as doubles. */
    private double[] weights;
}
