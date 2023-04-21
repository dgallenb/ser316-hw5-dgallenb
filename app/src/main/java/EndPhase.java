import java.util.ArrayList;

/**
 * The End phase is here solely to allow a free switch for a downed mon,
 * apply exp to the winner,
 * and to end the battle if there's no mons left on one side.
 * @author DJ
 *
 */
public class EndPhase implements AbstractPhase {
    protected TrainerEntity[] trainers; // assumed to be a human player
    protected UI ui;
    protected int nextPhase;
    protected ArrayList<Acquirable> acquired;
    protected Weather weather;
    
    public EndPhase(TrainerEntity t1, TrainerEntity t2, UI ui, Weather w) {
        this.trainers = new TrainerEntity[2];
        this.trainers[0] = t1;
        this.trainers[1] = t2;
        this.ui = ui;
        this.weather = w;
        this.nextPhase = 0;
        acquired = new ArrayList<Acquirable>();
    }
    
    public EndPhase(TrainerEntity t1, TrainerEntity t2, UI ui, Weather w,
            ArrayList<Acquirable>a) {
        this.trainers = new TrainerEntity[2];
        this.trainers[0] = t1;
        this.trainers[1] = t2;
        this.ui = ui;
        this.weather = w;
        this.nextPhase = 0;
        acquired = a;
    }

    @Override
    public AbstractPhase performPhase() {
        String s = "";
        // 1. check for ded mons
        for(int i = 0; i < trainers.length; ++i) {
            TrainerEntity t = trainers[i];
            if(t.getTrainer().countLiveMons() < 1) {
                
                s += t.getTrainer().getName() + " is out of codemons.\n";
                if(t instanceof HumanTrainerEntity) {
                    nextPhase = 4;
                }
                else {
                    nextPhase = 3;
                    // check exp
                    int dedLvl = t.getFrontMon().getLvl();
                    int significanceFactor = 25; // constant for this game, but varies in PTU
                    int expToGive = dedLvl * significanceFactor / (trainers.length - 1);
                    for(int j = 0; j < trainers.length; ++j) {
                        if(i == j) {
                            continue;
                        }
                        if(trainers[j].getFrontMon().getCurrentHP() > 0) {
                            trainers[j].getFrontMon().addExp(expToGive);
                            s +=  trainers[j].getFrontMon().getName() + " gained ";
                            s += "" + expToGive + " exp";
                            boolean leveled = trainers[j].getFrontMon().levelUp();
                            s += leveled ? " and leveled up!\n" : ".\n";
                        }
                    }
                }
            }
            else if(t.getFrontMon().getCurrentHP() <= 0) {
                int dedLvl = t.getFrontMon().getLvl();
                int significanceFactor = 25; // constant for this game, but varies in PTU
                int expToGive = dedLvl * significanceFactor / (trainers.length - 1);
                for(int j = 0; j < trainers.length; ++j) {
                    if(i == j) {
                        continue;
                    }
                    if(trainers[j].getFrontMon().getCurrentHP() > 0) {
                        trainers[j].getFrontMon().addExp(expToGive);
                        s +=  trainers[j].getFrontMon().getName() + " gained ";
                        s += "" + expToGive + " exp";
                        boolean leveled = trainers[j].getFrontMon().levelUp();
                        s += leveled ? " and leveled up!\n" : ".\n";
                    }
                }
                int newIndex = t.forceSwitch();
                t.getTrainer().switchMons(0, newIndex);
                s += t.getTrainer().getName() + " sent out " + 
                        t.getFrontMon().getName() + ".\n";
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
        switch(nextPhase) {
        case 0:
            return new BeginningPhase(trainers[0], trainers[1], ui, weather, acquired);
        case 3:
            return new CleanupPhase(trainers[0], trainers[1], ui, weather, acquired);
        case 4:
            return new DeadPhase(trainers[0], trainers[1], ui, weather, acquired);
        default:
            return new BeginningPhase(trainers[0], trainers[1], ui, weather, acquired);
        }
    }

    @Override
    public void displayPrePhaseDialogue() {
        String s = "";
        // no dialogue here at present, so just make a new line
        ui.display(s);
    }

}
