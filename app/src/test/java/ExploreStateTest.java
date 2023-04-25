import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class ExploreStateTest {
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
    public void testExploreState() {
        ExploreState es = new ExploreState(trainers, ui, weather);
        int output = es.processState();
        es.prepTrainer(5);
        assert(trainers.get(1) instanceof ComputerEntity);
        es.prepWildCodemon(new double[] {0.25, 0.05, 0.05, 0.1, 0.25, 0.25, 0.05});
        assert(trainers.get(1) instanceof WildEntity);
        es.exploreCity();
        es.exploreForest();
        es.exploreMountain();
    }
}