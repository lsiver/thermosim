package distillation;

public interface ColumnSolver {
    ColumnResult solve(DistillationColumn column, ThermodynamicProvider thermo);
    
}