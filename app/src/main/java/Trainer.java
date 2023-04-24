import java.util.ArrayList;

/**
 * The structural component of a trainer, responsible for storing the data associated with it.
 * @author DJ
 *
 */
public class Trainer {
    protected Codemon[] mons;
    protected String name;
    protected int monCount;
    protected int money;
    protected ArrayList<Item> items;
    public static final int MAXCODEMONS = 6;
    
    /**
     * Typical constructor. Does not give the trainer any codemons by default.
     */
    public Trainer() {
        mons = new Codemon[MAXCODEMONS];
        name = "Trainer";
        monCount = 0;
        items = new ArrayList<Item>();
    }
    
    /**
     * Creates the trainer with a starting codemon.
     * @param mon The trainer's starting codemon.
     */
    public Trainer(Codemon mon) {
        mons = new Codemon[6];
        name = "Trainer";
        mons[0] = mon;
        monCount = 1;
        items = new ArrayList<Item>();
    }

    /**
     * Returns the codemon at the specified index.
     * @param index The index to check for the codemon.
     * @return The codemon at that index, or null if nothing is there.
     */
    public Codemon getMon(int index) {
        if ((index >= 0) && (index < monCount)) {
            return mons[index];
        }
        return null;
    }
    
    /*
    public void setMons(Codemon[] mons) {
        this.mons = mons;
    }
    */
    
    /**
     * Counts how many codemons the trainer currently has.
     * @return The number of codemons the trainer possesses.
     */
    public int getMonCount() {
        return monCount;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    
    /**
     * Adds the specified amount of money to the trainer's wallet.
     * @param moneyAdded The amount of money to add.
     */
    public void addMoney(int moneyAdded) {
        this.money += moneyAdded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    /** 
     * Adds an item to the trainer's collection.
     *      Combines identical items into a single stack.
     * @param i The item to add.
     */
    public void addItem(Item i) {
        for (Item item : items) {
            if (item.sameItem(i)) {
                item.addQuantity(i.getQuantity());
                return;
            }
        }
        items.add(i); // only add if there's not already an item with this name
    }
    
    /**
     * Returns the number of items the trainer has.
     * @return The number of unique items the trainer has.
     */
    public int getItemCount() {
        return items.size();
    }
    
    /**
     * Retrieves the nth item from the trainer's collection.
     * @param index The index of the item to retrieve.
     * @return The item if it exists.
     */
    public Item getItem(int index) {
        if ((index >= 0) && (index < items.size())) {
            return items.get(index);
        }
        return null;
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
    
    /**
     * Adds a codemon to the trainer's collection if possible.
     * @param mon The codemon to add.
     * @return True if the codemon could be added, false otherwise.
     */
    public boolean addMon(Codemon mon) {
        if (monCount >= MAXCODEMONS) {
            return false;
        } else {
            mons[monCount] = mon;
            ++monCount;
            //fixMonOrder();
            return true;
        }
    }
    
    /**
     * Replaces the codemon at the specified index with the one given as an argument.
     * @param mon The codemon to keep.
     * @param index The index of the codemon to pitch.
     * @return The codemon the trainer released like Butterfree.
     */
    public Codemon replaceMon(Codemon mon, int index) {
        if ((index < 0) || (index >= mons.length)) {
            return mon;
        } else {
            Codemon output = mons[index];
            mons[index] = mon;
            return output;
        }
    }
    
    /**
     * Returns the number of codemon with currentHp > 0.
     * @return The number of battle-capable codemon.
     */
    public int countLiveMons() {
        int sum = 0;
        for (int i = 0; i < mons.length; ++i) {
            if (mons[i] != null) {
                if (mons[i].getCurrentHp() > 0) {
                    ++sum;
                }
            }
        }
        return sum;
    }
    
    /**
     * Gets the highest index to conceivably check for live codemons.
     * @return The index of the last codemon living in the trainer's collection.
     */
    public int lastLiveMonIndex() {
        for (int i = mons.length - 1; i >= 0; --i) {
            if (mons[i] != null) {
                if (mons[i].getCurrentHp() > 0) {
                    return i;
                }
            }
        }
        return 0;
    }
    
    /**
     * Swaps the positions of the codemon at the specified indices.
     * @param index1 The position of one codemon to be swapped.
     * @param index2 The position of the other codemon to be swapped.
     * @return True if the swap happened, false otherwise.
     */
    public boolean switchMons(int index1, int index2) {
        if ((index1 >= 0) && (index1 < mons.length) 
                && (mons[index1] != null)) {
            if ((index2 >= 0) && (index2 < mons.length)
                    && (mons[index2] != null)) {
                if (index1 != index2) {
                    Codemon temp = mons[index1];
                    mons[index1] = mons[index2];
                    mons[index2] = temp;
                    return true;
                }
                
            }
        }
        return false;
        
    }
    
    /**
     * Heals all codemon the trainer owns to full health.
     */
    public void healAll() {
        for (Codemon mon : mons) {
            if (mon != null) {
                mon.heal();
            }
            
        }
    }
    
    /**
     * Gets the average exp accumulated by the trainer amongst their codemons.
     * @return The average exp accumulated.
     */
    public int getAverageExp() {
        int totalExp = 0;
        int count = 0;
        for (Codemon m : mons) {
            if (m != null) {
                totalExp += m.getExp();
                ++count;
            }
        }
        if (count == 0) {
            return 0;
        }
        return totalExp / count;
    }
    
    /**
     * Reorders mons so that the null slots are all in back.
     */
    private void fixMonOrder() {
        int index1 = 0;
        int index2 = index1;
        while (index1 < mons.length) {
            if (mons[index1] != null) {
                ++index1;
            } else {
                index2 = (index1 + 1);
                while (index2 < mons.length) {
                    if (mons[index2] != null) {
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
