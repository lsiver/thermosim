package eos;
import java.util.Arrays;

public final class CubicEos {
    private final CubicForm form;
    private final CubicParameters params;
    private static final double[] PURE = {1.0};
 
    public CubicEos(CubicForm form, CubicParameters params) {
        this.form = form;
        this.params = params;
    }

    public double[] zRoots(double T, double P, double[] x) {
        double a = params.a(T, x);
        double b = params.b(x);

        double A = a * P / (Constants.R*Constants.R*T*T);
        double B = b * P / (Constants.R*T);

        ZCubicCoefficients c = form.zCubic(A, B);
        double[] roots = CubicSolver.solveRealRoots(c.c2(), c.c1(),c.c0());
        Arrays.sort(roots);
        return roots;
    }

    public double[] zRoots(double T, double P) {
        return zRoots(T, P, PURE);
    }

    public double[] volumes(double T, double P, double[] x) {
        double[] Z = zRoots(T, P, x);
        double R = Constants.R;

        double[] V = new double[Z.length];
        for (int i = 0; i < Z.length; i++) {
            V[i] = Z[i] * R * T / P;
        }

        return V;
    }

    public double[] volumes(double T, double P) {
        return volumes(T, P, PURE);
    }

    public double liquidVolume(double T, double P, double [] x) {
        //m3/mol
        double[] Z = zRoots(T, P, x);
        Arrays.sort(Z);

        double Zl = Z[0];
        return Zl * Constants.R * T / P;
    }

    public double vaporVolume(double T, double P, double[] x) {
        //m3/mol
        double[] Z = zRoots(T, P, x);
        Arrays.sort(Z);

        double Zv = Z[Z.length - 1];
        return Zv * Constants.R * T / P;
    }

    public double liquidVolume(double T, double P) {
        return liquidVolume(T, P, PURE);
    }

    public double vaporVolume(double T, double P) {
        return vaporVolume(T, P, PURE);
    }

    public double pressure(double T, double V, double[] x) {
        //Pa
        double a = params.a(T, x);
        double b = params.b(x);
        double R = Constants.R;

        double denom = (V + form.eta() * b) * (V + form.sigma() * b);

        return R * T / (V - b) - a/denom;
    }

    public static void main(String[] args){
        CubicForm form = new SRKForm();
        CubicParameters params = new SRKPureParameters(190.61, 4590000, 0.011);

        CubicEos eos = new CubicEos(form, params);
        double[] roots = eos.zRoots(323.15,189.97);
        System.out.println(roots[0]);
        System.out.println(roots.length);

        //this ,after converting, better evaluate to 2ft3
        System.out.println(eos.volumes(323.15, 18997424.2)[0]);

        //this better evaluate to ~192.5 atm
        System.out.println(eos.pressure(323.15, 0.0566337/453, PURE)*0.00000986);

        CubicForm RKform = new RKForm();
        CubicParameters params2 = new RKPureParameters(190.61, 4590000, 0.011);
        CubicEos eos2 = new CubicEos(RKform, params2);

        //this better evaluate to ~187 atm
        System.out.println(eos2.pressure(323.15, 0.0566337/453, PURE)*0.00000986);

    }
}