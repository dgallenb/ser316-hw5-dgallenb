/**
 * Encapsulation of a codemon type. There are seven.
 * @author DJ
 *
 */
public class MonType {
    // types: quartz, agate, sapphire, topaz, opal, peridot, onyx
    protected int type;
    
    public MonType(int i) {
        setType(i);
    }
    
    /*
    public MonType(String s) {
        setType(s);
    }
    */
    protected void setType(int i) {
        if ((i < 0) || (i > 6)) {
            type = 0;
        }
        type = i;
    }
    
    /*
    protected void setType(String s) {
        switch (s) {
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
    */
    public int getTypeNum() {
        return type;
    }
    
    /**
     * Converts the codemon type into a string.
     * @return A string representation of the type.
     */
    public String toString() {
        switch (type) {
            case 0:
                return "Normal";
            case 1:
                return "Fire";
            case 2: 
                return "Water";
            case 3:
                return "Electric";
            case 4:
                return "Grass";
            case 5:
                return "Ice";
            case 6:
                return "Fighting";
            case 7:
                return "Poison";
            case 8:
                return "Ground";
            case 9:
                return "Flying";
            case 10:
                return "Psychic";
            case 11:
                return "Bug";
            case 12:
                return "Rock";
            case 13:
                return "Ghost";
            case 14:
                return "Dragon";
            case 15:
                return "Dark";
            case 16:
                return "Steel";
            case 17:
                return "Fairy";
            default:
                return "Normal";
        }
    }
    
    public MonType copy() {
        return new MonType(type);
    }
    
    /**
     * Equivalent to montype here. Gets overwritten by the child class that 
     * handles multityped codemons.
     * @param m The monType to compare to
     * @return True if the mon types are the same (or, in a multitype mon,
     *      if one of the types inside each matches). False otherwise.
     */
    public boolean sameMonType(MonType m) {
        return this.equals(m);
    }
    
    public int getEffectiveDamage(int damage, MonType atkType) {
        return (int) (damage * getTypeMod(atkType));
    }
    
    // clear beats eclipse, everything else beats clear
    // cloudy beats rainy, stormy
    // rainy beats windy, snowy,
    // windy beats stormy, cloudy
    // stormy beats snowy, rainy
    // snowy beats cloudy, windy
    /**
     * Returns the scalar to multiply damage by for effective/ineffective moves.
     * @param defTypeNum The number indicator for the defender's type.
     * @param atkTypeNum The number indicator for the defender's type.
     * @return A scalar to multiply damage by.
     */
    protected static double typeMod(int defTypeNum, int atkTypeNum) {
        double superEffective = 1.5;
        double ineffective = 0.5;
        double e = superEffective;
        double i = ineffective;
        double[][] modMatrix = {
                // NOR, FIR, WAT, ELE, GRA,   ICE, FIG, POI, GRO, FLY,   
                // PSY, BUG, ROC, GHO, DRA,   DAR, STE, FAI
            new double[] {1, 1, 1, 1, 1,  1, 1, 1, 1, 1,   1, 1, i, 0, 1,   1, i, 1}, // normal
            new double[] {1, i, i, 1, e,  e, 1, 1, 1, 1,   1, e, i, 1, i,   1, e, 1}, // fire
            new double[] {1, e, i, 1, i,  1, 1, 1, e, 1,   1, 1, e, 1, i,   1, 1, 1}, // water
            new double[] {1, 1, e, i, i,  1, 1, 1, 0, e,   1, 1, 1, 1, i,   1, 1, 1}, // electric
            new double[] {1, i, e, 1, i,  1, 1, i, e, i,   1, i, e, 1, i,   1, 1, 1}, // grass
            new double[] {1, i, i, 1, e,  i, 1, 1, e, e,   1, 1, 1, 1, e,   1, i, 1}, // ice
            new double[] {e, 1, 1, 1, 1,  e, 1, i, 1, i,   i, i, e, 0, 1,   e, e, i}, // fighting
            new double[] {1, 1, 1, 1, e,  1, 1, i, i, 1,   1, 1, i, i, 1,   1, 0, i}, // poison
            new double[] {1, e, 1, e, i,  1, 1, e, 1, 0,   1, i, e, 1, 1,   1, e, 1}, // ground
            new double[] {1, 1, 1, i, e,  1, e, 1, 1, 1,   1, e, i, 1, 1,   1, i, 1}, // flying
            new double[] {1, 1, 1, 1, 1,  1, e, e, 1, 1,   i, 1, 1, 1, 1,   0, i, 1}, // psychic
            new double[] {1, i, 1, 1, e,  1, i, i, 1, i,   e, 1, 1, i, 1,   e, i, i}, // bug
            new double[] {1, e, 1, 1, 1,  e, i, 1, i, e,   1, e, 1, 1, 1,   1, i, 1}, // rock
            new double[] {0, 1, 1, 1, 1,  1, 1, 1, 1, 1,   e, 1, 1, e, 1,   i, 1, 1}, // ghost
            new double[] {1, 1, 1, 1, 1,  1, 1, 1, 1, 1,   1, 1, 1, 1, e,   1, i, 0}, // dragon
            new double[] {1, 1, 1, 1, 1,  1, i, 1, 1, 1,   e, 1, 1, e, 1,   i, 1, i}, // dark
            new double[] {1, i, i, i, 1,  i, 1, 1, 1, 1,   1, 1, e, 1, 1,   1, i, e}, // steel
            new double[] {1, i, 1, 1, 1,  1, e, i, 1, 1,   1, 1, 1, 1, e,   e, i, 1}  // fairy
            
        };
        double scalar = modMatrix[atkTypeNum][defTypeNum];
        return scalar;
    }
    
    protected static double typeMod(MonType defType, MonType atkType) {
        return typeMod(defType.getTypeNum(), atkType.getTypeNum());
    }
    
    public double getTypeMod(MonType atkType) {
        return typeMod(this.type, atkType.getTypeNum());
    }
}
