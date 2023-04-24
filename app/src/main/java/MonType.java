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
    
    public MonType(String s) {
        setType(s);
    }
    
    protected void setType(int i) {
        if ((i < 0) || (i > 6)) {
            type = 0;
        }
        type = i;
    }
    
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
        double[][] modMatrix = {
            new double[] {1, 1, 1, 1, 1, 1, 2}, // clear
            new double[] {1.25, 1, 1.5, 0.5, 1.5, 0.5, 0.5}, // cloudy
            new double[] {1.25, 0.5, 1, 1.5, 0.5, 1.5, 0.5}, // rainy
            new double[] {1.25, 1.5, 0.5, 1, 1.5, 0.5, 0.5}, // windy
            new double[] {1.25, 0.5, 1.5, 0.5, 1, 1.5, 0.5}, // stormy
            new double[] {1.25, 1.5, 0.5, 1.5, 0.5, 1, 0.5}, // snowy
            new double[] {0.5, 1.4, 1.4, 1.4, 1.4, 1.4, 0.5} // eclipse
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
