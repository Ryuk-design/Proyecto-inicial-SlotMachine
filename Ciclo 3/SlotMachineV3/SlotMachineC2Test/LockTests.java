package SlotMachineC2Test;
import SlotMachine_Package.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class LockTests.
 *
 * @author  (AhumadaF - CampoV)
 */
public class LockTests
{
    private SlotMachine machine;
    @BeforeEach
    public void setUpLock()
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
    //
    public void AfCvShouldLockUnlockedWheel()
    {   
        int wtl = 1;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        assertTrue(machine.ok());
    }
    
    @Test
    //
    public void AfCvShouldNotLockLockedWheel()
    {
        int wtl = 1;
        machine.lock(wtl);
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        assertFalse(machine.ok());
    }
    
    @Test
    //Si el indice es menor que uno, se reacomoda en la posicion 1.
    //
    public void AfCvLockWheelIfIndexOutOfRange()
    {
        int wtl = 0;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        machine.ok();
    }
    
    @Test
    //Si el indice es mayor que el tamaño del arreglo, este se reacomoda al final del arreglo.
    //
    public void AfCvShouldLockWheelIfIndexOutOfRange2()
    {
        int wtl = 999;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        assertTrue(machine.ok());
    }
    
    @Test
    //
    public void AfCvShouldNotSpin1IfLocked()
    {
        int wtl = 1;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        machine.spin();
        assertFalse(machine.ok());
    }
    
    @Test
    public void AfCvShouldNotSpin2IfLocked()
    {
        int wtl = 1;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        machine.spin(wtl);
        assertFalse(machine.ok());
    }
    
    @Test
    public void AfCvShouldNotPlaceSymbolIfLocked()
    {
        int wtl = 1;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        machine.placeSymbol(wtl, "red");
        assertFalse(machine.ok());
    }

    @Test
    public void AfCvShouldNotSpinStepsIfLocked()
    {
        int wtl = 1;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        machine.spin(wtl, 3);
        assertFalse(machine.ok());
    }
    
    @Test
    public void AfCvShouldNotsetSymbolsIfLocked()
    {
        int wtl = 1;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        String[] a = {"red"};
        machine.spin(a);
        assertFalse(machine.ok());
    }
    
    @Test
    public void AfCvShouldAddSymbolIfLocked()
    {
        int wtl = 1;
        machine.placeSymbol(wtl, "red");
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        machine.addSymbol(1, "blueviolet");
        assertEquals(machine.getSymbol(1).getColor(), "blueviolet");
        assertEquals(machine.getWheel(wtl).getCurrentSymbol().getColor(), "red");
    }
    
    @Test
    public void AfCvShouldNotDeleteSymbolIfLocked()
    {
        int wtl = 1;
        machine.placeSymbol(wtl, "red");
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        machine.delSymbol("red");
        assertEquals(machine.getWheel(wtl).getCurrentSymbol().getColor(), "red");
    }    
    
    @AfterEach
    public void tearDown()
    {
    }
}