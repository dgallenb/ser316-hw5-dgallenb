
public class Trainer {
    protected Codemon[] mons;
    protected String name;
    protected int monCount;
    
    public Trainer() {
        mons = new Codemon[6];
        name = "Trainer";
        monCount = 0;
    }
    
    public Trainer(Codemon mon) {
        mons = new Codemon[6];
        name = "Trainer";
        mons[0] = mon;
        monCount = 1;
    }

    public Codemon[] getMons() {
        return mons;
    }

    public void setMons(Codemon[] mons) {
        this.mons = mons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean addMon(Codemon mon) {
        if(monCount >= 6) {
            return false;
        }
        else {
            mons[monCount] = mon;
            ++monCount;
            return true;
        }
    }
    
    public Codemon replaceMon(Codemon mon, int index) {
        if((index < 0) || (index >= mons.length)) {
            return mon;
        }
        else {
            Codemon output = mons[index];
            mons[index] = mon;
            return output;
        }
    }
}
