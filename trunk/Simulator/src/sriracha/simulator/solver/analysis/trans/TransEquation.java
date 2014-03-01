package sriracha.simulator.solver.analysis.trans;

import sriracha.math.MathActivator;
import sriracha.math.interfaces.IComplex;
import sriracha.math.interfaces.IRealMatrix;
import sriracha.simulator.Options;
import sriracha.simulator.model.Circuit;
import sriracha.simulator.model.CircuitElement;
import sriracha.math.interfaces.IComplexVector;
import sriracha.math.interfaces.IRealVector;
import sriracha.simulator.model.elements.sources.Source;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: sikram
 * Date: 10/1/13
 * Time: 9:22 PM
 * To change this template use File | Settings | File Templates.
 * CURRENTLY INITIAL CONDITION is 0, I.E. V AT T=0 IS 0.
 * CURRENTLY ONLY IMPLEMENTS AC input (NOT DC INPUT)
 */
public class TransEquation {

    private MathActivator activator = MathActivator.Activator;

    public static int circuitNodeCount;

    private IRealMatrix C;
    private IRealVector b;
    /**
     * b(n+1)
     */
    private IRealVector b2;
    private IRealVector b3;
    private IRealMatrix G;
    private IRealVector initialVoltageGuess;
    private IRealVector matrixMultiplicationResult;
    private ArrayList<Source>sources;


    private TransEquation(int circuitNodeCount)
    {
        this.circuitNodeCount = circuitNodeCount;

        C = activator.realMatrix(circuitNodeCount, circuitNodeCount);
        b = activator.realVector(circuitNodeCount);
        b2 = activator.realVector(circuitNodeCount);
        b3 = activator.realVector(circuitNodeCount);
        G = activator.realMatrix(circuitNodeCount, circuitNodeCount);
        sources = new ArrayList<Source>();
        matrixMultiplicationResult = activator.realVector(circuitNodeCount);
        initialVoltageGuess = activator.realVector(circuitNodeCount);
    }

    public IRealVector getInitialGuess()
    {
          return C.solve(b3);
    }


    /**
     * builds matrix C + G/h for backward Euler
     *
     */
    public IRealMatrix buildMatrixP(double timeStep)
    {

        return (IRealMatrix) C.plus(G.times(1/timeStep));
    }

    /**
     * builds vector [(G/h)*x(n)+b(n+1)] for backward Euler
     *
     */
    public IRealVector buildVectorQ(double timeStep, IRealVector currentVoltage)
    {
           matrixMultiplication((IRealMatrix)(G.times(1/timeStep)), currentVoltage);
           return (IRealVector) (matrixMultiplicationResult.plus(b));
    }

    /* Solve: P*x(n+1) = Q for the given the current nodal voltages and the
    *  returns x(n+1) , i.e. nextVoltage
    * */
    IRealVector solve(double timeStep, IRealVector currentVoltage, double nextTime) {

        //Update the b vector for the "nextTime" specified.
        getNewBVector(nextTime);
        //(P = C + G/h)
        IRealMatrix P = buildMatrixP(timeStep);
        //Q = G/h*x(n) + b(n+1)
        IRealVector Q = buildVectorQ(timeStep, currentVoltage);
        if (Options.isPrintMatrix())
        {
            System.out.println(P);
            System.out.println("=\n");
            System.out.println(Q);
        }
        //P*x(n+1) = Q, solve for x(n+1)
        return P.solve(Q);

    }

    /**
     * Reload the b vector with transient values specified at "nextTime".
     * @param nextTime time value at which the b vector values should be probed.
     */
    public void getNewBVector(double nextTime) {
        b.clear();

        for(Source src: sources)
        {
            src.updateSourceVector(this, nextTime);
        }

    }

        public void matrixMultiplication(IRealMatrix a, IRealVector b)
    {
        int columnsInB = 1;
        double value = 0;

        for (int i = 0; i < circuitNodeCount; i++) {

            for (int j = 0; j < columnsInB; j++) {

                for (int k = 0; k < circuitNodeCount; k++) {

                    value = value + (a.getValue(i, k) * b.getValue(k));

                }
            }

            matrixMultiplicationResult.setValue(i, value);
        }
    }

    public static TransEquation generate(Circuit circuit)
    {
        TransEquation equation = new TransEquation(circuit.getMatrixSize());

        for (CircuitElement element : circuit.getElements())
        {
            element.applyTrans(equation);
        }

        return equation;
    }

    @Override
    public TransEquation clone()
    {
        TransEquation clone = new TransEquation(b.getDimension());
        clone.G =  G.clone();
        clone.C =  C.clone();
        clone.b =  b.clone();
        return clone;
    }

    private TransEquation(IRealMatrix c, IRealVector b, IRealMatrix g)
    {
        this.C = c;
        this.b = b;
        this.G = g;
    }

    public void applyTransRealMatrixStamp(int i, int j, double value)
    {

        //no stamps to ground
        if (i == -1 || j == -1) return;

        if (value != 0)
            C.addValue(i, j, value);

    }

    /**
     * Called during creation of this equation, the function construct the b vector by
     * obtaining reference to all sources in the circuit.
     * @param targetSrc
     */
    public void applySourceVectorStamp(Source targetSrc)
    {
        sources.add(targetSrc);
    }

    /**
     * Update the present b vector.
     * @param i index within the b vector where the change is to be applied.
     * @param d value to be added in the b vector at the specified "i" index.
     */
    public void updateSourceVector(int i, double d)
    {
        if(i == -1) return;

        if (d != 0)
            b.addValue(i, d);

    }
    public void applyComplexMatrixStamp(int i, int j, double value)
    {
        //no stamps to ground
        if (i == -1 || j == -1) return;

        if (value != 0)
        {
            G.addValue(i, j, value);

        }
    }

    public IRealMatrix getG() {
        return G;
    }

}
