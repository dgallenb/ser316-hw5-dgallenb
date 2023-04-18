
public class MoveFactory {
    private static MoveFactory instance;
    
    private MoveFactory() {
        
    }
    
    public static MoveFactory getInstance() {
        if(instance == null) {
            instance = new MoveFactory();
        }
        return instance;
    }
    
    public Move generateMove(String name, String description, 
            int moveType, int db, int frequency) {
        return new Move(name, description, db,
                new Frequency(frequency), new MonType(moveType));
    }
    
    /**
     * Generates a tier 1 move. Defined to be DB 1-5, random type, at will.
     * Type chosen is more likely to be the mon's type, more likely to be
     * Clear, less likely to be eclipse.
     * @param tier
     * @return
     */
    public Move generateMove(int tier, int monType) {
        
        double[][] dbDistribution = { new double[] {0, 1},
                new double[] {0, 0.05, 0.1, 0.35, 0.35, 0.15}, 
                new double[] {0, 0, 0, 0, 0.05, 0.1, 0.35, 0.35, 0.15}, 
                new double[] {0, 0, 0, 0, 0, 0.05, 0.05, 0.05, 0.35, 0.35, 0.15}, 
                new double[] {0, 0, 0, 0, 0, 0, 0, 0.05, 0.05, 0.05, 0.35, 0.35, 0.15}
        };
              
        double[][] transitionMatrix =  {
                new double[] {0.5, 0.08, 0.08, 0.08, 0.08, 0.08, 0.1}, // clear
                new double[] {0.2, 0.5, 0.07, 0.07, 0.07, 0.07, 0.02}, // cloudy
                new double[] {0.2, 0.07, 0.5, 0.07, 0.07, 0.07, 0.02}, // rainy
                new double[] {0.2, 0.07, 0.07, 0.5, 0.07, 0.07, 0.02}, // windy
                new double[] {0.2, 0.07, 0.07, 0.07, 0.5, 0.07, 0.02}, // stormy
                new double[] {0.2, 0.07, 0.07, 0.07, 0.07, 0.5, 0.02}, // snowy
                new double[] {0.05, 0.09, 0.09, 0.09, 0.09, 0.09, 0.5} // eclipse
        };
        
        int db = Utility.rollOnTable(dbDistribution[tier]);
        int moveType = Utility.rollOnTable(transitionMatrix[monType]);
        
        int freq = 0;
        if(db < 6) {
            freq = 0;
        } else if(db < 9) {
            freq = 1;
        } else if(db < 12) {
            freq = 2;
        } else {
            freq = 3;
        }
        MonType type = new MonType(moveType);
        String name = Utility.getTypedName(type) + " " + Utility.getTypedMoveName(type);
        String frequencyDesc = "";
        switch(freq) {
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
            
        }
        String description = name + ": A DB" + db + " " + type.toString() + 
                " move. Usable " + frequencyDesc + ".";
        // (String name, String description, int db, Frequency frequency, MonType type)
        return new Move(name, description, db, new Frequency(freq), type);
    }
    
}
