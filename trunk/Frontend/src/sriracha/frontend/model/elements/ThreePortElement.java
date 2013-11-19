package sriracha.frontend.model.elements;

import sriracha.frontend.model.*;

/**
 * Created with IntelliJ IDEA.
 * User: shadiasfour
 * Date: 13-11-07
 * Time: 5:19 PM
 *
 */
abstract public class ThreePortElement extends CircuitElement {

    public ThreePortElement(CircuitElementManager elementManager)
    {
        super(elementManager);
    }

    public int getPortCount() { return 3; }
}
