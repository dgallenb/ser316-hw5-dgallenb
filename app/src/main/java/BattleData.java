

public class BattleData {

    protected int initiative;
    protected TrainerEntity trainer;
    protected TrainerEntity target;
    protected int moveIndex;
    
    public BattleData(int init, TrainerEntity trainer, 
            TrainerEntity target, int moveIndex) {
        initiative = init;
        this.trainer = trainer;
        this.target = target;
        this.moveIndex = moveIndex;
        
    }
    
    
    
    public int getInitiative() {
        return initiative;
    }



    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }



    public TrainerEntity getTrainer() {
        return trainer;
    }



    public void setTrainer(TrainerEntity trainer) {
        this.trainer = trainer;
    }



    public TrainerEntity getTarget() {
        return target;
    }



    public void setTarget(TrainerEntity target) {
        this.target = target;
    }



    public int getMoveIndex() {
        return moveIndex;
    }



    public void setMove(int move) {
        this.moveIndex = move;
    }



    /**
     * Compareto. 
     * @param o THe compared object.
     * @return 0 if they're initiatives and equal, 1 if this one has higher 
     *      initiative, -1 if this initiative pair is slower.
     */
    public int compareTo(Object o) {
        if (o instanceof BattleData) {
            BattleData p2 = (BattleData) o;
            
            if (this.initiative > p2.initiative) {
                return 1;
            } else if (this.initiative < p2.initiative) {
                return -1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
        
    }
    
    public int hashCode() {
        //assert false : "hashCode not designed";
        return 42;
    }
    
    /**
     * Equals.
     * @return True if the initiative pairs have the same initiative, false otherwise.
     */
    public boolean equals(Object o) {
        if (o instanceof BattleData) {
            BattleData p2 = (BattleData) o;
            if (this.initiative == p2.initiative) {
                return true;
            }
        }
        return false;
    }
}
