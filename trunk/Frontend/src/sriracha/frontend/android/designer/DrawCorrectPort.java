package sriracha.frontend.android.designer;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import sriracha.frontend.android.model.CircuitElementView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: shadiasfour
 * Date: 13-10-12
 * Time: 7:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class DrawCorrectPort extends View {

    private IWireIntersection intersection;
    private ArrayList<CircuitElementView> elements;

    public DrawCorrectPort(Context context, ArrayList<CircuitElementView> elements, IWireIntersection intersection) {

        super(context);

        this.intersection = intersection;
        this.elements = elements;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStrokeWidth(4);

        paint.setColor(Color.RED);

        CircuitElementView myElement = null;

        for(CircuitElementView element : elements) {
            if(element.getElement().getPortCount() == 1) {
                if(element.getPortViews()[0].getY() == intersection.getY() && element.getPortViews()[0].getX() == intersection.getX()) {
                    canvas.drawCircle(intersection.getX(), intersection.getY(), 4, paint);
                    break;
                }
            }

            else if(element.getElement().getPortCount() == 2) {
                if((element.getPortViews()[0].getY() == intersection.getY() && element.getPortViews()[0].getX() == intersection.getX())
                        || (element.getPortViews()[1].getY() == intersection.getY() && element.getPortViews()[1].getX() == intersection.getX())) {
                    canvas.drawCircle(intersection.getX(), intersection.getY(), 4, paint);
                    break;
                }
            }
            else if(element.getElement().getPortCount() == 3) {
                if((element.getPortViews()[0].getY() == intersection.getY() && element.getPortViews()[0].getX() == intersection.getX())
                        || (element.getPortViews()[1].getY() == intersection.getY() && element.getPortViews()[1].getX() == intersection.getX())
                        ||  (element.getPortViews()[2].getY() == intersection.getY() && element.getPortViews()[2].getX() == intersection.getX())) {
                    canvas.drawCircle(intersection.getX(), intersection.getY(), 4, paint);
                    break;
                }
            }
            else if(element.getElement().getPortCount() == 4) {
                if((element.getPortViews()[0].getY() == intersection.getY() && element.getPortViews()[0].getX() == intersection.getX())
                        || (element.getPortViews()[1].getY() == intersection.getY() && element.getPortViews()[1].getX() == intersection.getX())
                        ||  (element.getPortViews()[2].getY() == intersection.getY() && element.getPortViews()[2].getX() == intersection.getX())
                        ||  (element.getPortViews()[3].getY() == intersection.getY() && element.getPortViews()[3].getX() == intersection.getX())) {
                    canvas.drawCircle(intersection.getX(), intersection.getY(), 4, paint);
                    break;
                }
            }
        }
    }
}
