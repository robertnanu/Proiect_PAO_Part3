import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Supplier;

public class CreateCar extends Headquarters {
    private static int NEW_ID = 0;
    // Am adaugat toate modelele posibile intr-un map
    private static HashMap<String, Supplier<Car>> existingCars = new HashMap<>() {{
        put("audi", Audi::new);
        put("bmw", Bmw::new);
        put("jaguar", Jaguar::new);
        put("jeep", Jeep::new);
        put("mercedes", Mercedes::new);
        put("peugeot", Peugeot::new);
        put("volkswagen", Volkswagen::new);
        put("volvo", Volvo::new);
    }};

    public static Car newCar() {

        Car x;

        Scanner f = new Scanner(System.in);

        System.out.println("Car model: ");
        while(true) {
            String model = f.nextLine();
            if(existingCars.containsKey(model)) {
                x = existingCars.get(model).get();
                break;
            }

            System.out.print("The only existing car models are: ");
            existingCars.forEach((type, value) -> System.out.print(type + "; "));
            System.out.println("\n");
        }
        addToHistory(NEW_ID);
        x.setId(NEW_ID++);

        //NEW_ID++;

        System.out.println("Car with id: " + x.getId() + " was successfully created!");

        return x;
    }

    public List<Car> readCarsFromDatabase(String name_table) throws SQLException {
        ArrayList<Car> x = new ArrayList<>();
        DB db = DB.getInstance();
        ResultSet res = db.query("SELECT carId, model FROM " + name_table);
        while(res.next()) {
            String model = res.getString("model");
            Car car = existingCars.get(model.toLowerCase()).get();
            car.setId(res.getInt("carId"));
            car.readFromDatabase(name_table);
            x.add(car);
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
