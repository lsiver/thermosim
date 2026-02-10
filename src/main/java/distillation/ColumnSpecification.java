package distillation;

public class ColumnSpecification {
    private final int Stages;
    private final int[] FeedPoints;
    private final int[] SideDraws;
    private final double[] PressureProfile;

    public ColumnSpecification(int Stages, int[] FeedPoints, int[] SideDraws, double[] PressureProfile) {
        this.Stages = Stages;
        this.FeedPoints = FeedPoints;
        this.SideDraws = SideDraws;
        this.PressureProfile = PressureProfile;
    }
}