package distillation;

import java.util.List;

import database.ChemicalComponent;

public class ThermodynamicSystem {
    private final List<ChemicalComponent> components;

    public ThermodynamicSystem(List<ChemicalComponent> components) {
        this.components = components;
    }

    public int getComponentCount() {
        return components.size();
    }

    public ChemicalComponent getComponent(int index) {
        return components.get(index);
    }
}