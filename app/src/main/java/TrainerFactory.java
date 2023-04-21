
public class TrainerFactory {
    private static TrainerFactory instance;
    
    private TrainerFactory() {
        
    }
    
    public static TrainerFactory getInstance() {
        if(instance == null) {
            instance = new TrainerFactory();
        }
        return instance;
    }
    
    public Trainer generateTrainerWithCodemonT1(int targetLvl) {
        Trainer t = new Trainer();
        t.setName(getTrainerName());
        int monCount = Utility.d(3);
        for(int i = 0; i < monCount; ++i) {
            int actualTarget = Math.max(1, targetLvl - 2 - i);
            Codemon c = CodemonFactory.getInstance().
                    generateCodemonWithT1Moves(actualTarget);
            t.addMon(c);
        }
        
        
        return t;
    }
    
    public String getTrainerName() {
        String[] names = new String[] { "Abbigail", "Bradamante", "Charlemagne", 
                "Enkidu", "Frankenstein", "Gawain", "Hokusai", "Iskander", "Jing Ke", 
                "Leonidas", "Marie", "Nightingale", "Penthesilea"
        };
        return names[Utility.d(names.length) - 1];
    }
}
