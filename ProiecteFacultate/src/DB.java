import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    public static DB instance = null;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/paodb";
    private static final String USER = "root";
    private static final String PASSWORD = "lionking2000";
    Connection here = null;

    public static DB getInstance() throws SQLException {
        if(instance == null)
            instance = new DB();
        
            return instance;
    }

    public void reload(String query) throws SQLException {
        Statement statement = null;
        statement = here.createStatement();
        statement.executeUpdate(query);
    }
    
    private DB() throws SQLException {
        here = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public ResultSet query(String query) throws SQLException {
        Statement statement = null;
        statement = here.createStatement();
        
        return statement.executeQuery(query);
    }
}
