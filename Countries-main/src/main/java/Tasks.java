import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Tasks {
    private static final String[] columns = new String[]{"happinessScore", "whiskerHigh", "whiskerLow", "GDP per Capita",
    "family", "health life expectation", "freedom", "generosity", "government trust", "dystopia residual"};
    private final ArrayList<Double> medianValues;

    private final DB requester = DB.getHandler();

    public Tasks() throws SQLException {
        medianValues = new ArrayList<>();
        for (var column : columns)
            medianValues.add(requester.getAverageStatForTask(column));
    }

    public void createGraphics() throws SQLException {
        var dataset = new DefaultCategoryDataset();
        var countriesStats = requester.getEconomicsAndCountries();
        for (var value : countriesStats.keySet()){
            dataset.setValue(value, countriesStats.get(value)[0], countriesStats.get(value)[1]);
            System.out.println(countriesStats.get(value)[0] + " " + value);
        }
        var chart = ChartFactory.createBarChart(
                "Economics by country",
                null,
                "Economic",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);
        chart.setBackgroundPaint(Color.CYAN);
        chart.getTitle().setPaint(Color.gray);
        var plot = chart.getCategoryPlot();
        var br = (BarRenderer) plot.getRenderer();
        br.setItemMargin(0.25);
        var domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        var frame = new JFrame("Ecomomics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var cp = new ChartPanel(chart);
        frame.add(cp);
        frame.pack();
        frame.setVisible(true);
    }

    public double getAverageHealth() throws SQLException {
        return requester.getAverageStatForTask("health life expectation");
    }

    public String getMostAverageCountry() throws SQLException {
        var deltasOfMostAverage = medianValues;
        var mostAverageCountryCount = 0;
        var currMostAverageCountry = "";
        var countriesList = requester.getAllNumericalStatsOfCountries();
        for (var country : countriesList.keySet()){
            var currCountryCount = 0;
            var countryStats = countriesList.get(country);
            for (var i = 0; i < 10; i++){
                if (Math.abs(countryStats.get(i) - medianValues.get(i)) < deltasOfMostAverage.get(i))
                    currCountryCount++;
            }
            if (currCountryCount > mostAverageCountryCount){
                deltasOfMostAverage = countryStats;
                mostAverageCountryCount = currCountryCount;
                currMostAverageCountry = country;
            }
        }
        return currMostAverageCountry;
    }

}
