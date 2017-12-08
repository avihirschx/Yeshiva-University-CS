package edu.yc.oats.algs;

public class TopologicalQ {
	private Iterable<Integer> order;

	public TopologicalQ(Digraph G) {

	}

	public boolean hasOrder() {
		return order != null;
	}

	public Iterable<Integer> order() {
		return order;
	} 
}