


public class Move {
    protected String name;
    protected String description;
    protected int db;
    protected Frequency frequency;
    protected boolean available;
    protected MonType type;
    protected int ac;
    
    public static final Move struggle = new Move(
            "Struggle", "Default move", 4, new Frequency(0), new MonType(0));
    
    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public Move() {
    
    }
    
    public Move(String name, String description, int db, 
            Frequency frequency, MonType type) {
        this.name = name;
        this.description = description;
        this.db = db;
        this.frequency = frequency.copy();
        this.available = true;
        this.type = type.copy();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    /**
     * Warning: unlike the constructor, this will not make a copy of the frequency given
     * @param frequency
     */
    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }
    
    public MonType getType() {
        return this.type;
    }
    
    /**
     * Warning: unlike the constructor, this will not make a copy of the type given
     * @param t a MonType to set the move to.
     */
    public void setType(MonType t) {
        this.type = t;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public int use() throws Exception {
        if(!available) {
            throw new Exception("Move Unavailable");
        }
        if(frequency.getTypeNum() != 0) {
            available = false;
        }
        return db;
    }
    
    public boolean refresh() {
        this.available = true;
        return true;
    }
    
    public boolean refreshScene() {
        if(frequency.getTypeNum() != 3) {
            this.available = true;
            return true;
        }
        return false;
    }
}


