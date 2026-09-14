package SlotMachineC2Test;
import SlotMachine_Package.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SpinSetSymbolsTests.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SpinSetSymbolsTests
{
    private SlotMachine machine;
    private String[] col = {"red", "blue", "green", "yellow", "brown"};
    private String[] col2 = {"darkgoldenrod", "gold", "navajowhite", "rebeccapurple", "yellowgreen"};
    private String[] col3 = {"darkgoldenrod", "gold", "navajowhite", "rebeccapurple", "black"};
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
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col2[i - 1]);
        }
    }
    
    @Test
    public void AfCvShouldPutInSlotMachineTheGivenConfiguration()
    {
        machine.spin(col2);
        assertArrayEquals(col2, machine.configuration());
        assertTrue(machine.ok());
    }
    
    @Test
    public void AfCvShouldNotPutInSlotMachineTheGivenConfigurationIfThereIsNoWheels()
    {
        for(int i = 1; i <= 5; i++)
        {
            machine.delWheel(i);
        }
        
        machine.spin(col2);
        assertFalse(machine.ok());
    }
    
    @Test
    public void AfCvShouldNotSpinIfConfigurationDoesNotBelongsToMasterSymbols()
    {
        String[] conbef = machine.configuration();
        machine.spin(col3);
        assertArrayEquals(conbef, machine.configuration());
        assertFalse(machine.ok());
    }
    
    @Test
    public void AfCvShouldNotSpinIfWheelLocked()
    {
        String[] conbef = machine.configuration();
        machine.lock(3);
        machine.spin(col2);
        assertArrayEquals(conbef, machine.configuration());
        assertFalse(machine.ok());
    }
    
    @AfterEach
    public void tearDown()
    {
    }
}
