
public class MoveItem extends Item {
    protected Move move;
    
    MoveItem(String name, String description, Move move, int quantity) {
        super(name, description, quantity);
        this.move = move;
    }
    
    public String getDescription() {
        return this.description + "Teaches " + move.getFullDesc();
    }
    
    public MoveItem(String name, String description, int quantity) {
        super(name, description, quantity);
        switch(name) {
        case "Move Stone":
            move = MoveFactory.getInstance().generateMove(1, Utility.d(7) - 1);
            break;
        case "Mighty Move Stone":
            move = MoveFactory.getInstance().generateMove(2, Utility.d(7) - 1);
            break;
        case "Epic Move Stone":
            move = MoveFactory.getInstance().generateMove(3, Utility.d(7) - 1);
            break;
        default:
            move = MoveFactory.getInstance().generateMove(1, Utility.d(7) - 1);
            break;
        }
        this.description += "\nMove learned: " + move.getDescription();
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }
    
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    public boolean sameItem(Item i) {
        if(i instanceof MoveItem) {
            MoveItem it = (MoveItem) i;
            if(it.getName().equals(this.getName())) {
                if(it.getMove().equals(this.getMove())) {
                    return true;
                }
            }
        }
        return false;
    }
}
