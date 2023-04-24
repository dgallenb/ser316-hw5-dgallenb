import java.util.ArrayList;

/**
 * The phase reached only if the player lost a battle. Forces a rest as the next state.
 * @author DJ
 *
 */
public class DeadPhase extends CleanupPhase implements AbstractPhase {

    /**
     * Constructor. 
     * @param trainers The trainers involved in the battle.
     * @param ui The UI to prod for input and displays.
     */
    public DeadPhase(ArrayList<TrainerEntity> trainers, UI ui) {
        super(trainers, ui);
    }
    
    /**
     * Constructor. 
     * @param trainers The trainers involved in the battle.
     * @param ui The UI to prod for input and displays.
     * @param a A list of the acquired rewards for the player.
     */
    public DeadPhase(ArrayList<TrainerEntity> trainers, UI ui,
            ArrayList<Acquirable> a) {
        super(trainers, ui, a);
    }

    @Override
    protected void generalCleanup() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void processRewards(TrainerEntity t) {
        while (acquired.size() > 0) {
            Acquirable a = acquired.remove(0);
            /*
            if(a instanceof Item) {
            }
            else if(a instanceof Codemon) {
                // sorry, if you get wiped, no new mons for you.
                // awardCodemon(t, (Codemon) a); 
            }
            else {
                // nothing should be here, but eh.
            }
            */
        }
        
    }

    /*
    protected void deadCheck() {
        for(int i = trainers.size() - 1; i >= 0; --i) {
            if(trainers.get(i).getTrainer().countLiveMons() < 1) {
                if(trainers.get(i) instanceof HumanTrainerEntity) {
                    nextPhase = 4;
                    ui.display("You have lost!\n");
                }
                else if(trainers.get(i) instanceof WildEntity) {
                    nextPhase = 6;
                }
                else if(trainers.get(i) instanceof ComputerEntity) {
                    int totalMoney = 0;
                    for(Codemon mon : trainers.get(i).getTrainer().getMons()) {
                        if(mon != null) {
                            totalMoney += 50 + (mon.getExp() / 2);
                        }
                    }
                    acquired.add(new Money(totalMoney));
                }
            }
        }
    }
    */

}
