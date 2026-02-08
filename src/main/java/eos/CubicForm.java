package eos;

public interface CubicForm {
    /* Constants used in the denominator (V + eta b)(V + sigma b) */
    double eta();
    double sigma();

    ZCubicCoefficients zCubic(double A, double B);
}
