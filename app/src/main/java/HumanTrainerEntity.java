
public class HumanTrainerEntity extends TrainerEntity {
    protected UI ui;

    public HumanTrainerEntity(Trainer t) {
        super(t);
        ui = new TextUI();
    }
    
    public UI getUI() {
        return this.ui;
    }
    
    public void setUI(UI ui) {
        this.ui = ui;
    }

    @Override
    public int decideBeginning() {
        String[] choices = new String[] {"Use item.", "Switch codemon.",
            "Focused training.", "Inspired training.", 
            "Brutal training.", "Agility training.",
            "Use a Capture Stone."
        };
        int input = decideGeneric("", choices);
        switch (input) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return input;
            case 1:
                handleItemsMenu(0);
                return 1;
            case 2:
                handleSwitchMenu(0, 0);
                return 2;
            default:
                return 3;
            
        }
        
        //return input;
    }
    
    /**
     * Handles the switch codemon menu.
     * @param forcedChoice1 Leave at 0. Used only to force inputs via testing.
     * @param forcedChoice2 Leave at 0. Used only to force inputs via testing.
     */
    public void handleSwitchMenu(int forcedChoice1, int forcedChoice2) {
        if (trainer.getMonCount() < 2) {
            ui.display("Switching not available yet.");
            return;
        }
        String s = "";
        s += "Choose a Codemon: \n";
        int count = 0;
        for (int i = 0; i < getTrainer().getMonCount(); ++i) {
            if (getTrainer().getMon(i) != null) {
                s += "" + (i + 1) + ". " + getTrainer().getMon(i).getName() + "\n";
                ++count;
            }
        }
        s += "" + (count + 1) + ". Back\n";
        ui.display(s);
        int choice1 = ui.getInt(1, count + 1);
        if (forcedChoice1 != 0) {
            choice1 = forcedChoice1;
            if (forcedChoice1 == -7) {
                choice1 = 0;
            }
        }
        if (choice1 == (count + 1)) {
            return;
        }
        if (getTrainer().getMon(choice1 - 1) == null) {
            ui.display("Error: ghost codemon detected!");
            handleSwitchMenu(forcedChoice1 + 1, forcedChoice2);
            return;
        } else {
            ui.display("Choose another Codemon:");
            int choice2 = ui.getInt(1, count + 1);
            if (forcedChoice2 != 0) {
                if (forcedChoice2 == -7) {
                    choice2 = 0;
                } else {
                    choice2 = forcedChoice2;
                }
            }
            if (choice2 == (count + 1)) {
                return;
            }
            while (true) {
                if ((choice2 != choice1)
                        && (getTrainer().getMon(choice2 - 1) != null)) {
                    break;
                } else {
                    ui.display("Invalid selection! Choose another Codemon:");
                    choice2 = ui.getInt(1, count + 1);
                }
            
            }
            getTrainer().switchMons(choice1 - 1, choice2 - 1);
        }
    }
    
    /**
     * Handles the items menu.
     * @param forcedChoice Leave at 0. Used only in testing.
     */
    public void handleItemsMenu(int forcedChoice) {
        String s = "";
        s += "Choose an item: \n";
        for (int i = 0; i <  getTrainer().countItems(); ++i) {
            Item item =  getTrainer().getItem(i);
            s += "" + (i + 1) + ". " + item.getName() + "\n";
        }
        s += "" + (getTrainer().countItems() + 1) + ". Back\n";
        ui.display(s);
        int choice = ui.getInt(1,   getTrainer().countItems() + 1);
        if (forcedChoice != 0) {
            if (forcedChoice == -7) {
                choice = 0;
            } else {
                choice = forcedChoice;
            }
        }
        if (choice ==  getTrainer().countItems() + 1) {
            return;
        } else {
            handleItemSpecificMenu(choice - 1, 0);
        }
    }
    
    /**
     * Handles the details menu for the item at the specified index.
     * @param index The index of the item in question.
     * @param forcedChoice Leave at 0. Used in testing.
     */
    public void handleItemSpecificMenu(int index, int forcedChoice) {
        Item item = getTrainer().getItem(index);
        if (item != null) {
            String s = item.toString() + "\n";
            s += "1. Use\n" + "2. Back";
            ui.display(s);
            int choice = ui.getInt(1, 2);
            if (forcedChoice != 0) {
                if (forcedChoice == -7) {
                    choice = 0;
                } else {
                    choice = forcedChoice;
                }
            }
            if (choice == 1) {
                handleUseItemMenu(index);
            } else {
                handleItemsMenu(0);
            }
        } else {
            handleItemsMenu(0);
        }
        
    }
    
    /**
     * Handles using an item that was in the specified index.
     * @param index The index of the item to use on a codemon.
     */
    public void handleUseItemMenu(int index) {
        String s = "";
        s += "Choose a Codemon: \n";
        for (int i = 0; i < getTrainer().getMonCount(); ++i) {
            if (getTrainer().getMon(i) != null) {
                s += "" + (i + 1) + ". "
                         + getTrainer().getMon(i).getName() + "\n";
            }
        }
        s += "" + (getTrainer().getMonCount() + 1) + ". Back\n";
        ui.display(s);
        int choice = ui.getInt(1,  getTrainer().getMonCount() + 1);
        if (choice == (getTrainer().getMonCount() + 1)) {
            handleItemSpecificMenu(index, 0);
        } else {
            boolean result =  getTrainer().getItem(index)
                    .use(getTrainer().getMon(choice - 1));
            if (result) {
                getTrainer().getItem(index).consume();
                
                ui.display("Used " +  getTrainer().getItem(index).getName() + " on "
                         + getTrainer().getMon(choice - 1).getName());     
                if (getTrainer().getItem(index).getQuantity() < 1) {
                    getTrainer().removeItem(index);
                }
            } else {
                if (getTrainer().getItem(index) instanceof MoveItem) {
                    overwriteMoveMenu(getTrainer().getMon(choice - 1), 
                            (MoveItem) getTrainer().getItem(index));
                } else {
                    ui.display("You can't use " +  getTrainer().getItem(index).getName() 
                            + " on " + getTrainer().getMon(choice - 1).getName());
                }
                
            }
            handleItemsMenu(0);
        }
    }
    
    /**
     * Handles the menu for overwriting a codemon's move.
     * @param c The codemon learning a new move.
     * @param item The item that prompted learning the move.
     * @return True if the move was learned, false otherwise.
     */
    public boolean overwriteMoveMenu(Codemon c, MoveItem item) {
        ui.display("That codemon can't learn another move. "
                + "Would you like to erase a move?\n1. Yes\n2. No.");
        int choice = ui.getInt(1, 2);
        if (choice == 1) {
            String s = "";
            s += "Select a move to overwrite.\n";
            int count = 0;
            for (int i = 0; i < c.getMoveCount(); ++i) {
                Move m = c.getMove(i);
                if (m != null) {
                    s += "" + (i + 1) + ". " + m.getFullDesc() + "\n";
                    ++count;
                }
            }
            s += "" + (count + 1) + ". " + item.getMove().getFullDesc() + " (Cancel)\n";
            ui.display(s);
            int moveSelection = ui.getInt(1, count + 1);
            if (moveSelection == (count + 1)) {
                return false;
            } else {
                c.overrideMove(item.getMove(), moveSelection - 1);
                item.consume();
                if (item.getQuantity() < 1) {
                    getTrainer().removeItem(item);
                }
                ui.display("Move overwritten");
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public int decideInput(int phase) {
        switch (phase) {
            case 0:
                return decideBeginning();
            case 1:
                return decideBattle();
            case 2:
                return forceSwitch();
            case 3:
                return 0;
            default:
                return 0;
        }
    }

    /**
     * Returns the index of the move selected by the player. 
     * -1 means struggle. 
     * -2 means wait.
     */
    @Override
    public int decideBattle() {
        String s = "";
        Codemon firstMon = getFrontMon();
        int[] moveIndices = firstMon.getAvailableMoveIndices();
        int decisionIndex = 0;
        if (moveIndices.length > 0) {
            String[] choices = new String[moveIndices.length + 1];
            for (int i = 0; i < moveIndices.length; ++i) {
                choices[i] = firstMon.getMove(moveIndices[i]).getName();
            }
            choices[choices.length - 1] = "Wait";
            decisionIndex = decideGeneric("Move choices: ", choices);
            if (decisionIndex >= choices.length) {
                return -2;
            } else {
                --decisionIndex;
            }
        } else { // moveIndices.length < 1)
            String[] choices = new String[] {"Struggle", "Wait"};
            decisionIndex = decideGeneric("Move choices: ", choices);
            
            return (decisionIndex == 1) ? -1 : -2;
        }
        while (!getFrontMon().getMove(decisionIndex).isAvailable()) {
            ui.display("Move unavailable!\n");
            decisionIndex = ui.getInt(1, moveIndices[moveIndices.length - 1]) - 1;
            if (decisionIndex == moveIndices.length) {
                return -2;
            }
        }
        // TODO Auto-generated method stub
        return decisionIndex;
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
    
    protected int decideGeneric(String preChoiceText, String[] choices) {
        String s = preChoiceText + "\n";
        for (int i = 0; i < choices.length; ++i) {
            String choice = choices[i];
            s += "" + (i + 1) + ". " + choice + "\n";
        }
        ui.display(s);
        return ui.getInt(1, choices.length);
    }

    @Override
    protected int forceSwitch() {
        String s = "";
        s += "Switch to a new Codemon: \n";
        for (int i = 0; i < getTrainer().getMonCount(); ++i) {
            Codemon mon = getTrainer().getMon(i);
            if (mon != null) {
                if (mon.getCurrentHp() > 0) {
                    s += "" + i + ". " + getTrainer().getMon(i).getName() + "\n";
                }
            }
        }
        ui.display(s);
        int index = ui.getInt(1, trainer.lastLiveMonIndex() + 1) - 1;
        while ((index >= trainer.getMonCount())
                || (trainer.getMon(index) == null)
                || (trainer.getMon(index).getCurrentHp() <= 0)) {
            ui.display("Invalid selection.\n");
            index = ui.getInt(1, trainer.getMonCount());
        }
        return index;
    }

}
