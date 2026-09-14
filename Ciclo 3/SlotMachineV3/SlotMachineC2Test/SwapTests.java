package SlotMachineC2Test;
import SlotMachine_Package.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SwapTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SwapTests
{
    private SlotMachine machine;
    @BeforeEach
    public void setUpSwap()
    {   
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
            machine.addWheel(i);
            machine.placeSymbol(i, col[i - 1]);
        }
    }
    
    @Test
    public void AfCvShouldSwapTwoExistingWheels()
    {
        String[] shouldBe = {"green", "blue", "red", "yellow", "brown"};
        machine.swap(1,3);
        assertArrayEquals(shouldBe, machine.configuration());
        assertTrue(machine.ok());
    }

    @Test
    public void AfCvShouldNotSwapIfPositionsAreTheSame()
    {
        String[] orig = machine.configuration();
        machine.swap(1,1);
        assertArrayEquals(orig, machine.configuration());
        assertTrue(machine.ok());
    }
    
    @Test
    //Si el indice es menor que uno, se reacomoda en la posicion 1.
    public void AfCvShouldSwapIfIndexOutOfRange()
    {
        String[] shouldBe = {"green", "blue", "red", "yellow", "brown"};
        machine.swap(0,3);
        assertArrayEquals(shouldBe, machine.configuration());
        assertTrue(machine.ok());
    }
    
    
    @Test
    //Si el indice es mayor que el tamaño del arreglo, este se reacomoda al final del arreglo.
    public void AfCvShouldSwapIfIndexOutOfRange2()
    {
        String[] shouldBe = {"brown", "blue", "green", "yellow", "red"};
        machine.swap(1,6);
        assertArrayEquals(shouldBe, machine.configuration());
        assertTrue(machine.ok());
    }
}