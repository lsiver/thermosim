package eos;

public final class RKForm implements CubicForm {
    @Override public double eta() { return 1.0; }
    @Override public double sigma() { return 0.0; }

    @Override
    public ZCubicCoefficients zCubic(double A, double B) {
        // RK: Z^3 - Z^2 + (A - B - B^2)Z - AB = 0
        // return c2, c1, c0 for monic cubic: Z^3 + c2 * Z^2 + c1 Z + C0 = 0

        double c2 = -1.0;
        double c1 = A - B - B*B;
        double c0 = -A * B;

        return new ZCubicCoefficients(c2, c1, c0);
    }
    
}
