import java.util.ArrayList;

import btree.BTree;

public class Index <Key extends Comparable<Key>>{

	Column<Key> column;
	String name;
	Table table;
	BTree<Key, Row> tree;
	ArrayList<Key> keys;
	
	public Index(String name, Column<Key> column, Table table) {
		this.name = name;
		this.column = column;
		this.table = table;
		this.keys = this.getKeys();
		
		//Find sentinel
		Key sentinel = null;
		
		
		tree = new BTree<Key, Row>(sentinel);
		
		if (this.keys != null && !this.table.getRows().isEmpty()) {
			int i = 0;
			for (Key key : this.keys) {
				this.tree.put(key, this.table.getRow(i));
				i++;
			}
		}
	}

	public Column<Key> getColumn() {
		return column;
	}

	public String getName() {
		return name;
	}
	
	public ArrayList<Key> getKeys() {
		return this.column.getData();
	}
	
	public void addItem(Key key, Row row) {
		this.tree.put(key, row);
	}
	
	public void removeItem(Key key, Row row) {
		this.tree.put(key, null);
	}
	
	public Row getRow(Key key) {
		return this.tree.get(key);
	}
	
}
