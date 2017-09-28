package edu.yc.oats.algs;

import java.util.Arrays;

public class SortDriver {

	static public void sort(Item[] a) {
		//Sort by color, then price, then description.
		Arrays.sort(a, new ColorComparator());
		Arrays.sort(a, new PriceComparator());
		Arrays.sort(a, new DescriptionComparator());
	}
	static public void sortByPrice(Item[] a) {
		//Sort only by price.
		Arrays.sort(a, new PriceComparator());
	}
}