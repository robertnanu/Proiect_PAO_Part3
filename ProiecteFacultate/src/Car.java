import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Car implements DATA {
    private int carId, dealershipID;

    abstract String getModel();
    abstract int getPrice();
    abstract String getFuelType();
    abstract int getFabricationYear();

    public int getId() {
        return carId;
    }

    public void setId(int carId) {
        this.carId = carId;
    }

    @Override
    public String toString() {
        return "Car id: " + carId + ", model: " + getModel() + " fabricated in: " + getFabricationYear() + ", is listed at: " + getPrice() + "$ and runs on: " + getFuelType();
    }

    public int getDealershipID() {
        return dealershipID;
    }

    public void setDealershipID(int dealershipID) {
        this.dealershipID = dealershipID;
    }

    @Override
    public void readFromDatabase(String name_table) throws SQLException {
        DB db = DB.getInstance();
        ResultSet res = db.query("SELECT carId FROM " + name_table);
        res.next();
        carId = res.getInt("carId");
    }

    @Override
    public void writeToDatabse(String name_table) throws SQLException {
        DB db = DB.getInstance();
        db.reload("INSERT INTO " + name_table + "(carId, model, dealershipId) VALUES " + "(" + carId + ",'" + getModel() + "'," + dealershipID + ")");
    }
}
