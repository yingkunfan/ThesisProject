package sriracha.frontend.model.elements.sources;

import sriracha.frontend.*;
import sriracha.frontend.model.*;
import sriracha.frontend.model.elements.*;

import java.io.*;

public class CurrentSource extends TwoPortElement implements Serializable
{
    private transient Property[] properties;

    private float dcCurrent;
    private String dcCurrentUnit;

    private float amplitude;
    private String amplitudeUnit;

    private float frequency;
    private String frequencyUnit;

    private float phase;
    private String phaseUnit;

    private float pulsed;
    private String pulsedUnit;

    private float instTime;
    private String timeUnit;

    public CurrentSource(CircuitElementManager elementManager)
    {
        super(elementManager);
    }

    public float getDcCurrent() { return dcCurrent; }
    public void setDcCurrent(float dcCurrent)
    {
        this.dcCurrent = dcCurrent;
    }
    public String getDcCurrentUnit() { return dcCurrentUnit; }
    public void setDcCurrentUnit(String dcCurrentUnit)
    {
        this.dcCurrentUnit = dcCurrentUnit;
    }

    public float getAmplitude() { return amplitude; }
    public void setAmplitude(float amplitude)
    {
        this.amplitude = amplitude;
    }
    public String getAmplitudeUnit() { return amplitudeUnit; }
    public void setAmplitudeUnit(String amplitudeUnit)
    {
        this.amplitudeUnit = amplitudeUnit;
    }

    public float getFrequency() { return frequency; }
    public void setFrequency(float frequency)
    {
        this.frequency = frequency;
    }
    public String getFrequencyUnit() { return frequencyUnit; }
    public void setFrequencyUnit(String frequencyUnit)
    {
        this.frequencyUnit = frequencyUnit;
    }

    public float getPhase() { return phase; }
    public void setPhase(float phase)
    {
        this.phase = phase;
    }
    public String getPhaseUnit() { return phaseUnit; }
    public void setPhaseUnit(String phaseUnit)
    {
        this.phaseUnit = phaseUnit;
    }

    @Override
    public Property[] getProperties()
    {
        if (properties == null)
        {
            final ScalarProperty dcProp = new ScalarProperty("DC Current", "A")
            {
                @Override
                public String getValue()
                {
                    return dcCurrent == 0 ? "0" : String.valueOf(dcCurrent);
                }
                @Override
                public void _trySetValue(String value)
                {
                    dcCurrent = Float.parseFloat(value);
                }
                @Override
                public String getUnit()
                {
                    return dcCurrentUnit == null || dcCurrentUnit.isEmpty() ? this.getBaseUnit() : dcCurrentUnit;
                }
                @Override
                public void setUnit(String newUnit)
                {
                    dcCurrentUnit = newUnit;
                }
            };

            final ScalarProperty acProp = new ScalarProperty("Amplitude", "A")
            {
                @Override
                public String getValue()
                {
                    return amplitude == 0 ? "" : String.valueOf(amplitude);
                }
                @Override
                public void _trySetValue(String value)
                {
                    if (value.isEmpty())
                        amplitude = 0;
                    else
                        amplitude = Float.parseFloat(value);
                }
                @Override
                public String getUnit()
                {
                    return amplitudeUnit == null || amplitudeUnit.isEmpty() ? this.getBaseUnit() : amplitudeUnit;
                }
                @Override
                public void setUnit(String newUnit)
                {
                    amplitudeUnit = newUnit;
                }
            };

            final ScalarProperty freqProp = new ScalarProperty("Frequency", "Hz")
            {
                @Override
                public String getValue()
                {
                    return frequency == 0 ? "" : String.valueOf(frequency);
                }
                @Override
                public void _trySetValue(String value)
                {
                    if (value.isEmpty())
                        frequency = 0;
                    else
                        frequency = Float.parseFloat(value);
                }
                @Override
                public String getUnit()
                {
                    return frequencyUnit == null || frequencyUnit.isEmpty() ? this.getBaseUnit() : frequencyUnit;
                }
                @Override
                public void setUnit(String newUnit)
                {
                    frequencyUnit = newUnit;
                }
                @Override
                public String[] getUnitsList()
                {
                    return new String[]{"Hz", "ω"};
                }
            };

            final ScalarProperty phaseProp = new ScalarProperty("Phase", "°")
            {
                @Override
                public String getValue()
                {
                    return phase == 0 ? "0" : String.valueOf(phase);
                }
                @Override
                public void _trySetValue(String value)
                {
                    if (value.isEmpty())
                        phase = 0;
                    else
                        phase = Float.parseFloat(value);
                }
                @Override
                public String getUnit()
                {
                    return phaseUnit == null || phaseUnit.isEmpty() ? this.getBaseUnit() : phaseUnit;
                }
                @Override
                public void setUnit(String newUnit)
                {
                    phaseUnit = newUnit;
                }
                @Override
                public String[] getUnitsList()
                {
                    return new String[]{"°", "rad"};
                }
            };

                final ScalarProperty pulseProp = new ScalarProperty("Pulsed Value", "V")
                {
                    @Override
                    public String getValue()
                    {
                        return pulsed == 0 ? "" : String.valueOf(pulsed);
                    }
                    @Override
                    public void _trySetValue(String value)
                    {
                        pulsed = Float.parseFloat(value);
                    }
                    @Override
                    public String getUnit()
                    {
                        return pulsedUnit == null || pulsedUnit.isEmpty() ? this.getBaseUnit() : pulsedUnit;
                    }
                    @Override
                    public void setUnit(String newUnit)
                    {
                        pulsedUnit = newUnit;
                    }
                };

                final ScalarProperty timeProp = new ScalarProperty("Instantaneous Time", "S")
                {
                    @Override
                    public String getValue()
                    {
                        return instTime == 0 ? "" : String.valueOf(instTime);
                    }
                    @Override
                    public void _trySetValue(String value)
                    {
                        instTime = Float.parseFloat(value);
                    }
                    @Override
                    public String getUnit()
                    {
                        return timeUnit == null || timeUnit.isEmpty() ? this.getBaseUnit() : timeUnit;
                    }
                    @Override
                    public void setUnit(String newUnit)
                    {
                        timeUnit = newUnit;
                    }

            };

            properties = new Property[]{dcProp, acProp, freqProp, phaseProp, pulseProp, timeProp};
        }
        return properties;
    }

    @Override
    public String getType()
    {
        return "Current Source";
    }

    @Override
    public String getNameTemplate()
    {
        return "I%d";
    }

    @Override
    public String toNetlistString(String[] nodes, NodeCrawler crawler)
    {
        return super.toNetlistString(nodes, crawler)
                + String.format("DC %f%s AC %f%s %f %f",
                dcCurrent, ScalarProperty.translateUnit(dcCurrentUnit),
                amplitude, ScalarProperty.translateUnit(amplitudeUnit),
                frequency,
                phase);
    }
}
