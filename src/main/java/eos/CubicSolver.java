package eos;

public final class CubicSolver {

    private CubicSolver() {}

    public static double[] solveRealRoots(double c2, double c1, double c0) {
        double a = c1 - c2*c2/3.0;
        double b = 2.0*c2*c2*c2/27.0 - c2*c1/3.0 + c0;

        double Q = a/3.0;
        double R = b/2.0;
        double D = Q*Q*Q + R*R;

        if (D > 1e-12) {
            double sqrtD = Math.sqrt(D);
            double u = cbrt(-R + sqrtD);
            double v = cbrt(-R - sqrtD);
            double y = u + v;
            double Z = y - c2/3.0;
            return new double[]{Z};

        } else if (Math.abs(D) <= 1e-12) {
            double u =cbrt(-R);
            double y1 = 2*u;
            double y2 = -u;
            return new double[]{
                y1 - c2/3.0,
                y2-c2/3.0
            };

        } else {
            double theta = Math.acos(-R/Math.sqrt(-Q*Q*Q));
            double r = 2*Math.sqrt(-Q);

            double Z1 = r*Math.cos(theta/3.0) - c2/3.0;
            double Z2 = r*Math.cos((theta + 2*Math.PI)/3.0) - c2/3.0;
            double Z3 = r*Math.cos((theta + 4*Math.PI)/3.0) - c2/3.0;

            return new double[]{Z1, Z2, Z3};
        }
    }

    private static double cbrt(double x) {
        return x >= 0 ? Math.cbrt(x) : -Math.cbrt(-x);
    }
    
}
