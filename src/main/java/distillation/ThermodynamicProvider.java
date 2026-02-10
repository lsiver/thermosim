package distillation;

public interface ThermodynamicProvider {
    double calculateKvalue(int componentIndex, double T, double P, double[] x, double[] y);
    double calculateLiquidEnthalpy(double T, double P, double[] x);
    double calculateVaporEnthalpy(double T, double P, double[] y);


    /*
    public double CalculateKvalue() {
    double[] allLiquidPhis = eos.calculateAllFugacityCoefficients(T,P,x,Phase...)
    double[] allVaporPhis = eos.calculateAllFugacityCoefficients(T,P,X,phase...)
    return allLiquidPhis[i] / allVaporPhis[i]
    }
    */
}