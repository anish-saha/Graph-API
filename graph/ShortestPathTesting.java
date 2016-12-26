package graph;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/** Unit tests for the Graph class.
 *  @author Anish Saha
 */
public class ShortestPathTesting {
    @Test
    @SuppressWarnings("deprecation")
    public void ugShortestTest() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 6; i += 1) {
            g.add();
        }
        g.add(1, 2);
        g.add(2, 3);
        g.add(2, 1);
        g.add(3, 1);
        g.add(3, 4);
        g.add(3, 5);
        g.add(4, 5);
        g.add(5, 3);
        g.add(5, 6);
        ShortestPathTestHelper test = new ShortestPathTestHelper(g, 1, 6);
        test.setWeight(1, 2, 1);
        test.setWeight(2, 3, 1);
        test.setWeight(2, 1, 2);
        test.setWeight(3, 1, 1);
        test.setWeight(3, 4, 5);
        test.setWeight(3, 5, 24);
        test.setWeight(4, 5, 8);
        test.setWeight(5, 3, 1);
        test.setWeight(5, 6, 2);
        test.setPaths();
        List<Integer> path = test.pathTo();
        Object[] res = path.toArray();
        assertEquals(res, new Integer[]{1, 3, 5, 6});
    }

    @Test
    @SuppressWarnings("deprecation")
    public void dgShortestTest() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 6; i += 1) {
            g.add();
        }
        g.add(1, 2);
        g.add(2, 3);
        g.add(2, 1);
        g.add(3, 1);
        g.add(3, 4);
        g.add(3, 5);
        g.add(4, 5);
        g.add(5, 3);
        g.add(5, 6);
        ShortestPathTestHelper test = new ShortestPathTestHelper(g, 1, 6);
        test.setWeight(1, 2, 1);
        test.setWeight(2, 3, 1);
        test.setWeight(2, 1, 2);
        test.setWeight(3, 1, 1);
        test.setWeight(3, 4, 5);
        test.setWeight(3, 5, 24);
        test.setWeight(4, 5, 8);
        test.setWeight(5, 3, 1);
        test.setWeight(5, 6, 2);
        test.setPaths();
        List<Integer> path = test.pathTo();
        Object[] res = path.toArray();
        assertEquals(res, new Integer[]{1, 2, 3, 4, 5, 6});
    }

    private class ShortestPathTestHelper extends SimpleShortestPaths {
        public ShortestPathTestHelper(Graph G, int source, int dest) {
            super(G, source, dest);
            int bound = 800;
            weights = new ArrayList<Double>();
            for (int i = 0; i < bound; i += 1) {
                weights.add(null);
            }
        }

        public void setWeight(int u, int v, double weight) {
            int i = _G.edgeId(u, v);
            weights.remove(i);
            weights.add(i, weight);
        }

        @Override
        public double getWeight(int u, int v) {
            int i = _G.edgeId(u, v);
            if (!(weights.get(i) == null)) {
                return weights.get(i);
            }
            return Double.MAX_VALUE;
        }

        private ArrayList<Double> weights;
    }
}
