package vlecalcs;

public final class RachfordRice {
    private RachfordRice() {}

    public static final class Result {
        public final double beta;
        public final PhaseSplit phase;

        public Result(double beta, PhaseSplit phase) {
            this.beta = beta;
            this.phase = phase;
        }
    }

    /* solve Rachford-Rice */
    public static Result solve(double[] z, double[] K) {
        return solve(z, K, 100, 1e-12);
    }

    public static Result solve(double[] z, double[] K, int maxIter, double tol) {
        if (z.length != K.length) {
            throw new IllegalArgumentException("z and K must have the same dimensions");
        }

        double f0 = f(0.0, z, K);
        double f1 = f(1.0, z, K);

        if (f0 < 0.0) {
            return new Result(0.0, PhaseSplit.ALL_LIQUID);
        }

        if (f1 > 0.0) {
            return new Result(1.0, PhaseSplit.ALL_VAPOR);
        }

        // Two-phase...root exists in [0, 1]
        double lo = 0.0;
        double hi = 1.0;
        double beta = 0.5;

        for (int iter = 0; iter < maxIter; iter++) {
            double fb = f(beta, z, K);

            if (Math.abs(fb) < tol) {
                return new Result(beta, PhaseSplit.TWO_PHASE);
            }

            double dfb = df(beta, z, K);
            double betaNew;

            if (dfb != 0.0 && Double.isFinite(dfb)) {
                betaNew = beta - fb / dfb;
            } else {
                betaNew = Double.NaN;
            }

            if (!Double.isFinite(betaNew) || betaNew <= lo || betaNew >= hi) {
                betaNew = 0.5 * (lo + hi);
            }

            if (fb > 0.0) {
                lo = beta;
            } else {
                hi = beta;
            }

            beta = betaNew;

            if (hi - lo < tol) {
                return new Result(0.5 * (lo + hi), PhaseSplit.TWO_PHASE);
            }
        }

        return new Result(0.5 * (lo + hi), PhaseSplit.TWO_PHASE);
    }

    /* RR function f(beta) */
    private static double f(double beta, double[] z, double[] K) {
        double sum = 0.0;
        for (int i =0; i < z.length; i++) {
            double Ki = K[i];
            if (Ki <= 0.0) {
                throw new IllegalArgumentException("K[" + i + "] must be > 0");
            }

            double denom = 1.0 + beta * (Ki - 1.0);
            sum += z[i] * (Ki - 1.0) / denom;
        }

        return sum;
    }

    /* Derivative f'(beta) for Newton Step */
    private static double df(double beta, double[] z, double[] K) {
        double sum = 0.0;
        for (int i = 0; i < z.length; i++) {
            double Ki = K[i];
            double denom = 1.0 + beta * (Ki - 1.0);
            double term = z[i] * (Ki - 1.0) / denom;
            sum -= term * (Ki - 1.0) / denom;
        }
        return sum;
    }
}
