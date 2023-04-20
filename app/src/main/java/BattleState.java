
public class BattleState implements GameState {
    
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
    
    
    
    


    @Override
    public void processState() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void moveState() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public String displayMenu() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void executeMenuOption(int option) {
        // TODO Auto-generated method stub
        
    }
    
    
    
}
