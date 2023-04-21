
public class ExploreState implements GameState {
    protected TrainerEntity[] trainers;
    protected Weather weather;
    protected UI ui;
    protected int forestExplored;
    protected int mountainExplored;
    protected int cityExplored;
    protected TrainerEntity newFoe;
    protected int nextState;
    
    public ExploreState(TrainerEntity[] trainers,  UI ui, Weather weather) {
        this.trainers = trainers;
        this.weather = weather;
        this.ui = ui;
        this.forestExplored = 0;
        this.mountainExplored = 0;
        this.cityExplored = 0;
        this.nextState = 0;
    }

    @Override
    public TrainerEntity[] processState(TrainerEntity[] trainers) {
        this.trainers = trainers;
        advanceTime();
        String s = "";
        int choice = exploreMenu();
        switch(choice) {
        case 1:
            s += exploreForest();
            break;
        case 2:
            s += exploreMountain();
            break;
        case 3: 
            s += exploreCity();
            break;
        case 4:
            rest();
            break;
        case 5: 
            break;
        }
        ui.display(s);
        return this.trainers;
    }
    
    public void rest() {
        trainers[0].getTrainer().healAll();
        ui.display("Your codemons returned to full health...but at what cost?");
    }
    
    public int exploreMenu() {   
        String s = "";
        String dayString = weather.isDay() ? "today" : "tonight";
        s += "The weather " + dayString + "  is " + weather.toString() + "\n";
        s += "Choose an area to explore:\n";
        s += "1. Forest\n" + "2. Mountain\n" + "3. City\n" + "4. Rest\n" + "5. Back\n";
        ui.display(s);
        return ui.getInt(1, 5);
    }
    
    // Encounter tables {wild mon, trainer }
    
    public String exploreForest() {
        double[] encounterTable = new double[] {0.7, 0.3}; 
        double[] typeTable = new double[] {0.25, 0.15, 0.2, 0.2, 0.08, 0.1, 0.02};
        return explore(encounterTable, typeTable, -2);
    }
    
    public String explore(double[] encounterTable, double[] typeTable, int lvlMod) {
        String s = "";
        int result = Utility.rollOnTable(encounterTable);
        switch(result) {
        case 0:          
            prepWildCodemon(typeTable);
            s += "You found a wild Codemon!";
            break;
        case 1:
            prepTrainer(trainers[0].getFrontMon().getLvl() + lvlMod);
            
            s += "You found a trainer ready to battle!";
            break;
        default:
        }
        return s;
    }
    
    public String exploreMountain() {
        double[] encounterTable = new double[] {0.7, 0.3}; 
        double[] typeTable = new double[] {0.25, 0.05, 0.05, 0.1, 0.25, 0.25, 0.05};
        return explore(encounterTable, typeTable, -1);
    }
    
    public String exploreCity() {
        double[] encounterTable = new double[] {0.2, 0.8}; 
        double[] typeTable = new double[] {0.25, 0.05, 0.05, 0.1, 0.25, 0.25, 0.05};
        return explore(encounterTable, typeTable, 0);
    }
    
    private void prepWildCodemon(double[] typeTable) {
        nextState = 4;
        int typeVal = Utility.rollOnTable(typeTable);
        int lvl = Math.max(trainers[0].getFrontMon().getLvl() - Utility.d(6), 2);
        int exp = Utility.getExpFromLevel(lvl);
        Codemon mon = CodemonFactory.getInstance().generateCodemonWithT1Moves(typeVal, exp);
        TrainerEntity player = trainers[0];
        trainers = new TrainerEntity[2];
        trainers[0] = player;
        trainers[1] = new WildEntity(new Trainer(mon));
    }
    
    private void prepTrainer(int targetLvl) {
        nextState = 4;
        Trainer t = TrainerFactory.getInstance().generateTrainerWithCodemonT1(targetLvl);
        TrainerEntity player = trainers[0];
        trainers = new TrainerEntity[2];
        trainers[0] = player;
        trainers[1] = new ComputerEntity(t);
    }
    
    public void advanceTime() {
        if(weather.isDay()) {
            weather.setDay(false);
            weather.transition();
        }
        else {
            weather.setDay(true);
            for(TrainerEntity t : trainers) {
                if(t != null) {
                    for(Codemon mon : t.getTrainer().getMons()) {
                        if(mon != null) {
                            mon.refresh();
                            mon.healTick();
                        }
                        
                    }
                }
            }
            weather.transition();
        }
    }

    @Override
    public int nextState() {
        // TODO Auto-generated method stub
        return nextState;
    }

}
