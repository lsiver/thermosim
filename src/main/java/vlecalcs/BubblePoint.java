package vlecalcs;

public final class BubblePoint {
    private final KModel kModel;

    public BubblePoint(KModel kModel) { this.kModel = kModel; }

    public static final class Result{
        public final double T;
        public final double[] y;
        public final double[] K;
        public final int iterations;
        public final boolean converged;

        public Result(double T, double[] y, double[] K, int iterations, boolean converged) {
            this.T = T;
            this.y = y.clone();
            this.K = K.clone();
            this.iterations = iterations;
            this.converged = converged;
        }
    }

    public Result solveAtP(double P, double[] x, double Tguess) {
        return solveAtP(P, x, Tguess, 200, 1e-10, 1e-8);
    }

    public Result solveAtP(
        double P,
        double[] x,
        double Tguess,
        int maxIter,
        double fTol,
        double tTol
    ) {
        validateComposition(x);

        Bracket br = bracketT(P, x, Tguess);

        double lo = br.lo;
        double hi = br.hi;
        double flo = F(lo, P, x);
        double fhi = F(hi, P, x);

        double mid = Double.NaN;
        double fmid = Double.NaN;

        boolean converged = false;
        int iter;

        for (iter = 0; iter < maxIter; iter++) {
            mid = 0.5 * (lo + hi);
            fmid = F(mid, P, x);

            if (Math.abs(fmid) < fTol || (hi - lo) < tTol) {
                converged = true;
                break;
            }

            if (fmid > 0.0) {
                hi = mid;
                fhi = fmid;
            } else {
                lo = mid;
                flo = fmid;
            }
        }

        double T = converged ? mid : 0.5 * (lo + hi);
        double[] K = kModel.K(T, P, x);
        double[] y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = x[i] * K[i];
        }

        normalize(y);
        return new Result(T, y, K, iter + 1, converged);
    }

    //Helper Functions //
    
    private static final class Bracket {
        final double lo, hi;
        Bracket(double lo, double hi) { this.lo = lo; this.hi = hi; }
    }

    private Bracket bracketT(double P, double[] x, double Tguess) {
        double lo = Math.max(1.0, Tguess);
        double hi = lo;

        double flo = F(lo, P, x);
        double fhi = flo;

        int expandMax = 80;

        if (flo > 0.0) {
            for (int k = 0; k < expandMax; k++ ) {
                hi = lo;
                fhi = flo;

                lo = Math.max(1.0, lo * 0.90 );
                flo = F(lo, P, x);

                if (flo < 0.0) {
                    return new Bracket(lo, hi);
                }
            }
        } else {
            for (int k = 0; k < expandMax; k ++) {
                lo = hi;
                flo = fhi;

                hi = hi * 1.10;
                fhi = F(hi, P, x);

                if (fhi > 0.0 ) {
                    return new Bracket(lo, hi);
                }
            }
        }

        throw new IllegalStateException(
            "Failed to bracket bubble-point temperature. " +
            "Try a different Tguess or check KModel validity/range"
        );
    }

    private double F(double T, double P, double[] x) {
        double[] K = kModel.K(T, P, x);
        double sum = 0.0;
        for (int i = 0; i< x.length; i++) {
            double Ki = K[i];
            if (!(Ki > 0.0) || !Double.isFinite(Ki)) {
                throw new IllegalArgumentException("Invalid K[" + i + "]=" + Ki + " at T=" + T);
            }

            sum += x[i] * Ki;
        }
        return sum - 1.0;

    }

    private static void validateComposition(double[] x) {
        double sum = 0.0;
        for (double xi : x) {
            if (xi < -1e-14) throw new IllegalArgumentException("Negative composition: " + xi);
            sum += xi;
        }
        if (sum <= 0.0) throw new IllegalArgumentException("Composition sum <= 0");
    }

    private static void normalize(double[] a) {
        double sum = 0.0;
        for (int i = 0; i< a.length; i++) {
            if (a[i] < 0 && a[i] > -1e-12) a[i] = 0.0;
            sum += a[i];
        }
        if (sum <= 0.0) throw new IllegalArgumentException("Cannot normalize: sum <= 0");
        for (int i = 0; i < a.length; i++) a[i] /= sum;
    }

}
