import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Service {
    public static final String CAR_TABLE = "paodb.cars";
    public static final String CREATE_CAR_TABLE = "paodb.createcar";
    public static final String DEALERSHIPS_TABLE = "paodb.dealerships";
    public static final String CREATE_DEALERSHIPS_TABLE = "paodb.createdealerships";
    public static final String AUDIT_FILE = "audit.csv";

    private final CreateDealerShip createDealerShip = new CreateDealerShip();
    private final CreateCar createCar = new CreateCar();
    private final Set<DealerShip> dealerships = new TreeSet<>();

    // 1. La primul pas dorim sa cream un dealership nou
    public void newDealerShip() throws IOException{
        CreateDealerShip x = new CreateDealerShip();
        DealerShip y = CreateDealerShip.newDealerShip();
        dealerships.add(y);
        log("createdealership");
    }

    // 2. Vrem sa adaugam masini in reprezentante
    public void addCarInDS(int dealershipId) throws Eroare, IOException {
        DealerShip x = getDSById(dealershipId);
        CreateCar car = new CreateCar();
        Car y = CreateCar.newCar();

        x.addCar(y);
        log("addcar");
    }

    // 3. Vrem sa afisam toate masinile dintr-o anumita reprezentanta
    public void showCars(int dealershipId) throws Eroare, IOException {
        System.out.println(getDSById(dealershipId).getCarDetails());
        log("showcars");
    }

    // 4. Vrem sa afisam masinile dintr-o reprezentanta in ordine descrescatoare a anilor
    public void sortCars(int dealershipId) throws Eroare {
        getDSById(dealershipId).sortCarsYear(); // Mai intai o sortez
        getDSById(dealershipId).reverseList(); // Apoi o inversez
    }

    // 5. Ma intereseaza motorizarea cea mai frecvent dorita
    public String bestFuelType() {
        Map<String, Integer> popular = new HashMap<>();
        Integer Max = -1;
        String motorizare = "No se puede mi amigo";

        // intru in dealership-uri pe rand
        for(DealerShip x: dealerships) {
            // pentru fiecare masina din dealership verif cea mai frecventa motorizare
            for(Car y: x.getCars()) {
                String fuelType = y.getFuelType();
                if(popular.containsKey(fuelType)) {
                    popular.put(fuelType, popular.get(fuelType) + 1);
                }
                else {
                    popular.put(fuelType, 1);
                }

                if(popular.get(fuelType) > Max) {
                    Max = popular.get(fuelType);
                    motorizare = fuelType;
                }
            }
        }
        return motorizare;
    }

    // 6. Vreau sa afisez toate masinile in functie de motorizare, intrucat am vazut inainte care este cea mai populara motorizare
    public int showLikes(String fuelType) throws Eroare {
        Map<String, Integer> exists = new HashMap<>();

        for(DealerShip x: dealerships) {
            for(Car y: x.getCars()) {
                fuelType = y.getFuelType();
                if(exists.containsKey(fuelType)) {
                    exists.put(fuelType, exists.get(fuelType) + 1);
                }
                else {
                    exists.put(fuelType, 1);
                }
                if(exists.get(fuelType) >= 1) {
                    return 1;
                }
            }
        }
        return 0;
    }

    // 7. Vreau sa pot verifica daca o masina exista sau nu intr-un dealership, in functie de nume
    public boolean carExists(int dealershipId, String carName) throws Eroare {
        return getDSById(dealershipId).carExists(carName);
    }

    // 8. Vreau sa pot vinde o masina creata anterior
    public void sellCar(int dealershipId, String carName) throws Eroare {
        DealerShip x = getDSById(dealershipId);
        x.sellCar(carName);
    }

    // 9. Vreau sa am optiunea sa elimin toate masinile adaugate anterior intr-un dealership
    public void clearOutDS(int dealershipId) throws Eroare {
        getDSById(dealershipId).clearDS();
    }

    private DealerShip getDSById(int dealershipId) throws Eroare {
        for(DealerShip x: dealerships) {
            if(x.getId() == dealershipId) {
                return x;
            }
        }
        throw new Eroare("This dealership does not exist.");
    }

    public void load() throws SQLException {
        createDealerShip.readFromDatabase(CREATE_DEALERSHIPS_TABLE);
        createCar.readFromDatabase(CREATE_CAR_TABLE);

        dealerships.addAll(createDealerShip.readDealershipsFromDatabase(DEALERSHIPS_TABLE));
        List<Car> cars = createCar.readCarsFromDatabase(CAR_TABLE);

        for(Car c: cars) {
            try {
                DealerShip ds = getDSById(c.getDealershipID());
                ds.addCar(c);
            } catch (Eroare InvalidCommand) {
                throw new SQLException("Database is invalid. Please try again.");
            }
        }
    }

    public void save() throws SQLException {
        DB db = DB.getInstance();

        db.reload("DELETE FROM " + CREATE_DEALERSHIPS_TABLE);
        db.reload("DELETE FROM " + CREATE_CAR_TABLE);
        db.reload("DELETE FROM " + DEALERSHIPS_TABLE);
        db.reload("DELETE FROM " + CAR_TABLE);

        createDealerShip.writeToDatabse(CREATE_DEALERSHIPS_TABLE);
        createCar.writeToDatabse(CREATE_CAR_TABLE);

        for(DealerShip ds: dealerships) {
            ds.writeToDatabse(DEALERSHIPS_TABLE);
            for(Car c: ds.getCars()) {
                c.writeToDatabse(CAR_TABLE);
            }
        }
    }

    private void log(String action) throws IOException {
        Write nanu = Write.getInstance();
        String timestamp = String.valueOf(System.currentTimeMillis());
        nanu.write(AUDIT_FILE, action + ", " + timestamp + ",\n");
    }
}