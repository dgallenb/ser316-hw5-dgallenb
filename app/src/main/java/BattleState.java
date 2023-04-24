import java.util.ArrayList;

public class BattleState implements GameState {
    protected ArrayList<TrainerEntity> trainers;
    protected Weather weather;
    protected UI ui;
    protected AbstractPhase phase;
    protected int nextState;
    
    /*
     * Steps of a turn:
     * 1. Trainers select moves. Player gets move selection via UI, Trainer-descendent
     * will determine behavior for wild codemons and AI trainer codemons.
     * 2. Determine the faster mon.
     * 3. Faster mon attacks, deals damage.
     * 4. Check if slower mon is ded. If yes, skip to end of turn.
     * 5. Slower mon attacks, deals damage.
     * 6. Check if faster mon is ded. If yes,
     * 7. End of turn checks. If a trainer is out of mons, the trainer loses.
     * 7.1. If a trainer has a ded mon, they choose a new mon to send out.
     * 
     */
    
    /**
     * The base constructor for the battle state.
     * @param trainers The current list of trainers to track (should be 2).
     * @param ui The UI to use to get input and send output.
     * @param weather The current weather in the game.
     */
    public BattleState(ArrayList<TrainerEntity> trainers,  UI ui, Weather weather) {
        /*
        this.trainers = new ArrayList<TrainerEntity>();
        for(int i = 0; i < trainers.size(); ++i) {
            this.trainers.add(trainers.get(i));
        }
        */
        
        this.trainers = trainers;
        this.weather = weather;
        this.ui = ui;
        phase = new BeginningPhase(trainers, ui, weather);
        this.nextState = 0;
    }

    @Override
    public int processState() {
        phase = new BeginningPhase(trainers, ui, weather);
        while (true) {
            if (phase instanceof DeadPhase) {
                nextState = 4;
                phase = phase.performPhase();
                break;
            } else if (phase instanceof ReturnPhase) {
                nextState = 0;
                phase = phase.performPhase();
                break;
            } else if (phase instanceof CapturedPhase) {
                nextState = 0;
                phase = phase.performPhase();
                break;
            } else if (phase == null) {
                break;
            } else {
                phase = phase.performPhase();
            }
        }
        // 
        /*
        for(TrainerEntity t : trainers) {
            if(t instanceof HumanTrainerEntity) {
                TrainerEntity[] output = new TrainerEntity[2];
                output[0] = t;
                return output;
            }
        }
        return null;
        */
        return nextState;
    }
}
