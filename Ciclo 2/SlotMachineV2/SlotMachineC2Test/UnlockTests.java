package SlotMachineC2Test;
import SlotMachine_Package.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class UnLockTests.
 *
 * @author  (AhumadaF - CampoV)
 */
public class UnlockTests
{
    private SlotMachine machine;
    @BeforeEach
    public void setUpUnlock()
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
    }
    
    @Test
    public void AfCvShouldUnlockLockedWheel()
    {   
        int wtl = 1;
        machine.lock(wtl);
        machine.unlock(wtl);
        assertFalse(machine.getWheel(wtl).getLocked());
        assertTrue(machine.ok());
    }
    
    @Test
    public void AfCvShouldNotUnlockUnlockedWheel()

    {
        int wtl = 1;
        machine.unlock(wtl);
        assertFalse(machine.getWheel(wtl).getLocked());
        assertFalse(machine.ok());
    }
    
    @Test
    //Si el indice es menor que uno, se reacomoda en la posicion 1.
    public void AfCvShouldUnlockWheelIfIndexOutOfRange()
    {
        int wtl = 0;
        machine.lock(wtl);
        machine.unlock(wtl);
        assertFalse(machine.getWheel(wtl).getLocked());
        machine.ok();
    }
    
    @Test
    //Si el indice es mayor que el tamaño del arreglo, este se reacomoda al final del arreglo.
    public void AfCvShouldUnlockWheelIfIndexOutOfRange2()
    {
        int wtl = 999;
        machine.lock(wtl);
        machine.unlock(wtl);
        assertFalse(machine.getWheel(wtl).getLocked());
        machine.ok();
    }

    @AfterEach
    public void tearDown()
    {
        machine = null;
    }
}
