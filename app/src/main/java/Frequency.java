
public class Frequency {
    // making this class because Eclipse won't recognize an enum. 
    /*
     * enum Frequency {
    AT_WILL, 
    EOT, 
    SCENE, 
    DAILY
}
     */
    private int type;
    
    public Frequency(int i) {
        setType(i);
    }
    
    public Frequency(String s) {
        setType(s);
    }
    
    public void setType(int i) {
        if((i < 0) || (i > 3)) {
            type = 0;
        }
        type = i;
    }
    
    public void setType(String s) {
        switch(s) {
            case "AT_WILL":
            case "at_will":
            case "atwill":
            case "ATWILL":
                type = 0;
                break;
            case "EOT":
            case "eot":
                type = 1;
                break;
            case "SCENE":
            case "scene":
            case "ops":
            case "OPS":
                type = 2;
                break;
            case "DAILY":
            case "daily":
            case "opd":
            case "OPD":
                type = 3;
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
            return "AT_WILL";
        case 1:
            return "EOT";
        case 2: 
            return "SCENE";
        case 3:
            return "DAILY";
        default:
            return "AT_WILL";
        }
    }
    
    public Frequency copy() {
        return new Frequency(type);
    }
}
