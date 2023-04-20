
public class HumanTrainerEntity extends TrainerEntity {
    protected UI ui;

    public HumanTrainerEntity(Trainer t) {
        super(t);
        ui = new TextUI();
    }

    @Override
    public int decideBeginning() {
        int input = ui.getInt(0, 11);
        // TODO Auto-generated method stub
        return input;
    }

    @Override
    public int decideInput(int phase) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int decideBattle() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int decideEnd() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int decideCleanup() {
        // TODO Auto-generated method stub
        return 0;
    }

}
