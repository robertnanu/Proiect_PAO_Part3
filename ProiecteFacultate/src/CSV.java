import java.io.IOException;

public interface CSV {
    int readDataFromFile(String file, int position) throws IOException;
    void writeDataInFile(String file) throws IOException;
}