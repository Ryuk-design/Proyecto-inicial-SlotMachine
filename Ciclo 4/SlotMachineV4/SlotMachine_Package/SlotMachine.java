package SlotMachine_Package;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Random;

/**
 * Simulador de una maquina tragamonedas compuesta por un conjunto de ruedas (Wheels)
 * donde cada rueda (Wheel) muestra un simbolo (Symbol) de un color del estandar CSS.
 * en esta maquina se puede:
 * agregar, eliminar, intercambiar ruedas
 * agregar y eliminar simbolos maestros (masterSymbols) que comparten todas las ruedas
 * fijar simbolos en las ruedas
 * bloquear y desbloquear ruedas individualmente
 * consultar configuracion de ruedas, simbolos disponibles y si la maquina se encuentra en estado de Jackpot.
 * @author (Samuel Ahumada) 
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
    private static final int MARGIN = 10; 
    private static final int GAP = 5;      
    private static final int FRAME_X = 10;  
    private static final int FRAME_Y = 10;
    private static final int FRAME_HEIGHT = 65;
    private static final int FRAME_WIDTH = 1515;

    /**
     * Constructor de la clase SlotMachine
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
        
        if(wheels.get(0).symbolsSize() == 0)
        {
                return false;            
        }
        
        Symbol ref = wheels.get(0).getCurrentSymbol();
        
        for(int i = 1; i < wheels.size(); i++)
        {
            // Ciclo 2 Agregacion de validacion de que ninguna rueda este vacia.
            
            if(wheels.get(i).symbolsSize() == 0)
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
        }
        else
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
    private void showError(String message)
    {
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
            
            // Validacion CICLO 4
            // A cada rueda se le va a asignar un vecino a su izquierda con el motivo de poder implementar la rueda lefty
        
            if(i == 0)
            {
                wheels.get(i).setLeftNeighbor(null);
            }
            else
            {
                wheels.get(i).setLeftNeighbor(wheels.get(i - 1));
            }
        }
        
        
    }
    
    /**
     * Agrega una nueva rueda normal a SlotMachine
     * @param pos posicion donde se insertara la rueda
     */
    public void addWheel(int pos)
    {
        addWheel("normal", pos);
    }
    
    /**
     * Agrega una nueva rueda del tipo elegido a SlotMachine
     * @param type tipo de rueda, "lefty" o "rebel", cualquier otro valor crea una rueda normal
     * @param pos posicion donde se insertara la rueda
     */
    public void addWheel(String type, int pos)
    {
        lastOperationOk = false;
        Wheel whl;
    
        if("lefty".equals(type))
        {
            whl = new Lefty(0, 0);
        }
        else if("rebel".equals(type))
        {
            whl = new Rebel(0, 0);
        }
        else
        {
            whl = new Wheel(0, 0);
        }
    
        // Refactorizacion CICLO 4
        pos = recalculateIndexes(pos, 'a', 'w');
    
        for(int i = 0; i < masterSymbols.size(); i++)
        {
            whl.addSymbol(i + 1, masterSymbols.get(i).copy());
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
     * @param pos posicion de la rueda a eliminar
     */
    public void delWheel(int pos)
    {
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            showError("No hay ruedas existentes");
            return;
        }
        
        // Refactorizacion CICLO 4
        pos = recalculateIndexes(pos, 'd', 'w');
        
        // Validacion CICLO 4 para rueda rebel
        if(!wheels.get(pos - 1).canDelete())
        {
            showError("Esta rueda no se deja eliminar");
            return;
        }
        
        wheels.get(pos - 1).makeInvisible();
        wheels.remove(pos - 1);
        recalculatePositions();
        lastOperationOk = true;
    }
    
    /**
     * Agrega un Symbol con un color existente que no haya sido usado con anterioridad.
     */
    public void addSymbol(int pos, String color)
    {
        addSymbol("normal", pos, color);
    }
    
    /**
     * Agrega un Symbol con un color existente que no haya sido usado con anterioridad.
     * @param type tipo de simbolo, puede ser "shy" o "ephemeral"
     * @param pos posicion donde se agregara el simbolo
     * @param color del simbolo
     */
    public void addSymbol(String type, int pos, String color)
    {
        lastOperationOk = false;
        Symbol sym;
    
        if("ephemeral".equals(type))
        {
            sym = new Ephemeral(color, 0, 0);
        }
        else if("shy".equals(type))
        {
            sym = new Shy(color, 0, 0);
        }
        else
        {
            sym = new Symbol(color, 0, 0);
        }
    
        if(availableColors.contains(color) == false)
        {
            showError("El color que se busca asignar a un nuevo simbolo ya esta siendo utilizado o el color no existe");
            return;
        }
    
        pos = recalculateIndexes(pos, 'a', 's');
        masterSymbols.add(pos - 1, sym);
        availableColors.remove(color);
    
        for(int i = 0; i < wheels.size(); i++)
        {
            wheels.get(i).addSymbol(pos, sym.copy());
        }
    
        lastOperationOk = true;
    }
    
    /**
     *  Elimina un Symbol de masterSymbols y a cada rueda y permite que el color que utilizaba el Symbol eliminado pueda ser reutilizado
     */
    public void delSymbol(String symbol)
    {
        lastOperationOk = false;
        
        // Agregacion de validacion CICLO 2 
        // Verifica si hay alguna rueda bloqueada
        for(int i = 0; i < wheels.size(); i++)
        {
            if(wheels.get(i).getLocked())
            {
                showError("No se pueden eliminar simbolos ya que hay al menos una rueda bloqueada");
                return;
            }
        }
        //
        boolean findSymbol = false;
        
        for(int i = 0; i < masterSymbols.size() && !findSymbol; i++)
        {
            if(symbol.equals(masterSymbols.get(i).getColor()))
            {
                masterSymbols.remove(i);
                
                for(int j = 0; j < wheels.size(); j++)
                {
                    wheels.get(j).removeSymbol(symbol);
                }
                
                updateJackpotStatus();
                availableColors.add(symbol);
                lastOperationOk = true;
                findSymbol = true;
            }
        }
        
        updateJackpotStatus();
        
        if(!findSymbol)
        {
            showError("El color del Simbolo que se busca eliminar no ha sido asignado aun");
        }
    }
    
    /**
     * Obliga a una Wheel a mostrar un Symbol en especifico 
     */
    public void placeSymbol(int wheel, String symbol)
    {
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            showError("No hay ruedas existentes");
            return;
        }
        
        // Refactorizacion CICLO 4
        wheel = recalculateIndexes(wheel, 'p', 'w');
        
        // Agregacion de validacion CICLO 2 
        // Verifica si la rueda que se le quiere cambiar el simbolo esta bloqueada
        if(getWheel(wheel).getLocked())
        {
            showError("La rueda esta bloqueada, entonces no se le puede forzar un simbolo a mostrar");
            return;
        }
        
        boolean res = wheels.get(wheel - 1).placeSymbol(symbol);
        lastOperationOk = res;
        updateJackpotStatus();
    }
    
    /**
     * Gira todas las Wheels
     */
    public void spin(){
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            showError("No hay ruedas existentes");
            return;
        }
        
        // Agregacion de validacion CICLO 2 
        // Verifica si hay alguna rueda bloqueada
        for(int i = 0; i < wheels.size(); i++)
        {
            if(wheels.get(i).getLocked())
            {
                showError("No se pueden girar todas las ruedas ya que hay al menos una bloqueada");
                return;
            }
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
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            showError("No hay ruedas existentes");
            return;
        }
        
        // Refactorizacion CICLO 4
        wheel = recalculateIndexes(wheel, 's', 'w');
        
        // Agregacion de validacion CICLO 2
        // Verifica si la rueda esta bloqueada
        if(getWheel(wheel).getLocked())
        {
            showError("La rueda esta bloqueada, entonces no se puede girar");
            return;
        }
        
        getWheel(wheel).spin();
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
        
        lastOperationOk = true;
        return distinct.size();
    }
    
    /**
     * Hace visible toda la maquina
     */
    public void makeVisible(){
        lastOperationOk = false;
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
        lastOperationOk = false;
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
        System.exit(0);
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
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            showError("No hay ruedas existentes");
            return;
        }
        
        //Refactorizacion CICLO 4
        wheel1 = recalculateIndexes(wheel1, 's', 'w');
        wheel2 = recalculateIndexes(wheel2, 's', 'w');
        
        //Validacion CICLO 4 para rueda rebel
        if(!getWheel(wheel1).canSwap() || !getWheel(wheel2).canSwap())
        {
            showError("Una de las ruedas no se deja intercambiar");
            return;
        }
        
        if(wheel1 == wheel2)
        {
            lastOperationOk = true;
            return;
        }
        
        Wheel w1 = getWheel(wheel1);
        wheels.set(wheel1 - 1, getWheel(wheel2));
        wheels.set(wheel2 - 1, w1);
        
        // debe recalcular posiciones para definir nuevo vecino en caso de swap rueda lefty
        recalculatePositions();
        
        lastOperationOk = true;
    }
    
    /**
     * Bloquea una rueda
     * @param wheel posicion de la rueda
     */
    public void lock(int wheel)
    {
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            showError("No hay ruedas existentes");
            return;
        }
        
        //Refactorizacion CICLO 4
        wheel = recalculateIndexes(wheel, 'l', 'w');
        
        //Validacion CICLO 4 para rueda rebel
        if(!getWheel(wheel).canLock())
        {
            showError("Esta rueda no se deja bloquear");
            return;
        }
        
        if(getWheel(wheel).getLocked())
        {
            showError("La rueda ya esta bloqueada");
            return;
        }
        
        getWheel(wheel).setLocked(true);
        lastOperationOk = true;
    }
    
    /**
     * Desbloquea una rueda bloqueada
     * @param wheel posicion de la rueda
     */
    public void unlock(int wheel)
    {
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            showError("No hay ruedas existentes");
            return;
        }
        
        //Refactorizacion CICLO 4
        wheel = recalculateIndexes(wheel, 'u', 'w');
        
        if(!getWheel(wheel).getLocked())
        {
            showError("La rueda ya esta desbloqueada");
            return;
        }
        
        getWheel(wheel).setLocked(false);
        lastOperationOk = true;
    }
    
    /**
     * Cambia el simbolo de una rueda segun la cantidad indicada de pasos
     * @param wheel posicion de la rueda
     * @param steps cantidad de pasos
     */
    public void spin(int wheel, int steps)
    {   
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            showError("No hay ruedas existentes");
            return;
        }
        
        if(steps == 0)
        {
            lastOperationOk = true;
            return;
        }
        
        if(masterSymbols.size() == 0)
        {
            showError("No hay simbolos sobre los cuales caminar");
            return;
        }
        
        wheel = recalculateIndexes(wheel, 's', 'w');
        
        if(getWheel(wheel).getLocked())
        {
            showError("La rueda esta bloqueada y no se puede girar");
            return;
        }
        
        /**
         * Modificacion ciclo 3
         * Se retiro una variable que guardaba steps con valor absoluto, ya que en el problema de la maraton se pide que en caso de que steps sea negativo
         * la rueda debe caminar en sentido contrario.
        */ 
        getWheel(wheel).spin(steps);
        lastOperationOk = true;
        updateJackpotStatus();
    }
    
    /**
     * Configura los simbolos de las ruedas de la maquina
     * @param configuracion de colores de simbolos
     */
    public void spin(String[]setSymbols)
    {
        lastOperationOk = false;

        if(wheels.size() == 0)
        {
            showError("No hay ruedas disponibles para configurar");
            return;
        }
        
        if(masterSymbols.size() < setSymbols.length)
        {
            showError("la configuracion enviada es mayor que la cantidad de simbolos existentes");
            return;
        }
        
        if(wheels.size() < setSymbols.length || wheels.size() > setSymbols.length)
        {
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
            showError("la configuracion enviada no coincide en su totalidad con los simbolos presentes en las ruedas");
            return;
        }
        
        for(int i = 0; i < wheels.size(); i++)
        {
            if(wheels.get(i).getLocked())
            {
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
        // Nueva condicion ciclo 3
        if(wheels.size() == 0)
        {
            throw new IllegalArgumentException("SlotMachine no tiene ruedas");
        }
        
        wheel = recalculateIndexes(wheel, 'g', 'w');
        
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
        // Nueva condicion ciclo 3
        if(masterSymbols.size() == 0)
        {
            throw new IllegalArgumentException("SlotMachine no tiene simbolos");
        }
        
        if(pos < 1)
        {
            pos = 1;
        } 
        else if(pos > masterSymbols.size())
        {
            pos = masterSymbols.size();
        }
        
        Symbol s = masterSymbols.get(pos - 1);
        return s;
    }
    
    //CICLO 3
    
    /**
     * Crea una instancia de SlotMachine con un numero igual de ruedas y simbolos con color aleatorio, gira las ruedas para que inicialicen aleatoriamente
     * @param n numero de ruedas
     */
    public SlotMachine(int n)
    { 
        wheels = new ArrayList<Wheel>();
        masterSymbols = new ArrayList<Symbol>();
        availableColors = Canvas.getAvailableColorNames();
        frame = new Rectangle(FRAME_HEIGHT, FRAME_WIDTH, FRAME_X, FRAME_Y, NORMAL_FRAME_COLOR);
        isVisible = false;
        lastOperationOk = true;
        Random random = new Random();
        
        for(int i = 0; i < n; i++)
        {
            addSymbol(i + 1, availableColors.get(random.nextInt(availableColors.size())));
        }
        
        for(int i = 0; i < n; i++)
        {
            addWheel(i + 1);
        }
        
        spin();
    }
    
    /**
     * Recalcula los indices dependiendo de la operacion y el objeto
     * @param op la operacion puede ser: add 'a', delete 'd', spin o swap's', unlock 'u', lock'l'. 'd', 's', 'u' y 'l' hacen lo mismo
     * @param obj el recalculo de los indices depende de si el objeto es un simbolo o una rueda 's' o 'r'
     */
    private int recalculateIndexes(int index, char op, char obj)
    {
        if(index < 1)
        {
            return 1;
        }
        
        if(op == 'a')
        {
            if(obj == 'w')
            {
                if(index > wheels.size())
                {
                    return wheels.size() + 1;
                }
            }
            
            if(obj == 's')
            {
                if(index > masterSymbols.size())
                {
                    return masterSymbols.size() + 1;
                }
            }
        }
        else 
        {
            if(obj == 'w')
            {
                if(index > wheels.size())
                {
                    return wheels.size();
                }
            }
        }
        
        return index;
    }
}    