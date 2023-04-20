
public class HumanTrainerEntity extends TrainerEntity {
    protected UI ui;

    public HumanTrainerEntity(Trainer t) {
        super(t);
        ui = new TextUI();
    }

    @Override
    public int decideBeginning() {
        int input = ui.getInt(0, 11);
        // TODO Auto-generated method stub
        return input;
    }

    @Override
    public int decideInput(int phase) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int decideBattle() {
        String s = "";
        Codemon firstMon = getFrontMon();
        int[] moveIndices = firstMon.getAvailableMoveIndices();
        
        if(moveIndices.length > 0) {
            s += "Move choices: \n";
            for(int i = 0; i < moveIndices.length; ++i) {
                s += "" + (i + 1) + ". " + firstMon.getMove(moveIndices[i]).getName() + "\n";
            }
            
        } 
        ui.display(s);
        int index = ui.getInt(1, moveIndices[moveIndices.length - 1]);
        int modifiedIndex = index - 1;
        while(!getFrontMon().getMove(modifiedIndex).isAvailable()) {
            ui.display("Move unavailable!\n");
            modifiedIndex = ui.getInt(1, moveIndices[moveIndices.length - 1]) - 1;
        }
        // TODO Auto-generated method stub
        return modifiedIndex;
    }

    @Override
    public int decideEnd() {
        
        return forceSwitch();
    }

    @Override
    public int decideCleanup() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected int forceSwitch() {
        String s = "";
        s += "Switch to a new Codemon: \n";
        for(int i = 1; i < trainer.getMons().length; ++i) {
            if(trainer.getMons()[i].getCurrentHP() > 0) {
                s += "" + i + ". " + trainer.getMons()[i].getName() + "\n";
            }
        }
        ui.display(s);
        int index = ui.getInt(1, trainer.getMons().length);
        while((trainer.getMons()[index] == null) || 
                (trainer.getMons()[index].getCurrentHP() <= 0)) {
            ui.display("Invalid selection.\n");
            index = ui.getInt(1, trainer.getMons().length);
        }
        return index;
    }

}
