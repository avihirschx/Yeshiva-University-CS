package edu.yc.oats.algs;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer>
{
	private int maxN;	//max num of elements
	private int n;		//current num of elements

	private int[] pq;	//binary heap using 1-based indexing
	private int[] qp;	//inverse (qp[pq[i]] = pq[qp[i]] = i)
	private Key[] keys;	//array of priorities of items

	IndexMinPQ(int maxN) {
		if (maxN < 0)
			throw new IllegalArgumentException();

		this.maxN = maxN;
		n = 0;

		keys = (Key[]) new Comparable[maxN + 1];
		pq = new int[maxN + 1];
		qp = new int[maxN + 1];

		for (int i = 0; i <= maxN; i++)
			qp[i] = -1;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public int size() {
		return n;
	}

	public boolean contains(int k) {
		if (k < 0 || k >= maxN)
			throw new IllegalArgumentException();

		return qp[k] != -1;
	}

	public Key keyOf(int i) {
		if (i < 0 || i >= maxN)
			throw new IllegalArgumentException();
		if (!contains(i))
			throw new IllegalArgumentException("Index is not present.");
		return keys[i];
	}

	public int minIndex() {
		if (n == 0)
			throw new NoSuchElementException();
		return pq[1];
	}
	public Key minKey() {
		if (n == 0)
			throw new NoSuchElementException();
		return keys[pq[1]];
	}

	public void insert(int i, Key key) {
		if (i < 0 || i >= maxN)
			throw new IllegalArgumentException();
		if (contains(i))
			throw new IllegalArgumentException("Index already present.");

		n++;
		qp[i] = n;
		pq[n] = i;
		keys[i] = key;
		swim(n);
	}

	public int delMin() {
		if (n == 0)
			throw new NoSuchElementException();

		int m = pq[1];
		exch(1, n--);
		sink(1);
		qp[m] = -1;
		keys[m] = null;
		return m;
	}


	//helper methods
	private boolean greater(int i, int j) {
		return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
	}
	private void exch(int i, int j) {
		int placeholder = pq[i];
		pq[i] = pq[j];
		pq[j] = placeholder;
		qp[pq[i]] = i;
		qp[pq[j]] = j;
	}
	private void swim(int k) {
		while(k > 1 && greater(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}
	private void sink(int k) {
		while (2 * k <= n) {
			int j = 2 * k;
			if (j < n && greater(j, j + 1))
				j++;
			if (!greater(k, j))
				break;
			exch(k, j);
			k = j;
		}
	}

	//iterator methods
	public Iterator<Integer> iterator() {
		return new HIterator();
	}

	private class HIterator implements Iterator<Integer>
	{
		private IndexMinPQ<Key> queue;

		public HIterator() {
			queue = new IndexMinPQ<Key>(pq.length - 1);
			for (int i = 1; i <= n; i++)
				queue.insert(pq[i], keys[pq[i]]);
		}
		public boolean hasNext() {
			return !queue.isEmpty();
		}
		public Integer next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return queue.delMin();
		}
	}

}