import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * This is a modification of the general purpose Canvas, specially made for
 * the BlueJ "shapes" example. 
 *
 * @author: Bruce Quig
 * @author: Michael Kolling (mik)
 *
 * @version: 1.6 (shapes)
 */
public class Canvas{
    // Note: The implementation of this class (specifically the handling of
    // shape identity and colors) is slightly more complex than necessary. This
    // is done on purpose to keep the interface and instance fields of the
    // shape objects in this project clean and simple for educational purposes.

    private static Canvas canvasSingleton;
    private static final Map<String, Color> CSS_COLORS = new HashMap<>();
    static {
        CSS_COLORS.put("aliceblue", Color.decode("#F0F8FF"));
        CSS_COLORS.put("antiquewhite", Color.decode("#FAEBD7"));
        CSS_COLORS.put("aqua", Color.decode("#00FFFF"));
        CSS_COLORS.put("aquamarine", Color.decode("#7FFFD4"));
        CSS_COLORS.put("azure", Color.decode("#F0FFFF"));
        CSS_COLORS.put("beige", Color.decode("#F5F5DC"));
        CSS_COLORS.put("bisque", Color.decode("#FFE4C4"));
        CSS_COLORS.put("black", Color.decode("#000000"));
        CSS_COLORS.put("blanchedalmond", Color.decode("#FFEBCD"));
        CSS_COLORS.put("blue", Color.decode("#0000FF"));
        CSS_COLORS.put("blueviolet", Color.decode("#8A2BE2"));
        CSS_COLORS.put("brown", Color.decode("#A52A2A"));
        CSS_COLORS.put("burlywood", Color.decode("#DEB887"));
        CSS_COLORS.put("cadetblue", Color.decode("#5F9EA0"));
        CSS_COLORS.put("chartreuse", Color.decode("#7FFF00"));
        CSS_COLORS.put("chocolate", Color.decode("#D2691E"));
        CSS_COLORS.put("coral", Color.decode("#FF7F50"));
        CSS_COLORS.put("cornflowerblue", Color.decode("#6495ED"));
        CSS_COLORS.put("cornsilk", Color.decode("#FFF8DC"));
        CSS_COLORS.put("crimson", Color.decode("#DC143C"));
        CSS_COLORS.put("cyan", Color.decode("#00FFFF"));
        CSS_COLORS.put("darkblue", Color.decode("#00008B"));
        CSS_COLORS.put("darkcyan", Color.decode("#008B8B"));
        CSS_COLORS.put("darkgoldenrod", Color.decode("#B8860B"));
        CSS_COLORS.put("darkgray", Color.decode("#A9A9A9"));
        CSS_COLORS.put("darkgreen", Color.decode("#006400"));
        CSS_COLORS.put("darkgrey", Color.decode("#A9A9A9"));
        CSS_COLORS.put("darkkhaki", Color.decode("#BDB76B"));
        CSS_COLORS.put("darkmagenta", Color.decode("#8B008B"));
        CSS_COLORS.put("darkolivegreen", Color.decode("#556B2F"));
        CSS_COLORS.put("darkorange", Color.decode("#FF8C00"));
        CSS_COLORS.put("darkorchid", Color.decode("#9932CC"));
        CSS_COLORS.put("darkred", Color.decode("#8B0000"));
        CSS_COLORS.put("darksalmon", Color.decode("#E9967A"));
        CSS_COLORS.put("darkseagreen", Color.decode("#8FBC8F"));
        CSS_COLORS.put("darkslateblue", Color.decode("#483D8B"));
        CSS_COLORS.put("darkslategray", Color.decode("#2F4F4F"));
        CSS_COLORS.put("darkslategrey", Color.decode("#2F4F4F"));
        CSS_COLORS.put("darkturquoise", Color.decode("#00CED1"));
        CSS_COLORS.put("darkviolet", Color.decode("#9400D3"));
        CSS_COLORS.put("deeppink", Color.decode("#FF1493"));
        CSS_COLORS.put("deepskyblue", Color.decode("#00BFFF"));
        CSS_COLORS.put("dimgray", Color.decode("#696969"));
        CSS_COLORS.put("dodgerblue", Color.decode("#1E90FF"));
        CSS_COLORS.put("firebrick", Color.decode("#B22222"));
        CSS_COLORS.put("floralwhite", Color.decode("#FFFAF0"));
        CSS_COLORS.put("forestgreen", Color.decode("#228B22"));
        CSS_COLORS.put("fuchsia", Color.decode("#FF00FF"));
        CSS_COLORS.put("gainsboro", Color.decode("#DCDCDC"));
        CSS_COLORS.put("ghostwhite", Color.decode("#F8F8FF"));
        CSS_COLORS.put("gold", Color.decode("#FFD700"));
        CSS_COLORS.put("goldenrod", Color.decode("#DAA520"));
        CSS_COLORS.put("gray", Color.decode("#808080"));
        CSS_COLORS.put("green", Color.decode("#008000"));
        CSS_COLORS.put("greenyellow", Color.decode("#ADFF2F"));
        CSS_COLORS.put("grey", Color.decode("#808080"));
        CSS_COLORS.put("honeydew", Color.decode("#F0FFF0"));
        CSS_COLORS.put("hotpink", Color.decode("#FF69B4"));
        CSS_COLORS.put("indianred", Color.decode("#CD5C5C"));
        CSS_COLORS.put("indigo", Color.decode("#4B0082"));
        CSS_COLORS.put("ivory", Color.decode("#FFFFF0"));
        CSS_COLORS.put("khaki", Color.decode("#F0E68C"));
        CSS_COLORS.put("lavender", Color.decode("#E6E6FA"));
        CSS_COLORS.put("lavenderblush", Color.decode("#FFF0F5"));
        CSS_COLORS.put("lawngreen", Color.decode("#7CFC00"));
        CSS_COLORS.put("lemonchiffon", Color.decode("#FFFACD"));
        CSS_COLORS.put("lightblue", Color.decode("#ADD8E6"));
        CSS_COLORS.put("lightcoral", Color.decode("#F08080"));
        CSS_COLORS.put("lightcyan", Color.decode("#E0FFFF"));
        CSS_COLORS.put("lightgoldenrodyellow", Color.decode("#FAFAD2"));
        CSS_COLORS.put("lightgray", Color.decode("#D3D3D3"));
        CSS_COLORS.put("lightgreen", Color.decode("#90EE90"));
        CSS_COLORS.put("lightgrey", Color.decode("#D3D3D3"));
        CSS_COLORS.put("lightpink", Color.decode("#FFB6C1"));
        CSS_COLORS.put("lightsalmon", Color.decode("#FFA07A"));
        CSS_COLORS.put("lightseagreen", Color.decode("#20B2AA"));
        CSS_COLORS.put("lightskyblue", Color.decode("#87CEFA"));
        CSS_COLORS.put("lightslategray", Color.decode("#778899"));
        CSS_COLORS.put("lightslategrey", Color.decode("#778899"));
        CSS_COLORS.put("lightsteelblue", Color.decode("#B0C4DE"));
        CSS_COLORS.put("lightyellow", Color.decode("#FFFFE0"));
        CSS_COLORS.put("lime", Color.decode("#00FF00"));
        CSS_COLORS.put("limegreen", Color.decode("#32CD32"));
        CSS_COLORS.put("linen", Color.decode("#FAF0E6"));
        CSS_COLORS.put("magenta", Color.decode("#FF00FF"));
        CSS_COLORS.put("maroon", Color.decode("#800000"));
        CSS_COLORS.put("mediumaquamarine", Color.decode("#66CDAA"));
        CSS_COLORS.put("mediumblue", Color.decode("#0000CD"));
        CSS_COLORS.put("mediumorchid", Color.decode("#BA55D3"));
        CSS_COLORS.put("mediumpurple", Color.decode("#9370DB"));
        CSS_COLORS.put("mediumseagreen", Color.decode("#3CB371"));
        CSS_COLORS.put("mediumslateblue", Color.decode("#7B68EE"));
        CSS_COLORS.put("mediumspringgreen", Color.decode("#00FA9A"));
        CSS_COLORS.put("mediumturquoise", Color.decode("#48D1CC"));
        CSS_COLORS.put("mediumvioletred", Color.decode("#C71585"));
        CSS_COLORS.put("midnightblue", Color.decode("#191970"));
        CSS_COLORS.put("mintcream", Color.decode("#F5FFFA"));
        CSS_COLORS.put("mistyrose", Color.decode("#FFE4E1"));
        CSS_COLORS.put("moccasin", Color.decode("#FFE4B5"));
        CSS_COLORS.put("navajowhite", Color.decode("#FFDEAD"));
        CSS_COLORS.put("navy", Color.decode("#000080"));
        CSS_COLORS.put("oldlace", Color.decode("#FDF5E6"));
        CSS_COLORS.put("olive", Color.decode("#808000"));
        CSS_COLORS.put("olivedrab", Color.decode("#6B8E23"));
        CSS_COLORS.put("orange", Color.decode("#FFA500"));
        CSS_COLORS.put("orangered", Color.decode("#FF4500"));
        CSS_COLORS.put("orchid", Color.decode("#DA70D6"));
        CSS_COLORS.put("palegoldenrod", Color.decode("#EEE8AA"));
        CSS_COLORS.put("palegreen", Color.decode("#98FB98"));
        CSS_COLORS.put("paleturquoise", Color.decode("#AFEEEE"));
        CSS_COLORS.put("palevioletred", Color.decode("#DB7093"));
        CSS_COLORS.put("papayawhip", Color.decode("#FFEFD5"));
        CSS_COLORS.put("peachpuff", Color.decode("#FFDAB9"));
        CSS_COLORS.put("peru", Color.decode("#CD853F"));
        CSS_COLORS.put("pink", Color.decode("#FFC0CB"));
        CSS_COLORS.put("plum", Color.decode("#DDA0DD"));
        CSS_COLORS.put("powderblue", Color.decode("#B0E0E6"));
        CSS_COLORS.put("purple", Color.decode("#800080"));
        CSS_COLORS.put("rebeccapurple", Color.decode("#663399"));
        CSS_COLORS.put("red", Color.decode("#FF0000"));
        CSS_COLORS.put("rosybrown", Color.decode("#BC8F8F"));
        CSS_COLORS.put("royalblue", Color.decode("#4169E1"));
        CSS_COLORS.put("saddlebrown", Color.decode("#8B4513"));
        CSS_COLORS.put("salmon", Color.decode("#FA8072"));
        CSS_COLORS.put("sandybrown", Color.decode("#F4A460"));
        CSS_COLORS.put("seagreen", Color.decode("#2E8B57"));
        CSS_COLORS.put("seashell", Color.decode("#FFF5EE"));
        CSS_COLORS.put("sienna", Color.decode("#A0522D"));
        CSS_COLORS.put("silver", Color.decode("#C0C0C0"));
        CSS_COLORS.put("skyblue", Color.decode("#87CEEB"));
        CSS_COLORS.put("slateblue", Color.decode("#6A5ACD"));
        CSS_COLORS.put("slategray", Color.decode("#708090"));
        CSS_COLORS.put("slategrey", Color.decode("#708090"));
        CSS_COLORS.put("snow", Color.decode("#FFFAFA"));
        CSS_COLORS.put("springgreen", Color.decode("#00FF7F"));
        CSS_COLORS.put("steelblue", Color.decode("#4682B4"));
        CSS_COLORS.put("tan", Color.decode("#D2B48C"));
        CSS_COLORS.put("teal", Color.decode("#008080"));
        CSS_COLORS.put("thistle", Color.decode("#D8BFD8"));
        CSS_COLORS.put("tomato", Color.decode("#FF6347"));
        CSS_COLORS.put("turquoise", Color.decode("#40E0D0"));
        CSS_COLORS.put("violet", Color.decode("#EE82EE"));
        CSS_COLORS.put("wheat", Color.decode("#F5DEB3"));
        CSS_COLORS.put("white", Color.decode("#FFFFFF"));
        CSS_COLORS.put("whitesmoke", Color.decode("#F5F5F5"));
        CSS_COLORS.put("yellow", Color.decode("#FFFF00"));
        CSS_COLORS.put("yellowgreen", Color.decode("#9ACD32"));
    }

