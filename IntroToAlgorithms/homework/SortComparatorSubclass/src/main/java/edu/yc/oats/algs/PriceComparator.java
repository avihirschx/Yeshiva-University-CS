package edu.yc.oats.algs;

import java.util.Comparator;

public class PriceComparator implements Comparator<Item> {

	@Override
	public int compare(Item item1, Item item2) {
		if (item1.getPrice() > item2.getPrice()) {
			return 1;
		}
		else if (item1.getPrice() < item2.getPrice()) {
			return -1;
		}
		return 0;
	}
} 