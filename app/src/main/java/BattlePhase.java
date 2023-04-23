import java.util.ArrayList;
import java.util.Comparator;

public class BattlePhase implements AbstractPhase {
    protected ArrayList<TrainerEntity> trainers; // assumed to be a human player
    protected UI ui;
    protected int nextPhase;
    protected ArrayList<Acquirable> acquired;
    protected Weather weather;
    
    public BattlePhase(ArrayList<TrainerEntity> trainers, UI ui, Weather w) {
        this.trainers = trainers;
        this.ui = ui;
        this.nextPhase = 2;
        this.weather = w;
        acquired = new ArrayList<Acquirable>();
    }
    
    public BattlePhase(ArrayList<TrainerEntity> trainers, UI ui, Weather w,
            ArrayList<Acquirable> a) {
        this.trainers = trainers;
        this.ui = ui;
        this.nextPhase = 2;
        this.weather = w;
        acquired = a;
    }

    /**
     * Gameplan: ask the users for a move to use, facepunch, move to end
     * or end.
     */
    @Override
    public AbstractPhase performPhase() {
        this.displayPrePhaseDialogue();
        //this.queryUser();
        int[] choices = queryUsers();
        String displayText = runThroughInitiative(choices);
        ui.display(displayText);
        return nextPhase(acquired);       
    }
    
    /**
     * status indicators
     * 0: battle continues, move to end phase
     * 1: P1 has no mons left, end battle
     * 4: P2 has no mons left, end battle
     * @param playerMove
     * @param opponentMove
     * @return
     */
    /*
    private String conductFacepunch(BattleMove playerMove, BattleMove opponentMove) {
        String s = "";
        BattleMove[] battleMoves = new BattleMove[2];
        int pInit = playerMove.getMon().getInitiative();
        int oInit = opponentMove.getMon().getInitiative();
        if(pInit == oInit) {
            double coinFlip = Math.random();
            int pIndex = coinFlip >= 0.5 ? 0 : 1;
            battleMoves[pIndex] = playerMove;
            battleMoves[1 - pIndex] = opponentMove;
        }
        else if(pInit > oInit) {
            battleMoves[0] = playerMove;
            battleMoves[1] = opponentMove;
        }
        else { // pInit < oInit
            battleMoves[0] = opponentMove;
            battleMoves[1] = playerMove;
        }
        for(int i = 0; i < battleMoves.length; ++i) {
            int index = battleMoves[i].getMovePosition();
            Weather weather = battleMoves[i].getWeather();
            Move move;
            if(index == -1) {
                move = Move.struggle;
            }
            else if(index == -2) {
                move = Move.wait;
            }
            else {
                move = battleMoves[i].getMon().getMove(index);
            }
            int damageSent = battleMoves[i].getMon().
                    attack(move.getType().getTypeNum(), weather);
            
            MonType atkType = move.getType();
            
            // check if the attack hit.
            int toHit = move.getAc() + battleMoves[1 - i].getMon().computeEvade();
            int roll = Utility.d(20) + battleMoves[i].getMon().getTempStats()[6];
            
            if(battleMoves[i].getTrainer().getName().contains("Wild")) {
                s += "Wild ";
            }
            else {
                s += battleMoves[i].getTrainer().getName() + "'s ";
            }
            s += battleMoves[i].getMon().getName() + " used " + move.getName() + ".\n";
            if(roll >= toHit) {
                if(move.getDb() < 1) { // status move, does no damage. Status moves
                    // handle stat changes themselves, no need to do anything.
                    
                    s +=  "Its stats changed!\n";
                    
                }
                else {
                    boolean crit = (Utility.d(20) + 
                            battleMoves[i].getMon().getTempStats()[7]) >= 20;
                    s += (crit ? "Critical hit! " : "");
                    int damageReceived = battleMoves[1 - i].getMon().
                            receiveDamage(damageSent, atkType, crit);
                    s += "It dealt " + damageReceived + " damage.\n";
                    battleMoves[1 - i].getMon().takeDamage(damageReceived);
                    
                    // Dead check
                    if(battleMoves[1 - i].getMon().getCurrentHP() <= 0) {
                        s += battleMoves[1 - i].getMon().getName() + " fainted!";
                        return s; // dead mon, skip to end phase
                        /*
                        if(battleMoves[1 - i].getTrainer().countLiveMons() > 0) {
                            if(battleMoves[1-i].equals(playerMove)) {
                                return 1;
                            }
                            else { // opponent is out a mon
                                return 2;
                            }
                        }
                        else {
                            if(battleMoves[1-i].equals(playerMove)) {
                                return 3;
                            }
                            else { // opponent is out
                                return 4;
                            }
                        }
                        */
    /*
                        // does the trainer have more mons?
                        // If yes, prompt player to switch in a mon.
                    }
                }
            } 
            else {
                s += " But it missed!\n";
            }
        }
        return s;
    }
    */
    // WARNING: This version does not randomize turn order in event of ties.
    public String runThroughInitiative(int[] choices) {
        String output = "";
        int[] initiativeOrder = getInitiative();
        for(int i = 0; i < initiativeOrder.length; ++i) {
            int tInit = initiativeOrder[i];
            int target = 0;
            if(tInit == 0) {
                target = choosePlayerTarget();
            }
            int moveChoice = choices[tInit];
            
            if(trainers.get(tInit).getFrontMon().getCurrentHP() > 0) {
                if(trainers.get(target).getFrontMon().getCurrentHP() > 0) {
                    output += fight(moveChoice, 
                            trainers.get(tInit), trainers.get(target));
                }
            }
            
        }

        return output;
    }
    
