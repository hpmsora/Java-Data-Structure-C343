//a simple implementation of Graph using adjacency list
//C343 2016

/////////////////////////////////////////////////////////////////
//
// Won Yong Ha
//
// CSCI-C 343
// Lab 12
//
// Start: 31 March 2016
// End: 31 March 2016
//
// Comment: None
//
/////////////////////////////////////////////////////////////////

import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class AdjGraph implements Graph {
    private boolean digraph;
    private int totalNode;
    private Vector<String> nodeList;
    private int totalEdge;
    private Vector<LinkedList<Integer>>  adjList; //Adjacency list
    private Vector<Boolean> visited;
    private Vector<Integer> nodeEnum; //list of nodes pre visit
    public AdjGraph() {
	init();
    }
    public AdjGraph(boolean ifdigraph) {
	init();
	digraph =ifdigraph;
    }
    public void init() {
	nodeList = new Vector<String>(); 
	adjList = new Vector<LinkedList<Integer>>(); 
	visited = new Vector<Boolean>();
	nodeEnum = new Vector<Integer>();
	totalNode = totalEdge = 0;
	digraph = false;
    }
    //set vertices
    public void setVertex(String[] nodes) {
	for(int i = 0; i < nodes.length; i ++) {
	    nodeList.add(nodes[i]);
	    adjList.add(new LinkedList<Integer>());
	    visited.add(false);
	    totalNode ++;
	}
    }
    //add a vertex
    public void addVertex(String label) {
	nodeList.add(label);
	visited.add(false);
	adjList.add(new LinkedList<Integer>());
	totalNode ++;
    }
    public int getNode(String node) {
	for(int i = 0; i < nodeList.size(); i ++) {
	    if(nodeList.elementAt(i).equals(node)) return i;
	}
	return -1;
    }
    //return the number of vertices
    public int length() {
	return nodeList.size();
    }
    //add edge from v1 to v2
    public void setEdge(int v1, int v2, int weight) {
	LinkedList<Integer> tmp = adjList.elementAt(v1);
	if(adjList.elementAt(v1).contains(v2) == false) {
	    tmp.add(v2);
	    adjList.set(v1,  tmp);
	    totalEdge ++;
	}
    }
    public void setEdge(String v1, String v2, int weight) {
	if((getNode(v1) != -1) && (getNode(v2) != -1)) {
	    //add edge from v1 to v2
	    setEdge(getNode(v1), getNode(v2), weight);
	    //for digraph, add edge from v2 to v1 as well
	    if(digraph == false) setEdge(getNode(v2), getNode(v1), weight);
	}
    }

    //it is important to keep track if a vertex is visited or not (for traversal)
    public void setVisited(int v) {
	visited.set(v, true);
	nodeEnum.add(v);
    }
    public boolean ifVisited(int v) {
	return visited.get(v);
    }
    public void clearWalk() {
	//clean up before traverse
	nodeEnum.clear();
	for(int i = 0; i < nodeList.size(); i ++) visited.set(i, false);
    }
    public void walk(String method) {
	clearWalk();
	//traverse the graph 
	for(int i = 0; i < nodeList.size(); i ++) {
	    if(ifVisited(i) == false) {
		if(method.equals("BFS")) BFS(i); //i as the start node
		else if(method.equals("DFS")) DFS(i); //i as the start node
		else {
		    System.out.println("unrecognized traversal order: " + method);
		    System.exit(0);
		}
	    }
	}
	System.out.println(method + ":");
	displayEnum();
    }

    public void walk2(String method) {
	int idx = 0;
	int[] comp = new int[nodeList.size()];
	clearWalk();
	for (int i = 0; i < nodeList.size(); i++) {
	    if (ifVisited(i) == false) {
		BFS(i);
		for (int j = 0; j < nodeList.size(); j++) {
		    if (ifVisited(j)) {
			comp[idx]++;
		    }
		}
		idx++;
	    }
	}

	int realsize = 0;
	for (int i = 1; i < nodeList.size(); i++) {
	    if (comp[i] == nodeList.size()) {
		realsize = i + 1;
	    }
	}
	int[] real_comp = new int[realsize];
	
	if (realsize >= 1)
	    real_comp[0] = comp[0];
	for (int i = 1; i < realsize; i++) {
	    real_comp[i] = comp[i] - comp[i - 1];
	}
	System.out.print(method + ": ");
	for (int i = 0; i < realsize; i++) {
	    System.out.print(real_comp[i] + " ");
	}
	System.out.print("\n");
    }

    public void DFS(int v) {
	setVisited(v);
	LinkedList<Integer> neighbors = adjList.elementAt(v);
	for(int i = 0; i < neighbors.size(); i ++) {
	    int v1 = neighbors.get(i);
	    if(ifVisited(v1) == false) DFS(v1); 
	}
    }
    public void BFS(int s) {
	ArrayList<Integer> toVisit = new ArrayList<Integer>();
	toVisit.add(s);
	while(toVisit.size() > 0) {
	    int v = toVisit.remove(0); //first-in, first-visit
	    setVisited(v);
	    LinkedList<Integer> neighbors = adjList.elementAt(v);
	    for(int i = 0; i < neighbors.size(); i ++) {
		int v1 = neighbors.get(i);
		if((ifVisited(v1) == false) && (toVisit.contains(v1) == false)) {
		    toVisit.add(v1);
		}
	    }
	}
    }
    public void display() {
	System.out.println("total nodes: " + totalNode);
	System.out.println("total edges: " + totalEdge);
    }
    public void displayEnum() {
	for(int i = 0; i < nodeEnum.size(); i ++) {
	    System.out.print(nodeList.elementAt(nodeEnum.elementAt(i)) + " ");
	}
	System.out.println();
    }



    //////////////////////////////////////////////////////////

    // HW9

    public void topSort() {

	String[] L = new String[0];
	String[] L_helper = new String[0];
	int[] C = new int[nodeList.size()];
	String[] Q = new String[nodeList.size()];
	String[]Q_helper = new String[nodeList.size()];
	String u;

	String[] check = new String[nodeList.size()];

	clearWalk();

	for(int i = 0; i < nodeList.size(); i++) {
	    BFS(i);
	    for(int j = 0; j < nodeList.size(); j++) {
		if(i != j)
		    if(ifVisited(j))
			C[i]++;
	    }
	    clearWalk();
	}

	//Test
	/*
	for(int i = 0; i < C.length; i++)
	    System.out.println(C[i]);
	*/

	for(int i = 0; i < nodeList.size(); i++)
	    check[i] = nodeList.elementAt(i);

	for(int i = 0; i < nodeList.size(); i++) {
	    Q[i] = nodeList.elementAt(i);
	    Q_helper[i] = nodeList.elementAt(i);
	}
	clearWalk();
	while (Q.length != 0) {
	    int idx = Q.length - 1;
	    for(int i = 0; i < Q.length; i++)
		Q_helper[i] = Q[i];
	    u = Q[Q.length - 1];
	    Q = new String[Q.length - 1];
	    for(int i = 0; i < Q.length; i++)
		Q[i] = Q_helper[i];
	    int curr_indx = L.length;
	    for(int i = 0; i < L.length; i++)
		L_helper[i] = L[i];
	    L = new String[L.length + 1];
	    for(int i = 0; i < L_helper.length; i++)
		L[i] = L_helper[i];
	    L_helper = new String[L.length];
	    L[curr_indx] = u;

	    BFS(idx);
	    for(int i = 0; i < nodeList.size(); i++) {
		if(i != idx)
		    if(ifVisited(i))
			C[idx]--;
	    }
	    Q_helper = new String[Q.length];
	    clearWalk();
	}
	int res = 0;
	for(int i = 0; i < L.length; i++) {
	    for(int j = 0; j < check.length; j++) {
		if(L[i] == check[j])
		    res++;
	    }
       	}

	if(res == check.length)
	    for(int i = 0; i < L.length; i++)
		System.out.print(L[i] + " ");
	else
	    System.out.print("Solution not found");
	System.out.print("\n");
    }

    //////////////////////////////////////////////////////////



    public static void main(String args[]) {

	AdjGraph example = new AdjGraph ();
        String[] ex = {"A", "B", "C", "D", "E"};

	example.setVertex(ex);
	example.setEdge("B", "A", 0);
	example.setEdge("B", "C", 0);

	//example.walk("BFS");
	//example.walk("DFS");
	example.walk2("BFS");
	example.topSort();
    }
}
