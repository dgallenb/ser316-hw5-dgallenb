
public class GamePlay {
    protected TrainerEntity[] trainers;
    protected GameState baseState;
    protected GameState menuState;
    protected GameState exploreState;
    protected GameState battleState;
    protected GameState shopState;
    
    protected GameState state;
    protected UI ui;
    protected Weather weather;
    
    
    public GamePlay(HumanTrainerEntity t) {
        trainers = new TrainerEntity[2];
        trainers[0] = t;
        baseState = new BaseState(trainers, ui);
        menuState = new MenuState(trainers, ui, weather);
        exploreState = new ExploreState(trainers, ui, weather);
        battleState = new BattleState(t, t, ui, weather); // dummy constructor, because
        // battle state needs two trainers to enter it.
        shopState = new ShopState(trainers, ui, weather);
        state = baseState;
    }
    
    public void play() {
        while(true) {
            
            trainers = state.processState();
            
            transition();          
        }
        
    }
    
    public void transition() {
        int nextState = state.nextState();
        switch(nextState) {
        case 0:
            state = baseState;
            break;
        case 1:
            state = menuState;
            break;
        case 2: 
            state = exploreState;
            break;
        case 3: 
            state = battleState;
            break;
        case 4:
            state = shopState;
        }
    }
    
    

}
