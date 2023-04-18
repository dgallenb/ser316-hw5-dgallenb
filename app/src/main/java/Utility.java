
public class Utility {

    /**
     * Assumes the table values add up to 1.
     * @param table array of values adding up to 1, representing the probability
     * of each entry.
     * @return the index indicated by the table array that the random number rolled.
     */
    public static int rollOnTable(double[] table) {
        double rng = Math.random();
        double sum = 0;
        for(int i = 0; i < table.length; ++i) {
            sum += table[i];
            if(rng < sum) {
                return i;
            }
        }
        return 0; // shouldn't be reachable
    }
    
    public static String getTypedName(MonType t) {
        String[][] namePool = {
            new String[] {"Crystal", "Pure", "Lucent", 
                    "Luminous", "Glitter", "Brilliant", "Glow"},
            new String[] {"Fog", "Mist", "Dim", 
                    "Blur", "Vaporous", "Fuzz", "Haze"},
            new String[] {"Flow", "Downpour", "Tide", 
                    "Course", "Gush", "Spout", "Progress"},
            new String[] {"Bluster", "Gust", "Draft", 
                    "Squall", "Swift", "Fleet", "Wing"},
            new String[] {"Thunder", "Lightning", "Tempest", 
                    "Howl", "Savage", "Torrent", "Bitter"},
            new String[] {"Frost", "Chill", "Ice", 
                    "Frigid", "Winter", "Niver", "Winter"},
            new String[] {"Occluded", "Dark", "Void", 
                    "Night", "Shadow", "Albedo", "Nebula"}
            };
        int roll = (int) Math.random() * namePool[t.getTypeNum()].length;
        return namePool[t.getTypeNum()][roll];
    }
    
    public static String getTypedMoveName(MonType t) {
        String[] namePool = {"Beam", "Burst", "Strike", "Blast",
                        "Blow", "Force", "Blade", "Fist", "Claw", 
                        "Fang", "Slap", "Kick", "Bite", "Song", 
                        "Call", "Howl", "Drop", "Fall"
                        };
            int roll = (int) Math.random() * namePool.length;
            return namePool[roll];
    }
    
    /**
     * Stealing PTU's exp chart up to lvl 40.
     * @param exp
     * @return lvl
     */
    public static int getLvlFromExp(int exp) {
        
        int[] expTable = {0, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 
                110, 135, 160, 190, 220, 250, 285, 320, 360, 400, 
                460, 530, 600, 670, 745, 820, 900, 990, 1075, 1165, 
                1260, 1355, 1455, 1555, 1660, 1770, 1880, 1995, 2110, 2230
        };
        
        for(int i = 0; i < 40; ++i) {
            if(exp < expTable[i]) {
                return i - 1;
            }
        }
        return 40;
    }
    
    public static int getExpFromLevel(int lvl) {
        int[] expTable = {0, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 
                110, 135, 160, 190, 220, 250, 285, 320, 360, 400, 
                460, 530, 600, 670, 745, 820, 900, 990, 1075, 1165, 
                1260, 1355, 1455, 1555, 1660, 1770, 1880, 1995, 2110, 2230
        };
        return expTable[lvl];
    }
    
    /**
     * Also stolen from PTU's damage bases. 
     * @param db 
     * @return
     */
    public static int dbToDamage(int db) { // I'm stealing PTU's damage base rolls.
        switch(db) {
        case 0:
            return 1;
        case 1:
            return d(6) + 1;
        case 2:
            return d(6) + 3;
        case 3:
            return d(6) + 5;
        case 4:
            return d(8) + 6;
        case 5:
            return d(8) + 8;
        case 6:
            return d(2,6) + 8;
        case 7:
            return d(2,6) + 10;
        case 8:
            return d(2,8) + 10;
        case 9:
            return d(2,10) + 10;
        case 10:
            return d(3,8) + 10;
        case 11:
            return d(3,10) + 10;
        case 12:
            return d(3,12) + 10;
        case 13:
            return d(4,10) + 10;
        case 14:
            return d(4,10) + 15;
        case 15:
            return d(4,10) + 20;
        case 16:
            return d(5,10) + 20;
        case 17:
            return d(5,12) + 25;
        case 18:
            return d(6,12) + 25;
        case 19:
            return d(6,12) + 30;
        case 20:
            return d(6,12) + 35;
            
        default:
            return 1;
        }
        
    }
    
    public static int d(int dieSize) {
        return (int) (Math.random() * dieSize + 1);
    }
    
    public static int d(int dieCount, int dieSize) {
        int total = 0;
        for(int i = 0; i < dieCount; ++i) {
            total += (int) (Math.random() * dieSize + 1);
        }
        return total;
    }
    
    public static int weatherDamageBonus(MonType type, Weather weather) {
        int[][] damageMatrix = {
                new int[] {5, 0, 0, 0, 0, 0, -5}, 
                new int[] {0, 5, 0, 0, 0, 0, -5}, 
                new int[] {0, 0, 5, 0, 0, 0, -5}, 
                new int[] {0, 0, 0, 5, 0, 0, -5}, 
                new int[] {0, 0, 0, 0, 5, 0, -5}, 
                new int[] {0, 0, 0, 0, 0, 5, -5}, 
                new int[] {5, 0, 0, 0, 0, 0, 15}
        };
        return damageMatrix[type.getTypeNum()][weather.getTypeNum()];
    }
}

