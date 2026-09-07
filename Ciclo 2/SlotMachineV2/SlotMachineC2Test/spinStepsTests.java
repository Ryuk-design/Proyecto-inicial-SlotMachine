package SlotMachineC2Test;
import SlotMachine_Package.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class spinStepsTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class spinStepsTests
{
    private SlotMachine machine;
    private String[] col = {"red", "blue", "green", "yellow", "brown"};
    @BeforeEach
    public void setUp()
    {   
        machine = new SlotMachine();
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
            machine.addWheel(i);
            machine.placeSymbol(i, col[i - 1]);
        }
    }
    
    @Test
    public void AfCvShouldSpinAnyNumberOfStepsInIntegerRange()
    {
        machine.placeSymbol(1, "red");
        machine.spin(1, 12);
        assertEquals(machine.getWheel(1).getCurrentSymbol().getColor(), "green");
        assertTrue(machine.ok());
    }
    
    @Test
    public void AfCvShouldNotSpinIfThereIsNoSymbols()
    {
        for(int i = 1; i <= 5; i++)
        {
            machine.delSymbol(col[i - 1]);
        }
        machine.spin(1, 12);
        assertFalse(machine.ok());
    }
    
    @Test
    //Si el indice es menor que uno, se reacomoda en la posicion 1.
    public void AfCvShouldSpinIfIndexOutOfRange()
    {
        machine.placeSymbol(1, "red");
        machine.spin(0, 1);
        assertEquals(machine.getWheel(1).getCurrentSymbol().getColor(), "blue");
        assertTrue(machine.ok());
    }
   
    @Test
    //Si el indice es mayor que el tamaño del arreglo, este se reacomoda al final del arreglo.
    public void AfCvShouldSpinIfIndexOutOfRange2()
    {
        machine.placeSymbol(1, "red");
        machine.spin(999, 3);
        assertEquals("green", machine.getWheel(999).getCurrentSymbol().getColor());
        assertTrue(machine.ok());
    }
    
    @Test
    public void AfCvShouldNotSpinIfWheelLocked()
    {
        machine.placeSymbol(1, "red");
        machine.lock(1);
        machine.spin(1, 870);
        assertEquals(machine.getWheel(1).getCurrentSymbol().getColor(), "red");
        assertFalse(machine.ok());
    }
    
    @AfterEach
    public void tearDown()
    {
    }
}