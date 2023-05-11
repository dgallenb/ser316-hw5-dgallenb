

public class Codemon extends Acquirable {
    protected Move[] moves;
    protected MonType type;
    protected String name;
    
    protected CodemonStats stats;

    protected int exp;
    protected int lvl;
    //protected boolean evolve;
    protected double[] bonusStatChance;
    
    public static final int MAXMOVES = 6;
    
    /**
     * Basic constructor.
     */
    public Codemon() {
        moves = new Move[MAXMOVES];
        this.name = "Default";

        this.type = new MonType(0);
        bonusStatChance = new double[] {0, 0, 0, 0};
        this.lvl = 1;
        this.exp = 0;
        //this.evolve = false;
        stats = new CodemonStats(12, 5, 5, 5, 5, 5, this.getLvl());
    }

    /**
     * Constructor. 
     * @param type The codemon's type.
     * @param hp The codemon's HP.
     * @param atk The codemon's attack.
     * @param def The codemon's defense.
     * @param spd The codemon's speed.
     * @param moves The codemon's starting moves.
     * @param exp The codemon's starting exp.
     */
    public Codemon(MonType type, int hp, int atk, int def, int satk, int sdef, int spd, 
            Move[] moves, int exp) {
        
        this.setType(type);
        this.setExp(exp);
        this.lvl = 1;
        this.moves = new Move[MAXMOVES];
        
        for (int i = 0; i < moves.length; ++i) {
            this.moves[i] = moves[i];
        }
        stats = new CodemonStats(hp, atk, def, satk, sdef, spd, this.getLvl());
        
        //this.evolve = false;
        bonusStatChance = new double[] {0, 0, 0, 0};
        this.name = Utility.getTypedName(type) + " " + Utility.getTypedName(type);
        this.levelUp();
        
    }
    
    /**
     * Constructor. Makes a copy of the input codemon.
     * @param basemon The codemon to copy.
     */
    public Codemon(Codemon basemon) {
        this.setType(basemon.getType());
        this.setExp(basemon.getExp());
        this.setLvl(basemon.getLvl());
        moves = new Move[MAXMOVES];
        for (int i = 0; i < moves.length; ++i) {
            moves[i] = basemon.getMove(i);
        }
        
        bonusStatChance = new double[] {0, 0, 0, 0};
        stats = basemon.copyCodemonStats();
        //this.evolve = basemon.canEvolve();
        
        for (int i = 0; i < bonusStatChance.length; ++i) {
            this.bonusStatChance[i] = basemon.getBonusStatChance(i);
        }
    }
    
    public CodemonStats copyCodemonStats() {
        return stats.duplicate();
    }
    
    public int getTempStat(int i) {
        return stats.getTempStat(i);
    }

    public void setTempStat(int tempStat, int index) {
        stats.setTempStat(tempStat, index);
    }
    
    /**
     * Gets the move at the specified index. Note: Returns null if there's no move 
     * in that index.
     * @param index The index of the move to retrieve.
     * @return A move if there's a move at that index, null otherwise.
     */
    public Move getMove(int index) {
        if ((index >= 0) && (index < MAXMOVES)) {
            return moves[index];
        }
        return null;
        
    }
    
    /**
     * Counts the number of moves the codemon has.
     * @return The number of moves known.
     */
    public int getMoveCount() {
        int total = 0;
        for (Move m : moves) {
            if (m != null) {
                ++total;
            }
        }
        return total;
    }
    
    /**
     * Attempts to add a move to the mon's movelist.
     * If the mon has 6 known moves already, return false and do not add the move.
     * @param moveToAdd The move to be added.
     * @return true if the move was added, false otherwise.
     */
    public boolean addMove(Move moveToAdd) {
        for (int i = 0; i < moves.length; ++i) {
            if (moves[i] == null) {
                moves[i] = moveToAdd;
                return true;
            }
        }
        return false;
    }
    
    public boolean overrideMove(Move moveToAdd, int index) {
        moves[index] = moveToAdd;
        return true;
    }
    
    /*
    public boolean canEvolve() {
        return evolve;
    }

    public void setEvolve(boolean canEvolve) {
        this.evolve = canEvolve;
    }
    */
    
    public MonType getType() {
        return type;
    }

