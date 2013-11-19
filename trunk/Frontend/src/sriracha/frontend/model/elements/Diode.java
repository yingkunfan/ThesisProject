package sriracha.frontend.model.elements;

import sriracha.frontend.model.ScalarProperty;
import sriracha.frontend.*;
import sriracha.frontend.model.*;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: shadiasfour
 * Date: 13-11-08
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Diode extends TwoPortElement implements Serializable{
    private transient Property[] properties;

    public Diode(CircuitElementManager elementManager)
    {
        super(elementManager);
    }

    public Property[] getProperties()
    {
        return new Property[0];
    }

    @Override
    public String getType()
    {
        return "Diode";
    }

    @Override
    public String getNameTemplate()
    {
        return "D%d";
    }

    @Override
    public String toNetlistString(String[] nodes, NodeCrawler crawler)
    {
        return super.toNetlistString(nodes, crawler) /*+ resistance + ScalarProperty.translateUnit(unit)*/;
    }
}
