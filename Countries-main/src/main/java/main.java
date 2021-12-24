import java.io.IOException;
import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws SQLException {
        var isFilled = true; //change to true to fill DB
        var task = new Tasks();
        try {
            var dbHandler = DB.getHandler();
            if(!isFilled)
                dbHandler.fillDB(Parser.getCountriesFromCSV());
            task.createGraphics();
            System.out.println(task.getAverageHealth());
            System.out.println(task.getMostAverageCountry());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}