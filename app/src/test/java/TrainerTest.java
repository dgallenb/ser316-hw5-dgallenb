import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TrainerTest {
    ArrayList<TrainerEntity> trainers;
    Weather weather;
    UI ui;
    CodemonFactory monFac;
    MoveFactory moveFac;

    @Before
    public void setUp() throws Exception {
        //Utility.stabilizeRng(new double[] {0.95, 0.05, 0.95, 0.05});
        ui = new PredictableUI();
        weather = new Weather(0);
        trainers = new ArrayList<TrainerEntity>();
        
        monFac = CodemonFactory.getInstance();
        moveFac = MoveFactory.getInstance();
        Trainer t = new Trainer(monFac.generateCodemonWithT1Moves(10));
        HumanTrainerEntity te = new HumanTrainerEntity(t);
        te.setUI(ui);
        
        Trainer t1 = new Trainer(monFac.generateCodemonWithT1Moves(5));
        ComputerEntity te1 = new ComputerEntity(t1);
        trainers.add(te);
        trainers.add(te1);
        //TrainerEntity t1 = new
    }

    @After
    public void tearDown() throws Exception {
        //Utility.randomizeRng();
    }
    
    @Test
    public void testTrainerItems() {
        Trainer t = new Trainer(monFac.generateCodemonWithT1Moves(5));
        Item i1 = new Item("XSpeed", "Temporarily increases a Codemon's speed.", 1);
        Item i2 = new MoveItem("Move Stone", "Teaches a codemon a new move.", 1);
        Item i3 = new Item("XSpeed", "Temporarily increases a Codemon's speed.", 1);
        Item i4 = new Item();
        Item i5 = new Item("Capture Stone", "Captures Codemon (probably)", 1);
        t.addItem(i1);
        t.addItem(i2);
        t.addItem(i3);
        t.addItem(i4);
        t.addItem(i5);
        t.addMoney(50);
        t.getItem(15);
        t.removeItem(30);
        t.removeItem(3);
        t.removeItem(i2);
    }
    
    @Test
    public void testTrainerMonStuff() {
        Trainer t = new Trainer(monFac.generateCodemonWithT1Moves(5));
        t.addMon(monFac.generateCodemonWithT1Moves(10));
        t.replaceMon(monFac.generateCodemonWithT1Moves(6), 17);
        Codemon mon = monFac.generateCodemonWithT1Moves(6);
        mon.takeDamage(1000);
        t.replaceMon(mon, 0);
        t.replaceMon(monFac.generateCodemonWithT1Moves(6), 4);
        assertEquals(t.lastLiveMonIndex(), 4);
        
        t.switchMons(-2, 0);
        t.switchMons(0, -2);
        t.switchMons(7, 0);
        t.switchMons(0, 7);
        t.switchMons(0, 0);
        t.switchMons(0, 0);
        
    }
    
}