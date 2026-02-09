package vlecalcs;

import java.util.Arrays;

import properties.AntoineMmHg;
import properties.VaporPressureCorrelation;

public final class TXYDiagram {
    private final KModel kModel;
    public final double[] y = new double[101];
    public final double[] T = new double[101];
    public final double[] x = new double[101];

    public TXYDiagram(KModel kModel) { this.kModel = kModel; }

    public void genXYDiagram(double P) {
        //Binary sweep
        BubblePoint bp = new BubblePoint(kModel);
        double[] xi = {0.0, 1.0};

        for (int i = 0; i< 101; i++) {
            BubblePoint.Result r = bp.solveAtP(P, xi, 360.0);
            x[i] = xi[0];
            xi[0] += 0.01;
            xi[1] -= 0.01;

            T[i] = r.T;
            y[i] = r.y[0];
        }

    };

    public static void main(String[] args) {
        double P = 101325.0;

        double[] z = {0.20, 0.80};

        VaporPressureCorrelation benzene = AntoineMmHg.of(6.90565, 1211.033, 220.790);
        VaporPressureCorrelation toluene = AntoineMmHg.of(6.95464, 1344.800, 219.480);

        KModel kModel = new RaoultsLawKModel(new VaporPressureCorrelation[]{benzene, toluene});

        TXYDiagram txy = new TXYDiagram(kModel);

        txy.genXYDiagram(P);

        for (int i =0;i<txy.y.length;i++) {
            System.out.println(txy.x[i]);
        }

    }
}