    public String fight(int moveChoice, 
            TrainerEntity attacker, TrainerEntity defender) {
        String output = "";
                
        int index = moveChoice;
        Codemon atkMon = attacker.getFrontMon();
        Codemon defMon = defender.getFrontMon();
        Move move;
        if(index == -1) {
            move = Move.struggle;
        }
        else if(index == -2) {
            move = Move.wait;
        }
        else {
            move = atkMon.getMove(index);
        }
        int damageSent = atkMon.attack(move, weather);
        
        MonType atkType = move.getType();
        
        // check if the attack hit.
        int toHit = move.getAc() + defMon.computeEvade();
        int roll = Utility.d(20) + atkMon.getTempStats()[6];
        
        if(attacker.getTrainer().getName().contains("Wild")) {
            output += "Wild ";
        }
        else {
            output += attacker.getTrainer().getName() + "'s ";
        }
        output += atkMon.getName() + " used " + move.getName() + ".\n";
        if(move.getDb() < 1) { // status move, does no damage. Status moves
            // handle stat changes themselves, no need to do anything.
            if(move instanceof StatusMove) {
                output += ((StatusMove) move).getBattleDescription() + "\n";
            }
            else if(move.equals(Move.wait)) {
                output += "(Alexa, play Furret Walk.)\n";
            }
            else {
                output += "Standing here, \nI realize \nYou are just like me\n" + 
                        "Trying to make history\n" + 
                        "But who's to judge\n" + 
                        "The right from wrong\n" + 
                        "When our guard is down\n" + 
                        "I think we'll both agree\n" + 
                        "That violence breeds violence\n" + 
                        "But in the end it has to be this way\n";
            }
        }
        else if(roll >= toHit) {
            boolean crit = (Utility.d(20) + 
                    atkMon.getTempStats()[7]) >= 20;
            output += (crit ? "Critical hit! " : "");
            int damageReceived = defMon.receiveDamage(damageSent, atkType, crit);
            output += "It dealt " + damageReceived + " damage.\n";
            defMon.takeDamage(damageReceived);
            if(defMon.getCurrentHP() <= 0) {
                output += defMon.getName() + " fainted!";
            }
        } 
        else {
            output += " But it missed!\n";
        }
        return output;
    }
    
    /**
     * Eventually, once multi-trainer battles get added (probably not for this
     * assignment), this will ask the player to choose a target in the pool of 
     * other trainers. For now, the trainer will just always target the one opponent
     * 
     * @return The index of the trainer to attack.
     */
    public int choosePlayerTarget() {
        return 1;
    }

    /**
     * Sorts the indexes of the trainers by their front mon's initiative.
     * @return
     */
    protected int[] getInitiative() {
        int[] output = new int[trainers.size()];
        ArrayList<InitiativePair> initiatives = new ArrayList<InitiativePair>();
        for(int i = 0; i < trainers.size(); ++i) {
            initiatives.add(new InitiativePair(
                    trainers.get(i).getFrontMon().getInitiative(), i));
        }
        Comparator<InitiativePair> initComparator = new Comparator<InitiativePair>() {
            public int compare(InitiativePair o1, InitiativePair o2) {
                return o1.compareTo(o2);
            }
        };
        initiatives.sort(initComparator);
        for(int j = 0; j < initiatives.size(); ++j) {
            output[j] = initiatives.get(j).trainerIndex;
        }
        return output;
    }
    
    @Override
    public int queryUser() {
        int[] choices = new int[trainers.size()];
        for(int i = 0; i < trainers.size(); ++i) {
            choices[i] = trainers.get(i).decideInput(1);
        }   
        // Note: kludge time. Assume human player is first in decisions in choices,
        // and that there's only one player out of the two.
        BattleMove[] battleMoves = new BattleMove[choices.length];
        for(int i = 0; i < choices.length; ++i) {
            battleMoves[i] = new BattleMove(trainers.get(i).getFrontMon(),
                    trainers.get(i).getTrainer(), choices[i], weather);
        }
        //String output = this.conductFacepunch(battleMoves[0], battleMoves[1]);
        String output = "";
        ui.display(output);
        return 0;
    }
    
    protected int[] queryUsers() {
        int[] choices = new int[trainers.size()];
        for(int i = 0; i < trainers.size(); ++i) {
            choices[i] = trainers.get(i).decideInput(1);
        }
        return choices;
    }

    /**
     * Always transitions to end phase.
     */
    @Override
    public AbstractPhase nextPhase(ArrayList<Acquirable> a) {
        return new EndPhase(trainers, ui, weather, acquired);
    }

    /**
     * The only dialogue that should be shown here is conditional on the player making
     * choices.
     */
    @Override
    public void displayPrePhaseDialogue() {
        //String s = "";
        
        //ui.display(s);
    }
    
    public class InitiativePair {
        int initiative;
        int trainerIndex;
        
        public InitiativePair(int init, int index) {
            initiative = init;
            trainerIndex = index;
        }
        
        public int compareTo(InitiativePair p2) {
            if(this.initiative > p2.initiative) {
                return 1;
            }
            else if(this.initiative < p2.initiative) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }

}
