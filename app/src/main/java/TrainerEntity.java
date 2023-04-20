/**
 * Behavioral class. Various inherited versions that dictate behavior.
 * Human will prompt UI for stoof. Computer will make maybe-not-dumb choices.
 * Wild will make random choices.
 * @author DJ
 *
 */
public abstract class TrainerEntity {
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
    
    /** options are: 0-5: use move in index i.
     *  -1: struggle. Hidden, only used if no other moves are available.
     * @return decision indicator.
     */
    public abstract int decideBattle();
    
    public abstract int decideEnd();
    
    public abstract int decideCleanup();
    
    /**
     * Phase indicator is 0 for beginning, 1 for attack choice, 2 is for end, 3 for cleanup
     * @param phase
     * @return
     */
    public abstract int decideInput(int phase);
    
    public Codemon getFrontMon() {
        return this.getTrainer().getMons()[0];
    }

}
