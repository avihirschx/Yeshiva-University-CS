import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.*;
import net.sf.jsqlparser.JSQLParserException;

public class Database {
	
	private Set<Table> tables;
	
	public Database() {
		tables = new HashSet<Table>();
	}

	public ResultSet execute(String SQL) throws JSQLParserException
	{
		return this.parseSQL(SQL);
	}

	private ResultSet parseSQL(String sqlString) throws JSQLParserException {
		SQLParser parser = new SQLParser();
		SQLQuery query = parser.parse(sqlString);
		ResultSet result = new ResultSet();
		if (query instanceof CreateTableQuery) {
			result = this.createTable((CreateTableQuery) query);
		} else if (query instanceof CreateIndexQuery) {
			result = this.createIndex((CreateIndexQuery) query);
//		} else if (query instanceof SelectQuery) {
//			result = this.select((SelectQuery) query);
		} else if (query instanceof InsertQuery) {
			result = this.insert((InsertQuery) query);
//		} else if (query instanceof UpdateQuery) {
//			result = this.update((UpdateQuery) query);
//		} else if (query instanceof DeleteQuery) {
//			result = this.delete((DeleteQuery) query);
		}
		
		return result;
	}
	
	private ResultSet createTable(CreateTableQuery query) {
		String name = query.getTableName();
		Column primary = new Column(query.getPrimaryKeyColumn(), true);
		ColumnDescription[] descriptions = query.getColumnDescriptions();
		
		Column[] columns = new Column[descriptions.length];
		
		int i = 0;
		for(ColumnDescription description : descriptions) {
			columns[i] = new Column(description, false);
			i++;
		}
		
		Table table = new Table(name, primary, columns);
		
		//make sure no table with the same name exists in tables.
		for(Table t : tables) {
			if (t.getName().equals(table.getName())) {
				//throw an exception!
				break;
			}
		}
		
		this.tables.add(table);
		
		ResultSet result = new ResultSet(table);
		return result;
	}
	
	private ResultSet createIndex(CreateIndexQuery query) {
		String tableName = query.getTableName();
		String colName = query.getColumnName();
		String indName = query.getIndexName();
		
		//Create blank table.
		Table table = null;
		
		
		//Find the table from the tables.
		for(Table t : tables) {
			if (t.getName().equals(tableName)) {
				table = t;
				break;
			}
		}
		
		ResultSet result = new ResultSet();
		result.trueResult();	//Assume it is found.
		
		//If the table was found, and the column exists
		//in this table, creates an index for that column.
		if (table != null) {
			for(Column column : table.getColumns()) {
				if(column.getName().equals(colName)) {
					Index index = new Index(indName, column, table);
					table.indices.add(index);
					return result;
				}
			}
		}
		//If the column doesn't exist or the table wasn't found -
		result.falseResult();
		return result;
		
	}

//	private ResultSet delete(DeleteQuery query) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	private ResultSet update(UpdateQuery query) {
	
//	}
//
	private ResultSet insert(InsertQuery query) {
		String tableName = query.getTableName();
		ColumnValuePair[] colValPairs = query.getColumnValuePairs();
		
		//Create blank table.
		Table table = null;
		
		//Find the table from the tables.
		for(Table t : tables) {
			if (t.getName().equals(tableName)) {
				table = t;
				break;
			}
		}
		
		ResultSet result = new ResultSet(table);
		result.trueResult();
		
		//If the table was found
		if(table != null) {
			int i = 0;
			Object[] data = new Object[table.getColumns().length];
			
			for (ColumnValuePair colValPair : colValPairs) {
				String columnName = colValPair.getColumnID().getColumnName();
				String value = colValPair.getValue();
				for(Column currentColumn : table.getColumns()) {
					String currentColumnName = currentColumn.getDescription().getColumnName();
					if (currentColumnName.equals(columnName)) {
						data[i] = value;
					}
				}
				i++;
			}
			Row row = new Row(data, table.getColumns(), table.getIndices());
			table.addRow(row);
			return result;
		}
		result.falseResult();
		return result;
		
	}
//
//	private ResultSet select(SelectQuery query) {
//		ResultSet result = new ResultSet();
//		result.trueResult();
//		
//		
//	}
}
