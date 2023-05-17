import java.util.List;

/**
 * Behavioral class. Various inherited versions that dictate behavior.
 * Human will prompt UI for stoof. Computer will make maybe-not-dumb choices.
 * Wild will make random choices.
 * @author DJ
 *
 */
public abstract class TrainerEntity implements Affectable {
    protected Trainer trainer;
    
    public TrainerEntity(Trainer t) {
        this.trainer = t;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
    
    /** options are: 0. No training. no switching. No item. wild only
     *  1-5. switch with index 1-5. 
     *  6-9. Apply training.
     *  10+. use item.
     * @return decision indicator.
     */
    public abstract int decideBeginning();
    
    /** Options are: 0-5: use move in index i.
     *  -1: struggle. Hidden, only used if no other moves are available.
     * @return decision indicator.
     */
    public abstract int decideBattle();
    
    public abstract int decideTarget(List<TrainerEntity> targets);
    
    public abstract int decideEnd();
    
    public abstract int decideCleanup();
    
    /**
     * Phase indicator is 0 for beginning, 1 for attack choice, 2 is for end, 3 for cleanup.
     * @param phase The integer indicator for the current phase.
     * @return The integer representing the trainer's decision.
     */
    public abstract int decideInput(int phase);
    
    public Codemon getFrontMon() {
        return this.getTrainer().getMon(0);
    }

    protected abstract int forceSwitch();
    
    public int affect(AbstractMoveEffect e) {
        if(e instanceof MoveEffectCodemon) {
            
        }
        else if(e instanceof MoveEffectTrainer) {
            
        }
        else if(e instanceof MoveEffectEnvironment) {
            
        }
        return 0;
    }

}
