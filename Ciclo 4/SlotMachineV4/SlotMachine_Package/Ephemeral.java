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
    private static final int MIN_DIAMETER = 2;
    private int diameter;

    /**
     * Metodo constructor del simbolo ephemeral
     * @param color asigna color al simbolo 
     * @param x asigna posicion horizontal al simbolo 
     * @param y asigna posicion vertical al simbolo 
     */
    public Ephemeral(String color, int x, int y)
    {
        super(color, x, y);
        diameter = DIAMETER;
    }
    
    /**
     * Consulta el diametro del simbolo ephemeral
     */
    @Override
    public int getDiameter()
    {
        return diameter;
    }

    @Override
    /**
     * Acciones que realiza cada vez que el simbolo ephemeral es seleccionado por la rueda a la que pertenece 
     */
    public void onStop()
    {
        if(diameter > MIN_DIAMETER)
        {
            diameter = diameter - STEP;

            if(diameter < MIN_DIAMETER)
            {
                diameter = MIN_DIAMETER;
            }

            setDiameter(diameter);
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
}