/**
 * Represents a move in the context of a battle. Has a codemon, a move,
 * and the weather state.
 * @author DJ
 *
 */
public class BattleMove {
    private Move move;
    private Codemon mon;
    private Weather weather;
    
    public BattleMove(Codemon mon, Move move, Weather weather) {
        this.mon = mon;
        this.move = move;
        this.weather = weather;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Codemon getMon() {
        return mon;
    }

    public void setMon(Codemon mon) {
        this.mon = mon;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
