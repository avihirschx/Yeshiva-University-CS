package edu.yc.oats.algs;

public class Item {
	private String description;
	private double price;

	public Item (final String description, final double price) {
		this.description = description;
		this.price = price;
	}
	public String getDescription () {
		return this.description;
	}
	public double getPrice() {
		return this.price;
	}
}