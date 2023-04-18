
public class MonType {
    // types: quartz, agate, sapphire, topaz, opal, peridot, onyx
    private int type;
    
    public MonType(int i) {
        setType(i);
    }
    
    public MonType(String s) {
        setType(s);
    }
    
    public void setType(int i) {
        if((i < 0) || (i > 6)) {
            type = 0;
        }
        type = i;
    }
    
    public void setType(String s) {
        switch(s) {
            case "QUARTZ":
            case "quartz":
            case "Quartz":
            case "clear":
            case "CLEAR":
                type = 0;
                break;
            case "CLOUDY":
            case "cloudy":
            case "Agate":
            case "AGATE":
            case "agate":
                type = 1;
                break;
            case "RAINY":
            case "rainy":
            case "Sapphire":
            case "sapphire":
            case "SAPPHIRE":
                type = 2;
                break;
            case "WINDY":
            case "windy":
            case "Topaz":
            case "TOPAZ":
            case "topaz":
                type = 3;
                break;
            case "STORMY":
            case "stormy":
            case "Opal":
            case "opal":
            case "OPAL":
                type = 4;
                break;
            case "SNOWY":
            case "snowy":
            case "Peridot":
            case "PERIDOT":
            case "peridot":
                type = 5;
                break;
            case "ECLIPSE":
            case "eclipse":
            case "Onyx":
            case "ONYX":
            case "onyx":
                type = 6;
                break;
            default:
                type = 0;
        }
    }
    
    public int getTypeNum() {
        return type;
    }
    
    public String toString() {
        switch(type) {
        
        case 0:
            return "QUARTZ";
        case 1:
            return "AGATE";
        case 2: 
            return "SAPPHIRE";
        case 3:
            return "TOPAZ";
        case 4:
            return "OPAL";
        case 5:
            return "PERIDOT";
        case 6:
            return "ONYX";
        default:
            return "QUARTZ";
        }
    }
    
    public MonType copy() {
        return new MonType(type);
    }
    
    public boolean equals(Object o) {
        if(o instanceof MonType) {
            return ((MonType) o).getTypeNum() == type;
        }
        return false;
    }
    
    
    
}
