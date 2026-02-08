package eos;

public interface CubicParameters {
    /* a(T) and b */
    double a(double T, double[] x);

    double b(double[] x);

    default double da_dT(double T) {
        throw new UnsupportedOperationException("da/dT not implemented yet");
    }
}
