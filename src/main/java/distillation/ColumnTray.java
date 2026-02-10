package distillation;

public final class ColumnTray extends Stage {
    double efficiency;

    public ColumnTray(double T, double P, double L, double V, double x, double y, double z, double efficiency) {
        super(T, P, L, V, x, y, z);
        this.efficiency = efficiency;
    }

}