
public class StatusMove extends Move {
    protected Codemon mon;
    protected int[] statChange;
    protected String battleDescription;
    
    public StatusMove(String name, String description, String battleDescription,
            int db, int ac, Frequency frequency, MonType type, Codemon mon) {
        super(name, description, db, ac, frequency, type);
        
    }
    
    public Codemon getMon() {
        return mon;
    }

    public void setMon(Codemon mon) {
        this.mon = mon;
    }

    public int[] getStatChange() {
        return statChange;
    }

    public void setStatChange(int[] statChange) {
        this.statChange = statChange;
    }
    
    public String getBattleDescription() {
        return battleDescription;
    }

    public void setBattleDescription(String battleDescription) {
        this.battleDescription = battleDescription;
    }

    public int use() throws Exception {
        if(!available) {
            throw new Exception("Move Unavailable");
        }
        if(frequency.getTypeNum() != 0) {
            available = false;
        }
        mon.applyStatChange(statChange);
        return db;
    }

    public boolean equals(Object o) {
        if(o instanceof StatusMove) {
            StatusMove i = (StatusMove) o;
            if(i.getDb() == this.getDb()) {
                if(i.getType().getTypeNum() == this.type.getTypeNum()) {
                    if(i.getAc() == this.getAc()) {
                        if(i.getFrequency().getTypeNum() == 
                                frequency.getTypeNum()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
