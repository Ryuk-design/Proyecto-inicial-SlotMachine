package SlotMachine_Package;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 * Simulador de una maquina tragamonedas compuesta por un conjunto de ruedas (Wheels)
 * donde cada rueda (Wheel) muestra un simbolo (Symbol) de un color del estandar CSS.
 * en esta maquina se puede:
 * agregar, eliminar, intercambiar ruedas
 * agregar y eliminar simbolos maestros (masterSymbols) que comparten todas las ruedas
 * fijar simbolos en las ruedas
 * bloquear y desbloquear ruedas individualmente
 * consultar configuracion de ruedas, simbolos disponibles y si la maquina se encuentra en estado de Jackpot.
 * @author (Samuel Ahumada - Nerieth Villota) 
 * @version (2.0)
 */
public class SlotMachine
{
    private ArrayList<Wheel> wheels;
    private ArrayList<Symbol> masterSymbols;
    private ArrayList<String> availableColors;
    private Rectangle frame;
    private boolean isVisible;
    private boolean lastOperationOk;
    private static final String NORMAL_FRAME_COLOR = "gray";
    private static final String JACKPOT_FRAME_COLOR = "gold";
    private static final int MARGIN = 40; 
    private static final int GAP = 20;      
    private static final int FRAME_X = 40;  
    private static final int FRAME_Y = 40;
    private static final int FRAME_HEIGHT = 260;
    private static final int FRAME_WIDTH = 1500;

    /**
     * constructor, lastOperationOk se inicializa en true.
     */
    public SlotMachine()
    {
        wheels = new ArrayList<Wheel>();
        masterSymbols = new ArrayList<Symbol>();
        availableColors = Canvas.getAvailableColorNames();
        frame = new Rectangle(FRAME_HEIGHT, FRAME_WIDTH, FRAME_X, FRAME_Y, NORMAL_FRAME_COLOR);
        isVisible = false;
        lastOperationOk = true;
    }
    
