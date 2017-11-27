package edu.yc.oats.algs;

import java.util.ArrayList;

public class BinarySearchST<Key extends Comparable<Key>, Value> 
{
	private Key[] keys;
	private Value[] vals;
	private int N;


	public BinarySearchST() {
		this(50);	//Initialize empty bsst with capacity of 50.
	}

	public BinarySearchST(int capacity) {
		keys = (Key[]) new Comparable[capacity];
		vals = (Value[]) new Object[capacity];
	}

	/** Initializes the ST with initial keys and corresponding values. The keys
	 * and values are inserted in array order: i.e., first (initialKeys[0],
	 * initialValues[0], then (initialKeys[1], initialValues[1])
	 * .... (initialKeys[N-1], initialValues[N-1])
	 */
	public BinarySearchST(Key[] initialKeys, Value[] initialValues) {
		if (initialKeys.length != initialValues.length)
			throw new IllegalArgumentException("Number of keys and values must be the same.");

		keys = (Key[]) new Comparable[initialKeys.length];
		vals = (Value[]) new Object[initialValues.length];

		int i = 0;

		for(Key key : initialKeys) {
			this.put(key, initialValues[i]);
			i++;
		}
	}

	public int size() {
		return N;
	}
	public boolean isEmpty () {
		return size() == 0;
	}

	public Value get(Key key) {
		if (key == null)
			throw new IllegalArgumentException("Key is null.");
		if (isEmpty())
			return null;
		int i = rank(key);
		if (i < N && keys[i].compareTo(key) == 0)
			return vals[i];
		else
			return null;
	}

	public int rank(Key key) {
		int lo = 0, hi = N-1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			int cmp = key.compareTo(keys[mid]);
			if (cmp < 0)
				hi = mid - 1;
			else if (cmp > 0)
				lo = mid + 1;
			else
				return mid;
		}
		return lo;
	}

	public void put(Key key, Value val) { // Search for key. Update value if found; grow table if new.
		if (key == null || val == null)
			throw new IllegalArgumentException ("Key or Value is null.");

		int i = rank(key);

		if (i < N && keys[i].compareTo(key) == 0) {
			vals[i] = val;
			return;
		}
		for (int j = N; j > i; j--) {
			keys[j] = keys[j-1];
			vals[j] = vals[j-1];
		}

		keys[i] = key;
		vals[i] = val;
		N++;
	}
	public void delete(Key key) {
		if (key == null)
			throw new IllegalArgumentException("Key is null.");
		if (isEmpty())
			return;

		int i = rank(key);

		if (i != N && keys[i].compareTo(key) == 0) {	//We found the right key
			for (int j = i; j < N - 1; j++)  {			//Delete implementation
				keys[j] = keys[j + 1];
				vals[j] = vals[j + 1];
			}
			N--;	//decrement size of table
		}
	}

	public Key min() {
		if (!isEmpty())
			return keys[0];
		return null;
	}

	public Key max() {
		if (!isEmpty())
			return keys[N-1];
		return null;
	}

	public Key select(int k) {
		if (k < 0 || k >= size())
			throw new IllegalArgumentException("Invalid argument.");
		return keys[k];
	}

	public boolean contains(Key key) {
		if (key == null)
			throw new IllegalArgumentException("Key is null.");
		return get(key) != null;
	}

	public Key ceiling(Key key) {
		if (key == null)
			throw new IllegalArgumentException("Key is null.");

		int i = rank(key);

		return keys[i];
	}

	public Key floor(Key key) {
		if (key == null)
			throw new IllegalArgumentException("Key is null.");

		int i = rank(key);

		if (i < N && key.compareTo(keys[i]) == 0)
			return keys[i];

		if (i == 0)
			return null;
		else
			return keys[i - 1];
	}

	public Iterable<Key> keys(Key lo, Key hi) {
		if (lo == null)
			throw new IllegalArgumentException("Low key is null.");
		if (hi == null)
			throw new IllegalArgumentException("High key is null.");

		ArrayList<Key> list = new ArrayList();
		for (int i = rank(lo); i < rank(hi); i++)
			list.add(keys[i]);
		if (contains(hi))
			list.add(keys[rank(hi)]);
		return list;
	}

	public Iterable<Key> keys() {
		return keys(min(), max());
	}
}