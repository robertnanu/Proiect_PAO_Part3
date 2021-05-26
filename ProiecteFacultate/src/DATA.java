import java.sql.SQLException;

public interface DATA {
    void readFromDatabase(String name_table) throws SQLException;
    void writeToDatabse(String name_table) throws SQLException;
}
