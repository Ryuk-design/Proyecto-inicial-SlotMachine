package SlotMachine_Package;
import java.util.ArrayList;
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
 * @version (5.0)
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
        if (wheels.size() < 3)
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
     * Calcula automaticamente las posiciones de cada Wheel al agregar o eliminar una rueda de cualquier tipo
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
     * @throws SlotMachineException INVALID_WHEEL_TYPE si el tipo de rueda no existe o no se puede crear
     */
    public void addWheel(int pos) throws SlotMachineException
    {
        addWheel("Wheel", pos);
    }
    
    /**
     * Agrega una nueva rueda del tipo elegido a SlotMachine
     * @param type tipo de rueda, "lefty" o "rebel", cualquier otro valor crea una rueda normal
     * @param pos posicion donde se insertara la rueda
     * @throws SlotMachineException INVALID_WHEEL_TYPE si el tipo de rueda no existe o no se puede crear
     */
    public void addWheel(String type, int pos) throws SlotMachineException
    {
        lastOperationOk = false;
        Wheel whl;
    
        try
        {
            Class<?> cls = Class.forName("SlotMachine_Package." + type);
            whl = (Wheel) cls.getConstructor(int.class, int.class).newInstance(0, 0);
        }
        catch(ReflectiveOperationException e)
        {
            throw new SlotMachineException(SlotMachineException.INVALID_WHEEL_TYPE, e);
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
    public void delWheel(int pos) throws SlotMachineException
    {
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            throw new SlotMachineException(SlotMachineException.NO_WHEELS);
        }
        
        // Refactorizacion CICLO 4
        pos = recalculateIndexes(pos, 'd', 'w');
        
        // Validacion CICLO 4 para rueda rebel
        if(!wheels.get(pos - 1).canDelete())
        {
            throw new SlotMachineException(SlotMachineException.WHEEL_CANNOT_DELETE);
        }
        
        wheels.get(pos - 1).makeInvisible();
        wheels.remove(pos - 1);
        recalculatePositions();
        lastOperationOk = true;
    }
    
    /**
     * Agrega un Symbol con un color existente que no haya sido usado con anterioridad.
     * @param pos posicion donde se agregara el simbolo
     * @param color del simbolo
     * @throws SlotMachineException INVALID_COLOR si el color ya esta siendo utilizado o no existe,
     *                              INVALID_SYMBOL_TYPE si el tipo de simbolo no existe o no se puede crear
     */
    public void addSymbol(int pos, String color) throws SlotMachineException
    {
        addSymbol("Symbol", pos, color);
    }
    
    /**
     * Agrega un Symbol con un color existente que no haya sido usado con anterioridad.
     * @param type tipo de simbolo, puede ser "shy" o "ephemeral"
     * @param pos posicion donde se agregara el simbolo
     * @param color del simbolo
     * @throws SlotMachineException INVALID_COLOR si el color ya esta siendo utilizado o no existe,
     *                              INVALID_SYMBOL_TYPE si el tipo de simbolo no existe o no se puede crear
     */
    public void addSymbol(String type, int pos, String color) throws SlotMachineException
    {
        lastOperationOk = false;
        Symbol sym;
    
        try
        {
            Class<?> cls = Class.forName(Symbol.class.getPackageName() + "." + type);
            sym = (Symbol) cls.getConstructor(String.class, int.class, int.class).newInstance(color, 0, 0);
        }
        catch(ReflectiveOperationException e)
        {
            throw new SlotMachineException(SlotMachineException.INVALID_SYMBOL_TYPE, e);
        }
    
        if(availableColors.contains(color) == false)
        {
            throw new SlotMachineException(SlotMachineException.INVALID_COLOR);
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
     * Elimina un Symbol de masterSymbols y a cada rueda y permite que el color que utilizaba el Symbol eliminado pueda ser reutilizado
     * @param symbol color del simbolo a eliminar
     * @throws SlotMachineException SOME_WHEEL_LOCKED si hay al menos una rueda bloqueada,
     *                              SYMBOL_NOT_ASSIGNED si el color no corresponde a ningun simbolo de la maquina
     */
    public void delSymbol(String symbol) throws SlotMachine_Package.SlotMachineException
    {
        lastOperationOk = false;
        
        // Agregacion de validacion CICLO 2 
        // Verifica si hay alguna rueda bloqueada
        for(int i = 0; i < wheels.size(); i++)
        {
            if(wheels.get(i).getLocked())
            {
                throw new SlotMachineException(SlotMachineException.SOME_WHEEL_LOCKED);
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
            throw new SlotMachineException(SlotMachineException.SYMBOL_NOT_ASSIGNED);
        }
    }
    
    /**
     * Obliga a una Wheel a mostrar un Symbol en especifico 
     * @param wheel posicion de la rueda
     * @param symbol color del simbolo que se quiere mostrar
     * @throws SlotMachineException NO_WHEELS si la maquina no tiene ruedas,
     *                              WHEEL_LOCKED si la rueda esta bloqueada
     */
    public void placeSymbol(int wheel, String symbol) throws SlotMachineException
    {
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            throw new SlotMachineException(SlotMachineException.NO_WHEELS);
        }
        
        // Refactorizacion CICLO 4
        wheel = recalculateIndexes(wheel, 'p', 'w');
        
        // Agregacion de validacion CICLO 2 
        // Verifica si la rueda que se le quiere cambiar el simbolo esta bloqueada
        if(getWheel(wheel).getLocked())
        {
            throw new SlotMachineException(SlotMachineException.WHEEL_LOCKED);
        }
        
        boolean res = wheels.get(wheel - 1).placeSymbol(symbol);
        lastOperationOk = res;
        updateJackpotStatus();
    }
    
    /**
     * Gira todas las ruedas
     * @throws SlotMachineException NO_WHEELS si la maquina no tiene ruedas,
     *                              SOME_WHEEL_LOCKED si hay al menos una rueda bloqueada
     */
    public void spin() throws SlotMachineException
    {
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            throw new SlotMachineException(SlotMachineException.NO_WHEELS);
        }
        
        // Agregacion de validacion CICLO 2 
        // Verifica si hay alguna rueda bloqueada
        for(int i = 0; i < wheels.size(); i++)
        {
            if(wheels.get(i).getLocked())
            {
                throw new SlotMachineException(SlotMachineException.SOME_WHEEL_LOCKED);
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
     * @param wheel posicion de la rueda
     * @throws SlotMachineException NO_WHEELS si la maquina no tiene ruedas,
     *                              WHEEL_LOCKED si la rueda esta bloqueada
     */
    public void spin(int wheel) throws SlotMachine_Package.SlotMachineException
    {
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            throw new SlotMachineException(SlotMachineException.NO_WHEELS);
        }
        
        // Refactorizacion CICLO 4
        wheel = recalculateIndexes(wheel, 's', 'w');
        
        // Agregacion de validacion CICLO 2
        // Verifica si la rueda esta bloqueada
        if(getWheel(wheel).getLocked())
        {
            throw new SlotMachineException(SlotMachineException.WHEEL_LOCKED);
        }
        
        getWheel(wheel).spin();
        lastOperationOk = true;
        updateJackpotStatus();
    }
    
    /**
     * Consulta los simbolos que estan en cada rueda de la maquina
     * @return lista con los colores de los simbolos en el orden que estan en todas las Wheels iniciando por el 1
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
     * Consulta el orden de la lista general de simbolos
     * @return colores de los Symbols en el orden que estan en masterSymbol iniciando por el 1
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
     * @return cantidad de simbolos distintos
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
    
    /**
     * Cierra el simulador
     */
    public void exit()
    {
        makeInvisible();
        lastOperationOk = true;
        System.exit(0);
    }
    
    /**
     * Consulta si la ultima operacion realizada fue exitosa
     * @return estado de la ultima operacion realizada
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
     * @throws SlotMachineException NO_WHEELS si la maquina no tiene ruedas,
     *                              WHEEL_CANNOT_SWAP si alguna de las dos ruedas no se deja intercambiar (rebel)
     */
    public void swap(int wheel1, int wheel2) throws SlotMachineException
    {
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            throw new SlotMachineException(SlotMachineException.NO_WHEELS);
        }
        
        //Refactorizacion CICLO 4
        wheel1 = recalculateIndexes(wheel1, 's', 'w');
        wheel2 = recalculateIndexes(wheel2, 's', 'w');
        
        //Validacion CICLO 4 para rueda rebel
        if(!getWheel(wheel1).canSwap() || !getWheel(wheel2).canSwap())
        {
            throw new SlotMachineException(SlotMachineException.WHEEL_CANNOT_SWAP);
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
     * @throws NO_WHEELS si la maquina no tiene ruedas,
     *         WHEEL_CANNOT_LOCK si la rueda no se deja bloquear (rebel),
     *         WHEEL_ALREADY_LOCKED si la rueda ya esta bloqueada
     */
    public void lock(int wheel) throws SlotMachineException
    {
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            throw new SlotMachineException(SlotMachineException.NO_WHEELS);
        }
        
        //Refactorizacion CICLO 4
        wheel = recalculateIndexes(wheel, 'l', 'w');
        
        //Validacion CICLO 4 para rueda rebel
        if(!getWheel(wheel).canLock())
        {
            throw new SlotMachineException(SlotMachineException.WHEEL_CANNOT_LOCK);
        }
        
        if(getWheel(wheel).getLocked())
        {
            throw new SlotMachineException(SlotMachineException.WHEEL_ALREADY_LOCKED);
        }
        
        getWheel(wheel).setLocked(true);
        lastOperationOk = true;
    }
    
    /**
     * Desbloquea una rueda bloqueada
     * @param wheel posicion de la rueda
     * @throws SlotMachineException NO_WHEELS si la maquina no tiene ruedas,
     *                              WHEEL_ALREADY_UNLOCKED si la rueda ya esta desbloqueada
     */
    public void unlock(int wheel) throws SlotMachineException
    {
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            throw new SlotMachineException(SlotMachineException.NO_WHEELS);
        }
        
        //Refactorizacion CICLO 4
        wheel = recalculateIndexes(wheel, 'u', 'w');
        
        if(!getWheel(wheel).getLocked())
        {
            throw new SlotMachineException(SlotMachineException.WHEEL_ALREADY_UNLOCKED);
        }
        
        getWheel(wheel).setLocked(false);
        lastOperationOk = true;
    }
    
    /**
     * Cambia el simbolo de una rueda segun la cantidad indicada de pasos
     * @param wheel posicion de la rueda
     * @param steps cantidad de pasos
     * @throws SlotMachineException NO_WHEELS si la maquina no tiene ruedas,
     *                              NO_SYMBOLS_TO_WALK si la maquina no tiene simbolos sobre los cuales caminar,
     *                              WHEEL_LOCKED si la rueda esta bloqueada
     */
    public void spin(int wheel, int steps) throws SlotMachineException
    {   
        lastOperationOk = false;
        
        if(wheels.size() == 0)
        {
            throw new SlotMachineException(SlotMachineException.NO_WHEELS);
        }
        
        if(steps == 0)
        {
            lastOperationOk = true;
            return;
        }
        
        if(masterSymbols.size() == 0)
        {
            throw new SlotMachineException(SlotMachineException.NO_SYMBOLS_TO_WALK);
        }
        
        wheel = recalculateIndexes(wheel, 's', 'w');
        
        if(getWheel(wheel).getLocked())
        {
            throw new SlotMachineException(SlotMachineException.WHEEL_LOCKED);
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
     * @throws SlotMachineException NO_WHEELS si la maquina no tiene ruedas,
     *                              INVALID_CONFIGURATION si la configuracion es mayor que los simbolos existentes,
     *                              no coincide con la cantidad de ruedas o no coincide con los simbolos de la maquina,
     *                              SOME_WHEEL_LOCKED si hay al menos una rueda bloqueada
     */
    public void spin(String[]setSymbols) throws SlotMachine_Package.SlotMachineException
    {
        lastOperationOk = false;

        if(wheels.size() == 0)
        {
            throw new SlotMachineException(SlotMachineException.NO_WHEELS);
        }
        
        if(masterSymbols.size() < setSymbols.length)
        {
            throw new SlotMachineException(SlotMachineException.INVALID_CONFIGURATION);
        }
        
        if(wheels.size() < setSymbols.length || wheels.size() > setSymbols.length)
        {
            throw new SlotMachineException(SlotMachineException.INVALID_CONFIGURATION);
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
            throw new SlotMachineException(SlotMachineException.INVALID_CONFIGURATION);
        }
        
        for(int i = 0; i < wheels.size(); i++)
        {
            if(wheels.get(i).getLocked())
            {
                throw new SlotMachineException(SlotMachineException.SOME_WHEEL_LOCKED);
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
     * @return rueda correspondiente a la posicion recibida
     * @throws SlotMachineException NO_WHEELS si la maquina no tiene ruedas
     */
    public Wheel getWheel(int wheel) throws SlotMachineException
    {
        // Nueva condicion ciclo 3
        if(wheels.size() == 0)
        {
            throw new SlotMachineException(SlotMachineException.NO_WHEELS);
        }
        
        wheel = recalculateIndexes(wheel, 'g', 'w');
        
        Wheel w = wheels.get(wheel - 1);
        return w;
    }
    
    /**
     * Getter de simbolo
     * @param posicion del simbolo
     * @return simbolo correspondiente a la posicion recibida
     * @throws SlotMachineException si la maquina no tiene simbolos
     */
    public Symbol getSymbol(int pos) throws SlotMachineException
    {
        // Nueva condicion ciclo 3
        if(masterSymbols.size() == 0)
        {
            throw new SlotMachineException(SlotMachineException.NO_SYMBOLS);
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
     * @throws SlotMachineException NO_WHEELS si n es menor que 1, porque no se crea ninguna rueda que girar
     */
    public SlotMachine(int n) throws SlotMachineException
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
     * @return el indice recalculado
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