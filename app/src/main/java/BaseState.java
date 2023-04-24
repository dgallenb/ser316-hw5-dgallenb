import java.util.ArrayList;
/**
 * The starting state that gets looped back to.
 * @author DJ
 *
 */

public class BaseState implements GameState {
    //protected ArrayList<TrainerEntity> trainers;
    protected UI ui;
    protected int nextState;
    
    /**
     * Constructor.
     * @param ui The UI to use for querying info and displaying it.
     */
    public BaseState(UI ui) {
        this.ui = ui;
        nextState = 1;
    }
    
    /**
     * The constructor for the base state.
     * @param trainers The current list of trainers to track (should be only 1).
     * @param ui The UI to use to get input and send output.
     */
    public BaseState(ArrayList<TrainerEntity> trainers, UI ui) {
        //this.trainers = trainers;
        this.ui = ui;
        nextState = 1;
        
    }
    
    @Override
    public int processState() {
        baseMenu();
        return nextState;
    }

    /**
     * Handles the base menu.
     */
    public void baseMenu() {
        String s = "";
        s += "Choose an option:\n" + "1. Explore\n" + "2. Shop\n" + "3. Menu\n";
        s += "4. Rest\n";
        ui.display(s);
        nextState = ui.getInt(1, 4);
    }

}
