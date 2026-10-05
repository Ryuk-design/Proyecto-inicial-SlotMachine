package SlotMachine_Package;
import java.util.ArrayList;
import java.util.Random;

/**
 * Representa una rueda (Wheel) de la maquina tragamonedas (SlotMachine)
 * contiene una lista de simbolos disponibles para mostrar y un indice
 * que señala cual de ellos esta mostrando actualmente.
 * permite agregar y remover simbolos
 * forzar la visualizacion de un simbolo en especifico
 * girar aleatoriamente o girar un numero de pasos una rueda
 * permite bloquear y desbloquear una rueda
 * 
 * @version (4.0)
 */
public class Wheel
{
    private ArrayList<Symbol> symbols;
    private int index;
    private Rectangle window;
    private boolean isVisible;
    private int xPosition;
    private int yPosition;
    public static final int WINDOW_WIDTH = 25; 
    public static final int WINDOW_HEIGHT = 45; 
    private static final String COLOR = "red";
    // ciclo 2
    private boolean locked = false;

    /**
     * Metodo constructor de una rueda
     * @param x posicion horizontal de la rueda
     * @param y posicion vertical de la rueda
     */
    public Wheel(int x, int y)
    {
        this(x, y, COLOR);
    }
    
    /**
     * Metodo constructor sobrecargado, es protegido porque tiene el proposito de ser usado por las ruedas que lo heredan para poder distinguirlas claramente
     * @param x posicion horizontal de la rueda
     * @param y posicion vertical de la rueda
     * @param color color asignado a la rueda
     */
    protected Wheel(int x, int y, String color)
    {
        symbols = new ArrayList<Symbol>();
        window = new Rectangle(WINDOW_HEIGHT, WINDOW_WIDTH, x, y, color);
        index = 0;
        xPosition = x;
        yPosition = y;
        isVisible = false;
    }
    
    /**
     * Actualiza la posicion y visibilidad de el Symbol de la posicion index actual para que quede centrado en Wheel
     */
    private void updateSymbol()
    {
        if(symbols.size() == 0)
        {
            return;
        }
        
        for(int i = 0; i < symbols.size(); i++)
        {
            symbols.get(i).makeInvisible();
        }
        
        int circleX = xPosition + (WINDOW_WIDTH - Symbol.DIAMETER) / 2;
        int circleY = yPosition + (WINDOW_HEIGHT - Symbol.DIAMETER) / 2;
        Symbol currSym = symbols.get(index);
        currSym.setPosition(circleX, circleY);
        
        if(isVisible) 
        {
            currSym.makeVisible();
        }
    }
    
    /**
     * Agrega un simbolo convencional a la rueda
     * @param pos posicion donde se quiere insertar el simbolo
     * @param color color del simbolo
     * @return true si la operacion es exitosa
     */
    public boolean addSymbol(int pos, String color){
        return addSymbol(pos, new Symbol(color, 0, 0));
    }
    
    // Nuevo addSymbol CICLO 4 para el manejo de los simbolos shy y ephemeral
    /**
     * Agrega un simbolo a la rueda en una posicion especifica
     * Agrega un simbolo ya creado a la rueda en una posicion especifica
     * @param pos posicion donde se quiere insertar el simbolo
     * @param sym simbolo a insertar
     * @return true si la operacion es exitosa
     */
    public boolean addSymbol(int pos, Symbol sym)
    {
        if(pos < 1)
        {
            pos = 1;
        } 
        else if(pos > symbols.size() + 1)
        {
            pos = symbols.size() + 1;
        }
        
        boolean empty = (symbols.size() == 0);
        symbols.add(pos - 1, sym);
        
        if(!empty && pos - 1 <= index)
        { 
            index++; 
        }
        
        updateSymbol();
        return true;
    }

