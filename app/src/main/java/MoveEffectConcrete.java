
public abstract class MoveEffectConcrete implements AbstractMoveEffect {
    Affectable affected;
    
    public MoveEffectConcrete(Affectable a) {
        affected = a;
    }
    
    @Override
    public Affectable getAffected() {
        return affected;
    }

    @Override
    public void setAffected(Affectable e) {
        affected = e;
        
    }

    @Override
    public abstract void applyEffect();
    
}
