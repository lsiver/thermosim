package distillation;

import properties.AntoineMmHg;
import properties.VaporPressureCorrelation;
import vlecalcs.KModel;
import vlecalcs.RaoultsLawKModel;

public class RefineryPackage implements ThermodynamicProvider{

    VaporPressureCorrelation benzene = AntoineMmHg.of(6.90565, 1211.033, 220.790);
    VaporPressureCorrelation toluene = AntoineMmHg.of(6.95464, 1344.800, 219.480);

    private final KModel kmodel = new RaoultsLawKModel(new VaporPressureCorrelation[]{benzene, toluene});

    public RefineryPackage() {
    }

    @Override
    public double calculateKvalue(int componentIndex, double T, double P, double[] x, double[] y) {
        return 0.0;
    }

    @Override
    public double calculateLiquidEnthalpy(double T, double P, double[] x) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateLiquidEnthalpy'");
    }

    @Override
    public double calculateVaporEnthalpy(double T, double P, double[] y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateVaporEnthalpy'");
    }

}
