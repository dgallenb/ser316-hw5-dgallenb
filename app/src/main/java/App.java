
/**
 * Main function is located here.
 * @author DJ
 *
 */
public class App {
    
    public void println(String text) {
        System.out.println(text);
    }

    /**
     * Main. You know, the main class.
     * @param args I forget what this is for.
     */
    public static void main(String[] args) {
        
        
        GamePlay game = new GamePlay(new BlindIdiotUI());
        //GamePlay game = new GamePlay();
        game.play(50);
    }
}
