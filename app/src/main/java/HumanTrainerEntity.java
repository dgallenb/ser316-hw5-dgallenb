
public class HumanTrainerEntity extends TrainerEntity {
    protected UI ui;

    public HumanTrainerEntity(Trainer t) {
        super(t);
        ui = new TextUI();
    }

    @Override
    public int decideBeginning() {
        String s = "";
        s += "1. Use item.\n 2. Switch codemon. \n";
        s += "3. Focused training. \n4. Inspired training. \n";
        s += "5. Brutal training. \n6. Agility training. \n";
        ui.display(s);
        int input = ui.getInt(1, 6);
        switch(input) {
        case 3:
        case 4:
        case 5:
        case 6:
            return input;
        case 1:
            handleItemsMenu();
            return 1;
        case 2:
            handleSwitchMenu();
            
        }
        // TODO Auto-generated method stub
        return input;
    }
    
    public void handleSwitchMenu() {
        String s = "";
        s += "Choose a Codemon: \n";
        for(int i = 0; i < getTrainer().getMons().length; ++i) {
            if(getTrainer().getMons()[i] != null) {
                s += "" + (i + 1) + ". " + getTrainer().getMons()[i].getName() + "\n";
            }
        }
        ui.display(s);
        int choice1 = ui.getInt(1, getTrainer().getMonCount());
        if(getTrainer().getMons()[choice1 - 1] == null) {
            ui.display("Error: ghost codemon detected!");
            handleSwitchMenu();
            return;
        }
        else {
            ui.display("Choose another Codemon:");
            int choice2 = ui.getInt(1, getTrainer().getMonCount());
            while(true) {
                if((choice2 != choice1) && 
                        (getTrainer().getMons()[choice2 - 1] != null)) {
                        break;
                }
                else {
                    ui.display("Invalid selection! Choose another Codemon:");
                    choice2 = ui.getInt(1, getTrainer().getMonCount());
                }
            
            }
            getTrainer().switchMons(choice1, choice2);
        }
    }
    
    public void handleItemsMenu() {
        String s = "";
        s += "Choose an item: \n";
        for(int i = 0; i <  getTrainer().countItems(); ++i) {
            Item item =  getTrainer().getItem(i);
            s += "" + (i + 1) + item.getName() + "\n";
        }
        s += "" + ( getTrainer().countItems() + 1) + ". Back\n";
        ui.display(s);
        int choice = ui.getInt(1,   getTrainer().countItems() + 1);
        if(choice ==  getTrainer().countItems() + 1) {
            return;
        }
        else {
            handleItemSpecificMenu(choice - 1);
        }
    }
    
    public void handleItemSpecificMenu(int index) {
        String s =  getTrainer().getItem(index).toString() + "\n";
        s += "1. Use\n" + "2. Back";
        ui.display(s);
        int choice = ui.getInt(1, 2);
        if(choice == 1) {
            handleUseItemMenu(index);
        }
        else {
            handleItemsMenu();
        }
    }
    
    public void handleUseItemMenu(int index) {
        String s = "";
        s += "Choose a Codemon: \n";
        for(int i = 0; i <  getTrainer().getMons().length; ++i) {
            if(  getTrainer().getMons()[i] != null) {
                s += "" + (i + 1) + ". " + 
                         getTrainer().getMons()[i].getName() + "\n";
            }
        }
        s += "" + ( getTrainer().getMonCount() + 1) + ". Back\n";
        ui.display(s);
        int choice = ui.getInt(1,  getTrainer().getMonCount() + 1);
        if(choice == ( getTrainer().getMonCount() + 1)) {
            handleItemSpecificMenu(index);
        }
        else {
            boolean result =  getTrainer().getItem(index).
                    use( getTrainer().getMons()[choice]);
            if(result) {
                 getTrainer().getItem(index).consume();
                if( getTrainer().getItem(index).getQuantity() < 1) {
                     getTrainer().removeItem(index);
                }
                ui.display("Used " +  getTrainer().getItem(index) + " on " + 
                         getTrainer().getMons()[choice]);                
            }
            else {
                ui.display("You can't use " +  getTrainer().getItem(index) + " on " + 
                         getTrainer().getMons()[choice]);
            }
            handleItemsMenu();
        }
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
