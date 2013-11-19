package sriracha.frontend.model.elements;

import sriracha.frontend.model.CircuitElement;
import sriracha.frontend.model.CircuitElementManager;

/**
 * Created with IntelliJ IDEA.
 * User: shadiasfour
 * Date: 13-11-13
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class FourPortElement extends CircuitElement {

    public FourPortElement(CircuitElementManager elementManager)
    {
        super(elementManager);
    }

    public int getPortCount() { return 4; }
}
