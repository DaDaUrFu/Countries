import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class DB {

    private static final String con = "jdbc:sqlite:Countries.sqlite";
    private static DB handler = null;

    public static DB getHandler() throws SQLException {
        if (handler == null)
            handler = new DB();
        return handler;
    }

    private Connection connection;

    private DB() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(con);
    }

    public void addCountry(Country country) throws SQLException {
        var countryStatement = this.connection.prepareStatement("insert into " +
                                                                "Countries(name, happinessRank, happinessScore, whiskerHigh, whiskerLow, " +
                                                                "'GDP per Capita', family, 'health life expectation', freedom, generosity, 'government trust', 'dystopia residual') " +
                                                                "values (?,?,?,?,?,?,?,?,?,?,?,?)");
        var regionStatement = this.connection.prepareStatement("insert into Regions('country name', region)" +
                                                               "values (?,?)");

        countryStatement.setObject(1, country.name);
        countryStatement.setObject(2, country.happinessRank);
        countryStatement.setObject(3, country.happinessScore);
        countryStatement.setObject(4, country.whiskerHigh);
        countryStatement.setObject(5, country.whiskerLow);
        countryStatement.setObject(6, country.gdpPerCapita);
        countryStatement.setObject(7, country.family);
        countryStatement.setObject(8, country.healthLifeExpectation);
        countryStatement.setObject(9, country.freedom);
        countryStatement.setObject(10, country.generosity);
        countryStatement.setObject(11, country.governmentTrust);
        countryStatement.setObject(12, country.dystopiaResidual);
        countryStatement.execute();

        regionStatement.setObject(1, country.name);
        regionStatement.setObject(2, country.region);
        regionStatement.execute();
    }

    public void fillDB(List<Country> countries) throws SQLException {
        for (var country : countries)
            addCountry(country);
    }

    public double getAverageStatForTask(String stat) throws SQLException {
        var sqlQuery = """
                select average_value
                from Averages
                where column_name = ?""";
        var stmt = connection.prepareStatement(sqlQuery);
        stmt.setString(1, stat);
        var result = stmt.executeQuery();
        return result.getDouble(1);
    }

    public HashMap<String,ArrayList<Double>> getAllNumericalStatsOfCountries() throws SQLException {
        var sqlQuery = """
        select name, happinessScore, whiskerHigh, whiskerLow, "GDP per Capita", family, "health life expectation",\040
        freedom, generosity, "government trust", "dystopia residual"\040
        from Countries, Regions\040
        where Countries.name = Regions."Country name" and (Regions.Region = 'Western Europe' or Regions.Region = 'Sub-Saharan Africa')""";
        var stmt = connection.createStatement();
        var res = stmt.executeQuery(sqlQuery);
        var map = new HashMap<String, ArrayList<Double>>();
        while (res.next()) {
            var resList = new ArrayList<Double>();
            resList.add(res.getDouble(2));
            resList.add(res.getDouble(3));
            resList.add(res.getDouble(4));
            resList.add(res.getDouble(5));
            resList.add(res.getDouble(6));
            resList.add(res.getDouble(7));
            resList.add(res.getDouble(8));
            resList.add(res.getDouble(9));
            resList.add(res.getDouble(10));
            resList.add(res.getDouble(11));
            map.put(res.getString(1), resList);
        }
        return map;
    }

    public TreeMap<Double, String[]> getEconomicsAndCountries() throws SQLException {
        var sqlQuery = """
                select name, Region, "GDP per Capita"
                from Countries, Regions
                where name = "Country name"
                order by "GDP per Capita" desc""";
        var stmt = connection.createStatement();
        var res = stmt.executeQuery(sqlQuery);
        var map = new TreeMap<Double, String[]>();
        while (res.next())
            map.put(res.getDouble(3),
                    new String[]{res.getString(1), res.getString(2)});
        return map;
    }
}