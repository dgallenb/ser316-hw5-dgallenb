
public class Codemon {
    protected Move[] moves;
    protected MonType type;
    protected String name;
    
    protected int hp;
    protected int atk;
    protected int def;
    protected int spd;
    protected int exp;
    protected int lvl;
    
    protected double[] bonusStatChance;

    protected int currentHP;
    
    public Codemon() {
        moves = new Move[6];
        
        this.setHp(50);
        this.setAtk(5);
        this.setDef(5);
        this.setSpd(5);
        this.setCurrentHP(this.getHp());
        this.type = new MonType(0);
        bonusStatChance = new double[] {0, 0, 0, 0};
        this.lvl = 1;
        this.exp = 0;
    }
    
    public Codemon(MonType type, int hp, int atk, int def, int spd, Move[] moves, int exp) {
        this.setHp(hp);
        this.setAtk(atk);
        this.setDef(def);
        this.setSpd(spd);
        this.setCurrentHP(this.getHp());
        this.setType(type);
        this.setExp(exp);
        this.levelUp();
        
        this.moves = moves;
        bonusStatChance = new double[] {0, 0, 0, 0};
    }
    
    public Codemon(Codemon basemon) {
        this.setHp(basemon.getHp());
        this.setAtk(basemon.getAtk());
        this.setDef(basemon.getDef());
        this.setSpd(basemon.getSpd());
        this.setCurrentHP(basemon.getCurrentHP());
        this.setType(basemon.getType());
        this.setExp(basemon.getExp());
        this.setLvl(basemon.getLvl());
        this.moves = basemon.getMoves();
        bonusStatChance = new double[] {0, 0, 0, 0};
        
        double[] baseStatChance = basemon.getBonusStatChance();
        for(int i = 0; i < baseStatChance.length; ++i) {
            this.bonusStatChance[i] = baseStatChance[i];
        }
    }
    
    public Move[] getMoves() {
        return moves;
    }
    
    /**
     * Attempts to add a move to the mon's movelist.
     * If the mon has 6 known moves already, return false and do not add the move.
     * @param moveToAdd
     * @return true if the move was added, false otherwise.
     */
    public boolean addMove(Move moveToAdd) {
        for(int i = 0; i < moves.length; ++i) {
            if(moves[i] == null) {
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
    
    public boolean levelUp() {
        int expectedLevel = Utility.getLvlFromExp(this.getLvl());
        if(expectedLevel > this.getLvl()) {
            performLevelUp();
            levelUp();
            return true;
        }
        return false;
    }
    
    public void performLevelUp() {
        Utility.getLevelUpBonus(type, bonusStatChance);
    }

    public double[] getBonusStatChance() {
        return bonusStatChance;
    }

    public void setBonusStatChance(double[] bonusStatChance) {
        this.bonusStatChance = bonusStatChance;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }
    
    /**
     * Heal the mon by X (or to full, if given no argument).
     * @param healValue
     * @return The actual amount healed. No overhealing!
     */
    public int heal(int healValue) {
        if((healValue + this.getCurrentHP()) > this.getHp()) {
            int healed = this.getHp() - this.getCurrentHP();
            this.setCurrentHP(this.getHp());
            return healed;
        }
        this.setCurrentHP(this.getCurrentHP() + healValue);
        return healValue;
    }
    
    public int heal() {
        int healed = this.getHp() - this.getCurrentHP();
        this.setCurrentHP(this.getHp());
        return healed;
    }
    
    /**
     * 
     * @param index of the codemon's move.
     * @param weather
     * @return Damage to be dealt. 
     */
    public int attack(int index, Weather weather) {
        try {
            int db = moves[index].use();
            int typedDamage = Utility.dbToDamage(computeDB(db, moves[index].getType()));
            return computeDamage(typedDamage) + Utility.weatherDamageBonus(type, weather);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
    
    public int computeDB(int db, MonType moveType) {
        if(moveType.equals(type)) {
            return db + 2;
        }
        return db;
    }
    
    public int computeDamage(int unmoddedDamage) {
        return unmoddedDamage + getAtk();
    }
    
    
    // 
    //  
    /**
     * damage - def, then scale for type advantage/disadvantage.
     * cap at the mon's HP.
     * @param dbDamage
     * @param atkType
     * @return
     */
    public int receiveDamage(int dbDamage, MonType atkType, boolean crit) {
        int damageAfterBlock = dbDamage - getDef();
        damageAfterBlock *= (crit? 2 : 1);
        int typeModDamage = Utility.typeMod(damageAfterBlock, type, atkType);
        if(typeModDamage > currentHP) {
            return currentHP;
        }
        
        return typeModDamage;
    }
    
    public void refreshScene() {
        for(int i = 0; i < moves.length; ++i) {
            moves[i].refreshScene();
        }
    }
    
    public void refresh() {
        for(int i = 0; i < moves.length; ++i) {
            moves[i].refresh();
        }
    }
    
    /**
     * Gets the move at the specified index. Note: Returns null if there's no move 
     * in that index.
     * @param index
     * @return
     */
    public Move getMove(int index) {
        if((index < 0) || (index >= moves.length)){
            return null;
        }
        return moves[index];
    }
    
    /**
     * 
     * @param incomingDamage Damage to be taken. This value should be the output of
     * receiveDamage().
     * @return Current HP after taking damage.
     */
    public int takeDamage(int incomingDamage) {
        setCurrentHP(getCurrentHP() - incomingDamage);
        return getCurrentHP();
    }
    
    public String getDescription() {
        String output = "";
        output += this.getName() + ": a lvl. " + this.getLvl() + " " + 
        this.getType().toString() + "codemon.";
        
        return output;
    }
    
    public String getStatDesc() { 
        String output = "";
        output += "HP:  " + this.getCurrentHP() + "/" + this.getHp() +
                "\nATK: " + this.getAtk() + "\nDEF" + this.getDef() + 
                "\nSPD: " + this.getSpd();
        return output;
    }
    
    public String getMoveDesc() {
        String output = "";
        for(int i = 0; i < moves.length; ++i) {
            if(moves[i] != null) {
                output += moves[i].getDescription() + "\n";
            }
        }
        return output;
    }
    
    public int[] getAvailableMoveIndices() {
        int sum = 0;
        for(int i = 0; i < moves.length; ++i) {
            if(moves[i] != null) {
                if(moves[i].isAvailable()) {
                    ++sum;
                }
            }
        }
        int[] output = new int[sum];
        int index = 0;
        for(int i = 0; i < moves.length; ++i) {
            if(moves[i].isAvailable()) {
                output[index] = i;
                ++index;
            }
        }
        return output;
    }
    
    public int predictDB(int index) {
        return computeDB(index, getType());
    }
    
    public EvolvedCodemon evolve(int[] statBoosts) {
        return new EvolvedCodemon(this, statBoosts);
    }
    
    public int computeEvade() {
        int spdEvade = this.getSpd() / 5;
        int defEvade = this.getSpd() / 5;
        return Math.max(spdEvade, defEvade);
    }
}
