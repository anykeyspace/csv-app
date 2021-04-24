package anykeyspace.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class CsvUtils {

    private CsvUtils() {
    }

    public static List<String[]> readAll(String filename) throws IOException {
        Path path = Paths.get(filename);
        Reader reader = Files.newBufferedReader(path);
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list = csvReader.readAll();
        reader.close();
        csvReader.close();
        return list;
    }

    public static void writeAll(List<String[]> stringArray, String resultFilename) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(resultFilename, false));
        writer.writeAll(stringArray);
        writer.close();
    }
}
