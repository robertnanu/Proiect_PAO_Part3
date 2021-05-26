import java.io.IOException;
import java.io.RandomAccessFile;

public class Read {
    private static Read instance = null;

    private Read() {
    }

    public static Read getInstance() {
        if(instance == null)
            instance = new Read();
        return instance;
    }

    public String[] readLine(String file, int position) throws IOException {
        RandomAccessFile f = new RandomAccessFile(file, "r");
        f.seek(position);

        String l = f.readLine();

        if(l == null)
            return null;
        
        return l.split(",");
    }
}