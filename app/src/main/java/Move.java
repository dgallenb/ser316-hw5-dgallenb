


public class Move {
    protected String name;
    protected String description;
    protected int db;
    protected Frequency frequency;
    protected boolean available;
    protected MonType type;
    protected int ac;
    
    public static final Move struggle = new Move(
            "Struggle", "Default move", 4, 4, new Frequency(0), new MonType(0));
    
    public static final Move wait = new Move(
            "Wait", "Default move", 0, 0, new Frequency(0), new MonType(0));
    
    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public Move() {
        
    }
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getFullDesc() {
        return name + ": a " + this.type.toString() + " DB" + db + 
                " move. AC" + ac + ". Usable " + frequency.toString() + ". ";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description.equals("")) {
            this.description = name + ": a DB" + db + " move. AC" + ac + 
                    ". Usable " + frequency.toString() + ". ";
        }
        else {
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
    
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    public boolean equals(Object o) {
        if(o instanceof Move) {
            Move i = (Move) o;
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


