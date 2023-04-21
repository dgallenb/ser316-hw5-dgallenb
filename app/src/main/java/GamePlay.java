
public class GamePlay {
    protected GameState menuState;
    protected GameState exploreState;
    protected GameState battleState;
    protected GameState shopState;
    protected GameState state;
    protected UI ui;
    protected Weather weather;
    
    
    public GamePlay(HumanTrainerEntity t) {
        TrainerEntity[] trainers = new TrainerEntity[2];
        trainers[0] = t;
        menuState = new MenuState(trainers, ui);
        exploreState = new ExploreState(trainers, ui, weather);
        battleState = new BattleState(t, t, ui, weather); // dummy constructor, because
        // battle state needs two trainers to enter it.
        shopState = new ShopState(trainers, ui);
    }
    
    

}
