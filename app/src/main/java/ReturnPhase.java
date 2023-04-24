import java.util.ArrayList;

public class ReturnPhase extends CleanupPhase implements AbstractPhase {
    
    public ReturnPhase(ArrayList<TrainerEntity> trainers, UI ui, 
            Weather weather, ArrayList<Acquirable> a) {
        super(trainers, ui, weather, a);
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
    
    protected void processRewards(TrainerEntity t) {
        while(acquired.size() > 0) {
            Acquirable a = acquired.remove(0);
            if(a instanceof Money) {
                awardMoney(t, (Money) a);
            }
            else if(a instanceof Item) {
                awardItem(t, (Item) a);
            }
            else {
                // nothing should be here, but eh.
            }
        }
    }
    
    protected void awardMoney(TrainerEntity t, Money m) {
        t.getTrainer().setMoney(t.getTrainer().getMoney() + m.getTotal());
        ui.display("You got " + m.toString());
    }
    
    /**
     * Unused at the moment, but could be handy down the road
     * @param t The trainer to receive the item.
     * @param i The item to be given.
     */
    protected void awardItem(TrainerEntity t, Item i) {
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
                    ui.display("It became " + e.getName() + "!");
                }
            }
        }
    }
    
    protected void collectMoney() {
        int total = 0;
        for(int i = 1; i < trainers.size(); ++i) {
            total += calculateMoney(trainers.get(i));
        }
        acquired.add(new Money(total));
    }
    
    protected int calculateMoney(TrainerEntity t) {
        int total = 0;
        for(Codemon c : t.getTrainer().getMons()) {
            if(c != null) {
                total += c.getExp();
            }
        }
        return total + 50;
    }

    @Override
    protected void generalCleanup() {
        checkEvolutions(trainers.get(0));
        processRewards(trainers.get(0));
        
    }

}
