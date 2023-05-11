/**
 * A set of utility methods that I want all classes to have access to.
 * Mostly static numbers or tables to be rolled on.
 * @author DJ
 *
 */
public class Utility {
    
    private static double[] rngStabilizer;
    private static int stabilizerIndex = -1;
    
    public static final int STAT_LIST_LENGTH = 8;

    /**
     * Removes randomness from random number generation and instead cycles through 
     * the given values.
     * Added to enable testing to predict outcomes of accuracy rolls and damage rolls.
     * @param rolls The values to select in succession when a random number is requested.
     */
    public static void stabilizeRng(double[] rolls) {
        rngStabilizer = new double[rolls.length];
        for (int i = 0; i < rolls.length; ++i) {
            rngStabilizer[i] = rolls[i];
        }
        stabilizerIndex = 0;
    }
    
    /**
     * Resets the saved number sequence so random numbers are used once again.
     */
    public static void randomizeRng() {
        rngStabilizer = null;
    }
    
    /**
     * Assumes the table values add up to 1.
     * @param table array of values adding up to 1, representing the probability
     *      of each entry.
     * @return the index indicated by the table array that the random number rolled.
     */
    public static int rollOnTable(double[] table) {
        double rng = rng();
        double sum = 0;
        for (int i = 0; i < table.length; ++i) {
            sum += table[i];
            if (rng < sum) {
                return i;
            }
        }
        return 0; // shouldn't be reachable
    }
    
    /**
     * Returns a random name of the type requested.
     * @param t the MonType of the codemon/attack to be named.
     * @return A name that can be used for a codemon or move.
     */
    public static String getTypedName(MonType t) {
        String[][] namePool = {
            new String[] {"Crystal", "Pure", "Lucent", 
                "Luminous", "Glitter", "Brilliant", "Glow"},
            new String[] {"Blaze", "Burn", "Scorch", 
                    "Inferno", "Flame", "Heat", "Volcano"},
            new String[] {"Flow", "Downpour", "Tide", 
                    "Course", "Gush", "Spout", "Fin"},
            new String[] {"Data", "Lightning", "Thunder", 
                "Shock", "Volt", "Surge", "Zap"},
            new String[] {"Forest", "Oak", "Vine", 
                    "Flower", "Vigor", "Shoot", "Bloom"},
            new String[] {"Frost", "Chill", "Ice", 
                    "Frigid", "Winter", "Niver", "Winter"},
            new String[] {"Discipline", "Force", "Muscle", 
                    "Palm", "Foot", "Mach", "Flex"},
            new String[] {"Toxic", "Venom", "Caustic", 
                    "Serpent", "Sting", "Sick", "Viral"},
            new String[] {"Earth", "Terra", "Subterranea", 
                    "Digger", "Drill", "Tunnel", "Mine"},
            new String[] {"Frost", "Chill", "Ice", 
                    "Frigid", "Winter", "Niver", "Winter"},
            new String[] {"Bluster", "Gust", "Draft", 
                    "Squall", "Swift", "Fleet", "Wing"},
            new String[] {"Mind", "Psi", "Esper", 
                    "Psion", "Erudite", "Mental", "Psych"},
            new String[] {"Insect", "Weevile", "Spider", 
                    "Chitin", "Pincer", "Chirp", "Creep"},
            new String[] {"Obsidian", "Onyx", "Ruby", 
                    "Granite", "Opal", "Sapphire", "Emerald"},
            new String[] {"Spook", "Lurk", "Startle", 
                    "Stalk", "Skulk", "Fade", "Fear"},
            new String[] {"Draco", "Titan", "Scale", 
                    "Hoard", "Rage", "Twister", "Shenlong"},
            new String[] {"Occluded", "Dark", "Void", 
                    "Night", "Shadow", "Albedo", "Nebula"},
            new String[] {"Metal", "Sturdy", "Sound", 
                    "Immovable", "Chrome", "Mech", "Iron"},
            new String[] {"Fickle", "Fae", "Seelie", 
                    "Glimmer", "Fable", "Charm", "Flutter"}          
            };
        int roll = (int) (rng() * namePool[t.getTypeNum()].length);
        return namePool[t.getTypeNum()][roll];
    }
    
    /**
     * Returns a random name for a move. Type irrelevant at this time.
     * @param t The codemon type of the move to be named. Unused at present.
     * @return A random name to use as the root of a move.
     */
    public static String getTypedMoveName(MonType t) {
        String[] namePool = {"Beam", "Burst", "Strike", "Blast",
            "Blow", "Force", "Blade", "Fist", "Claw", 
            "Fang", "Slap", "Kick", "Bite", "Song", 
            "Call", "Howl", "Drop", "Fall"
            };
        int roll = (int) (rng() * namePool.length);
        return namePool[roll];
    }
    
    /**
     * Stealing PTU's exp chart up to lvl 40.
     * @param exp The exp the codemon currently has.
     * @return lvl  The expected level of the codemon who has the given exp.
     */
    public static int getLvlFromExp(int exp) {
        
        int[] expTable = {0, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 
            110, 135, 160, 190, 220, 250, 285, 320, 360, 400, 
            460, 530, 600, 670, 745, 820, 900, 990, 1075, 1165, 
            1260, 1355, 1455, 1555, 1660, 1770, 1880, 1995, 2110, 2230
        };
        
        for (int i = 0; i < 40; ++i) {
            if (exp < expTable[i]) {
                return i - 1;
            }
        }
        return 40;
    }
    
