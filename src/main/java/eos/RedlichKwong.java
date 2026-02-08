package eos;

public class RedlichKwong {
    //P = RT/(V - b) - a(T) / V*(V+b)
    //a(T) = psi * alpha(Tr)* R^2 * Tc^2 / Pc
    //b = omega * R * Tc / Pc
    //For RK, alpha(Tr) = Tr^(-1/2)
    //for RK, sigma = 1, eta = 0, omega = 0.08664, psi = 0.42748, Zc = 1/3

    double sigma = 1;
    double eta = 0;
    double omega = 0.08664;
    double psi = 0.42748;
    double Zc = 1/3;
    double temperature; //rankine
    double volume; //ft^3
    double pressure; //atm

    public RedlichKwong(double temperature, double volume  ) {
        this.temperature = temperature+459.67;
        this.volume = volume;
    }

    public double calcPressure(double Tc, double Pc) {
        /* Must supply critical temperature (Tc) and critical pressure (Pc) */
        double Tr = temperature / Tc;

        double a_T = psi*Math.pow(Tr,-0.5)*Math.pow(0.7302,2)*Math.pow(Tc,2)/Pc;
        double b = omega*0.7302*Tc/Pc;
        this.pressure = 0.7302*temperature/(volume - b) - a_T / (volume*(volume+b));

        return this.pressure;
    }

    public static void main(String[] args) {
        RedlichKwong test = new RedlichKwong(122, 2);
        test.calcPressure(343.1, 45.4);
        System.out.println(test.pressure);
    }
}