
public class Codemon {
    Move[] moves;
    MonType type;
    String name;
    
    public Codemon() {
        moves = new Move[6];
    }
    
    
    
    public static int dbToDamage(int db) { // I'm stealing PTU's damage base rolls.
        switch(db) {
        case 0:
            return 1;
        case 1:
            return d(6) + 1;
        case 2:
            return d(6) + 3;
        case 3:
            return d(6) + 5;
        case 4:
            return d(8) + 6;
        case 5:
            return d(8) + 8;
        case 6:
            return d(2,6) + 8;
        case 7:
            return d(2,6) + 10;
        case 8:
            return d(2,8) + 10;
        case 9:
            return d(2,10) + 10;
        case 10:
            return d(3,8) + 10;
        case 11:
            return d(3,10) + 10;
        case 12:
            return d(3,12) + 10;
        case 13:
            return d(4,10) + 10;
        case 14:
            return d(4,10) + 15;
        case 15:
            return d(4,10) + 20;
        case 16:
            return d(5,10) + 20;
            
            
            
            default:
                return 1;
        }
        
    }
    
    public static int d(int dieSize) {
        return (int) (Math.random() * dieSize + 1);
    }
    
    public static int d(int dieCount, int dieSize) {
        int total = 0;
        for(int i = 0; i < dieCount; ++i) {
            total += (int) (Math.random() * dieSize + 1);
        }
        return total;
    }
}
