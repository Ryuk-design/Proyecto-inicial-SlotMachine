package SlotMachine_Package;
import SlotMachine_Package.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotMachineContestTest.
 * @author  Samuel Ahumada Florez
 */
public class SlotMachineContestTest
{
    //SolveTest
    
    @Test
    public void pruebaSolve()
    {
        int[][] sol = SlotMachineContest.solve(50);
        assertTrue(sol.length < 10000);
    }
    
    public void pruebaSolve2()
    {
        int[][] sol = SlotMachineContest.solve(49);
        assertTrue(sol.length < 10000);
    }
    
    //Simulate Test
    
    @Test
    public void pruebaVisual1Simulate()
    {
        SlotMachineContest.simulate(3);
    }
    
    @Test
    public void pruebaVisual2Simulate()
    {
        SlotMachineContest.simulate(5);
    }
    
    @Test
    public void pruebaVisual3Simulate()
    {
        SlotMachineContest.simulate(10);
    }
}