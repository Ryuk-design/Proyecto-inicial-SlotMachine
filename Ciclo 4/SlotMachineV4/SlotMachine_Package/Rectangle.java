package SlotMachine_Package;
import java.awt.*;

/**
 * A rectangle that can be manipulated and that draws itself on a canvas.
 *
 * @author  Michael Kolling and David J. Barnes (Modified)
 * @version 2.0
 */
public class Rectangle extends Shape
{
    public static int EDGES = 4;
    private int height;
    private int width;

    /**
     * Create a new rectangle at a given position with a given size and color.
     * @author Samuel Ahumada.
     * @param height  la altura del rectangulo en pixeles.
     * @param width  la anchura del rectangulo en pixeles.
     * @param xPosition  coordenada en el eje x del rectangulo.
     * @param yPosition  coordenada en el eje y del rectangulo.
     * @param color  color del rectangulo segun los estandares CSS.
     */
    public Rectangle(int height, int width, int xPosition, int yPosition, String color)
    {
        super(xPosition, yPosition, color);
        this.height = height;
        this.width = width;
    }

    protected void draw()
    {
        if(isVisible)
        {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition,
                                       width, height));
            canvas.wait(10);
        }
    }

    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidth must be >=0.
     */
    public void changeSize(int newHeight, int newWidth)
    {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
}