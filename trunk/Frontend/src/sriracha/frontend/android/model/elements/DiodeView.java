package sriracha.frontend.android.model.elements;

import android.content.Context;
import sriracha.frontend.R;
import sriracha.frontend.android.ElementTypeUUID;
import sriracha.frontend.android.designer.WireManager;
import sriracha.frontend.android.model.CircuitElementPortView;
import sriracha.frontend.android.model.CircuitElementView;
import sriracha.frontend.model.CircuitElement;

import java.util.UUID;

public class DiodeView extends CircuitElementView
{
    CircuitElementPortView ports[];

    public DiodeView(Context context, CircuitElement element, float positionX, float positionY, WireManager wireManager)
    {
        super(context, element, positionX, positionY, wireManager);
    }

    @Override
    public int getDrawableId()
    {
        return R.drawable.dnt_diode;
    }

    @Override
    public CircuitElementPortView[] getPortViews()
    {
        if (ports == null)
        {
            ports = new CircuitElementPortView[]{
                    new CircuitElementPortView(this, 0.5f, 0),
                    new CircuitElementPortView(this, -0.5f, 0),
            };
        }
        return ports;
    }

    @Override
    public UUID getTypeUUID()
    {
        return ElementTypeUUID.INDUCTOR;
    }
}
