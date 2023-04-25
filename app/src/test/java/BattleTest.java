import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class BattleTest {
    ArrayList<TrainerEntity> trainers;
    Weather weather;
    UI ui;
    
    
    @Before
    public void setUp() throws Exception {
        Utility.stabilizeRng(new double[] {0.95, 0.05, 0.95, 0.05});
        ui = new TextUI();
        weather = new Weather(0);
        trainers = new ArrayList<TrainerEntity>();
        
        //TrainerEntity t1 = new
    }

    @After
    public void tearDown() throws Exception {
        Utility.randomizeRng();
    }
    
    
    
    
    
    @Test
    public void moveConstructorTest() {
        Move move1 = new Move("Base", "Base description", 10, 2, new Frequency(0), new MonType(0));
        Move move2 = new Move();
        
        assertEquals("Base", move1.getName());
        assertEquals("Base description", move1.getDescription());
        assertEquals(10, move1.getDb());
        assertEquals(2, move1.getAc());
        assertEquals(true, move1.isAvailable());
        assertEquals(0, move1.getType().getTypeNum());
        
        assertEquals("Struggle", move2.getName());
        assertEquals("Default move", move2.getDescription());
        assertEquals(4, move2.getDb());
        assertEquals(4, move2.getAc());
        assertEquals(true, move2.isAvailable());
        assertEquals(0, move2.getType().getTypeNum());
    }
    /*
    @Test 
    public void codemonConstructorTest1() {
        
    }
    
    @Test 
    public void beginningPhaseCheck {
        //BattleState(ArrayList<TrainerEntity> trainers,  UI ui, Weather weather)
    }
    */
}
