public class Country {
    public String name;
    public String region;
    public int happinessRank;
    public double happinessScore;
    public double whiskerHigh;
    public double whiskerLow;
    public double gdpPerCapita;
    public double family;
    public double healthLifeExpectation;
    public double freedom;
    public double generosity;
    public double governmentTrust;
    public double dystopiaResidual;

    public Country(String name, String region, int happinessRank, double happinessScore,
                   double whiskerHigh, double whiskerLow, double gdpPerCapita,
                   double family, double healthLifeExpectation, double freedom, double generosity,
                   double governmentTrust, double dystopiaResidual){
        this.name = name;
        this.region = region;
        this.happinessRank = happinessRank;
        this.happinessScore = happinessScore;
        this.whiskerHigh = whiskerHigh;
        this.whiskerLow = whiskerLow;
        this.gdpPerCapita = gdpPerCapita;
        this.family = family;
        this.healthLifeExpectation = healthLifeExpectation;
        this.freedom = freedom;
        this.generosity = generosity;
        this.governmentTrust = governmentTrust;
        this.dystopiaResidual = dystopiaResidual;
    }
}
