import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Headquarters implements DATA {
    private final static ArrayList<Integer> history = new ArrayList<>();
    abstract int getNewID();
    abstract void setNewID(int id);

    
    @Override
    public void readFromDatabase(String name_table) throws SQLException {
        DB db = DB.getInstance();
        ResultSet res = db.query("SELECT newId FROM " + name_table);
        res.next();
        setNewID(res.getInt("newId"));
    }

    @Override
    public void writeToDatabse(String name_table) throws SQLException {
        DB db = DB.getInstance();
        db.reload("INSERT INTO " + name_table + "(newId) VALUES (" + getNewID() + ")");
    }

    protected static void addToHistory(Integer id) {
        history.add(id);
    }

    protected ArrayList<Integer> getHistory() {
        return history;
    }
}
