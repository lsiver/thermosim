package properties;

public final class AntoineMmHg implements VaporPressureCorrelation {
    private final double A;
    private final double B;
    private final double C;

    private AntoineMmHg(double A, double B, double C){
        this.A = A;
        this.B = B;
        this.C = C;
    }

    public static AntoineMmHg of(double A, double B, double C) {
        return new AntoineMmHg(A, B, C);
    }

    @Override
    public double Psat(double T) {
        double Tc = T - 273.15;
        double log10Psat = A - (B / (Tc + C));

        double psat_mmHg = Math.pow(10.0, log10Psat);
        return psat_mmHg * 133.322368;
    }

    @Override
    public String toString() {
        return "AntoineMmHg(A=" + A + ", B=" + B + ", C=" + C + ")";
    }
}
