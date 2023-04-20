
public class GamePlay {
    protected GameState menuState;
    protected GameState exploreState;
    protected GameState battleState;
    protected GameState townState;
    protected GameState state;
    protected UI ui;
    protected Weather weather;
    
    
    public GamePlay(HumanTrainerEntity t) {
        menuState = new MenuState(t, ui);
        exploreState = new ExploreState(t, ui);
        battleState = new BattleState(t, t, ui, weather); // dummy constructor, because
        // battle state needs two trainers to enter it.
    }
    
    

}
