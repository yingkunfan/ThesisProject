package sriracha.frontend.android.model.elements;

import android.content.Context;
import sriracha.frontend.R;
import sriracha.frontend.android.ElementTypeUUID;
import sriracha.frontend.android.designer.WireManager;
import sriracha.frontend.android.model.CircuitElementPortView;
import sriracha.frontend.android.model.CircuitElementView;
import sriracha.frontend.model.CircuitElement;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: shadiasfour
 * Date: 13-11-13
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class NMosfetView extends CircuitElementView {

    CircuitElementPortView ports[];

    public NMosfetView(Context context, CircuitElement element, float positionX, float positionY, WireManager wireManager)
    {
        super(context, element, positionX, positionY, wireManager);
    }

    @Override
    public int getDrawableId()
    {
        return R.drawable.mosfet_n;
    }

    @Override
    public CircuitElementPortView[] getPortViews()
    {
        if (ports == null)
        {
            ports = new CircuitElementPortView[]{
                    new CircuitElementPortView(this, -0.5f, 0),
                    new CircuitElementPortView(this, 0.5f, 0),
                    new CircuitElementPortView(this, 0, -0.5f),
                    new CircuitElementPortView(this, 0, 0.5f)
            };
        }
        return ports;
    }

    @Override
    public UUID getTypeUUID()
    {
        return ElementTypeUUID.NMOSFET;
    }
}
