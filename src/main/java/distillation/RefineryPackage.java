package distillation;

import properties.AntoineMmHg;
import properties.VaporPressureCorrelation;
import vlecalcs.KModel;
import vlecalcs.RaoultsLawKModel;

public class RefineryPackage implements ThermodynamicProvider{



    private final KModel kmodel;

    public RefineryPackage(KModel kmodel) {
        this.kmodel = kmodel;
    }

    @Override
    public double calculateKvalue(int componentIndex, double T, double P, double[] x, double[] y) {
        return kmodel.K(T, P, x)[componentIndex];
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

    public static void main(String[] args) {
    VaporPressureCorrelation benzene = AntoineMmHg.of(6.90565, 1211.033, 220.790);
    VaporPressureCorrelation toluene = AntoineMmHg.of(6.95464, 1344.800, 219.480);
    KModel kmodel = new RaoultsLawKModel(new VaporPressureCorrelation[]{benzene, toluene});

    RefineryPackage testPackage = new RefineryPackage(kmodel);

    System.out.println(testPackage.calculateKvalue(0,300,101325,new double[]{0.0,0.1,0.5,1.0},new double[]{0.0, 0.3,0.7,1.0}));
    System.out.println(testPackage.calculateKvalue(1,300,101325,new double[]{0.0,0.1,0.5,1.0},new double[]{0.0, 0.3,0.7,1.0}));
    //double[] x and double[] y does not matter for Raoults law but it is part of the interface contract.
    // These WILL matter for complicated methods (PR fugacity)




    }

}
