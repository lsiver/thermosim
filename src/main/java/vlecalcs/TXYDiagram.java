package vlecalcs;

import java.util.Arrays;

import properties.AntoineMmHg;
import properties.VaporPressureCorrelation;

public final class TXYDiagram {
    private final KModel kModel;

    public TXYDiagram(KModel kModel) { this.kModel = kModel; }

    public static class VLEPoint {
        public double targetZ;
        public double tBubble;
        public double tDew;
        public double yEq;

        public VLEPoint(double z, double tb, double td, double y) {
            this.targetZ = z;
            this.tBubble = tb;
            this.tDew = td;
            this.yEq = y;
        }
    }

    public VLEPoint[] genXYDiagram(double P) {
        //Binary sweep
        VLEPoint[] points = new VLEPoint[101];

        BubblePoint bp = new BubblePoint(kModel);
        DewPoint dp = new DewPoint(kModel);

        double guessTbp = 360.0;
        double guessTdp = 360.0;

        for (int i = 0; i<= 100; i++) {
            double zVal = i / 100.0;
            double[] composition = {zVal, 1.0 - zVal};
            BubblePoint.Result bpr = bp.solveAtP(P, composition, guessTbp);
            DewPoint.Result dpr = dp.solveAtP(P, composition, guessTdp);

            guessTbp = bpr.T;
            guessTdp = dpr.T;

            points[i] = new VLEPoint(zVal, bpr.T, dpr.T, bpr.y[0]);
        }
        
        return points;

    };


    public static void main(String[] args) {
        double P = 101325.0;
        
        VaporPressureCorrelation benzene = AntoineMmHg.of(6.90565, 1211.033, 220.790);
        VaporPressureCorrelation toluene = AntoineMmHg.of(6.95464, 1344.800, 219.480);

        KModel kModel = new RaoultsLawKModel(new VaporPressureCorrelation[]{benzene, toluene});

        TXYDiagram txy = new TXYDiagram(kModel);

        VLEPoint[] points = txy.genXYDiagram(P);

        for (int i =0;i<points.length;i++) {
            System.out.println(points[i].targetZ);
            System.out.println("Tbub " + points[i].tBubble);
            System.out.println("TDew " + points[i].tDew);
        }

    }
}
