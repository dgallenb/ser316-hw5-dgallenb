
public class MoveCategory {
    protected int typeNum; // 0 for physical, 1 for special, 2 for status
    
    public MoveCategory(int typeNum) {
        this.typeNum = typeNum;
    }

    public int getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(int typeNum) {
        this.typeNum = typeNum;
    }
    
    public String toString() {
        switch(typeNum) {
            case 0:
                return "Physical";
            case 1:
                return "Special";
            case 2:
                return "Status";
            default:
                return "Unknown";
        }
    }
}