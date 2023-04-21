
public interface GameState {
    public TrainerEntity[] processState(TrainerEntity[] trainers);
    public int nextState();
}
