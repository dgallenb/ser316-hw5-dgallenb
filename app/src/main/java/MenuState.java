import java.util.ArrayList;

public class MenuState implements GameState {
    protected TrainerEntity player;
    protected ArrayList<TrainerEntity> trainers;
    protected UI ui;
    protected Weather weather;
    
    public MenuState(ArrayList<TrainerEntity> trainers, UI ui, Weather weather) {
        player = trainers.get(0);
        this.trainers = trainers;
        this.ui = ui;
        this.weather = weather;
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
    public ArrayList<TrainerEntity> processState(ArrayList<TrainerEntity> trainers) {
        this.trainers = trainers;
        handleBaseMenu();
        
        return this.trainers;
    }
    
    public int handleBaseMenu() {
        String s = "";
        s += player.getTrainer().getName() + ": $" + player.getTrainer().getMoney() + "\n";
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
        default:
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
        s += "" + (player.getTrainer().getMonCount() + 1) + ". Back\n";
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
        int choice = ui.getInt(1, 2);
        switch(choice) {
        case 1:
            pat(index); // does nothing of note, then moves to the handleMonMenu.
            handleMonMenu();
            break;
        case 2:
        default:
            handleMonMenu();
            break;
            
        }
    }
    
    public void handleSwitchMenu() {
        ((HumanTrainerEntity) player).handleSwitchMenu();
    }
    
    public void handleItemsMenu() {
        ((HumanTrainerEntity) player).handleItemsMenu();
    }
    
    public void pat(int index) {
        Codemon mon = player.getTrainer().getMons()[index];
        ui.display("You pat " + mon.getName() + " on the head. They trill contentedly.\n");
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
    public int nextState() {
        return 0;
    }

}
