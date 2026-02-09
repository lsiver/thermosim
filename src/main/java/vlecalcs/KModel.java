package vlecalcs;

public interface KModel {
    /* Returns Ki = yi/xi for each component at (T, P, x) or (T, P, z) */
    double[] K(double T, double P, double[] zOrX);
}
