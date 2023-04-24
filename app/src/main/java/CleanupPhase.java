import java.util.ArrayList;

public abstract class CleanupPhase implements AbstractPhase {
    protected ArrayList<TrainerEntity> trainers; // assumed to be a human player
    protected UI ui;
    protected int nextPhase;
    protected ArrayList<Acquirable> acquired;
    protected Weather weather;
    
    public CleanupPhase(ArrayList<TrainerEntity> trainers, UI ui, Weather w) {
        this.trainers = trainers;
        this.ui = ui;
        this.weather = w;
        this.nextPhase = 5;
        acquired = new ArrayList<Acquirable>();
    }
    
    public CleanupPhase(ArrayList<TrainerEntity> trainers, UI ui, Weather w,
            ArrayList<Acquirable>a) {
        this.trainers = trainers;
        this.ui = ui;
        this.weather = w;
        this.nextPhase = 5;
        acquired = a;
    }

    /**
     * 1. Remove ded trainers
     * 2. If the player is ded, return DeadPhase so the gamestate knows to transition.
     * 3. Clean up each trainer's temp effects.
     * 3. Award money and items and codemons.
     * 4. Check for evolutions. If needed, perform them.
     */
    @Override
    public AbstractPhase performPhase() {
        displayPrePhaseDialogue();
        cleanCombatModifiers();
        generalCleanup();
        return nextPhase(acquired);

    }
    
    /**
     * Different cleanup phases check different values.
     * Dead Phase: player lost, so there's no need to check rewards.
     * Captured phase: there's a codemon acquired.
     * Return phase: 
     */
    protected abstract void generalCleanup();
    
    /**
     * Some battles end with rewards given, but not all, and they give different types 
     * of rewards. So each concrete implementation will figure this out on its own.
     * @param t
     */
    protected abstract void processRewards(TrainerEntity t);
    
    
    /*
     * Modifiers are meant to last one battle, so every battle needs to check them.
     */
    protected void cleanCombatModifiers() {
        for(TrainerEntity t : trainers) {
            if(t != null) {
                for(Codemon mon : t.getTrainer().getMons()) {
                    if(mon != null) {
                        mon.refreshScene();
                        mon.resetTempStats();
                    }
                }
            }
        }
    }
    
    
    


    @Override
    public int queryUser() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * End of the road, pick a new state.
     */
    @Override
    public AbstractPhase nextPhase(ArrayList<Acquirable> a) {
        return null;
    }

    @Override
    public void displayPrePhaseDialogue() {
        // TODO Auto-generated method stub
        String s = "";
        
        s += "The battle has ended. \n";
        ui.display(s);
    }

}
