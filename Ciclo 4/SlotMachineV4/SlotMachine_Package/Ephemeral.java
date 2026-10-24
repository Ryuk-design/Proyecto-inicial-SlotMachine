package SlotMachine_Package;

/**
 * Subclase de simbolo que cada vez que la rueda a la que pertenece gire, este va a disminuir su tamaño hasta quedar como un punto
 *
 * @author (Samuel Ahumada)
 * @version (1.0)
 */
public class Ephemeral extends Symbol
{
    private static final int STEP = 4;
    private static final int MIN_SIZE = 2;
    private int size;

    /**
     * Metodo constructor del simbolo ephemeral
     * @param color asigna color al simbolo 
     * @param x asigna posicion horizontal al simbolo 
     * @param y asigna posicion vertical al simbolo 
     */
    public Ephemeral(String color, int x, int y)
    {
        super(color, new Triangle(SIZE, SIZE, x, y, color));
        size = SIZE;
    }
    
    /**
     * Consulta el tamaño del simbolo ephemeral, en este caso representa la altura y el ancho de la base
     * @return el tamaño del simbolo 
     */
    @Override
    public int getSize()
    {
        return size;
    }

    /**
     * Acciones que realiza cada vez que el simbolo ephemeral es seleccionado por la rueda a la que pertenece 
     */
    @Override
    public void onStop()
    {
        if(size > MIN_SIZE)
        {
            size = size - STEP;

            if(size < MIN_SIZE)
            {
                size = MIN_SIZE;
            }

            ((Triangle) figure).changeSize(size, size);
        }
    }
    
    /**
     * Crea una copia del simbolo ephemeral para que cada rueda tenga su propio objeto
     * @return copia del simbolo ephemeral
     */
    @Override
    public Symbol copy()
    {
        return new Ephemeral(getColor(), 0, 0);
    }
    
    /**
     * Cambia la posicion del simbolo ephemeral
     * @param x posicion horizontal
     * @param y posicion vertical
     */
    @Override
    public void setPosition(int x, int y)
    {
        super.setPosition(x + size / 2, y);
    }
}