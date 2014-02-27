package sriracha.simulator.parser;

import sriracha.math.MathActivator;
import sriracha.math.interfaces.IComplex;
import sriracha.simulator.model.CircuitElement;
import sriracha.simulator.model.ICollectElements;
import sriracha.simulator.model.elements.Capacitor;
import sriracha.simulator.model.elements.Diode;
import sriracha.simulator.model.elements.Inductor;
import sriracha.simulator.model.elements.Resistor;
import sriracha.simulator.model.elements.ctlsources.*;
import sriracha.simulator.model.elements.sources.CurrentSource;
import sriracha.simulator.model.elements.sources.VoltageSource;
import sriracha.simulator.model.models.CircuitElementModel;
import sriracha.simulator.model.models.DiodeModel;

/**
 * Created by yiqing on 26/02/14.
 */
public class ElementParser {

    public static void createCurrentSource(ICollectElements elementCollection, String name, String node1, String node2, String... params)
    {
        SourceValue value = findPhasorOrDC(params);
        CircuitBuilder.ACValue = value.AC;
        CircuitBuilder.DCValue = value.DC;
        CurrentSource source;
        if (value.AC != null)
            source = new CurrentSource(name, value.AC);
        else
            source = new CurrentSource(name, value.DC);

        int node1Index = elementCollection.assignNodeMapping(node1);
        int node2Index = elementCollection.assignNodeMapping(node2);
        source.setNodeIndices(node1Index, node2Index);
        elementCollection.addElement(source);
    }

    public static void createVoltageSource(ICollectElements elementCollection, String name, String node1, String node2, String... params)
    {
        SourceValue value = findPhasorOrDC(params);
        CircuitBuilder.ACValue = value.AC;
        CircuitBuilder.DCValue = value.DC;
        VoltageSource source = new VoltageSource(name, value.DC, value.AC);

        int node1Index = elementCollection.assignNodeMapping(node1);
        int node2Index = elementCollection.assignNodeMapping(node2);
        source.setNodeIndices(node1Index, node2Index);
        elementCollection.addElement(source);
    }

    public static void createResistor(ICollectElements elementCollection, String name, String node1, String node2, String value)
    {
        Resistor r = new Resistor(name, parseDouble(value));
        int node1Index = elementCollection.assignNodeMapping(node1);
        int node2Index = elementCollection.assignNodeMapping(node2);
        r.setNodeIndices(node1Index, node2Index);
        elementCollection.addElement(r);
    }


    public static void createCapacitor(ICollectElements elementCollection, String name, String node1, String node2, String value)
    {
        Capacitor c = new Capacitor(name, parseDouble(value));
        int node1Index = elementCollection.assignNodeMapping(node1);
        int node2Index = elementCollection.assignNodeMapping(node2);
        c.setNodeIndices(node1Index, node2Index);
        elementCollection.addElement(c);
    }

    public static void createInductor(ICollectElements elementCollection, String name, String node1, String node2, String value)
    {
        Inductor i = new Inductor(name, parseDouble(value));
        int node1Index = elementCollection.assignNodeMapping(node1);
        int node2Index = elementCollection.assignNodeMapping(node2);
        i.setNodeIndices(node1Index, node2Index);
        elementCollection.addElement(i);
    }




    public static void createVCCS(ICollectElements elementCollection, String name, String node1, String node2, String control1, String control2, String value)
    {
        VCCS vccs = new VCCS(name, parseDouble(value));
        createVoltageControlledSource(elementCollection, node1, node2, control1, control2, vccs);
    }

    public static void createVCVS(ICollectElements elementCollection, String name, String node1, String node2, String control1, String control2, String value)
    {
        VCVS vcvs = new VCVS(name, parseDouble(value));
        createVoltageControlledSource(elementCollection, node1, node2, control1, control2, vcvs);
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
            return new SourceValue(parseDouble(params[0]));

        if (params[0].equalsIgnoreCase("DC"))
        {
            if ((params.length > 2) && (params.length <= 6))
            {
                if (params[2].equalsIgnoreCase("AC"))
                {
                    double amplitude = 1, phase = 0;
                    if (params.length >= 4)
                        amplitude = parseDouble(params[3]);
                    if (params.length >= 5)
                        phase = Math.toRadians(parseDouble(params[4]));
                    if (params.length >= 6) {
                        CircuitBuilder.transFrequency = parseDouble(params[5]);
                    }

                    double real = amplitude * Math.cos(phase);
                    double imaginary = amplitude * Math.sin(phase);

                    return new SourceValue(parseDouble(params[1]), MathActivator.Activator.complex(real, imaginary));
                }
                else throw new ParseException("Invalid parameters on Voltage Source " + params);
            }  else if (params.length >= 6)
            {
                if (params[2].equalsIgnoreCase(""))
                {
                    double amplitude = 1, phase = 0;
                    //frequency = 1;
                    if (params.length >= 4)
                        amplitude = parseDouble(params[3]);
                    if (params.length >= 5)
                        phase = Math.toRadians(parseDouble(params[4]));
                    if (params.length >= 6)
                        CircuitBuilder.transFrequency = parseDouble(params[5]);

                    double real = amplitude * Math.cos(phase);
                    double imaginary = amplitude * Math.sin(phase);

                    return new SourceValue(parseDouble(params[1]), MathActivator.Activator.complex(real, imaginary));
                }
                else throw new ParseException("Invalid parameters on Voltage Source " + params);
            }
            else
            {
                return new SourceValue(parseDouble(params[1]));
            }

        }
        else if (params[0].equalsIgnoreCase("AC"))
        {
            double amplitude = 1, phase = 0;
            if (params.length >= 2)
                amplitude = parseDouble(params[1]);
            if (params.length >= 3)
                phase = Math.toRadians(parseDouble(params[2]));

            double real = amplitude * Math.cos(phase);
            double imaginary = amplitude * Math.sin(phase);


            return new SourceValue(MathActivator.Activator.complex(real, imaginary));
        }
        else
            throw new ParseException("Invalid source format: " + params[0]);
    }

    /**
     * parses a double and allows for Spice compatible postfixes (case insensitive)
     * T = 10^12
     * G = 10^9
     * Meg = 10^6
     * K = 10^3
     * m = 10^-3
     * u = 10^-6
     * n = 10^-9
     * p = 10^-12
     * f = 10^-15
     * <p/>
     * the string can be followed with any text after these as long as what comes before is parsable
     *
     * @param str - the string to parse
     * @return result as a double
     */
    public static double parseDouble(String str)
    {

        int i = 0;
        char c = str.charAt(0);
        while ((Character.isDigit(c) || c == '.' || c == '-' || Character.toLowerCase(c) == 'e') && ++i < str.length())
        {
            c = str.charAt(i);
        }

        String str1 = str.substring(0, i), str2 = str.substring(i);

        double factor = 1;

        str2 = str2.toLowerCase();

        if (str2.startsWith("t"))
            factor = Math.pow(10, 12);
        else if (str2.startsWith("g"))
            factor = Math.pow(10, 9);
        else if (str2.startsWith("meg"))
            factor = Math.pow(10, 6);
        else if (str2.startsWith("k"))
            factor = Math.pow(10, 3);
        else if (str2.startsWith("m"))
            factor = Math.pow(10, -3);
        else if (str2.startsWith("u"))
            factor = Math.pow(10, -6);
        else if (str2.startsWith("n"))
            factor = Math.pow(10, -9);
        else if (str2.startsWith("p"))
            factor = Math.pow(10, -12);
        else if (str2.startsWith("f"))
            factor = Math.pow(10, -15);


        return factor * Double.parseDouble(str1);

    }


}
