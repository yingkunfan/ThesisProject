package sriracha.simulator.parser;

import sriracha.math.MathActivator;
import sriracha.simulator.model.CircuitElement;
import sriracha.simulator.model.ICollectElements;
import sriracha.simulator.model.elements.ctlsources.*;
import sriracha.simulator.model.elements.sources.CurrentSource;
import sriracha.simulator.model.elements.sources.VoltageSource;

import java.util.Arrays;


/**
 * Created by yiqing on 27/02/14.
 */
public class SourceParser {

    public static void createCurrentSource(ICollectElements elementCollection, String[]params)
    {
        String[] additionalParams = Arrays.copyOfRange(params, 3, params.length);
        SourceValue value = findPhasorOrDC(additionalParams);
        CurrentSource source = new CurrentSource(params[0], value.DC, value.AC);

        int node1Index = elementCollection.assignNodeMapping(params[1]);
        int node2Index = elementCollection.assignNodeMapping(params[2]);
        source.setNodeIndices(node1Index, node2Index);
        elementCollection.addElement(source);
    }

    public static void createVoltageSource(ICollectElements elementCollection, String[]params)
    {
        String[] additionalParams = Arrays.copyOfRange(params, 3, params.length);
        SourceValue value = findPhasorOrDC(additionalParams);
        VoltageSource source = new VoltageSource(params[0], value.DC, value.AC);

        int node1Index = elementCollection.assignNodeMapping(params[1]);
        int node2Index = elementCollection.assignNodeMapping(params[2]);
        source.setNodeIndices(node1Index, node2Index);
        elementCollection.addElement(source);
    }

    public static void createVCCS(ICollectElements elementCollection, String[]params)
    {
        VCCS vccs = new VCCS(params[0], CircuitBuilder.parseDouble(params[5]));
        createVoltageControlledSource(elementCollection, params[1], params[2], params[3], params[4], vccs);
    }

    public static void createVCVS(ICollectElements elementCollection, String[]params)
    {
        VCVS vcvs = new VCVS(params[0], CircuitBuilder.parseDouble(params[5]));
        createVoltageControlledSource(elementCollection, params[1], params[2], params[3], params[4], vcvs);
    }

    public static void createCCCS(ICollectElements elementCollection, String name, String node1, String node2, CircuitElement vSource, String value)
    {
        CCCS cccs = new CCCS(name, CircuitBuilder.parseDouble(value), (VoltageSource) vSource);

        createCurrentControlledSource(elementCollection, node1, node2, cccs);
    }


    public static void createCCVS(ICollectElements elementCollection, String name, String node1, String node2, CircuitElement vSource, String value)
    {
        CCVS ccvs = new CCVS(name, CircuitBuilder.parseDouble(value), (VoltageSource) vSource);
        SourceParser.createCurrentControlledSource(elementCollection, node1, node2, ccvs);
    }

    public static void createVoltageControlledSource(ICollectElements elementCollection, String node1, String node2, String control1, String control2, VCSource source)
    {
        int node1Index = elementCollection.assignNodeMapping(node1);
        int node2Index = elementCollection.assignNodeMapping(node2);
        int controlNode1Index = elementCollection.assignNodeMapping(control1);
        int controlNode2Index = elementCollection.assignNodeMapping(control2);
        source.setNodeIndices(node1Index, node2Index, controlNode1Index, controlNode2Index);
        elementCollection.addElement(source);
    }

    public static void createCurrentControlledSource(ICollectElements elementCollection, String node1, String node2, CCSource source)
    {
        int node1Index = elementCollection.assignNodeMapping(node1);
        int node2Index = elementCollection.assignNodeMapping(node2);
        source.setNodeIndices(node1Index, node2Index);
        elementCollection.addElement(source);
    }

    public static SourceValue findPhasorOrDC(String... params)
    {
        if (params.length == 1)
            return new SourceValue(CircuitBuilder.parseDouble(params[0]));

        if (params[0].equalsIgnoreCase("DC"))
        {
            //If DC specified, skip
            if ((params.length > 2) && (params.length <= 6))
            {
                if (params[2].equalsIgnoreCase("AC"))
                {
                    double amplitude = 1, phase = 0;
                    if (params.length >= 4)
                        amplitude = CircuitBuilder.parseDouble(params[3]);
                    if (params.length >= 5)
                        phase = Math.toRadians(CircuitBuilder.parseDouble(params[4]));
                    if (params.length >= 6) {
                        //CircuitBuilder.transFrequency = CircuitBuilder.parseDouble(params[5]);
                    }

                    double real = amplitude * Math.cos(phase);
                    double imaginary = amplitude * Math.sin(phase);

                    return new SourceValue(CircuitBuilder.parseDouble(params[1]), MathActivator.Activator.complex(real, imaginary));
                }
                else throw new ParseException("Invalid parameters on Voltage Source " + params);
            }  else if (params.length >= 6)
            {
                if (params[2].equalsIgnoreCase(""))
                {
                    double amplitude = 1, phase = 0;
                    //frequency = 1;
                    if (params.length >= 4)
                        amplitude = CircuitBuilder.parseDouble(params[3]);
                    if (params.length >= 5)
                        phase = Math.toRadians(CircuitBuilder.parseDouble(params[4]));
                    if (params.length >= 6)
                        ;//CircuitBuilder.transFrequency = CircuitBuilder.parseDouble(params[5]);

                    double real = amplitude * Math.cos(phase);
                    double imaginary = amplitude * Math.sin(phase);

                    return new SourceValue(CircuitBuilder.parseDouble(params[1]), MathActivator.Activator.complex(real, imaginary));
                }
                else throw new ParseException("Invalid parameters on Voltage Source " + params);
            }
            //only purely DC source
            else
            {
                return new SourceValue(CircuitBuilder.parseDouble(params[1]));
            }

        }
        //If source is purely AC
        else if (params[0].equalsIgnoreCase("AC"))
        {
            double amplitude = 1, phase = 0;
            if (params.length >= 2)
                amplitude = CircuitBuilder.parseDouble(params[1]);
            if (params.length >= 3)
                phase = Math.toRadians(CircuitBuilder.parseDouble(params[2]));

            double real = amplitude * Math.cos(phase);
            double imaginary = amplitude * Math.sin(phase);


            return new SourceValue(MathActivator.Activator.complex(real, imaginary));
        }
        else
            throw new ParseException("Invalid source format: " + params[0]);
    }

}
