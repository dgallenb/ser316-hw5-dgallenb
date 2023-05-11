
public class StatusMove extends Move {
    Codemon mon;
    int[] statChanges; // HP change is heal/recoil, other stat changes are combat stages
    
    // hp, atk, def, satk, sdef, spd, evade, initiative, accuracy, crit range bonus
    // hp changes should probably not be a thing for mid-battle stuff.
    
    public StatusMove(String name, String description, int db, int ac,
            Frequency frequency, MonType type, Codemon c, int[] statChanges) {
        super(name, description, db, ac, new MoveCategory(2), frequency, type);
        mon = c;
        this.statChanges = new int[Utility.STAT_LIST_LENGTH];
        int size = Math.min(Utility.STAT_LIST_LENGTH, statChanges.length);
        for(int i = 0; i < size; ++i) {
            this.statChanges[i] = statChanges[i];
        }
    }
    
    public int use() throws Exception {
        int output = super.use();
        applyStatChanges();
        
        return output;
        
    }
    
    protected void applyStatChanges() {
        if(statChanges[0] != 0) {
            mon.heal((int) statChanges[0]);
        }
        for(int i = 1; i <= 5; ++i) {
            mon.applyCombatStage(i, statChanges[i]);
        }
        mon.addEvade(statChanges[6]);
        mon.addInitiative(statChanges[7]);
        mon.addAccuracy(statChanges[8]);
        mon.addCritRange(statChanges[9]);
        
    }
}
