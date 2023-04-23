/**
 * A decorator type used to handle when a codemon has 2+ types.
 * @author DJ
 *
 */
public class MonTypeMulti extends MonType {
    MonType baseType;

    public MonTypeMulti() {
        super(0);
    }
    
    public MonTypeMulti(int i) {
        super(i);
        
    }
    
    public MonTypeMulti(MonType t, int i) {
        super(i);
        baseType = t;
    }
    
    public MonTypeMulti(int i, int j) {
        super(i);
        baseType = new MonType(j);
    }
    
    /**
     * 
     * @param t1
     * @param t2 a Multi-type class for a codemon type.
     */
    public MonTypeMulti(MonType t1, MonTypeMulti t2) {
        super(t1.getTypeNum());
        baseType = t2;
    }
    
    /**
     * Returns true if the outer type is the same as this one, or if the inner type
     * is the same.
     */
    public boolean sameMonType(MonType m) {
        boolean output = super.sameMonType(m);
        if(baseType != null) {
            output = output || m.sameMonType(baseType);
        }
        
        return output;
    }

    public double getTypeMod(MonType atkType) {
        double outerTypeMod = super.getTypeMod(atkType);
        if(baseType != null) {
            outerTypeMod = baseType.getTypeMod(atkType);
        }
        return outerTypeMod;
    }
    
}
