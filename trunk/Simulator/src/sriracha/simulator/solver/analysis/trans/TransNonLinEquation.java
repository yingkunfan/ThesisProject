package sriracha.simulator.solver.analysis.trans;

import sriracha.math.MathActivator;
import sriracha.math.interfaces.IRealMatrix;
import sriracha.math.interfaces.IRealVector;
import sriracha.simulator.model.NonLinCircuitElement;

import java.util.ArrayList;

/**
 * Created by yiqing on 01/03/14.
 */
public class TransNonLinEquation extends TransEquation{

    public static final double STD_H = 1e-9;
    public static final double STD_THRESHOLD = 9e-15;
    public static final int STD_DIVERGENCE_TOLERANCE = 5;
    public static final int STD_CONT_METHOD_ATTEMPTS = 3;

    /**
     * Factory object for the Math module's objects.
     */
    private MathActivator activator = MathActivator.Activator;

    private ArrayList<NonLinCircuitElement> nonLinearElem;


    private TransNonLinEquation(int circuitNodeCount) {
        super(circuitNodeCount);

        //Note: the array list initiate with a guessed size of amount of
        //non-linear circuit element. (guessing it as number of nodes)
        nonLinearElem = new ArrayList<NonLinCircuitElement>(circuitNodeCount);
    }

    public void applyNonLinearCircuitElem(NonLinCircuitElement input){
        nonLinearElem.add(input);
    }
}
