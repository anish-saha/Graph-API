package graph;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

/** Unit tests for the Graph class.
 *  @author Anish Saha
 */
public class TraversalTesting {
    @Test
    public void ugTraversalBFS() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 8; i += 1) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 3);
        g.add(2, 3);
        g.add(4, 5);
        g.add(5, 3);
        g.add(3, 6);
        g.add(3, 7);
        g.add(6, 8);
        g.add(7, 8);
        BreadthFirstTraversal t = new BreadthFirstTraversal(g);
        t.traverse(1);
        ArrayList<Integer> res = new ArrayList<Integer>();
        res.add(1);
        res.add(2);
        res.add(4);
        res.add(3);
        res.add(5);
        res.add(6);
        res.add(7);
        res.add(8);
        assertEquals(res, t.getMarked());
    }

    @Test
    public void ugTraversalDFS() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 8; i += 1) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 3);
        g.add(2, 3);
        g.add(4, 5);
        g.add(5, 3);
        g.add(3, 6);
        g.add(3, 7);
        g.add(6, 8);
        g.add(7, 8);
        DepthFirstTraversal t = new DepthFirstTraversal(g);
        t.traverse(1);
        ArrayList<Integer> res = new ArrayList<Integer>();
        res.add(1);
        res.add(2);
        res.add(3);
        res.add(5);
        res.add(4);
        res.add(6);
        res.add(8);
        res.add(7);
        assertEquals(res, t.getMarked());
        res.clear();
        res.add(4);
        res.add(5);
        res.add(7);
        res.add(8);
        res.add(6);
        res.add(3);
        res.add(2);
        res.add(1);
        assertEquals(res, t.getVis());
    }

    @Test
    public void dgTraversalBFS() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 8; i += 1) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 3);
        g.add(2, 3);
        g.add(4, 5);
        g.add(5, 3);
        g.add(3, 6);
        g.add(3, 7);
        g.add(6, 8);
        g.add(7, 8);
        BreadthFirstTraversal t = new BreadthFirstTraversal(g);
        t.traverse(1);
        ArrayList<Integer> res = new ArrayList<Integer>();
        res.add(1);
        res.add(2);
        res.add(4);
        res.add(3);
        res.add(5);
        res.add(6);
        res.add(7);
        res.add(8);
        assertEquals(res, t.getMarked());
    }

    @Test
    public void dgTraversalDFS() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 8; i += 1) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 3);
        g.add(2, 3);
        g.add(4, 5);
        g.add(5, 3);
        g.add(3, 6);
        g.add(3, 7);
        g.add(6, 8);
        g.add(7, 8);
        DepthFirstTraversal t = new DepthFirstTraversal(g);
        t.traverse(1);
        ArrayList<Integer> res = new ArrayList<Integer>();
        res.add(1);
        res.add(2);
        res.add(3);
        res.add(6);
        res.add(8);
        res.add(7);
        res.add(4);
        res.add(5);
        assertEquals(res, t.getMarked());
        res.clear();
        res.add(8);
        res.add(6);
        res.add(7);
        res.add(3);
        res.add(2);
        res.add(5);
        res.add(4);
        res.add(1);
        assertEquals(res, t.getVis());
    }
}
