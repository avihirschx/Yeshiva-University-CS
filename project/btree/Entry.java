package btree;

public class Entry {
	protected Comparable key; // Key of entry.
	protected Object value; // Reference to an object
	protected Node child; // Child of this entry.

	public Entry(Comparable key, Object val, Node child) {
		this.key = key; 	// Every entry has a key
		this.value = val; 	// Entries of external nodes have values
		this.child = child;	// Entries of internal nodes have children
	}
}