    /**
     * Remueve un Symbol de symbols, reacomodando symbols e index y haciendo los ajustes visuales correspondientes.
     * @param color recibe el color de el Symbol a eliminar
     * @return true si la operacion es exitosa, de lo contrario false
     */
    public boolean removeSymbol(String color)
    {
        for(int i = 0; i < symbols.size(); i++)
        {
            if(color.equals(symbols.get(i).getColor()))
            {
                symbols.remove(i);
                
                if(i < index)
                {
                    index--;
                }
                
                if(index >= symbols.size() && symbols.size() > 0){
                    index = symbols.size() - 1;
                }
                
                updateSymbol();
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Selecciona un Symbol seleccionado por el usuario para ser mostrado por Wheel
     * @param color recibe el color de el Symbol a ser mostrado
     * @return true si la operacion es exitosa, de lo contrario false
     */
    public boolean placeSymbol(String color)
    {
        for(int i = 0; i < symbols.size(); i++)
        {
            if(color.equals(symbols.get(i).getColor()))
            {
                index = i;
                updateSymbol();
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Cambia aleatoriamente el simbolo que muestra Wheel
     */
    public void spin()
    {
        if(symbols.size() == 0)
        {
            return;
        }
        
        Random random = new Random();
        index = random.nextInt(symbols.size());
        symbols.get(index).onStop();
        updateSymbol();
    }
    
    /**
     * Consulta el simbolo actual que muestra la rueda
     * @return retorna el Symbol dentro de symbols en la posicion index
     */
    public Symbol getCurrentSymbol()
    {
        return symbols.get(index);
    }
    
    /**
     * Cambia la posicion de la rueda y reacomoda el simbolo actual de acuerdo a la nueva posicion
     * @param x posicion en x donde se colocara la rueda
     * @param y posicion en y donde se colocara la rueda
     */
    public void setPosition(int x, int y)
    {
        window.setPosition(x, y);
        xPosition = x;
        yPosition = y;
        updateSymbol();
    }
    
    /**
     * Hace visible la rueda y el simbolo al que apunta
     */
    public void makeVisible()
    {
        this.isVisible = true;
        window.makeVisible();
        updateSymbol();
    }
    
    /**
     * Hace invisible la rueda y el simbolo al que apunta
     */
    public void makeInvisible()
    {
        this.isVisible = false;
        window.makeInvisible();
         
        if(symbols.size() > 0)
        {
            Symbol currSym = getCurrentSymbol();
            currSym.makeInvisible();
        }
    }
    // CICLO 2
    /**
     * Consulta el estado de bloqueo de la rueda
     * @return true si esta bloqueada o false si no bloqueada
     */
    public boolean getLocked()
    {
        return locked;
    }
    
    /**
     * Cambia el estado de bloqueo de la rueda
     * @param status true si se quiere bloquear la rueda, false si se quiere desbloquear la rueda
     */
    public void setLocked(boolean status)
    {
        locked = status;
    }
    
    /**
     * Gira la rueda una determinada cantidad de pasos
     * @param steps pasos a dar
     */
    public void spin(int steps)
    {
        /**
        * Modificacion ciclo 3
        * Se agrego un condicional para verificar que existan simbolos en la rueda
        */
        if(symbols.size() == 0)
        {
            return;
        }
        
        /**
        * Se agrego condicional para verificar si la direccion de los pasos. 
        */
        if(steps > 0)
        {
            for(int i = 0; i < steps; i++)
            {
                index = (index + 1) % symbols.size();
                updateSymbol();
                
                if(isVisible)
                {
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch(InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        else
        {
            for(int i = 0; i < -steps; i++)
            {
                index--;
                
                if(index < 0)
                {
                    index = symbols.size() - 1;
                }
                
                updateSymbol();
                
                if(isVisible)
                {
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch(InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        
        symbols.get(index).onStop();
        updateSymbol();
    }
    
    /**
     * Cantidad de simbolos que contiene una rueda
     * @return cantidad de simbolos
     */
    public int symbolsSize()
    {
        return symbols.size();
    }
    
    // Nuevo metodo CICLO 4 setLeftNeighbor
    
    /**
     * Asigna la rueda que esta a la izquierda
     * por defecto no hace nada, las subclases la sobreescriben
     * @param leftNeighbor la rueda que esta a la izquierda
     */
    public void setLeftNeighbor(Wheel leftNeighbor){}
    
    // Nuevos metodos CICLO 4 para ser sobreescritos por Rebel
    
    /**
     * Consulta si la rueda se puede bloquear
     * @return true si la rueda se puede bloquear, false si no se puede bloquear
     */
    public boolean canLock()
    {
        return true;
    }
    
    /**
     * Consulta si la rueda se puede intercambiar
     * @return true si la rueda se puede intercambiar, false si no se puede intercambiar
     */
    public boolean canSwap()
    {
        return true;
    }
    
    /**
     * Consulta si la rueda se puede borrar
     * @return true si la rueda se puede borrar, false si no se puede borrar
     */
    public boolean canDelete()
    {
        return true;
    }
}  