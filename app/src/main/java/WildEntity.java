
public class WildEntity extends TrainerEntity {

    public WildEntity(Trainer t) {
        super(t);
    }

    @Override
    public int decideSwitch() {
        return 0;
    }

    @Override
    public int decideAttack() {
        Codemon mon = trainer.getMons()[0];
        int[] movesAvailable = mon.getAvailableMoveIndices();
        if(movesAvailable.length < 1) {
            return -1;
        }
        int index = Utility.d(movesAvailable.length) - 1;

        return index;
    }
    

}
