package database;

public class ChemicalComponent {
    private final String name;
    private final String casNumber;
    private final double molarMass;
    private final double criticalTemperature;
    private final double criticalPressure;
    private final double acentricFactor;
    private final double[] cpCoefficients;

    public ChemicalComponent(String name, double molarMass, double criticalTemperature, double criticalPressure, double acentricFactor) {
        this.name = name;
        this.casNumber = "empty";
        this.molarMass = molarMass;
        this.criticalTemperature = criticalTemperature;
        this.criticalPressure = criticalPressure;
        this.acentricFactor = acentricFactor;
        this.cpCoefficients = new double[]{0.0,0.0,0.0,0.0,0.0};
    }

    public double getCriticalTemperature() { return criticalTemperature; }
    public double getCriticalPressure() { return criticalPressure; }
    public double getAcentricFactor() { return acentricFactor; }
    public double[] getcpCoefficients() { return cpCoefficients; }

}