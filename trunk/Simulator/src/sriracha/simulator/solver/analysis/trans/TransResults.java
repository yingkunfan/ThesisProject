package sriracha.simulator.solver.analysis.trans;

import sriracha.math.interfaces.IRealVector;
import sriracha.simulator.solver.analysis.IAnalysisResults;
import sriracha.simulator.solver.analysis.IResultVector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sheharyar
 * Date: 11/16/13
 * Time: 9:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransResults implements IAnalysisResults
{
    private List<IResultVector> data;

    public TransResults()
    {
        data = new ArrayList<IResultVector>();
    }

    public void addVector(double transValue, IRealVector vector)
    {
        data.add(new TransResult(transValue, vector));
    }


    @Override
    public List<IResultVector> getData()
    {
        return data;
    }
}
