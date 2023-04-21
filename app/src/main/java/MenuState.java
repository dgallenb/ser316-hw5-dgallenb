
public class MenuState implements GameState {
    protected TrainerEntity player;
    protected UI ui;
    
    public MenuState(TrainerEntity t, UI ui) {
        player = t;
        this.ui = ui;
    }

    /*
     * menus that can be cycled through:
     * Codemon
     *     Details
     *          choose a mon
     *              Pat
     *              Back
     *          Back
     *     Switch
     *          Choose a mon
     *              Choose another mon
     *                  swap
     *          Back
     *     Back
     * Items
     *      Choose an item
     *          Details
     *          Use
     *              Choose a mon
     *                  Use
     *                  Failed
     *              Back
     *          Back
     * Back
     * 
     */
    @Override
    public TrainerEntity[] processState() {
        handleBaseMenu();
        
        TrainerEntity[] output = new TrainerEntity[1];
        output[0] = player;
        return output;
    }
    
    public int handleBaseMenu() {
        String s = "";
        s += "Menu options: \n" + "1. Codemon\n" + "2. Items\n" + "3. Back";
        ui.display(s);
        int choice = ui.getInt(1, 3);
        switch(choice) {
        case 1:
            handleMonMenu();
            break;
        case 2:
            handleItemsMenu();
            break;
        case 3:
        default:
            return 0;
        }
        
        return choice;
    }
    
    public void handleMonMenu() {
        String s = "";
        s += "Codemon: \n";
        for(int i = 0; i < player.getTrainer().getMons().length; ++i) {
            if( player.getTrainer().getMons()[i] != null) {
                s += "" + getChar(i) + ". " + 
                        player.getTrainer().getMons()[i].getName() + "\n";
            }
        }
        s += "Options: \n" + "1. Details\n" + "2. Switch\n" + "3. Back\n";
        ui.display(s);
        
        int choice = ui.getInt(1, 3);
        
        switch(choice) {
        case 1:
            handleDetailedMonMenu();
            break;
        case 2:
            handleSwitchMenu();
            break;
        case 3:
            handleBaseMenu();
        }
        return;
    }
    
    public void handleDetailedMonMenu() {
        String s = "";
        s += "Choose a Codemon: \n";
        for(int i = 0; i < player.getTrainer().getMons().length; ++i) {
            if( player.getTrainer().getMons()[i] != null) {
                s += "" + (i + 1) + ". " + 
                        player.getTrainer().getMons()[i].getName() + "\n";
            }
        }
        s += "" + (player.getTrainer().getMonCount() + 1) + "\n";
        ui.display(s);
        int choice = ui.getInt(1, player.getTrainer().getMonCount() + 1);
        if(choice == (player.getTrainer().getMonCount() + 1)) {
            handleMonMenu();
            return;
        }
        else if(player.getTrainer().getMons()[choice - 1] == null) {
            ui.display("Error: ghost codemon detected!");
            handleDetailedMonMenu();
        }
        else {
            handleDetailedDescriptionMenu(choice - 1);
        }
        
    }
    
    public void handleDetailedDescriptionMenu(int index) {
        String s = "";
        Codemon mon = player.getTrainer().getMons()[index];
        s += mon.getDescription() + "\n" + mon.getStatDesc() + "\n" + mon.getMoveDesc() + "\n";
        s += "Options: \n" + "1. Pat\n" + "2. Back\n";
        ui.display(s);
        int choice = ui.getInt(1, player.getTrainer().getMonCount());
        switch(choice) {
        case 1:
            pat(index); // does nothing of note, then moves to the handleMonMenu.
        case 2:
            handleMonMenu();
        }
    }
    
    public void handleSwitchMenu() {
        String s = "";
        s += "Choose a Codemon: \n";
        for(int i = 0; i < player.getTrainer().getMons().length; ++i) {
            if( player.getTrainer().getMons()[i] != null) {
                s += "" + (i + 1) + ". " + 
                        player.getTrainer().getMons()[i].getName() + "\n";
            }
        }
        ui.display(s);
        int choice1 = ui.getInt(1, player.getTrainer().getMonCount());
        if(player.getTrainer().getMons()[choice1 - 1] == null) {
            ui.display("Error: ghost codemon detected!");
            handleSwitchMenu();
            return;
        }
        else {
            ui.display("Choose another Codemon:");
            int choice2 = ui.getInt(1, player.getTrainer().getMonCount());
            while(true) {
                if((choice2 != choice1) && 
                        (player.getTrainer().getMons()[choice2 - 1] != null)) {
                        break;
                }
                else {
                    ui.display("Invalid selection! Choose another Codemon:");
                    choice2 = ui.getInt(1, player.getTrainer().getMonCount());
                }
            
            }
            player.getTrainer().switchMons(choice1, choice2);
        }
    }
    
    public void pat(int index) {
        Codemon mon = player.getTrainer().getMons()[index];
        ui.display("You pat " + mon.getName() + " on the head. They trill contentedly.\n");
    }
    
    public void handleItemsMenu() {
        String s = "";
        s += "Choose an item: \n";
        for(int i = 0; i < player.getTrainer().countItems(); ++i) {
            Item item = player.getTrainer().getItem(i);
            s += "" + (i + 1) + item.getName() + "\n";
        }
        s += "" + (player.getTrainer().countItems() + 1) + ". Back\n";
        ui.display(s);
        int choice = ui.getInt(1,  player.getTrainer().countItems() + 1);
        if(choice == player.getTrainer().countItems() + 1) {
            handleBaseMenu();
        }
        else {
            handleItemSpecificMenu(choice - 1);
        }
    }
    
    public void handleItemSpecificMenu(int index) {
        String s = player.getTrainer().getItem(index).toString() + "\n";
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
        for(int i = 0; i < player.getTrainer().getMons().length; ++i) {
            if( player.getTrainer().getMons()[i] != null) {
                s += "" + (i + 1) + ". " + 
                        player.getTrainer().getMons()[i].getName() + "\n";
            }
        }
        s += "" + (player.getTrainer().getMonCount() + 1) + ". Back\n";
        ui.display(s);
        int choice = ui.getInt(1, player.getTrainer().getMonCount() + 1);
        if(choice == (player.getTrainer().getMonCount() + 1)) {
            handleItemSpecificMenu(index);
        }
        else {
            boolean result = player.getTrainer().getItem(index).
                    use(player.getTrainer().getMons()[choice]);
            if(result) {
                player.getTrainer().getItem(index).consume();
                if(player.getTrainer().getItem(index).getQuantity() < 1) {
                    player.getTrainer().removeItem(index);
                }
                ui.display("Used " + player.getTrainer().getItem(index) + " on " + 
                        player.getTrainer().getMons()[choice]);                
            }
            else {
                ui.display("You can't use " + player.getTrainer().getItem(index) + " on " + 
                        player.getTrainer().getMons()[choice]);
            }
            handleItemsMenu();
        }
        
    }
    
    /**
     * Returns A, B, C, D, E, or F
     * @param i
     * @return
     */
    private String getChar(int i) {
        switch(i) {
        case 0:
            return "A";
        case 1:
            return "B";
        case 2:
            return "C";
        case 3:
            return "D";
        case 4: 
            return "E";
        default:
            return "F";
        }
    }

    @Override
    public void moveState() {
        // TODO Auto-generated method stub

    }

    @Override
    public String displayMenu() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void executeMenuOption(int option) {
        // TODO Auto-generated method stub

    }

}
