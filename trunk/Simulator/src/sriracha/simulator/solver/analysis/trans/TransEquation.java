package sriracha.simulator.solver.analysis.trans;

import sriracha.math.MathActivator;
import sriracha.math.interfaces.IComplex;
import sriracha.math.interfaces.IRealMatrix;
import sriracha.simulator.Options;
import sriracha.simulator.model.Circuit;
import sriracha.simulator.model.CircuitElement;
import sriracha.math.interfaces.IComplexVector;
import sriracha.math.interfaces.IRealVector;

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
    private IComplexVector b;
    /**
     * b(n+1)
     */
    private IRealVector b2;
    private IRealVector b3;
    private IRealMatrix G;
    private IRealVector initialVoltageGuess;
    private IRealVector matrixMultiplicationResult;

    private TransEquation(int circuitNodeCount)
    {
        this.circuitNodeCount = circuitNodeCount;

        C = activator.realMatrix(circuitNodeCount, circuitNodeCount);
        b = activator.complexVector(circuitNodeCount);
        b2 = activator.realVector(circuitNodeCount);
        b3 = activator.realVector(circuitNodeCount);
        G = activator.realMatrix(circuitNodeCount, circuitNodeCount);
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
        //System.out.println("G matrix: " + G);
        //System.out.println("C matrix: " + C);
         //CALL MATRIX MULTIPLICATION METHOD
           matrixMultiplication((IRealMatrix)(G.times(1/timeStep)), currentVoltage);
           return (IRealVector) (matrixMultiplicationResult.plus(b2));
    }

    /* Solve: P*x(n+1) = Q for the given the current nodal voltages and the
    *  returns x(n+1) , i.e. nextVoltage
    * */
    IRealVector solve(double timeStep, IRealVector currentVoltage, double nextTime, double frequency, IComplex AcValue,
                      double DcValue, int p) {

        //FUNCTION CALL
        getNewBVector(frequency, nextTime, AcValue, DcValue);

        /* For the first time that solve is called, use a zero initial guess */
        if (p == 0) {
            //(P = C + G/h)
            IRealMatrix P = buildMatrixP(timeStep);
            //IRealVector Q = buildVectorQ(timeStep, initialVoltageGuess);

            /* Apply DC analysis to get initial guess (AC sources are shorted to 0) */
            initialVoltageGuess =  getInitialGuess();
            //Q = G/h*x(n) + b(n+1)
            IRealVector Q =  buildVectorQ(timeStep, initialVoltageGuess);

            if (Options.isPrintMatrix())
            {
                System.out.println(P);
                System.out.println("=\n");
                System.out.println(Q);
            }

            return P.solve(Q);
        //For all subsequent calculation for time steps following the initial guess
        }  else {
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
            //P * x(n+1) = Q, solve for x(n+1)
            return P.solve(Q);
        }
    }

    /* TODO: currently only for AC and DC sources */
    public void getNewBVector(double frequency, double nextTime, IComplex AcValue, double DcValue) {

        IComplex element;
        double real;
        double imag;
        double amplitude;
        double phase;
        double result;
        for (int i = 0; i < circuitNodeCount; i++) {
            //Note at this point, vector b is already filled with phasor values, all that left to do
            //is to find the present magnitude of the signal at each node
            element = b.getValue(i);
            real = element.getReal();
            imag = element.getImag();

            /* For AC source */
            if (AcValue.getReal() != 0 || AcValue.getImag() != 0) {

                if (real != 0 || imag != 0) {

                     real = AcValue.getReal();
                     imag = AcValue.getImag();
                     amplitude = Math.sqrt((real*real) + (imag*imag));
                     phase = Math.atan(imag/real);
                     result = amplitude*(Math.cos(((Math.PI * 2 * frequency) * nextTime) + phase));
                     b2.setValue(i, result);
                     b3.setValue(i, real);
                     //System.out.println("Result: " + result);

               }

            /* For DC Source */
            } else {
                 result = DcValue;
                 b2.setValue(i, result);
                 b3.setValue(i, result);
            }
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
        clone.G = (IRealMatrix) G.clone();
        clone.C = (IRealMatrix) C.clone();
        clone.b = (IComplexVector) b.clone();
        return clone;
    }

    private TransEquation(IRealMatrix c, IComplexVector b, IRealMatrix g)
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

    public void applySourceVectorStamp(int i, IComplex d)
    {
        //no stamps to ground
        if (i == -1) return;

        b.addValue(i, d);
    }


    public void applyTransConductorInductor(int i, int j, double value)
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
