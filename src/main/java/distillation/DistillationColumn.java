package distillation;

import java.util.ArrayList;
import java.util.List;

import database.ChemicalComponent;

public final class DistillationColumn {
    private final int stageCount;
    private Stage[] stages;

    public DistillationColumn(int stageCount) {
        this.stageCount = stageCount;

        for (int i = 0; i < stageCount; i++) {
            stages[i] = new Stage(300+i*2,101325+i*10,1,1,.5,.5,.5);
        }

    }



    public static void main(String[] args) {
        List<ChemicalComponent> chemicalList = new ArrayList<>();
        chemicalList.add(new ChemicalComponent("Methane", 16.04, 190.56, 45.99e5, 0.011));
        chemicalList.add(new ChemicalComponent("Ethane", 30.07, 305.32, 48.72e5, 0.099));
        
        ThermodynamicSystem thermoSystem = new ThermodynamicSystem(chemicalList);

        ColumnSpecification columnSpec = new ColumnSpecification(20, new int[]{5}, null, null);




    
    
    
    
    
    }
    
}