import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateDealerShip extends Headquarters {
    public static int NEW_ID = 0;

    public static DealerShip newDealerShip() {
        DealerShip nanu = new DealerShip();

        Scanner f = new Scanner(System.in);

        System.out.println("Please choose a name for the dealership: ");

        // Setez numele dealership-ului
        nanu.setName(f.nextLine());
        addToHistory(NEW_ID);
        nanu.setId(NEW_ID);

        // Crestem id-ul pentru urmatorul dealership pe care vrem sa il cream
        NEW_ID++;

        System.out.println("The dealership: " + nanu.getName() + " has been created succesfully with a new id of: " + nanu.getId());

        return nanu;
    }

    public List<DealerShip> readDealershipsFromDatabase(String name_table) throws SQLException {
        ArrayList<DealerShip> x = new ArrayList<>();
        DB db = DB.getInstance();
        ResultSet res = db.query("SELECT dealershipId FROM " + name_table);
        while(res.next()) {
            DealerShip dealerShip = new DealerShip();
            dealerShip.setId(res.getInt("dealershipId"));
            x.add(dealerShip);
        }

        return x;
    }

    @Override
    int getNewID() {
        return NEW_ID;
    }

    @Override
    void setNewID(int id) {
        NEW_ID =id;
    }
}
