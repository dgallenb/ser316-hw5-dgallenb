
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
    
    private void handleTurn(Trainer player1, Trainer player2) {
     // determine the faster mon, faster mon attacks. 
        // if the slower mon isn't dead, the slower mon attacks back.
        // 
    }
    
    private void facepunchPhase(BattleMove playerMove, BattleMove opponentMove) {
        BattleMove[] battleMoves = new BattleMove[2];
        int pSpd = playerMove.getMon().getSpd();
        int oSpd = opponentMove.getMon().getSpd();
        if(pSpd == oSpd) {
            double coinFlip = Math.random();
            int pIndex = coinFlip >= 0.5 ? 0 : 1;
            battleMoves[pIndex] = playerMove;
            battleMoves[1 - pIndex] = opponentMove;
        }
        else if(pSpd > oSpd) {
            battleMoves[0] = playerMove;
            battleMoves[1] = opponentMove;
        }
        else { // pSpd < oSpd
            battleMoves[0] = opponentMove;
            battleMoves[1] = playerMove;
        }
        for(int i = 0; i < 1; ++i) {
            int index = battleMoves[i].getMovePosition();
            Weather weather = battleMoves[i].getWeather();
            int damageSent = battleMoves[i].getMon().attack(index, weather);
            Move move = battleMoves[i].getMon().getMove(index);
            if(move == null) {
                continue;
            }
            MonType atkType = battleMoves[i].getMon().getMove(index).getType();
            int damageReceived = battleMoves[1 - i].getMon().
                    receiveDamage(damageSent, atkType);
            battleMoves[1 - i].getMon().takeDamage(damageReceived);
            
            // Dead check
            if(battleMoves[1 - i].getMon().getCurrentHP() <= 0) {
                
            }
        }
    }
    
}
