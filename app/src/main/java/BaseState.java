
public class BaseState implements GameState {
    protected TrainerEntity[] trainers;
    protected UI ui;
    protected int nextState;
    
    public BaseState(TrainerEntity[] trainers, UI ui) {
        this.trainers = trainers;
        this.ui = ui;
        
    }
    
    @Override
    public TrainerEntity[] processState(TrainerEntity[] trainers) {
        baseMenu();
        return trainers;
    }

    @Override
    public int nextState() {
        return nextState;
    }
    
    public void baseMenu() {
        String s = "";
        s += "Choose an option:\n" + "1. Explore\n" + "2. Shop\n" + "3. Menu\n";
        s += "4. Rest\n";
        ui.display(s);
        nextState = ui.getInt(1, 4);
    }

}
