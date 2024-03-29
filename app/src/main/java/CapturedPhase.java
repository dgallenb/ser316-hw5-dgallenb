import java.util.ArrayList;

public class CapturedPhase extends CleanupPhase {

    public CapturedPhase(ArrayList<TrainerEntity> trainers, UI ui) {
        super(trainers, ui);
    }
    
    public CapturedPhase(ArrayList<TrainerEntity> trainers, UI ui,
            ArrayList<Acquirable> a) {
        super(trainers, ui, a);
    }
    
    
    protected void processRewards(TrainerEntity t) {
        while (acquired.size() > 0) {
            Acquirable a = acquired.remove(0);
            if (a instanceof Item) {
                awardItem(t, (Item) a);
            } else if (a instanceof Codemon) {
                awardCodemon(t, (Codemon) a); 
            } else {
                // nothing should be here, but eh.
            }
        }
    }
    
    /**
     * Unused at the moment, but could be handy down the road.
     * @param t The trainer to receive the item.
     * @param i The item to be given.
     */
    protected void awardItem(TrainerEntity t, Item i) {
        t.getTrainer().addItem(i);
        ui.display("You got a " + i.getName());
    }
    
    private void awardCodemon(TrainerEntity t, Codemon c) {
        String s = "";
        s += "You gained " + c.getName() + ", a " + c.getType().toString();
        s += " codemon.\n";
        if (t.getTrainer().getMonCount() < t.getTrainer().getMonCount()) {
            boolean added = t.getTrainer().addMon(c);
            if (!added) {
                s += "But for some reason, you couldn't keep them.\n";
                
            }
            ui.display(s);
            return;
        } else {
            s += "But you already have " + t.getTrainer().getMonCount() + " codemon\n";
            ui.display(s);
            releaseMon(t, c);
        }
        
        
        
    }
    
    private void releaseMon(TrainerEntity t, Codemon c) {
        String s = "";
        s += "Who would you like to release?\n";
        for (int i = 0; i < t.getTrainer().getMonCount(); ++i) {
            if (t.getTrainer().getMon(i) != null) {
                s += "" + (i + 1) + ". " + t.getTrainer().getMon(i).getName() + "\n";
            }
        }
        s += "" + (t.getTrainer().getMonCount() + 1) + ". " + c.getName() + "\n";
        ui.display(s);
        int index = ui.getInt(1, t.getTrainer().getMonCount() + 1) - 1;
        if (index == t.getTrainer().getMonCount()) {
            ui.display("You released " + c.getName() + ", never to see them again.");
            return;
        }
        while (t.getTrainer().getMon(index) == null) {
            ui.display("Invalid (and confusing) selection!\n");
            index = ui.getInt(1, t.getTrainer().getMonCount() + 1) - 1;
        }
        Codemon temp = t.getTrainer().getMon(index);
        
        t.getTrainer().replaceMon(c, index);
        ui.display("You replaced " + temp.getName() + " with " + c.getName());
    }

    @Override
    protected void generalCleanup() {
        // TODO Auto-generated method stub
        processRewards(trainers.get(0));
    }

}
