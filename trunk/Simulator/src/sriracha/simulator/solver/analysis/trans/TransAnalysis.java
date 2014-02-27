package sriracha.simulator.solver.analysis.trans;

import sriracha.simulator.Options;
import sriracha.simulator.Simulator;
import sriracha.simulator.model.Circuit;
import sriracha.simulator.solver.analysis.Analysis;
import sriracha.simulator.solver.analysis.AnalysisType;
import sriracha.simulator.solver.analysis.IAnalysisResults;
import sriracha.math.MathActivator;
import sriracha.math.interfaces.IComplex;
import sriracha.simulator.parser.CircuitBuilder;
import sriracha.math.interfaces.IRealVector;

/**
 * Created with IntelliJ IDEA.
 * User: sikram
 * Date: 10/1/13
 * Time: 8:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransAnalysis extends Analysis{

    private MathActivator activator = MathActivator.Activator;

    private TransEquation equation;
    // private TransEquation originalEquation;

    private double timeStart;

    private double timeEnd;

    double timeStep;

    double frequency;

    /* TODO: Initial voltage is 0 for DC sources only */
    public IRealVector voltageVector;

    /* TODO: Initial voltage is 0 for DC sources only */
    //double initialVoltage = 0;

    //double currentVoltage = initialVoltage;

    public TransAnalysis(double timeStart, double timeEnd, double timeStep)
    {
        super(AnalysisType.TR);
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.timeStep = timeStep;
        this.frequency = frequency;
    }

    @Override
    public void extractSolvingInfo(Circuit circuit)
    {
         equation = TransEquation.generate(circuit);
    }

    @Override
    public IAnalysisResults run()
    {
        voltageVector = activator.realVector(equation.getG().getNumberOfColumns());
        TransResults results = new TransResults();
        double currentTime = timeStart;
        double nextTime = 0;
        int p = 0;

        for (double i = timeStart; i <= timeEnd; i += timeStep) {

             /* To ensure that x(n+1) i.e. the next voltage value is not out of range */
            if (nextTime >= timeEnd) {
               break;
            }

            if (Simulator.Instance.isCancelRequested())
                return null;

            if (Options.isPrintProgress())
                System.out.println("Transient solving point");

            nextTime =  currentTime + timeStep;

            IRealVector soln = null;//equation.solve(timeStep, voltageVector, nextTime, ACfrequency, AcValue, DcValue, p);

//            /* The first result is the initial guess. */
//            if (p == 0) {
//
//               results.addVector(currentTime, equation.getInitialGuess());
//
//            /* Subsequent results are found through backward euler */
//            }

            results.addVector(nextTime, soln);
            currentTime += timeStep;
            voltageVector = soln;
            p++;

        }

        return results;
    }

}