    /**
     * Consulta si es jackpot
     * @return true si es jackpot, false si no es jackpot
     */
    public boolean isJackpot()
    {
        if (wheels.size() < 2)
        {
            return false;
        }
        // Ciclo 2 Agregacion de validacion de que ninguna rueda este vacia.
        if(wheels.get(0).size() == 0)
        {
                return false;            
        }
        
        Symbol ref = wheels.get(0).getCurrentSymbol();
        
        for(int i = 1; i < wheels.size(); i++)
        {
            // Ciclo 2 Agregacion de validacion de que ninguna rueda este vacia.
            if(wheels.get(i).size() == 0)
            {
                return false;            
            }
            
            Symbol cur = wheels.get(i).getCurrentSymbol();
            
            if(ref.isSameSymbol(cur) == false)
            {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Cambia el color de la maquina si es Jackpot o si no es Jackpot
     */
    private void updateJackpotStatus()
    {
        if(isJackpot())
        {
            frame.changeColor(JACKPOT_FRAME_COLOR);
        }else
        {
            frame.changeColor(NORMAL_FRAME_COLOR);
        }
        
        if(isVisible)
        {
            makeVisible();
        }
    }
    
    /**
     * En caso de que la maquina y sus componentes sean visibles, si el usuario intenta hacer una accion que desencadene en un error, el metodo se encarga de darle un mensaje de advertencia. 
     */
    private void showError(String message){
        if(isVisible)
        {
            JOptionPane.showMessageDialog(null, message);
        }
    }
    
    /**
     * Calcula automaticamente las posiciones de cada Wheel
     */
    private void recalculatePositions()
    {
        for (int i = 0; i < wheels.size(); i++)
        {
            int x = FRAME_X + MARGIN + i * (Wheel.WINDOW_WIDTH + GAP);
            wheels.get(i).setPosition(x, FRAME_Y + MARGIN);
        }
    }
    
    /**
     * Agrega una nueva rueda a SlotMachine 
     */
    public void addWheel(int pos)
    {
        Wheel whl = new Wheel(0, 0);
        if(pos < 1)
        {
            pos = 1;
        } else if(pos > wheels.size() + 1)
        {
            pos = wheels.size() + 1;
        }
        
        for(int i = 0; i < masterSymbols.size(); i++)
        {
            whl.addSymbol(i + 1, masterSymbols.get(i).getColor());
        }
        
        wheels.add(pos - 1, whl);
        recalculatePositions();
        
        if(isVisible)
        {
            whl.makeVisible();
        }
        
        lastOperationOk = true;
    }
    
    /**
     * Elimina una rueda en una posicion determinada. 
     * @param 
     */
    public void delWheel(int pos)
    {
        if(wheels.size() == 0)
        {
            lastOperationOk = false;
            showError("No se puede eliminar ruedas de una lista de ruedas vacia");
            return;
        }else if(pos < 1)
        {
                pos = 1;
        }else if (pos > wheels.size())
        {
                pos = wheels.size();
        }
        
        wheels.get(pos - 1).makeInvisible();
        wheels.remove(pos - 1);
        recalculatePositions();
        lastOperationOk = true;
    }
    
    /**
     * Agrega un Symbol con un color existente que no haya sido usado con anterioridad a masterSymbols y posteriormente lo agrega a cada rueda. 
     */
    public void addSymbol(int pos, String color)
    {
        if(availableColors.contains(color) == false)
        {
            lastOperationOk = false;
            showError("El color que se busca asignar a un nuevo simbolo ya esta siendo utilizado o el color no existe");
            return;
        }
        
        if(pos < 1)
        {
            pos = 1;
        }else if (pos > masterSymbols.size() + 1)
        {
            pos = masterSymbols.size() + 1;
        }
        
        Symbol sym = new Symbol(color, 0, 0);
        masterSymbols.add(pos - 1, sym);
        availableColors.remove(color);
        
        for(int i = 0; i < wheels.size(); i++)
        {
            wheels.get(i).addSymbol(pos, color);
        }
        
        lastOperationOk = true;
    }
    
    /**
     *  Elimina un Symbol de masterSymbols y a cada rueda y permite que el color que utilizaba el Symbol eliminado pueda ser reutilizado
     */
    public void delSymbol(String symbol)
    {
        // Agregacion de validacion CICLO 2 
        // Verifica si hay alguna rueda bloqueada
        for(int i = 0; i < wheels.size(); i++)
        {
            if(wheels.get(i).getLocked())
            {
                lastOperationOk = false;
                showError("No se pueden eliminar simbolos ya que hay al menos una rueda bloqueada");
                return;
            }
        }
        //
        for(int i = 0; i < masterSymbols.size(); i++)
        {
            if (symbol.equals(masterSymbols.get(i).getColor()))
            {
                masterSymbols.remove(i);
                
                for(int j = 0; j < wheels.size(); j++)
                {
                    wheels.get(j).removeSymbol(symbol);
                }
                
                updateJackpotStatus();
                availableColors.add(symbol);
                lastOperationOk = true;
                return;
            }
        }
        
        updateJackpotStatus();
        lastOperationOk = false;
        showError("El color del Simbolo que se busca eliminar no ha sido asignado aun");
    }
    
    /**
     * Obliga a una Wheel a mostrar un Symbol en especifico 
     */
    public void placeSymbol(int wheel, String symbol)
    {
        // Agregacion de validacion CICLO 2 
        // Verifica si la rueda que se le quiere cambiar el simbolo esta bloqueada
        if(wheels.get(wheel - 1).getLocked())
        {
            lastOperationOk = false;
            showError("La rueda esta bloqueada, entonces no se le puede forzar un simbolo a mostrar");
            return;
        }
        //
        if(wheels.size() == 0)
        {
            lastOperationOk = false;
            showError("No hay ruedas existentes");
            return;
        } else if (wheel < 1)
        {
            wheel = 1;        
        } else if (wheel > wheels.size())
        {
            wheel = wheels.size();
        }
        
        boolean res = wheels.get(wheel - 1).placeSymbol(symbol);
        lastOperationOk = res;
        updateJackpotStatus();
    }
    
    /**
     * Gira todas las Wheels
     */
    public void spin(){
        // Agregacion de validacion CICLO 2 
        // Verifica si hay alguna rueda bloqueada
        for(int i = 0; i < wheels.size(); i++)
        {
            if(wheels.get(i).getLocked())
            {
                lastOperationOk = false;
                showError("No se pueden girar todas las ruedas ya que hay al menos una bloqueada");
                return;
            }
        }
        //
        if(wheels.size() == 0)
        {
            lastOperationOk = false;
            showError("No hay ruedas existentes");
            return;
        }
        
        for(int i = 0; i < wheels.size(); i++)
        {
            wheels.get(i).spin();
        }
        
        lastOperationOk = true;
        updateJackpotStatus();
    }
    
    /**
     * Gira una Wheel en especifico 
     */
    public void spin(int wheel)
    {
        // Agregacion de validacion CICLO 2
        // Verifica si la rueda esta bloqueada
        if(wheels.get(wheel - 1).getLocked())
        {
            lastOperationOk = false;
            showError("La rueda esta bloqueada, entonces no se puede girar");
            return;
        }
        //
        if(wheels.size() == 0)
        {
            lastOperationOk = false;
            showError("No hay ruedas existentes");
            return;
        }
        
        if(wheel < 1)
        {
            wheel = 1;
        }
        
        if(wheel > wheels.size())
        {
            wheel = wheels.size();
        }
        
        wheels.get(wheel - 1).spin();
        lastOperationOk = true;
        updateJackpotStatus();
    }
    
    /**
     * @return Retorna una lista conlos colores de los simbolos en el orden que estan en todas las Wheels iniciando por el 1
    */
    public String[] configuration()
    {
        String[] config = new String[wheels.size()];
        
        for(int i = 0; i < wheels.size(); i++)
        {
            config[i] = wheels.get(i).getCurrentSymbol().getColor();
        }
        
        return config;
    }
    
    /**
     * @return retorna los colores de los Symbols en el orden que estan en masterSymbol iniciando por el 1
     */
    public String[] symbols()
    {
        String[] symbs = new String[masterSymbols.size()];
        
        for(int i = 0; i < masterSymbols.size(); i++)
        {
            symbs[i] = masterSymbols.get(i).getColor();
        }
        
        return symbs;
    }
    
    /**
     * Cuantos colores distintos hay entre los Symbols que muestran las Wheels 
     */
    public int distinctSymbols()
    {
        String[] config = configuration();
        ArrayList<String> distinct = new ArrayList<String>();
        
        for(int i = 0; i < config.length; i++)
        {
            if(distinct.contains(config[i]) == false)
            {
                distinct.add(config[i]);
            }
        }
        
        return distinct.size();
    }
    
    /**
     * Hace visible toda la maquina
     */
    public void makeVisible(){
        frame.makeVisible();
        
        for(int i = 0; i < wheels.size(); i++)
        {
            wheels.get(i).makeVisible();
        }
        
        isVisible = true;
        lastOperationOk = true;
    }
    
    /**
     * Hace invisible toda la maquina
     */
    public void makeInvisible()
    {
        frame.makeInvisible();
        
        for(int i = 0; i < wheels.size(); i++)
        {
            wheels.get(i).makeInvisible();
        }
        
        isVisible = false;
        lastOperationOk = true;
    }
    
    public void exit()
    {
        makeInvisible();
        lastOperationOk = true;
    }
    
    /**
     * @return getter de lastOperationOk 
     */
    public boolean ok()
    {
        return lastOperationOk;
    }
    
    //CICLO 2
    
    /**
     * Intercambia la posicion de dos ruedas.
     * @param wheel1 posicion de la primera rueda
     * @param wheel2 posicion de la segunda rueda
     */
    public void swap(int wheel1, int wheel2)
    {
        if(wheel1 < 1)
        {
            wheel1 = 1;
        } else if(wheel1 > wheels.size())
        {
            wheel1 = wheels.size();
        }
        
        if(wheel2 < 1)
        {
            wheel2 = 1;
        }else if(wheel2 > wheels.size())
        {
            wheel2 = wheels.size();
        }
        
        if(wheel1 == wheel2)
        {
            lastOperationOk = true;
            return;
        }
        
        Wheel w1 = wheels.get(wheel1 - 1);
        wheels.set(wheel1 - 1, wheels.get(wheel2 - 1));
        wheels.set(wheel2 - 1, w1);
        lastOperationOk = true;
    }
    
    /**
     * Bloquea una rueda
     * @param wheel posicion de la rueda
     */
    public void lock(int wheel)
    {
        if(wheel < 1)
        {
            wheel = 1;
        } else if(wheel > wheels.size())
        {
            wheel = wheels.size();
        }
        
        if(wheels.get(wheel - 1).getLocked())
        {
            lastOperationOk = false;
            showError("La rueda ya esta bloqueada");
            return;
        }
        
        wheels.get(wheel - 1).setLocked(true);
        lastOperationOk = true;
    }
    
    /**
     * Desbloquea una rueda bloqueada
     * @param wheel posicion de la rueda
     */
    public void unlock(int wheel)
    {
        if(wheel < 1)
        {
            wheel = 1;
        } 
        else if(wheel > wheels.size())
        {
            wheel = wheels.size();
        }
        
        if(!wheels.get(wheel - 1).getLocked())
        {
            lastOperationOk = false;
            showError("La rueda ya esta desbloqueada");
            return;
        }
        
        wheels.get(wheel - 1).setLocked(false);
        lastOperationOk = true;
    }
    
    /**
     * Cambia el simbolo de una rueda segun la cantidad indicada de pasos
     * @param wheel posicion de la rueda
     * @param steps cantidad de pasos
     */
    public void spin(int wheel, int steps)
    {
        if(wheels.size() == 0)
        {
            lastOperationOk = false;
            showError("No hay ruedas existentes");
            return;
        }
        
        if(masterSymbols.size() == 0)
        {
            lastOperationOk = false;
            showError("No hay simbolos sobre los cuales caminar");
            return;
        }
        
        if(wheel < 1)
        {
            wheel = 1;
        } 
        else if(wheel > wheels.size())
        {
            wheel = wheels.size();
        }
        
        if(wheels.get(wheel - 1).getLocked())
        {
            lastOperationOk = false;
            showError("La rueda esta bloqueada y no se puede girar");
            return;
        }
        
        steps = Math.abs(steps);
        wheels.get(wheel - 1).spin(steps);
        lastOperationOk = true;
        updateJackpotStatus();
    }
    
    /**
     * Configura los simbolos de las ruedas de la maquina
     * @param configuracion de colores de simbolos
     */
    public void spin(String[]setSymbols)
    {
        if(wheels.size() == 0)
        {
            lastOperationOk = false;
            showError("No hay ruedas disponibles para configurar");
            return;
        }
        
        if(masterSymbols.size() < setSymbols.length)
        {
            lastOperationOk = false;
            showError("la configuracion enviada es mayor que la cantidad de simbolos existentes");
            return;
        }
        
        if(wheels.size() < setSymbols.length || wheels.size() > setSymbols.length)
        {
            lastOperationOk = false;
            showError("La configuracion enviada no coincide con la cantidad de ruedas existentes");
            return;
        }
        
        int count = 0;
        for(int i = 0; i < masterSymbols.size(); i++)
        {
            for(int j = 0; j < setSymbols.length; j++)
            {
                if(masterSymbols.get(i).getColor().equals(setSymbols[j]))
                {
                    count++;
                }
            }
        }
        
        if(!(count == setSymbols.length))
        {
            lastOperationOk = false;
            showError("la configuracion enviada no coincide en su totalidad con los simbolos presentes en las ruedas");
            return;
        }
        
        for(int i = 0; i < wheels.size(); i++)
        {
            if(wheels.get(i).getLocked())
            {
                lastOperationOk = false;
                showError("No se puede cambiar la configuracion de las ruedas ya que hay al menos una bloqueada");
                return;
            }
        }
        
        for(int i = 0; i < wheels.size(); i++)
        {
            placeSymbol(i + 1, setSymbols[i]);
        }
        
        lastOperationOk = true;
    }
    
    /**
     * Getter de rueda
     * @param posicion de la rueda
     * @return rueda
     */
    public Wheel getWheel(int wheel)
    {
        if(wheel < 1)
        {
            wheel = 1;
        } 
        else if(wheel > wheels.size())
        {
            wheel = wheels.size();
        }
        
        Wheel w = wheels.get(wheel - 1);
        return w;
    }
    
    /**
     * Getter de simbolo
     * @param posicion del simbolo
     * @return simbolo
     */
    public Symbol getSymbol(int pos)
    {
        if(pos < 1)
        {
            pos = 1;
        } 
        else if(pos > wheels.size())
        {
            pos = wheels.size();
        }
        
        Symbol s = masterSymbols.get(pos - 1);
        return s;
    }
}    