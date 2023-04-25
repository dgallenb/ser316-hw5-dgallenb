/**
 * Still a blind idiot, but this doesn't display anything. 
 *      Used for running tests.
 * @author DJ
 *
 */
public class PredictableUI implements UI {
    private double[] sequence = new double[] 
        { 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};
    private int index = 0;

    @Override
    public void display(String s) {
        // TODO Auto-generated method stub
        System.out.println(s);
    }

    @Override
    public int getInt(int min, int max) {
        int output = (int) ((sequence[index] * (max - min)) + min + 1);
        index = (index + 1) % sequence.length;
        return output;
    }

    @Override
    public String getString(int max) {
        // TODO Auto-generated method stub
        return null;
    }

}