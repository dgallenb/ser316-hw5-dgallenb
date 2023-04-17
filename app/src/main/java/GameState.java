
public interface GameState {
    void process();
    String promptUser();
    void receiveInput(String input);
    
}
