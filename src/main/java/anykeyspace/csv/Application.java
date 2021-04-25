package anykeyspace.csv;

import java.util.List;
import java.util.Optional;

import static anykeyspace.csv.CsvUtils.readAll;
import static anykeyspace.csv.CsvUtils.writeAll;

public class Application {

    private static final String SOURCE_DATA_FILE = "input_for_transferring.csv";
    private static final String TARGET_DATA_FILE = "output_results.csv";
    private static final String RESULT_FILE = "result.csv";

    private static final int BETRIEB = 0;
    private static final int KUHNR = 1;
    private static final int PEDOMETERNR = 2;

    public static void main(String[] args) throws Exception {
        String sourceDataFile = SOURCE_DATA_FILE;
        String targetDataFile = TARGET_DATA_FILE;
        String resultFile = RESULT_FILE;
        char separator = ',';

        if (args.length == 4) {
            sourceDataFile = args[0];
            targetDataFile = args[1];
            resultFile = args[2];
            separator = args[3].charAt(0);
        }

        List<String[]> sourceWithHeader = readAll(sourceDataFile, separator);
        List<String[]> targetWithHeader = readAll(targetDataFile, separator);

        List<String[]> source = sourceWithHeader.subList(1, sourceWithHeader.size());
        List<String[]> target = targetWithHeader.subList(1, targetWithHeader.size());

        target.forEach(targetLine -> {
            Optional<String[]> foundSourceLine = source.stream()
                    .filter(dataLine -> isEquals(targetLine, dataLine))
                    .findFirst();
            foundSourceLine.ifPresent(sourceLine -> targetLine[PEDOMETERNR] = sourceLine[PEDOMETERNR]);
        });

        writeAll(targetWithHeader, resultFile);
    }

    private static boolean isEquals(String[] line1, String[] line2) {
        return line1[BETRIEB].equals(line2[BETRIEB])
                && line1[KUHNR].equals(line2[KUHNR]);
    }
}
