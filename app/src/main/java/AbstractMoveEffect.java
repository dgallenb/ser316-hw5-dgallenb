
public abstract interface AbstractMoveEffect {
    
    public Affectable getAffected();
    public void setAffected(Affectable e);
    public void applyEffect();
    
    // move effects come in 5 categories: do a thing to another target, do a thing to the user,
    // do a thing to the environment (weather), do a thing to the trainer (blessings, hazards),
    // switch,
    
}
