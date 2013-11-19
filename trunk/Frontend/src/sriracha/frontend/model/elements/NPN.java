package sriracha.frontend.model.elements;

import sriracha.frontend.NodeCrawler;
import sriracha.frontend.model.CircuitElementManager;
import sriracha.frontend.model.Property;
import sriracha.frontend.model.ScalarProperty;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: shadiasfour
 * Date: 13-11-08
 * Time: 1:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class NPN extends ThreePortElement implements Serializable{
    private transient Property[] properties;

    private float bf = 1;
    private String bfUnit = "μ";

    private float br = 1;
    private String brUnit = "kΩ";

    private float cr = 1;
    private String crUnit = "kΩ";

    private float er = 1;
    private String erUnit = "kΩ";

    public NPN(CircuitElementManager elementManager)
    {
        super(elementManager);
    }

    public Property[] getProperties()
    {
        if (properties == null)
        {
            ScalarProperty betaProp = new ScalarProperty("Forward Beta", "")
            {
                @Override
                public String getValue()
                {
                    return String.valueOf(bf);
                }
                @Override
                public void _trySetValue(String value)
                {
                    float floatValue = Float.parseFloat(value);
                    if (floatValue <= 0)
                        throw new NumberFormatException("Beta value must be greater than zero");
                    bf = floatValue;
                }
                @Override
                public String getUnit()
                {
                    return bfUnit == null || bfUnit.isEmpty() ? this.getBaseUnit() : bfUnit;
                }
                @Override
                public void setUnit(String newUnit)
                {
                    bfUnit = newUnit;
                }
            };
            ScalarProperty brProp =
                    new ScalarProperty("Base Resistance", "kΩ")
                    {
                        @Override
                        public String getValue()
                        {
                            return String.valueOf(br);
                        }
                        @Override
                        public void _trySetValue(String value)
                        {
                            float floatValue = Float.parseFloat(value);
                            if (floatValue <= 0)
                                throw new NumberFormatException("Base resistance value must be greater than zero");
                            br = floatValue;
                        }
                        @Override
                        public String getUnit()
                        {
                            return brUnit == null || brUnit.isEmpty() ? this.getBaseUnit() : brUnit;
                        }
                        @Override
                        public void setUnit(String newUnit)
                        {
                            brUnit = newUnit;
                        }

                    };
            ScalarProperty crProp =
                    new ScalarProperty("Collector Resistance", "kΩ")
                    {
                        @Override
                        public String getValue()
                        {
                            return String.valueOf(cr);
                        }
                        @Override
                        public void _trySetValue(String value)
                        {
                            float floatValue = Float.parseFloat(value);
                            if (floatValue <= 0)
                                throw new NumberFormatException("Collector resistance value must be greater than zero");
                            cr = floatValue;
                        }
                        @Override
                        public String getUnit()
                        {
                            return crUnit == null || crUnit.isEmpty() ? this.getBaseUnit() : crUnit;
                        }
                        @Override
                        public void setUnit(String newUnit)
                        {
                            crUnit = newUnit;
                        }

                    };
            ScalarProperty erProp =
                    new ScalarProperty("Emitter Resistance", "kΩ")
                    {
                        @Override
                        public String getValue()
                        {
                            return String.valueOf(er);
                        }
                        @Override
                        public void _trySetValue(String value)
                        {
                            float floatValue = Float.parseFloat(value);
                            if (floatValue <= 0)
                                throw new NumberFormatException("Emitter resistance value must be greater than zero");
                            er = floatValue;
                        }
                        @Override
                        public String getUnit()
                        {
                            return erUnit == null || erUnit.isEmpty() ? this.getBaseUnit() : erUnit;
                        }
                        @Override
                        public void setUnit(String newUnit)
                        {
                            erUnit = newUnit;
                        }

                    };


            properties = new Property[]{betaProp, brProp, crProp, erProp};

        }
        return properties;
    }

    @Override
    public String getType()
    {
        return "NPN";
    }

    @Override
    public String getNameTemplate()
    {
        return "NPN%d";
    }

    @Override
    public String toNetlistString(String[] nodes, NodeCrawler crawler)
    {
        return super.toNetlistString(nodes, crawler) + bf + ScalarProperty.translateUnit(bfUnit)
                + br + ScalarProperty.translateUnit(brUnit) + cr + ScalarProperty.translateUnit(crUnit)
                + er + ScalarProperty.translateUnit(erUnit);
    }
}
