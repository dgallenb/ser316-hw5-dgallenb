/**
 * I might include dual-type functionality, but we'll see if I have time for that.
 * @author DJ
 *
 */
public class EvolvedCodemon extends Codemon {
    
    protected String addedName;
    
    public EvolvedCodemon(Codemon basemon) {
        this(basemon, new int[] {20, 5, 5, 5});
    }
    
    public EvolvedCodemon(Codemon basemon, int[] statBoosts) {
        this(basemon, new int[] {20, 5, 5, 5}, true);
    }
    
    public EvolvedCodemon(Codemon basemon, int[] statBoosts, boolean addType) {
        this.setHp(basemon.getHp());
        this.setAtk(basemon.getAtk());
        this.setDef(basemon.getDef());
        this.setSpd(basemon.getSpd());
        this.setCurrentHP(basemon.getCurrentHP());
        this.type = basemon.getType();
        bonusStatChance = basemon.getBonusStatChance();
        this.lvl = basemon.getLvl();
        this.exp = basemon.getExp();
        this.evolve = false;
        tempStats = basemon.getTempStats();
        this.moves = basemon.getMoves();
        
        this.name = basemon.getName();
        this.addedName = ""; //
        
        addType();
    }
    
    public String getName() {
        return addedName + " " + this.getName();
    }
    
    public String getDescription() {
        String output = "";
        output += this.getName() + ": a lvl. " + this.getLvl() + " " + 
        this.getType().toString() + " evolved codemon.";
        
        return output;
    }
    
    public void addType(MonType type) {
        if(!this.getType().sameMonType(type)) {
            this.setType(new MonTypeMulti(getType(), type.getTypeNum()));
            
        }
    }
    
    protected void addName(MonType type) {
        if(this.addedName.equals("")) {
            this.addedName = Utility.getTypedName(this.getType());
        }
        else {
            this.addedName = Utility.getTypedName(this.getType()) + " " + 
                    addedName;
        }
    }
    
    
    
    public void addType() {
        int count = 0; 
        for(Move m : getMoves()) {
            if(m != null) {
                ++count;
            }
        }
        int typeIndex = Utility.d(count) - 1;
        if(!getType().sameMonType(getMoves()[typeIndex].getType())) {
            this.setType(new MonTypeMulti(getType(), 
                   moves[typeIndex].getType().getTypeNum()));
        }
        addName(moves[typeIndex].getType());
    }
}
