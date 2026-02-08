package eos;

public final class ZCubicCoefficients {
    private final double c2;
    private final double c1;
    private final double c0;

    public ZCubicCoefficients(double c2, double c1, double c0) {
        this.c2 = c2;
        this.c1 = c1;
        this.c0 = c0;
    }

    public double c2() { return c2; }
    public double c1() { return c1; }
    public double c0() { return c0; }
    
}
