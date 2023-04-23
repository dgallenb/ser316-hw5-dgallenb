import java.util.ArrayList;

public class DeadPhase extends CleanupPhase implements AbstractPhase {

    public DeadPhase(ArrayList<TrainerEntity> trainers, UI ui, Weather w) {
        super(trainers, ui, w);
    }
    
    public DeadPhase(ArrayList<TrainerEntity> trainers, UI ui, Weather w,
            ArrayList<Acquirable> a) {
        super(trainers, ui, w, a);
    }

 

}
