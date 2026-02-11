package vlecalcs;

public final class DewPoint {

    public static final class Result {
        public final double T;     
        public final double[] x;   
        public final double[] K;    
        public final int iterations;
        public final boolean converged;

        public Result(double T, double[] x, double[] K, int iterations, boolean converged) {
            this.T = T;
            this.x = x.clone();
            this.K = K.clone();
            this.iterations = iterations;
            this.converged = converged;
        }
    }

    private final KModel kModel;

    public DewPoint(KModel kModel) {
        this.kModel = kModel;
    }

    public Result solveAtP(double P, double[] y, double Tguess) {
        return solveAtP(P, y, Tguess, 200, 1e-10, 1e-8);
    }

    public Result solveAtP(
            double P,
            double[] y,
            double Tguess,
            int maxIter,
            double gTol,
            double tTol
    ) {
        validateComposition(y);

        Bracket br = bracketT(P, y, Tguess);

        double lo = br.lo;
        double hi = br.hi;

        double glo = G(lo, P, y);
        double ghi = G(hi, P, y);

        double mid = Double.NaN;
        double gmid = Double.NaN;

        boolean converged = false;
        int iter;
        for (iter = 0; iter < maxIter; iter++) {
            mid = 0.5 * (lo + hi);
            gmid = G(mid, P, y);

            if (Math.abs(gmid) < gTol || (hi - lo) < tTol) {
                converged = true;
                break;
            }

            if (glo * gmid <= 0.0) {
                hi = mid;
                ghi = gmid;
            } else {
                lo = mid;
                glo = gmid;
            }
        }

        double T = converged ? mid : 0.5 * (lo + hi);
        double[] K = kModel.K(T, P, y);

        double[] x = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            x[i] = y[i] / K[i];
        }
        normalizeInPlace(x);

        return new Result(T, x, K, iter + 1, converged);
    }


    private static final class Bracket {
        final double lo, hi;
        Bracket(double lo, double hi) { this.lo = lo; this.hi = hi; }
    }



private Bracket bracketT(double P, double[] y, double Tguess) {
    double T0 = Math.max(1.0, Tguess);
    double g0 = G(T0, P, y);

    int expandMax = 80;

    if (g0 > 0.0) {
        double lo = T0;
        double glo = g0;
        double hi = T0;

        for (int k = 0; k < expandMax; k++) {
            hi *= 1.10;
            double ghi = G(hi, P, y);
            if (ghi < 0.0) {
                return new Bracket(lo, hi);
            }
            lo = hi;
            glo = ghi;
        }
    } else {
        double hi = T0;
        double ghi = g0;
        double lo = T0;

        for (int k = 0; k < expandMax; k++) {
            lo = Math.max(1.0, lo * 0.90);
            double glo = G(lo, P, y);
            if (glo > 0.0) {
                return new Bracket(lo, hi);
            }
            hi = lo;
            ghi = glo;
        }
    }

    throw new IllegalStateException(
            "Failed to bracket dew-point temperature. " +
            "Try a different Tguess or check KModel validity/range."
    );
}


    /*
    G(T) = sum_i y_i / K_i(T,P,y) - 1 = 0
     */
    private double G(double T, double P, double[] y) {
        double[] K = kModel.K(T, P, y);
        double sum = 0.0;

        for (int i = 0; i < y.length; i++) {
            double Ki = K[i];
            if (!(Ki > 0.0) || !Double.isFinite(Ki)) {
                throw new IllegalArgumentException("Invalid K[" + i + "]=" + Ki + " at T=" + T);
            }
            sum += y[i] / Ki;
        }
        return sum - 1.0;
    }

    private static void validateComposition(double[] y) {
        double sum = 0.0;
        for (double yi : y) {
            if (yi < -1e-14) throw new IllegalArgumentException("Negative composition: " + yi);
            sum += yi;
        }
        if (sum <= 0.0) throw new IllegalArgumentException("Composition sum <= 0");
    }

    private static void normalizeInPlace(double[] a) {
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < 0 && a[i] > -1e-12) a[i] = 0.0;
            sum += a[i];
        }
        if (sum <= 0.0) throw new IllegalArgumentException("Cannot normalize: sum <= 0");
        for (int i = 0; i < a.length; i++) a[i] /= sum;
    }
}
