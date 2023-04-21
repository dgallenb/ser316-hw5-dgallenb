
public class RestState implements GameState {
    protected TrainerEntity[] trainers;
    protected UI ui;
    protected int nextState;
    protected Weather weather;
    
    public RestState(TrainerEntity[] trainers,  UI ui, Weather weather) {
        this.trainers = trainers;
        this.weather = weather;
        this.ui = ui;
        this.nextState = 0;
    }

    @Override
    public TrainerEntity[] processState(TrainerEntity[] trainers) {
        this.trainers = trainers;
        rest();
        weather.advanceTime();
        return this.trainers;
    }

    @Override
    public int nextState() {
        // TODO Auto-generated method stub
        return nextState;
    }
    
    public void rest() {
        trainers[0].getTrainer().healAll();
        ui.display("Your codemons returned to full health...but at what cost?");
    }

}
