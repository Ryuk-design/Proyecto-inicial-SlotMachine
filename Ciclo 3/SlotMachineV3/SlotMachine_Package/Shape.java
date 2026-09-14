package SlotMachine_Package;

/**
 * Clase padre para las figuras que se pueden dibujar en el Canvas
 * Centraliza el estado y comportamiento a todas las figuras: posicion, color, visibilidad, movimiento y eldibujo y borrado sobre el Canvas.
 * @author (Samuel Ahumada - Nerieth Villota)
 * @version (1.0)
 */
public class Shape
{
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;

    /**
     * Construye una figura en una posicion y color determinados.
     * @param xPosition posicion en el eje x de la figura
     * @param yPosition posicion en el eje y de la figura
     * @param color color de la figura segun el estandar CSS
     */
    public Shape(int xPosition, int yPosition, String color)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.color = color;
        isVisible = false;
    }

    /**
     * Dibuja la figura en el Canvas con las especificaciones actuales
     * Cada figura concreta define su propia forma geometrica
     */
    protected void draw(){}

    /**
     * Borra la figura del Canvas si estaba visible
     */
    protected void erase()
    {
        if(isVisible)
        {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    /**
     * Hace visible la figura
     */
    public void makeVisible()
    {
        isVisible = true;
        draw();
    }

    /**
     * Hace invisible la figura
     */
    public void makeInvisible()
    {
        erase();
        isVisible = false;
    }

    /**
     * Cambia el color de la figura
     * @param newColor el nuevo color
     */
    public void changeColor(String newColor)
    {
        color = newColor;
        draw();
    }

    /**
     * Cambia la posicion de la figura logica y visualmente
     * @param x posicion horizontal
     * @param y posicion vertical
     */
    public void setPosition(int x, int y)
    {
        erase();
        xPosition = x;
        yPosition = y;
        draw();
    }
}