package eos;

import constants.Constants;

public final class RKPureParameters implements CubicParameters {
    private final double Tc, Pc, omega;
    private final double a0, b;

    public RKPureParameters(double Tc, double Pc, double omega) {
        this.Tc = Tc;
        this.Pc = Pc;
        this.omega = omega;

        double R = Constants.R;
        this.a0 = 0.42747 * R * R * Tc * Tc / Pc;
        this.b = 0.08664 * R * Tc / Pc;
    }

    @Override
    public double a(double T, double[] x) {
        double Tr = T / Tc;
        double alpha = Math.pow(Tr,-0.5);
        return a0 * alpha;
    }

    @Override
    public double b(double[] x) {
        return b;
    }
    
}
