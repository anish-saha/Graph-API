package graph;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

/** Unit tests for the Graph class.
 *  @author Anish Saha
 */
public class GraphTesting {
    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void undirectedTrivialTest() {
        UndirectedGraph g = new UndirectedGraph();
        assertEquals(0, g.vertexSize());
        assertEquals(0, g.maxVertex());
        assertEquals(0, g.edgeSize());
        assertEquals(0, g.outDegree(1));
        assertEquals(0, g.outDegree(37));
        assertEquals(0, g.inDegree(1));
        assertEquals(0, g.inDegree(37));
        assertEquals(false, g.successors(1).hasNext());
        assertEquals(false, g.predecessors(1).hasNext());
        assertEquals(false, g.edges().hasNext());
        assertEquals(false, g.vertices().hasNext());
        assertEquals(0, g.successor(1, 1));
        assertEquals(0, g.predecessor(1, 1));
        assertEquals(false, g.contains(1));
        assertEquals(false, g.contains(1, 37));
        assertEquals(false, g.contains(0));
        assertEquals(false, g.contains(0, 37));
    }

    @Test
    public void directedTrivialTest() {
        DirectedGraph g = new DirectedGraph();
        assertEquals(0, g.vertexSize());
        assertEquals(0, g.maxVertex());
        assertEquals(0, g.edgeSize());
        assertEquals(0, g.outDegree(1));
        assertEquals(0, g.outDegree(37));
        assertEquals(0, g.inDegree(1));
        assertEquals(0, g.inDegree(37));
        assertEquals(false, g.successors(0).hasNext());
        assertEquals(false, g.predecessors(0).hasNext());
        assertEquals(false, g.edges().hasNext());
        assertEquals(false, g.vertices().hasNext());
        assertEquals(0, g.successor(1, 1));
        assertEquals(0, g.predecessor(1, 1));
        assertEquals(false, g.contains(1));
        assertEquals(false, g.contains(1, 37));
        assertEquals(false, g.contains(0));
        assertEquals(false, g.contains(0, 37));
    }

