/**
 * A factory for producing trainers.
 * @author DJ
 *
 */
public class TrainerFactory {
    private static TrainerFactory instance;
    
    private TrainerFactory() {
        
    }
    
    /**
     * Factory pattern method. Returns the one instance of the class allowed.
     * @return The trainer factory to use to make new trainers.
     */
    public static TrainerFactory getInstance() {
        if (instance == null) {
            instance = new TrainerFactory();
        }
        return instance;
    }
    
    /**
     * Generates a trainer with a random number of codemon between 1 and 3, 
     *      at a level approximating the player's level.
     * @param targetLvl The level of codemon the game should assume the player has.
     * @return A brand spanking new trainer to battle.
     */
    public Trainer generateTrainerWithCodemonT1(int targetLvl) {
        Trainer t = new Trainer();
        t.setName(getTrainerName());
        int monCount = Utility.d(3);
        for (int i = 0; i < monCount; ++i) {
            int actualTarget = Math.max(1, targetLvl - 2 - i);
            Codemon c = CodemonFactory.getInstance()
                .generateCodemonWithT1Moves(actualTarget);
            t.addMon(c);
        }
        
        
        return t;
    }
    
    /**
     * Generates a random name from an unbiased list of definitely-modern names.
     * @return A string containing the name to give the trainer.
     */
    public String getTrainerName() {
        String[] names = new String[] { "Abbigail", "Bradamante", "Charlemagne", 
            "Enkidu", "Frankenstein", "Gawain", "Hokusai", "Iskander", "Jing Ke", 
            "Leonidas", "Marie", "Nightingale", "Penthesilea"
        };
        return names[Utility.d(names.length) - 1];
    }
}
