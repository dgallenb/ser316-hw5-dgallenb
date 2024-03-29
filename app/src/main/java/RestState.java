import java.util.ArrayList;

public class RestState implements GameState {
    protected ArrayList<TrainerEntity> trainers;
    protected UI ui;
    protected int nextState;
    protected Weather weather;
    
    /**
     * The base constructor for the rest state.
     * @param trainers The current list of trainers to track (should be only 1).
     * @param ui The UI to use to get input and send output.
     * @param weather The current weather in the game.
     */
    public RestState(ArrayList<TrainerEntity> trainers,  UI ui, Weather weather) {
        this.trainers = trainers;
        this.weather = weather;
        this.ui = ui;
        this.nextState = 0;
    }

    @Override
    public int processState() {
        rest();
        weather.advanceTime();
        return nextState;
    }
    
    /**
     * Heals the player's codemons and displays an ominous message.
     */
    public void rest() {
        trainers.get(0).getTrainer().healAll();
        ui.display("Your codemons returned to full health...but at what cost?");
    }

}
