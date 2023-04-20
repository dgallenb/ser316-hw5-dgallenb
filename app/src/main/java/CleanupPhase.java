import java.util.ArrayList;

public class CleanupPhase implements AbstractPhase {
    protected TrainerEntity[] trainers; // assumed to be a human player
    protected UI ui;
    protected int nextPhase;
    protected ArrayList<Acquirable> acquired;
    
    public CleanupPhase(TrainerEntity t1, TrainerEntity t2, UI ui) {
        this.trainers = new TrainerEntity[2];
        this.trainers[0] = t1;
        this.trainers[1] = t2;
        this.ui = ui;
        acquired = new ArrayList<Acquirable>();
    }
    
    public CleanupPhase(TrainerEntity t1, TrainerEntity t2, UI ui, 
            ArrayList<Acquirable>a) {
        this.trainers = new TrainerEntity[2];
        this.trainers[0] = t1;
        this.trainers[1] = t2;
        this.ui = ui;
        acquired = a;
    }

    @Override
    public void performPhase() {
        // TODO Auto-generated method stub

    }

    @Override
    public int queryUser() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public AbstractPhase nextPhase(ArrayList<Acquirable> a) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void displayPrePhaseDialogue() {
        // TODO Auto-generated method stub

    }

}
