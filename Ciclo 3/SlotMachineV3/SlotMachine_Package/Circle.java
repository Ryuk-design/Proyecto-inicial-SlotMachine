package SlotMachine_Package;
import java.awt.*;
import java.awt.geom.*;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 *
 * @author  Michael Kolling and David J. Barnes (Modified)
 * @version 2.0
 */
public class Circle extends Shape
{
    public static final double PI = 3.1416;
    private int diameter;

    /**
     * Metodo costructor del circulo
     * @author Samuel Ahumada
     * @param diameter  el diametro del circulo en pixeles.
     * @param xPosition  la posicion en el ejex del circulo.
     * @param yPosition  la posicion en el ejey del circulo.
     * @param color  el color del circulo segun el estandar CSS.
     */
    public Circle(int diameter, int xPosition, int yPosition, String color)
    {
        super(xPosition, yPosition, color);
        this.diameter = diameter;
    }

    protected void draw()
    {
        if(isVisible)
        {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new Ellipse2D.Double(xPosition, yPosition,
                diameter, diameter));
            canvas.wait(10);
        }
    }

    /**
     * Change the size.
     * @param newDiameter the new size (in pixels). Size must be >=0.
     */
    public void changeSize(int newDiameter)
    {
        erase();
        diameter = newDiameter;
        draw();
    }
}