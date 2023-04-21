import java.util.ArrayList;

public class CleanupPhase implements AbstractPhase {
    protected TrainerEntity[] trainers; // assumed to be a human player
    protected UI ui;
    protected int nextPhase;
    protected ArrayList<Acquirable> acquired;
    protected Weather weather;
    
    public CleanupPhase(TrainerEntity t1, TrainerEntity t2, UI ui, Weather w) {
        this.trainers = new TrainerEntity[2];
        this.trainers[0] = t1;
        this.trainers[1] = t2;
        this.ui = ui;
        this.weather = w;
        this.nextPhase = 5;
        acquired = new ArrayList<Acquirable>();
    }
    
    public CleanupPhase(TrainerEntity t1, TrainerEntity t2, UI ui, Weather w,
            ArrayList<Acquirable>a) {
        this.trainers = new TrainerEntity[2];
        this.trainers[0] = t1;
        this.trainers[1] = t2;
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
        deadCheck();
        cleanCombatModifiers();
        if(nextPhase != 4) {
            processRewards(trainers[0]);
            checkEvolutions(trainers[0]);
        }
        
        return nextPhase(acquired);

    }
    
    private void deadCheck() {
        for(int i = trainers.length - 1; i >= 0; --i) {
            if(trainers[i].getTrainer().countLiveMons() < 1) {
                if(trainers[i] instanceof HumanTrainerEntity) {
                    nextPhase = 4;
                    ui.display("You have lost!\n");
                }
                else if(trainers[i] instanceof WildEntity) {
                    nextPhase = 6;
                }
                else if(trainers[i] instanceof ComputerEntity) {
                    int totalMoney = 0;
                    for(Codemon mon : trainers[i].getTrainer().getMons()) {
                        if(mon != null) {
                            totalMoney += 50 + (mon.getExp() / 2);
                        }
                    }
                    acquired.add(new Money(totalMoney));
                }
            }
        }
    }
    
    private void cleanCombatModifiers() {
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
    
    private void processRewards(TrainerEntity t) {
        
        while(acquired.size() > 0) {
            Acquirable a = acquired.remove(0);
            if(a instanceof Money) {
                awardMoney(t, (Money) a);
            }
            else if(a instanceof Item) {
                awardItem(t, (Item) a);
            }
            else if(a instanceof Codemon) {
                awardCodemon(t, (Codemon) a); 
            }
            else {
                // nothing should be here, but eh.
            }
        }
    }
    
    private void awardCodemon(TrainerEntity t, Codemon c) {
        String s = "";
        s += "You gained " + c.getName() + ", a " + c.getType().toString();
        s += " codemon.\n";
        if(t.getTrainer().getMonCount() < t.getTrainer().getMons().length) {
            boolean added = t.getTrainer().addMon(c);
            if(!added) {
                s += "But for some reason, you couldn't keep them.\n";
                
            }
            ui.display(s);
            return;
        }
        else {
            s += "But you already have " + t.getTrainer().getMonCount() + " codemon\n";
            ui.display(s);
            releaseMon(t, c);
        }
        
        
        
    }
    
    private void releaseMon(TrainerEntity t, Codemon c) {
        String s = "";
        s += "Who would you like to release?\n";
        for(int i = 0; i < t.getTrainer().getMons().length; ++i) {
            if(t.getTrainer().getMons()[i] != null) {
                s += "" + (i + 1) + ". " + t.getTrainer().getMons()[i].getName() + "\n";
            }
        }
        s += "" + (t.getTrainer().getMons().length + 1) + c.getName() + "\n";
        ui.display(s);
        int index = ui.getInt(1, t.getTrainer().getMons().length + 1) - 1;
        while(t.getTrainer().getMons()[index] == null) {
            ui.display("Invalid (and confusing) selection!\n");
            index = ui.getInt(1, t.getTrainer().getMons().length + 1) - 1;
        }
        Codemon temp = t.getTrainer().getMons()[index];
        
        t.getTrainer().replaceMon(c, index);
        ui.display("You replaced " + temp.getName() + " with " + c.getName());
    }
    
    private void awardMoney(TrainerEntity t, Money m) {
        t.getTrainer().setMoney(t.getTrainer().getMoney() + m.getTotal());
        ui.display("You got " + m.toString());
    }
    
    private void awardItem(TrainerEntity t, Item i) {
        t.getTrainer().addItem(i);
        ui.display("You got a " + i.getName());
    }
    
    private void checkEvolutions(TrainerEntity t) {
        for(int i = 0; i < t.getTrainer().getMons().length; ++i) {
            Codemon mon = t.getTrainer().getMons()[i];
            if(mon != null) {
                if(mon.canEvolve()) {
                    ui.display(mon.getName() + " is evolving!");
                    EvolvedCodemon e = mon.evolve();
                    t.getTrainer().replaceMon(e, i);
                    ui.display("It became " + mon.getName() + "!");
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
    }

}
