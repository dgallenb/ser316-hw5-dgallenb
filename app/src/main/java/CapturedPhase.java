import java.util.ArrayList;

public class CapturedPhase extends CleanupPhase {

    public CapturedPhase(TrainerEntity t1, TrainerEntity t2, UI ui, Weather w) {
        super(t1, t2, ui, w);
    }
    
    public CapturedPhase(TrainerEntity t1, TrainerEntity t2, UI ui, Weather w,
            ArrayList<Acquirable> a) {
        super(t1, t2, ui, w, a);
    }

}
