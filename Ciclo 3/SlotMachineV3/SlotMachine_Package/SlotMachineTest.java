package SlotMachine_Package;
import SlotMachine_Package.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class SlotMachineTest
{
    private SlotMachine machine;
    private String[] col = {"red", "blue", "green", "yellow", "brown"};
    private String[] col2 = {"darkgoldenrod", "gold", "navajowhite", "rebeccapurple", "yellowgreen"};
    private String[] col3 = {"darkgoldenrod", "gold", "navajowhite", "rebeccapurple", "black"};
    
    //CICLO 2
    
    //SwapTest
    
    @Test
    public void AfCvShouldSwapTwoExistingWheels()
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
    public void AfCvShouldNotSwapIfPositionsAreTheSame()
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
    public void AfCvShouldSwapIfIndexOutOfRange()
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
    public void AfCvShouldSwapIfIndexOutOfRange2()
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
    public void AfCvShouldLockUnlockedWheel()
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
    public void AfCvShouldNotLockLockedWheel()
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
        machine.lock(wtl);
        assertTrue(machine.getWheel(wtl).getLocked());
        assertFalse(machine.ok());
    }
    
    @Test
    //Si el indice es menor que uno, se reacomoda en la posicion 1.
    public void AfCvLockWheelIfIndexOutOfRange()
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
    public void AfCvShouldLockWheelIfIndexOutOfRange2()
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
    public void AfCvShouldNotSpin1IfLocked()
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
        machine.spin();
        assertFalse(machine.ok());
    }
    
    @Test
    public void AfCvShouldNotSpin2IfLocked()
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
        machine.spin(wtl);
        assertFalse(machine.ok());
    }
    
    @Test
    public void AfCvShouldNotPlaceSymbolIfLocked()
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
        machine.placeSymbol(wtl, "red");
        assertFalse(machine.ok());
    }

    @Test
    public void AfCvShouldNotSpinStepsIfLocked()
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
        machine.spin(wtl, 3);
        assertFalse(machine.ok());
    }
    
    @Test
    public void AfCvShouldNotsetSymbolsIfLocked()
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
        machine.spin(a);
        assertFalse(machine.ok());
    }
    
    @Test
    public void AfCvShouldAddSymbolIfLocked()
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
    public void AfCvShouldNotDeleteSymbolIfLocked()
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
        machine.delSymbol("red");
        assertEquals(machine.getWheel(wtl).getCurrentSymbol().getColor(), "red");
    }
    
    //UnlockTest
    
    @Test
    public void AfCvShouldUnlockLockedWheel()
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
    public void AfCvShouldNotUnlockUnlockedWheel()
    {
        machine = new SlotMachine();
        String[] col = {"red", "blue", "green", "yellow", "brown"};
        machine.addWheel(1);
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        int wtl = 1;
        machine.unlock(wtl);
        assertFalse(machine.getWheel(wtl).getLocked());
        assertFalse(machine.ok());
    }
    
    @Test
    //Si el indice es menor que uno, se reacomoda en la posicion 1.
    public void AfCvShouldUnlockWheelIfIndexOutOfRange()
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
    public void AfCvShouldUnlockWheelIfIndexOutOfRange2()
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
    public void AfCvShouldSpinAnyNumberOfStepsInIntegerRange()
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
    public void AfCvShouldNotSpinIfThereIsNoSymbols()
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
        
        machine.spin(1, 12);
        assertFalse(machine.ok());
    }
    
    @Test
    //Si el indice es menor que uno, se reacomoda en la posicion 1.
    public void AfCvShouldSpinIfIndexOutOfRange()
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
    public void AfCvShouldSpinIfIndexOutOfRange2()
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
    public void AfCvShouldNotSpinIfWheelLocked()
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
        machine.spin(1, 870);
        assertEquals(machine.getWheel(1).getCurrentSymbol().getColor(), "red");
        assertFalse(machine.ok());
    }
    
    //SpinSetSymbolsTest
    
    @Test
    public void AfCvShouldPutInSlotMachineTheGivenConfiguration()
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
    public void AfCvShouldNotPutInSlotMachineTheGivenConfigurationIfThereIsNoWheels()
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
        
        machine.spin(col2);
        assertFalse(machine.ok());
    }
    
    @Test
    public void AfCvShouldNotSpinIfConfigurationDoesNotBelongsToMasterSymbols()
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
        machine.spin(col3);
        assertArrayEquals(conbef, machine.configuration());
        assertFalse(machine.ok());
    }
    
    @Test
    public void AfCvShouldNotSpinIfWheelLocked2()
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
        machine.spin(col2);
        assertArrayEquals(conbef, machine.configuration());
        assertFalse(machine.ok());
    }
}