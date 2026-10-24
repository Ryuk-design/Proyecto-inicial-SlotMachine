package SlotMachine_Package;
import SlotMachine_Package.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SlotMachineC2Test
{
    private SlotMachine machine;
    private String[] col = {"red", "blue", "green", "yellow", "brown"};
    private String[] col2 = {"darkgoldenrod", "gold", "navajowhite", "rebeccapurple", "yellowgreen"};
    private String[] col3 = {"darkgoldenrod", "gold", "navajowhite", "rebeccapurple", "black"};
    
    //CICLO 2
    
    //SwapTest
    
    @Test
    public void ShouldSwapTwoExistingWheels() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
            machine.addWheel(i);
            machine.placeSymbol(i, col[i - 1]);
        }
        
        String[] shouldBe = {"green", "blue", "red", "yellow", "brown"};
        machine.swap(1,3);
        assertArrayEquals(shouldBe, machine.configuration());
        assertTrue(machine.ok());
    }

    @Test
    public void ShouldNotSwapIfPositionsAreTheSame() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
            machine.addWheel(i);
            machine.placeSymbol(i, col[i - 1]);
        }
        
        String[] orig = machine.configuration();
        machine.swap(1,1);
        assertArrayEquals(orig, machine.configuration());
        assertTrue(machine.ok());
    }
    
    @Test
    //Si el indice es menor que uno, se reacomoda en la posicion 1.
    public void ShouldSwapIfIndexOutOfRange() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
            machine.addWheel(i);
            machine.placeSymbol(i, col[i - 1]);
        }
        
        String[] shouldBe = {"green", "blue", "red", "yellow", "brown"};
        machine.swap(0,3);
        assertArrayEquals(shouldBe, machine.configuration());
        assertTrue(machine.ok());
    }
    
    
    @Test
    //Si el indice es mayor que el tamaño del arreglo, este se reacomoda al final del arreglo.
    public void ShouldSwapIfIndexOutOfRange2() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
            machine.addWheel(i);
            machine.placeSymbol(i, col[i - 1]);
        }
        
        String[] shouldBe = {"brown", "blue", "green", "yellow", "red"};
        machine.swap(1,6);
        assertArrayEquals(shouldBe, machine.configuration());
        assertTrue(machine.ok());
    }
    
    //LockTest
    
    @Test
    public void ShouldLockUnlockedWheel() throws SlotMachineException
    {   
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 1;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        assertTrue(machine.ok());
    }
    
    @Test
    public void ShouldNotLockLockedWheel() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 1;
        machine.lock(wtl);
        
        try
        {
            machine.lock(wtl);
            fail();
        }
        catch(SlotMachineException e)
        {
            assertEquals(SlotMachineException.WHEEL_ALREADY_LOCKED, e.getMessage());
        }
        
        assertTrue(machine.getWheel(wtl).getLocked());
        assertFalse(machine.ok());
    }
    
    @Test
    //Si el indice es menor que uno, se reacomoda en la posicion 1.
    public void LockWheelIfIndexOutOfRange() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 0;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        machine.ok();
    }
    
    @Test
    //Si el indice es mayor que el tamaño del arreglo, este se reacomoda al final del arreglo.
    public void ShouldLockWheelIfIndexOutOfRange2() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 999;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        assertTrue(machine.ok());
    }
    
    @Test
    public void ShouldNotSpin1IfLocked() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 1;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        
        try
        {
            machine.spin();
            fail();
        }
        catch(SlotMachineException e)
        {
            assertEquals(SlotMachineException.SOME_WHEEL_LOCKED, e.getMessage());
        }
        
        assertFalse(machine.ok());
    }
    
    @Test
    public void ShouldNotSpin2IfLocked() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 1;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        
        try
        {
            machine.spin(wtl);
            fail();
        }
        catch(SlotMachineException e)
        {
            assertEquals(SlotMachineException.WHEEL_LOCKED, e.getMessage());
        }
        
        assertFalse(machine.ok());
    }
    
    @Test
    public void ShouldNotPlaceSymbolIfLocked() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 1;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        
        try
        {
            machine.placeSymbol(wtl, "red");
            fail();
        }
        catch(SlotMachineException e)
        {
            assertEquals(SlotMachineException.WHEEL_LOCKED, e.getMessage());
        }
        
        assertFalse(machine.ok());
    }

    @Test
    public void ShouldNotSpinStepsIfLocked() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 1;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        
        try
        {
            machine.spin(wtl, 3);
            fail();
        }
        catch(SlotMachineException e)
        {
            assertEquals(SlotMachineException.WHEEL_LOCKED, e.getMessage());
        }

        assertFalse(machine.ok());
    }
    
    @Test
    public void ShouldNotsetSymbolsIfLocked() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 1;
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        String[] a = {"red"};
        try
        {
            machine.spin(a);
            fail();
        }
        catch(SlotMachineException e)
        {
            assertEquals(SlotMachineException.SOME_WHEEL_LOCKED, e.getMessage());
        }
        
        assertFalse(machine.ok());
    }
    
    @Test
    public void ShouldAddSymbolIfLocked() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 1;
        machine.placeSymbol(wtl, "red");
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        machine.addSymbol(1, "blueviolet");
        assertEquals(machine.getSymbol(1).getColor(), "blueviolet");
        assertEquals(machine.getWheel(wtl).getCurrentSymbol().getColor(), "red");
    }
    
    @Test
    public void ShouldNotDeleteSymbolIfLocked() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 1;
        machine.placeSymbol(wtl, "red");
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());

        try
        {
            machine.delSymbol("red");
            fail();
        }
        catch(SlotMachineException e)
        {
            assertEquals(SlotMachineException.SOME_WHEEL_LOCKED, e.getMessage());
        }
        
        assertEquals(machine.getWheel(wtl).getCurrentSymbol().getColor(), "red");
    }
    
    //UnlockTest
    
    @Test
    public void ShouldUnlockLockedWheel() throws SlotMachineException
    {   
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 1;
        machine.lock(wtl);
        machine.unlock(wtl);
        assertFalse(machine.getWheel(wtl).getLocked());
        assertTrue(machine.ok());
    }
    
    @Test
    public void ShouldNotUnlockUnlockedWheel() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 1;
        
        try
        {
            machine.unlock(wtl);
            fail();
        }
        catch(SlotMachineException e)
        {
            assertEquals(SlotMachineException.WHEEL_ALREADY_UNLOCKED, e.getMessage());
        }
        
        assertFalse(machine.getWheel(wtl).getLocked());
        assertFalse(machine.ok());
    }
    
    @Test
    //Si el indice es menor que uno, se reacomoda en la posicion 1.
    public void ShouldUnlockWheelIfIndexOutOfRange() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 0;
        machine.lock(wtl);
        machine.unlock(wtl);
        assertFalse(machine.getWheel(wtl).getLocked());
        machine.ok();
    }
    
    @Test
    //Si el indice es mayor que el tamaño del arreglo, este se reacomoda al final del arreglo.
    public void ShouldUnlockWheelIfIndexOutOfRange2() throws SlotMachineException
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 999;
        machine.lock(wtl);
        machine.unlock(wtl);
        assertFalse(machine.getWheel(wtl).getLocked());
        machine.ok();
    }
    
    //SpinStepsTests
    
    @Test
    //Sustentacion Samuel
    public void ShouldSpinAnyNumberOfStepsInIntegerRange() throws SlotMachineException
    {
        machine = new SlotMachine();
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
            machine.addWheel(i);
            machine.placeSymbol(i, col[i - 1]);
        }
        
        machine.placeSymbol(1, "red");
        // machine.makeVisible();
        machine.spin(1, 12);
        assertEquals(machine.getWheel(1).getCurrentSymbol().getColor(), "green");
        assertTrue(machine.ok());
    }
    
    @Test
    public void ShouldNotSpinIfThereIsNoSymbols() throws SlotMachineException
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
            machine.delSymbol(col[i - 1]);
        }
        
        try
        {
            machine.spin(1, 12);
            fail();
        }
        catch(SlotMachineException e)
        {
            assertEquals(SlotMachineException.NO_SYMBOLS_TO_WALK, e.getMessage());
        }
        
        assertFalse(machine.ok());
    }
    
    @Test
    //Si el indice es menor que uno, se reacomoda en la posicion 1.
    public void ShouldSpinIfIndexOutOfRange() throws SlotMachineException
    {
        machine = new SlotMachine();
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
            machine.addWheel(i);
            machine.placeSymbol(i, col[i - 1]);
        }
        
        machine.placeSymbol(1, "red");
        machine.spin(0, 1);
        assertEquals(machine.getWheel(1).getCurrentSymbol().getColor(), "blue");
        assertTrue(machine.ok());
    }
   
    @Test
    //Si el indice es mayor que el tamaño del arreglo, este se reacomoda al final del arreglo.
    public void ShouldSpinIfIndexOutOfRange2() throws SlotMachineException
    {
        machine = new SlotMachine();
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
            machine.addWheel(i);
            machine.placeSymbol(i, col[i - 1]);
        }
        
        machine.placeSymbol(1, "red");
        machine.spin(999, 3);
        assertEquals("green", machine.getWheel(999).getCurrentSymbol().getColor());
        assertTrue(machine.ok());
    }
    
    @Test
    public void ShouldNotSpinIfWheelLocked() throws SlotMachineException
    {
        machine = new SlotMachine();
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
            machine.addWheel(i);
            machine.placeSymbol(i, col[i - 1]);
        }
        
        machine.placeSymbol(1, "red");
        machine.lock(1);
        try
        {
            machine.spin(1, 870);
            fail();
        }
        catch(SlotMachineException e)
        {
            assertEquals(SlotMachineException.WHEEL_LOCKED, e.getMessage());
        }
        
        assertEquals(machine.getWheel(1).getCurrentSymbol().getColor(), "red");
        assertFalse(machine.ok());
    }
    
    //SpinSetSymbolsTest
    
    @Test
    public void ShouldPutInSlotMachineTheGivenConfiguration() throws SlotMachineException
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
        
        machine.spin(col2);
        assertArrayEquals(col2, machine.configuration());
        assertTrue(machine.ok());
    }
    
    @Test
    public void ShouldNotPutInSlotMachineTheGivenConfigurationIfThereIsNoWheels() throws SlotMachineException
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
        
        for(int i = 1; i <= 5; i++)
        {
            machine.delWheel(i);
        }
        
        try
        {
            machine.spin(col2);
            fail();
        }
        catch(SlotMachineException e)
        {
            assertEquals(SlotMachineException.NO_WHEELS, e.getMessage());
        }
        
        assertFalse(machine.ok());
    }
    
    @Test
    public void ShouldNotSpinIfConfigurationDoesNotBelongsToMasterSymbols() throws SlotMachineException
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
        
        String[] conbef = machine.configuration();
        try
        {
            machine.spin(col3);
            fail();
        }
        catch(SlotMachineException e)
        {
            assertEquals(SlotMachineException.INVALID_CONFIGURATION, e.getMessage());
        }
        
        assertArrayEquals(conbef, machine.configuration());
        assertFalse(machine.ok());
    }
    
    @Test
    public void ShouldNotSpinIfWheelLocked2() throws SlotMachineException
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
        
        String[] conbef = machine.configuration();
        machine.lock(3);
        try
        {
            machine.spin(col2);
            fail();
        }
        catch(SlotMachineException e)
        {
            assertEquals(SlotMachineException.SOME_WHEEL_LOCKED, e.getMessage());
        }
        
        assertArrayEquals(conbef, machine.configuration());
        assertFalse(machine.ok());
    }
}