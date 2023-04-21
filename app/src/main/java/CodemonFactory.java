
public class CodemonFactory {
    private static CodemonFactory instance;
    
    private CodemonFactory() {
        
    }
    
    public static CodemonFactory getInstance() {
        if(instance == null) {
            instance = new CodemonFactory();
        }
        return instance;
    }
    
    public Codemon generateCodemonWithT1Moves(int monType, 
            int baseHP, int baseAtk, int baseDef, int baseSpd, 
            int movesKnown, int exp) {
        Move[] moves = new Move[6];
        for(int i = 0; i < movesKnown; ++i) {
            if(i >= moves.length) {
                break;
            }
            moves[i] = MoveFactory.getInstance().generateMove(1, monType);
        }
        if(exp < 0) {
            exp = 0;
        }
        return new Codemon(new MonType(monType), 
                baseHP, baseAtk, baseDef, baseSpd, moves, exp);
    }
    
    public Codemon generateCodemonWithT1Moves(int monType, int exp) {
        int moves = Utility.d(4);
        return generateCodemonWithT1Moves(monType, 25, 5, 5, 5, moves, exp);
    }
    
    public Codemon generateCodemonWithT1Moves(int lvl) {
        return generateCodemonWithT1Moves(Utility.d(7) - 1, Utility.getExpFromLevel(lvl));
    }
}
