import java.util.ArrayList;

import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnDescription;

public class Column <T extends Comparable <T>>{

	ColumnDescription description;
	ArrayList<T> data;
	Index<T> index;
	
	public Column (ColumnDescription description, boolean isPrimary) {
		this.description = description;
		this.data = new ArrayList<T>();
		this.index = null;
		
	}
	
	public void setIndex(Index<T> index) {
		this.index = index;
	}
	
	public ArrayList<T> getData() {
		return this.data;
	}
	
	public Object getData(int location) {
		return data.get(location);
	}
	
	public void add(T object) {
		this.data.add(object);
	}
	
	public String getName() {
		return this.description.getColumnName();
	}
	
	public Index<?> getIndex() {
		return this.index;
	}
	
	public boolean hasIndex() {
		return this.index != null;
	}
	
	public ColumnDescription getDescription() {
		return this.description;
	}
	
}