    /**
     * Returns the exp a codemon should have if it has the given level.
     * @param lvl The level of the codemon.
     * @return The minimum amount of exp the codemon should have.
     */
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
     * @param db The "damage base" of a move. 
     * @return The damage that should be dealt based on the input "damage base".
     */
    public static int dbToDamage(int db) { // I'm stealing PTU's damage base rolls.
        switch (db) {
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
    
    /**
     * Rolls an N-sided die and returns the result.
     * @param dieSize The number of sides on the die.
     * @return The result of the roll.
     */
    public static int d(int dieSize) {
        return (int) (rng() * dieSize + 1);
    }
    
    /**
     * Rolls M N-sided dice and returns the sum of the rolls.
     * @param dieCount How many dice to roll.
     * @param dieSize The size of each die.
     * @return The sum of all dice rolled.
     */
    public static int d(int dieCount, int dieSize) {
        int total = 0;
        for (int i = 0; i < dieCount; ++i) {
            total += (int) (rng() * dieSize + 1);
        }
        return total;
    }
    
    /**
     * Weather gives a slight modifier to the damage dealt.
     * @param type The type of the move to be used.
     * @param weather The current weather.
     * @return The amount of extra damage to be dealt in that weather.
     */
    public static int weatherDamageBonus(MonType type, Weather weather) {
        int[][] damageMatrix = {
            new int[] {5, 0, 0, 0, 0, 0, 5}, 
            new int[] {0, 5, 0, 0, 0, 0, -5}, 
            new int[] {0, 0, 5, 0, 0, 0, -5}, 
            new int[] {0, 0, 0, 5, 0, 0, -5}, 
            new int[] {0, 0, 0, 0, 5, 0, -5}, 
            new int[] {0, 0, 0, 0, 0, 5, -5}, 
            new int[] {0, 0, 0, 0, 0, 0, 15}
        };
        return damageMatrix[type.getTypeNum()][weather.getTypeNum()];
    }
    
    /**
     * Rolls type-specific probability of hp, atk, def, and spd increases.
     * If a probability is over 1, then the value always increases by at least the
     * whole number. 
     * @param type The type of the mon.
     * @param scalarMods Some evolved codemon will increase odds of stat increases.
     * @return The total bonus to each stat that a codemon gets.
     */
    public static int[] getLevelUpBonus(int[] baseStats, int[] statSpread, double[] scalarMods) {
        int[] totals = {1, 0, 0, 0, 0, 0};
        
        
        return totals;
        /*
        int[] totals = {0, 0, 0, 0};
        double[][] statChance = {
            new double[] {4.5, 0.7, 1.1, 0.2}, 
            new double[] {2.5, 0.7, 0.6, 0.25}, 
            new double[] {1.5, 1.2, 0.3, 0.65}, 
            new double[] {1.2, 0.9, 0.9, 0.45}, 
            new double[] {2.1, 0.9, 0.7, 0.25}, 
            new double[] {2.9, 0.8, 0.8, 0.35}, 
            new double[] {2.5, 0.9, 0.7, 1.1}
        };
        
        for (int i = 0; i < totals.length; ++ i) {
            double rng = rng();
            double stat = statChance[type.getTypeNum()][i] + scalarMods[i];
            double chance = stat;
            if (stat >= 1) {
                chance = stat - ((int) stat);
                totals[i] = ((int) stat);
            }
            totals[i] += rng < chance ? 1 : 0;
        }
        
        return totals;
        */
    }
    
    /*
     * Returns the bonus values to provide to a codemon on level up that doesn't specify 
     *      extra bonuses.
     * @param type The codemon's type.
     * @return An array of the bonuses to give to the codemon for leveling up.
     */
    /*public static int[] getLevelUpBonus(MonType type) {
        return getLevelUpBonus(type, new double[] {0, 0, 0, 0});
    }
    */
    
    /**
     * The core random number generator for the class. 
     * @return Either a random number between 0 and 1, or a value in a predetermined 
     *      sequence (used only so that testing coverage is easier).
     */
    private static double rng() {
        if (rngStabilizer != null) {
            if (rngStabilizer[stabilizerIndex] >= 1) {
                rngStabilizer[stabilizerIndex] = rngStabilizer[stabilizerIndex]
                    - ((int) rngStabilizer[stabilizerIndex]);
            }
            stabilizerIndex = (stabilizerIndex + 1) % rngStabilizer.length;
            return rngStabilizer[stabilizerIndex];
        }
        return Math.random();
    }
    
    
    /*
    public static int computeExp(TrainerEntity t) {
        int expTotal = 0;
        for (int i = 0; i < t.getTrainer().getMonCount(); ++i) {
            Codemon mon = t.getTrainer().getMon(i);
            if (mon != null) {
                expTotal += mon.getLvl();
            }
        }
        
        return expTotal;
    }
    */
}

