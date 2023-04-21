import java.util.ArrayList;

public class BattlePhase implements AbstractPhase {
    protected TrainerEntity[] trainers; // assumed to be a human player
    protected UI ui;
    protected int nextPhase;
    protected ArrayList<Acquirable> acquired;
    protected Weather weather;
    
    public BattlePhase(TrainerEntity t1, TrainerEntity t2, UI ui, Weather w) {
        this.trainers = new TrainerEntity[2];
        this.trainers[0] = t1;
        this.trainers[1] = t2;
        this.ui = ui;
        this.nextPhase = 2;
        this.weather = w;
        acquired = new ArrayList<Acquirable>();
    }
    
    public BattlePhase(TrainerEntity t1, TrainerEntity t2, UI ui, Weather w,
            ArrayList<Acquirable> a) {
        this.trainers = new TrainerEntity[2];
        this.trainers[0] = t1;
        this.trainers[1] = t2;
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
        this.queryUser();
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
            int damageSent = battleMoves[i].getMon().attack(index, weather);
            Move move = battleMoves[i].getMon().getMove(index);
            MonType atkType = new MonType(0);
            if(move == null) {
                move = Move.struggle;
            } 
            else {
                atkType = battleMoves[i].getMon().getMove(index).getType();
            }
            
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

    @Override
    public int queryUser() {
        int[] choices = new int[trainers.length];
        for(int i = 0; i < trainers.length; ++i) {
            choices[i] = trainers[i].decideInput(1);
        }   
        // Note: kludge time. Assume human player is first in decisions in choices,
        // and that there's only one player out of the two.
        BattleMove[] battleMoves = new BattleMove[choices.length];
        for(int i = 0; i < choices.length; ++i) {
            battleMoves[i] = new BattleMove(trainers[i].getFrontMon(),
                    trainers[i].getTrainer(), choices[i],weather);
        }
        String output = this.conductFacepunch(battleMoves[0], battleMoves[1]);
        ui.display(output);
        return 0;
    }

    /**
     * Always transitions to end phase.
     */
    @Override
    public AbstractPhase nextPhase(ArrayList<Acquirable> a) {
        return new EndPhase(trainers[0], trainers[1], ui, weather, acquired);
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

}
