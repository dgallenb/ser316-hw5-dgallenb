import java.util.HashMap;

public class ServantFactory extends CodemonFactory {
    
    public static HashMap<String, Codemon> servantData;
    public static HashMap<String, Move> moveData;
    public static HashMap<String, Move[]> movepoolData;
    
    protected ServantFactory() {
        super();
        loadMoveData();
        loadMovepoolData();
        loadServantData();
    }
    
    protected static void loadMoveData() {
        
    }
    
    protected static void loadMovepoolData() {
        
    }
    
    protected static void insertServantData(String name, int atk, int def, 
            int satk, int sdef, int spd) {
        insertServantData(name, 12, atk, def, satk, sdef, spd);
    }
    
    protected static void insertServantData(String name, int hp, int atk, int def, 
            int satk, int sdef, int spd) {
        servantData.put(name, new Codemon(new MonType(0), hp, atk, def, satk, sdef, spd,
                movepoolData.get(name), 0));
    }
    
    protected static void loadServantData() {
        servantData = new HashMap<String, Codemon>();
        //(MonType type, int hp, int atk, int def, int satk, int sdef, int spd, 
        //Move[] moves, int exp)
        insertServantData("Marie", 8, 8, 14, 14, 14);
        insertServantData("BB", 5, 11, 17, 17, 8);
        insertServantData("Miss Crane",  5, 5, 12, 11, 9);
        insertServantData("Meltryllis", 5, 5, 12, 11, 9);
        insertServantData("Lion King", 5, 11, 17, 17, 8);
        insertServantData("Koyanskaya", 17, 8, 17, 17, 11);
        insertServantData("MHX", 14, 11, 11, 8, 18);
        insertServantData("Melusine", 11, 18, 18, 14, 14);
        insertServantData("Ishtar", 14, 11, 11, 14, 17);
        insertServantData("Kingprotea", 18, 20, 20, 8, 14, 14);
                
    }
    
    public Codemon generateServant(String name, int lvl) {
        Codemon servant = servantData.get(name);
        servant.addExp(Utility.getExpFromLevel(lvl));
        return servant;
    }
}