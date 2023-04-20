
public class Money extends Acquirable {
    protected int total;
    
    public Money(int value) {
        this.total = value;
    }
    
    public int getTotal() {
        return this.total;
    }

    public String toString() {
        return "$" + total;
    }
}
