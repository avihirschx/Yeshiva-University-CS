package btree;

public class BTree<T extends Comparable<T>, Row> {
	private Node root; // root of the tree
	private int height; // height of the tree
	private int size; // number of key-value pairs in the B-tree

	public BTree(T sentinel) {
		root = new Node(0); // Create root node with 0 children.
		root.entries[0] = new Entry(sentinel, null, null);	//Create the sentinel key
	}

	public boolean isEmpty() {
		return this.getSize() == 0;
	}

	public int getSize() {
		return this.size;
	}

	public int getHeight() {
		return this.height;
	}

	public Row get(T key) {
		return this.get(this.root, key, this.height);
	}

	private Row get(Node currentNode, T key, int height) {
		Entry[] children = currentNode.entries;

		// external node
		if (height == 0) {
			for (int j = 0; j < currentNode.size; j++) {
				if (eq(key, children[j].key)) {
					return (Row) children[j].value;
				}
			}
		}

		// internal node
		else {
			for (int j = 0; j < currentNode.size; j++) {
				if (j + 1 == currentNode.size || less(key, children[j + 1].key)) {
					return get(children[j].child, key, height - 1);
				}
			}
		}
		return null;
	}

	public void put(T key, Row value) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null.");
		}
		Node newNode = put(root, key, value, height);
		size++;

		if (newNode == null) {
			return;
		}

		// need to split root
		Node parent = new Node(2);
		parent.entries[0] = new Entry(root.entries[0].key, null, root);
		parent.entries[1] = new Entry(newNode.entries[0].key, null, newNode);
		root = parent;
		height++;
	}

	private Node put(Node currentNode, T key, Row value, int height) {

		int j; // Index where we're going to put the new entry.
		Entry newEntry = new Entry(key, value, null); // a new entry with the
														// key and value

		// external node
		if (height == 0) {
			// Find the index to insert the new entry.
			for (j = 0; j < currentNode.size; j++) {
				// Ascend through currentNode's entries until
				// our key is less than a key in currentNode's entries.
				if (less(key, currentNode.entries[j].key)) {
					break;
				}
			}
		}

		// internal node
		else {

			// Find the index to insert the new entry.
			for (j = 0; j < currentNode.size; j++) {

				// If we're at the end of the array of entries or the key we're
				// looking for
				// is less than the next key in currentNode's entries...
				if ((j + 1 == currentNode.size) || less(key, currentNode.entries[j + 1].key)) {

					// recursively call put on currentNode's entry's child
					Node node = put(currentNode.entries[j].child, key, value, height - 1);
					// increment j
					j++;

					// if null, no split, return null.
					if (node == null) {
						return null;
					}

					// if it returned a node, add a new entry to this node.
					newEntry.key = node.entries[0].key;
					newEntry.child = node;
					break;
				}
			}
		}

		// Move backward through the entries, shifting each
		// to the right one place to make way for newEntry.
		for (int i = currentNode.size; i > j; i--) {
			currentNode.entries[i] = currentNode.entries[i - 1];
		}

		// Add the entry at index j and increase currentNode's size.
		currentNode.entries[j] = newEntry;
		currentNode.size++;

		// if currentNode is empty enough, we're good.
		if (currentNode.size < 4) {
			return null;
		}
		// otherwise, split it.
		else {
			return split(currentNode);
		}
	}

	// split a node
	private Node split(Node currentNode) {
		// Create new node with 2 entries.
		Node newNode = new Node(2);
		// Effectively remove last 2 entries of currentNode.
		currentNode.size = 2;
		for (int i = 0; i < 2; i++) {
			// fill newNode with the last 2 entries of currentNode.
			newNode.entries[i] = currentNode.entries[2 + i];
		}
		return newNode;
	}

	// comparison functions - make Comparable instead of Key to avoid casts
	private boolean less(Comparable k1, Comparable k2) {
		return k1.compareTo(k2) < 0;
	}

	private boolean eq(Comparable k1, Comparable k2) {
		return k1.compareTo(k2) == 0;
	}
}