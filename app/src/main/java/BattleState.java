
public class BattleState implements GameState {
    protected TrainerEntity[] trainers;
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
    public BattleState(TrainerEntity[] trainers,  UI ui, Weather weather) {
        this.trainers = trainers;
        this.weather = weather;
        this.ui = ui;
        phase = new BeginningPhase(trainers[0], trainers[1], ui, weather);
        this.nextState = 0;
    }
    
    public BattleState(TrainerEntity t1, TrainerEntity t2, UI ui, Weather weather) {
        trainers = new TrainerEntity[2];
        trainers[0] = t1;
        trainers[1] = t2;
        this.weather = weather;
        this.ui = ui;
        phase = new BeginningPhase(trainers[0], trainers[1], ui, weather);
        this.nextState = 0;
    }


    @Override
    public TrainerEntity[] processState(TrainerEntity[] trainers) {
        this.trainers = trainers;
        phase = new BeginningPhase(trainers[0], trainers[1], ui, weather);
        while(true) {
            if(phase instanceof DeadPhase) {
                // Do a thing to denote that the player needs to get yeeted out somewhere safe
                break;
            }
            else if(phase instanceof ReturnPhase) {
                break;
            }
            else if(phase == null) {
                break;
            }
            else {
                phase = phase.performPhase();
            }
        }
        // 
        for(TrainerEntity t : trainers) {
            if(t instanceof HumanTrainerEntity) {
                TrainerEntity[] output = new TrainerEntity[1];
                output[0] = t;
                return output;
            }
        }
        return null;
    }

    @Override
    public int nextState() {
        return nextState;
    }
}
