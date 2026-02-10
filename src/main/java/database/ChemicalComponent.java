package database;

public class ChemicalComponent {
    private final String name;
    private final String casNumber;
    private final double molarMass;
    private final double criticalTemperature;
    private final double criticalPressure;
    private final double acentricFactor;

    public ChemicalComponent(String name, double molarMass, double criticalTemperature, double criticalPressure, double acentricFactor) {
        this.name = name;
        this.casNumber = "empty";
        this.molarMass = molarMass;
        this.criticalTemperature = criticalTemperature;
        this.criticalPressure = criticalPressure;
        this.acentricFactor = acentricFactor;
    }
}