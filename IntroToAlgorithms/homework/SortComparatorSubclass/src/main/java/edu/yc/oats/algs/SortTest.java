package edu.yc.oats.algs;

public class SortTest {
	public static void main(String[] args) {

		ColoredItem item1 = new ColoredItem("C", 20, Color.VIOLET);
		ColoredItem item2 = new ColoredItem("C", 20, Color.RED);
		ColoredItem item3 = new ColoredItem("C", 10, Color.YELLOW);
		Item item4 = new Item("B", 30);
		ColoredItem item5 = new ColoredItem("C", 20, Color.ORANGE);
		Item item6 = new Item("A", 10);

		Item[] items = {item1, item2, item3, item4, item5, item6};

		int k = 1;
		System.out.println("\nYour item array:");
		for (Item i : items) {
			System.out.print(k + ": " + i.getDescription() + " ");
			System.out.print(i.getPrice() + " ");
			if (i instanceof ColoredItem) {
				System.out.print(((ColoredItem)i).getColor() + " ");
			}
			System.out.println();
			k++;
		}

		SortDriver.sort(items);

		k = 1;
		System.out.println("\nYour fully sorted array:");
		for (Item i : items) {
			System.out.print(k + ": " + i.getDescription() + " ");
			System.out.print(i.getPrice() + " ");
			if (i instanceof ColoredItem) {
				System.out.print(((ColoredItem)i).getColor() + " ");
			}
			System.out.println();
			k++;
		}

		SortDriver.sortByPrice(items);

		k = 1;
		System.out.println("\nYour sorted array (by price):");
		for (Item i : items) {
			System.out.print(k + ": " + i.getDescription() + " ");
			System.out.print(i.getPrice() + " ");
			if (i instanceof ColoredItem) {
				System.out.print(((ColoredItem)i).getColor() + " ");
			}
			System.out.println();
			k++;
		}
	}
}