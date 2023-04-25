import java.util.ArrayList;

public class BeginningPhase implements AbstractPhase {
    protected ArrayList<TrainerEntity> trainers; // assumed to be a human player
    protected UI ui;
    protected int nextPhase;
    protected ArrayList<Acquirable> acquired;
    protected Weather weather;
    
    /**
     * Constructor.
     * @param trainers The trainers involved in the battle.
     * @param ui The UI to prod for input and displays.
     */
    public BeginningPhase(ArrayList<TrainerEntity> trainers, UI ui, Weather w) {
        this.trainers = trainers;
        this.ui = ui;
        acquired = new ArrayList<Acquirable>();
        this.nextPhase = 1;
        this.weather = w;
    }
    
    /**
     * Constructor.
     * @param trainers The trainers involved in the battle.
     * @param ui The UI to prod for input and displays.
     * @param w The current weather.
     * @param a A list of the acquired rewards for the player.
     */
    public BeginningPhase(ArrayList<TrainerEntity> trainers, UI ui, Weather w,
            ArrayList<Acquirable> a) {
        this.trainers = trainers;
        this.ui = ui;
        this.nextPhase = 1;
        acquired = a;
        this.weather = w;
    }
    
    /*
    public ArrayList<TrainerEntity> getTrainers() {
        return trainers;
    }

    public void setTrainers(ArrayList<TrainerEntity> trainers) {
        this.trainers = trainers;
    }
    */

    public UI getUi() {
        return ui;
    }

    public void setUi(UI ui) {
        this.ui = ui;
    }
    /*
    public int getNextPhase() {
        return nextPhase;
    }

    public void setNextPhase(int nextPhase) {
        this.nextPhase = nextPhase;
    }
    */
    public void addAcquired(Acquirable a) {
        acquired.add(a);
    }
    
    

    @Override
    public AbstractPhase performPhase() {
        this.displayPrePhaseDialogue();
        if (nextPhase != 6) { // skip combat if a capture succeeds.
            this.queryUser();
        }
        
        return nextPhase(acquired);
        
        
    }
    
    /**
     * Handles user input for the given trainer entity.
     * @param t The trainer entity in question.
     * @param choice The choice they made.
     */
    public void handleUserInput(TrainerEntity t, int choice) {
        nextPhase = 1;
        String s = "";
        s += t.getTrainer().getName() + " ";
        switch (choice) {
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
                t.getFrontMon().addAccuracy(1);
                break;
            case 4:
                s += "used inspired training. \n";
                t.getFrontMon().addEvade(1);
                break;
            case 5:
                s += "used brutal training. \n";
                t.getFrontMon().addCritRange(1);
                break;
            case 6:
                s += "used agility training. \n";
                t.getFrontMon().addInitiative(4);
                break;
            case 7:
                /*
                s += "used a potion.\n";
                int healed = t.getFrontMon().heal(20);
                s += t.getFrontMon().getName() + " healed by " + healed + ".\n";
                break;
                */
                s += "used a capture stone.\n";
                // determine target
                // Generally, a multi-trainer battle should not have wild mons.
                // Target index is not allowed to be zero, as that slot should be
                // the human trainer and/or primary opponent (ie, target for all 
                // other trainers).
                int targetIndex = 1;
                if (trainers.size() != 2) {
                    targetIndex = 1;
                }
                if ((targetIndex > 0) && (targetIndex < trainers.size())) {
                    if (trainers.get(targetIndex) instanceof WildEntity) {
                        boolean captureResult = trainers.get(targetIndex)
                                .getFrontMon().attemptCapture();
                        if (captureResult) {
                            nextPhase = 6;
                            this.addAcquired(trainers.get(targetIndex).getFrontMon());
                            s += "Success!\n";
                        } else {
                            s += "Failure!\n";
                        }
                    } else {
                        s += "That's not an acquirable codemon!\n";
                    }
                } else {
                    s += "That's not an acquirable codemon!\n";
                }
                break;
            default:
                s += "just wants to attack. \n";
                break;
            
        }
        ui.display(s);
        
    }

    @Override
    public int queryUser() {
        int[] choices = new int[trainers.size()];
        for (int i = 0; i < trainers.size(); ++i) {
            if (trainers.get(i) != null) {
                choices[i] = trainers.get(i).decideBeginning();
            } else {
                choices[i] = 0;
            }
            
        }
        
        for (int i = 0; i < choices.length; ++i) {
            if (nextPhase != 6) {
                handleUserInput(trainers.get(i), choices[i]);
            }
        }
       
        return 0;
    }

    @Override
    public AbstractPhase nextPhase(ArrayList<Acquirable> a) {
        switch (nextPhase) {
            /*
            case 0:
                return new BeginningPhase(trainers, ui, weather, acquired);
                */
            case 1:
                return new BattlePhase(trainers, ui, weather, acquired);
                /*
            case 2:
                return new EndPhase(trainers, ui, weather, acquired);
             
            case 4:
                return new DeadPhase(trainers, ui, acquired);
            case 3:
            case 5:
                return new ReturnPhase(trainers, ui, acquired);
                */
            case 6:
            
                return new CapturedPhase(trainers, ui, acquired);
            default:
                return new BattlePhase(trainers, ui, weather, acquired);
        }
        
    }

    @Override
    public void displayPrePhaseDialogue() {
        String s = "";
        for (TrainerEntity t : trainers) {
            if (t != null) {
                s += t.getTrainer().getName() + " has " + t.getFrontMon().getName();
                s += " (" + t.getFrontMon().getCurrentHp()
                        + "/" + t.getFrontMon().getHp() + ").\n";
            }
        }
        
        ui.display(s);
    }

}
