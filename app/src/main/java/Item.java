
public class Item extends Acquirable {
    protected int quantity;
    protected String name;
    protected String description;
    
    
    public Item() {
        this.quantity = 1;
        this.name = "Mysterious Berry";
        this.description = "It probably does something. Who knows?";
    }
    
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

    public boolean use(Codemon c) {
        if(quantity < 1) {
            return false;
        }
        switch(this.name) {
        case "Potion":
            if(c.getCurrentHP() >= c.getHp()) {
                return false;
            }
            c.heal(20);
            return true;
        case "Mysterious Berry":
            if(c.getCurrentHP() >= c.getHp()) {
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
        default:
            return false;
        }
    }
    
    public boolean consume() {
        quantity--;
        
        return false;
    }
    
    public String toString() {
        return this.getName() + " (" + this.getQuantity() + "): " + this.getDescription();
    }
}
