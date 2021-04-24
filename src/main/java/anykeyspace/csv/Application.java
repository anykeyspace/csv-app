package anykeyspace.csv;

import java.util.List;
import java.util.Optional;

import static anykeyspace.csv.CsvUtils.readAll;
import static anykeyspace.csv.CsvUtils.writeAll;

public class Application {

    private static final int BETRIEBSNR_1 = 0;
    private static final int BETRIEB_1 = 1;
    private static final int TIERNR_1 = 2;
    private static final int PEDNR_1 = 3;
    private static final int LEERZEILE = 4;
    private static final int BETRIEBSNR_2 = 5;
    private static final int BETRIEB_2 = 6;
    private static final int TIERNR_2 = 7;
    private static final int PEDNR_2 = 8;


    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Input file name required");
            return;
        }

        String inputFilename = args[0];

        List<String[]> dataWithHeader = readAll(inputFilename);

        List<String[]> data = dataWithHeader.subList(1, dataWithHeader.size());

        data.forEach(firstTableLine -> {
            Optional<String[]> foundSecondTableLine = data.stream()
                    .filter(secondTableLine -> isEquals(firstTableLine, secondTableLine))
                    .findFirst();
            foundSecondTableLine.ifPresent(line -> firstTableLine[PEDNR_1] = line[PEDNR_2]);
        });

        writeAll(dataWithHeader, "result_" + inputFilename);
    }

    private static boolean isEquals(String[] line1, String[] line2) {
        return line1[BETRIEBSNR_1].equals(line2[BETRIEBSNR_1])
                && line1[BETRIEB_1].equals(line2[BETRIEB_2])
                && line1[TIERNR_1].equals(line2[TIERNR_2]);
    }

}
