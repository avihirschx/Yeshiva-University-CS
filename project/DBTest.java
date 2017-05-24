import net.sf.jsqlparser.JSQLParserException;

public class DBTest {

	public static void main(String[] args) throws JSQLParserException {
		Database database = new Database();
		
		ResultSet createTable = database.execute("CREATE TABLE table1 ("
		+ " BannerID int,"
		+ " SSNum int UNIQUE,"
		+ " FirstName varchar(255),"
		+ " LastName varchar(255) NOT NULL,"
		+ " GPA decimal(1,2) DEFAULT 0.00,"
		+ " CurrentStudent boolean DEFAULT true,"
		+ " PRIMARY KEY (BannerID))");
		
		ResultSet createIndex1 = database.execute("CREATE INDEX SSNum_Index on table1 (SSNum);");
		ResultSet createIndex2 = database.execute("CREATE INDEX FirstName_Index1 on table1 (FirstName);");
		
		//Doesn't work - problem with the added row's key.
		ResultSet insertRow = database.execute("INSERT INTO table1 (FirstName, LastName, BannerID, SSNum, GPA, currentstudent) VALUES ('Shimon', 'Ferez' , 0, 1678354, 9.2, false);");
		
		System.out.println("Done");
		
	}
}
