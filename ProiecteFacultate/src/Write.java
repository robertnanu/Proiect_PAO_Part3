import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Write {
    private static Write instance = null;


    private Write() {
    }

    public static Write getInstance() {
        if (instance == null)
            instance = new Write();

        return instance;
    }

    public void write(String file, String str) throws IOException {
        FileWriter nanu = new FileWriter(file, true);
        nanu.write(str);
        nanu.close();
    }

    public Boolean isEmpty(String file) throws IOException {
        File f = new File(file);
        return f.length() == 0;
    }

    public void delete(String file) throws IOException {
        new FileWriter(file, false).close();
    }
}
