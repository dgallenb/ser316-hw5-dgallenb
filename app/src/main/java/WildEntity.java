
public class WildEntity extends TrainerEntity {

    public WildEntity(Trainer t) {
        super(t);
        // assume the trainer has a pokemon set to it
        if(this.trainer.getMons().length > 0) {
            this.trainer.setName("Wild " + getFrontMon().getName());
        }
        
    }

    public int decideSwitch() {
        return 0;
    }
    
    public int decideBeginning() {
        return decideSwitch();
    }

    @Override
    public int decideInput(int phase) {
        switch(phase) {
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

    @Override
    public int decideBattle() {
        Codemon mon = trainer.getMons()[0];
        int[] movesAvailable = mon.getAvailableMoveIndices();
        if(movesAvailable.length < 1) {
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
