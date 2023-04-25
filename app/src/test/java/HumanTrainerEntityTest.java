import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class HumanTrainerEntityTest {
    ArrayList<TrainerEntity> trainers;
    Weather weather;
    UI ui;

    @Before
    public void setUp() throws Exception {
        //Utility.stabilizeRng(new double[] {0.95, 0.05, 0.95, 0.05});
        ui = new PredictableUI();
        weather = new Weather(0);
        trainers = new ArrayList<TrainerEntity>();
        
        CodemonFactory monFac = CodemonFactory.getInstance();
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
    public void testSwitchMenu() {
        HumanTrainerEntity he = (HumanTrainerEntity) trainers.get(0);
        CodemonFactory monFac = CodemonFactory.getInstance();
        he.getTrainer().addMon(monFac.generateCodemonWithT1Moves(10));
        he.handleSwitchMenu(1, -7);
        he.handleSwitchMenu(1, 2);
    }
    
    @Test
    public void testItemSpecificMenu() {
        HumanTrainerEntity he = (HumanTrainerEntity) trainers.get(0);
        he.getTrainer().addItem(new Item());
        he.handleItemSpecificMenu(0, 2);
    }
    
    @Test
    public void testItemsMenu() {
        HumanTrainerEntity he = (HumanTrainerEntity) trainers.get(0);
        CodemonFactory monFac = CodemonFactory.getInstance();
        he.getTrainer().addMon(monFac.generateCodemonWithT1Moves(10));
        he.handleItemsMenu(1);
        //he.handleItemsMenu(2);
    }
    
}
    