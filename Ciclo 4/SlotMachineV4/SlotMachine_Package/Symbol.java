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
    protected Shape figure;
    private String color;
    private boolean isVisible;
    public static final int SIZE = 20;

    /**
     * Metodo constructor del simbolo
     * @param color asigna color al simbolo
     * @param x asigna posicion horizontal al simbolo
     * @param y asigna posicion vertical al simbolo
     */
    public Symbol(String color, int x, int y)
    {
        this.color = color;
        figure = new Circle(SIZE, x, y, this.color);
        isVisible = false;
    }
    
    /**
     * Metodo constructor del simbolo, lo usan las subclases para elegir la figura con la que se dibuja
     * @param color asigna color al simbolo
     * @param figure figura con la que se dibuja el simbolo (Rectangle, Circle, Triangle)
     */
    protected Symbol(String color, Shape figure)
    {
        this.color = color;
        this.figure = figure;
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
     * @return true si es visible, false si no es visible
     */
    public boolean isVisible()
    {
        return isVisible;
    }
    
    //Nuevos metodos ciclo 4 para el manejo de los simbolos ephemeral y shy
    
    /**
     * Consulta el tamaño del simbolo, el simbolo convencional se dibuja como un circulo, asi que el tamaño representa el diametro
     * @return el tamaño del simbolo
     */
    public int getSize()
    {
        return SIZE;
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
}