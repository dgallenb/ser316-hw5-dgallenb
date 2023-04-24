/**
 * I might include dual-type functionality, but we'll see if I have time for that.
 * @author DJ
 *
 */
public class EvolvedCodemon extends Codemon {
    
    protected String addedName;
    
    /**
     * Constructor for making an evolved codemon out of another codemon.
     * @param basemon The base codemon to evolve.
     */
    public EvolvedCodemon(Codemon basemon) {
        this(basemon, new int[] {20, 5, 5, 5});
    }
    
    /**
     * Constructor for making an evolved codemon out of another codemon with 
     *      specific evolution-based boosts to stats.
     * @param basemon The base codemon to evolve.
     * @param statBoosts An array of stat boosts.
     */
    public EvolvedCodemon(Codemon basemon, int[] statBoosts) {
        this(basemon, new int[] {20, 5, 5, 5}, true);
    }
    
    /**
     * Constructor for making an evolved codemon out of another codemon.
     * @param basemon The base codemon to evolve.
     * @param statBoosts An array of stat boosts.
     * @param addType True if the constructor should add a random type to the mon,
     *      chosen from the codemon's moves.
     */
    public EvolvedCodemon(Codemon basemon, int[] statBoosts, boolean addType) {
        this.setHp(basemon.getHp());
        this.setAtk(basemon.getAtk());
        this.setDef(basemon.getDef());
        this.setSpd(basemon.getSpd());
        this.setCurrentHp(basemon.getCurrentHp());
        this.type = basemon.getType();
        for (int i = 0; i < bonusStatChance.length; ++i) {
            this.bonusStatChance[i] = basemon.getBonusStatChance(i);
        }
        this.lvl = basemon.getLvl();
        this.exp = basemon.getExp();
        this.evolve = false;
        this.tempStats = new int[8];
        
        for (int i = 0; i < moves.length; ++i) {
            moves[i] = basemon.getMove(i);
        }
        
        this.name = basemon.getName();
        this.addedName = ""; //
        
        addType();
    }
    
    public String getName() {
        return addedName + " " + this.name;
    }
    
    /**
     * Return a prettified description of the codemon.
     * @return A string representing the new codemon's description.
     */
    public String getDescription() {
        String output = "";
        output += this.getName() + ": a lvl. " + this.getLvl() + " "
                + this.getType().toString() + " evolved codemon.";
        
        return output;
    }
    
    /**
     * Adds the chosen type to this codemon's type, if the codemon doesn't
     *      already have it.
     * @param type The type to add to the codemon.
     */
    public void addType(MonType type) {
        if (!this.getType().sameMonType(type)) {
            this.setType(new MonTypeMulti(getType(), type.getTypeNum()));
            
        }
    }
    
    /**
     * Adds a type from the moves to the codemon's total types.
     */
    public void addType() {
        int count = 0; 
        for (int i = 0; i < MAXMOVES; ++i) {
            if (this.getMove(i) != null) {
                ++count;
            }
        }
        int typeIndex = Utility.d(count) - 1;
        if (!getType().sameMonType(getMove(typeIndex).getType())) {
            this.setType(new MonTypeMulti(getType(), 
                   moves[typeIndex].getType().getTypeNum()));
        }
        addName(moves[typeIndex].getType());
    }
    
    /**
     * Extends the name via decoration of the codemon.
     * @param type The type to add to the codemon.
     */
    protected void addName(MonType type) {
        if (this.addedName.equals("")) {
            this.addedName = Utility.getTypedName(this.getType());
        } else {
            this.addedName = Utility.getTypedName(this.getType()) + " " 
                    + addedName;
        }
    }
    
    
    
}
