/**
 * Encapsulation of an item in the game.
 * @author DJ
 *
 */
public class Item extends Acquirable {
    protected int quantity;
    protected String name;
    protected String description;
    
    
    /**
     * Generic constructor. Makes a Mysterious Berry.
     */
    public Item() {
        this.quantity = 1;
        this.name = "Mysterious Berry";
        this.description = "It probably does something. Who knows?";
    }
    
    /**
     * Constructor.
     * @param name The item's name.
     * @param description The item's description.
     * @param quantity How many of the item to start in the pile.
     */
    public Item(String name, String description, int quantity) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void addQuantity(int quantityAdded) {
        this.quantity += quantityAdded;
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
    
    public String getFullDesc() {
        return name + " (" + quantity + "): " + description;
    }

    /** 
     * Uses the item on the codemon, if possible.
     * @param c The codemon to use the item on.
     * @return True if the item was used, false otherwise.
     */
    public boolean use(Codemon c) {
        if (quantity < 1) {
            return false;
        }
        switch (this.name) {
            case "Potion":
                if (c.getCurrentHp() >= c.getHp()) {
                    return false;
                }
                c.heal(20);
                return true;
            case "Mysterious Berry":
                if (c.getCurrentHp() >= c.getHp()) {
                    return false;
                }
                c.heal(100);
                return true;
            case "XAttack":
                c.applyCombatStage(1);
                return true;
            case "XDefend":
                c.applyCombatStage(2);
                return true;
            case "XSpeed":
                c.applyCombatStage(3);
                return true; 
            case "Move Stone":
                Move m = MoveFactory.getInstance().generateMove(1, c.getType().getTypeNum());
                return c.addMove(m);
            case "Mighty Move Stone":
                m = MoveFactory.getInstance().generateMove(2, c.getType().getTypeNum());
                return c.addMove(m);
            case "Epic Move Stone":
                m = MoveFactory.getInstance().generateMove(3, c.getType().getTypeNum());
                return c.addMove(m);                              
            default:
                return false;
        }
    }
    
    /**
     * Decreases the item's quantity.
     * @return False, always.
     */
    public boolean consume() {
        quantity--;
        
        return false;
    }
    
    /**
     * Converts the representation into a String.
     * @return The String description of an item.
     */
    public String toString() {
        return this.getName() + " (" + this.getQuantity() + "): " + this.getDescription();
    }
    
    /**
     * Returns if the items are the same thing.
     * @param i The item to compare against.
     * @return True if the items are the same, false otherwise.
     */
    public boolean sameItem(Item i) {
        if (i instanceof MoveItem) {
            if (this instanceof MoveItem) {
                return ((MoveItem) this).sameItem((MoveItem) i);
            } else {
                return false;
            }
        } else if (this instanceof MoveItem) {
            return false;
        } else if (i.getName().equals(this.getName())) {
            return true;
        } else {
            return false;
        }
    }
}
