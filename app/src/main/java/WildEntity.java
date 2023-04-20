
public class WildEntity extends TrainerEntity {

    public WildEntity(Trainer t) {
        super(t);
    }

    public int decideSwitch() {
        return 0;
    }
    
    public int decideBeginning() {
        return decideSwitch();
    }

    public int decideAttack() {
        Codemon mon = trainer.getMons()[0];
        int[] movesAvailable = mon.getAvailableMoveIndices();
        if(movesAvailable.length < 1) {
            return -1;
        }
        int index = Utility.d(movesAvailable.length) - 1;

        return index;
    }

    @Override
    public int decideInput(int phase) {
        switch(phase) {
        case 0:
            return decideBeginning();
        case 1:
            return decideAttack();
        case 2: 
            return 0;
        case 3:
            return 0;
        default:
            return 0;
        }
        
    }
     

}
