package sriracha.simulator.solver.analysis.trans;

import sriracha.simulator.solver.analysis.IResultVector;
import sriracha.math.interfaces.IRealVector;

/**
 * Created with IntelliJ IDEA.
 * User: sikram
 * Date: 10/24/13
 * Time: 1:50 AM
 * To change this template use File | Settings | File Templates.
 */

public class TransResult implements IResultVector
{
    private double transValue;
    private IRealVector vector;

    public TransResult(double transValue, IRealVector vector)
    {
        this.transValue = transValue;
        this.vector = vector;
    }

    public double getTransValue()
    {
        return transValue;
    }

    public IRealVector getVector()
    {
        return vector;
    }

    @Override
    public double getX()
    {
        return transValue;
    }

    @Override
    public IRealVector getData()
    {
        return vector;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
