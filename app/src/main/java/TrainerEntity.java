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
        this.trainer = trainer;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
    
    public abstract int decideSwitch();
    
    public abstract int decideAttack();
    
    public abstract int decideItem();

}
