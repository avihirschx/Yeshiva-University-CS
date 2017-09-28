package edu.yc.oats.algs;

public class ColoredItem extends Item {
	private Color color;
	public ColoredItem (final String description, final double price, final Color color) {
		super(description, price);
		this.color = color;
	}
	public Color getColor() {
		return this.color;
	}
} 