import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import btree.BTree;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnDescription;

public class Table {
	
	String name;
	Column primaryColumn;
	Column[] columns;
	ArrayList<Index> indices;
	ArrayList<Row> rows;
	
	public Table(String name, Column primary, Column[] columns) {
		this.name = name;
		this.primaryColumn = primary;
		this.columns = columns;
		this.rows = new ArrayList<Row>();
		this.indices = new ArrayList<Index>();
		if(primary.getDescription() != null) {
			Index primaryIndex = new Index("primary", primary, this);
			this.indices.add(primaryIndex);
		}
	}
	
	public Table() {
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Index> getIndices() {
		return this.indices;
	}
	
	public Column[] getColumns() {
		return this.columns;
	}
	
	public Row getRow(int index) {
		return this.rows.get(index);
	}
	
	public ArrayList<Row> getRows() {
		return this.rows;
	}
	
	public void addIndex(Index index) {
		this.indices.add(index);
	}
	
	public void addRow(Row row) {
		this.rows.add(row);
		
		int i = 0;
		for (Column column : columns) {
			column.add((Comparable)row.getData()[i]);
			i++;
		}
		if (!this.indices.isEmpty()) {
			for(Index index : this.indices) {
				if (row.getIndices().contains(index)) {
					index.addItem((Comparable) row.getKey(index), row);
				}
			}
		}
	}
	
	public void deleteRow(Row row) {
		this.rows.remove(row);
	}
	
//	public void print() {
//		System.out.println(name);
//		
//	}
}
