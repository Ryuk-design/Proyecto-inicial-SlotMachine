import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 * @author (Samuel Ahumada - Nerieth Sofia) 
 * @version (1.0)
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
    public SlotMachine(){
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
    public boolean isJackpot(){
        if (wheels.size() < 2){
            return false;
        }
        Symbol ref = wheels.get(0).getCurrentSymbol();
        for(int i = 1; i < wheels.size(); i++){
            Symbol cur = wheels.get(i).getCurrentSymbol();
            if(ref.isSameSymbol(cur) == false){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Cambia el color de la maquina si es Jackpot o si no es Jackpot
     */
    private void updateJackpotStatus(){
        if(isJackpot()){
            frame.changeColor(JACKPOT_FRAME_COLOR);
        } else {
            frame.changeColor(NORMAL_FRAME_COLOR);
        }
        makeVisible();
    }
    
    /**
     * En caso de que la maquina y sus componentes sean visibles, si el usuario intenta hacer una accion que desencadene en un error, el metodo se encarga de darle un mensaje de advertencia. 
     */
    private void showError(String message){
        if(isVisible){
            JOptionPane.showMessageDialog(null, message);
        }
    }
    
    /**
     * Calcula automaticamente las posiciones de cada Wheel
     */
    private void recalculatePositions(){
        for (int i = 0; i < wheels.size(); i++){
            int x = FRAME_X + MARGIN + i * (Wheel.WINDOW_WIDTH + GAP);
            wheels.get(i).setPosition(x, FRAME_Y + MARGIN);
        }
    }
    
    /**
     * Agrega una nueva rueda a SlotMachine 
     */
    public void addWheel(int pos){
        Wheel whl = new Wheel(0, 0);
        if(pos < 1){
            pos = 1;
        } else if (pos > wheels.size() + 1){pos = wheels.size() + 1;}
        
        for(int i = 0; i < masterSymbols.size(); i++){
            whl.addSymbol(i + 1, masterSymbols.get(i).getColor());
        }
        wheels.add(pos - 1, whl);
        recalculatePositions();
        if(isVisible){
            whl.makeVisible();
        }
        lastOperationOk = true;
    }
    
    /**
     * Elimina una rueda en una posicion determinada. 
     */
    public void delWheel(int pos){
        if(wheels.size() == 0){
            lastOperationOk = false;
            showError("No se puede eliminar ruedas de una lista de ruedas vacia");
            return;
        }else if(pos < 1){
                pos = 1;
        }else if (pos > wheels.size()){
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
    public void addSymbol(int pos, String color){
        if(availableColors.contains(color) == false){
            lastOperationOk = false;
            showError("El color que se busca asignar a un nuevo simbolo ya esta siendo utilizado o el color no existe");
            return;
        }
        if(pos < 1){
            pos = 1;
        }else if (pos > masterSymbols.size() + 1){
            pos = masterSymbols.size() + 1;
        }
        Symbol sym = new Symbol(color, 0, 0);
        masterSymbols.add(pos - 1, sym);
        availableColors.remove(color);
        for(int i = 0; i < wheels.size(); i++){
            wheels.get(i).addSymbol(pos, color);
        }
        lastOperationOk = true;
    }
    
    /**
     *  Elimina un Symbol de masterSymbols y a cada rueda y permite que el color que utilizaba el Symbol eliminado pueda ser reutilizado
     */
    public void delSymbol(String symbol){
        for(int i = 0; i < masterSymbols.size(); i++){
            if (symbol.equals(masterSymbols.get(i).getColor())){
                masterSymbols.remove(i);
                for(int j = 0; j < wheels.size(); j++){
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
    public void placeSymbol(int wheel, String symbol){
        if(wheels.size() == 0){
            lastOperationOk = false;
            showError("No hay ruedas existentes");
            return;
        } else if (wheel < 1){
            wheel = 1;        
        } else if (wheel > wheels.size()){
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
        if(wheels.size() == 0){
            lastOperationOk = false;
            showError("No hay ruedas existentes");
            return;
        }
        for(int i = 0; i < wheels.size(); i++){
            wheels.get(i).spin();
        }
        lastOperationOk = true;
        updateJackpotStatus();
    }
    
    /**
     * Gira una Wheel en especifico 
     */
    public void spin(int wheel){
        if(wheels.size() == 0){
            lastOperationOk = false;
            showError("No hay ruedas existentes");
            return;
        }
        if(wheel < 1){wheel = 1;}
        if(wheel > wheels.size()){wheel = wheels.size();}
        wheels.get(wheel - 1).spin();
        lastOperationOk = true;
        updateJackpotStatus();
    }
    
    /**
     * @return Retorna una lista conlos colores de los simbolos en el orden que estan en todas las Wheels iniciando por el 1
    */
    public String[] configuration(){
        String[] config = new String[wheels.size()];
        for(int i = 0; i < wheels.size(); i++){
            config[i] = wheels.get(i).getCurrentSymbol().getColor();
        }
        return config;
    }
    
    /**
     * @return retorna los colores de los Symbols en el orden que estan en masterSymbol iniciando por el 1
     */
    public String[] symbols(){
        String[] symbs = new String[masterSymbols.size()];
        for(int i = 0; i < masterSymbols.size(); i++){
            symbs[i] = masterSymbols.get(i).getColor();
        }
        return symbs;
    }
    
    /**
     * Dice que tantos colores distintos hay entre los Symbols que muestran las Wheels 
     */
    public int distinctSymbols(){
        String[] config = configuration();
        ArrayList<String> distinct = new ArrayList<String>();
        for(int i = 0; i < config.length; i++){
            if(distinct.contains(config[i]) == false){
                distinct.add(config[i]);
            }
        }
        return distinct.size();
    }
    
    public void makeVisible(){
        frame.makeVisible();
        for(int i = 0; i < wheels.size(); i++){
            wheels.get(i).makeVisible();
        }
        isVisible = true;
        lastOperationOk = true;
    }
    
    public void makeInvisible(){
        frame.makeInvisible();
        for(int i = 0; i < wheels.size(); i++){
            wheels.get(i).makeInvisible();
        }
        isVisible = false;
        lastOperationOk = true;
    }
    
    public void exit(){
        makeInvisible();
        lastOperationOk = true;
    }
    
    /**
     * @return getter de lastOperationOk 
     */
    public boolean ok(){
        return lastOperationOk;
    }
}    