package edu.yc.oats.algs;

import java.util.Comparator;

public class ColorComparator implements Comparator<Item> {

	@Override
	public int compare(Item item1, Item item2) {
		//Equal unless 1) they're both ColoredItems and 2) their Colors are different
		if (item1 instanceof ColoredItem && item2 instanceof ColoredItem) {
			if (((ColoredItem)item1).getColor().compareTo(((ColoredItem)item2).getColor()) > 0) {
				return 1;
			}
			else if (((ColoredItem)item1).getColor().compareTo(((ColoredItem)item2).getColor()) < 0) {
				return -1;
			}
		}
		return 0;
	}
}