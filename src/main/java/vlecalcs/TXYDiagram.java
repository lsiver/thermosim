package vlecalcs;

import java.util.Arrays;

import properties.AntoineMmHg;
import properties.VaporPressureCorrelation;

public final class TXYDiagram {
    private final KModel kModel;
    public final double[] Tbub = new double[101];
    public final double[] Tdew = new double[101];
    public final double[] x = new double[101];
    public final double[] y = new double[101];

    public TXYDiagram(KModel kModel) { this.kModel = kModel; }

    public void genXYDiagram(double P) {
        //Binary sweep
        BubblePoint bp = new BubblePoint(kModel);
        DewPoint dp = new DewPoint(kModel);
        double[] xi = {0.0, 1.0};

        for (int i = 0; i< 101; i++) {
            BubblePoint.Result bpr = bp.solveAtP(P, xi, 360.0);
            DewPoint.Result dpr = dp.solveAtP(P, xi, 360.0);
            x[i] = xi[0];
            xi[0] += 0.01;
            xi[1] -= 0.01;

            Tbub[i] = bpr.T;
            Tdew[i] = dpr.T;
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

        for (int i =0;i<txy.Tbub.length;i++) {
            System.out.println(txy.x[i]);
            System.out.println("Tbub " + txy.Tbub[i]);
            System.out.println("TDew " + txy.Tdew[i]);
        }

    }
}
