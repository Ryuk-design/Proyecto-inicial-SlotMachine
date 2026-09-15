package SlotMachine_Package;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotMachineContestTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SlotMachineContestTest
{
    
    //Simulate Test
    
    @Test
    public void pruebaVisual1Simulate(){
        SlotMachineContest.simulate(3);
    }
    
    @Test
    public void pruebaVisual2Simulate(){
        SlotMachineContest.simulate(5);
    }
    
    @Test
    public void pruebaVisual3Simulate(){
        SlotMachineContest.simulate(10);
    }
}