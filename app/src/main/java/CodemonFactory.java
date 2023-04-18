
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
            int movesKnown) {
        Move[] moves = new Move[6];
        for(int i = 0; i < movesKnown; ++i) {
            if(i >= moves.length) {
                break;
            }
            moves[i] = MoveFactory.getInstance().generateMove(1, monType);
        }
        return new Codemon(new MonType(monType), 
                baseHP, baseAtk, baseDef, baseSpd, moves);
    }
}
