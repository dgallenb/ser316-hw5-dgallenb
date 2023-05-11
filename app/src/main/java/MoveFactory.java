/**
 * A factory for producing Move objects.
 * @author DJ
 *
 */
public class MoveFactory {
    private static MoveFactory instance;
    
    private MoveFactory() {
        
    }
    
    /**
     * The Factory design pattern's primary method.
     * @return Returns the one instance of this factory allowed.
     */
    public static MoveFactory getInstance() {
        if (instance == null) {
            instance = new MoveFactory();
        }
        return instance;
    }
    
    public Move generateMove(String name, String description, 
            int db, int ac, int moveCat, int moveType, int frequency) {
        return new Move(name, description, db, ac, new MoveCategory(moveCat),
                new Frequency(frequency), new MonType(moveType));
    }
    
    /**
     * Generates a tier 1 move. Defined to be DB 1-5, random type, at will.
     * Type chosen is more likely to be the mon's type, more likely to be
     * Clear, less likely to be eclipse.
     * @param tier The integer indicating the rough power level of the moves to supply.
     * @param locked True if the move should be exactly the mon's type, 
     *      false if it's allowed to be random.
     * @return A new move.
     */
    public Move generateMove(int tier, int monType, boolean locked) {
        double[][] dbDistribution = { new double[] {0, 1},
            new double[] {0, 0.05, 0.1, 0.35, 0.35, 0.15}, 
            new double[] {0, 0, 0, 0, 0.05, 0.1, 0.35, 0.35, 0.15}, 
            new double[] {0, 0, 0, 0, 0, 0.05, 0.05, 0.05, 0.35, 0.35, 0.15}, 
            new double[] {0, 0, 0, 0, 0, 0, 0, 0.05, 0.05, 0.05, 0.35, 0.35, 0.15},
            new double[] {0, 0, 0, 0, 0, 1},
            new double[] {0, 0, 0, 0, 0, 0, 1},
            new double[] {0, 0, 0, 0, 0, 0, 0, 1},
            new double[] {0, 0, 0, 0, 0, 0, 0, 0, 1},
            new double[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            new double[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            new double[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            new double[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
        };
        double b = 0.05; // just to shorten the width of the matrix below
        double t = 1 - 17 * b;
                  
        double[][] transitionMatrix =  {
            new double[] {t, b, b, b, b,  b, b, b, b, b,  b, b, b, b, b,  b, b, b}, // normal
            new double[] {b, t, b, b, b,  b, b, b, b, b,  b, b, b, b, b,  b, b, b}, // fire
            new double[] {b, b, t, b, b,  b, b, b, b, b,  b, b, b, b, b,  b, b, b}, // water
            new double[] {b, b, b, t, b,  b, b, b, b, b,  b, b, b, b, b,  b, b, b}, // electric
            new double[] {b, b, b, b, t,  b, b, b, b, b,  b, b, b, b, b,  b, b, b}, // grass
            new double[] {b, b, b, b, b,  t, b, b, b, b,  b, b, b, b, b,  b, b, b}, // ice
            new double[] {b, b, b, b, b,  b, t, b, b, b,  b, b, b, b, b,  b, b, b}, // fighting
            new double[] {b, b, b, b, b,  b, b, t, b, b,  b, b, b, b, b,  b, b, b}, // poison
            new double[] {b, b, b, b, b,  b, b, b, t, b,  b, b, b, b, b,  b, b, b}, // ground
            new double[] {b, b, b, b, b,  b, b, b, b, t,  b, b, b, b, b,  b, b, b}, // flying
            new double[] {b, b, b, b, b,  b, b, b, b, b,  t, b, b, b, b,  b, b, b}, // psychic
            new double[] {b, b, b, b, b,  b, b, b, b, b,  b, t, b, b, b,  b, b, b}, // bug
            new double[] {b, b, b, b, b,  b, b, b, b, b,  b, b, t, b, b,  b, b, b}, // rock
            new double[] {b, b, b, b, b,  b, b, b, b, b,  b, b, b, t, b,  b, b, b}, // ghost
            new double[] {b, b, b, b, b,  b, b, b, b, b,  b, b, b, b, t,  b, b, b}, // dragon
            new double[] {b, b, b, b, b,  b, b, b, b, b,  b, b, b, b, b,  t, b, b}, // dark
            new double[] {b, b, b, b, b,  b, b, b, b, b,  b, b, b, b, b,  b, t, b}, // steel
            new double[] {b, b, b, b, b,  b, b, b, b, b,  b, b, b, b, b,  b, b, t} // fairy
            
        };
            
        int db = Utility.rollOnTable(dbDistribution[tier]);
        int moveType = monType;
        if (!locked) {
            moveType = Utility.rollOnTable(transitionMatrix[monType]);
        }
        
        
        int freq = 0;
        if (db < 6) {
            freq = 0;
        } else if (db < 9) {
            freq = 1;
        } else if (db < 12) {
            freq = 2;
        } else {
            freq = 3;
        }
        MonType type = new MonType(moveType);
        String name = Utility.getTypedName(type) + " " + Utility.getTypedMoveName(type);
        String frequencyDesc = "";
        switch (freq) {
            case 0:
                frequencyDesc = "at will";
                break;
            case 1:
                frequencyDesc = "every other turn";
                break;
            case 2: 
                frequencyDesc = "once per scene";
                break;
            case 3:
                frequencyDesc = "once per day";
                break;
            default:
                frequencyDesc = "at will";
                break;
            
        }
        int ac = Utility.d(3) + 1;
        String description = name + ": A DB" + db + " " + type.toString()
                + " move. AC: " + ac + ". Usable " + frequencyDesc + ".";
        // (String name, String description, int db, Frequency frequency, MonType type)
        return new Move(name, description, db, ac, 
                new MoveCategory(Utility.d(2)), new Frequency(freq), type);
    }
    
    /**
     * Generates a tier 1 move. Defined to be DB 1-5, random type, at will.
     * Type chosen is more likely to be the mon's type, more likely to be
     * Clear, less likely to be eclipse.
     * @param tier The integer indicating the rough power level of the moves to supply.
     * @return A new move.
     */
    public Move generateMove(int tier, int monType) {
        return generateMove(tier, monType, false);
        
    }
    
}
