
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
     *     Switch
     *     Back
     * Items
     * Back
     * 
     */
    @Override
    public TrainerEntity[] processState() {
        
        
        
        
        TrainerEntity[] output = new TrainerEntity[1];
        output[0] = player;
        return output;
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
