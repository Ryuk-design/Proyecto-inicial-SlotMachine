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
 * @version (2.0)
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
     * Metodo constructor
     * @param x posicion horizontal de Wheel
     * @param y posicion vertical de Wheel
     */
    public Wheel(int x, int y)
    {
        symbols = new ArrayList<Symbol>();
        window = new Rectangle(WINDOW_HEIGHT, WINDOW_WIDTH, x, y, COLOR);
        index = 0;
        xPosition = x;
        yPosition = y;
        isVisible = false;
    }
    
    /**
     * Actualiza la posicion y visibilidad de el Symbol de la posicion index actual para que quede centrado en Wheel
     * @author Samuel Ahumada
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
     * Agrega un simbolo a la rueda en una posicion especifica
     * Si la posicion es menor que 1, se usa la posicion 1
     * Si la posicion es mayor al maximo posible, se agrega al final
     * @author Samuel Ahumada 
     * @param pos posicion donde se quiere insertar el simbolo
     * @return true si la operacion es exitosa
     */
    public boolean addSymbol(int pos, String color){
        Symbol sym = new Symbol(color, 0, 0);
        
        if (pos < 1)
        {
            pos = 1;
        } 
        else if (pos > symbols.size() + 1)
        {
            pos = symbols.size() + 1;
        }
        
        boolean empty = (symbols.size() == 0);
        symbols.add(pos - 1, sym);
        /** 
         * Correccion de bug Ciclo 2, el indice no avanzaba al agregar un nuevo simbolo, 
         * por lo que el simbolo al que apuntaba la rueda cambiaba automaticamente al agregar un simbolo justo detras del que apuntaba index.
         */
        if (!empty && pos - 1 <= index)
        { 
            index++; 
        }
        
        updateSymbol();
        return true;
    }
    
    /**
     * Remueve un Symbol de symbols, reacomodando symbols e index y haciendo los ajustes visuales correspondientes.
     * @author Samuel Ahumada
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
     * @author Samuel Ahumada
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
     * @author Samuel Ahumada
     */
    public void spin()
    {
        if(symbols.size() == 0)
        {
            return;
        }
        
        Random random = new Random();
        index = random.nextInt(symbols.size());
        updateSymbol();
    }
    
    /**
     * @author Samuel Ahumada
     * @return retorna el Symbol dentro de symbols en la posicion index
     */
    public Symbol getCurrentSymbol()
    {
        return symbols.get(index);
    }
    
    /**
     * Cambia la posicion de Wheel y reacomoda Symbol actualpo de acuerdo a la nueva posicion
     * @author Samuel Ahumada
     */
    public void setPosition(int x, int y)
    {
        window.setPosition(x, y);
        xPosition = x;
        yPosition = y;
        updateSymbol();
    }
    
    /**
     * Hace visible Symbol actual y Wheel
     * @author Samuel Ahumada
     */
    public void makeVisible()
    {
         this.isVisible = true;
         window.makeVisible();
         updateSymbol();
    }
    
    /**
     * Hace visible Symbol actual y Wheel
     * @author Samuel Ahumada
     */
    public void makeInvisible()
    {
         window.makeInvisible();
         this.isVisible = false;
         
         if(symbols.size() > 0)
        {
            Symbol currSym = getCurrentSymbol();
            currSym.makeInvisible();
        }
    }
    // CICLO 2
    /**
     * Getter de estado de bloqueo de la rueda
     * @return bloqueado o no bloqueado
     */
    public boolean getLocked()
    {
        return locked;
    }
    
    /**
     * Setter de bloqueo
     * @param status bloqueado o no bloqueado
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
                index = index - 1;
                
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
    }
    
    /**
     * Cantidad de simbolos que contiene una rueda
     * @return cantidad de simbolos
     */
    public int size()
    {
        return symbols.size();
    }
}  