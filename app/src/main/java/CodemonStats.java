import java.util.ArrayList;

public class CodemonStats {
    protected int[] baseStats;
    protected int[] addedStats;
    
    protected int currentHp;
    protected int maxHp;
    
    public static final int STAT_LENGTH = 6;
    
    protected int[] tempStats; // holds one-battle-only buffs/debuffs.
    // hp, atk, def, satk, sdef, spd, evade, initiative, accuracy, crit range bonus
    // hp changes should probably not be a thing for mid-battle stuff.
    
    public CodemonStats(int bhp, int batk, int bdef, int bsatk, int bsdef, int bspd, int lvl) {
        baseStats = new int[STAT_LENGTH];
        addedStats = new int[STAT_LENGTH];
        baseStats[0] = bhp;
        baseStats[1] = batk;
        baseStats[2] = bdef;
        baseStats[3] = bsatk;
        baseStats[4] = bsdef;
        baseStats[5] = bspd;
        
        maxHp = 3 * baseStats[0] + lvl + 10;
        currentHp = maxHp;
        
        tempStats = new int[Utility.STAT_LIST_LENGTH];
    }
    
    public CodemonStats(int[] baseStats, int[] curStats) {
        this.baseStats = new int[STAT_LENGTH];
        this.addedStats = new int[STAT_LENGTH];
        
        if((this.baseStats.length == baseStats.length) 
                && (this.addedStats.length == curStats.length)) {
            for(int i = 0; i < STAT_LENGTH; ++i) {
                this.baseStats[i] = baseStats[i];
                this.addedStats[i] = curStats[i];
            }
        }
    }
    
    public int getHp() {
        return baseStats[0] + addedStats[0];
    }

    public int getAtk() {
        return baseStats[1] + addedStats[1];
    }

    public int getDef() {
        return baseStats[2] + addedStats[2];
    }

    public int getSatk() {
        return baseStats[3] + addedStats[3];
    }

    public int getSdef() {
        return baseStats[4] + addedStats[4];
    }

    public int getSpd() {
        return baseStats[5] + addedStats[5];
    }
    
    public int getCurrentAtk() {
        return combatStageMath(this.getAtk(), tempStats[1]);
    }
    
    public int getCurrentDef() {
        return combatStageMath(this.getDef(), tempStats[2]);
    }
    
    public int getCurrentSatk() {
        return combatStageMath(this.getSatk(), tempStats[3]);
    }
    
    public int getCurrentSdef() {
        return combatStageMath(this.getSdef(), tempStats[4]);
    }
    
    public int getCurrentSpd() {
        return combatStageMath(this.getSpd(), tempStats[5]);
    }
    
    public int getInitiative() {
        return this.getCurrentSpd() + tempStats[7];
    }
    
    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }
    
    public CodemonStats duplicate() {
        return new CodemonStats(baseStats, addedStats);
    }
    
    public int getTempStat(int i) {
        return tempStats[i];
    }
    
    public void setTempStat(int tempStat, int index) {
        this.tempStats[index] = tempStat;
    }
    
    /**
     * Get current accuracy. Currently affected by Focused training.
     * @return Current accuracy bonus.
     */
    public int getAccuracy() {
        return tempStats[8];
    }
    
    /**
     * Get the current bonus to critical threat range. Brutal Training.
     * @return the bonus to critical threat range.
     */
    public int getCritRange() {
        return tempStats[9];
    }
    
    /**
     * Calculates the mon's evasion (1/5th of their defense or speed, whichever is higher).
     * @return The mon's current evasion.
     */
    public int computeEvade(MoveCategory cat) {
        int spdEvade = this.getCurrentSpd() / 5;
        int defEvade = 0;
        switch(cat.getTypeNum()) {
            case 0:
                defEvade = this.getCurrentDef() / 5;
                break;
            case 1:
                defEvade = this.getCurrentSdef() / 5;
                break;
            default:
                defEvade = 0;
                break;
        }
        return Math.max(spdEvade, defEvade) + tempStats[6];
    }
    
    /**
     * Reset all temporary stats to 0.
     */
    public void resetTempStats() {
        for (int j = 0; j < tempStats.length; ++j) {
            tempStats[j] = 0;
        }
    }
    
    /**
     * Adds the input value to temporary evasion.
     * @param val The amount to add.
     */
    public void addEvade(int val) {
        tempStats[6] += val;
    }
    
    /**
     * Adds the input value to temporary initiative bonus.
     * @param val The amount to add.
     */
    public void addInitiative(int val) {
        tempStats[7] += val;
    }
    
    /**
     * Adds the input value to temporary accuracy.
     * @param val The amount to add.
     */
    public void addAccuracy(int val) {
        tempStats[8] += val;
    }
    
    /**
     * Adds the input value to temporary crit range.
     * @param val The amount to add.
     */
    public void addCritRange(int val) {
        tempStats[9] += val;
    }
    
    /**
     * Applies a temporary boost equal to 20% of the base stat value to the stat at the
     *      specified index.
     * @param index The index of the stat to boost.
     * @param stages The number of combat stages to apply.
     * @return true if the stat was increased, false otherwise.
     */
    public boolean applyCombatStage(int index, int stages) {
        if((index > 0) && (index < STAT_LENGTH)) {
            tempStats[index] += stages;
            return true;
        }
        return false;
    }
    
    protected int combatStageMath(int stat, int stage) {
        // If add and negative tempStat, +10%
        // If add and positive/zero tempStat, +20%
        // If ~add and negative/zero tempStat, -10%
        // If ~add and positive tempStat, -20%
        if(stage > 0) {
            return stat + Math.max((2 * stage * stat) / 5, 1);
        }
        else if(stage < 0){
            return stat - Math.max((1 * stage * stat) / 5, 0);
        }
        else {
            return stat;
        }
    }
    
    public void levelUp() {
        // figure out what stats can increase.
        ArrayList<Integer> statIndices = new ArrayList<Integer>();
        for(int i = 0; i < STAT_LENGTH; ++i) {
            if(keepsBSO(i)) {
                statIndices.add(i);
            }
        }
        // pick one at random to increase.
        int indexOfStat = statIndices.get(Utility.d(statIndices.size()) - 1);
        addedStats[indexOfStat] += 1;
        
        maxHp += 1 + (indexOfStat == 0 ? 3 : 0);
        
    }
    
    protected boolean keepsBSO(int index) {
        // for a given stat, it keeps BSO iff increasing that stat does not push the stat to 
        // equal or greater than any other stat with a higher BSO value.
        int newStatVal = baseStats[index] + addedStats[index] + 1;
        for(int i = 0; i < STAT_LENGTH; ++i) {
            if(i != index) {
                
                if(baseStats[i] > baseStats[index]) {
                    int iStatVal = baseStats[i] + addedStats[i];
                    if(iStatVal <= newStatVal) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
}
