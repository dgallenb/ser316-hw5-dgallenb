
public class StatusMove extends Move {
    protected Codemon mon;
    protected int[] statChange;
    
    public StatusMove(String name, String description, int db, int ac,
            Frequency frequency, MonType type, Codemon mon) {
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

    
}
