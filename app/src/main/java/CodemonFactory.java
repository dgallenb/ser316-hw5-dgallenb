/**
 * Factory that makes codemons.
 * @author DJ
 *
 */



public class CodemonFactory {
    private static CodemonFactory instance;
    
    private CodemonFactory() {
        
    }
    
    /**
     * The Factory design pattern's primary method.
     * @return Returns the one instance of this factory allowed.
     */
    public static CodemonFactory getInstance() {
        if (instance == null) {
            instance = new CodemonFactory();
        }
        return instance;
    }
    
    /**
     * Generates a codemon with "tier 1" moves.
     * @param monType The integer representing the mon's type.
     * @param baseHp The mon's base HP.
     * @param baseAtk The mon's base HP.
     * @param baseDef The mon's base HP.
     * @param baseSpd The mon's base HP.
     * @param movesKnown The number of moves known to start.
     * @param exp The mon's starting exp.
     * @return A new codemon.
     */
    public Codemon generateCodemonWithT1Moves(int monType, 
            int baseHp, int baseAtk, int baseDef, int baseSpd, 
            int movesKnown, int exp) {
        Move[] moves = new Move[6];
        for (int i = 0; i < movesKnown; ++i) {
            if (i >= moves.length) {
                break;
            }
            moves[i] = MoveFactory.getInstance().generateMove(1, monType);
        }
        if (exp < 0) {
            exp = 0;
        }
        return new Codemon(new MonType(monType), 
                baseHp, baseAtk, baseDef, baseSpd, moves, exp);
    }
    
    public Codemon generateCodemonWithT1Moves(int monType, int exp) {
        int moves = Utility.d(4);
        return generateCodemonWithT1Moves(monType, 25, 5, 5, 5, moves, exp);
    }
    
    public Codemon generateCodemonWithT1Moves(int lvl) {
        return generateCodemonWithT1Moves(Utility.d(7) - 1, Utility.getExpFromLevel(lvl));
    }
}
