import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class MenuStateTest {
    ArrayList<TrainerEntity> trainers;
    Weather weather;
    UI ui;
    
    @Before
    public void setUp() throws Exception {
        Utility.stabilizeRng(new double[] {0.95, 0.05, 0.95, 0.05});
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
        Utility.randomizeRng();
    }
    
    @Test
    public void testMonMenu() {
        MenuState menu = new MenuState(trainers, ui, weather);
        menu.handleMonMenu(0);
        menu.handleMonMenu(0);
        menu.handleDetailedMonMenu(0);
        
        Trainer t = trainers.get(0).getTrainer();
        CodemonFactory monFac = CodemonFactory.getInstance();
        t.addMon(monFac.generateCodemonWithT1Moves(10));
        t.addMon(monFac.generateCodemonWithT1Moves(10));
        t.addMon(monFac.generateCodemonWithT1Moves(10));
        t.addMon(monFac.generateCodemonWithT1Moves(10));
        t.addMon(monFac.generateCodemonWithT1Moves(10));
        
        //menu.handleDetailedDescriptionMenu(0);
        //menu.handleDetailedDescriptionMenu(1);
        //menu.handleDetailedDescriptionMenu(2);
        for (int i = 0; i < trainers.get(0).getTrainer().getMonCount(); ++i) {
            for (int j = -1; j >= -3; --j) {
                menu.handleDetailedDescriptionMenu(i, j);
            }
        }
        menu.handleDetailedMonMenu(-7);
        
    } 
    
    @Test
    public void testMonMenu2() {
        MenuState menu = new MenuState(trainers, ui, weather);
        
        Trainer t = trainers.get(0).getTrainer();
        CodemonFactory monFac = CodemonFactory.getInstance();
        t.addMon(monFac.generateCodemonWithT1Moves(10));
        t.addMon(monFac.generateCodemonWithT1Moves(10));

        HumanTrainerEntity te = new HumanTrainerEntity(t);
        te.setUI(ui);
        trainers.set(0, te);
        menu.handleDetailedMonMenu(-1);
        menu.handleDetailedMonMenu(-2);
        menu.handleDetailedMonMenu(-3);
        menu.handleDetailedMonMenu(-4);
        
        menu.handleDetailedDescriptionMenu(0, -1);
    }
}