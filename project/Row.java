import java.util.ArrayList;

public class Row {
	
	Object[] data;
	Column[] columns;
	ArrayList<Index> indices;
	
	public Row(Object[] data, Column[] columns, ArrayList<Index> indices) {
		this.data = data;
		this.columns = columns;
		this.indices = indices;
	}
	
	public Row(Object[] row, Column[] columns) {
		this.data = row;
		this.columns = columns;
	}
	
	public Object getKey(Index index) {
		int i = 0;
		for(Column column : columns) {
			if (column.hasIndex()){
				if(column.getIndex().equals(index)) {
					return this.data[i];
				}
			}
			i++;
		}
		//Failed to find index.
		return null;
	}
	
	public Object[] getData() {
		return this.data;
	}
	
	public ArrayList<Index> getIndices() {
		return this.indices;
	}
}