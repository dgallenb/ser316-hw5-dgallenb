public abstract interface AbstractMove {

    public int getAc();
    public void setAc(int ac);
    public void setName(String name);
    public String getName();
    public String getFullDesc();
    public String getDescription();
    public void setDescription(String description);
    public int getDb();
    public Frequency getFrequency();
    public MonType getType();
    public boolean isAvailable();
    public void setAvailable(boolean available);
    public int use() throws Exception;
    public boolean refresh(Frequency f);
    public boolean isCrit(int roll);
   
    
    
}