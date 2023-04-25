import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class SystemTest {
    ArrayList<TrainerEntity> trainers;
    Weather weather;
    UI ui;
    
    @Before 
    public void setUp() throws Exception {
        //Utility.stabilizeRng(new double[] {0.95, 0.05, 0.95, 0.05});
        ui = new PredictableUI();
        weather = new Weather(0);
        trainers = new ArrayList<TrainerEntity>();
        
        //TrainerEntity t1 = new
    }

    @After
    public void tearDown() throws Exception {
        Utility.randomizeRng();
    }
    
    @Test
    public void systemTest() {
        PredictableUI ui2 = new PredictableUI();
        GamePlay game = new GamePlay(ui2);
        game.play(50);
    }
    
    
}