    /**
     * Factory method to get the canvas singleton object.
     */
    public static Canvas getCanvas(){
        if(canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Shapes Demo", 1920, 500, 
                                         Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    //  ----- instance part -----

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List <Object> objects;
    private HashMap <Object,ShapeDescription> shapes;
    
    /**
     * Create a Canvas.
     * @param title  title to appear in Canvas Frame
     * @param width  the desired width for the canvas
     * @param height  the desired height for the canvas
     * @param bgClour  the desired background colour of the canvas
     */
    private Canvas(String title, int width, int height, Color bgColour){
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList <Object>();
        shapes = new HashMap <Object,ShapeDescription>();
    }

    /**
     * Set the canvas visibility and brings canvas to the front of screen
     * when made visible. This method can also be used to bring an already
     * visible canvas to the front of other windows.
     * @param visible  boolean value representing the desired visibility of
     * the canvas (true or false) 
     */
    public void setVisible(boolean visible){
        if(graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background colour
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Draw a given shape onto the canvas.
     * @param  referenceObject  an object to define identity for this shape
     * @param  color            the color of the shape
     * @param  shape            the shape object to be drawn on the canvas
     */
     // Note: this is a slightly backwards way of maintaining the shape
     // objects. It is carefully designed to keep the visible shape interfaces
     // in this project clean and simple for educational purposes.
    public void draw(Object referenceObject, String color, Shape shape){
        objects.remove(referenceObject);   // just in case it was already there
        objects.add(referenceObject);      // add at the end
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }
 
    /**
     * Erase a given shape's from the screen.
     * @param  referenceObject  the shape object to be erased 
     */
    public void erase(Object referenceObject){
        objects.remove(referenceObject);   // just in case it was already there
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Set the foreground colour of the Canvas.
     * @param  newColour   the new colour for the foreground of the Canvas 
     */
      public void setForegroundColor(String colorString){
        Color color = CSS_COLORS.get(colorString.toLowerCase());
        if (color != null) {
            graphic.setColor(color);
        } else {
            graphic.setColor(Color.black);
        }
    }
    
    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     * @param  milliseconds  the number 
     */
    public void wait(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e){
            // ignoring exception at the moment
        }
    }

    /**
     * Redraw ell shapes currently on the Canvas.
     */
    private void redraw(){
        erase();
        for(Iterator i=objects.iterator(); i.hasNext(); ) {
                       shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
    }
       
    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase(){
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }
    
    public static ArrayList<String> getAvailableColorNames(){
    return new ArrayList<String>(CSS_COLORS.keySet());
    }   

    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel{
        public void paint(Graphics g){
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
    
    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class ShapeDescription{
        private Shape shape;
        private String colorString;

        public ShapeDescription(Shape shape, String color){
            this.shape = shape;
            colorString = color;
        }

        public void draw(Graphics2D graphic){
            setForegroundColor(colorString);
            graphic.draw(shape);
            graphic.fill(shape);
        }
    }

}
