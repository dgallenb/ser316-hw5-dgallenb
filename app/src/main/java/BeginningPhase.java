import java.util.ArrayList;

public class BeginningPhase implements AbstractPhase {
    protected TrainerEntity[] trainers; // assumed to be a human player
    protected UI ui;
    protected int nextPhase;
    protected ArrayList<Acquirable> acquired;
    
    public BeginningPhase(TrainerEntity t1, TrainerEntity t2, UI ui) {
        this.trainers = new TrainerEntity[2];
        this.trainers[0] = t1;
        this.trainers[1] = t2;
        this.ui = ui;
        acquired = new ArrayList<Acquirable>();
    }
    
    public BeginningPhase(TrainerEntity t1, TrainerEntity t2, UI ui, 
            ArrayList<Acquirable>a) {
        this.trainers = new TrainerEntity[2];
        this.trainers[0] = t1;
        this.trainers[1] = t2;
        this.ui = ui;
        acquired = a;
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
        this.queryUser();
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
        case 2:
        case 3:
        case 4:
        case 5:
            t.getTrainer().switchMons(0, choice);
            s += "switched to " + t.getFrontMon().getName();
            break;
        case 6:
            s += "used focused training.\n";
            t.getFrontMon().applyStatChange(new int[] {0, 0, 0, 0, 0, 0, 1, 0});
            break;
        case 7:
            s += "used inspired training. \n";
            t.getFrontMon().applyStatChange(new int[] {0, 0, 0, 0, 1, 0, 0, 0});
            break;
        case 8:
            s += "used brutal training. \n";
            t.getFrontMon().applyStatChange(new int[] {0, 0, 0, 0, 0, 0, 0, 1});
            break;
        case 9:
            s += "used agility training. \n";
            t.getFrontMon().applyStatChange(new int[] {0, 0, 0, 0, 0, 1, 0, 0});
            break;
        case 10:
            s += "used a potion.\n";
            int healed = t.getFrontMon().heal(20);
            s += t.getFrontMon().getName() + "healed by " + healed + ".\n";
            break;
        case 11:
            s += "used a capture stone.\n";
            if(t instanceof WildEntity) {
                boolean captureResult = t.getFrontMon().attemptCapture();
                if(captureResult) {
                    nextPhase = 3;
                    this.addAcquired(t.getFrontMon());
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
            choices[i] = trainers[i].decideInput(0);
        }
        
        for(int i = 0; i < choices.length; ++i) {
            handleUserInput(trainers[i], choices[i]);
        }
       
       return 0;
    }

    @Override
    public AbstractPhase nextPhase(ArrayList<Acquirable> a) {
        switch(nextPhase) {
        case 0:
            return new BeginningPhase(trainers[0], trainers[1], ui, acquired);
        case 1:
            return new BattlePhase(trainers[0], trainers[1], ui, acquired);
        case 2:
            return new EndPhase(trainers[0], trainers[1], ui, acquired);
        case 3:
            return new CleanupPhase(trainers[0], trainers[1], ui, acquired);
        default:
            return new BattlePhase(trainers[0], trainers[1], ui, acquired);
        }
        
    }

    @Override
    public void displayPrePhaseDialogue() {
        String s = "";
        for(TrainerEntity t : trainers) {
            s += t.getTrainer().getName() + " has " + t.getFrontMon().getName();
            s += " (" + t.getFrontMon().getCurrentHP() + 
                    "/" + t.getFrontMon().getHp() + ").\n";
        }
        
        s += "1. Use item.\n 2. Switch codemon. \n";
        s += "3. Focused training. \n4. Inspired training. \n";
        s += "5. Brutal training. \n6. Agility training. \n";
        
        ui.display(s);
        queryUser();
    }

}
