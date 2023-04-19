import java.util.ArrayList;

public class Trainer {
    protected Codemon[] mons;
    protected String name;
    protected int monCount;
    protected ArrayList<Item> items;
    
    public Trainer() {
        mons = new Codemon[6];
        name = "Trainer";
        monCount = 0;
        items = new ArrayList<Item>();
    }
    
    public Trainer(Codemon mon) {
        mons = new Codemon[6];
        name = "Trainer";
        mons[0] = mon;
        monCount = 1;
        items = new ArrayList<Item>();
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
    
    public Item getItem(int index) {
        return items.get(index);
    }
    
    public boolean removeItem(Item item) {
        return items.remove(item);
    }
    
    public boolean removeItem(int index) {
        Item i = items.remove(index);
        return i != null;
    }
    
    public int countItems() {
        return items.size();
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
    
    /**
     * Returns true if the trainer has at least one codemon with currentHP > 0
     * @return
     */
    public int countLiveMons() {
        int sum = 0;
        for(int i = 0; i < mons.length; ++i) {
            if(mons[i] != null) {
                if(mons[i].getCurrentHP() > 0) {
                    ++sum;
                }
            }
        }
        return sum;
    }
    
    public boolean switchMons(int index1, int index2) {
        if((index1 < 0) || (index1 > mons.length) || (mons[index1] == null)) {
            return false;
        }
        if((index1 < 0) || (index1 > mons.length) || (mons[index1] == null)) {
            return false;
        }
        Codemon temp = mons[index1];
        mons[index1] = mons[index2];
        mons[index2] = temp;
        return true;
    }
    
    /**
     * Reorders mons so that the null slots are all in back
     */
    private void fixMonOrder() {
        int index1 = 0;
        int index2 = index1;
        while(index1 < mons.length) {
            if(mons[index1] != null) {
                ++index1;
            }
            else {
                index2 = (index1 + 1);
                while(index2 < mons.length) {
                    if(mons[index2] != null) {
                        switchMons(index1, index2);
                        index2 = mons.length;
                        ++index1;
                    }
                    ++index2;
                }
            }
        }
    }
    
}
