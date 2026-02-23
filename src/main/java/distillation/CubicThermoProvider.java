package distillation;

import eos.CubicEos;
import vlecalcs.*;

public class CubicThermoProvider {
    //private final CubicEos eos;
    private final KModel kmodel;
    //private final EnthalpyModel enthalpyModel;

    public CubicThermoProvider(KModel kmodel) {
        this.kmodel = kmodel;
        //this.eos = eos;
        //this.enthalpyModel = enthalpyModel;
    }



}
