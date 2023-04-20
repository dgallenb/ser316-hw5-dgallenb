import java.util.ArrayList;

public abstract interface AbstractPhase {
    
    public abstract AbstractPhase performPhase();
    
    public abstract int queryUser();
    
    public abstract AbstractPhase nextPhase(ArrayList<Acquirable> a);
    
    public abstract void displayPrePhaseDialogue();
}
