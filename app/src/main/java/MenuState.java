import java.util.ArrayList;

public class MenuState implements GameState {
    protected TrainerEntity player;
    //protected ArrayList<TrainerEntity> trainers;
    protected UI ui;
    //protected Weather weather;
    protected int nextState;
    
    /**
     * The base constructor for the menu state.
     * @param trainers The current list of trainers to track (should be only 1).
     * @param ui The UI to use to get input and send output.
     * @param weather The current weather in the game.
     */
    public MenuState(ArrayList<TrainerEntity> trainers, UI ui, Weather weather) {
        player = trainers.get(0);
        //this.trainers = trainers;
        this.ui = ui;
        //this.weather = weather;
        this.nextState = 0;
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
    public int processState() {
        handleBaseMenu();
        
        return nextState;
    }
    
    /**
     * Handles the base menu.
     * @return Returns the value the user selected (currently useless).
     */
    public int handleBaseMenu() {
        String s = "";
        s += player.getTrainer().getName() + ": $" + player.getTrainer().getMoney() + "\n";
        s += "Menu options: \n" + "1. Codemon\n" + "2. Items\n" + "3. Back";
        ui.display(s);
        int choice = ui.getInt(1, 3);
        switch (choice) {
            case 1:
                handleMonMenu(0);
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
    
    /**
     * Handles the basic codemon menu.
     * @param forcedChoice Added for testing. Ends loop if this is negative.
     */
    public void handleMonMenu(int forcedChoice) {
        String s = "";
        s += "Codemon: \n";
        for (int i = 0; i < player.getTrainer().getMonCount(); ++i) {
            if (player.getTrainer().getMon(i) != null) {
                s += "" + getChar(i) + ". "
                        + player.getTrainer().getMon(i).getName() + "\n";
            }
        }
        s += "Options: \n" + "1. Details\n" + "2. Switch\n" + "3. Back\n";
        ui.display(s);
        
        int choice = ui.getInt(1, 3);
        if(forcedChoice < 0) {
            return;
        }
        switch (choice) {
            case 1:
                handleDetailedMonMenu(0);
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
    
    /**
     * Handles the detailed mon menu.
     * @param forcedChoice Added for testing. Ends loop if this is -1.
     */
    public void handleDetailedMonMenu(int forcedChoice) {
        String s = "";
        s += "Choose a Codemon: \n";
        for (int i = 0; i < player.getTrainer().getMonCount(); ++i) {
            if (player.getTrainer().getMon(i) != null) {
                s += "" + (i + 1) + ". "
                        + player.getTrainer().getMon(i).getName() + "\n";
            }
        }
        s += "" + (player.getTrainer().getMonCount() + 1) + ". Back\n";
        ui.display(s);
        int choice = ui.getInt(1, player.getTrainer().getMonCount() + 1);
        if (forcedChoice == -7) {
            return;
        }
        else if(forcedChoice < 0) {
            choice = -1 * forcedChoice;
        }
        if (choice == (player.getTrainer().getMonCount() + 1)) {
            handleMonMenu(forcedChoice);
            return;
        } else if (player.getTrainer().getMon(choice - 1) == null) {
            ui.display("Error: ghost codemon detected!");
            handleDetailedMonMenu(forcedChoice);
        } else {
            handleDetailedDescriptionMenu(choice - 1, forcedChoice);
        }
        
    }
    
    /**
     * Handles the detailed description menu for the codemon at the specified index.
     * @param index The index of the codemon to give more details about.
     * @param forcedChoice Added for testing. Ends loop if this is -1.
     */
    public void handleDetailedDescriptionMenu(int index, int forcedChoice) {
        String s = "";
        Codemon mon = player.getTrainer().getMon(index);
        if (mon != null) {
            s += mon.getDescription() + "\n" + mon.getStatDesc();
            s += "\n" + mon.getMoveDesc() + "\n";
            s += "Options: \n" + "1. Pat\n" + "2. Back\n";
            ui.display(s);
            int choice = ui.getInt(1, 2);
            if(forcedChoice < 0) {
                choice = -1 * forcedChoice;
            }
            switch (choice) {
                case 1:
                    pat(index); // does nothing of note, then moves to the handleMonMenu.
                    handleMonMenu(forcedChoice);
                    break;
                case 2:
                    handleMonMenu(forcedChoice);
                    break;
                default:
                    handleMonMenu(forcedChoice);
                    break;
                
            }
        } else {
            handleMonMenu(forcedChoice);
        }
        
        
    }
    
    public void handleSwitchMenu() {
        ((HumanTrainerEntity) player).handleSwitchMenu();
    }
    
    public void handleItemsMenu() {
        ((HumanTrainerEntity) player).handleItemsMenu();
    }
    
    public void pat(int index) {
        Codemon mon = player.getTrainer().getMon(index);
        ui.display("You pat " + mon.getName() + " on the head. They trill contentedly.\n");
    }
    
    /**
     * Returns A, B, C, D, E, F, or G.
     * @param i The number the letter is in the alphabet.
     * @return A character as specified.
     */
    private String getChar(int i) {
        switch (i) {
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
            case 5:
                return "F";
            default: 
                return "G";
        }
    }
}
