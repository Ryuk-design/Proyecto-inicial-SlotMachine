package SlotMachine_Package;

/**
 * Representa un simbolo individual mostrado por una rueda de la maquina
 * tragamonedas, identificado por un color unico y dibujado como un circulo
 * en el canvas. Permite consultar su color, controlar su visibilidad,
 * cambiar su posicion en pantalla y compararse con otro simbolo para
 * determinar si representan el mismo color.
 * 
 * @author (Samuel Ahumada) 
 * @version (2.0)
 */
public class Symbol
{
    private Circle figure;
    private String color;
    private boolean isVisible;
    public static final int DIAMETER = 20;

    /**
     * Metodo constructor del simbolo
     * @param color asigna color al simbolo
     * @param x asigna posicion horizontal al simbolo
     * @param y asigna posicion vertical al simbolo
     */
    public Symbol(String color, int x, int y)
    {
        this.color = color;
        figure = new Circle(DIAMETER, x, y, this.color);
        isVisible = false;
    }
    
    /**
     * Consulta el color del simbolo
     * @return color del simbolo
     */
    public String getColor()
    {
        return color;
    }
    
    /**
     * Hace visible el simbolo
     */
    public void makeVisible()
    {
        figure.makeVisible();
        isVisible = true;
    }
    

    /**
     * Hace invisible el simbolo
     */
    public void makeInvisible()
    {
        figure.makeInvisible();
        isVisible = false;
    }
    
    /**
     * Compara el color de dos simbolos
     * @param sym2 simbolo a comparar
     * @return true si tienen el mismo color, false si no tienen el mismo color
     */
    public boolean isSameSymbol(Symbol sym2)
    {
        if(color.equals(sym2.getColor()))
        {
            return true;
        }else
        {
            return false;
        }
    }
    
    /**
     * Cambia la posicion horizontal y vertical del simbolo
     * @param x asigna posicion horizontal al simbolo
     * @param y asigna posicion vertical al simbolo
     */
    public void setPosition(int x, int y)
    {
        figure.setPosition(x, y);
    }
    
    /**
     * Consulta el estado de visibilidad del simbolo
     */
    public boolean isVisible()
    {
        return isVisible;
    }
    
    //Nuevos metodos ciclo 4 para el manejo de los simbolos ephemeral y shy
    
    /**
     * Consulta el diametro del simbolo
     */
    public int getDiameter()
    {
        return DIAMETER;
    }
    
    /**
     * Consulta si el simbolo esta escondido
     * las subclases lo sobreescriben a su manera
     * @return false porque el simbolo convencional nunca esta escondido
     */
    public boolean isHidden()
    {
        return false;
    }
    
    /**
     * Aviso que recibe el simbolo cuando un giro de la rueda termina en el
     * Por defecto no hace nada, las subclases lo sobreescriben a su manera
     */
    public void onStop()
    {
    }
    
    /**
     * Crea una copia nueva del simbolo para que cada rueda tenga su propio objeto
     * cada subclase lo sobreescribe
     * @return copia del simbolo
     */
    public Symbol copy()
    {
        return new Symbol(getColor(), 0, 0);
    }
    
    /**
     * Cambia el diametro del circulo con el que se dibuja el simbolo
     * @param diamenter nuevo diametro del circulo
     */
    protected void setDiameter(int diameter)
    {
        figure.changeSize(diameter);
    }
}