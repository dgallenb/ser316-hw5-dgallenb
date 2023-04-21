import java.util.ArrayList;

public class ReturnPhase extends CleanupPhase implements AbstractPhase {
    
    public ReturnPhase(TrainerEntity t1, TrainerEntity t2, UI ui, 
            Weather weather, ArrayList<Acquirable> a) {
        super(t1, t2, ui, weather, a);
    }

    @Override
    public AbstractPhase performPhase() {
        return super.performPhase();
    }

    @Override
    public int queryUser() {
        return super.queryUser();
    }

    @Override
    public AbstractPhase nextPhase(ArrayList<Acquirable> a) {
        // TODO Auto-generated method stub
        return super.nextPhase(a);
    }

    @Override
    public void displayPrePhaseDialogue() {
        super.displayPrePhaseDialogue();
        
    }

}
