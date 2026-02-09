package vlecalcs;

import properties.VaporPressureCorrelation;

public final class RaoultsLawKModel implements KModel {
    private final VaporPressureCorrelation[] psat;

    public RaoultsLawKModel(VaporPressureCorrelation[] psat) {
        this.psat = psat;
    }

    @Override
    public double[] K(double T, double P, double[] zOrX) {
        double[] K = new double[psat.length];
        for (int i = 0; i < K.length; i++) {
            K[i] = psat[i].Psat(T) / P;
        }
        return K;
    }
}
