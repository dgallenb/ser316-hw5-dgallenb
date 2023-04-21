
public class ComputerEntity extends TrainerEntity {

    public ComputerEntity(Trainer t) {
        super(t);
    }

    /**
     * If the front liner has under half HP, check a random slot.
     * If that slot has more HP, swap. Otherwise, don't bother.
     */
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
    
    public int decideTraining() {
        return Utility.d(4) - 1;
    }
    
    /** options are: 0. No training. no switching. No item. wild only
     *  1-5. switch with index 1-5. 
     *  6-9. Apply training.
     *  10+. use item.
     * @return decision indicator.
     */
    public int decideBeginning() {
        int switchDecision = decideSwitch();
        if(switchDecision == 0) {
            return decideTraining();
        }
        else {
            return switchDecision;
        }
    }

    @Override
    public int decideInput(int phase) {
        switch(phase) {
        case 0: // beginning phase
            return decideBeginning();  
        }
        return 0;
    }

    @Override
    public int decideBattle() {
        int[] moves = getFrontMon().getAvailableMoveIndices();
        int moveChoice = -1;
        int maxDB = 0;
        for(int i : moves) {
            if(getFrontMon().predictDB(i) > maxDB) {
                moveChoice = i;
            }
        }
        // TODO Auto-generated method stub
        return moveChoice;
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

    @Override
    protected int forceSwitch() {
        int[] liveIndices = new int[trainer.countLiveMons()];
        int count = 0;
        for(int i = 1; i < trainer.getMons().length; ++i) {
            if(trainer.getMons()[i] != null) {
                if(trainer.getMons()[i].getCurrentHP() > 0) {
                    liveIndices[count] = i;
                    ++count;
                }
            }
        }
        
        return liveIndices[Utility.d(liveIndices.length) - 1];
    }

}
