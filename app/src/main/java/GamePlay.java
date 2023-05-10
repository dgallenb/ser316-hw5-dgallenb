import java.util.ArrayList;

/**
 * The class responsible for playing the actual game.
 * @author DJ
 *
 */
public class GamePlay {
    protected ArrayList<TrainerEntity> trainers;
    protected GameState baseState;
    protected GameState menuState;
    protected GameState exploreState;
    protected GameState battleState;
    protected GameState shopState;
    protected GameState restState;
    
    protected GameState state;
    protected UI ui;
    protected Weather weather;
    
    /**
     * Starts the game with a text UI.
     */
    public GamePlay() {
        this(new TextUI());
    }
    
    /**
     * Starts the game with the specified UI.
     * @param ui The UI to use for all interactions.
     */
    public GamePlay(UI ui) {
        Trainer t = TrainerFactory.getInstance().generateTrainerWithCodemonT1(5);
        t.setName("Player");
        t.setMoney(20000);
        t.addItem(new Item("Capture Stone", "Captures Codemon (probably)", 99));
        HumanTrainerEntity player = new HumanTrainerEntity(t);
        Move m = new Move("Low DB Test", "Test", 1, 2, new Frequency(0), new MonType(0));
        MoveItem mi = new MoveItem("Low DB Item", "Testing", m, 1);
        t.addItem(mi);
        player.setUI(ui);
        this.ui = ui;
        
        trainers = new ArrayList<TrainerEntity>();
        trainers.add(0, player);
        trainers.add(1, null);
        weather = new Weather(0);
        weather.setDay(false);
        baseState = new BaseState(trainers, ui);
        menuState = new MenuState(trainers, ui, weather);
        exploreState = new ExploreState(trainers, ui, weather);
        battleState = new BattleState(trainers, ui, weather); 
        shopState = new ShopState(trainers, ui, weather);
        restState = new RestState(trainers, ui, weather);
        state = baseState;
    }
    
    /**
     * Starts the game with the specified player and UI.
     * @param t The representation of the player, hooked up to a UI.
     * @param ui The UI to use for all other interactions.
     */
    public GamePlay(HumanTrainerEntity t, UI ui) {
        trainers = new ArrayList<TrainerEntity>();
        trainers.add(0, t);
        trainers.add(1, null);
        weather = new Weather(0);
        weather.setDay(false);
        this.ui = ui;
        baseState = new BaseState(trainers, ui);
        menuState = new MenuState(trainers, ui, weather);
        exploreState = new ExploreState(trainers, ui, weather);
        battleState = new BattleState(trainers, ui, weather); 
        shopState = new ShopState(trainers, ui, weather);
        restState = new RestState(trainers, ui, weather);
        state = baseState;
    }
    
    /**
     * Plays the game for predetermined number of state transitions.
     * @param limit The maximum number of transitions to perform.
     */
    public void play(int limit) {
        
        while (limit > 0) {
            int nextState = state.processState();
            
            transition(nextState);    
            --limit;
        }
        ui.display(getFullTrainerInfo());
    }
    
    /**
     * Plays out the game.
     */
    public void play() {
        while (true) {     
            int nextState = state.processState();
            
            transition(nextState);    
        }
    }
    
    /**
     * Retrieves the full info of the player at the end of the game.
     * @return A string representing the player's "progress".
     */
    public String getFullTrainerInfo() {
        Trainer t = trainers.get(0).getTrainer();
        String output = "Trainer: " + t.getName() + ", $" + t.getMoney();
        output += "\n\nItems:\n";
        for (int i = 0; i < t.getItemCount(); ++i) {
            output += t.getItem(i).getFullDesc() + "\n";
        }
        output += "\n\nCodemons:\n";
        for (int i = 0; i < t.getMonCount(); ++i) {
            Codemon c = t.getMon(i);
            
            if (c != null) {
                output += c.getFullDesc();
                output += "\n";
            }
        }
        
        return output;
    }
    
    /**
     * Moves to the next state based on the indicated integer.
     * @param nextState The indicator for the next state to move to.
     */
    public void transition(int nextState) {
        switch (nextState) {
            case 0:
                state = baseState;
                break;
            case 1:
                state = exploreState; 
                break;
            case 2: 
                state = shopState;
                break;
            case 3: 
                state = menuState;
                break;
            case 4:
                state = restState;            
                break;
            case 5:
    
                state = battleState;
                break;
            default:
                state = baseState;
                break;
        }
    }
    
    

}
