import java.util.ArrayList;

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
    
    public GamePlay() {
        this(new TextUI());
    }
    
    public GamePlay(UI ui) {
        Trainer t = TrainerFactory.getInstance().generateTrainerWithCodemonT1(5);
        t.setName("Player");
        t.setMoney(20000);
        t.addItem(new Item("Capture Stone", "Captures Codemon (probably)", 99));
        HumanTrainerEntity player = new HumanTrainerEntity(t);
        
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
    
    public void play(int limit) {
        
        while(limit > 0) {
            trainers = state.processState(trainers);
            
            transition();    
            --limit;
        }
        ui.display(getFullTrainerInfo());
    }
    
    public String getFullTrainerInfo() {
        Trainer t = trainers.get(0).getTrainer();
        String output = "Trainer: " + t.getName() + ", $" + t.getMoney();
        output += "\n\nItems:\n";
        for(int i = 0; i < t.getItemCount(); ++i) {
            output += t.getItem(i).getFullDesc() + "\n";
        }
        output += "\n\nCodemons:\n";
        for(Codemon c : t.getMons()) {
            if(c != null) {
                output += c.getFullDesc();
                output += "\n";
            }
        }
        
        return output;
    }
    
    public void play() {
        while(true) {     
            trainers = state.processState(trainers);
            
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
