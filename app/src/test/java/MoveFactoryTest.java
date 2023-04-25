import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MoveFactoryTest {
    
    
    @Test public void factoryTest() {
        MoveFactory factory = MoveFactory.getInstance();
        
        for(int i = 5; i <= 12; ++i) {
            for(int j = 0; j < 7; ++j) {
                factoryTestSpecific(factory, i, j);
            }
        }
    }
    
    public void factoryTestSpecific(MoveFactory factory, int tier, int type) {
        Move m = factory.generateMove(tier, type, true);
        
        assertEquals(m.getDb(), tier);
        assertEquals(m.getType().getTypeNum(), type);
    }
    
    @Test
    public void factoryTestBasic() {
        MoveFactory factory = MoveFactory.getInstance();
        Move m = factory.generateMove("Punch", "Punch punch", 5, 12, 6, 3);
        
        assertEquals(m.getName(), "Punch");
        assertEquals(m.getDescription(), "Punch punch");
        assertEquals(m.getDb(), 5);
        assertEquals(m.getAc(), 12);
        assertEquals(m.getType().getTypeNum(), 6);
        assertEquals(m.getFrequency().getTypeNum(), 3);
    }
}