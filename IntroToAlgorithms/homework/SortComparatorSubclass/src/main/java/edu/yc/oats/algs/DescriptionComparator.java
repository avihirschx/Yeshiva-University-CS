package edu.yc.oats.algs;

import java.util.Comparator;

public class DescriptionComparator implements Comparator<Item> {

	@Override
	public int compare(Item item1, Item item2) {
		if (item1.getDescription().compareTo(item2.getDescription()) > 0) {
			return 1;
		} 
		else if (item1.getDescription().compareTo(item2.getDescription()) < 0) {
			return -1;
		}
		return 0;
	}
}