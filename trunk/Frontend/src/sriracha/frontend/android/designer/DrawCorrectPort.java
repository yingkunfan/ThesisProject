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
            if(element.getElement().getPortCount() != 1) {
                if((element.getPortViews()[0].getY() == intersection.getY() && element.getPortViews()[0].getX() == intersection.getX())
                        || (element.getPortViews()[1].getY() == intersection.getY() && element.getPortViews()[1].getX() == intersection.getX())) {
                    myElement = element;  //mark the element that was just clicked on
                    break;
                }
            }
        }
        for(CircuitElementView element: elements) {
            if(element != myElement && myElement != null) { // because we dont want to draw a circle on the the element clicked on
                int[] correctPort = returnCorrectPort(element, myElement, intersection);
                canvas.drawCircle(correctPort[0], correctPort[1], 4, paint);
            }
        }
    }

    // The following method returns the port where the red circle should be drawn based on where the user last clicked
    // and other elements' positions
    private int[] returnCorrectPort(CircuitElementView element, CircuitElementView myElement, IWireIntersection intersection) {
      //  String temp = element.getElement().getName();
        int[] correctPort = new int[2];
        if(element.getElement().getPortCount() != 1) {

            if(element.getOrientation() % 180 == 0) {
                if(intersection.getY() == myElement.getPortViews()[0].getY()) {
                    correctPort[0] = element.getPortViews()[0].getX();
                    correctPort[1] = element.getPortViews()[0].getY();

                }

                else {
                    correctPort[0] = element.getPortViews()[1].getX();
                    correctPort[1] = element.getPortViews()[1].getY();

               }
            }
            else if(element.getOrientation() == 90) {
                if(intersection.getY() == myElement.getPortViews()[0].getY()) {
                    if((element.getPortViews()[1].getX() <= intersection.getX() && element.getPortViews()[1].getY() <= myElement.getPortViews()[1].getY())
                            || (element.getPortViews()[1].getX() > intersection.getX() && element.getPortViews()[1].getY() > myElement.getPortViews()[1].getY()))  {
                        correctPort[0] = element.getPortViews()[0].getX();
                        correctPort[1] = element.getPortViews()[0].getY();
                    } else {
                        correctPort[0] = element.getPortViews()[1].getX();
                        correctPort[1] = element.getPortViews()[1].getY();
                    }
                }
                else {
                    if((element.getPortViews()[1].getX() <= intersection.getX() && element.getPortViews()[1].getY() <= myElement.getPortViews()[1].getY())
                            || (element.getPortViews()[1].getX() > intersection.getX() && element.getPortViews()[1].getY() > myElement.getPortViews()[1].getY()))  {
                        correctPort[0] = element.getPortViews()[1].getX();
                        correctPort[1] = element.getPortViews()[1].getY();
                    } else {
                        correctPort[0] = element.getPortViews()[0].getX();
                        correctPort[1] = element.getPortViews()[0].getY();
                    }
                }
            } else if(element.getOrientation() == 270) {
                if(intersection.getY() == myElement.getPortViews()[0].getY()) {
                    if((element.getPortViews()[0].getX() <= intersection.getX() && element.getPortViews()[0].getY() <= myElement.getPortViews()[1].getY())
                            || (element.getPortViews()[0].getX() > intersection.getX() && element.getPortViews()[0].getY() > myElement.getPortViews()[1].getY()))  {
                        correctPort[0] = element.getPortViews()[1].getX();
                        correctPort[1] = element.getPortViews()[1].getY();
                    } else {
                        correctPort[0] = element.getPortViews()[0].getX();
                        correctPort[1] = element.getPortViews()[0].getY();
                    }
                }
                else {
                    if((element.getPortViews()[0].getX() <= intersection.getX() && element.getPortViews()[0].getY() <= myElement.getPortViews()[1].getY())
                            || (element.getPortViews()[0].getX() > intersection.getX() && element.getPortViews()[0].getY() > myElement.getPortViews()[1].getY()))  {
                        correctPort[0] = element.getPortViews()[0].getX();
                        correctPort[1] = element.getPortViews()[0].getY();
                    } else {
                        correctPort[0] = element.getPortViews()[1].getX();
                        correctPort[1] = element.getPortViews()[1].getY();
                    }
                }
            }


            if((myElement.getElement().getName().charAt(0) == 'V' || myElement.getElement().getName().charAt(0) == 'E') ||
                    (element.getElement().getName().charAt(0) == 'V' || element.getElement().getName().charAt(0) == 'E')) {
                if(correctPort[0] == element.getPortViews()[0].getX() && correctPort[1] == element.getPortViews()[0].getY()) {
                    correctPort[0] = element.getPortViews()[1].getX();
                    correctPort[1] = element.getPortViews()[1].getY();
                } else {
                    correctPort[0] = element.getPortViews()[0].getX();
                    correctPort[1] = element.getPortViews()[0].getY();
                }

            }

        }

        else {
            correctPort[0] = element.getPortViews()[0].getX();
            correctPort[1] = element.getPortViews()[0].getY();
        }

        return correctPort;
    }



}
