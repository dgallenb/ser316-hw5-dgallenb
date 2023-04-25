import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CodemonTest {

    @Test
    public void codemonConstructor() {
        Move[] moves = new Move[6];
        moves[0] = MoveFactory.getInstance().generateMove(1, 1);
        Codemon c1 = new Codemon(new MonType(1), 40, 5, 6, 7, moves, 0);
        
        Codemon c2 = new Codemon(c1);
        
        assertEquals(c1.getAtk(),5);
        assertEquals(c1.getDef(),6);
        assertEquals(c1.getSpd(),7);
        assertEquals(c1.getHp(),40);
        assertEquals(c1.getCurrentHp(), 40);
        for(int i = 0; i < 8; ++i) {
            assertEquals(c1.getTempStat(i),0);
        }
        
        
        assertEquals(c2.getAtk(),5);
        assertEquals(c2.getDef(),6);
        assertEquals(c2.getSpd(),7);
        assertEquals(c2.getHp(), 40);
        assertEquals(c2.getCurrentHp(), 40);
        for(int i = 0; i < 8; ++i) {
            assertEquals(c2.getTempStat(i),0);
        }
        assertEquals(c2.getMove(-1), null);
    }
    
    @Test
    public void basicCodemonConstructorTest() {
        Trainer t1 = new Trainer();
        Move[] moveset1 = new Move[6];
        moveset1[0] = new Move("Base", "Base description", 10, 2, new Frequency(0), new MonType(0));
        Move[] moveset3 = new Move[6];
        for(int i = 0; i < moveset3.length; ++i) {
            moveset3[i] = new Move("Base", "Base description", 10, 2, new Frequency(0), new MonType(0));
        }
        MonType type1 = new MonType(0);
        Codemon c1 = new Codemon(type1, 30, 6, 6, 6, moveset3, 0);
        Codemon c2 = new Codemon();
        c2.addMove(new Move("Added Move", "Desc", 9, 2, new Frequency(1), new MonType(1)));
        
        assertEquals(c1.getHp(), 30);
        assertEquals(c1.getAtk(), 6);
        assertEquals(c1.getDef(), 6);
        assertEquals(c1.getSpd(), 6);
        assertEquals(c1.getType().getTypeNum(), 0);
        assertEquals(c1.getMove(0).getName(), "Base");
        assertEquals(c1.getMove(0).getDescription(), "Base description");
        assertEquals(c1.getMove(0).getDb(), 10);
        assertEquals(c1.getMove(0).getAc(), 2);
        assertEquals(c1.getMove(0).getType().getTypeNum(), 0);
    }
    
    @Test
    public void codemonMoveMethods() {
        Move[] moves = new Move[6];
        moves[0] = MoveFactory.getInstance().generateMove(1, 1);
        moves[1] = MoveFactory.getInstance().generateMove(2, 2);
        Codemon c1 = new Codemon(new MonType(1), 40, 5, 6, 7, moves, 0);
        
        assertEquals(2, c1.getMoveCount());
        c1.addMove(MoveFactory.getInstance().generateMove(3, 2));
        assertEquals(3, c1.getMoveCount());
        c1.overrideMove(MoveFactory.getInstance().generateMove(3, 2), 0);
        assertEquals(3, c1.getMoveCount());
        c1.addMove(MoveFactory.getInstance().generateMove(3, 2));
        c1.addMove(MoveFactory.getInstance().generateMove(3, 2));
        c1.addMove(MoveFactory.getInstance().generateMove(3, 2));
        boolean b = c1.addMove(MoveFactory.getInstance().generateMove(3, 2));
        assertEquals(false, b);
    }
    
    @Test
    public void codemonExpLvlMethods() {
        Move[] moves = new Move[6];
        moves[0] = MoveFactory.getInstance().generateMove(1, 1);
        moves[1] = MoveFactory.getInstance().generateMove(2, 2);
        Codemon c1 = new Codemon(new MonType(6), 40, 5, 6, 7, moves, 0);
        c1.setName("Borgar");
        
        c1.addExp(250);
        boolean b1 = c1.levelUp();
        assertEquals(b1, true);
        c1.performLevelUp();
        assertEquals(true, c1.canEvolve());
        c1.setEvolve(true);
        c1.setBonusStatChance(50.1, 1);
        c1.addExp(35);
        int atk1 = c1.getAtk();
        c1.performLevelUp();
        assertEquals(c1.getAtk(), atk1 + 51);
        Codemon c2 = c1.evolve();
        
    }
    
    @Test
    public void codemonCurrentVals() {
        Move[] moves = new Move[6];
        moves[0] = MoveFactory.getInstance().generateMove(1, 1);
        moves[1] = MoveFactory.getInstance().generateMove(2, 2);
        Codemon c1 = new Codemon(new MonType(6), 40, 5, 6, 7, moves, 0);
        
        for(int i = 0; i < 8; ++i) {
            c1.setTempStat(i, i);            
        }
        assertEquals(c1.getCurrentHp(),40);
        assertEquals(c1.getCurrentAtk(),6);
        assertEquals(c1.getCurrentDef(),8);
        assertEquals(c1.getCurrentSpd(),10);
        assertEquals(c1.computeEvade(), 6); // +4 + 10/5
        assertEquals(c1.getInitiative(),15);
        assertEquals(c1.getAccuracy(),6);
        assertEquals(c1.getCritRange(),7);
        
        c1.applyCombatStage(1);
        assertEquals(c1.getCurrentAtk(),7);
        c1.applyCombatStage(2);
        assertEquals(c1.getCurrentDef(),9);
        c1.applyCombatStage(3);
        assertEquals(c1.getCurrentSpd(),11);
        
        Codemon c2 = new Codemon(new MonType(6), 40, 5, 6, 7, moves, 0);
        c2.addAccuracy(1);
        assertEquals(1, c2.getAccuracy());
        c2.addEvade(1);
        assertEquals(2, c2.computeEvade());
        c2.addCritRange(3);
        assertEquals(3, c2.getCritRange());
        c2.addInitiative(5);
        assertEquals(12, c2.getInitiative());
        
    }


}