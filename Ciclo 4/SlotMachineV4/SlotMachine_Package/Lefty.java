package SlotMachine_Package;

public class Lefty extends Wheel
{
    private Wheel leftNeighbor; 
    private static final String COLOR = "blue";

    /**
     * Metodo constructor de una rueda lefty sin una rueda vecina a la izquierda
     * @param x posicion horizontal de la rueda
     * @param y posicion vertical de la rueda
     */
    public Lefty(int x, int y)
    {
        super(x, y, COLOR);
        leftNeighbor = null;
    }
    
    /**
     * Guarda la rueda que esta a la izquierda para copiarla cunado la rueda lefty gire
     * @param left rueda de la izquierda, o null si no hay
     */
    @Override
    public void setLeftNeighbor(Wheel left)
    {
        this.leftNeighbor = left;
    }

    /**
     * Gira la rueda, si tiene una rueda vecina a la izquierda, copia su simbolo
     * sin vecina gira al azar como una rueda normal
     */
    @Override
    public void spin()
    {
        if(leftNeighbor == null)
        {
            super.spin();
        }
        else
        {
            copyLeft();
        }
    }
    
    /**
     * Gira la rueda una cantidad de pasos, si tiene una rueda vecina a la izquierda, copia su simbolo
     * si no tiene vecina gira la cantidad de pasos indicados
     * @param steps cantidad de pasos
     */
    @Override
    public void spin(int steps)
    {
        if(leftNeighbor == null)
        {
            super.spin(steps);
        }
        else
        {
            copyLeft();
        }
    }
    
    /**
     * Muestra el mismo simbolo que esta mostrando la rueda de la izquierda
     */
    private void copyLeft()
    {
        if(leftNeighbor.symbolsSize() == 0)
        {
            return;
        }

        placeSymbol(leftNeighbor.getCurrentSymbol().getColor());
        getCurrentSymbol().onStop();
        updateSymbol();
    }
}