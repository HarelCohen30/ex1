package ex1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;


public class WGraph_DS implements weighted_graph{

	private HashMap <Integer , node_info> point ;
	private int size_of_edges;
	private int mode_count;


	public static class NodeInfo implements node_info,Comparable<NodeInfo>{

		private int key;
		private String info;
		private double tag;//price to this node;
		private HashMap<Integer, node_info> Neighbors = new HashMap <Integer , node_info>();
		private HashMap <Integer,Double> weight = new HashMap <Integer,Double>();
		private node_info Prev;
		private int level;
		private static int last_key=-1;


		public NodeInfo()
		{
			key=last_key+1;
			last_key=key;
			info="";
			tag=0;
		}
		public NodeInfo(int key)
		{
			this.key=key;
			last_key=key;
			info="";
			tag=0;
		}

		public int getKey() {
			return key;		
		}
		public void setkey(int key) {
			this.key=key;
		}

		public String getInfo() {
			return info;
		}

		public void setInfo(String s) {
			this.info=s;
		}

		public double getTag() {
			return tag;
		}

		public void setTag(double t) {
			this.tag=t;
		}

		public Collection <node_info> getNi() {//This method returns a collection with all the Neighbor nodes of this node_data
			Collection<node_info> coll = new  ArrayList<node_info>();
			Iterator<node_info> iter = Neighbors.values().iterator();
			while (iter.hasNext())
			{
				node_info b=iter.next();
				coll.add(b);
			}
			return coll;
		}

		public boolean hasNi(int key) {//return true iff this<==>key are adjacent, as an edge between them
			if (Neighbors.containsKey(key))return true;
			return false;
		}

		public void addNi(node_info t) {//This method adds the node_data (t) to this node_data
			Neighbors.put(t.getKey(),t);	
		}
		public void addWeight(int node2, double w) {
			weight.put(node2,w);
		}

		public void removeNode(node_info node) {
			//Removes the edge this-key,
			//Removes the edge this-node.
			Neighbors.remove(node.getKey());
		}

		public double getW (int node2_key) {
			double w= weight.get(node2_key);
			return w;
		}

		public void removeW(int node2) {
			//Removes the edge between node1 and node2.
			weight.remove(node2);
		}

		public node_info getPrev() {
			return Prev;
		}

		public void setPrev(node_info prev) {
			Prev = prev;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}


		public int compareTo(NodeInfo node) {
			if(node.getTag()>this.tag)return 1;
			if(node.getTag()<this.tag)return -1;
			else return 0;
		}	

	}





	public WGraph_DS ()
	{
		point=new HashMap <Integer , node_info>();
		size_of_edges=0;
		mode_count=0;
	}

	public node_info getNode(int key) {
		node_info a= point.get(key);
		if (a!=null)
		{
			return a;
		}
		return null;
	}

	public boolean hasEdge(int node1, int node2) {

		NodeInfo a1=(NodeInfo) point.get(node1);
		NodeInfo a2=(NodeInfo) point.get(node2);
		if (a1==null || a2==null ) return false; 
		else if(a1.hasNi(node2) && a2.hasNi(node1))
		{
			return true;
		}
		return false;
	}

	public double getEdge(int node1, int node2) {
		if((point.get(node1)!=null && point.get(node2)!=null) && hasEdge(node1, node2))
		{
			node_info n = point.get(node1);
			NodeInfo N =(NodeInfo)n;
			double b = N.getW(node2);
			return b;
		}
		return -1;
	}

	public void addNode(int key) 
	{
		node_info n=new NodeInfo(key);
		if(!point.containsKey(key))
		{
			addNode(n);
		}
	}

	public void addNode(node_info n) {//add a new node to the graph with the given node_data
		if (n!=null)
		{
			point.put(n.getKey(), n);
			mode_count++;
		}
	}

	public void connect(int node1, int node2, double w) {
		boolean a=point.get(node1)!=null && point.get(node2)!=null;
		boolean c= node1 != node2;
		boolean d= w>=0;
		if (a&&c&&d)
		{
			NodeInfo a1=(NodeInfo) point.get(node1);
			NodeInfo a2=(NodeInfo) point.get(node2); 
			if(!hasEdge(node1,node2))
			{	
				a1.addNi(point.get(node2));
				a2.addNi(point.get(node1));
				a1.addWeight(node2, w);
				a2.addWeight(node1, w);
				size_of_edges++;
			}
			else
			{
				a1.addWeight(node2, w);
				a2.addWeight(node1, w);
			}
			mode_count++;
		}
	}

	public Collection <node_info> getV() {
		//This method return a pointer (shallow copy) for the
		// collection representing all the nodes in the graph. 
		if (point==null)return null;
		return point.values();
	}

	public Collection <node_info> getV(int node_id) {
		//This method return a collection of  the
		// collection representing all the nodes connected to node_id
		// This method returns a collection containing all the
		// nodes connected to node_id

		Collection<node_info> coll = new  ArrayList<node_info>();
		Iterator<node_info> iter = point.values().iterator();
		while (iter.hasNext())
		{
			node_info b=iter.next();
			coll.add(b);
		}
		return coll;
	}

	public node_info removeNode( int key) {//Delete the node (with the given ID) from the graph



		node_info temp = point.get(key);
		if (temp!=null)
		{
			NodeInfo Temp=(NodeInfo) temp;
			Collection<node_info> collection = Temp.getNi();
			Iterator<node_info> iter = collection.iterator();

			while (iter.hasNext())
			{
				node_info b=iter.next();
				removeEdge(b.getKey(),key);
			}
			Temp.getNi().clear();
			point.remove(key);
			mode_count++;
			return temp;
		}
		return null;
	}

	public void removeEdge(int node1, int node2) {
		if (hasEdge(node1,node2))
		{
			NodeInfo a1=(NodeInfo) point.get(node1);
			NodeInfo a2=(NodeInfo) point.get(node2); 
			a1.removeW(node2);
			a2.removeW(node1);
			a1.removeNode(a2);
			a2.removeNode(a1);
			size_of_edges--;
			mode_count++;
		}
	}

	public int nodeSize() {
		if (point!=null)
		{
			int x=getV().size();
			return x;
		}
		else
		{
			return 0;
		}
	}

	public int edgeSize() {////return the number of edges 
		return size_of_edges;
	}

	public int getMC() {//return the Mode Count
		return mode_count++;
	}

	public void set_SizeOfEdges(int size_of_edges) {
		this.size_of_edges=size_of_edges;
	}

}
