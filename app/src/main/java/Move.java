


public class Move {
    protected String name;
    protected String description;
    protected int db;
    protected Frequency frequency;
    protected boolean available;
    
    public Move() {
    
    }
    
    public Move(String name, String description, int db, Frequency frequency) {
        this.name = name;
        this.description = description;
        this.db = db;
        this.frequency = frequency.copy();
        this.available = true;
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
    
    public void refresh() {
        this.available = true;
    }
}


