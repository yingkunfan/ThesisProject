package sriracha.frontend.model.elements;

import sriracha.frontend.NodeCrawler;
import sriracha.frontend.model.CircuitElementManager;
import sriracha.frontend.model.Property;
import sriracha.frontend.model.ScalarProperty;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: shadiasfour
 * Date: 13-11-13
 * Time: 12:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class PMosfet extends FourPortElement implements Serializable {

    private transient Property[] properties;

    private float transconductance = 1;
    private String tranUnit = "μA/V^2";

    private float bulkThreshold = 1;
    private String bulkUnit = "μV^0.5";

    private float surfacePotential = 1;
    private String sPUnit = "μV";

    private float channelLength = 1;
    private String cLUnit = "μV^-1";

    private float drainResistance = 1;
    private String dRUnit = "μΩ";

    private float sourceResistance = 1;
    private String sRUnit = "μΩ";

    private float rsh = 1;
    private String rshUnit = "μΩ";

    private float cbd = 1;
    private String cbdUnit = "μF";

    private float cbs = 1;
    private String cbsUnit = "μF";

    private float thresholdVoltage = 1;
    private String vTUnit = "μV";

    public PMosfet(CircuitElementManager elementManager)
    {
        super(elementManager);
    }

    public Property[] getProperties()
    {
        if (properties == null)
        {
            ScalarProperty transconductanceProp = new ScalarProperty("Transconductance", "A/V^2")
            {
                @Override
                public String getValue()
                {
                    return String.valueOf(transconductance);
                }
                @Override
                public void _trySetValue(String value)
                {
                    float floatValue = Float.parseFloat(value);
                    if (floatValue <= 0)
                        throw new NumberFormatException("Transconductance value must be greater than zero");
                    transconductance = floatValue;
                }
                @Override
                public String getUnit()
                {
                    return tranUnit == null || tranUnit.isEmpty() ? this.getBaseUnit() : tranUnit;
                }
                @Override
                public void setUnit(String newUnit)
                {
                    tranUnit = newUnit;
                }
            };
            ScalarProperty gammaProp =
                    new ScalarProperty("Bulk Threshold", "V^0.5")
                    {
                        @Override
                        public String getValue()
                        {
                            return String.valueOf(bulkThreshold);
                        }
                        @Override
                        public void _trySetValue(String value)
                        {
                            float floatValue = Float.parseFloat(value);
                            if (floatValue <= 0)
                                throw new NumberFormatException("Bulk Threshold value must be greater than zero");
                            bulkThreshold = floatValue;
                        }
                        @Override
                        public String getUnit()
                        {
                            return bulkUnit == null || bulkUnit.isEmpty() ? this.getBaseUnit() : bulkUnit;
                        }
                        @Override
                        public void setUnit(String newUnit)
                        {
                            bulkUnit = newUnit;
                        }

                    };
            ScalarProperty surfacePotentialProp =
                    new ScalarProperty("Surface Potential", "V")
                    {
                        @Override
                        public String getValue()
                        {
                            return String.valueOf(surfacePotential);
                        }
                        @Override
                        public void _trySetValue(String value)
                        {
                            float floatValue = Float.parseFloat(value);
                            if (floatValue <= 0)
                                throw new NumberFormatException("Surface Potential value must be greater than zero");
                            surfacePotential = floatValue;
                        }
                        @Override
                        public String getUnit()
                        {
                            return sPUnit == null || sPUnit.isEmpty() ? this.getBaseUnit() : sPUnit;
                        }
                        @Override
                        public void setUnit(String newUnit)
                        {
                            sPUnit = newUnit;
                        }

                    };
            ScalarProperty channelLengthProp =
                    new ScalarProperty("Channel Length", "V^-1")
                    {
                        @Override
                        public String getValue()
                        {
                            return String.valueOf(channelLength);
                        }
                        @Override
                        public void _trySetValue(String value)
                        {
                            float floatValue = Float.parseFloat(value);
                            if (floatValue <= 0)
                                throw new NumberFormatException("Channel Length value must be greater than zero");
                            channelLength = floatValue;
                        }
                        @Override
                        public String getUnit()
                        {
                            return cLUnit == null || cLUnit.isEmpty() ? this.getBaseUnit() : cLUnit;
                        }
                        @Override
                        public void setUnit(String newUnit)
                        {
                            cLUnit = newUnit;
                        }

                    };

            ScalarProperty drainResistanceProp =
                    new ScalarProperty("Drain Resistance", "Ω")
                    {
                        @Override
                        public String getValue()
                        {
                            return String.valueOf(drainResistance);
                        }
                        @Override
                        public void _trySetValue(String value)
                        {
                            float floatValue = Float.parseFloat(value);
                            if (floatValue <= 0)
                                throw new NumberFormatException("Drain Resistance value must be greater than zero");
                            drainResistance = floatValue;
                        }
                        @Override
                        public String getUnit()
                        {
                            return dRUnit == null || dRUnit.isEmpty() ? this.getBaseUnit() : dRUnit;
                        }
                        @Override
                        public void setUnit(String newUnit)
                        {
                            dRUnit = newUnit;
                        }

                    };
            ScalarProperty sourceResistanceProp =
                    new ScalarProperty("Source Resistance", "Ω")
                    {
                        @Override
                        public String getValue()
                        {
                            return String.valueOf(sourceResistance);
                        }
                        @Override
                        public void _trySetValue(String value)
                        {
                            float floatValue = Float.parseFloat(value);
                            if (floatValue <= 0)
                                throw new NumberFormatException("Source Resistance value must be greater than zero");
                            sourceResistance = floatValue;
                        }
                        @Override
                        public String getUnit()
                        {
                            return sRUnit == null || sRUnit.isEmpty() ? this.getBaseUnit() : sRUnit;
                        }
                        @Override
                        public void setUnit(String newUnit)
                        {
                            sRUnit = newUnit;
                        }

                    };

            ScalarProperty vTProp = new ScalarProperty("Threshold Voltage", "V")
            {
                @Override
                public String getValue()
                {
                    return String.valueOf(thresholdVoltage);
                }
                @Override
                public void _trySetValue(String value)
                {
                    float floatValue = Float.parseFloat(value);
                    if (floatValue <= 0)
                        throw new NumberFormatException("Threshold Voltage value must be greater than zero");
                    thresholdVoltage = floatValue;
                }
                @Override
                public String getUnit()
                {
                    return vTUnit == null || vTUnit.isEmpty() ? this.getBaseUnit() : vTUnit;
                }
                @Override
                public void setUnit(String newUnit)
                {
                    vTUnit = newUnit;
                }

            };

            ScalarProperty rshProp = new ScalarProperty("Sheet Resistance", "Ω")
            {
                @Override
                public String getValue()
                {
                    return String.valueOf(rsh);
                }
                @Override
                public void _trySetValue(String value)
                {
                    float floatValue = Float.parseFloat(value);
                    if (floatValue <= 0)
                        throw new NumberFormatException("Sheet Resistance value must be greater than zero");
                    rsh = floatValue;
                }
                @Override
                public String getUnit()
                {
                    return rshUnit == null || rshUnit.isEmpty() ? this.getBaseUnit() : rshUnit;
                }
                @Override
                public void setUnit(String newUnit)
                {
                    rshUnit = newUnit;
                }

            };

            ScalarProperty cbdProp = new ScalarProperty("Drain-Bulk Capacitance", "F")
            {
                @Override
                public String getValue()
                {
                    return String.valueOf(cbd);
                }
                @Override
                public void _trySetValue(String value)
                {
                    float floatValue = Float.parseFloat(value);
                    if (floatValue <= 0)
                        throw new NumberFormatException("Drain-Bulk Capacitance value must be greater than zero");
                    cbd = floatValue;
                }
                @Override
                public String getUnit()
                {
                    return cbdUnit == null || cbdUnit.isEmpty() ? this.getBaseUnit() : cbdUnit;
                }
                @Override
                public void setUnit(String newUnit)
                {
                    cbdUnit = newUnit;
                }

            };

            ScalarProperty cbsProp = new ScalarProperty("Source-Bulk Capacitance", "F")
            {
                @Override
                public String getValue()
                {
                    return String.valueOf(cbs);
                }
                @Override
                public void _trySetValue(String value)
                {
                    float floatValue = Float.parseFloat(value);
                    if (floatValue <= 0)
                        throw new NumberFormatException("Source-Bulk Capacitance value must be greater than zero");
                    cbs = floatValue;
                }
                @Override
                public String getUnit()
                {
                    return cbsUnit == null || cbsUnit.isEmpty() ? this.getBaseUnit() : cbsUnit;
                }
                @Override
                public void setUnit(String newUnit)
                {
                    cbsUnit = newUnit;
                }

            };

            properties = new Property[]{vTProp, transconductanceProp, gammaProp, surfacePotentialProp, channelLengthProp,
                    drainResistanceProp, sourceResistanceProp, rshProp, cbdProp, cbsProp};

        }
        return properties;
    }

    @Override
    public String getType()
    {
        return "PMosfet";
    }

    @Override
    public String getNameTemplate()
    {
        return "PMosfet%d";
    }

    @Override
    public String toNetlistString(String[] nodes, NodeCrawler crawler)
    {
        return super.toNetlistString(nodes, crawler) + thresholdVoltage + ScalarProperty.translateUnit(vTUnit)
                + transconductance + ScalarProperty.translateUnit(tranUnit) + bulkThreshold + ScalarProperty.translateUnit(bulkUnit)
                + surfacePotential + ScalarProperty.translateUnit(sPUnit) + channelLength + ScalarProperty.translateUnit(cLUnit)
                + drainResistance + ScalarProperty.translateUnit(dRUnit) + sourceResistance + ScalarProperty.translateUnit(sRUnit)
                + rsh + ScalarProperty.translateUnit(rshUnit) + cbd + ScalarProperty.translateUnit(cbdUnit)
                + cbs + ScalarProperty.translateUnit(cbsUnit);
    }
}
