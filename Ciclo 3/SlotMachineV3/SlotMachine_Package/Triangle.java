package SlotMachine_Package;
import java.awt.*;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * @author  Michael Kolling and David J. Barnes (Modified)
 * @version 2.0
 */
public class Triangle extends Shape
{
    public static int VERTICES = 3;
    private int height;
    private int width;

    /**
     * Create a new triangle at a given position with a given size and color.
     * @author Samuel Ahumada
     * @param height  la altura del triangulo en pixeles.
     * @param width  la anchura del triangulo en pixeles.
     * @param xPosition  coordenada en el eje x del triangulo.
     * @param yPosition  coordenada en el eje y del triangulo.
     * @param color  color del triangulo segun los estandares CSS.
     */
    public Triangle(int height, int width, int xPosition, int yPosition, String color)
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
            int[] xpoints = { xPosition, xPosition + (width / 2), xPosition - (width / 2) };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
            canvas.wait(10);
        }
    }

    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidht must be >=0.
     */
    public void changeSize(int newHeight, int newWidth)
    {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
}