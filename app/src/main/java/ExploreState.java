import java.util.ArrayList;
/**
 * The state in charge of handling exploration.
 * @author DJ
 *
 */

public class ExploreState implements GameState {
    protected ArrayList<TrainerEntity> trainers;
    protected Weather weather;
    protected UI ui;
    // Unused values in current version
    //protected int forestExplored;
    //protected int mountainExplored;
    //protected int cityExplored;
    protected int nextState;
    
    /**
     * The base constructor for the explore state.
     * @param trainers The current list of trainers to track (should be only 1).
     * @param ui The UI to use to get input and send output.
     * @param weather The current weather in the game.
     */
    public ExploreState(ArrayList<TrainerEntity> trainers,  UI ui, Weather weather) {
        this.trainers = trainers;
        this.weather = weather;
        this.ui = ui;
        //this.forestExplored = 0;
        //this.mountainExplored = 0;
        //this.cityExplored = 0;
        this.nextState = 0;
    }

    @Override
    public int processState() {
        advanceTime();
        String s = "";
        int choice = exploreMenu();
        switch (choice) {
            case 1:
                nextState = 5;
                s += exploreForest();
                break;
            case 2:
                nextState = 5;
                s += exploreMountain();
                break;
            case 3: 
                nextState = 5;
                s += exploreCity();
                break;
            case 4:
                nextState = 0;
                break;
            default:
                nextState = 0;
                break;
        }
        ui.display(s);
        return nextState;
    }
    
    /**
     * Handles the explore menu.
     * @return The integer value of the player's choice.
     */
    public int exploreMenu() {   
        String s = "";
        String dayString = weather.isDay() ? "today" : "tonight";
        s += "The weather " + dayString + " is " + weather.toString() + "\n";
        s += "Choose an area to explore:\n";
        s += "1. Forest\n" + "2. Mountain\n" + "3. City\n" + "4. Back\n";
        ui.display(s);
        return ui.getInt(1, 5);
    }
    
    // Encounter tables {wild mon, trainer }
    
    
    
    /**
     * Handles exploring anywhere, once the location variables are established.
     * @param encounterTable An array of the probabilities of encountering wild mons and
     *      trainers.
     * @param typeTable An array of the probability that a wild mon will be of a given type.
     * @param lvlMod A difficulty modifier to the level of codemons that will be generated.
     * @return Returns a string to display as a result of that exploration.
     */
    public String explore(double[] encounterTable, double[] typeTable, int lvlMod) {
        String s = "";
        int result = Utility.rollOnTable(encounterTable);
        switch (result) {
            case 0:          
                prepWildCodemon(typeTable);
                s += "You found a wild Codemon!";
                break;
            case 1:
                prepTrainer(Utility.getLvlFromExp(
                        trainers.get(0).getTrainer().getAverageExp()) + lvlMod);
                
                s += "You found a trainer ready to battle!";
                break;
            default:
        }
        return s;
    }
    
    /**
     * Handles exploring the forest.
     * @return Returns a string to display as a result of that exploration.
     */
    public String exploreForest() {
        double[] encounterTable = new double[] {0.7, 0.3}; 
        double[] typeTable = new double[] {0.25, 0.15, 0.2, 0.2, 0.08, 0.1, 0.02};
        return explore(encounterTable, typeTable, -2);
    }
    
    /**
     * Handles exploring the mountain.
     * @return Returns a string to display as a result of that exploration.
     */
    public String exploreMountain() {
        double[] encounterTable = new double[] {0.7, 0.3}; 
        double[] typeTable = new double[] {0.25, 0.05, 0.05, 0.1, 0.25, 0.25, 0.05};
        return explore(encounterTable, typeTable, -1);
    }
    
    /**
     * Handles exploring the city.
     * @return Returns a string to display as a result of that exploration.
     */
    public String exploreCity() {
        double[] encounterTable = new double[] {0.2, 0.8}; 
        double[] typeTable = new double[] {0.25, 0.05, 0.05, 0.1, 0.25, 0.25, 0.05};
        return explore(encounterTable, typeTable, 0);
    }
    
    /** 
     * Creates a wild codemon to battle the player in the next phase.
     * @param targetLvl The level to target when generating codemons.
     */
    public void prepWildCodemon(double[] typeTable) {
        nextState = 5;
        int typeVal = Utility.rollOnTable(typeTable);
        int lvl = Math.max(trainers.get(0).getFrontMon().getLvl() - Utility.d(6), 2);
        int exp = Utility.getExpFromLevel(lvl);
        Codemon mon = CodemonFactory.getInstance().generateCodemonWithT1Moves(typeVal, exp);
        //TrainerEntity player = trainers[0];
        //trainers = new TrainerEntity[2];
        //trainers[0] = player;
        trainers.set(1, new WildEntity(new Trainer(mon)));
    }
    
    /** 
     * Creates a trainer to battle the player in the next phase.
     * @param targetLvl The level to target when generating codemons.
     */
    public void prepTrainer(int targetLvl) {
        nextState = 5;
        Trainer t = TrainerFactory.getInstance().generateTrainerWithCodemonT1(targetLvl);
        //TrainerEntity player = trainers[0];
        //trainers = new TrainerEntity[2];
        //trainers[0] = player;
        trainers.set(1, new ComputerEntity(t));
    }
    
    /**
     * Moves time along. Heals players' mons for a tick of HP, changes the weather,
     *      and refreshes all moves (because right now, daily moves are wonky).
     */
    public void advanceTime() {
        weather.advanceTime();
        for (TrainerEntity t : trainers) {
            if (t != null) {
                for (int i = 0; i < t.getTrainer().getMonCount(); ++i) {
                    Codemon mon = t.getTrainer().getMon(i);
                    if (mon != null) {
                        mon.refresh();
                        mon.healTick();
                    }
                }
            }
        }
    }
}
