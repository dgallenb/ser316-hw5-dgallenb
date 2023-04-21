
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
        description += "\nMove learned: " + move.getDescription();
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }
    
    public boolean equals(Object o) {
        if(o instanceof MoveItem) {
            MoveItem i = (MoveItem) o;
            if(i.getName().equals(this.getName())) {
                if(i.getMove().equals(this.getMove())) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
