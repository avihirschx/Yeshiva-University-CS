package edu.yc.oats.algs;

import java.util.ArrayList;

public class TopologicalQ {
	private int[] indegrees;
	private int[] outdegrees;
	private Queue<Integer> order;
	private int vertices;

	public TopologicalQ(Digraph G) {
		
		//First, store the number of indegrees and outdegrees of each vertex.
		vertices = G.V();				//vertices
		indegrees = getIns(G);
		outdegrees = getOuts(G);
		order = new Queue<>();
		int c = 0;

		//Next, create queue with all sources.
		Queue<Integer> sources = new Queue<>();
		for(int i = 0; i < vertices; i++)
			if (indegrees[i] == 0)
				sources.enqueue(i);

		//While there are more sources...
		while(!sources.isEmpty()) {

			//remove a source and add it to topological order.
			int source = sources.dequeue();
			order.enqueue(source);
			c++;

			//for every vertex adjacent to the source, decrement its indegrees. add to sources if it hits 0.
			for(int i : G.adj(source)) {
				indegrees[i]--;
				if (indegrees[i] == 0)
					sources.enqueue(i);
			}

		}
		if (c != vertices) {
			order = null;
		}
	}

	public boolean hasOrder() {
		return order != null;
	}

	public Iterable<Integer> order() {
		return order;
	}

	public int[] getIns(Digraph G) {
		int[] n = new int[vertices];	//Initialize empty array
		for (int i = 0; i < vertices; i++)
			n[i] = G.indegree(i);	//Loop through vertices, storing their indegrees.
		return n;
	}

	public int[] getOuts(Digraph G) {
		int[] n = new int[vertices];	//Initialize empty array
		for (int i = 0; i < vertices; i++)
			n[i] = G.outdegree(i);	//Loop through vertices, storing their outdegrees.
		return n;
	}

	/*
	 * USELESS METHOD
	 *
	 */
	// public boolean isDAG(Digraph G) {
	// 	Queue<Integer> sinks = new Queue<>();

	// 	for(int i = 0; i < vertices; i++)
	// 		if (outdegrees[i] == 0)
	// 			sinks.enqueue(i);

	// 	while(!sinks.isEmpty()) {
	// 		int sink = sinks.dequeue();

	// 		for (int i : G.adj(sink)) {
	// 			outdegrees[i]--;
	// 			if (outdegrees[i] == 0)
	// 				sinks.enqueue(i);
	// 		}
	// 	}

	// 	for(int i : outdegrees) {
	// 		if (i > 0)
	// 			return true;
	// 	}
	// 	return false;
	// }

	public static void main(String[] args) {
		In input = new In("tinyDG.txt");
		Digraph graph = new Digraph (input);
		TopologicalQ top = new TopologicalQ(graph);

		if (top.hasOrder()) {
			Queue<Integer> result = (Queue) top.order();
			for (int i = 0; i < result.size(); i++) {
				System.out.println(result.dequeue() + " ");
			}
		}
	}
}