package btree;

public class Node {
	protected int size; 		// number of entries in this node
	protected Entry[] entries; 	// the array of entries

	// create a node with i children
	public Node(int i) {
		this.size = i;
		this.entries = new Entry[4];
	}
}