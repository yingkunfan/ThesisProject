package sriracha.simulator.solver.analysis;

import sriracha.math.interfaces.IRealMatrix;

/**
 * Created by yiqing on 01/03/14.
 */
public abstract class Equation {
    protected boolean isLinear;
    protected IRealMatrix G;

    public Equation(boolean isLinear)
    {
        this.isLinear = isLinear;
    }

    public abstract void applyRealMatrixStamp(int i, int j, double value);

    public boolean isLinear(){
        return isLinear;
    }
}
