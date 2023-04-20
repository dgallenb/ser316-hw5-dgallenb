
public class Item extends Acquirable {
    protected int quantity;
    protected String name;
    protected String description;
    
    
    public Item() {
        this.quantity = 1;
        this.name = "Mysterious berry";
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
        return true;
    }
    
    public boolean consume() {
        quantity--;
        
        return false;
    }
}
