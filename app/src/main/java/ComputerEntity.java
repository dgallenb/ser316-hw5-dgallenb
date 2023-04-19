
public class ComputerEntity extends TrainerEntity {

    public ComputerEntity(Trainer t) {
        super(t);
        // TODO Auto-generated constructor stub
    }

    /**
     * If the front liner has under half HP, check a random slot.
     * If that slot has more HP, swap. Otherwise, don't bother.
     */
    @Override
    public int decideSwitch() {
        
        if(trainer.countLiveMons() > 1) {
            int curHP = trainer.getMons()[0].getCurrentHP();
            int maxHP = trainer.getMons()[0].getHp();
            if((curHP * 2 ) < maxHP) {
                int index = Utility.d(trainer.countLiveMons()) - 1;
                if(index != 0) {
                    if(trainer.getMons()[index].getCurrentHP() > curHP) {
                        return index;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int decideAttack() {
        int[] moves = trainer.getMons()[0].getAvailableMoveIndices();
        int moveChoice = -1;
        int maxDB = 0;
        for(int i : moves) {
            if(trainer.getMons()[0].predictDB(i) > maxDB) {
                moveChoice = i;
            }
        }
        // TODO Auto-generated method stub
        return moveChoice;
    }

}
