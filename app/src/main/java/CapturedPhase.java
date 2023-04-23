import java.util.ArrayList;

public class CapturedPhase extends CleanupPhase {

    public CapturedPhase(ArrayList<TrainerEntity> trainers, UI ui, Weather w) {
        super(trainers, ui, w);
    }
    
    public CapturedPhase(ArrayList<TrainerEntity> trainers, UI ui, Weather w,
            ArrayList<Acquirable> a) {
        super(trainers, ui, w, a);
    }

}
