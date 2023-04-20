
public class BattleState implements GameState {

    @Override
    public void process() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String promptUser() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void receiveInput(String input) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Steps of a turn:
     * 1. Trainers select moves. Player gets move selection via UI, Trainer-descendent
     * will determine behavior for wild codemons and AI trainer codemons.
     * 2. Determine the faster mon.
     * 3. Faster mon attacks, deals damage.
     * 4. Check if slower mon is ded. If yes, skip to end of turn.
     * 5. Slower mon attacks, deals damage.
     * 6. Check if faster mon is ded. If yes,
     * 7. End of turn checks. If a trainer is out of mons, the trainer loses.
     * 7.1. If a trainer has a ded mon, they choose a new mon to send out.
     * @param player1
     * @param player2
     */
    
    private String handleTurn(TrainerEntity player1, TrainerEntity player2) {
        return "";
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
                    boolean crit = Math.random() > 0.95;
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
    
    /**
     * 1. Check if the trainers want to switch mons. 
     * 2. If yes, switch the mons around. 
     * @param t1
     * @param t2
     * @return codes to be determined.
     */
    private int[] switchPhase(TrainerEntity t1, TrainerEntity t2) {
        int switch1 = t1.decideSwitch();
        int switch2 = t2.decideSwitch();
        
        t1.getTrainer().switchMons(0, switch1);
        t2.getTrainer().switchMons(0, switch2);
        return new int[] {switch1, switch2};
    }
    
    private int[] itemPhase(TrainerEntity t1, TrainerEntity t2) {
        int item1 = t1.decideItem();
        int item2 = t2.decideItem();
        
        if((item1 >= 0) && (item1 < t1.getTrainer().countItems())) {
            t1.getTrainer().getItem(item1).use(t1.getTrainer().getMons()[0]);
        }
        
        if((item2 >= 0) && (item2 < t2.getTrainer().countItems())) {
            t2.getTrainer().getItem(item2).use(t2.getTrainer().getMons()[0]);
        }
        
        return new int[] {item1, item2};
    }
    
    
    
}
