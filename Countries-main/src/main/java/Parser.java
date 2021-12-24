import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parser {
    public static ArrayList<Country> getCountriesFromCSV() throws IOException {
        var countries = new ArrayList<Country>();
        var head = true;
        List<String[]> lines = null;
        try (var r = new CSVReader(new FileReader("Показатель счастья по странам 2017 - Показатель счастья по странам 2017.csv"))){
            lines = r.readAll();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        for (var line : lines)
        {
            if (head){
                head = false;
                continue;
            }
            countries.add(new Country(line[0], line[1], Integer.parseInt(line[2]),
                    Double.parseDouble(line[3]),  Double.parseDouble(line[4]),  Double.parseDouble(line[5]),
                    Double.parseDouble(line[6]),  Double.parseDouble(line[7]),  Double.parseDouble(line[8]),
                    Double.parseDouble(line[9]),  Double.parseDouble(line[10]),  Double.parseDouble(line[11]),
                    Double.parseDouble(line[12])));
        }
        return countries;
    }
}
