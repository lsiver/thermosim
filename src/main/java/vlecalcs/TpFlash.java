package vlecalcs;

import java.util.Arrays;

import properties.AntoineMmHg;
import properties.VaporPressureCorrelation;

public final class TpFlash {
    private final KModel kModel;

    public TpFlash(KModel kModel) { this.kModel = kModel; }

    public FlashResult solve(double T, double P, double[] z) {
        double[] K = kModel.K(T, P, z);

        RachfordRice.Result rr = RachfordRice.solve(z, K);
        double beta = rr.beta;

        double[] x = new double[z.length];
        double[] y = new double[z.length];

        for (int i = 0; i < z.length; i++) {
            x[i] = z[i] / (1.0 + beta * (K[i] - 1.0));
            y[i] = K[i] * x[i];
        }

        // System.out.println("K = " + Arrays.toString(K));
        // System.out.println("phase = " + rr.phase + ", beta = " + rr.beta);

        normalize(x);
        normalize(y);
        return new FlashResult(beta, x, y, K);
    }

private static void normalize(double[] x) {
    double sum = 0.0;

    for (int i = 0; i <x.length; i++) {
        if (x[i] < 0 && x[i] > -1e-12) {
            x[i] =0.0;
        }
        sum += x[i];
    }

    if (sum <= 0.0) {
        throw new IllegalArgumentException("Cannot normalize");
    }

    for (int i = 0; i < x.length; i++) {
        x[i] /= sum;
    }
}

public static void main(String[] args) {
    double T = 360.0;
    double P = 60000.0;

    double[] z = {0.20, 0.80};

    VaporPressureCorrelation benzene = AntoineMmHg.of(6.90565, 1211.033, 220.790);
    VaporPressureCorrelation toluene = AntoineMmHg.of(6.95464, 1344.800, 219.480);

    KModel kModel = new RaoultsLawKModel(new VaporPressureCorrelation[]{benzene, toluene});

    TpFlash flash = new TpFlash(kModel);

    FlashResult r = flash.solve(T, P, z);

        System.out.println("Test Run");
        System.out.println("T = " + T + " K");
        System.out.println("P = " + P + " Pa");
        System.out.println("z = " + Arrays.toString(z));
        System.out.println("beta = " + r.vaporFraction());
        System.out.println("x = " + Arrays.toString(r.x()));
        System.out.println("y = " + Arrays.toString(r.y()));
        //Add this as an actual unit test eventually

}
}

