package SlotMachine_Package;
import SlotMachine_Package.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SlotMachineC4Test
{
    
    private SlotMachine machine;
    private String[] col = {"red", "blue", "green", "yellow", "brown"};

    // Lefty Tests
    @Test
    public void AfLeftyShouldCopyLeftWheelOnSpinAll()
    {
        machine = new SlotMachine();
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        machine.addWheel(1);
        machine.addWheel("lefty", 2);
        
        for(int i = 0; i < 20; i++)
        {
            machine.spin();
            String[] conf = machine.configuration();
            assertEquals(conf[0], conf[1]);
        }
        
        assertTrue(machine.ok());
    }

    @Test
    public void AfLeftyShouldCopyLeftWheelOnSpinOne()
    {
        machine = new SlotMachine();
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        machine.addWheel(1);
        machine.addWheel("lefty", 2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.spin(2);
        assertEquals("red", machine.getWheel(2).getCurrentSymbol().getColor());
        assertTrue(machine.ok());
    }

    @Test
    public void AfLeftyShouldCopyLeftWheelAndIgnoreSteps()
    {
        machine = new SlotMachine();
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        machine.addWheel(1);
        machine.addWheel("lefty", 2);
        machine.placeSymbol(1, "green");
        machine.placeSymbol(2, "red");
        machine.spin(2, 1);
        assertEquals("green", machine.getWheel(2).getCurrentSymbol().getColor());
        assertTrue(machine.ok());
    }

    @Test
    public void AfLeftyShouldNotChangeIfOnlyLeftWheelSpins()
    {
        machine = new SlotMachine();
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        machine.addWheel(1);
        machine.addWheel("lefty", 2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.spin(1, 2);
        assertEquals("green", machine.getWheel(1).getCurrentSymbol().getColor());
        assertEquals("blue", machine.getWheel(2).getCurrentSymbol().getColor());
    }

    @Test
    public void AfLeftyShouldNotCopyIfLocked()
    {
        machine = new SlotMachine();
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        machine.addWheel(1);
        machine.addWheel("lefty", 2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.lock(2);
        machine.spin(2);
        assertEquals("blue", machine.getWheel(2).getCurrentSymbol().getColor());
        assertFalse(machine.ok());
    }

    @Test
    public void AfLeftyShouldCopyNewLeftNeighborAfterSwap()
    {
        machine = new SlotMachine();
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel("lefty", 3);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "green");
        machine.placeSymbol(3, "blue");
        machine.swap(2, 3);
        machine.spin(2);
        assertEquals("red", machine.getWheel(2).getCurrentSymbol().getColor());
    }

    @Test
    public void AfLeftyShouldNotFailIfLeftWheelHasNoSymbols()
    {
        machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel("lefty", 2);
        machine.spin(2);
        assertTrue(machine.ok());
    }
    
    // Rebel Tests
    
    @Test
    public void AfCvRebelShouldNotBeLocked()
    {
        machine = new SlotMachine();
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        machine.addWheel("rebel", 1);
        machine.lock(1);
        assertFalse(machine.getWheel(1).getLocked());
        assertFalse(machine.ok());
    }

    @Test
    public void AfCvRebelShouldNotBeSwapped()
    {
        machine = new SlotMachine();
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        machine.addWheel(1);
        machine.addWheel("rebel", 2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        String[] orig = machine.configuration();
        machine.swap(1, 2);
        assertArrayEquals(orig, machine.configuration());
        assertFalse(machine.ok());
    }

    @Test
    public void AfCvRebelShouldNotBeDeleted()
    {
        machine = new SlotMachine();
        
        for(int i = 1; i <= 5; i++)
        {
            machine.addSymbol(i, col[i - 1]);
        }
        
        machine.addWheel("rebel", 1);
        machine.delWheel(1);
        assertEquals(1, machine.configuration().length);
        assertFalse(machine.ok());
    }
    
    //Ephemeral Tests

    @Test
    public void AfEphemeralShouldGetSmallerOnSpin()
    {
        machine = new SlotMachine();
        machine.addSymbol("ephemeral", 1, "red");
        machine.addWheel(1);
        int before = machine.getWheel(1).getCurrentSymbol().getDiameter();
        machine.spin();
        int after = machine.getWheel(1).getCurrentSymbol().getDiameter();
        assertTrue(after < before);
        assertTrue(machine.ok());
    }

    @Test
    public void AfEphemeralShouldStopGettingSmaller()
    {
        machine = new SlotMachine();
        machine.addSymbol("ephemeral", 1, "red");
        machine.addWheel(1);
        
        for(int i = 0; i < 50; i++)
        {
            machine.spin();
        }
        
        int last = machine.getWheel(1).getCurrentSymbol().getDiameter();
        machine.spin();
        assertEquals(last, machine.getWheel(1).getCurrentSymbol().getDiameter());
        assertTrue(last > 0);
    }

    @Test
    public void NormalSymbolShouldNotGetSmallerOnSpin()
    {
        machine = new SlotMachine();
        machine.addSymbol(1, "red");
        machine.addWheel(1);
        int before = machine.getWheel(1).getCurrentSymbol().getDiameter();
        machine.spin();
        assertEquals(before, machine.getWheel(1).getCurrentSymbol().getDiameter());
    }

    //Shy Tests

    @Test
    public void AfShyShouldChangeVisibilityOnEverySpin()
    {
        machine = new SlotMachine();
        machine.addSymbol("shy", 1, "red");
        machine.addWheel(1);
        assertFalse(machine.getWheel(1).getCurrentSymbol().isHidden());
        machine.spin();
        assertTrue(machine.getWheel(1).getCurrentSymbol().isHidden());
        machine.spin();
        assertFalse(machine.getWheel(1).getCurrentSymbol().isHidden());
        machine.spin();
        assertTrue(machine.getWheel(1).getCurrentSymbol().isHidden());
    }

    @Test
    public void AfShyShouldNotToggleOnPlaceSymbol()
    {
        machine = new SlotMachine();
        machine.addSymbol("shy", 1, "red");
        machine.addWheel(1);
        machine.placeSymbol(1, "red");
        assertFalse(machine.getWheel(1).getCurrentSymbol().isHidden());
    }

    @Test
    public void AfNormalSymbolShouldNotHide()
    {
        machine = new SlotMachine();
        machine.addSymbol(1, "red");
        machine.addWheel(1);
        machine.spin();
        machine.spin();
        assertFalse(machine.getWheel(1).getCurrentSymbol().isHidden());
    }
}