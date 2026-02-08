package eos;

public final class SRKPureParameters implements CubicParameters {
    private final double Tc, Pc, omega;
    private final double a0, b;

    public SRKPureParameters(double Tc, double Pc, double omega) {
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
        double m = 0.480 + 1.574 * omega - 0.176 * omega * omega;
        double alpha = Math.pow(1.0 + m * (1.0 - Math.sqrt(Tr)), 2.0);
        return a0 * alpha;
    }

    @Override
    public double b(double[] x) {
        return b;
    }
    
}
