package SlotMachine_Package;

/**
 * Subclase de simbolo que cada vez que este sea seleccionado por la rueda a la que pertenece, va a alternar su estado de visibilidad
 * 
 * @author (Samuel Ahumada) 
 * @version (1.0)
 */

public class Shy extends Symbol
{
    private boolean hidden;
    private static final int SIDE = 15;

    /**
     * Metodo constructor del simbolo shy
     * @param color asigna color al simbolo 
     * @param x asigna posicion horizontal al simbolo 
     * @param y asigna posicion vertical al simbolo 
     */
    public Shy(String color, int x, int y)
    {
        super(color, new Rectangle(SIDE, SIDE, x, y, color));
        hidden = false;
    }

    /**
     * Consulta si el simbolo shy esta escondido
     * @return true si esta escondido, false si no esta escondido
     */
    @Override
    public boolean isHidden()
    {
        return hidden;
    }

    /**
     * Accion que realiza cada vez que el simbolo shy es seleccionado por la rueda a la que pertenece 
     */
    @Override
    public void onStop()
    {
        hidden = !hidden;
    }

    /**
     * Hace visible al simbolo shy si esta escondido
     */
    @Override
    public void makeVisible()
    {
        if(!hidden)
        {
            super.makeVisible();
        }
    }

    /**
     * Crea una copia del simbolo shy para que cada rueda tenga su propio objeto
     * @return copia del simbolo shy
     */
    @Override
    public Symbol copy()
    {
        return new Shy(getColor(), 0, 0);
    }
    
    @Override
    public int getSize()
    {
        return SIDE;
    }
}