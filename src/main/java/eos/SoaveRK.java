package eos;

public class SoaveRK {
    /*
    SoaveRedlichKwong EOS

    P = RT/(V - b) - a(T) / V*(V+b)
    a(T) = psi * alpha(Tr)* R^2 * Tc^2 / Pc
    b = omega * R * Tc / Pc
    For SRK, alpha(Tr) = alphaSRK(Tr; w)
    alphaSRK(Tr; w) = (1 + (0.480 +1.574*w - 0.176*w^2)(1 - Tr^0.5))^2
    w from tables or solved from w = -1.0 - log10(Pr*) at Tr = 0.7
    for SRK, sigma = 1, eta = 0, omega = 0.08664, psi = 0.42748, Zc = 1/3
    */

    double sigma = 1;
    double eta = 0;
    double omega = 0.08664;
    double psi = 0.42748;
    double Zc = 1/3;
    double T; //temperature, rankine
    double V; //volume, ft^3
    double P; //pressure, atm

    public SoaveRK(double T, double V  ) {
        this.T = T+459.67;
        this.V = V;
    }

    public double calcPressure(double Tc, double Pc, double w) {
        /* Must supply critical temperature (Tc) and critical pressure (Pc) */

        double Tr = T / Tc;

        double alpha_SRK = Math.pow((1+ (0.480 + 1.574 * w - 0.176 * Math.pow(w,2))*(1 - Math.pow(Tr,0.5))),2);
        double a_T = psi*alpha_SRK*Math.pow(0.7302,2)*Math.pow(Tc,2)/Pc;
        double b = omega*0.7302*Tc/Pc;
        this.P = 0.7302*T/(V - b) - a_T / (V*(V+b));

        return this.P;
    }

    public static void main(String[] args) {
        SoaveRK test = new SoaveRK(122, 2);
        test.calcPressure(343.1, 45.4, 0.012);
        System.out.println(test.P);
    }
}