import java.util.ArrayList;

/**
 * The End phase is here solely to allow a free switch for a downed mon,
 * apply exp to the winner,
 * and to end the battle if there's no mons left on one side.
 * @author DJ
 *
 */
public class EndPhase implements AbstractPhase {
    protected ArrayList<TrainerEntity> trainers; // assumed to be a human player
    protected UI ui;
    protected int nextPhase;
    protected ArrayList<Acquirable> acquired;
    protected Weather weather;
    
    /**
     * Constructor.
     * @param trainers The trainers involved in the battle.
     * @param ui The UI to prod for input and displays.
     * @param w The current weather.
     */
    public EndPhase(ArrayList<TrainerEntity> trainers, UI ui, Weather w) {
        this.trainers = trainers;
        this.ui = ui;
        this.weather = w;
        this.nextPhase = 0;
        acquired = new ArrayList<Acquirable>();
    }
    
    /**
     * Constructor.
     * @param trainers The trainers involved in the battle.
     * @param ui The UI to prod for input and displays.
     * @param w The current weather.
     * @param a A list of the acquired rewards for the player.
     */
    public EndPhase(ArrayList<TrainerEntity> trainers, UI ui, Weather w,
            ArrayList<Acquirable> a) {
        this.trainers = trainers;
        this.ui = ui;
        this.weather = w;
        this.nextPhase = 0;
        acquired = a;
    }

    @Override
    public AbstractPhase performPhase() {
        String s = "";
        // 1. check for ded mons
        for (int i = 0; i < trainers.size(); ++i) {
            TrainerEntity t = trainers.get(i);
            if (t.getTrainer().countLiveMons() < 1) {
                
                s += t.getTrainer().getName() + " is out of codemons.\n";
                if (t instanceof HumanTrainerEntity) {
                    nextPhase = 4;
                } else {
                    nextPhase = 5;
                    // check exp
                    int dedLvl = t.getFrontMon().getLvl();
                    int significanceFactor = 25; // constant for this game, but varies in PTU
                    int expToGive = dedLvl * significanceFactor / (trainers.size() - 1);
                    for (int j = 0; j < trainers.size(); ++j) {
                        if (i == j) {
                            continue;
                        }
                        if (trainers.get(j).getFrontMon().getCurrentHp() > 0) {
                            trainers.get(j).getFrontMon().addExp(expToGive);
                            s +=  trainers.get(j).getFrontMon().getName() + " gained ";
                            s += "" + expToGive + " exp";
                            boolean leveled = trainers.get(j).getFrontMon().levelUp();
                            s += leveled ? " and leveled up!\n" : ".\n";
                        }
                    }
                }
            } else if (t.getFrontMon().getCurrentHp() <= 0) {
                int dedLvl = t.getFrontMon().getLvl();
                int significanceFactor = 25; // constant for this game, but varies in PTU
                int expToGive = dedLvl * significanceFactor / (trainers.size() - 1);
                for (int j = 0; j < trainers.size(); ++j) {
                    if (i == j) {
                        continue;
                    }
                    if (trainers.get(j).getFrontMon().getCurrentHp() > 0) {
                        trainers.get(j).getFrontMon().addExp(expToGive);
                        s +=  trainers.get(j).getFrontMon().getName() + " gained ";
                        s += "" + expToGive + " exp";
                        boolean leveled = trainers.get(j).getFrontMon().levelUp();
                        s += leveled ? " and leveled up!\n" : ".\n";
                    }
                }
                int newIndex = t.forceSwitch();
                boolean hasSwitched = false;
                while (!hasSwitched) {
                    hasSwitched = t.getTrainer().switchMons(0, newIndex);
                }
                
                s += t.getTrainer().getName() + " sent out "
                        + t.getFrontMon().getName() + ".\n";
            }
        }
        ui.display(s);
        return nextPhase(acquired);
    }

    @Override
    public int queryUser() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public AbstractPhase nextPhase(ArrayList<Acquirable> a) {
        switch (nextPhase) {
            case 0:
                return new BeginningPhase(trainers, ui, weather, acquired);
            
            case 4:
                return new DeadPhase(trainers, ui, acquired);
            case 5:
                return new ReturnPhase(trainers, ui, acquired);
            case 6:
                return new CapturedPhase(trainers, ui, acquired);  
                // not reachable from here  
            default:
                return new BeginningPhase(trainers, ui, weather, acquired);
        }
    }

    @Override
    public void displayPrePhaseDialogue() {
        String s = "";
        // no dialogue here at present, so just make a new line
        ui.display(s);
    }

}
