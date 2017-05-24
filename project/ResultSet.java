import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnDescription;

public class ResultSet {
	
	private Table table;
	
	public ResultSet(Table table) {
		this.table = table;
	}
	
	public ResultSet() {
		this.table = new Table();
	}
	
	public void falseResult() {
		String[] data = new String[]{"false"};
		Column column = new Column(null, false);
		column.add("false");
		Column[] columns = new Column[]{column};
		Row row = new Row(data, columns);
		this.table = new Table("table", column, columns);
		table.addRow(row);
	}
	
	public void trueResult() {
		String[] data = new String[]{"true"};
		Column column = new Column(null, false);
		column.add("true");
		Column[] columns = new Column[]{column};
		Row row = new Row(data, columns);
		this.table = new Table("table", column, columns);
		table.addRow(row);
	}
	
	public Table getTable() {
		return this.table;
	}
	
	
}
