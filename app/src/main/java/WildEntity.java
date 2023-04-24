/**
 * Wild Entity behavior defined here. Wild codemons always attack, never switch, 
 * and never use training orders. Moves chosen at random.
 * @author DJ
 *
 */
public class WildEntity extends TrainerEntity {

    /**
     * Wild entities need a dummy trainer inside them to function, 
     *      even if they only have one codemon.
     * @param t The trainer containing the single codemon that represents the wild codemon.
     */
    public WildEntity(Trainer t) {
        super(t);
        // assume the trainer has a pokemon set to it
        if (this.trainer.getMonCount() > 0) {
            this.trainer.setName("Wild " + getFrontMon().getName());
        }
        
    }
    
    /**
     * Wild Codemon never switch.
     * @return 0.
     */
    public int decideSwitch() {
        return 0;
    }
    
    public int decideBeginning() {
        return decideSwitch();
    }

    @Override
    public int decideInput(int phase) {
        switch (phase) {
            case 0:
                return decideBeginning();
            case 1:
                return decideBattle();
            case 2: 
                return 0;
            case 3:
                return 0;
            default:
                return 0;
        }
        
    }

    /**
     * The behavior of codemon during the battle phase. Moves chosen at random.
     * @return The index of the move to use.
     */
    @Override
    public int decideBattle() {
        Codemon mon = trainer.getMon(0);
        int[] movesAvailable = mon.getAvailableMoveIndices();
        if (movesAvailable.length < 1) {
            return -1;
        }
        int index = Utility.d(movesAvailable.length) - 1;

        return index;
    }

    /**
     * Not needed because wild codemons don't make decisions here.
     */
    @Override
    public int decideEnd() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Not needed because wild codemons don't make decisions here.
     */
    @Override
    public int decideCleanup() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Not needed because wild codemons don't make decisions here.
     */
    @Override
    protected int forceSwitch() {
        // TODO Auto-generated method stub
        return 0;
    }
     

}