    public void setType(MonType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
    
    public void addExp(int expToAdd) {
        this.setExp(this.getExp() + expToAdd);
    }
    
    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
    
    /**
     * Checks whether the codemon is ready to level up.
     * @return true if the codemon iready to level up, false otherwise.
     */
    public boolean levelUp() {
        int expectedLevel = Utility.getLvlFromExp(this.getExp());
        if (expectedLevel > this.getLvl()) {
            performLevelUp();
            levelUp();
            return true;
        }
        return false;
    }
    
    /**
     * Ignores whether the mon should level up, and just applies stat changes.
     */
    public void performLevelUp() {
        /*if (lvl % 15 == 0) {
            evolve = true;
        }
        */
        ++lvl;
        stats.levelUp();
    }

    
    public double getBonusStatChance(int index) {
        return bonusStatChance[index];
    }

    public void setBonusStatChance(double bonusStatChance, int index) {
        this.bonusStatChance[index] = bonusStatChance;
    }

    public int getHp() {
        return stats.getMaxHp();
    }

    // TODO check if this is needed
    /*
    public void setHp(int hp) {
        this.hp = hp;
    }
    */

    public int getAtk() {
        return stats.getAtk();
    }
    
    public int getDef() {
        return stats.getDef();
    }

    public int getSatk() {
        return stats.getSatk();
    }
    
    public int getSdef() {
        return stats.getSdef();
    }
    
    /*
    public void setAtk(int atk) {
        this.atk = atk;
    }
    
    public void setSatk(int satk) {
        this.satk = satk;
    }
    
    public void setSdef(int sdef) {
        this.sdef = sdef;
    }
    
    
    
    public void setDef(int def) {
        this.def = def;
    }
    */
    
    public int getSpd() {
        return stats.getSpd();
    }
    
    public int getCurrentAtk() {
        return stats.getCurrentAtk();
    }
    
    public int getCurrentDef() {
        return stats.getCurrentDef();
    }
    
    public int getCurrentSatk() {
        return stats.getCurrentSatk();
    }
    
    public int getCurrentSdef() {
        return stats.getCurrentSdef();
    }
    
    public int getCurrentSpd() {
        return stats.getCurrentSpd();
    }
    
    public int getInitiative() {
        return stats.getInitiative();
    }

    /*
    public void setSpd(int spd) {
        this.spd = spd;
    }
    */

    public int getCurrentHp() {
        return stats.getCurrentHp();
    }

    // TODO check if this is needed
    public void setCurrentHp(int currentHp) {
        stats.setCurrentHp(currentHp);;
    }
    
    
    /**
     * Get current accuracy. Currently affected by Focused training.
     * @return Current accuracy bonus.
     */
    public int getAccuracy() {
        return stats.getAccuracy();
    }
    
    /**
     * Get the current bonus to critical threat range. Brutal Training.
     * @return the bonus to critical threat range.
     */
    public int getCritRange() {
        return stats.getCritRange();
    }
    
    /**
     * Heal the mon by X (or to full, if given no argument).
     * @param healValue The amount to heal by.
     * @return The actual amount healed. No overhealing!
     */
    public int heal(int healValue) {
        if ((healValue + this.getCurrentHp()) > this.getHp()) {
            int healed = this.getHp() - this.getCurrentHp();
            this.setCurrentHp(this.getHp());
            return healed;
        }
        this.setCurrentHp(this.getCurrentHp() + healValue);
        return healValue;
    }
    
    /**
     * Heal the mon to full.
     * @return The actual amount healed. No overhealing!
     */
    public int heal() {
        int healed = this.getHp() - this.getCurrentHp();
        this.setCurrentHp(this.getHp());
        return healed;
    }
    
    /**
     * Heals the pokemon by its tick value (10% of its health).
     * @return The total amount healed.
     */
    public int healTick() {
        return heal(this.getHp() / 10);
    }
    
    /**
     * Computes the total damage that this mon should do when attacking.
     *      Also marks the move as unavailable if the frequency isn't sufficient.
     * @param index Index of the codemon's move.
     * @param weather The current weather.
     * @return Damage to be dealt. 
     */
    public int attack(int index, Weather weather) {
        try {
            int db = moves[index].use();
            int typedDamage = Utility.dbToDamage(computeDb(db, moves[index].getType()));
            return computeDamage(typedDamage) + Utility.weatherDamageBonus(type, weather);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * Computes the total damage that this mon should do when attacking.
     *      Also marks the move as unavailable if the frequency isn't sufficient.
     * @param m The move to use.
     * @param weather The current weather.
     * @return Damage to be dealt. 
     */
    /*public int attack(int index, Weather weather) {
        int index = getIndex(m);
        if (index >= 0) {
            try {
                m.use();
                int db = moves[index].use();
                int typedDamage = Utility.dbToDamage(computeDb(db, moves[index].getType()));
                return computeDamage(typedDamage) + Utility.weatherDamageBonus(type, weather);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return 0;
    }
    */
    /*
    protected boolean hasMove(Move moveToCheck) {
        for (Move m : moves) {
            if (m != null) {
                if (moveToCheck.equals(m)) {
                    return true;
                }
            }
        }
        return false;
    }
    */
    
    /**
     * Gets the index of the provided move if the mon has it.
     * @param moveToCheck The index of the move in question.
     * @return -1 if the mon doesn't have the move, the index if they do.
     */
    protected int getIndex(Move moveToCheck) {
        for (int i = 0; i < moves.length; ++i) {
            if (moves[i] != null) {
                if (moves[i].equals(moveToCheck)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * If the codemon has the same type as the move it is using, +2 DB.
     * @param db The initial damage base of the move.
     * @param moveType The move's codemon type.
     * @return The type-modified damage base.
     */
    public int computeDb(int db, MonType moveType) {
        if (moveType.sameMonType(type)) {
            return db + 2;
        }
        return db;
    }
    
    /**
     * Adds the codemon's attack value to the damage.
     * @param unmoddedDamage The damage before accounting for this mon's attack.
     * @return The damage to be dealt to the defender.
     */
    public int computeDamage(int unmoddedDamage) {
        return unmoddedDamage + getCurrentAtk();
    }
    
    
    // 
    //  
    /**
     * damage - def, then scale for type advantage/disadvantage.
     *      cap at the mon's HP.
     * @param dbDamage The rolled damage to deal to the mon.
     * @param atkType The codemon type of the attacking move.
     * @param crit True if the attack is a critical hit.
     * @return
     */
    public int receiveDamage(int dbDamage, MonType atkType, boolean crit) {
        int damageAfterBlock = dbDamage - getCurrentDef();
        damageAfterBlock *= (crit ? 2 : 1);
        if(damageAfterBlock < 1) {
            damageAfterBlock = 1;
        }
        int typeModDamage = type.getEffectiveDamage(damageAfterBlock, atkType);
        if (typeModDamage > stats.getCurrentHp()) {
            return stats.getCurrentHp();
        }
        
        return typeModDamage;
    }
    
    /** 
     * Refresh the codemon's non-daily moves.
     */
    public void refreshScene() {
        for (int i = 0; i < moves.length; ++i) {
            if (moves[i] != null) {
                moves[i].refresh(new Frequency(2));
            }
            
        }
    }
    
    /**
     * Reset all temporary stats to 0.
     */
    public void resetTempStats() {
        stats.resetTempStats();
    }
    
    /**
     * Refresh all the codemon's moves.
     */
    public void refresh() {
        for (int i = 0; i < moves.length; ++i) {
            if (moves[i] != null) {
                moves[i].refresh(new Frequency(3));
            }
        }
    }
    
    
    /**
     * Reduce the codemon's HP by the specified amount.
     * @param incomingDamage Damage to be taken. This value should be the output of
     *      receiveDamage().
     * @return Current HP after taking damage.
     */
    public int takeDamage(int incomingDamage) {
        setCurrentHp(getCurrentHp() - incomingDamage);
        return getCurrentHp();
    }
    
    /**
     * Gets a basic description of this codemon.
     * @return A string.
     */
    public String getDescription() {
        String output = "";
        output += this.getName() + ": a lvl. " + this.getLvl() + " "
                + this.getType().toString() + " codemon.";
        
        return output;
    }
    
    /**
     * Gets a string of the codemon's stats.
     * @return A string.
     */
    public String getStatDesc() { 
        String output = "";
        output += "HP:  " + this.getCurrentHp() + "/" + this.getHp()
                + "\nATK: " + this.getAtk() + "\nDEF: " + this.getDef()
                + "\nSPD: " + this.getSpd() + "\nEXP: " + this.getExp();
        return output;
    }
    
    /**
     * Gets a description of each of the codemon's moves.
     * @return A string.
     */
    public String getMoveDesc() {
        String output = "";
        for (int i = 0; i < moves.length; ++i) {
            if (moves[i] != null) {
                output += moves[i].getDescription() + "\n";
            }
        }
        return output;
    }
    
    /**
     * Makes a pretty cute description of the codemon.
     * @return The description of the codemon in full.
     */
    public String getFullDesc() {
        return getDescription() + "\n" + getStatDesc() + "\n"
                + getMoveDesc();
    }
    
    /**
     * Returns an array of indices, listing the moves currently available.
     * @return An array of indexes.
     */
    public int[] getAvailableMoveIndices() {
        int sum = 0;
        for (int i = 0; i < moves.length; ++i) {
            if (moves[i] != null) {
                if (moves[i].isAvailable()) {
                    ++sum;
                }
            }
        }
        int[] output = new int[sum];
        int index = 0;
        for (int i = 0; i < moves.length; ++i) {
            if (moves[i] != null) {
                if (moves[i].isAvailable()) {
                    output[index] = i;
                    ++index;
                }
            }
        }
        return output;
    }
    
    /**
     * Predict the damage base of the move at the specified index.
     * @param index The index of the move to check.
     * @return The expected damage base of a move.
     */
    public int predictDb(int index) {
        return computeDb(index, getType());
    }
    
    /**
     * Evolves this codemon and returns the result as a new codemon.
     * @param statBoosts An integer array of bonus stats to apply.
     * @return A new codemon.
     */
    public EvolvedCodemon evolve(int[] statBoosts) {
        return new EvolvedCodemon(this, statBoosts);
    }
    
    /**
     * Evolves this codemon and returns the result as a new codemon.
     * @return A new codemon.
     */
    public EvolvedCodemon evolve() {
        //this.setEvolve(false);
        return new EvolvedCodemon(this);
    }
    
    /**
     * Calculates the mon's evasion (1/5th of their defense or speed, whichever is higher).
     * @return The mon's current evasion.
     */
    public int computeEvade(MoveCategory cat) {
        return stats.computeEvade(cat);
    }
    
    /*
    /**
     * Applies the specified stat changes as temporary changes.
     * @param changes The array of stat changes to make temporarily.
     * @return True if the stat changes were applied, false otherwise.
     */
    /*
    public boolean applyStatChange(int[] changes) {
        if (changes.length != tempStats.length) {
            return false;
        }
        for (int i = 0; i < tempStats.length; ++i) {
            tempStats[i] += changes[i];
        }
        return true;
    }
    */
    
    /**
     * Adds the input value to temporary evasion.
     * @param val The amount to add.
     */
    public void addEvade(int val) {
        stats.addEvade(val);
    }
    
    /**
     * Adds the input value to temporary initiative bonus.
     * @param val The amount to add.
     */
    public void addInitiative(int val) {
        stats.addInitiative(val);
    }
    
    /**
     * Adds the input value to temporary accuracy.
     * @param val The amount to add.
     */
    public void addAccuracy(int val) {
        stats.addAccuracy(val);
    }
    
    /**
     * Adds the input value to temporary crit range.
     * @param val The amount to add.
     */
    public void addCritRange(int val) {
        stats.addCritRange(val);
    }
    
    /**
     * Applies a temporary boost equal to 20% of the base stat value to the stat at the
     *      specified index.
     * @param index The index of the stat to boost.
     * @return true if the stat was increased, false otherwise.
     */
    public boolean applyCombatStage(int index, int stages) {
        return stats.applyCombatStage(index, stages);
    }
    
    /**
     * Tries to capture this codemon.
     * @return true on success, false on failure.
     */
    public boolean attemptCapture() {
        if (this.getCurrentHp() < 1) {
            return false;
        }
        
        int hpLost = this.getHp() - this.getCurrentHp();
        int diceRoll = Utility.d(this.getHp());
        if ((diceRoll + hpLost) >= this.getHp()) {
            return true;
        } else {
            return false;
        }
    }
    
}
