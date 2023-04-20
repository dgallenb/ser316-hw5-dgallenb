import java.util.ArrayList;

public class BattlePhase implements AbstractPhase {
    protected TrainerEntity[] trainers; // assumed to be a human player
    protected UI ui;
    protected int nextPhase;
    protected ArrayList<Acquirable> acquired;
    
    public BattlePhase(TrainerEntity t1, TrainerEntity t2, UI ui) {
        this.trainers = new TrainerEntity[2];
        this.trainers[0] = t1;
        this.trainers[1] = t2;
        this.ui = ui;
        this.nextPhase = 1;
        acquired = new ArrayList<Acquirable>();
    }
    
    public BattlePhase(TrainerEntity t1, TrainerEntity t2, UI ui, 
            ArrayList<Acquirable>a) {
        this.trainers = new TrainerEntity[2];
        this.trainers[0] = t1;
        this.trainers[1] = t2;
        this.ui = ui;
        this.nextPhase = 1;
        acquired = a;
    }

    /**
     * Gameplan: ask the users for a move to use, facepunch, move to either cleanup
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
     * 0: battle continues
     * 1: P1 has a KO'd mon, call out another mon
     * 2: P2 has a KO'd mon, call out another mon
     * 3: P1 has no mons left, end battle
     * 4: P2 has no mons left, end battle
     * @param playerMove
     * @param opponentMove
     * @return
     */
    private int conductFacepunch(BattleMove playerMove, BattleMove opponentMove) {
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
        for(int i = 0; i < 1; ++i) {
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
            
            if(roll >= toHit) {
                if(move.getDb() < 1) { // status move, does no damage. Status moves
                    // handle stat changes themselves, no need to do anything.
                    
                }
                else {
                    boolean crit = (Utility.d(20) + 
                            battleMoves[i].getMon().getTempStats()[7]) >= 20;
                    int damageReceived = battleMoves[1 - i].getMon().
                            receiveDamage(damageSent, atkType, crit);
                    battleMoves[1 - i].getMon().takeDamage(damageReceived);
                    
                    // Dead check
                    if(battleMoves[1 - i].getMon().getCurrentHP() <= 0) {
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
                        // does the trainer have more mons?
                        // If yes, prompt player to switch in a mon.
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int queryUser() {
        int[] choices = new int[trainers.length];
        for(int i = 0; i < trainers.length; ++i) {
            choices[i] = trainers[i].decideInput(0);
        }   
    
        for(int i = 0; i < choices.length; ++i) {
            handleUserInput(trainers[i], choices[i]);
        }
   
        return 0;
    }
    
    public void handleUserInput(TrainerEntity t, int choice) {
        
    }

    @Override
    public AbstractPhase nextPhase(ArrayList<Acquirable> a) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void displayPrePhaseDialogue() {
        String s = "";
        Codemon firstMon = trainers[0].getFrontMon();
        int[] moveIndices = firstMon.getAvailableMoveIndices();
        
        if(moveIndices.length > 0) {
            s += "Move choices: \n";
            for(int i = 0; i < moveIndices.length; ++i) {
                s += "" + (i + 1) + ". " + firstMon.getMove(moveIndices[i]).getName() + "\n";
            }
            
        } 
        ui.display(s);
    }

}
