
/**
 * Write a description of class Symbol here.
 * 
 * @author (Samuel Ahumada) 
 * @version (a version number or a date)
 */
public class Symbol
{
    private Circle figure;
    private String color;
    private boolean isVisible;
    public static final int DIAMETER = 80;

    /**
     * @author Samuel Ahumada
     * @param color asigna color a Symbol
     * @param x asigna posicion horizontal a Symbol
     * @param y asigna posicion vertical a Symbol
     */
    public Symbol(String color, int x, int y){
        this.color = color;
        figure = new Circle(DIAMETER, x, y, this.color);
        isVisible = false;
    }
    
    /**
     * @author Samuel Ahumada
     * @return el color asignado a Symbol
     */
    public String getColor(){
        return color;
    }
    
    /**
     * Hace visible Symbol en canvas.
     * @author Samuel Ahumada
     */
    public void makeVisible(){
        figure.makeVisible();
        isVisible = true;
    }
    
    /**
     * Hace invisible Symbol en canvas.
     * @author Samuel Ahumada
     */
    public void makeInvisible(){
        figure.makeInvisible();
        isVisible = false;
    }
    
    /**
     * Compara el color de dos Symbols y dice si son iguales o distintos
     * @author Samuel Ahumada
     */
    public boolean isSameSymbol(Symbol sym2){
        if(color.equals(sym2.getColor())){
            return true;
        }else{return false;}
    }
    
    /**
     * Cambia la posicion horizontal y vertical de Symbol
     * @author Samuel Ahumada
     * @param x asigna posicion horizontal a Symbol
     * @param y asigna posicion vertical a Symbol
     */
    public void setPosition(int x, int y){
        figure.setPosition(x, y);
    }
    
    /**
     * getter de visibilidad
     * @author Samuel Ahumada
     */
    public boolean isVisible(){
        return isVisible;
    }
}