package ex1;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;

//import java.util.Random;

import org.junit.Test;

public class my_WGraph_AlgoTest {

	@Test
	void test1() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(3);
		g.addNode(4);
		g.removeNode(2);
		g.removeNode(2);
		g.removeNode(1);
		g.addNode(7);
		g.addNode(8);
		g.removeNode(4);
		int s = g.nodeSize();
		assertEquals(4,s);
	}

	@Test
	void test2() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(3);
		g.addNode(4);
		g.connect(1,2,1);
		g.connect(2,3,1);
		g.connect(1,3,3);
		g.connect(1,2,3);
		int edge =  g.edgeSize();
		assertEquals(3,edge);
	}
	void test3() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(3);
		g.addNode(4);
		g.connect(1,2,1);
		g.connect(2,3,1);
		g.connect(1,3,3);
		g.connect(1,2,3);
		Collection<node_info> v = g.getV();
		Iterator<node_info> iter = v.iterator();
		while (iter.hasNext()) {
			node_info n = iter.next();
			assertNotNull(n);
		}
	}
}
