package ex1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import ex1.WGraph_DS.NodeInfo;



public class WGraph_Algo implements weighted_graph_algorithms {

	private  weighted_graph g;

	public WGraph_Algo(){
		g=new WGraph_DS();
	}

	public void init(weighted_graph g) {
		this.g=g;
	}

	public weighted_graph getGraph() {

		if (g!=null)
		{
			return g;
		}
		return null;
	}

	public weighted_graph copy() {
		// Compute a deep copy of this graph.
		weighted_graph new_graph=new WGraph_DS();
		Collection<node_info> collection = g.getV();
		Iterator<node_info> iter = collection.iterator();
		while (iter.hasNext())//dist is zero.
		{
			NodeInfo C=(NodeInfo)iter.next();
			C.setLevel(0);
			C.setPrev(null);
		}
		//BFS
		Iterator<node_info> iter4 = collection.iterator();
		if (iter4.hasNext()==false)
		{
			return new_graph;
		}
		int src =iter4.next().getKey();
		Queue<node_info> q = new LinkedList<node_info>(); 
		node_info d=g.getNode(src);
		q.add(d);
		new_graph.addNode(d.getKey());
		NodeInfo D=(NodeInfo)d;
		D.setPrev(d);

		while (q.isEmpty()==false)
		{
			node_info temp_vertex=q.poll();
			NodeInfo TempV=(NodeInfo)temp_vertex;
			Collection<node_info> collection1 = TempV.getNi();
			int i=0;
			int size1=collection1.size();
			while (i<size1)
			{
				NodeInfo Temp=(NodeInfo)(collection1.toArray()[i]);
				if (Temp.getPrev()==null)
				{
					q.add(Temp);	
					Temp.setPrev(temp_vertex);
					new_graph.addNode(Temp.getKey());
				}
				i++;
			}	
		}
		WGraph_DS t =(WGraph_DS)new_graph;
		t.set_SizeOfEdges(g.edgeSize());

		return new_graph;
	}

	public boolean isConnected() {
		//Returns true if and only if (iff) there is a valid path from EVREY node to each
		//  other node. NOTE: assume ubdirectional graph.
		if (g.nodeSize()==0) return true; //graph is connected by definition
		else if (g.nodeSize()==1) return true;
		else if (g.nodeSize()==2&&g.edgeSize()==1) return true;
		else if (g.nodeSize()-1>g.edgeSize())return false; //not enough edges.

		//Check if the number of nodes I went through is equal to the number of nodes in the graph
		Collection<node_info> collection = g.getV();
		Iterator<node_info> iter = collection.iterator();
		while (iter.hasNext())//dist is zero.
		{
			NodeInfo C=(NodeInfo)iter.next();
			C.setLevel(0);
			C.setPrev(null);
		}
		//BFS
		int src =g.getV().iterator().next().getKey();
		int dist=0;
		Queue<node_info> q = new LinkedList<node_info>(); 
		node_info d=g.getNode(src);
		q.add(d);

		int visited=1;
		NodeInfo D=(NodeInfo)d;
		D.setPrev(d);
		D.setLevel(0);

		while (q.isEmpty()==false)
		{
			node_info temp_vertex=q.poll();
			NodeInfo TempV=(NodeInfo)temp_vertex;
			dist=TempV.getLevel()+1;
			Collection<node_info> collection1 = TempV.getNi();
			int i=0;
			int size1=collection1.size();
			while (i<size1)
			{
				NodeInfo Temp=(NodeInfo)(collection1.toArray()[i]);
				if (Temp.getPrev()==null)
				{
					q.add(Temp);	
					Temp.setTag(dist);
					Temp.setPrev(temp_vertex);
					visited++;
				}
				i++;
			}	
		}
		if (visited==g.nodeSize())return true;
		return false;
	}	

	public void DijkstraAlgoritem (int src, int dest)
	{
		Collection<node_info> collection = g.getV();
		Iterator<node_info> iter = collection.iterator();
		while (iter.hasNext())
		{
			NodeInfo C=(NodeInfo)iter.next();
			C.setTag(Double.MAX_VALUE);
			C.setPrev(null);
			C.setInfo("");
		}
		NodeInfo d=(NodeInfo) g.getNode(src);
		Queue<NodeInfo> q = new PriorityQueue<NodeInfo>();
		q.add(d);
		d.setPrev(d);
		d.setTag(0);

		while (q.isEmpty()==false && q.peek().getKey()!=dest)
		{
			NodeInfo node_1 =(NodeInfo) q.poll();
			Collection<node_info> collection1 = node_1.getNi();
			int i=0;
			int size1=collection1.size();
			while (i<size1)
			{
				NodeInfo node_2=(NodeInfo)(collection1.toArray()[i]);
				if (node_2.getInfo()!="black")
				{
					q.add(node_2);
					double node_1W = node_1.getTag();
					double edgeW = g.getEdge(node_1.getKey(), node_2.getKey());
					double node_2W = node_2.getTag();
					if (node_1W + edgeW < node_2W)
					{
						node_2.setPrev(node_1);
						node_2.setTag(node_1W + edgeW);
					}
				}
				i++;
			}
			node_1.setInfo("black");
		}
	}

	public double shortestPathDist(int src, int dest) {
		if (g.nodeSize()==0 || g.nodeSize()==1) return -1;
		DijkstraAlgoritem (src,dest);
		NodeInfo d = (NodeInfo) g.getNode(dest);
		double dist =d.getTag();
		if (d.getPrev()!=null) return dist;
		else return -1;
	}

	public List<node_info> shortestPath(int src, int dest) {
		DijkstraAlgoritem (src,dest);
		Stack <node_info> st=new Stack<node_info>();
		NodeInfo E=(NodeInfo)g.getNode(dest);
		while (E!=g.getNode(src))
		{
			st.add(E);
			E=(NodeInfo)E.getPrev();
		}
		node_info g=st.peek();
		NodeInfo G=(NodeInfo)g;
		st.add(G.getPrev());

		if (st.empty())return null;//if no such path --> returns null

		List<node_info> path = new ArrayList<node_info>();
		while (!st.empty())
		{
			path.add(st.pop());	
		}

		return path;

	}

	public boolean save(String file) {
		boolean ans=false;
		ObjectOutputStream oos;
		try
		{
			FileOutputStream fout=new FileOutputStream (file);
			oos= new ObjectOutputStream (fout);
			oos.writeObject(this);
			ans=true;

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return ans;
		}

		//g.save(file);
		return false;
	}

	public boolean load(String file) {
		try
		{
			FileInputStream streamIn = new FileInputStream (file);
			ObjectInputStream objectinputstream =new ObjectInputStream(streamIn);
			WGraph_Algo readCase = (WGraph_Algo) objectinputstream.readObject();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	public boolean equals(Object G1)
	{
		return equals((WGraph_DS)G1);
	}

}