    @Test
    public void undirectedTest() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 5; i += 1) {
            g.add();
        }

        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 3);
        g.add(5, 6);
        g.add(1, 2);

        assertEquals(5, g.vertexSize());
        assertEquals(3, g.edgeSize());
        assertEquals(3, g.outDegree(1));
        assertEquals(3, g.inDegree(1));
        assertEquals(1, g.outDegree(4));
        assertEquals(1, g.inDegree(4));
        assertEquals(true, g.contains(1));
        assertEquals(true, g.contains(4));
        assertEquals(false, g.contains(0));
        assertEquals(false, g.contains(8));
        assertEquals(true, g.contains(1, 4));
        assertEquals(true, g.contains(1, 2));
        assertEquals(false, g.contains(1, 1));
        assertEquals(false, g.contains(2, 5));
        assertEquals(false, g.contains(0, 1));
    }

    @Test
    public void undirectedTest2() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 5; i += 1) {
            g.add();
        }

        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 3);
        g.add(5, 6);
        g.add(1, 2);

        int scount1 = 0;
        for (int x : g.successors(1)) {
            scount1 += 1;
        }
        assertEquals(3, scount1);
        assertEquals(2, g.successor(1, 0));
        assertEquals(4, g.successor(1, 1));
        assertEquals(3, g.successor(1, 2));
        assertEquals(1, g.successor(2, 0));
        assertEquals(1, g.successor(3, 0));
        assertEquals(1, g.successor(4, 0));

        int scount2 = 0;
        for (int x : g.successors(2)) {
            scount2 += 1;
        }
        assertEquals(1, scount2);

        int pcount1 = 0;
        for (int x : g.predecessors(1)) {
            pcount1 += 1;
        }
        assertEquals(3, pcount1);
        assertEquals(2, g.predecessor(1, 0));
        assertEquals(4, g.predecessor(1, 1));
        assertEquals(3, g.predecessor(1, 2));
        assertEquals(1, g.predecessor(2, 0));
        assertEquals(1, g.predecessor(3, 0));
        assertEquals(1, g.predecessor(4, 0));

        int pcount2 = 0;
        for (int x : g.predecessors(2)) {
            pcount2 += 1;
        }
        assertEquals(1, pcount2);
        assertEquals(0, g.successor(5, 0));
        assertEquals(0, g.successor(0, 0));
        assertEquals(0, g.successor(8, 0));
    }

    @Test
    public void undirectedTest3() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 5; i += 1) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 3);
        g.add(5, 6);
        g.add(1, 2);
        g.remove(4);
        g.remove(1, 2);
        g.remove(8);
        g.remove(2, 3);

        assertEquals(4, g.vertexSize());
        assertEquals(5, g.maxVertex());
        assertEquals(1, g.edgeSize());
        assertEquals(1, g.outDegree(1));
        assertEquals(1, g.inDegree(1));
        assertEquals(0, g.outDegree(2));
        assertEquals(0, g.inDegree(2));
        assertEquals(true, g.contains(1));
        assertEquals(false, g.contains(4));
        assertEquals(true, g.contains(1, 3));
        assertEquals(false, g.contains(1, 2));
        assertEquals(false, g.contains(1, 4));

        g.add(1, 1);

        assertEquals(4, g.vertexSize());
        assertEquals(5, g.maxVertex());
        assertEquals(2, g.edgeSize());
        assertEquals(2, g.outDegree(1));
        assertEquals(2, g.inDegree(1));
        assertEquals(true, g.contains(1, 1));
        assertEquals(true, g.contains(1, 3));
        assertEquals(false, g.contains(1, 4));

        g.remove(5);

        assertEquals(3, g.vertexSize());
        assertEquals(3, g.maxVertex());
        assertEquals(0, g.successor(2, 0));
        assertEquals(0, g.predecessor(2, 0));
        assertEquals(0, g.successor(4, 0));
        assertEquals(0, g.predecessor(4, 0));
    }

    @Test
    public void undirectedTest4() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 5; i += 1) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 3);
        g.add(5, 6);
        g.add(1, 2);
        g.remove(4);
        g.remove(1, 2);
        g.remove(8);
        g.remove(2, 3);
        g.add(1, 1);
        g.remove(5);

        int count1 = 0;
        for (int x : g.successors(1)) {
            count1 += 1;
        }
        int count2 = 0;
        for (int x: g.predecessors(1)) {
            count2 += 1;
        }
        int count3 = 0;
        for (int x : g.successors(4)) {
            count3 += 1;
        }
        int count4 = 0;
        for (int x : g.predecessors(4)) {
            count4 += 1;
        }
        int count5 = 0;
        for (int x : g.predecessors(2)) {
            count5 += 1;
        }
        int count6 = 0;
        for (int x : g.successors(2)) {
            count6 += 1;
        }

        assertEquals(2, count1);
        assertEquals(2, count2);
        assertEquals(0, count3);
        assertEquals(0, count4);
        assertEquals(0, count5);
        assertEquals(0, count6);
    }

    @Test
    public void directedTest() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 5; i += 1) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 3);
        g.add(5, 6);
        g.add(1, 2);

        assertEquals(5, g.vertexSize());
        assertEquals(3, g.edgeSize());
        assertEquals(3, g.outDegree(1));
        assertEquals(0, g.inDegree(1));
        assertEquals(0, g.outDegree(4));
        assertEquals(1, g.inDegree(4));
        assertEquals(true, g.contains(1));
        assertEquals(true, g.contains(4));
        assertEquals(false, g.contains(0));
        assertEquals(false, g.contains(8));
        assertEquals(true, g.contains(1, 4));
        assertEquals(true, g.contains(1, 2));
        assertEquals(false, g.contains(1, 1));
        assertEquals(false, g.contains(2, 5));
        assertEquals(false, g.contains(0, 1));
    }

    @Test
    public void directedTest2() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 5; i += 1) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 3);
        g.add(5, 6);
        g.add(1, 2);

        int scount1 = 0;
        for (int x : g.successors(1)) {
            scount1 += 1;
        }
        assertEquals(3, scount1);
        assertEquals(2, g.successor(1, 0));
        assertEquals(4, g.successor(1, 1));
        assertEquals(3, g.successor(1, 2));
        assertEquals(0, g.successor(2, 0));
        assertEquals(0, g.successor(3, 0));
        assertEquals(0, g.successor(4, 0));

        int scount2 = 0;
        for (int x : g.successors(2)) {
            scount2 += 1;
        }
        assertEquals(0, scount2);

        int pcount1 = 0;
        for (int x : g.predecessors(1)) {
            pcount1 += 1;
        }
        assertEquals(0, pcount1);
        assertEquals(0, g.predecessor(1, 0));
        assertEquals(0, g.predecessor(1, 1));
        assertEquals(0, g.predecessor(1, 2));
        assertEquals(1, g.predecessor(2, 0));
        assertEquals(1, g.predecessor(3, 0));
        assertEquals(1, g.predecessor(4, 0));

        int pcount2 = 0;
        for (int x : g.predecessors(2)) {
            pcount2 += 1;
        }
        assertEquals(1, pcount2);
        assertEquals(0, g.successor(5, 0));
        assertEquals(0, g.successor(0, 0));
        assertEquals(0, g.successor(8, 0));
    }

    @Test
    public void directedTest3() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 5; i += 1) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 3);
        g.add(5, 6);
        g.add(1, 2);
        g.remove(4);
        g.remove(1, 2);
        g.remove(8);
        g.remove(2, 3);

        assertEquals(4, g.vertexSize());
        assertEquals(5, g.maxVertex());
        assertEquals(1, g.edgeSize());
        assertEquals(1, g.outDegree(1));
        assertEquals(0, g.inDegree(1));
        assertEquals(0, g.outDegree(2));
        assertEquals(0, g.inDegree(2));
        assertEquals(1, g.inDegree(3));
        assertEquals(0, g.outDegree(3));
        assertEquals(true, g.contains(1));
        assertEquals(false, g.contains(4));
        assertEquals(true, g.contains(1, 3));
        assertEquals(false, g.contains(1, 2));
        assertEquals(false, g.contains(1, 4));
        assertEquals(false, g.contains(5, 10));
        assertEquals(false, g.contains(4, 5));

        g.add(1, 1);

        assertEquals(4, g.vertexSize());
        assertEquals(2, g.edgeSize());
        assertEquals(2, g.outDegree(1));
        assertEquals(1, g.inDegree(1));
        assertEquals(true, g.contains(1, 1));
        assertEquals(true, g.contains(1, 3));
        assertEquals(false, g.contains(1, 4));

        g.remove(5);

        assertEquals(3, g.maxVertex());
        assertEquals(0, g.successor(2, 0));
        assertEquals(0, g.predecessor(2, 0));
        assertEquals(0, g.successor(4, 0));
        assertEquals(0, g.predecessor(4, 0));
    }

    @Test
    public void directedTest4() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 5; i += 1) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 3);
        g.add(5, 6);
        g.add(1, 2);
        g.remove(4);
        g.remove(1, 2);
        g.remove(8);
        g.remove(2, 3);
        g.add(1, 1);
        g.remove(5);
        int count1 = 0;
        for (int x : g.successors(1)) {
            count1 += 1;
        }
        int count2 = 0;
        for (int x: g.predecessors(1)) {
            count2 += 1;
        }
        int count3 = 0;
        for (int x : g.successors(4)) {
            count3 += 1;
        }
        int count4 = 0;
        for (int x : g.predecessors(4)) {
            count4 += 1;
        }
        int count5 = 0;
        for (int x : g.predecessors(2)) {
            count5 += 1;
        }
        int count6 = 0;
        for (int x : g.successors(2)) {
            count6 += 1;
        }

        assertEquals(2, count1);
        assertEquals(1, count2);
        assertEquals(0, count3);
        assertEquals(0, count4);
        assertEquals(0, count5);
        assertEquals(0, count6);
    }

    @Test
    public void dfsTest() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 5; i += 1) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 3);

        ArrayList<Integer> res = new ArrayList<Integer>();
        DepthFirstTraversal t = new DepthFirstTraversal(g);
        t.traverse(1);
        res.add(2);
        res.add(4);
        res.add(3);
        res.add(1);
        assertEquals(res, t.getVis());
    }

    @Test
    public void testEdge() {
        UndirectedGraph g = new UndirectedGraph();
        g.add();
        g.add();
        g.add(1, 2);
        g.add(2, 1);
        g.add(1, 1);
        g.add(2, 2);
        g.add(2, 2);
        assertEquals(true, g.contains(1, 1));
        assertEquals(true, g.contains(1, 2));
        assertEquals(true, g.contains(2, 1));
        assertEquals(true, g.contains(2, 2));
        g.remove(2, 2);
        assertEquals(true, g.contains(1, 2));
        assertEquals(false, g.contains(2, 2));
        g.remove(1, 1);
        assertEquals(false, g.contains(1, 1));
        g.remove(1, 1);
        assertEquals(true, g.contains(2, 1));
    }
}
