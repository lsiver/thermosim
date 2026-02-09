package vlecalcs;

public final class FlashResult {
    // private final double T;
    // private final double P;
    private final double vaporFraction;
    // private final double[] z;
    private final double[] x;
    private final double[] y;
    private final double[] K;
    // private final int iterations;
    // private final boolean converged;
    // private final PhaseSplit phaseSplit;

    public FlashResult(
        // double T,
        // double P,
        double vaporFraction,
        // double[] z,
        double[] x,
        double[] y,
        double[] K
        // int iterations,
        // boolean converged,
        // PhaseSplit phaseSplit
    ) {
        if (vaporFraction < -1e-12 || vaporFraction > 1.0 + 1e-12) {
            throw new IllegalArgumentException(
                "vaporFraction out of [0,1]: " + vaporFraction);
        }

        // this.T = T;
        // this.P = P;
        this.vaporFraction = vaporFraction;
        // this.z = z.clone();
        this.x = x.clone();
        this.y = y.clone();
        this.K = (K == null) ? null : K.clone();
        // this.iterations = iterations;
        // this.converged = converged;
        // this.phaseSplit = phaseSplit;
    }

    // public double temperature() {
    //     return T;
    // }
    // public double pressure() {
    //     return P;
    // }

    public double vaporFraction() {
        return vaporFraction;
    }

    public double liquidFraction() {
        return 1.0 - vaporFraction;
    }

    // public double[] z() {
    //     return z.clone();
    // }

    public double[] x() {
        return x.clone();
    }

    public double[] y() {
        return y.clone();
    }

    public double[] K() {
        return (K == null) ? null : K.clone();
    }

    // public int iterations() {
    //     return iterations;
    // }

    // public boolean converged() {
    //     return converged;
    // }

    // public PhaseSplit phaseSplit() {
    //     return phaseSplit;
    // }

    // public int nComponents() {
    //     return z.length;
    // }

    public boolean isTwoPhase(double tol) {
        return vaporFraction > tol && vaporFraction < 1.0 - tol;
    }

    @Override
    public String toString() {
        return "FlashResult{" +
                // "T=" + T +
                // ", P=" + P +
                ", beta=" + vaporFraction +
                // ", phase=" + phaseSplit +
                // ", converged=" + converged +
                // ", iterations=" + iterations +
                '}';
    }
}
