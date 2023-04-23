import java.util.ArrayList;

public interface GameState {
    public ArrayList<TrainerEntity> processState(ArrayList<TrainerEntity> trainers);
    public int nextState();
}
