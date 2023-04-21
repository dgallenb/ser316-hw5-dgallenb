import java.util.ArrayList;

public class BeginningPhase implements AbstractPhase {
    protected TrainerEntity[] trainers; // assumed to be a human player
    protected UI ui;
    protected int nextPhase;
    protected ArrayList<Acquirable> acquired;
    protected Weather weather;
    
    public BeginningPhase(TrainerEntity t1, TrainerEntity t2, UI ui, Weather w) {
        this.trainers = new TrainerEntity[2];
        this.trainers[0] = t1;
        this.trainers[1] = t2;
        this.ui = ui;
        acquired = new ArrayList<Acquirable>();
        this.nextPhase = 1;
        this.weather = w;
    }
    
    public BeginningPhase(TrainerEntity t1, TrainerEntity t2, UI ui, Weather w,
            ArrayList<Acquirable>a) {
        this.trainers = new TrainerEntity[2];
        this.trainers[0] = t1;
        this.trainers[1] = t2;
        this.ui = ui;
        this.nextPhase = 1;
        acquired = a;
        this.weather = w;
    }
    
    public TrainerEntity[] getTrainers() {
        return trainers;
    }

    public void setTrainers(TrainerEntity[] trainers) {
        this.trainers = trainers;
    }

    public UI getUi() {
        return ui;
    }

    public void setUi(UI ui) {
        this.ui = ui;
    }

    public int getNextPhase() {
        return nextPhase;
    }

    public void setNextPhase(int nextPhase) {
        this.nextPhase = nextPhase;
    }
    
    public void addAcquired(Acquirable a) {
        acquired.add(a);
    }

    public BattleState getBattleState() {
        return battleState;
    }

    public void setBattleState(BattleState battleState) {
        this.battleState = battleState;
    }

    BattleState battleState;
    
    

    @Override
    public AbstractPhase performPhase() {
        this.displayPrePhaseDialogue();
        if(nextPhase != 6) { // skip combat if a capture succeeds.
            this.queryUser();
        }
        
        return nextPhase(acquired);
        
        
    }
    
    public void handleUserInput(TrainerEntity t, int choice) {
        nextPhase = 1;
        String s = "";
        s += t.getTrainer().getName() + " ";
        switch(choice) {
        case 0: 
            s += "just wants to attack. \n";
            break;
        case 1:
            // already handled inside the TrainerEntity.
        case 2:
            // already handled inside the TrainerEntity.
            /*
            t.getTrainer().switchMons(0, choice);
            s += "switched to " + t.getFrontMon().getName();
            break;
            */
        case 3:
            s += "used focused training.\n";
            t.getFrontMon().applyStatChange(new int[] {0, 0, 0, 0, 0, 0, 1, 0});
            break;
        case 4:
            s += "used inspired training. \n";
            t.getFrontMon().applyStatChange(new int[] {0, 0, 0, 0, 1, 0, 0, 0});
            break;
        case 5:
            s += "used brutal training. \n";
            t.getFrontMon().applyStatChange(new int[] {0, 0, 0, 0, 0, 0, 0, 1});
            break;
        case 6:
            s += "used agility training. \n";
            t.getFrontMon().applyStatChange(new int[] {0, 0, 0, 0, 0, 1, 0, 0});
            break;
        case 7:
            /*
            s += "used a potion.\n";
            int healed = t.getFrontMon().heal(20);
            s += t.getFrontMon().getName() + " healed by " + healed + ".\n";
            break;
            */
            s += "used a capture stone.\n";
            if(trainers[1] instanceof WildEntity) {
                boolean captureResult = trainers[1].getFrontMon().attemptCapture();
                if(captureResult) {
                    nextPhase = 6;
                    this.addAcquired(trainers[1].getFrontMon());
                    s += "Success!\n";
                }
                else {
                    s += "Failure!\n";
                }
            }
            else {
                s += "That's not an acquirable codemon!\n";
            }
            
            
        }
        ui.display(s);
        
    }

    @Override
    public int queryUser() {
        int[] choices = new int[trainers.length];
        for(int i = 0; i < trainers.length; ++i) {
            choices[i] = trainers[i].decideBeginning();
        }
        
        for(int i = 0; i < choices.length; ++i) {
            if(nextPhase != 6) {
                handleUserInput(trainers[i], choices[i]);
            }
        }
       
       return 0;
    }

    @Override
    public AbstractPhase nextPhase(ArrayList<Acquirable> a) {
        switch(nextPhase) {
        case 0:
            return new BeginningPhase(trainers[0], trainers[1], ui, weather, acquired);
        case 1:
            return new BattlePhase(trainers[0], trainers[1], ui, weather, acquired);
        case 2:
            return new EndPhase(trainers[0], trainers[1], ui, weather, acquired);
        case 3:
            return new CleanupPhase(trainers[0], trainers[1], ui, weather, acquired);
        case 4:
            return new DeadPhase(trainers[0], trainers[1], ui, weather, acquired);
        case 5:
            return new ReturnPhase(trainers[0], trainers[1], ui, weather, acquired);
        case 6:
            return new CapturedPhase(trainers[0], trainers[1], ui, weather, acquired);
        default:
            return new BattlePhase(trainers[0], trainers[1], ui, weather, acquired);
        }
        
    }

    @Override
    public void displayPrePhaseDialogue() {
        String s = "";
        for(TrainerEntity t : trainers) {
            if( t != null ) {
                s += t.getTrainer().getName() + " has " + t.getFrontMon().getName();
                s += " (" + t.getFrontMon().getCurrentHP() + 
                        "/" + t.getFrontMon().getHp() + ").\n";
            }
        }
        
        ui.display(s);
    }

}
