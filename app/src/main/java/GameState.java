
public interface GameState {
    public TrainerEntity[] processState();
    public void moveState();
    public String displayMenu();
    public void executeMenuOption(int option);
    
}
