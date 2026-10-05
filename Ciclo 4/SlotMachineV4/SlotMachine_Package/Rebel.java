package SlotMachine_Package;

public class Rebel extends Wheel
{
    private static final String COLOR = "black";

    /**
     * Metodo constructor de una rueda rebel
     * @param x posicion horizontal de la rueda
     * @param y posicion vertical de la rueda
     */
    public Rebel(int x, int y)
    {
        super(x, y, COLOR);
    }
    
    /**
     * Una rueda rebel no se deja bloquear
     * @return false siempre
     */
    @Override
    public boolean canLock()
    {
        return false;
    }

    /**
     * Una rueda rebelde no se deja intercambiar
     * @return false siempre
     */
    @Override
    public boolean canSwap()
    {
        return false;
    }

    /**
     * Una rueda rebelde no se deja borrar
     * @return false siempre
     */
    @Override
    public boolean canDelete()
    {
        return false;
    }
}