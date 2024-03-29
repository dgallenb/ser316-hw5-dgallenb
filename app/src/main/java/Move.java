/**
 * The encapsulation of a move that a codemon can use in battle.
 * @author DJ
 *
 */
public class Move {
    protected String name;
    protected String description;
    protected int db;
    protected Frequency frequency;
    protected boolean available;
    protected MonType type;
    protected int ac;
    protected int id;
    
    public static final Move struggle = new Move(
            "Struggle", "Default move", 4, 4, new Frequency(0), new MonType(0));
    
    public static final Move wait = new Move(
            "Wait", "Default move", 0, 0, new Frequency(0), new MonType(0));

    /**
     * Default constructor. Makes a copy of the move Struggle.
     */
    public Move() {
        name = "Struggle";
        description = "Default move";
        db = 4;
        ac = 4;
        frequency = new Frequency(0);
        type = new MonType(0);
        available = true;
        id = name.hashCode() + 100 * db + 1000 * ac
                + 10 * frequency.getTypeNum() + type.getTypeNum();
    }
    
    /**
     * Constructor for a codemon's move.
     * @param name The name of the move.
     * @param description The description of the move.
     * @param db The damage base of the move.
     * @param ac The AC roll that the codemon needs to match to hit with the move.
     * @param frequency How often the move may be used.
     * @param type The codemon type of the move.
     */
    public Move(String name, String description, int db, int ac,
            Frequency frequency, MonType type) {
        this.name = name;
        this.description = description;
        this.db = db;
        this.ac = ac;
        this.frequency = frequency.copy();
        this.available = true;
        this.type = type.copy();
    }
    
    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getFullDesc() {
        return name + ": a " + this.type.toString() + " DB" + db
                + " move. AC" + ac + ". Usable " + frequency.toString() + ". ";
    }

    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the move.
     * @param description The new description to use. Has a default if "" is given.
     */
    public void setDescription(String description) {
        if (description.equals("")) {
            this.description = name + ": a DB" + db + " move. AC" + ac
                    + ". Usable " + frequency.toString() + ". ";
        } else {
            this.description = description;
        }
        
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public Frequency getFrequency() {
        return frequency;
    }
    
    public MonType getType() {
        return this.type;
    }

    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the move's availability.
     * @param available True if the move should be made available, false otherwise.
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    /**
     * Uses the move if it's available to be used.
     * @return The damage base of the move.
     * @throws Exception Thrown if the move is unavailable.
     */
    public int use() throws Exception {
        if (!available) {
            throw new Exception("Move Unavailable");
        }
        if (frequency.getTypeNum() != 0) {
            available = false;
        }
        return db;
    }
    
    /**
     * Refreshes the move regardless of frequency.
     * @return
     */
    public boolean refresh() {
        this.available = true;
        return true;
    }
    
    /**
     * Refreshes all moves with scene or every-other-turn frequency.
     * @return True if the move was able to be refreshed, false otherwise.
     */
    public boolean refreshScene() {
        if (frequency.getTypeNum() != 3) {
            this.available = true;
            return true;
        }
        return false;
    }
    
    /**
     * Refreshes all moves with every-other-turn frequency.
     * @return True if the move was able to be refreshed, false otherwise.
     */
    public boolean refreshEot() {
        if (frequency.getTypeNum() == 2) {
            this.available = true;
            return true;
        }
        return false;
    }
    
    public int hashCode() {
        //assert false : "hashCode not designed";
        return id;
    }
    
    /**
     * Generic equality check. Uses the internal ID generated based on the move's attributes.
     */
    public boolean equals(Object o) {
        if (o instanceof Move) {
            if (this.hashCode() == o.hashCode()) {
                return true;
            }
        }
        return false;
    }
}


